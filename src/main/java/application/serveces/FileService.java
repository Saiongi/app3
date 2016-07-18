package application.serveces;

import application.model.document.Document;
import application.model.document.Incoming;
import application.model.document.Outgoing;
import application.model.document.Task;
import application.model.staff.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Света on 05.07.2016.
 */

public class FileService {

     private static Connection con = null;
    // переменная для хранения Persons
    Collection<Person> personsCollection;
    // переменная для хранения Departments
    Departments departments;
    // переменная для хранения Organizations
    Organizations organizations;
    //переменная для хранения объектов
    Object obj;
    // переменная для хранения файлов
    File file;
    DocService docService;
    Persons persons;
    //для хранения документов
    TreeSet<Document> allDoc;
    //для хранения документов с искомым id
    TreeSet<Document> idFindDoc;
FileService() throws URISyntaxException {

    personsCollection = readFiles();
    //создаем экземпляр DocService
    docService = new DocService();
    //сохраняем person в docFieldStorage
    docService.savePersons(persons);
    //создаем документы
     allDoc = createDocuments();
}
    //считываем person.xml
    public Collection<Person> readFiles() throws URISyntaxException {
                try {
                    ClassLoader classLoader = getClass().getClassLoader();
                    URL url = classLoader.getResource("person.xml");
                    file = new File(url.getPath());
                    JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    Object o = jaxbUnmarshaller.unmarshal(file);
                     persons = (Persons) o;
                    return persons.person;
                } catch (Exception ex) {
                    Logger.getLogger(FileService.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                //если неудача - возвращаем null
            return null;
            }
    //ищем документ по id
    public TreeSet<Document> findDocId(TreeSet<Document> allDoc, int findId){
        Map<Integer, TreeSet<Document>> docsByPersonMap = new TreeMap<Integer, TreeSet<Document>>();

        //сортируем документ по Id автора
        for (Document d: allDoc) {
            if (! docsByPersonMap.containsKey(d.getAuthor().getId())){
                docsByPersonMap.put(d.getAuthor().getId(), new TreeSet<Document>());
            }
            docsByPersonMap.get(d.getAuthor().getId()).add(d);
        }

        // Ищем документы по  искомому id и записываем их в treeset idFindDoc
        for (Integer id : docsByPersonMap.keySet()){
              if (id==findId) {
                  idFindDoc = docsByPersonMap.get(id);
              }
        }
        //если документов в treset не записалось,
        // т.е. не найдены документы с автром с искомым id тогда возвращаем null
        if (idFindDoc.isEmpty()) {
            return null;
        //если документы найдены , возвращаем коллекцию
       }else return idFindDoc;
    }

    public TreeSet<Document>  createDocuments(){
        //доп масссив для случайной генерации одного из документов
        Class[] classDoc = new Class[3];
        classDoc[0] = Task.class;
        classDoc[1] = Outgoing.class;
        classDoc[2] = Incoming.class;
        //создаем TreeSet для хранения документов
        TreeSet<Document> allDoc = new TreeSet<Document>();
        int p;//переменная для хранения случайного значения
        //создаем документы
        for (int i = 0; i < 30; i++) {
            p = (int) (Math.random() * 3);
            Document doc = docService.createDoc(classDoc[p]);
            if (doc != null) {
                allDoc.add(doc);
            }
        }
        //возвращаем TreeSet с документами
        return allDoc;
    }

    public void insertPerson(Person person) throws SQLException {

        PreparedStatement stmt = null;

            stmt = con.prepareStatement(
                    "INSERT INTO person " +
                            "(id, name, secondname, surname, position) " +
                            "VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, person.getId());
            stmt.setString(2, person.getName());
            stmt.setString(3, person.getSecondName());
            stmt.setString(4, person.getSurname());
            stmt.setString(5, person.getPosition());
            stmt.execute();
    }

    public void insertPersonFromPersonsCollection() throws SQLException {
        //Создаем соединение с бд
        con = null;
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
        String url = "jdbc:derby:appBase;create=true";
        //String url = "jdbc:derby://localhost:1527/appBase;create=true";
        //Имя пользователя БД
        //  String name = "user";
        //Пароль
        // String password = "123456";
        try {
            //Загружаем драйвер
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //Создаём соединение
            con = DriverManager.getConnection(url);
            //записываем данные в бд
            for (Person p: persons.person){
                insertPerson(p);
            }

        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public  void createDBConnection() {
        DerbyDBManager db = new DerbyDBManager("ApplicationDB");

        try {
            try {
                // запись данных в таблицу

                fillDB(db, persons);
            } catch (SQLException e) {
                // если БД не существовала, то создаем таблицу
                // и после этого заполняем её значениями
                db.executeUpdate("CREATE TABLE Person(id int, name varchar(128)," +
                        " secondname varchar(128), surname varchar(128), position varchar(128))");
                fillDB(db, persons);

            }
            //вывод данных
            ResultSet rs = db.executeQuery("SELECT * FROM Person");
            String dataFromTable="";
            while(rs.next()) {

                dataFromTable=dataFromTable+rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5);
            }
            String pr = dataFromTable;
            int p=0;

        } catch (SQLException e) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public  void fillDB(DerbyDBManager db, Persons persons) throws SQLException {
        db.executeUpdatePersons(persons);
    }


}

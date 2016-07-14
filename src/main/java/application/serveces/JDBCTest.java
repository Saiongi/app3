package application.serveces;

    import application.model.document.NotFoundException;
    import application.model.staff.Person;
    import application.model.staff.Persons;

    import javax.xml.bind.JAXBContext;
    import javax.xml.bind.Unmarshaller;
    import java.io.File;
    import java.net.URISyntaxException;
    import java.net.URL;
    import java.sql.*;
    import java.util.Collection;
    import java.util.logging.*;

public class JDBCTest {
    /*
    private static Connection con;
    Persons persons;
    File file;
    Collection<Person> personsCollection;
    FileService fileService;


        public static void main(String[] args) {

            //Создаем соединение с бд

            Connection connection = null;
            //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
            String url = "jdbc:derby://localhost:1527/appbase";
            //Имя пользователя БД
            String name = "user";
            //Пароль
            String password = "123456";
            try {
                //Загружаем драйвер
                Class.forName("org.apache.derby.jdbc.ClientDriver");
               // System.out.println("Драйвер подключен");
                //Создаём соединение
                connection = DriverManager.getConnection(url, name, password);
              //  System.out.println("Соединение установлено");

      //записываем данные в бд
                for(Person pers: fileService.readFiles()){

                }

            } catch (Exception ex) {
                //выводим наиболее значимые сообщения
                Logger.getLogger(JDBCTest.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    public void insertPerson(Person person) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "INSERT INTO staff.person " +
                            "(id, name, secondname, surname, position) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, person.getId());
            stmt.setString(2, person.getName());
            stmt.setString(3, person.getSecondName());
            stmt.setString(4, person.getSurname());
            stmt.setString(5, person.getPosition());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public Collection<Person> readFiles() throws URISyntaxException {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource("person.xml");
            if (url != null) {
                file = new File(url.getPath());
            }else{
                throw new NotFoundException("File is not found!");
            }
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
    }*/
}


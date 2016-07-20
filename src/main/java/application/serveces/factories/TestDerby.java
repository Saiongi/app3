package application.serveces.factories;

import application.model.staff.Person;
import application.model.staff.Persons;
import application.serveces.DerbyDBManager;
import application.serveces.FileService;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Светлана on 20.07.2016.
 */
public class TestDerby {
    private static Connection con = null ;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private static final String url = "jdbc:derby:" ;
    ///имя БД
    private static String dbName = null;
    //Создаем соединение с бд
    public TestDerby(Persons persons) {
        this.dbName="Test";
        // если искомой бд не существует, создаем ее
        if(!dbExists()) {
            try {
                //Загружаем драйвер
                Class.forName(driver) ;
                // Подключение к БД или её создание
                con = DriverManager.getConnection(url + dbName + ";create=true");

                this.executeUpdate("CREATE TABLE Person(id int, name varchar(128)," +
                        " secondname varchar(128), surname varchar(128), position varchar(128))");
                this.executeUpdatePersons(persons);

            } catch (ClassNotFoundException e) {
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            } catch (SQLException e) {
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    private Boolean dbExists() {
        Boolean exists = false ;
        try {
            Class.forName(driver) ;
            con = DriverManager.getConnection(url + dbName);
            exists = true ;
        } catch(Exception e) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            // Если база не создана то ничего не делаем
        }
        return(exists) ;
    }

    public void executeUpdate(String sql) throws SQLException {
        PreparedStatement prstmt = con.prepareStatement(sql);
        int count = prstmt.executeUpdate();
        prstmt.close();
    }

    public void executeUpdatePersons(Persons persons) throws SQLException {
        PreparedStatement prstmt = null;
        ResultSet result;
        for (Person person: persons.person) {
            if (!hasPerson(person.getId())){
                //Записываем
                prstmt = con.prepareStatement(
                        "INSERT INTO Person " +
                                "(id, name, secondname, surname, position) " +
                                "VALUES (?, ?, ?, ?, ?)");
                prstmt.setInt(1, person.getId());
                prstmt.setString(2, person.getName());
                prstmt.setString(3, person.getSecondName());
                prstmt.setString(4, person.getSurname());
                prstmt.setString(5, person.getPosition());
                prstmt.executeUpdate();
                //Записали
            }

            //если в результате запроса возвратилось id  то переходим к след элементу
       /*     if (result.getObject("id")==null) {
                //иначе добавляем элемент

                prstmt = con.prepareStatement(
                        "INSERT INTO Person " +
                                "(id, name, secondname, surname, position) " +
                                "VALUES (?, ?, ?, ?, ?)");
                prstmt.setInt(1, person.getId());
                prstmt.setString(2, person.getName());
                prstmt.setString(3, person.getSecondName());
                prstmt.setString(4, person.getSurname());
                prstmt.setString(5, person.getPosition());
                prstmt.execute();
            }*/
        }
    }

    public boolean hasPerson(int id){
        boolean res = false;
        PreparedStatement prstmt = null;
        try {
            prstmt = con.prepareStatement("SELECT * FROM Person WHERE id = ?");
            prstmt.setInt(1, id);
            ResultSet result = prstmt.executeQuery();
            while(result.next()){
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        // Statement stmt = con.createStatement() ;
        PreparedStatement prstmt = con.prepareStatement(sql);
        ResultSet result = prstmt.executeQuery();
        return result;
    }

    public Persons getPersonsFromDB(){

        ResultSet rs = null;
        Persons persons = new Persons();
        Person person = new Person();
        try {
            rs = this.executeQuery("SELECT * FROM Person");


            while (rs.next()) {
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSecondName(rs.getString("secondname"));
                person.setSurname(rs.getString("surname"));
                person.setSurname(rs.getString("position"));
                persons.person.add(person);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}

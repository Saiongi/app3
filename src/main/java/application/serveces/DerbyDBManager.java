
package application.serveces;

import application.model.staff.Person;
import application.model.staff.Persons;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Светлана on 18.07.2016.
 */
public class DerbyDBManager {
    private static Connection con = null ;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private static final String url = "jdbc:derby:" ;
    ///имя БД
    private static String dbName = null;
    //Создаем соединение с бд
    public DerbyDBManager(String dbName) {
        this.dbName=dbName;
        // если искомой бд не существует, создаем ее
        if(!dbExists()) {
            try {
                //Загружаем драйвер
                Class.forName(driver) ;
                // Подключение к БД или её создание
                con = DriverManager.getConnection(url + dbName + ";create=true");
            } catch (ClassNotFoundException e) {
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            } catch (SQLException e) {
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    //проверяем, существует ли бд
    private Boolean dbExists()
    {
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

    // запрос на обновление базы данных  (INSERT, UPDATE, CREATE TABLE и т.п.)
    public void executeUpdate(String sql) throws SQLException {
        Statement stmt = con.createStatement() ;
        int count = stmt.executeUpdate(sql) ;
        stmt.close() ;
    }

    PreparedStatement stmt = null;
    public void executeUpdatePersons(Persons persons) throws SQLException {
        PreparedStatement stmt = null;
        for (Person person: persons.person) {

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
    }

    // запрос на выборку данных из базы
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = con.createStatement() ;
        ResultSet result = stmt.executeQuery(sql) ;
        return result;
    }
}

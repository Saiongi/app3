package application.serveces;

/**
 * Created by Светлана on 18.07.2016.
 */
import application.model.staff.Person;

import java.sql.*;
public class TestDB {

    static Connection conn = null;
/*
    public static void createDBConnection() {
        DerbyDBManager db = new DerbyDBManager("ApplicationDB");

        try {
            try {
                // запись данных в таблицу
                fillDB(db);
            } catch (SQLException e) {
                // если БД не существовала, то создаем таблицу
                // и после этого заполняем её значениями
                db.executeUpdate("CREATE TABLE person(id int, name varchar(128)," +
                        " secondname varchar(128), surname varchar(128), position varchar(128))");
                fillDB(db);
            }

            ResultSet rs = db.executeQuery("SELECT * FROM demotab");
            while (rs.next()) {
                System.out.println(
                        "i = " + rs.getString(1) + ", i^2 = " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillDB(DerbyDBManager db) throws SQLException {
        //записываем данные в бд
        for (Person p: persons.person){
            insertPerson(p);
        }
    }
    */
}

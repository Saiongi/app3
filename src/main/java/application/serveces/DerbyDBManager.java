
package application.serveces;

import application.model.staff.*;

import java.sql.*;
import java.util.ArrayList;
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



    // запрос на выборку данных из базы
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = con.createStatement() ;
        ResultSet result = stmt.executeQuery(sql) ;
        return result;
    }
    PreparedStatement stmt = null;
    public void executeUpdatePersons(Persons persons) throws SQLException {
        PreparedStatement prstmt = null;
        ResultSet result;
        for (Person person: persons.person) {
            //stmt = con.createStatement() ;
           // result = stmt.executeQuery("SELECT * FROM person WHERE id = findId ") ;
            prstmt = con.prepareStatement("SELECT * FROM Person WHERE id =?");
            prstmt.setInt(1, person.getId());
            result = prstmt.executeQuery();
           // result.next();
           // Boolean str=result.getBoolean("id");
           // prstmt.close();
            //если в результате запроса возвратилось id  то переходим к след элементу
            if (result.wasNull()) {
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
            }
        }
    }

    public void executeUpdateDepartments(Departments departments) throws SQLException {
        PreparedStatement prstmt = null;
        PreparedStatement stmtTel = null;
        ResultSet result;


        for (Department department: departments.department) {
            prstmt = con.prepareStatement("SELECT * FROM Department WHERE id =?");
            prstmt.setInt(1, department.getId());
            result = prstmt.executeQuery();
            //если в результате запроса возвратилось id  то переходим к след элементу
            if (result.wasNull()) {
                prstmt = con.prepareStatement(
                        "INSERT INTO Department " +
                                "(id, name, secondname, surname, position) " +
                                "VALUES (?, ?, ?, ?, ?)");
                prstmt.setInt(1, department.getId());
                prstmt.setString(2, department.getDepartName());
                prstmt.setString(3, department.getShortName());
                prstmt.setString(4, department.getBoss());
                //       stmt.setString(5, department.getTelNumbers());
                prstmt.execute();

                ArrayList<Integer> depTel = department.getTelNumbers();

                //записываем номера телефонов в отдельную таблицу
                for (int i = 0; i < depTel.size(); i++) {
                    stmtTel = con.prepareStatement(
                            "INSERT INTO DepartmentTel " +
                                    "(id, telNumber) " +
                                    "VALUES (?, ?)");
                    stmtTel.setInt(1, department.getId());
                    stmtTel.setInt(2, depTel.get(i));
                }
            }
        }
    }

    public void executeUpdateOrganizations(Organizations organizations) throws SQLException {
        PreparedStatement prstmt = null;
        PreparedStatement stmtTel = null;
        Statement stmt = null ;
        ResultSet result;
        int findId;



        for (Organization organization: organizations.organization) {
            stmt = con.createStatement() ;
            findId = organization.getId();
            result = stmt.executeQuery("SELECT * FROM Organization WHERE id = findId") ;
            //если в результате запроса возвратилось id  то переходим к след элементу
            if (result.getString("id")!= null) {
                break;
                //иначе добавляем элемент
            }else {
                prstmt = con.prepareStatement(
                        "INSERT INTO Departments " +
                                "(id, name, secondname, surname, position) " +
                                "VALUES (?, ?, ?, ?, ?)");
                prstmt.setInt(1, organization.getId());
                prstmt.setString(2, organization.getOrgName());
                prstmt.setString(3, organization.getShortName());
                prstmt.setString(4, organization.getOrgBoss());
                prstmt.execute();

                ArrayList<Integer> orgTel = organization.getOrgTelNumbers();

                //записываем номера телефонов в отдельную таблицу
                for (int i = 0; i < orgTel.size(); i++) {
                    stmtTel = con.prepareStatement(
                            "INSERT INTO OrganizationTel " +
                                    "(id, telNumber) " +
                                    "VALUES (?, ?)");
                    stmtTel.setInt(1, organization.getId());
                    stmtTel.setInt(2, orgTel.get(i));
                    stmtTel.execute();
                }
            }

        }
    }
}

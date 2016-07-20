
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

    // запрос на обновление базы данных  (INSERT, UPDATE, CREATE TABLE и т.п.)
    public void executeUpdate(String sql) throws SQLException {
        PreparedStatement prstmt = con.prepareStatement(sql);
        int count = prstmt.executeUpdate();
        prstmt.close();
    }
    // запрос на выборку данных из базы
    public ResultSet executeQuery(String sql) throws SQLException {
        PreparedStatement prstmt = con.prepareStatement(sql);
        ResultSet result = prstmt.executeQuery() ;
        return result;
    }
    //добавление нескольких person(persons)
    public void executeUpdatePersons(Persons persons) throws SQLException {
        for (Person person: persons.person) {
            executeUpdatePerson(person);
        }
    }
    //добавление person
    public void executeUpdatePerson(Person person) throws SQLException {
        PreparedStatement prstmt = null;

            //если в результате запроса возвратилось id  то переходим к след элементу
            if (!hasElementInTable(person.getId(), "person")){
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
    //добавление нескольких department(departments)
    public void executeUpdateDepartments(Departments departments) throws SQLException {
        for (Department department: departments.department) {
          executeUpdateDepartment(department);  //если в результате запроса возвратилось id  то переходим к след элементу
        }
    }
    //добавление department
    public void executeUpdateDepartment(Department department) throws SQLException {
        PreparedStatement prstmt = null;
        PreparedStatement prstmtTel = null;
        if (!hasElementInTable(department.getId(), "department")){
            prstmt = con.prepareStatement(
                    "INSERT INTO Department " +
                            "(id, departname, shortname, boss) " +
                            "VALUES (?, ?, ?, ?)");
            prstmt.setInt(1, department.getId());
            prstmt.setString(2, department.getDepartName());
            prstmt.setString(3, department.getShortName());
            prstmt.setString(4, department.getBoss());
            prstmt.execute();

            ArrayList<Integer> depTel = department.getTelNumbers();

            //записываем номера телефонов в отдельную таблицу
            for (int i = 0; i < depTel.size(); i++) {
                prstmtTel = con.prepareStatement(
                        "INSERT INTO DepartmentTel " +
                                "(id, telNumber) " +
                                "VALUES (?, ?)");
                prstmtTel.setInt(1, department.getId());
                prstmtTel.setInt(2, depTel.get(i));
                prstmtTel.execute();
            }
        }
    }
    //добавление нескольких organization(organizations)
    public void executeUpdateOrganizations(Organizations organizations) throws SQLException {

        for (Organization organization: organizations.organization) {
            executeUpdateOrganization(organization);
        }
    }
    //добавление organization
    public void executeUpdateOrganization(Organization organization) throws SQLException {
        PreparedStatement prstmt = null;
        PreparedStatement prstmtTel = null;
            if (!hasElementInTable(organization.getId(), "organization")){
                prstmt = con.prepareStatement(
                        "INSERT INTO Departments " +
                                "(id, orgname, shortname, boss) " +
                                "VALUES (?, ?, ?, ?)");
                prstmt.setInt(1, organization.getId());
                prstmt.setString(2, organization.getOrgName());
                prstmt.setString(3, organization.getShortName());
                prstmt.setString(4, organization.getOrgBoss());
                prstmt.execute();

                ArrayList<Integer> orgTel = organization.getOrgTelNumbers();
                //записываем номера телефонов в отдельную таблицу
                for (int i = 0; i < orgTel.size(); i++) {
                    prstmtTel = con.prepareStatement(
                            "INSERT INTO OrganizationTel " +
                                    "(id, telNumber) " +
                                    "VALUES (?, ?)");
                    prstmtTel.setInt(1, organization.getId());
                    prstmtTel.setInt(2, orgTel.get(i));
                    prstmtTel.execute();
                }
            }


    }
    //проверка элемента на наличие(принимает id элемента и название таблицы, в которой проверяем наличие)
    public boolean hasElementInTable (int id, String tableName) {
        boolean res = false;
        PreparedStatement prstmt = null;
        try {
            prstmt = con.prepareStatement("SELECT * FROM "+ tableName +" WHERE id = ?");
            prstmt.setInt(1, id);
            ResultSet result = prstmt.executeQuery();
            while (result.next()) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    //получаем persons(вся таблица)
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
    //получаем person по id
    public Person getPersonFromDB(int id){
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        Person person = new Person();
        try {
            prstmt = con.prepareStatement(
                    "SELECT * FROM Person WHERE id = ?");
            prstmt.setInt(1, id);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSecondName(rs.getString("secondname"));
                person.setSurname(rs.getString("surname"));
                person.setSurname(rs.getString("position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
    //получаем departments(вся таблица)
    public Departments getDepartmentsFromDB(){
        Departments departments = new Departments();
        Department department = new Department();
        ResultSet rs2  = null;
        ResultSet rs = null;
        try {
            rs = this.executeQuery("SELECT * FROM Department");
            while (rs.next()) {
                department.setId(rs.getInt("id"));
                department.setDepartName(rs.getString("departname"));
                department.setShortName(rs.getString("shortname"));
                department.setBoss(rs.getString("boss"));

                rs2 = this.executeQuery("SELECT telNumber FROM DepartmentTel WHERE id="+rs.getInt("id")+"");
                ArrayList<Integer> depTel = new ArrayList<Integer>();
                while (rs2.next()) {
                    depTel.add(rs2.getInt("telNumber"));
                }
                department.setTelNumbers(depTel);
                departments.department.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
    //получаем department по id
    public Department getDepartmentFromDB(int id){
        PreparedStatement prstmt = null;
        Department department = new Department();
        ResultSet rs2  = null;
        ResultSet rs = null;
        try {
            prstmt = con.prepareStatement(
                    "SELECT * FROM Department WHERE id = ?");
            prstmt.setInt(1, id);
            rs = prstmt.executeQuery();

            while (rs.next()) {
                department.setId(rs.getInt("id"));
                department.setDepartName(rs.getString("departname"));
                department.setShortName(rs.getString("shortname"));
                department.setBoss(rs.getString("boss"));

                rs2 = this.executeQuery("SELECT telNumber FROM DepartmentTel WHERE id="+rs.getInt("id")+"");
                ArrayList<Integer> depTel = new ArrayList<Integer>();
                while (rs2.next()) {
                    depTel.add(rs2.getInt("telNumber"));
                }
                department.setTelNumbers(depTel);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }
    //получаем organizations(вся таблица)
    public Organizations getOrganizationsFromDB(){
        Organizations organizations = new Organizations();
        Organization organization = new Organization();
        ResultSet rs2  = null;
        ResultSet rs = null;
        try {
            rs = this.executeQuery("SELECT * FROM Organization");
            while (rs.next()) {
                organization.setId(rs.getInt("id"));
                organization.setOrgName(rs.getString("orgname"));
                organization.setShortName(rs.getString("shortname"));
                organization.setOrgBoss(rs.getString("orgboss"));

                rs2 = this.executeQuery("SELECT telNumber FROM OrganizationTel WHERE id="+rs.getInt("id")+"");
                ArrayList<Integer> orgTel = new ArrayList<Integer>();
                while (rs2.next()) {
                    orgTel.add(rs2.getInt("telNumber"));
                }
                organization.setOrgTelNumbers(orgTel);
                organizations.organization.add(organization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organizations;
    }
    //получаем organization по id
    public Organization getOrganizationFromDB(int id){
        PreparedStatement prstmt = null;
        Organization organization = new Organization();
        ResultSet rs2  = null;
        ResultSet rs = null;
        try {
            prstmt = con.prepareStatement(
                    "SELECT * FROM Department WHERE id = ?");
            prstmt.setInt(1, id);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                organization.setId(rs.getInt("id"));
                organization.setOrgName(rs.getString("orgname"));
                organization.setShortName(rs.getString("shortname"));
                organization.setOrgBoss(rs.getString("orgboss"));

                rs2 = this.executeQuery("SELECT telNumber FROM OrganizationTel WHERE id="+rs.getInt("id")+"");
                ArrayList<Integer> orgTel = new ArrayList<Integer>();
                while (rs2.next()) {
                    orgTel.add(rs2.getInt("telNumber"));
                }
                organization.setOrgTelNumbers(orgTel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organization;
    }

/*
    public boolean hasDepartment(int id) {
        boolean res = false;
        PreparedStatement prstmt = null;
        try {
            prstmt = con.prepareStatement("SELECT * FROM Department WHERE id = ?");
            prstmt.setInt(1, id);
            ResultSet result = prstmt.executeQuery();
            while (result.next()) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public boolean hasOrganization(int id) {
        boolean res = false;
        PreparedStatement prstmt = null;
        try {
            prstmt = con.prepareStatement("SELECT * FROM Organization WHERE id = ?");
            prstmt.setInt(1, id);
            ResultSet result = prstmt.executeQuery();
            while (result.next()) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
*/
}

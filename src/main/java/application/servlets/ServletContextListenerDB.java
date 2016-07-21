package application.servlets;

import application.serveces.DerbyDBManager;
import application.serveces.FileService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Светлана on 20.07.2016.
 */
public class ServletContextListenerDB implements ServletContextListener {
    ServletContext context;
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        FileService fileService = null;
        try {
            fileService = new FileService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        DerbyDBManager derbyDBManager=new DerbyDBManager("ApplicationDB");

        context = contextEvent.getServletContext();

        fileService.readFiles();
        try {
            fileService = new FileService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            try {
                // запись данных в таблицу
                derbyDBManager.executeUpdatePersons(fileService.getPersons());
            } catch (SQLException e) {
                // если БД не существовала, то создаем таблицу и записываем данные
                derbyDBManager.createPersonTable();
                derbyDBManager.executeUpdatePersons(fileService.getPersons());
            }

            try {
                // запись данных в таблицу
                derbyDBManager.executeUpdateOrganizations(fileService.getOrganizations());
            } catch (SQLException e) {
                // если БД не существовала или нет таблицы, то создаем таблицу
                // и после этого заполняем её значениями
               derbyDBManager.createOrganizationTables();
                //запись данных из созданных таблиц
                derbyDBManager.executeUpdateOrganizations(fileService.getOrganizations());
            }

            try {
                // запись данных в таблицу
                derbyDBManager.executeUpdateDepartments(fileService.getDepartments());
            } catch (SQLException e) {
                // если БД не существовала, то создаем таблицу
                // и после этого заполняем её значениями
                derbyDBManager.createDepartmentTables();
                //запись данных из созданных таблиц
                derbyDBManager.executeUpdateDepartments(fileService.getDepartments());
            }

        } catch (SQLException e) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();

    }
}

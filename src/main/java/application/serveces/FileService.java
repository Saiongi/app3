package application.serveces;

import application.TestDoc;
import application.model.staff.Departments;
import application.model.staff.Organizations;
import application.model.staff.Persons;
import application.model.staff.Staff;
import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Света on 05.07.2016.
 */

public class FileService {
    // переменная для хранения Persons
    Persons persons;
    // переменная для хранения Departments
    Departments departments;
    // переменная для хранения Organizations
    Organizations organizations;
    //переменная для хранения объектов
    Object obj;
    // переменная для хранения файлов
    File file;
    DocService docService;


FileService(){
    //1. сохраняем данные из файлов(пока только персонал
    //Создаем hashmap для хранения классов и названий файлов
    Map<String,Class> staffMap = new HashMap<String,Class>();
    staffMap.put("persons.xml", Persons.class);
    staffMap.put("departments.xml", Departments.class);
    staffMap.put("organizations.xml", Organizations.class);

   // FileService fileService = new FileService();
    persons =  readFiles(staffMap);
    //создаем экземпляр DocService
   // docService = new DocService();
    //сохраняем persons в docfieldStorage
   // docService.savePersons(persons);

}

    public Persons readFiles(Map<String,Class> staffMap){
        //считываем сохраняем данные из файлов

        //читаем файлы
        for(Map.Entry entry:staffMap.entrySet()){
            try {
                //записываем выбранный файл
                file = new File(System.getProperty("user.dir")
                        + File.separator + entry.getKey());
                // создаем образец контекста и передаем Class объекта с которым будем работать
                JAXBContext context = JAXBContext.newInstance((Class) entry.getValue());
                Unmarshaller unmarshaller = context.createUnmarshaller();
                // сохраняем данные в объект
                obj = (Object) unmarshaller.unmarshal(file);

            } catch (JAXBException ex) {
                Logger.getLogger(TestDoc.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            //Сохраняем объект в зависимости от его класса
            if (obj instanceof Persons){
                persons = (Persons)obj;
            }else if (obj instanceof Departments) {
                departments = (Departments) obj;
            } else if (obj instanceof Organizations){
                organizations = (Organizations) obj;
            }
        }

        return persons;
     }

}

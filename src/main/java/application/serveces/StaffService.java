package application.serveces;

import application.DocFieldsStorage;
import application.TestDoc;
import application.model.document.Document;
import application.model.document.Incoming;
import application.model.document.Outgoing;
import application.model.document.Task;
import application.model.staff.Departments;
import application.model.staff.Organizations;
import application.model.staff.Person;
import application.model.staff.Persons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Света on 01.07.2016.
 */
@Path("/employees")
public class StaffService {
    Gson gson;
    DocService docService;

   StaffService(){
       int p;//переменная для хранения случайного значения

       //создаем экземпляр DocService
       docService = new DocService();
       //сохраняем сотрудников в DocFieldStorage
       docService.readFiles();
       //доп масссив для случайной генерации одного из документов
       Class[] classDoc = new Class[3];
       classDoc[0] = Task.class;
       classDoc[1] = Outgoing.class;
       classDoc[2] = Incoming.class;
       //создаем TreeSet для хранения документов
       TreeSet<Document> allDoc = new TreeSet<Document>();
       //создаем документы
       for (int i = 0; i < 30; i++) {
           p = (int) (Math.random() * 3);
           Document doc = docService.createDoc(classDoc[p]);
           if (doc != null) {
               allDoc.add(doc);
           }
       }
       //выводим документы в консоль

       Map<Person, TreeSet<Document>> docsByPersonMap = new TreeMap<Person, TreeSet<Document>>();
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
       //сортируем документы по авторам
       for (Document d : allDoc) {
           if (!docsByPersonMap.containsKey(d.getAuthor())) {
               docsByPersonMap.put(d.getAuthor(), new TreeSet<Document>());
           }
           docsByPersonMap.get(d.getAuthor()).add(d);
       }

       //генерируем отчеты по каждому автору в json файлы
       GsonBuilder builder = new GsonBuilder();
       Gson gson = new Gson();
       String authorStr;
       String stringInJSON;
       String dirName = "e:\\java\\workspace\\app3\\";
       String extension = ".json";
       // сортировка документов по автору
       for (Person author : docsByPersonMap.keySet()) {
           //очищаем строку
           stringInJSON="";
           //сохраняем имя автора в переменную
           authorStr = author.getSurname() + " " + author.getName() + " " + author.getSecondName();
           //добавляем документы в формате json в строку
           for (Document d : docsByPersonMap.get(author)) {
               stringInJSON = stringInJSON + gson.toJson(d);
           }

           try (FileWriter fileWriter = new FileWriter(dirName + authorStr + extension)) {
               fileWriter.write(stringInJSON);
           } catch (IOException ex) {
               Logger.getLogger(TestDoc.class.getName())
                       .log(Level.SEVERE, null, ex);
           }
       }

       //очищаем строку
       stringInJSON="";
       for (Person person : docService.getDocFieldsStorage().getPersonDocStorage().values()) {
           //добавляем сотрудников в формате json в строку
           stringInJSON = stringInJSON + gson.toJson(person);
       }
       try (FileWriter fileWriter = new FileWriter(dirName+"Persons"+ extension)) {
           fileWriter.write(stringInJSON);
       } catch (IOException ex) {
           Logger.getLogger(TestDoc.class.getName())
                   .log(Level.SEVERE, null, ex);
       }
   }

    @Produces({"application/xml","application/json"})
    @Path("/empl")
    @GET
    public Persons getJSONPersons(){

        StaffService a = new StaffService();

        return  a.docService.persons;

    }
}


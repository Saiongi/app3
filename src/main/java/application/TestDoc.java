package application;

/**
 * Created by Света on 01.07.2016.
 */
import application.model.document.Document;
import application.model.document.Incoming;
import application.model.document.Outgoing;
import application.model.document.Task;
import application.model.staff.*;
import application.serveces.DocService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.script.ScriptEngine.FILENAME;


public class TestDoc {

    public static void main(String[] args) throws IOException {
        Persons persons = null;
        Departments departments=null;
        Organizations organizations=null;
        Object obj=null;
        File file;
        int p;//переменная для хранения случайного значения

        //считываем сохраняем данные из файлов
        Map<String,Class>  staffMap = new HashMap<String,Class>();
        staffMap.put("persons.xml", Persons.class);
        staffMap.put("departments.xml", Departments.class);
        staffMap.put("organizations.xml", Organizations.class);

        for(Map.Entry entry:staffMap.entrySet()){
            try {
                file = new File(System.getProperty("user.dir")
                        + File.separator + entry.getKey());
                JAXBContext context = JAXBContext.newInstance((Class) entry.getValue());
                Unmarshaller unmarshaller = context.createUnmarshaller();
                obj = (Object) unmarshaller.unmarshal(file);

            } catch (JAXBException ex) {
                Logger.getLogger(TestDoc.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            if (obj instanceof Persons){
                persons = (Persons)obj;
            }else if (obj instanceof Departments) {
                departments = (Departments) obj;
            } else if (obj instanceof Organizations){
                organizations = (Organizations) obj;
            }
        }

        //создаем экземпляр DocService
        DocService docService = new DocService();
        //сохраняем сотрудников в DocFieldStorage
        docService.savePersons(persons);
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
        for (Document d : allDoc) {
            //    System.out.println(d.toString());
        }
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
        String string;
        String name1 = "e:\\java\\app\\";
        String name2 = ".json";
        // сортировка документов по автору
        for (Person author : docsByPersonMap.keySet()) {
            string="";
            //сохраняем имя автора в переменную
            authorStr = author.getSurname() + " " + author.getName() + " " + author.getSecondName();
            //добавляем
            for (Document d : docsByPersonMap.get(author)) {
                string = string + gson.toJson(d);
            }

            try (FileWriter fileWriter = new FileWriter(name1 + authorStr + name2)) {
                fileWriter.write(string);
            } catch (IOException ex) {
                Logger.getLogger(TestDoc.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}


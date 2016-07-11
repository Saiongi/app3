package application.serveces;

import application.model.document.Document;
import application.model.document.Incoming;
import application.model.document.Outgoing;
import application.model.document.Task;
import application.model.staff.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Света on 05.07.2016.
 */

public class FileService {
    // переменная для хранения Persons
    Collection<Person> personsCollection;
    // переменная для хранения Departments
    Departments departments;
    // переменная для хранения Organizations
    Organizations organizations;
    //переменная для хранения объектов
    Object obj;
    // переменная для хранения файлов
    File file;
    DocService docService;
    Persons persons;
    //для хранения документов
    TreeSet<Document> allDoc;
    //для хранения документов с искомым id
    TreeSet<Document> idFindDoc;
FileService() throws URISyntaxException {

    personsCollection = readFiles();
    //создаем экземпляр DocService
    docService = new DocService();
    //сохраняем person в docFieldStorage
    docService.savePersons(persons);
    //создаем документы
     allDoc = createDocuments();
}
    //считываем person.xml
    public Collection<Person> readFiles() throws URISyntaxException {
                try {
                    ClassLoader classLoader = getClass().getClassLoader();
                    URL url = classLoader.getResource("person.xml");
                    file = new File(url.getPath());
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
            }
    //ищем документ по id
    public TreeSet<Document> findDocId(TreeSet<Document> allDoc, int findId){
        Map<Integer, TreeSet<Document>> docsByPersonMap = new TreeMap<Integer, TreeSet<Document>>();

        //сортируем документ по Id автора
        for (Document d: allDoc) {
            if (! docsByPersonMap.containsKey(d.getAuthor().getId())){
                docsByPersonMap.put(d.getAuthor().getId(), new TreeSet<Document>());
            }
            docsByPersonMap.get(d.getAuthor().getId()).add(d);
        }

        // Ищем документы по  искомому id и записываем их в treeset idFindDoc
        for (Integer id : docsByPersonMap.keySet()){
              if (id==findId) {
                  idFindDoc = docsByPersonMap.get(id);
              }
        }
        //если документов в treset не записалось,
        // т.е. не найдены документы с автром с искомым id тогда возвращаем null
        if (idFindDoc.isEmpty()) {
            return null;
        //если документы найдены , возвращаем коллекцию
       }else return idFindDoc;
    }

    public TreeSet<Document>  createDocuments(){
        //доп масссив для случайной генерации одного из документов
        Class[] classDoc = new Class[3];
        classDoc[0] = Task.class;
        classDoc[1] = Outgoing.class;
        classDoc[2] = Incoming.class;
        //создаем TreeSet для хранения документов
        TreeSet<Document> allDoc = new TreeSet<Document>();
        int p;//переменная для хранения случайного значения
        //создаем документы
        for (int i = 0; i < 30; i++) {
            p = (int) (Math.random() * 3);
            Document doc = docService.createDoc(classDoc[p]);
            if (doc != null) {
                allDoc.add(doc);
            }
        }
        //возвращаем TreeSet с документами
        return allDoc;
    }

}

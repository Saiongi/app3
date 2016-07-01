package application.serveces;

/**
 * Created by Света on 01.07.2016.
 */

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.DocFieldsStorage;
import application.TestDoc;
import application.model.document.Document;
import application.model.document.DocumentExistsException;
import application.model.staff.Departments;
import application.model.staff.Organizations;
import application.model.staff.Person;
import application.model.staff.Persons;
import application.serveces.factories.DocumentFactory;
import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by Света on 22.06.2016.
 */
public class DocService {

    DocumentFactory documentFactory;

    HashSet<String> regNumbers;
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
    DocFieldsStorage docFieldsStorage;
    Gson gson;
    String string;

    public DocService(){
        regNumbers = new HashSet<String>();
        documentFactory = new DocumentFactory();
        docFieldsStorage = new DocFieldsStorage();
        persons = null;
        departments=null;
        organizations=null;
        obj=null;

    }

    public DocFieldsStorage getDocFieldsStorage() {
        return docFieldsStorage;
    }

    public void setDocFieldsStorage(DocFieldsStorage docFieldsStorage) {
        this.docFieldsStorage = docFieldsStorage;
    }

    public void regDoc(Document doc) throws DocumentExistsException {

        String regNom = docFieldsStorage.getRegisterNumOfDoc();
        if (regNumbers.contains(regNom)){
            throw new DocumentExistsException("Exception! Document with this number already exists!");
        } else{
            doc.setRegisterNumOfDoc(regNom);//добавляем документу рег номер
            regNumbers.add(regNom);// добавляем рег номер в коллекцию уже существующих рег номеров
            doc.setDateOfRegistration(docFieldsStorage.getDate());//задаем дату
        }
    }

    public Document createDoc(Class aClass) {
        Document doc = documentFactory.createDocument(aClass);
        docFieldsStorage.saveDocField(doc);
        try {
            regDoc(doc);
            return doc;
        } catch (DocumentExistsException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void savePersons(Persons persons){
        int i=0;
        for (Person person: persons.persons) {
            docFieldsStorage.addPersonToDocStorage(i, person);
            i++;
        }

    }

    public void readFiles(){
        //считываем сохраняем данные из файлов
        //Создаем hashmap для хранения классов и названий файлов
        Map<String,Class> staffMap = new HashMap<String,Class>();
        staffMap.put("persons.xml", Persons.class);
        staffMap.put("departments.xml", Departments.class);
        staffMap.put("organizations.xml", Organizations.class);
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

        //сохраняем сотрудников в DocFieldStorage
        savePersons(persons);
    }










}

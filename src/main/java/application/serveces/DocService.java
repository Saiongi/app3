package application.serveces;

/**
 * Created by Света on 01.07.2016.
 */

import java.util.HashSet;

import application.DocFieldsStorage;
import application.model.document.Document;
import application.model.document.DocumentExistsException;
import application.model.staff.Person;
import application.model.staff.Persons;
import application.serveces.factories.DocumentFactory;

import java.util.HashSet;

/**
 * Created by Света on 22.06.2016.
 */
public class DocService {

    DocumentFactory documentFactory;
    DocFieldsStorage docFieldsStorage;
    HashSet<String> regNumbers;

    public DocService(){
        regNumbers = new HashSet<String>();
        documentFactory = new DocumentFactory();
        docFieldsStorage = new DocFieldsStorage();
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

    public void savePersons(Persons persons){
        int i=0;
        for (Person person: persons.persons) {
            docFieldsStorage.getPerson(i, person);
            i++;
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
}

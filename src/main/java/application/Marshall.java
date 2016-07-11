package application;
import application.model.staff.Person;
import application.model.staff.Persons;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Света on 08.07.2016.
 */
public class Marshall {

    public static void main(String[] args) {
        Person pers = new Person();
        pers.setId(1);
        pers.setSurname("Захарова");
        pers.setName("Светлана");
        pers.setSecondName("Сергеевна");
        pers.setPosition("практикант");
        Person pers2 = new Person();
        pers2.setId(1);
        pers2.setSurname("Захарова");
        pers2.setName("Светлана");
        pers2.setSecondName("Сергеевна");
        pers2.setPosition("практикант");

        Persons person = new Persons();
        person.person.add(pers);
        person.person.add(pers2);


        try {
            File file = new File(System.getProperty("user.dir")
                    + File.separator + "person.xml");
            JAXBContext context = JAXBContext.newInstance(Persons.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(person, file);
            marshaller.marshal(person, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(Marshall.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}

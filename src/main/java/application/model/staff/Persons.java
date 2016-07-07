package application.model.staff;

/**
 * Created by Света on 01.07.2016.
 */
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "Persons")
@XmlRootElement
public class Persons {
    @XmlElementWrapper(name="person", nillable = true)
    public List<Person> persons = new ArrayList<Person>();

    public List<Person> getPersons() {
        return persons;
    }
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}

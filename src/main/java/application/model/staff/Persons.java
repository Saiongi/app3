package application.model.staff;

/**
 * Created by Света on 01.07.2016.
 */
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "Persons")
public class Persons {
    @XmlElementWrapper(name = "personList", nillable = true)
    public List<Person> person = new ArrayList<Person>();

}
package application.model.staff;

/**
 * Created by Света on 01.07.2016.
 */
/**
 * Created by Света on 28.06.2016.
 */
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
@XmlType(name = "Departments")
@XmlRootElement
public class Departments {
    @XmlElementWrapper(name="departmentList", nillable = true)
    public List<Department> department = new ArrayList<Department>();
}

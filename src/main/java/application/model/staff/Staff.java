package application.model.staff;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Света on 01.07.2016.
 */
@XmlType(propOrder = { "id" }, name = "staff")
@XmlRootElement(name = "staff")
public abstract class Staff {


    private int id;

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }
}

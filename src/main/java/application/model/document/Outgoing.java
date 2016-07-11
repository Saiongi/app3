package application.model.document;

/**
 * Created by Света on 01.07.2016.
 */
import application.model.staff.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Света on 20.06.2016.
 */
@XmlType(name = "Outgoing")
public class Outgoing extends Document {
    private Person destination;  //адресат
    private String deliveryMethod;  //способ доставки


    @Override
    public String getTable() {
        return null;
    }

    public Person getDestination(){
        return this.destination;
    }
    @XmlElement
    public void setDestination(Person destination) {
        this.destination = destination;
    }

    public String getDeliveryMethod(){
        return this.deliveryMethod;
    }
    @XmlElement
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
    @Override
    public String toString() {

        String str ="\n"+ "Идентификатор документа: "+this.getId()+"\nНазвание документа:"+this.getNameDoc()+
                "\nТекст документа: "+ this.getText()+"\nРегистрационный номер документа: "+this.getRegisterNumOfDoc()+
                "\nДата регистрации документа: "+ this.getDateOfRegistration()+"\nАвтор: "+this.getAuthor().getSurname() +
                " "+ this.getAuthor().getName()+" "+this.getAuthor().getSecondName()+
                "\nАдресат: "+destination.getSurname() + " " + destination.getName() + " " +
                destination.getSecondName()+ "\nСпособ доставки: "+deliveryMethod;

        return str;
    }

}

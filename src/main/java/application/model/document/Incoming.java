package application.model.document;

/**
 * Created by Света on 01.07.2016.
 */
import application.model.staff.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
@XmlType(name = "Incoming")
public class Incoming extends Document {
    private Person sender;  //отправитель;
    private Person destination; //адресат;
    private int incomeNumber; //исходящий номер;
    private Date incomeDateOfRegistration; //исходящая дата регистрации.


    @Override
    public String getTable() {
        return null;
    }

    public Person getSender(){
        return this.sender;
    }
    @XmlElement
    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getDestination(){
        return this.destination;
    }
    @XmlElement
    public void setDestination(Person destination) {
        this.destination = destination;
    }

    public int getIncomeNumber(){
        return this.incomeNumber;
    }
    @XmlElement
    public void setIncomeNumber(int incomeNumber) {
        this.incomeNumber = incomeNumber;
    }

    public Date getIncomeDateOfRegistration(){
        return this.incomeDateOfRegistration;
    }
    @XmlElement
    public void setIncomeDateOfRegistration(Date incomeDateOfRegistration) {
        this.incomeDateOfRegistration = incomeDateOfRegistration;
    }

    @Override
    public String toString() {
        String str = "\n"+"идентификатор документа: "+this.getId()+"\nНазвание документа: "+this.getNameDoc()+
                "\nТекст документа: "+ this.getText()+"\nРегистрационный номер документа: "+this.getRegisterNumOfDoc()+
                "\nДата регистрации документа: "+ this.getDateOfRegistration()+"\nАвтор: "+this.getAuthor().getSurname()+" "+
                this.getAuthor().getName()+" "+this.getAuthor().getSecondName()+
                "\nОтправитель: "+sender.getSurname()+" "+ sender.getName()+" "+sender.getSecondName()+
                "\nАдресат: "+destination.getSurname()+destination.getName() +
                destination.getSecondName()+ "\nИсходящий номер: "+incomeNumber+
                "\n" + "Исходящая дата регистрации: "+incomeDateOfRegistration;
        return str;
    }



}


package application.model.document;


import application.model.Storable;
import application.model.staff.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Класс Документ реализует интерфейс Comparable
 * для возможности сортировки по полям Автор, Дата регистрации
 * и  Регистрационный номер. Реализует интерфейс Storable, который
 * в дальнейшем будет использован для сохранения документов.
 */
@XmlRootElement(name = "document")
public abstract class Document implements Comparable, Storable {
    //идентификатор документа;
    private int id;
    // название документа;
    private String nameDoc;
    // текст документа;
    private String text;
    //регистрационный номер документа;
    private String registerNumOfDoc;
    //дата регистрации документа;
    private Date dateOfRegistration;
    //автор документа.
    private Person author;

    //
    @Override
    public String getTable() {
        return null;
    }

    public int getId(){
        return this.id;
    }
    @XmlElement
    public void setId(int id){
        this.id=id;
    }

    public String getNameDoc(){
        return this.nameDoc;
    }
    @XmlElement
    public void setNameDoc(String nameDoc){
        this.nameDoc = nameDoc;
    }
    public String getText(){
        return this.text;
    }
    @XmlElement
    public void setText(String text){
        this.text=text;
    }

    public String getRegisterNumOfDoc(){
        return this.registerNumOfDoc;
    }
    @XmlElement
    public void setRegisterNumOfDoc(String registerNumOfDoc ){
        this.registerNumOfDoc = registerNumOfDoc;
    }

    public Date getDateOfRegistration(){
        return this.dateOfRegistration;
    }
    @XmlElement
    public void setDateOfRegistration(Date dateOfRegistration){
        this.dateOfRegistration = dateOfRegistration;
    }

    public Person getAuthor(){
        return this.author;
    }
    @XmlElement
    public void setAuthor(Person author) {
        this.author = author;
    }

    @Override
    public int compareTo(Object obj) {
        Document entry = (Document) obj;
        int result = author.getSurname().compareTo(entry.author.getSurname());
        if (result!=0){
            return result;
        }
        result = author.getName().compareTo(entry.author.getName());
        if (result!=0){
            return result;
        }
        result = author.getSecondName().compareTo(entry.author.getSecondName());
        if (result!=0){
            return result;
        }

        result = dateOfRegistration.compareTo(entry.dateOfRegistration);
        if(result != 0) {
            return result;
        }

        result = registerNumOfDoc.compareTo(entry.registerNumOfDoc);
        if(result != 0) {
            return result;
        }

        return 0;
    }

}

package application.model.document;

/**
 * Created by Света on 01.07.2016.
 */
public class DocumentExistsException extends Exception {


    public DocumentExistsException(String msgText){
        super(msgText);
    }

}

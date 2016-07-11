package application.serveces;


import application.model.document.Document;
import application.model.staff.Person;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Created by Света on 01.07.2016.
 */
@Path("/employees")
public class StaffService {
    TreeSet<Document> document ;
    FileService fileService;
    Collection<Person> personCollection;
    //создаем экземпляр personService
    public StaffService() throws URISyntaxException {
    fileService = new FileService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/get")
    public Collection<Person> getJSONPersons(){
        personCollection = fileService.personsCollection;
        return  personCollection;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML+";charset=UTF-8")
    @Path("/get/{id}")
    public TreeSet<Document> getXMLDocuments(@PathParam("id") int id){

        document = fileService.findDocId(fileService.allDoc, id);
            return document;
    }

}


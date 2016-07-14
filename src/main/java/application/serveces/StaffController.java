package application.serveces;


import application.model.document.Document;
import application.model.document.NotFoundException;
import application.model.staff.Person;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by Света on 01.07.2016.
 */
@Path("/employees")
public class StaffController {
    TreeSet<Document> document ;
    FileService fileService;
    Collection<Person> personCollection;
    //создаем экземпляр personService
    public StaffController() throws URISyntaxException {
    fileService = new FileService();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")

    public Collection<Person> getJSONPersons() throws NotFoundException{
        try {
            fileService.insertPersonFromPersonsCollection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        personCollection = fileService.personsCollection;
      if (personCollection.isEmpty())throw new NotFoundException("Employees is not found!");
        return  personCollection;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML+";charset=UTF-8")
    @Path("/{id}")
    public TreeSet<Document> getXMLDocuments(@PathParam("id") int id) throws NotFoundException{

        document = fileService.findDocId(fileService.allDoc, id);
        if (document.isEmpty()) throw new NotFoundException("Documents with this author id does not exist!");
            return document;
    }

}


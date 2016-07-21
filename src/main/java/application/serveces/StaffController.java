package application.serveces;


import application.model.document.Document;
import application.model.staff.Person;
import application.model.staff.Persons;

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
    DerbyDBManager derbyDBManager;
    Collection<Person> personCollection;
    public StaffController() {
        try {
            fileService = new FileService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        derbyDBManager = new DerbyDBManager("ApplicationDB");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/get")
    public Persons getJSONPersons() throws SQLException {
      // fileService.createDBConnection();
    //   Persons persons = fileService.db.getPersonsFromDB();
        Persons persons = derbyDBManager.getPersonsFromDB() ;
       return persons;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML+";charset=UTF-8")
    @Path("/get/{id}")
    public TreeSet<Document> getXMLDocuments(@PathParam("id") int id){
        document = fileService.findDocId(fileService.allDoc, id);
            return document;
    }



}


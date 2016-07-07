package application.serveces;



import application.model.staff.Person;
import com.google.gson.Gson;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.*;


/**
 * Created by Света on 01.07.2016.
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class StaffService {
    FileService fileService;
    Gson gson;
    String stringInJSON;
    Collection<Person> personCollection;
    public StaffService(){
    fileService = new FileService();
    stringInJSON=" ";
        personCollection = fileService.persons.getPersons();;
    }
    @GET
    @Path("/get")
    public Collection<Person> getJSONPersons(){
        return  personCollection;

    }



}


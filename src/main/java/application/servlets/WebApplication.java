package application.servlets;


import application.serveces.StaffController;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Света on 04.07.2016.
 */
@ApplicationPath("/ecm")
public class WebApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(StaffController.class);
        return classes;
    }


}

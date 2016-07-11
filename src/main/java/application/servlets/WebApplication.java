package application.servlets;


import application.serveces.StaffService;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Света on 04.07.2016.
 */
@ApplicationPath("/ecm")
public class WebApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(StaffService.class);
        return classes;
    }


}

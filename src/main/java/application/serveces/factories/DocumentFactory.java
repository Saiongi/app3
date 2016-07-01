package application.serveces.factories;

import application.model.document.Document;
import application.model.document.Incoming;
import application.model.document.Outgoing;
import application.model.document.Task;

import java.lang.reflect.InvocationTargetException;

public class DocumentFactory implements BaseDocumentFactory {

    @Override
    public Document createDocument(String type) {

        if ("Task".equals(type)) {
            return createTask();
        } else if ("Incoming".equals(type)) {
            return createIncoming();
        } else if ("Outgoing".equals(type)) {
            return createOutgoing();
        }
        return null;

    }
    public Task createTask(){
        Task task=new Task();
        return task;
    }
    public Incoming createIncoming() {
        Incoming incoming=new Incoming();
        return incoming;
    }
    public Outgoing createOutgoing() {
        Outgoing outgoing=new Outgoing();
        return outgoing;
    }

    public Document createDocument(Class aClass) {
        try {
            return (Document)aClass.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}


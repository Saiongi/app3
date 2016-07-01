package application.serveces.factories;

import application.model.document.Document;

/**
 * Created by Света on 22.06.2016.
 */
public interface BaseDocumentFactory {
    Document createDocument(String type);
}

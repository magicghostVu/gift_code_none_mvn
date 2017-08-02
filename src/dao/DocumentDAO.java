package dao;

import org.bson.Document;

import java.util.List;

/**
 * Created by Fresher on 28/07/2017.
 */
public interface DocumentDAO {
    Document getDocumentById(String id);

    List<Document> getDocumentsByCollectionName(String collectionName);

}

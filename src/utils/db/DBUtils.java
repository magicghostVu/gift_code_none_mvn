package utils.db;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import org.bson.Document;
import work.with.db.DatabaseConn;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Fresher on 28/07/2017.
 */
public class DBUtils {

    private DatabaseConn databaseConn;

    public GiftCodeType dectectType(String idCode) {
        MongoCollection<Document> typeMappingCollection = databaseConn.getCollection(ServerConfig.TYPE_MAPPING);
        Document q = new Document();
        q.put("_id", idCode);
        Document res = typeMappingCollection.find(q).limit(1).first();
        if (res == null) {
            return GiftCodeType.NONE;
        } else {
            return GiftCodeType.valueOf(res.getString("type"));
        }
    }


    public boolean addACollectionName(String collectionName) {
        MongoCollection<Document> collection = databaseConn.getCollection(ServerConfig.COLLECTIONNAME_GENERATED);
        Document d = new Document();
        d.put("_id", collectionName);
        boolean exist = collection.find(d).limit(1).first() != null;
        if (exist) {
            return false;
        } else {
            collection.insertOne(d);
            return true;
        }

    }

    public List<String> getAllColltionGenerated(){
        MongoCollection<Document> collection = databaseConn.getCollection(ServerConfig.COLLECTIONNAME_GENERATED);
        FindIterable<Document> res= collection.find();
        Set<Document> all= new HashSet<>();
        res.into(all);
        return all.stream().map(document -> document.getString("_id")).collect(Collectors.toList());
    }


    public DatabaseConn getDatabaseConn() {
        return databaseConn;
    }

    public void setDatabaseConn(DatabaseConn databaseConn) {
        this.databaseConn = databaseConn;
    }
}

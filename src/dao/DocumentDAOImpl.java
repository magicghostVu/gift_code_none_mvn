package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import org.bson.Document;
import work.with.db.DatabaseConn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Fresher on 28/07/2017.
 */
public class DocumentDAOImpl implements DocumentDAO {

    private DatabaseConn databaseConn;

    private MongoCollection<Document> giftCodeColl;

    @Override
    public Document getDocumentById(String id) {
        Document q= new Document();
        q.put("_id", id);
        return giftCodeColl.find(q).limit(1).first();
    }

    @Override
    public List<Document> getDocumentsByCollectionName(String collectionName) {
        Document q= new Document();
        q.put("nameOfCollection", collectionName);
        FindIterable<Document> res= giftCodeColl.find(q);
        Set<Document> documentSet= new HashSet<>();
        res.into(documentSet);
        return new ArrayList<>(documentSet);
    }

    public DatabaseConn getDatabaseConn() {
        return databaseConn;
    }

    public void setDatabaseConn(DatabaseConn databaseConn) {
        this.databaseConn = databaseConn;
    }

    public MongoCollection<Document> getGiftCodeColl() {
        return giftCodeColl;
    }

    public void setGiftCodeColl(MongoCollection<Document> giftCodeColl) {
        this.giftCodeColl = giftCodeColl;
    }

    public void init(){
        giftCodeColl= databaseConn.getCollection(ServerConfig.GIFT_CODE_GENERAL);
    }
}

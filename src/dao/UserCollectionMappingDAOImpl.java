package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import model.UserCollectionMappingModel;
import org.bson.Document;
import work.with.db.DatabaseConn;

/**
 * Created by magic_000 on 02/08/2017.
 */
public class UserCollectionMappingDAOImpl implements UserCollectionMappingDAO {

    private DatabaseConn conn;
    private MongoCollection<Document> collectionUserCollMapping;
    private Gson gson= new Gson();

    @Override
    public UserCollectionMappingModel getUserCollectionModelByCollectionName(String name) {
        Document q= new Document();
        q.put("_id", name);
        Document modelDocument= collectionUserCollMapping.find(q).limit(1).first();
        if(modelDocument!=null){
            return gson.fromJson(modelDocument.toJson(),UserCollectionMappingModel.class);
        }else{
            return null;
        }
    }

    @Override
    public boolean saveAModel(UserCollectionMappingModel model) {
        try {
            //update
            if(modelExist(model)){
                Document q= new Document();
                q.put("_id", model.get_id());
                Document updateCmd= new Document("$set", model.toDocument());
                collectionUserCollMapping.updateOne(q, updateCmd);
            }
            //insert
            else{
                collectionUserCollMapping.insertOne(model.toDocument());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modelExist(UserCollectionMappingModel model){
        Document q= new Document();
        q.put("_id", model.get_id());
        return collectionUserCollMapping.find(q).limit(1).first()!=null;
    }

    public DatabaseConn getConn() {
        return conn;
    }

    public void setConn(DatabaseConn conn) {
        this.conn = conn;
    }

    public void init(){
        this.collectionUserCollMapping= conn.getCollection(ServerConfig.USER_COLLECTION_MAPPING);
    }

}

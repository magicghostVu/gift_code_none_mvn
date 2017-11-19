package dao;

import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import model.TrackingDataModel;
import org.bson.Document;
import work.with.db.DatabaseConn;

/**
 * Created by magic_000 on 18/11/2017.
 */
public class TrackingModelDAO {

    private DatabaseConn databaseConn;

    private MongoCollection<Document> trackingCollection;

    public TrackingModelDAO() {

    }

    public DatabaseConn getDatabaseConn() {
        return databaseConn;
    }




    public void setDatabaseConn(DatabaseConn databaseConn) {
        this.databaseConn = databaseConn;
    }

    public void init(){
        trackingCollection= databaseConn.getCollection(ServerConfig.TRACKING_COLLECTION_NAME);

        // init index here


    }

    public boolean saveATrackingModel(TrackingDataModel  model){
        try{
            trackingCollection.insertOne(model.toDocument());
            return true;
        }catch (Exception e){
            //Logg
            e.printStackTrace();
            return false;
        }
    }

    // add more function to query
}

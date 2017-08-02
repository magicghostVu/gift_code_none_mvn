package work.with.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.ServerConfig;
import org.bson.Document;

/**
 * Created by Fresher on 27/07/2017.
 */
public class DatabaseConn {
    //private MongoClient mongoClient;

    private MongoDatabase mongoDatabase;

    public DatabaseConn(){
        MongoClient mongoClient= new MongoClient(ServerConfig.HOST_MONGO, ServerConfig.PORT_MONGO);
        this.mongoDatabase= mongoClient.getDatabase(ServerConfig.DB_NAME);
    }


    /*public MongoDatabase getMongoDatabase(){
        return mongoDatabase;
    }*/

    public MongoCollection<Document> getCollection(String collectionName){
        return mongoDatabase.getCollection(collectionName);
    }



}

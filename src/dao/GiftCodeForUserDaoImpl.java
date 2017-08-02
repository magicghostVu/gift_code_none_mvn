package dao;

import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import model.GiftCodeForUser;
import org.bson.Document;
import utils.db.Utils;
import work.with.db.DatabaseConn;

/**
 * Created by magic_000 on 28/07/2017.
 */
public class GiftCodeForUserDaoImpl implements GiftCodeForUserDao {
    private DatabaseConn databaseConn;
    //static Gson gson = new Gson();
    private MongoCollection<Document> giftCodeUserColl ;
    @Override
    public GiftCodeForUser getGiftCodeForUserById(String id) {
        Document q = new Document();
        q.put("_id", id);
        Document res = giftCodeUserColl.find(q).limit(1).first();
        if (res != null) {
            return Utils.convertDocumentToGiftCodeForUser(res);
        } else {
            return null;
        }
    }

    @Override
    public boolean save(GiftCodeForUser giftCodeForUser) {
        try {
            Document documentToSave = giftCodeForUser.toDocument();
            // update
            if(giftCodeExist(giftCodeForUser.get_id())){
                Document q= new Document();
                q.put("_id", giftCodeForUser.get_id());
                Document updateCmd= new Document();
                updateCmd.put("$set", documentToSave);
                giftCodeUserColl.updateOne(q, updateCmd);
                return true;
            }
            // insert
            else{
                giftCodeUserColl.insertOne(documentToSave);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean giftCodeExist(String id) {
        Document q = new Document();
        q.put("_id", id);
        Document res = giftCodeUserColl.find(q).limit(1).first();
        return res != null;
    }

    public DatabaseConn getDatabaseConn() {
        return databaseConn;
    }

    public void setDatabaseConn(DatabaseConn databaseConn) {
        this.databaseConn = databaseConn;
    }


    /*private GiftCodeForUser convertDocumentToGiftCodeForUser(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        JSONArray arrayItem = jsonObject.getJSONArray("gameItems");
        jsonObject.remove("gameItems");
        List<GameItem> gameItemList = IntStream.range(0, arrayItem.length())
                .mapToObj(arrayItem::getJSONObject)
                .map(JSONObject::toString)
                .map(stringJson -> gson.fromJson(stringJson, GameItem.class)).collect(Collectors.toList());
        GiftCodeForUser giftCodeForUser = gson.fromJson(jsonObject.toString(), GiftCodeForUser.class);
        giftCodeForUser.setGameItems(gameItemList);
        return giftCodeForUser;
    }*/



    public void init(){
       giftCodeUserColl = databaseConn.getCollection(ServerConfig.GIFT_CODE_FOR_USER_COLL);
    }

}

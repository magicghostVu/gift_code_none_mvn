package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.ServerConfig;
import model.GiftCodeGeneral;
import org.bson.Document;
import utils.db.Utils;
import work.with.db.DatabaseConn;

/**
 * Created by Fresher on 28/07/2017.
 */
public class GiftCodeGeneralDaoImpl implements GiftCodeGeneralDAO {

    private DatabaseConn conn;
    private MongoCollection<Document> collectionGiftCodeGeneral;
    Gson gson = new Gson();

    @Override
    public GiftCodeGeneral getGiftCodeGeneralById(String id) {
        Document q = new Document();
        q.put("_id", id);
        Document res = collectionGiftCodeGeneral.find(q).limit(1).first();
        if (res == null) {
            return null;
        } else {
            return Utils.convertDocumentToGiftCodeGeneral(res);
        }
    }

    @Override
    public boolean save(GiftCodeGeneral giftCodeGeneral) {
        try {
            if (giftCodeExist(giftCodeGeneral.get_id())) {
                Document q= new Document();
                q.put("_id", giftCodeGeneral.get_id());
                Document updateCmd= new Document("$set", giftCodeGeneral.toDocument());
                collectionGiftCodeGeneral.updateOne(q, updateCmd);
            } else {
                collectionGiftCodeGeneral.insertOne(giftCodeGeneral.toDocument());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean giftCodeExist(String code) {
        Document q = new Document();
        q.put("_id", code);
        Document res = collectionGiftCodeGeneral.find(q).limit(1).first();
        return res != null;
    }

    public DatabaseConn getConn() {
        return conn;
    }

    public void setConn(DatabaseConn conn) {
        this.conn = conn;
    }

    /*private GiftCodeGeneral convertDocumentToGiftCodeGeneral(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        JSONArray arrayItem = jsonObject.getJSONArray("gameItems");
        List<GameItem> gameItemList = IntStream.range(0, arrayItem.length())
                .mapToObj(arrayItem::getJSONObject)
                .map(JSONObject::toString)
                .map(stringJson -> gson.fromJson(stringJson, GameItem.class))
                .collect(Collectors.toList());
        jsonObject.remove("gameItems");
        GiftCodeGeneral res = gson.fromJson(jsonObject.toString(), GiftCodeGeneral.class);
        res.setGameItems(gameItemList);
        return res;
    }*/


    public void init() {
        collectionGiftCodeGeneral = conn.getCollection(ServerConfig.GIFT_CODE_GENERAL);
    }
}

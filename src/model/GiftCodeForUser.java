package model;

import com.google.gson.Gson;
import model.sub_properties.GameItem;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by magic_000 on 28/07/2017.
 */
public class GiftCodeForUser extends AbstractGiftCodeModel {

    static Gson gson = new Gson();

    private int uid;

    public GiftCodeForUser(String _id, List<GameItem> gameItems, long lastModified, int timesCanUsed, int timesUsed, long expTime, int uid) {
        super(_id, gameItems, lastModified, timesCanUsed, timesUsed, expTime);
        this.uid = uid;
    }

    public GiftCodeForUser() {
    }

    @Override
    void update() {

    }

    @Override
    boolean save() {


        return true;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    @Override
    public Document toDocument() {
        JSONObject jsonObject = new JSONObject(gson.toJson(this));
        Document res = new Document();
        jsonObject.keySet().forEach(key -> res.put(key, jsonObject.get(key)));
        res.remove("gameItems");
        JSONArray gameItemsArr= jsonObject.getJSONArray("gameItems");
        List<Document> documentsGameItems= IntStream.range(0, gameItemsArr.length())
                .mapToObj(gameItemsArr::getJSONObject)
                .map(jsonObject1 -> {
                    Document gameItem= new Document();
                    jsonObject1.keySet().forEach(key->{
                        gameItem.put(key, jsonObject1.get(key));
                    });
                    return gameItem;
                }).collect(Collectors.toList());
        res.put("gameItems", documentsGameItems);
        return res;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}

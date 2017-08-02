package model;

import com.google.gson.Gson;
import model.sub_properties.GameItem;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by magic_000 on 28/07/2017.
 */
public class GiftCodeGeneral extends AbstractGiftCodeModel {


    //private List<Integer> listUidUsed;

    public GiftCodeGeneral() {
        //listUidUsed= new ArrayList<>();
    }


    public GiftCodeGeneral(String _id, List<GameItem> gameItems, long lastModified, int timesCanUsed, int timesUsed, long expTime) {
        super(_id, gameItems, lastModified, timesCanUsed, timesUsed, expTime);
        //listUidUsed= new ArrayList<>();
    }

    static Gson gson = new Gson();

    @Override
    public void update() {

    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    String toJson() {
        return gson.toJson(this);
    }

    @Override
    public Document toDocument() {
        JSONObject jsonObject = new JSONObject(toJson());
        Document res = new Document();
        jsonObject.keySet().forEach(key -> res.put(key, jsonObject.get(key)));
        res.remove("gameItems");
        JSONArray gameItemsArr = jsonObject.getJSONArray("gameItems");
        List<Document> documentsGameItems = IntStream.range(0, gameItemsArr.length())
                .mapToObj(gameItemsArr::getJSONObject)
                .map(jsonObject1 -> {
                    Document gameItem = new Document();
                    System.out.println();
                    jsonObject1.keySet().forEach(key -> gameItem.put(key, jsonObject1.get(key)));
                    return gameItem;
                }).collect(Collectors.toList());
        res.put("gameItems", documentsGameItems);
        //res.put("listUidUsed", listUidUsed);
        return res;
    }
}

package utils.db;

import com.google.gson.Gson;
import model.GiftCodeForUser;
import model.GiftCodeGeneral;
import model.sub_properties.GameItem;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Fresher on 28/07/2017.
 */
public class Utils {

    static Gson gson = new Gson();

    public static String genMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


    public static boolean checkGiftCodeValid(String giftCode) {
        if (giftCode.length() != 10) return false;
        String first8 = giftCode.substring(0, 8);
        String last2 = giftCode.substring(8, 10);
        String md5OfF8 = genMD5(first8);
        String first2OfMD5 = md5OfF8.substring(0, 2);
        return last2.equals(first2OfMD5);
    }


    public static GiftCodeForUser convertDocumentToGiftCodeForUser(Document document) {
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
    }


    public static GiftCodeGeneral convertDocumentToGiftCodeGeneral(Document document) {
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
    }

}

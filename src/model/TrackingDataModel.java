package model;

import com.google.gson.Gson;
import org.bson.Document;

import java.util.List;

/**
 * Created by magic_000 on 18/11/2017.
 */
public class TrackingDataModel  {

    private static Gson gson= new Gson();

    private Long timeStamp;

    private Integer uid;

    private Long gold;

    private Long coin;

    private String action;

    private List<Object> listParams;

    public TrackingDataModel(Long timeStamp, Integer uid, Long gold, Long coin, String action) {
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.gold = gold;
        this.coin = coin;
        this.action = action;
    }

    public TrackingDataModel() {
    }

    public Document toDocument(){
        String json= gson.toJson(this);
        return gson.fromJson(json, Document.class);
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public List<Object> getListParams() {
        return listParams;
    }

    public void setListParams(List<Object> listParams) {
        this.listParams = listParams;
    }
}

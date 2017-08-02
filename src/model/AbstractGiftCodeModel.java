package model;

import model.sub_properties.GameItem;
import org.bson.Document;
import utils.db.GiftCodeType;

import java.util.List;

/**
 * Created by Fresher on 27/07/2017.
 */
public class AbstractGiftCodeModel {


    public AbstractGiftCodeModel() {
    }

    public AbstractGiftCodeModel(String _id, List<GameItem> gameItems, long lastModified, int timesCanUsed, int timesUsed, long expTime) {
        this._id = _id;
        this.gameItems = gameItems;
        this.lastModified = lastModified;
        this.timesCanUsed = timesCanUsed;
        this.timesUsed = timesUsed;
        this.expTime = expTime;
    }

    // gift code will return to client
    private String _id;
    private List<GameItem> gameItems;
    private long lastModified;
    private int timesCanUsed;
    private int timesUsed;

    private long expTime;

    private GiftCodeType type;

    private String nameOfCollection;



    void update() {

    }


    boolean save() {
        return false;
    }

    String toJson() {
        return null;
    }

    Document toDocument(){
        return null;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<GameItem> getGameItems() {
        return gameItems;
    }

    public void setGameItems(List<GameItem> gameItems) {
        this.gameItems = gameItems;
    }


    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public int getTimesCanUsed() {
        return timesCanUsed;
    }

    public void setTimesCanUsed(int timesCanUsed) {
        this.timesCanUsed = timesCanUsed;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public GiftCodeType getType() {
        return type;
    }

    public void setType(GiftCodeType type) {
        this.type = type;
    }

    public String getNameOfCollection() {
        return nameOfCollection;
    }

    public void setNameOfCollection(String nameOfCollection) {
        this.nameOfCollection = nameOfCollection;
    }
}

package model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magic_000 on 02/08/2017.
 */
public class UserCollectionMappingModel {
    //collectionName
    private String _id;
    private List<Integer> uidUsed;


    public UserCollectionMappingModel() {
    }

    public UserCollectionMappingModel(String _id) {
        this._id = _id;
        this.uidUsed= new ArrayList<>();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Integer> getUidUsed() {
        return uidUsed;
    }

    public void setUidUsed(List<Integer> uidUsed) {
        this.uidUsed = uidUsed;
    }

    public Document toDocument(){
        Document res= new Document();
        res.put("_id", _id);
        res.put("uidUsed", uidUsed);
        return res;
    }

}

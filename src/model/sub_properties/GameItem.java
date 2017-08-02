package model.sub_properties;

/**
 * Created by Fresher on 27/07/2017.
 */
public class GameItem {
    private String id;
    private long quantity;

    public GameItem(String id, long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}

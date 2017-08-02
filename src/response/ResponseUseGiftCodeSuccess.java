package response;

import model.sub_properties.GameItem;

import java.util.List;

/**
 * Created by Fresher on 28/07/2017.
 */
public class ResponseUseGiftCodeSuccess extends BaseResponse {

    List<GameItem> gameItems;

    public ResponseUseGiftCodeSuccess(int errCode, List<GameItem> gameItems) {
        super(errCode);
        this.gameItems= gameItems;
    }


    public List<GameItem> getGameItems() {
        return gameItems;
    }

    public void setGameItems(List<GameItem> gameItems) {
        this.gameItems = gameItems;
    }
}

package response;

import org.bson.Document;

import java.util.List;

/**
 * Created by Fresher on 28/07/2017.
 */
public class ResponseGetAllGiftCodeByCollectionName  extends BaseResponse {

    List<Document> giftCodes;

    public ResponseGetAllGiftCodeByCollectionName(int errCode, List<Document> documents) {
        super(errCode);
        this.giftCodes= documents;
    }

    public List<Document> getGiftCodes() {
        return giftCodes;
    }

    public void setGiftCodes(List<Document> giftCodes) {
        this.giftCodes = giftCodes;
    }
}

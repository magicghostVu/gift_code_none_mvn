package response;

import java.util.List;

/**
 * Created by Fresher on 28/07/2017.
 */
public class ResopnseAllCollectionGenerated extends BaseResponse {
    private List<String> allCollectionName;


    public ResopnseAllCollectionGenerated(int errCode, List<String> allCollectionName) {
        super(errCode);
        this.allCollectionName = allCollectionName;
    }

    public List<String> getAllCollectionName() {
        return allCollectionName;
    }

    public void setAllCollectionName(List<String> allCollectionName) {
        this.allCollectionName = allCollectionName;
    }
}

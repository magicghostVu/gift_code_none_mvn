package response;

import java.util.List;

/**
 * Created by magic_000 on 02/08/2017.
 */
public class ResponseListUidByCollectionName extends BaseResponse {


    private List<Integer> listUId;

    public ResponseListUidByCollectionName(int errCode, List<Integer> listUid) {
        super(errCode);
        this.listUId=listUid;
    }

    public List<Integer> getListUId() {
        return listUId;
    }

    public void setListUId(List<Integer> listUId) {
        this.listUId = listUId;
    }
}

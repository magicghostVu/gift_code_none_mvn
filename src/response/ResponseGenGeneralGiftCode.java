package response;

import java.util.List;

/**
 * Created by Fresher on 28/07/2017.
 */
public class ResponseGenGeneralGiftCode extends BaseResponse {
    private List<String> codesList;

    public ResponseGenGeneralGiftCode(int errCode, List<String> codes) {
        super(errCode);
        this.codesList= codes;
    }

    public List<String> getCodesList() {
        return codesList;
    }

    public void setCodesList(List<String> codesList) {
        this.codesList = codesList;
    }
}

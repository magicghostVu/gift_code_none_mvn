package response;

import com.google.gson.Gson;

/**
 * Created by Fresher on 28/07/2017.
 */
public class BaseResponse {
    private int errCode;
    private String data;

    static Gson gson= new Gson();

    public BaseResponse(int errCode ) {
        this.errCode = errCode;

    }



    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toJson(){
        return gson.toJson(this);
    }

}

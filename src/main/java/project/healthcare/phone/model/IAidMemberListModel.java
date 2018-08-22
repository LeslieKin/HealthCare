package project.healthcare.phone.model;

import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IAidMemberListModel {

    interface DataCallback{

        void onSuccess(JSONObject data);

        void onFailed();
    }

    void setDataCallback(DataCallback callback);

    void sendAidMemberRequest(int mid,String data);
}

package project.healthcare.phone.model;

import android.os.Bundle;

import org.json.JSONObject;

/**
 * @author li.jq 940655212@qq.com
 *         Created on 2017/8/24.
 */

public interface IAidRecordListModel {

    interface DataCallback{

        void onSuccess(JSONObject data);

        void onFailed();
    }

    void setDataCallback(DataCallback callback);

    void sendCanaelAidRequest(Bundle params);
}


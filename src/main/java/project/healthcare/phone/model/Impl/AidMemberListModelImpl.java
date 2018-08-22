package project.healthcare.phone.model.Impl;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.model.IAidMemberListModel;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidMemberListModelImpl implements IAidMemberListModel {

    @Override
    public void setDataCallback(DataCallback callback) {
        mDataCallback = callback ;
    }

    @Override
    public void sendAidMemberRequest(int mid, String data) {
        HttpUtil.post(
                ServerPath.SET_AID_CONTACT,
                new JSONRequestParams()
                        .setParams(CommonKeys.MID, mid)
                        .setParams(CommonKeys.RELATIVES,data),
                mSetAidMemberResultReceiver
        );
    }

    private JSONDataReceiver mSetAidMemberResultReceiver = new JSONDataReceiver() {

        @Override
        protected void onSuccess(JSONObject data) {
           mDataCallback.onSuccess(data);
        }

        @Override
        protected void onFailed() {
           mDataCallback.onFailed();
        }
    };

    private DataCallback mDataCallback;
}

package project.healthcare.phone.model.Impl;

import android.os.Bundle;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.model.IAidRecordListModel;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidRecordListModelImpl implements IAidRecordListModel {

    @Override
    public void setDataCallback(DataCallback callback) {
        mDataCallback = callback;
    }

    @Override
    public void sendCanaelAidRequest(Bundle params) {
        HttpUtil.post(
                ServerPath.CANCEL_AID,
                new JSONRequestParams()
                        .setParams(CommonKeys.MID, params.getInt(CommonKeys.MID))
                        .setParams(CommonKeys.AID, params.getInt(CommonKeys.ID))
                        .setParams(CommonKeys.STATE, params.getInt(CommonKeys.TYPE)),
                mResultDataReceiver);
    }

    private JSONDataReceiver mResultDataReceiver = new JSONDataReceiver() {

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

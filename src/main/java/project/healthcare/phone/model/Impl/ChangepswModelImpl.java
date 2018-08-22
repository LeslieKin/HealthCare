package project.healthcare.phone.model.Impl;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.CommonKeys;
import project.healthcare.phone.model.IChangepswModel;

/**
 * @author li.jq
 * email 940655212@qq.com
 * @version 1.0
 */

public class ChangepswModelImpl implements IChangepswModel {

    @Override
    public void setDataCallback(final DataCallback dataCallback) {
        mDataCallback = dataCallback;
    }

    @Override
    public void sendChangpswRequest(final String phone, final String password) {
        HttpUtil.post(
                ServerPath.CHANGE_PASSWORD,
                new JSONRequestParams()
                        .setParams(CommonKeys.PHONE, phone)
                        .setParams(CommonKeys.PASSWORD, password),
                new ChangePasswordReceiver());
    }

    private final class ChangePasswordReceiver extends JSONDataReceiver {

        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onSuccess(data);
        }

        @Override
        protected void onFailed() {
            mDataCallback.onFailed();
        }
    }

    private DataCallback mDataCallback;
}

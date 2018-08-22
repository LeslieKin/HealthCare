package project.healthcare.phone.model.Impl;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.db.bean.User;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.CommonKeys;
import project.healthcare.phone.model.IFillDetailModel;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class FillDetailModelImpl implements IFillDetailModel {

    @Override
    public void setDataCallback(DataCallback dataCallback) {
        mDataCallback = dataCallback;
    }

    @Override
    public void sendRegisterRequest(User user) {
        HttpUtil.post(ServerPath.REGISTER,
                new JSONRequestParams()
                        .setParams(CommonKeys.NAME, user.getName())
                        .setParams(CommonKeys.IDENTITY, user.getIdentity())
                        .setParams(CommonKeys.PHONE, user.getPhone())
                        .setParams(CommonKeys.PASSWORD, user.getPassword()),
                new RegisterReceiver());
    }


    private final class RegisterReceiver extends JSONDataReceiver {

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

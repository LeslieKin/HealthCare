package project.healthcare.phone.model.Impl;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONRequestParams;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.model.ILgByPhoneModel;
import project.healthcare.phone.receive.StringReceiver;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class LgByPhoneModelImpl implements ILgByPhoneModel {

    @Override
    public void setDataCallback(DataCallback callback) {
        mCallback = callback;
    }

    @Override
    public void sendGetIdentityRequest(final String phone) {
        HttpUtil.post(
                ServerPath.GET_IDENTITY,
                new JSONRequestParams()
                        .setParams(CommonKeys.PHONE, phone),
                new IdentityReceiver());
    }

    /**
     * 身份证信息接收处理器
     */
    private final class IdentityReceiver extends StringReceiver {
        @Override
        public void onSuccess(String identity) {
            mCallback.onSuccess(identity);
        }

        @Override
        public void onFailed() {
            mCallback.onFailed();
        }
    }

    private DataCallback mCallback;
}


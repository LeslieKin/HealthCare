package project.healthcare.phone.model.Impl;

import android.text.TextUtils;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.ui.activity.Session;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.LogModes;
import project.healthcare.phone.constants.SessionKeys;
import project.healthcare.phone.model.ISendCodeModel;
import project.healthcare.phone.preference.Pref;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class SendCodeModelImpl implements ISendCodeModel {

    @Override
    public void setDataCallback(DataCallback dataCallback) {
        mCallback=dataCallback;
    }

    @Override
    public void sendVericodeRequest(final String phone, final int type) {
        HttpUtil.post(ServerPath.SEND_VERICODE,
                new JSONRequestParams()
                        .setParams(CommonKeys.PHONE, phone)
                        .setParams("checkType", type),
                new SendVericodeReceiver());
    }

    /**
    * 发送验证码接收器
    */
    private final class SendVericodeReceiver extends JSONDataReceiver {

        @Override
        protected void onSuccess(JSONObject data) {
           mCallback.onSuccess(data);
        }

        @Override
        protected void onFailed() {
           mCallback.onFailed();
        }
    }

    /**
     * 更新成员信息
     */
    @Override
    public void startDataUpdate() {
        // TODO Auto-generated method stub
        int logMode = Pref.getAppPreference().getLogMode ();
        String data = Pref.getAppPreference().getLogCount();
        String lastVisitor = Pref.getAppPreference().getLastVisitor();
        if (!TextUtils.isEmpty(lastVisitor)) {
            Session.data.putString(SessionKeys.IDENTITY, lastVisitor);
        } else if (logMode == LogModes.IDENTITY) {
            Pref.getAppPreference().setLastVisitor(data);
            Session.data.putString(SessionKeys.IDENTITY, data);
        }
    }

    private DataCallback mCallback;
}

package project.healthcare.phone.model.Impl;

import android.os.Bundle;
import android.text.TextUtils;

import com.wilimx.constants.Constants;
import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.LogModes;
import project.healthcare.phone.constants.SessionKeys;
import project.healthcare.phone.db.IdentityCount;
import project.healthcare.phone.db.IdentityCountQueryManager;
import project.healthcare.phone.model.ILoginModel;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.ui.activity.Session;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class LoginModelImpl implements ILoginModel {

    @Override
    public void setDataCallback(DataCallback dataCallback) {
        mDataCallback = dataCallback;
    }

    /**
     * 生成页面参数
     *
     * @return 页面参数
     */
    @Override
    public Bundle getPageParams() {
        Bundle data = new Bundle();

        IdentityCount count = mIdentityQueryManager.findLastIdentityCount();
        if (count != null) {
            data.putString(CommonKeys.IDENTITY, count.getIdentity());
            if (Pref.getAppPreference().isRememberPassword()) {
                data.putString(CommonKeys.PASSWORD, count.getPassword());
            } else {
                data.putString(CommonKeys.PASSWORD, Constants.EMPTY_TEXT);
            }
        }
        data.putBoolean(CommonKeys.REMEMBER_PASSWORD, Pref.getAppPreference().isRememberPassword());
        return data;
    }

    /**
     * 保存手机登陆信息
     * 身份证号登陆
     */
    @Override
    public void saveIdentityLogInfo(String identity, String password, boolean isRememberPassword) {
        if (Pref.getAppPreference().getLogMode() != LogModes.IDENTITY
                || !identity.equals(Pref.getAppPreference().getLogCount())) {
            Pref.getAppPreference().clearLastVisitor();
        }
        Pref.getAppPreference().setLogMode(LogModes.IDENTITY);
        Pref.getAppPreference().setLogCount(identity);

        mIdentityQueryManager.saveIdentityCount(identity, password);
        if (!isRememberPassword) {
            mIdentityQueryManager.clearAllPasswords();
        }
        Pref.getAppPreference().setRememberPassword(isRememberPassword);
        Pref.getAppPreference().setAutoLogin(isAutoLogin);
    }

    /**
     * 更新成员信息
     */
    @Override
    public void startDataUpdate() {
        // TODO Auto-generated method stub
        int logMode = Pref.getAppPreference().getLogMode();
        String data = Pref.getAppPreference().getLogCount();
        String lastVisitor = Pref.getAppPreference().getLastVisitor();
        if (!TextUtils.isEmpty(lastVisitor)) {
            Session.data.putString(SessionKeys.IDENTITY, lastVisitor);
        } else if (logMode == LogModes.IDENTITY) {
            Pref.getAppPreference().setLastVisitor(data);
            Session.data.putString(SessionKeys.IDENTITY, data);
        }
    }

    /**
     * 发送登陆请求
     */
    @Override
    public void sendLoginRequest(String identity, String password) {
        HttpUtil.post(
                ServerPath.LOGIN,
                new JSONRequestParams()
                        .setParams(CommonKeys.IDENTITY, identity)
                        .setParams(CommonKeys.PASSWORD, password),
                new LoginResultReceiver()
        );
    }

    /**
     * 登陆结果接收
     */
    private final class LoginResultReceiver extends JSONDataReceiver {

        @Override
        protected void onSuccess(JSONObject data) {
            if(mDataCallback!=null){
                mDataCallback.onSuccess(data);
            }
        }

        @Override
        protected void onFailed() {
            if(mDataCallback!=null){
                mDataCallback.onFailed();
            }
        }
    }

    // 身份证账号查询器
    private IdentityCountQueryManager mIdentityQueryManager = new IdentityCountQueryManager();

    //回调
    private DataCallback mDataCallback;

    //自动登录
    private boolean isAutoLogin = true;
}

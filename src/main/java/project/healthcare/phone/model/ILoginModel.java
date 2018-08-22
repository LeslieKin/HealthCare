package project.healthcare.phone.model;

import android.os.Bundle;

import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface ILoginModel {

    interface DataCallback{

        void onSuccess(JSONObject data);

        void onFailed();
    }

    void setDataCallback(DataCallback dataCallback);

    /**
     * 发送登陆请求
     * @param identity  身份证号
     * @param password  密码
     */
    void sendLoginRequest(String identity,String password);

    /**
     *
     * @return
     */
    Bundle getPageParams();

    void saveIdentityLogInfo(String identity,String password,boolean isRememberPassword);

    void startDataUpdate();
}

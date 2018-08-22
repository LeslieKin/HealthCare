package project.healthcare.phone.model;

import org.json.JSONObject;

/**
 * @author li.jq 940655212@qq.com
 *         Created on 2017/8/23.
 */

public interface ISendCodeModel {

    interface DataCallback{

        void onSuccess(JSONObject data);

        void onFailed();
    }

    void setDataCallback(DataCallback dataCallback);

    /**
     * 发送发送验证码请求
     * @param phone 手机号
     * @param type  类型
     */
    void sendVericodeRequest(String phone,int type);

    void startDataUpdate();


}

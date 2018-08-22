package project.healthcare.phone.model;

import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IChangepswModel {

    /**
     * 数据回调
     */
    interface DataCallback{

    /**
    * 请求成功
    * @param data 返回数据
    */
    void onSuccess(JSONObject data);

    /**
     * 请求失败
     */
    void onFailed();
}

    /**
     * 设置回调
     * @param dataCallback 回调
     */
    void setDataCallback(DataCallback dataCallback);

    /**
     * 发送修改密码请求
     * @param phone 手机号
     * @param password 密码
     */
    void sendChangpswRequest(String phone,String password);
}

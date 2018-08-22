package project.healthcare.phone.model;

import org.json.JSONObject;

import project.healthcare.phone.db.bean.User;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IFillDetailModel {

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
     * 发送注册请求
     * @param user 用户信息
     */
    void sendRegisterRequest(User user);
}

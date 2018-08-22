package project.healthcare.phone.model;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface ILgByPhoneModel {

    interface DataCallback{

        void onSuccess(String data);

        void onFailed();
    }

    void setDataCallback(DataCallback callback);

    /**
     * 发送获取身份证号请求
     * @param phone 手机号
     */
    void sendGetIdentityRequest(String phone);
}

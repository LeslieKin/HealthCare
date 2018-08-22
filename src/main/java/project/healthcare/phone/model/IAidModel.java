package project.healthcare.phone.model;

import android.app.Activity;

import com.baidu.location.BDLocation;

import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IAidModel {

    interface DataCallback {

        /**
         * 请求成功
         * GRL : GetRecordList
         * @param data 返回数据
         */
        void onGRLSuccess(JSONObject data);

        void onGRLFailed();

        /**
         * 请求成功
         * GML : GetMemberList
         * @param data 返回数据
         */
        void onGMLSuccess(JSONObject data);

        void onGMLFailed();

        /**
         * 请求成功
         * GAR : GetAidResult
         * @param data 返回数据
         */
        void onGARSuccess(JSONObject data);

        void onGARFailed();

        void onReceiveLocation(BDLocation location);

    }

    void inits();

    void setDataCallback(DataCallback callback);

    /**
     * 发送获取急救记录请求
     */
    void sendGetRecordListReq();

    /**
     * 发送获取紧急联系人请求
     */
    void sendGetMenberListReq();

    /**
     * 发送获取位置信息请求
     * @param location 位置信息
     */
    void sendGetAidResultReq(BDLocation location);

    void setLocationClient(Activity activity);

    void stopLocationClient();

    int getMid();


}

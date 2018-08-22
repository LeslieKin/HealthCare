package project.healthcare.phone.model.Impl;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.db.UserInfoQueryManager;
import project.healthcare.phone.model.IAidModel;
import project.healthcare.phone.preference.Pref;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidModelImpl implements IAidModel {

    @Override
    public void inits() {
        mid = userInfoQueryManager.findMember(Pref.getAppPreference().getLogCount()).getId();
    }

    @Override
    public void setDataCallback(final DataCallback callback) {
        mDataCallback=callback;
    }

    @Override
    public void sendGetRecordListReq() {
        HttpUtil.post(
                ServerPath.GET_AID_RECORD,
                new JSONRequestParams()
                        .setParams(CommonKeys.MID, mid),
                mRecordReceiver);
    }

    @Override
    public void sendGetMenberListReq() {
        HttpUtil.post(
                ServerPath.GET_AID_CONTACT,
                new JSONRequestParams()
                        .setParams(CommonKeys.MID, mid),
                mMemberReceiver);
    }

    @Override
    public void sendGetAidResultReq(final BDLocation location) {
        HttpUtil.post(
                ServerPath.REQUEST_AID,
                new JSONRequestParams()
                        .setParams(CommonKeys.IDENTITY, Pref.getAppPreference().getLogCount())
                        .setParams(CommonKeys.LONGITUDE, location.getLongitude())
                        .setParams(CommonKeys.LATITUDE, location.getLatitude())
                        .setParams(CommonKeys.ADDRESS, location.getAddrStr()),
                mAidResultReceiver);
    }

    @Override
    public void setLocationClient(final Activity activity) {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(activity.getApplicationContext());
            mLocationClient.registerLocationListener(mLocationListener);
        }

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void stopLocationClient() {
        mLocationClient.stop();
    }

    @Override
    public int getMid() {
        return mid;
    }

    /**
    * 急救记录
    */
    private JSONDataReceiver mRecordReceiver = new JSONDataReceiver() {

        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onGRLSuccess(data);
        }

        @Override
        protected void onFailed() {
            mDataCallback.onGRLFailed();
        }
    };

    /**
    * 紧急联系人记录
    */
    private JSONDataReceiver mMemberReceiver = new JSONDataReceiver() {

        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onGMLSuccess(data);
        }

        @Override
        protected void onFailed() {
            mDataCallback.onGMLFailed();
        }
    };

    /**
     * 急救记录
     */
    private JSONDataReceiver mAidResultReceiver = new JSONDataReceiver() {

        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onGARSuccess(data);
        }

        @Override
        protected void onFailed() {
            mDataCallback.onGARFailed();
        }
    };


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 当不需要定位图层时关闭定位图层
            mDataCallback.onReceiveLocation(location);
        }

    }

    private DataCallback mDataCallback;

    //定位服务器
    private LocationClient mLocationClient;

    private MyLocationListener mLocationListener = new MyLocationListener();

    private UserInfoQueryManager userInfoQueryManager = new UserInfoQueryManager();

    private int mid;
}

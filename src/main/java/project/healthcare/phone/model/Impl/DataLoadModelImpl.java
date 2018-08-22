package project.healthcare.phone.model.Impl;

import android.text.TextUtils;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONArrayDataReceiver;
import com.wilimx.http.JSONRequestParams;
import com.wilimx.work.RequestCounter;

import org.json.JSONArray;

import java.util.List;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.UserInfo;
import project.healthcare.phone.db.CompositeData;
import project.healthcare.phone.db.CompositeDataQueryManager;
import project.healthcare.phone.db.DetectData;
import project.healthcare.phone.db.DetectDataQueryManager;
import project.healthcare.phone.db.DetectInfo;
import project.healthcare.phone.db.UserInfoQueryManager;
import project.healthcare.phone.model.IDataLoadModel;
import project.healthcare.phone.receive.DetectDataReceiver;
import project.healthcare.phone.receive.FileReceiver;
import project.healthcare.phone.receive.MemberInfoListReceiver;
import project.healthcare.phone.util.RequestUtil;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class DataLoadModelImpl implements IDataLoadModel {


    @Override
    public void setDataCallback(DataCallback callback) {
        mCallback = callback;
    }

    @Override
    public void sendLoadTypesRequest() {
        HttpUtil.post(ServerPath.GET_AV_TYPES,
                RequestUtil.makeIdentityRequestParams(),
                new AvTypesReceiver());
    }

    @Override
    public void sendUpdateDetectDataRequest(final String identity) {
        if (!TextUtils.isEmpty(identity)) {
            HttpUtil.post(ServerPath.GET_DETECT_DATA,
                    new JSONRequestParams().setParams(CommonKeys.IDENTITY, identity),
                    new DetectDataReceiveHandler());
        }
    }

    @Override
    public void sendUpdateMemberDataRequest(String identity) {
        HttpUtil.post(
                ServerPath.GET_MEMBERINFO_LIST,
                new JSONRequestParams()
                        .setParams(CommonKeys.IDENTITY, identity),
                new MemberInfoReceiver());
    }

    @Override
    public void updateMemberInfo(List<UserInfo> userInfoList) {
        mUserInfoQueryManager = new UserInfoQueryManager();
        mUserInfoQueryManager.deleteAll();

        mPhotoRequestCounter = new RequestCounter();
        mPhotoRequestCounter.addHandler(new RequestCounter.CountHandler() {
            @Override
            public void onCountStart() {
            }

            @Override
            public void onCountStop() {
                if (mDataLoadFinished) {
                    mCallback.toPostLoadFinsh();
                }
            }
        });

        PhotoReceiver receiver;

        for (UserInfo userInfo : userInfoList) {
            mUserInfoQueryManager.save(userInfo);
            mPhotoRequestCounter.addCount();
            receiver = new PhotoReceiver();
            receiver.setData(userInfo);
            HttpUtil.get(
                    ServerPath.BASE_IMAGE_ADDRESS + userInfo.getPhotoAddress(),
                    null,
                    receiver);
        }
        mCallback.loadAvailabeTypes();
    }

    @Override
    public void updateDetectData(DetectInfo detectData) {
        updateCompositeInfo(detectData.getCompositeInfoList());
        updateDetectInfos(detectData.getDetectInfoList());

        if (mPhotoRequestCounter != null && !mPhotoRequestCounter.isEmpty()) {
            mDataLoadFinished = true;
        } else {
            mCallback.toPostLoadFinsh();
        }
    }

    /**
     * 更新综合检测信息
     *
     * @param compositeInfoList 综合检测信息列表
     */
    private final void updateCompositeInfo(List<CompositeData> compositeInfoList) {
        if (compositeInfoList == null) {
            return;
        }
        CompositeDataQueryManager queryManager = new CompositeDataQueryManager();
        queryManager.deleteAll();

        for (CompositeData info : compositeInfoList) {
            queryManager.save(info);
        }
    }

    /**
     * 更新检测信息列表
     */
    private final void updateDetectInfos(List<DetectData> detectInfoList) {
        if (detectInfoList == null) {
            return;
        }
        DetectDataQueryManager queryManager = new DetectDataQueryManager();
        queryManager.deleteAll();

        for (DetectData info : detectInfoList) {
            queryManager.save(info);
        }
    }

    /**
     * 可用检测类型数据接收器
     */
    private final class AvTypesReceiver extends JSONArrayDataReceiver {
        @Override
        protected void onSuccess(JSONArray data) {
            mCallback.onLAVSuccess(data);
        }

        @Override
        protected void onFailed() {
            mCallback.onLAVFailed(getErrorCode());
        }
    }

    /**
     * 成员信息接收处理器
     */
    private final class DetectDataReceiveHandler extends DetectDataReceiver {

        @Override
        protected void onSuccess(DetectInfo detectData) {
            mCallback.onUDDSuccess(detectData, getErrorCode());
        }

        @Override
        public void onFailed() {
            mCallback.onUDDFailed(getErrorCode());
        }
    }


    /**
     * 成员信息接收处理器
     */
    private final class MemberInfoReceiver extends MemberInfoListReceiver {

        @Override
        public void onSuccess(List<UserInfo> userInfoList) {
            mCallback.onUMDSuccess(userInfoList, getErrorCode());
        }

        @Override
        public void onFailed() {
            mCallback.onUMDFailed(getErrorCode());
        }
    }

    /**
     * 照片接收处理器
     */
    private final class PhotoReceiver extends FileReceiver<UserInfo> {

        @Override
        protected void onSuccess(byte[] data) {
            UserInfo info = getData();
            info.setPhoto(data);
            mUserInfoQueryManager.update(info);
            mPhotoRequestCounter.removeCount();
        }

        @Override
        protected void onFailed() {
            mPhotoRequestCounter.removeCount();
        }
    }

    /**
     * 解析整数数组
     *
     * @param array JSON数组
     * @return 整数数组
     */
    public final int[] parseIntArray(JSONArray array) {
        if (array != null) {
            final int SIZE = array.length();
            int result[] = new int[SIZE];

            for (int i = 0; i < SIZE; ++i) {
                result[i] = array.optInt(i);
            }
            return result;
        }
        return null;
    }


    private DataCallback mCallback;

    // 用户信息查询处理器
    private UserInfoQueryManager mUserInfoQueryManager = null;

    // 照片请求计数
    private RequestCounter mPhotoRequestCounter = null;

    // 数据加载操作是否完成
    private boolean mDataLoadFinished = false;
}

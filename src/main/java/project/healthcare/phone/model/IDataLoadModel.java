package project.healthcare.phone.model;

import org.json.JSONArray;

import java.util.List;

import project.healthcare.phone.constants.UserInfo;
import project.healthcare.phone.db.DetectInfo;

/**
 * @author li.jq 940655212@qq.com
 *         Created on 2017/8/25.
 */

public interface IDataLoadModel {

    interface DataCallback{

        void onLAVSuccess(JSONArray data);

        void onLAVFailed(int errorCode);

        void onUDDSuccess(DetectInfo detectData,int errorCode);

        void onUDDFailed(int errorCode);

        void onUMDSuccess(List<UserInfo> userInfoList,int errorCode);

        void onUMDFailed(int errorCode);

        void toPostLoadFinsh();

        void loadAvailabeTypes();
    }

    void setDataCallback(DataCallback callback);

    int[] parseIntArray(JSONArray array);

    void sendLoadTypesRequest();

    void sendUpdateDetectDataRequest(String identity);

    void sendUpdateMemberDataRequest(String identity);

    void updateMemberInfo(List<UserInfo> userInfoList);

    void updateDetectData(DetectInfo detectData);
}

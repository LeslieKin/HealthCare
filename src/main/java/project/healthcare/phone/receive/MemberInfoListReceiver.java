package project.healthcare.phone.receive;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.UserInfo;

import com.wilimx.http.JSONBasedDataReceiver;

/**
 * 成员信息列表数据接收器
 */
public abstract class MemberInfoListReceiver extends JSONBasedDataReceiver<List<UserInfo>> {

  @Override
  protected List<UserInfo> parseData(JSONObject data) {
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    try{
      JSONArray userInfoArray = data.getJSONArray(CommonKeys.RESULT);
      for(int i=0, len=userInfoArray.length(); i<len; i++){
        JSONObject jsonUserInfo = userInfoArray.getJSONObject(i);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(jsonUserInfo.getInt(CommonKeys.ID));
        userInfo.setName(jsonUserInfo.getString(CommonKeys.USERNAME));
        userInfo.setIdentity(jsonUserInfo.getString(CommonKeys.IDENTITY));
        userInfo.setPhotoAddress(jsonUserInfo.getString(CommonKeys.PIC));
        
        JSONArray unlockTypesArray = jsonUserInfo.getJSONArray(CommonKeys.UNLOCK_TYPES);
        int[] mUnlockTypes = new int[unlockTypesArray.length()];
        for(int j=0, jlen=unlockTypesArray.length(); j<jlen; j++)
          mUnlockTypes[i] = unlockTypesArray.getInt(j);
        userInfo.setUnlockTypes(mUnlockTypes);
        
        JSONArray mDataTypesArray = jsonUserInfo.getJSONArray(CommonKeys.DATA_TYPES);
        int[] mDataTypes = new int[mDataTypesArray.length()];
        for(int j=0, jlen=mDataTypesArray.length(); j<jlen; j++)
          mDataTypes[j] = mDataTypesArray.getInt(j);
        userInfo.setDataTypes(mDataTypes);
        userInfoList.add(userInfo);
      }
    }catch(JSONException e){
      e.printStackTrace();
    }
    return userInfoList;
  }

}

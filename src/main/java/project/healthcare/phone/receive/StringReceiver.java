package project.healthcare.phone.receive;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;

import com.wilimx.http.JSONBasedDataReceiver;

/**
 * 字符串数据接收器
 * 
 * @author xiao.yl
 * created at 2014-06-07 10:50
 */
public abstract class StringReceiver extends JSONBasedDataReceiver<String> {

  @Override
  protected String parseData(JSONObject data) {
    int errorCode = data.optInt(CommonKeys.ERROR_CODE, CommonKeys.NONE);
    if (errorCode != CommonKeys.NONE) {
      setErrorCode(errorCode);
    }
    return data.optString(CommonKeys.IDENTITY, null);
  }

}

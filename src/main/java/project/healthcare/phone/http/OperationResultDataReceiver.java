package project.healthcare.phone.http;

import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;

import com.wilimx.http.JSONBasedDataReceiver;

/**
 * 操作结果数据接收器
 * 
 * <pre>Target Response:
 * {
 *   result : "" // [boolean] 删除操作是否成功
 * }
 * </pre>
 * 
 * @see CommonKeys#RESULT
 */
public abstract class OperationResultDataReceiver extends JSONBasedDataReceiver<Boolean> {

  @Override
  protected Boolean parseData(JSONObject data) {
    return data.optBoolean(CommonKeys.RESULT);
  }

}

package project.healthcare.phone.util;

import project.healthcare.phone.preference.Pref;
import com.wilimx.http.JSONRequestParams;

/**
 * 请求工具
 * 
 * @author cai.wh
 */
public class RequestUtil {

  /**
   * 生成身份证请求参数
   * 
   * @return 身份证请求参数
   */
  public static final JSONRequestParams makeIdentityRequestParams() {
    return new JSONRequestParams()
      .setParams("identity", Pref.getAppPreference().getLogCount());
  }

  private RequestUtil() {}

}

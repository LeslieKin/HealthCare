package project.healthcare.phone.constants;

public interface PrefKeys {

  /**
   * 设备ID
   * :String
   */
  String EQUIP_ID = "equipId";

  /**
   * 地理位置
   * :String
   * 
   * <p>Pattern:
   * "lat,lng"
   * </p>
   */
  String LOCATION = "location";

  /**
   * 设备支持的检测类型
   * :String
   * 
   * <p>Pattern:
   * "type,.."
   * </p>
   */
  String SUPPORT_TYPES = "supportTypes";

  /**
   * 设备支持的检测类型是否经过手动配置
   * :boolean
   */
  String SUPPORT_CONFIG = "supportConfig";

  /* -- 登录首选项 -- */
  /**
   * 登录模式
   */
  public static final String LOG_MODE = "logMode";

  /**
   * 登录首选项
   */
  public static final String LOG_COUNT = "logCount";

  /**
   * 最后一次访问者
   */
  public static final String LAST_VISITOR = "lastVisitor";

  /**
   * 记住密码状态
   */
  public static final String FLAG_REMEMBER_PASSWORD = "rememberPassword";

  /**
   * 自动登录
   */
  public static final String AUTO_LOGIN = "autoLogin";
  
  /**
   * 推送ID
   */
  public static final String PUSH_ID = "push_id";

  /**
   * 救助
   */
  public static final String AID_INFO = "aid_info";
  
  /**
   * 新急救信息
   */
  public static final String NEW_AID_INFO = "new_aid_info";
}

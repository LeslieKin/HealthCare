package project.healthcare.phone.constants;

public interface CommonKeys {

  /**
   * 名字
   */
  String NAME = "name";

  /**
   * 身份证
   */
  String IDENTITY = "identity";

  /**
   * 手机号
   */
  String PHONE = "phone";

  /**
   * 密码
   */
  String PASSWORD = "password";

  /**
   * 记住密码
   * :boolean
   */
  String REMEMBER_PASSWORD = "rememberPassword";

  /**
   * 自动登录
   * :boolean
   */
  String AUTO_LOGIN = "autoLogin";

  /**
   * 登录模式
   * :int
   * 
   * @see ILogModes#IDENTITY
   * @see ILogModes#PHONE
   */
  String LOG_MODE = "logMode";

  /**
   * 结果
   */
  String RESULT = "result";

  /**
   * 错误码
   */
   String ERROR_CODE    = "errorCode";

  /**
   * 无错误
   */
  int NONE = -0x01;
  
}

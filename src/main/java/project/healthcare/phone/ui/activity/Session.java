package project.healthcare.phone.ui.activity;

import android.graphics.Bitmap;

/**
 * 会话
 * 
 * @author xiao.yl
 * created at 2014-07-29 07:48
 */
public class Session extends com.wilimx.app.Session {

  /**
   * 获取用户照片
   * 
   * @param image 用户照片
   */
  public static final void setUserImage(Bitmap image) {
    _userImage = image;
  }

  /**
   * 设置用户照片
   * 
   * @return 用户照片
   */
  public static final Bitmap getUserImage() {
    return _userImage;
  }

  /**
   * 设置登录状态
   * 
   * @param status 登录状态
   */
  public static final void setRunStatus(boolean status) {
    _runStatus = status;
  }

  /**
   * 判断用户登录状态
   * 
   * @return 用户登录状态
   */
  public static final boolean isDeviceRunning() {
    return _runStatus;
  }

  // 用户照片
  private static Bitmap _userImage = null;

  // 登录状态
  private static boolean _runStatus = false;

}

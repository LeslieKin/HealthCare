package project.healthcare.phone.preference;

import project.healthcare.phone.constants.DetectType;
import project.healthcare.phone.constants.LogModes;
import project.healthcare.phone.constants.PrefKeys;
import project.healthcare.phone.constants.PrefNames;
import android.text.TextUtils;

import com.wilimx.app.Preference;

public class AppPreference extends Preference {

  public AppPreference() {
    setPreferenceName(PrefNames.APP);
  }

  /**
   * 获取推送ID
   * 
   * @return 推送ID
   */
  public final String getPushId() {
    return getString(PrefKeys.PUSH_ID);
  }

  /**
   * 设置推送ID
   * 
   * @param pushId 推送ID
   */
  public final void setPushId(String pushId) {
    saveString(PrefKeys.PUSH_ID, pushId);
  }

  /**
   * 获取设备ID
   * 
   * @return 设备ID
   */
  public String getEquipId() {
    return getString(PrefKeys.EQUIP_ID);
  }

  /**
   * 设置设备ID
   * 
   * @param equipId 设备ID
   */
  public void setEquipId(String equipId) {
    saveString(PrefKeys.EQUIP_ID, equipId);
  }

  /**
   * 获取地理位置
   * 
   * @return 地理位置
   */
  public double[] getLocation() {
    String location = getString(PrefKeys.LOCATION);

    if (!TextUtils.isEmpty(location)) {
      String pos[] = location.split(",");

      if (pos.length == 2) {
        return new double[] {
          Double.parseDouble(pos[0]),
          Double.parseDouble(pos[1])
        };
      }
    }
    return null;
  }

  /**
   * 设置地理位置
   * 
   * @param lat 纬度
   * @param lng 经度
   */
  public void setLocation(double lat, double lng) {
    saveString(PrefKeys.LOCATION, lat + "," + lng);
  }

  /**
   * 获取设备支持的检测类型
   * <p>
   * <code>null</code>表示支持所有检测类型
   * </p>
   * 
   * @return 设备支持的检测类型
   */
  public int[] getSupportTypes() {
    String types = getString(PrefKeys.SUPPORT_TYPES);

    if (!TextUtils.isEmpty(types)) {
      String parts[] = types.split(",");
      int SIZE = parts.length;
      int result[] = new int[SIZE];

      for (int i = 0; i < SIZE; ++i) {
        result[i] = Integer.parseInt(parts[i], 10);
      }
      return result;
    }
    return getBoolean(PrefKeys.SUPPORT_CONFIG) ? null : DEFAULT_SUPPORT_TYPES;
  }

  /**
   * 设置设备支持的检测类型
   * 
   * @param supportTypes 设备支持的检测类型
   */
  public synchronized void setSupportTypes(int... supportTypes) {
    if (supportTypes != null && supportTypes.length != 0) {
      StringBuilder sb = mStringBuilder;
      sb.delete(0, sb.length());

      for (int type : supportTypes) {
        sb
          .append(type)
          .append(',');
      }
      sb.delete(sb.length() - 1, sb.length());
      saveString(PrefKeys.SUPPORT_TYPES, sb.toString());
    } else {
      saveString(PrefKeys.SUPPORT_TYPES, null);
    }
    saveBoolean(PrefKeys.SUPPORT_CONFIG, true);
  }


  /**
   * 获取登录模式
   * 
   * @return 登录模式
   */
  public final int getLogMode() {
    return getInt(PrefKeys.LOG_MODE, LogModes.IDENTITY);
  }

  /**
   * 设置登录模式
   * 
   * @param logMode 登录模式
   */
  public final void setLogMode(int logMode) {
    if (logMode != LogModes.PHONE) {
      logMode = LogModes.IDENTITY;
    }
    saveInt(PrefKeys.LOG_MODE, logMode);
  }
  /**
   * 获取登录账号
   * 
   * @return 登录账号
   */
  public final String getLogCount() {
    return getString(PrefKeys.LOG_COUNT);
  }

  /**
   * 设置登录账号
   * 
   * @param logCount 登录账号
   */
  public final void setLogCount(String logCount) {
    if (!TextUtils.isEmpty(logCount)) {
      saveString(PrefKeys.LOG_COUNT, logCount);
    }
  }


  /**
   * 获取最后一次访问者
   * 
   * @return 最后一次访问者
   */
  public final String getLastVisitor() {
    return getString(PrefKeys.LAST_VISITOR);
  }

  /**
   * 设置最后一次访问者
   * 
   * @param lastVisitor 最后一次访问者
   */
  public final void setLastVisitor(String lastVisitor) {
    saveString(PrefKeys.LAST_VISITOR, lastVisitor);
  }

  /**
   * 清空最后一次访问者
   */
  public final void clearLastVisitor() {
    clear(PrefKeys.LAST_VISITOR);
  }
  
  /**
   * 判断是否记住密码
   * 
   * @return 判断结果
   */
  public final boolean isRememberPassword() {
    return getBoolean(PrefKeys.FLAG_REMEMBER_PASSWORD, true);
  }

  /**
   * 设置记住密码
   * 
   * @param flag 记住密码标志
   */
  public final void setRememberPassword(boolean flag) {
    saveBoolean(PrefKeys.FLAG_REMEMBER_PASSWORD, flag);
  }

  /**
   * 判断是否自动登录
   * 
   * @return 判断结果
   */
  public final boolean isAutoLogin() {
    return getBoolean(PrefKeys.AUTO_LOGIN, true);
  }

  /**
   * 设置自动登录
   * 
   * @param flag 自动登录标志
   */
  public final void setAutoLogin(boolean flag) {
    saveBoolean(PrefKeys.AUTO_LOGIN, flag);
  }

  /**
   * 救助信息
   */
  public final void setAidInfo (String info) {
    saveString(PrefKeys.AID_INFO, info);
  }

  /**
   * 获取救助信息
   */
  public final String getAidInfo () {
    return getString(PrefKeys.AID_INFO);
  }

  /**
   * 新急救信息
   * @return
   */
  public final boolean getIsNewAidInfo() {
    return getBoolean(PrefKeys.NEW_AID_INFO);
  }

  /**
   * 新急救信息
   * @return
   */
  public final void setIsNewAidInfo(boolean isNew) {
    saveBoolean(PrefKeys.NEW_AID_INFO, isNew);
  }

  // 默认支持的检测类型
  private static final int DEFAULT_SUPPORT_TYPES[] = new int[] {
    DetectType.BLOOD_PRESSURE,
    DetectType.BLOOD_OXYGEN,
    DetectType.HEART_RATE,
    DetectType.TEMP
  };

  private final StringBuilder mStringBuilder = new StringBuilder();

}

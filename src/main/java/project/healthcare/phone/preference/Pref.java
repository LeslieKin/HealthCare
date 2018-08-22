package project.healthcare.phone.preference;

import com.wilimx.app.Preference;

public class Pref {

  /**
   * 获取应用首选项
   * 
   * @return 应用首选项
   */
  public static AppPreference getAppPreference() {
    return Preference.getInstance(AppPreference.class);
  }

  private Pref() {}

}

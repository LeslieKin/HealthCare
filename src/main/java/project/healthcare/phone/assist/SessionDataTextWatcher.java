package project.healthcare.phone.assist;

import com.wilimx.app.Session;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Session数据文本观察器
 * <p>当文本视图内容更改时，将文本内容保存到Session的指定键名称对应的键值中</p>
 * 
 * @author xiao.yl
 * created at 2014-05-23 18:34
 */
public final class SessionDataTextWatcher implements TextWatcher {

  /**
   * 初始化Session数据文本观察器
   * 
   * @param sessionKey Session键名称
   * @return Session数据文本观察器
   */
  public static final SessionDataTextWatcher init(String sessionKey) {
    if (!TextUtils.isEmpty(sessionKey)) {
      SessionDataTextWatcher watcher = new SessionDataTextWatcher();
      watcher.mSessionKey = sessionKey;
      return watcher;
    }
    return null;
  }

  @Override
  public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    Session.data.putString(mSessionKey, s.toString());
  }

  @Override
  public final void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public final void afterTextChanged(Editable s) {
  }

  private SessionDataTextWatcher() {}

  // Session 键名称
  private String mSessionKey = null;

}

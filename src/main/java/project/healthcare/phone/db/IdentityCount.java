package project.healthcare.phone.db;

import project.healthcare.phone.config.QueryColumns;

import com.wilimx.db.Entity;
import com.wilimx.db.annotation.table.ColumnName;
import com.wilimx.db.annotation.table.PrimaryKey;


/**
 * 身份证账号
 * 
 * @author xiao.yl
 * created at 2014-03-31 09:40
 */
public final class IdentityCount extends Entity {

  /**
   * 获取身份证号码
   * 
   * @return 身份证号码
   */
  public final String getIdentity() {
    return mIdentity;
  }

  /**
   * 获取密码
   * 
   * @return 密码
   */
  public final String getPassword() {
    return mPassword;
  }

  /**
   * 获取登录时间
   * 
   * @return 登录时间
   */
  public final long getLogTime() {
    return mLogTime;
  }

  /**
   * 设置身份证号码
   * 
   * @param  identity 身份证号码
   */
  public final void setIdentity(String identity) {
    mIdentity = identity;
  }

  /**
   * 设置密码
   * 
   * @param  password 密码
   */
  public final void setPassword(String password) {
    mPassword = password;
  }

  /**
   * 设置登录时间
   * 
   * @param  logTime 登录时间
   */
  public final void setLogTime(long logTime) {
    mLogTime = logTime;
  }

  // 身份证号码
  @PrimaryKey
  private String mIdentity;

  // 密码
  @ColumnName(QueryColumns.PASSWORD)
  private String mPassword;

  // 登录时间
  @ColumnName(QueryColumns.LOGTIME)
  private long mLogTime;

}

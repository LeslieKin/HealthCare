package project.healthcare.phone.db;

import java.util.List;

import project.healthcare.phone.config.QueryColumns;

import com.wilimx.constants.Constants;
import com.wilimx.db.QueryManager;
import com.wilimx.db.SortOrder;
import com.wilimx.db.annotation.DatabaseConfigType;
import com.wilimx.util.LogUtil;

import android.text.TextUtils;

/**
 * 身份证账号查询管理器
 * 
 * @author xiao.yl
 * created at 2014-03-31 10:06
 */
@DatabaseConfigType(UserInfoDatabaseConfig.class)
public final class IdentityCountQueryManager extends QueryManager<IdentityCount> {

  /**
   * 查找所有身份证账号
   * 
   * @return 所有身份证账号
   */
  public final List<IdentityCount> findAllIdentityCounts() {
    return edit()
      .addSortOrder(QueryColumns.LOGTIME, SortOrder.DESC)
      .query();
  }

  /**
   * 查找最后一个身份证账号
   * 
   * @return 最后一个身份证账号
   */
  public final IdentityCount findLastIdentityCount() {
    return edit()
      .addSortOrder(QueryColumns.LOGTIME, SortOrder.DESC)
      .queryUnique();
  }

  /**
   * 保存身份证账号
   * 
   * @param identity 身份证
   * @param password 密码
   */
  public final void saveIdentityCount(String identity, String password) {
    if (!TextUtils.isEmpty(identity)) {
      IdentityCount count = new IdentityCount();
      LogUtil.d("身份证号",identity);
      count.setIdentity(identity);
      count.setPassword(password);
      count.setLogTime(System.currentTimeMillis());
      save(count);
    }
  }

  /**
   * 清空所有身份证账号的密码
   */
  public final void clearAllPasswords() {
    edit()
      .addContentValue(QueryColumns.PASSWORD, Constants.EMPTY_TEXT)
      .update();
  }

  /**
   * 更新身份证账号
   * 
   * @param count 身份证账号
   */
  public final void updateIdentityCount(IdentityCount count) {
    if (count != null) {
      count.setLogTime(System.currentTimeMillis());
      update(count);
    }
  }

}

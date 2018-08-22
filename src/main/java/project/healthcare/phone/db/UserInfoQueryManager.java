package project.healthcare.phone.db;

import java.util.List;

import project.healthcare.phone.config.QueryColumns;
import project.healthcare.phone.constants.UserInfo;

import com.wilimx.db.QueryManager;
import com.wilimx.db.SortOrder;
import com.wilimx.db.annotation.DatabaseConfigType;

/**
 * 用户信息查询管理器
 * 
 * @author xiao.yl
 * created at 2014-04-04 16:25
 */
@DatabaseConfigType(UserInfoDatabaseConfig.class)
public final class UserInfoQueryManager extends QueryManager<UserInfo> {

  /**
   * 查找所有家庭成员
   * 
   * @return 所有家庭成员
   */
  public final UserInfo findMember(String identity) {
    return edit()
      .addCondition(QueryColumns.IDENTITY, identity)
      .queryUnique();
  }

  /**
   * 查找所有家庭成员
   * 
   * @return 所有家庭成员
   */
  public final List<UserInfo> findAllMembers() {
    return edit()
      .addSortOrder(QueryColumns.BIRTH, SortOrder.ASC)
      .query();
  }

}

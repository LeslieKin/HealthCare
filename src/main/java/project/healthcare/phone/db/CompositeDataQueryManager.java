package project.healthcare.phone.db;

import java.util.List;

import com.wilimx.db.QueryManager;
import com.wilimx.db.SortOrder;
import com.wilimx.db.annotation.DatabaseConfigType;

/**
 * 综合数据查询管理器
 * 
 * @author xiao.yl
 * created at 2014-07-30 16:00
 */
@DatabaseConfigType(UserInfoDatabaseConfig.class)
public class CompositeDataQueryManager extends QueryManager<CompositeData> {

  /**
   * 获取最近一次综合测量结果的得分
   * 
   * @return 最近一次综合测量结果的得分
   */
  public final int findLastestCompositeScore() {
    CompositeData data = edit()
      .addSortOrder("time", SortOrder.DESC)
      .queryUnique();

    if (data == null) {
      return 0;
    }
    return (int) data.getScore();
  }

  /**
   * 获取综合得分
   * 
   * @return float[] 综合得分
   */
  public final float[] findAllScoresFAsc() {
    List<CompositeData> dataList = edit()
      .addSortOrder("time", SortOrder.ASC)
      .query();

    if (dataList != null) {
      int size = dataList.size();
      float[] result = new float[size];

      for (int i = 0; i < size; ++i) {
        result[i] = (float) dataList.get(i).getScore();
      }
      return result;
    }
    return null;
  }

  /**
   * 获取最后一次综合测试的时间
   * @return
   */
  public final long findLastestCompositeTime() {
    CompositeData data = edit()
        .addSortOrder("time", SortOrder.DESC)
        .queryUnique();
    if (data == null) {
      return 0;
    }
    return data.getTime();
  }

}

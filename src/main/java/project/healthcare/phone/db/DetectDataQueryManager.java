package project.healthcare.phone.db;

import java.util.List;

import com.wilimx.db.QueryManager;
import com.wilimx.db.SortOrder;
import com.wilimx.db.annotation.DatabaseConfigType;

/**
 * 检测数据查询管理器
 * 
 * @author xiao.yl
 * created at 2014-07-30 16:02
 */
@DatabaseConfigType(UserInfoDatabaseConfig.class)
public class DetectDataQueryManager extends QueryManager<DetectData> {

  /**
   * 获取最后一次检测数据
   * @return
   */
  public DetectData findLastDetectData(int type) {
    return edit()
      .addSortOrder("time", SortOrder.DESC)
      .addCondition("type", type)
      .queryUnique();
  }

  /**
   * 查找所有检测数据（按时间从后往前排序）
   * 
   * @return 所有检测数据
   */
  public List<DetectData> findAllDetectData() {
    return edit()
      .addSortOrder("time", SortOrder.DESC)
      .query();
  }

  public List<DetectData> findAllDetectData(int type) {
    return edit()
      .addSortOrder("time", SortOrder.DESC)
      .addCondition("type", type)
      .query();
  }


  /**
   * 查找类型检测数据得分（按时间从前往后排序）
   * 
   * @return 类型检测数据得分
   */
  public float[] findAllDetectScoreAsc(int type) {
    List<DetectData> dataList=  edit()
      .addSortOrder("time", SortOrder.ASC)
      .addCondition("type", type)
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
   * 查找类型检测数据（按时间从前往后排序）
   * 
   * @return 类型检测数据
   */
  public float[][] findAllDetectDataAsc(int type) {
    
    List<DetectData> dataList = edit()
        .addSortOrder("time", SortOrder.ASC)
        .addCondition("type", type)
        .query();

    if (dataList != null) {
      if (dataList.size() != 0) {
        int LINESIZE = dataList.get(0).getMeasureValues().length;
        int COLUMNSIZE = dataList.size();

        float[][] result = new float[LINESIZE][COLUMNSIZE];

        for (int i = 0; i < LINESIZE; ++i) {
          for (int j = 0;j < COLUMNSIZE; ++j) {
            result[i][j] = (float) dataList.get(j).getMeasureValues()[i];
          }
        }
      return result;
      } else {
        return null;
      }
    }
    return null;
  }

  /**
   * 查找指定类型平均值
   */
  public int findAvgDetectData(int type) {
    int count = 0;
    int RVG = 0;
    List<DetectData> data = findAllDetectData(type);
    if (data.size() == 0) {
      return 0;
    } else {
      for (int i = 0; i < data.size() ;i++) {
        count += data.get(i).getScore();
      }
      RVG = count/data.size();
    }
    return RVG;
  }

}

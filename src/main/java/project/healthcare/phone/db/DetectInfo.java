package project.healthcare.phone.db;

import java.util.List;

/**
 * 检测数据
 * 
 * @author xiao.yl
 * created at 2014-04-30 09:02
 */
public class DetectInfo {

  /**
   * 获取综合信息列表
   * 
   * @return 综合信息列表
   */
  public final List<CompositeData> getCompositeInfoList() {
    return mCompositeInfoList;
  }

  /**
   * 获取检测信息列表
   * 
   * @return 检测信息列表
   */
  public final List<DetectData> getDetectInfoList() {
    return mDetectInfoList;
  }

  /**
   * 设置综合信息列表
   * 
   * @param  compositeInfoList 综合信息列表
   */
  public final void setCompositeInfoList(List<CompositeData> compositeInfoList) {
    mCompositeInfoList = compositeInfoList;
  }

  /**
   * 设置检测信息列表
   * 
   * @param  detectInfoList 检测信息列表
   */
  public final void setDetectInfoList(List<DetectData> detectInfoList) {
    mDetectInfoList = detectInfoList;
  }

  // 综合信息列表
  private List<CompositeData> mCompositeInfoList;

  // 检测信息列表
  private List<DetectData> mDetectInfoList;

}

package project.healthcare.phone.db;

import com.wilimx.db.Entity;

/**
 * 综合数据
 * 
 * @author xiao.yl
 * created at 2014-07-30 15:36
 */
public class CompositeData extends Entity {

  /**
   * 获取检测地点
   * 
   * @return 检测地点
   */
  public final String getPlace() {
    return mPlace;
  }

  /**
   * 设置检测地点
   * 
   * @param place
   *          检测地点
   */
  public final void setPlace(String place) {
    mPlace = place;
  }

  /**
   * 获取检测时间
   * 
   * @return 检测时间
   */
  public final long getTime() {
    return mTime;
  }

  /**
   * 设置检测时间
   * 
   * @param time
   *          检测时间
   */
  public final void setTime(long time) {
    mTime = time;
  }

  /**
   * 获取得分
   * 
   * @return 得分
   */
  public final double getScore() {
    return mScore;
  }

  /**
   * 设置得分
   * 
   * @param score
   *          得分
   */
  public final void setScore(double score) {
    mScore = score;
  }

  /**
   * 获取健康状态ID
   * 
   * @return 健康状态ID
   */
  public final int getStateId() {
    return mStateId;
  }

  /**
   * 设置健康状态ID
   * 
   * @param stateId
   *          健康状态ID
   */
  public final void setStateId(int stateId) {
    mStateId = stateId;
  }

  /**
   * 获取评语
   * 
   * @return 评语
   */
  public final String getComment() {
    return mComment;
  }

  /**
   * 设置评语
   * 
   * @param comment
   *          评语
   */
  public final void setComment(String comment) {
    mComment = comment;
  }

  /**
   * 获取建议
   * 
   * @return 建议
   */
  public final String getSuggest() {
    return mSuggest;
  }

  /**
   * 设置建议
   * 
   * @param suggest
   *          建议
   */
  public final void setSuggest(String suggest) {
    mSuggest = suggest;
  }

  // 检测地点
  private String mPlace;

  // 检测时间
  private long mTime;

  // 得分
  private double mScore;

  // 健康状态ID
  private int mStateId;

  // 评语
  private String mComment;

  // 建议
  private String mSuggest;

}

package project.healthcare.phone.config;

import android.util.SparseArray;

import project.healthcare.phone.R;
import project.healthcare.phone.constants.HealthStateType;

/**
 * 健康状态配置枚举常量
 * 
 * @author xiao.yl
 * created at 2014-07-29 11:09
 */
public enum HealthStateConfig {

  /**
   * 良好
   */
  GOOD(
    HealthStateType.GOOD,
    R.string.state_good),

  /**
   * 一般
   */
  NORMAL(
    HealthStateType.NORMAL,
    R.string.state_normal),

  /**
   * 差
   */
  POOL(
    HealthStateType.POOL,
    R.string.state_pool);

  /**
   * 获取健康状态类型配置
   * 
   * @param typeKey 类型键名称
   * @return 健康状态类型配置
   * 
   * HealthStateType#GOOD
   * HealthStateType#NORMAL
   * HealthStateType#POOL
   */
  public static final HealthStateConfig get(int typeKey) {
    return _instanceMap.get(typeKey);
  }

  /**
   * 获取标题资源ID
   * 
   * @return 标题资源ID
   */
  public final int getTitleRes() {
    return mTitleRes;
  }

  /**
   * 获取类型键名称
   * 
   * @return 类型键名称
   */
  public final int getTypeKey() {
    return mTypeKey;
  }

  /*
   * 构造函数
   * 
   * @param typeKey 类型键名称
   * @param titleRes 标题资源ID
   */
  private HealthStateConfig(int typeKey, int titleRes) {
    mTypeKey = typeKey;
    mTitleRes = titleRes;
  }


  // 实例映射
  private static final SparseArray<HealthStateConfig> _instanceMap;
  static {
    _instanceMap = new SparseArray<HealthStateConfig>();

    for (HealthStateConfig config : values()) {
      _instanceMap.put(config.getTypeKey(), config);
    }
  }

  // 类型键名称
  private final int mTypeKey;

  // 标题资源ID
  private final int mTitleRes;

}

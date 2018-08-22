package project.healthcare.phone.config;

import android.util.SparseArray;
import project.healthcare.phone.R;
import project.healthcare.phone.constants.DetectType;

/**
 * 检测类型配置枚举常量
 * 
 */
public enum DetectTypeConfig{
  
  /**
   * 血压
   */
  BLOOD_PRESSURE(
    DetectType.BLOOD_PRESSURE,
    R.string.blood_pressure,
    R.drawable.icon_blood_pressure,
    R.string.format_blood_pressure,
    R.string.format_current_blood_pressure),

  /**
   * 血糖
   */
  BLOOD_SUGAR(
    DetectType.BLOOD_SUGAR,
    R.string.blood_sugar,
    R.drawable.icon_blood_sugar,
    R.string.format_blood_sugar,
    R.string.format_current_blood_sugar),

  /**
   * 血氧
   */
  BLOOD_OXYGEN(
    DetectType.BLOOD_OXYGEN,
    R.string.blood_oxygen,
    R.drawable.icon_oxygen_blood,
    R.string.format_blood_oxygen,
    R.string.format_current_blood_oxygen),

  /**
   * 心率
   */
  HEART_RATE(
    DetectType.HEART_RATE,
    R.string.heart_rate,
    R.drawable.icon_heart_rate,
    R.string.format_heart_rate,
    R.string.format_current_heart_rate),

  /**
   * 体温
   */
  TEMP(
    DetectType.TEMP,
    R.string.temp,
    R.drawable.icon_temperture,
    R.string.format_temperture,
    R.string.format_current_blood_temp),

  /**
   * 体重
   */
  WEIGHT(
    DetectType.WEIGHT,
    R.string.weight,
    R.drawable.icon_weight,
    R.string.format_weight,
    R.string.format_current_blood_weight);

  /**
   * 获取检测类型配置
   * 
   * @param typeKey 类型键名称
   * @return 检测类型配置
   * 
   * @see DetectType#BLOOD_PRESSURE
   * @see DetectType#BLOOD_SUGAR
   * @see DetectType#BLOOD_OXYGEN
   * @see DetectType#HEART_RATE
   * @see DetectType#TEMP
   * @see DetectType#WEIGHT
   */
  public static final DetectTypeConfig get(int typeKey) {
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
  
  /**
   * 获取类型字体名字
   * 开始检测界面的检测类型?
   * @return 类型键名称
   */
  //public final int getTextDrawableRes() {
  //  return mTextDrawableRes;
  //}
  /**
   * 获取类型图标
   * @return
   */
  public final int getIconRes() {
    return mIcon;
  }

  /**
   * 获取提示格式化
   */
  public final int getTipFormatRes() {
    return mTipRes;
  }

  /**
   * 获取
   * @return
   */
  public final int getCurrentValueRes() {
    return mCurrentValueRes;
  }

  /*
   * 构造函数
   * 
   * @param typeKey 类型键名称
   * @param titleRes 标题资源ID
   */
  DetectTypeConfig(int typeKey, int titleRes, int iconRes, int tipRes, int currentValueRes) {
    mTypeKey = typeKey;
    mTitleRes = titleRes;
    mIcon = iconRes;
    mTipRes = tipRes;
    mCurrentValueRes = currentValueRes;
  //  mTextDrawableRes=textDrawableRes;
  }


  // 实例映射
  private static final SparseArray<DetectTypeConfig> _instanceMap;
  static {
    _instanceMap = new SparseArray<DetectTypeConfig>();

    for (DetectTypeConfig config : values()) {
      _instanceMap.put(config.getTypeKey(), config);
    }
  }

  // 类型键名称
  private final int mTypeKey;

  // 标题资源ID
  private final int mTitleRes;

  //图标
  private final int mIcon;

  //提示格式化（XX综合得分XX分）
  private final int mTipRes;
  
  //当前XX值
  private final int mCurrentValueRes;

 //字体资源ID
 //private final int mTextDrawableRes;
 
}

package project.healthcare.phone.measure;

import android.util.SparseArray;

import project.healthcare.phone.constants.DetectType;

/**
 * 测量组件工厂
 * 
 * @author xiao.yl
 * created at 2014-08-02 10:31
 */
public class MeasureFactory {

  /**
   * 获取测量组件
   * 
   * @param detectType 检测类型
   */
  public static final MeasureCompat getMeasureCompat(int detectType) {
    MeasureCompat instance = _instanceCache.get(detectType);

    if (instance == null) {
      switch (detectType) {
      case DetectType.BLOOD_PRESSURE:
      case DetectType.BLOOD_OXYGEN:
      case DetectType.TEMP:
      case DetectType.HEART_RATE:
        instance = new EhealthBluetoothMeasureCompat();
        break;

      case DetectType.BLOOD_SUGAR:
        instance = new EpsMeasureCompat();
        break;
      }
      _instanceCache.put(detectType, instance);
    }
    return instance;
  }

  /**
   * 销毁测量组件
   * 
   * @param detectType 检测类型
   */
  public static final void destroyMeasureCompat(int detectType) {
    _instanceCache.remove(detectType);
  }

  // 测量组件实例缓存
  private static final SparseArray<MeasureCompat> _instanceCache = new SparseArray<MeasureCompat>();

}

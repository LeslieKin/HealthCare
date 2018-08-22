package project.healthcare.phone.measure;

import extend.wilimx.device.DeviceClient.OnConnectFinishListener;

/**
 * 检测组件
 * 
 * @author xiao.yl
 * created at 2014-07-29 07:43
 */
public interface MeasureCompat {

  /**
   * 测量完成监听器
   */
  public static interface OnMeasureFinishListener {
    /**
     * 测量完成回调
     * 
     * <pre>Data Sample:
     * 
     * BLOOD_PRESSURE : [
     *   "",             // [double] 收缩压(systolic )
     *   ""              // [double] 舒张压(diastolic)
     * ],
     * BLOOD_SUGAR  : "", // [double] 血糖值
     * BLOOD_OXYGEN : "", // [double] 血氧值
     * TEMP         : "", // [double] 体温
     * WEIGHT       : "", // [double] 体重
     * HEART_RATE   : "", // [double] 心率
     * 
     * </pre>
     * 
     * @param detectType 检测类型
     * @param data 数据
     */
    void onMeasureFinish(int detectType, Object data);
  }

  /**
   * 连接
   */
  void connect();

  /**
   * 开始测量
   * 
   * @param detectType 检测类型
   */
  void startMeasure(int detectType);

  /**
   * 停止测量
   */
  void stop();

  /**
   * 断开连接
   */
  void disConnect();

  /**
   * 设置连接完成监听器
   * 
   * @param listener 连接完成监听器
   */
  void setOnConnectFinishListener(OnConnectFinishListener listener);

  /**
   * 设置测量完成监听器
   * 
   * @param listener 测量完成监听器
   */
  void setOnMeasureFinishListener(OnMeasureFinishListener listener);

}

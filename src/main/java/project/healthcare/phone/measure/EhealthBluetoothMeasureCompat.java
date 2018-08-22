package project.healthcare.phone.measure;

import extend.wilimx.bluetooth.BluetoothClient;

/**
 * Ehealth测量组件
 * 
 * @author xiao.yl
 * created at 2014-07-31 20:19
 */
public class EhealthBluetoothMeasureCompat extends BluetoothClient implements MeasureCompat {

  public EhealthBluetoothMeasureCompat() {
    setDeviceNamePattern("E-Health");
  }

  @Override
  public void startMeasure(int detectType) {
    mMeasureClient.startMeasure(detectType);
  }

  @Override
  public void stop() {
  }

  @Override
  public void setOnMeasureFinishListener(OnMeasureFinishListener listener) {
    mMeasureClient.setOnMeasureFinishListener(listener);
  }

  @Override
  protected void onConnectFinish() {
    mMeasureClient.setTransferAssist(getTransferAssist());
  }

  // 测量客户端
  private EhealthClient mMeasureClient = new EhealthClient();

}

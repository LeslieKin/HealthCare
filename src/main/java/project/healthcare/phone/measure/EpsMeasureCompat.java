package project.healthcare.phone.measure;

import java.io.IOException;

import project.healthcare.phone.constants.DetectType;

import me.lexjoy.bluetooth.BluetoothClient;
import me.lexjoy.utils.GlobalUtils;

import extend.wilimx.device.DeviceClient.OnConnectFinishListener;

public class EpsMeasureCompat implements MeasureCompat {

  public EpsMeasureCompat() {
    this.client = new BluetoothClient("EPS-GLUCOMETER");
  }

  @Override
  public void connect() {
    //new ConnectThread().start();
    //IMPORTANT: eps client could be connected only when it kept the
    // measure result on screen(after measure finished).
    // If its measure has not finished, we could never build a connection.

    // post connect finish directly
    if (this.connectListener == null) {
      return;
    }
    this.connectListener.onConnectSuccess();
  }

  @Override
  public void startMeasure(int detectType) {
    new MeasureThread().start();
  }

  @Override
  public void stop() {
    this.client.close();
  }

  @Override
  public void disConnect() {
    this.client.close();
  }

  @Override
  public void setOnConnectFinishListener(OnConnectFinishListener listener) {
    this.connectListener = listener;
  }

  @Override
  public void setOnMeasureFinishListener(OnMeasureFinishListener listener) {
    this.measureListener = listener;
  }

//  private void doDeviceConnect() {
//    final boolean IS_CONNECT_SUCCESS = this.client.open();
//    final OnConnectFinishListener listener = this.connectListener;
//
//    if (listener == null) {
//      return;
//    }
//    GlobalUtils.postRunner(new Runnable() {
//      @Override
//      public void run() {
//        if (IS_CONNECT_SUCCESS) {
//          listener.onConnectSuccess();
//        } else {
//          listener.onConnectFailed(-1);
//        }
//      }
//    });
//  }

  private synchronized void doDeviceMeasure() {
    final OnMeasureFinishListener listener = this.measureListener;

    if (listener == null) {
      return;
    }

    BluetoothClient client = this.client;
    byte[] data = null;

    if (client.open()) {
      try {
        client.write(new byte[]{(byte) 0x55});
        data = client.read();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        client.close();
      }
    }
    final double[] GLUCOSE_VALUE = readGlucose(data);

    GlobalUtils.postRunner(new Runnable() {
      @Override
      public void run() {
        listener.onMeasureFinish(DetectType.BLOOD_SUGAR, GLUCOSE_VALUE);
      }
    });
  }

  private static double[] readGlucose(byte[] data) {
    if (data == null || data.length < MIN_GLUCOSE_DATA_LENGTH) {
      return null;
    }
    return new double[] {readFackHexValue(data[5], data[6]) / 18D}; // mg/dl -> mmol/l
  }

  private static int readFackHexValue(byte high, byte low) {
    return readFackHexValue(high) * 100 + readFackHexValue(low);
  }

  private static int readFackHexValue(byte b) {
    int hexByte = b & 0xFF;
    return (hexByte >>> 4) * 10 + (hexByte & 0x0F);
  }

  private static final int MIN_GLUCOSE_DATA_LENGTH = 13;

  private final BluetoothClient client;

  private OnConnectFinishListener connectListener;

  private OnMeasureFinishListener measureListener;

//  private class ConnectThread extends Thread {
//    @Override
//    public void run() {
//      EpsMeasureCompat.this.doDeviceConnect();
//    }
//  }

  private class MeasureThread extends Thread {
    @Override
    public void run() {
      EpsMeasureCompat.this.doDeviceMeasure();
    }
  }

}

package project.healthcare.phone.measure;

import java.io.IOException;
import project.healthcare.phone.constants.DetectType;
import project.healthcare.phone.measure.MeasureCompat.OnMeasureFinishListener;
import project.healthcare.phone.measure.ehealth.BloodOxygenFinder;
import project.healthcare.phone.measure.ehealth.ContentFinder;
import project.healthcare.phone.measure.ehealth.HeartRateFinder;
import project.healthcare.phone.measure.ehealth.MsgReader;
import project.healthcare.phone.measure.ehealth.NibpFinder;
import project.healthcare.phone.measure.ehealth.PackageFinder;
import project.healthcare.phone.measure.ehealth.TempFinder;
import com.wilimx.app.Session;
import com.wilimx.assist.IOAssist;
import com.wilimx.assist.TaskManager;
import com.wilimx.util.LogUtil;
import com.wilimx.util.TextUtil;

/**
 * Ehealth客户端
 * 
 * @author xiao.yl
 * created at 2014-08-04 17:08
 */
public class EhealthClient {

  /**
   * 数据包类型常量接口
   * 
   * @author xiao.yl
   * created at 2014-08-04 17:08
   */
  public static interface Package {
    byte PACIENT_INFO = 0x11;
    byte MEASURE_DATA = 0x12;
    byte ALARM_SWITCH = 0x20;
    byte TECH_ALARM   = 0x21;
    byte CONTROL      = 0x50;
    byte USER_INFO    = 0x23;
  }

  /**
   * 监护数据类型常量接口
   * 
   * @author xiao.yl
   * created at 2014-08-04 17:09
   */
  public static interface Measure {
    byte ECG  = 0x0A;
    byte NIBP = 0x0C;
    byte SPO2 = 0x0D;
    byte TEMP = 0x0E;
  }

  /**
   * 设置测量完成监听器
   * 
   * @param listener 测量完成监听器
   */
  public void setOnMeasureFinishListener(OnMeasureFinishListener listener) {
    mMeasureFinishListener = listener;
  }

  /**
   * 设置数据传输辅助
   * 
   * @param assist 数据传输辅助
   */
  public void setTransferAssist(IOAssist assist) {
    mTransferAssit = assist;
  }

  /**
   * 开始测量
   * 
   * @param detectType 检测类型
   */
  public void startMeasure(int detectType) {
    if (mTaskManager.beginTask()) {
      new MeasureThread(detectType).start();
    }
  }

  /*
   * 测量检测数据
   * 
   * @param detectType 检测类型
   * @param finder 数据包查找器
   */
  private final void measureData(int detectType, PackageFinder<?> finder) throws IOException {
    if (finder == null || !findContent(mUserInfoFinder, false)) {
      postMeasureFinish(detectType, null);
      return;
    }
    findContent(finder, null);
    postMeasureFinish(detectType, finder.getResult());
  }

  /*
   * 发布测量完成事件
   * 
   * @param detectType 检测类型
   * @param data 数据
   */
  private final void postMeasureFinish(final int detectType, final Object data) {
    monitorMeasureResult(detectType, data);
    if (mMeasureFinishListener != null) {
      Session.getHandler().post(new Runnable() {
        @Override
        public void run() {
          mMeasureFinishListener.onMeasureFinish(detectType, data);
        }
      });
    }
  }

  private<T> T findContent(ContentFinder<T> finder, T defValue) {
    IOAssist assist = mTransferAssit;

    if (assist == null) {
      return defValue;
    }
    finder.reset();

    MsgReader reader = new MsgReader(assist.getInputStream());

    try {
      byte[] msg = null;

      while(true) {
        msg = reader.read();
        LogUtil.i(TAG, "receive message:\n" + TextUtil.readHexContent(msg));

        // find content
        if (msg == null) {
          return defValue;
        } else {
          finder.find(msg);

          if (finder.hasFound()) {
            return finder.getResult();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      reader.close();
    }
    return defValue;
  }

  private static boolean isPackage(byte header[], byte packageType) {
    return header[3] == packageType;
  }

  private final void monitorMeasureResult(int detectType, final Object data) {
    double result[] = (double[]) data;

    switch (detectType) {
    case DetectType.BLOOD_PRESSURE:
      if (data != null) {
        LogUtil.i(TAG, "------ >> measure result --\nsystolic: " + result[0]
            + " mmHg\ndiastolic: " + result[1] + " mmHg\n:~ ------");
      }
      break;

    case DetectType.BLOOD_OXYGEN:
      if (data != null) {
        LogUtil.i(TAG, "------ >> measure result --\nblood oxygen: " + result[0]
            + " %\n:~ ------");
      }
      break;

    case DetectType.TEMP:
      if (data != null) {
        LogUtil.i(TAG, "------ >> measure result --\nblood oxygen: " + result[0]
            + " ℃\n:~ ------");
      }
      break;

    case DetectType.HEART_RATE:
      if (data != null) {
        LogUtil.i(TAG, "------ >> measure result --\nblood oxygen: " + result[0]
            + " bmp\n:~ ------");
      }
      break;
    }
  }

  private static final String TAG = "ehealth";

  // 测量完成监听器
  private OnMeasureFinishListener mMeasureFinishListener = null;

  // 任务管理器
  private TaskManager mTaskManager = new TaskManager();

  private IOAssist mTransferAssit = null;

  private UserInfoFinder mUserInfoFinder = new UserInfoFinder();

  /*
   * 测量线程
   */
  private class MeasureThread extends Thread {

    private MeasureThread(int detectType) {
      mDetectType = detectType;
    }

    @Override
    public void run() {
      try {
        PackageFinder<?> finder = null;

        switch (mDetectType) {
        case DetectType.BLOOD_PRESSURE:
          finder = new NibpFinder();
          break;

        case DetectType.BLOOD_OXYGEN:
          finder = new BloodOxygenFinder();
          break;

        case DetectType.TEMP:
          finder = new TempFinder();
          break;

        case DetectType.HEART_RATE:
          finder = new HeartRateFinder();
          break;
        }
        measureData(mDetectType, finder);
      } catch (IOException e) {
        e.printStackTrace();
      }
      mTaskManager.finishTask();
      LogUtil.d("测量结束","请看检测结果请看检测结果");
    }
    private int mDetectType = -1;
  }

  private static class UserInfoFinder implements ContentFinder<Boolean> {
    @Override
    public void find(byte[] data) {
      if (data != null && data.length > 4 && isPackage(data, Package.USER_INFO)) {
        mHasUserInfo = true;
      }
    }

    @Override
    public void reset() {
      mHasUserInfo = false;
    }

    @Override
    public boolean hasFound() {
      return mHasUserInfo;
    }

    @Override
    public Boolean getResult() {
      return mHasUserInfo;
    }

    private boolean mHasUserInfo;
  }

}

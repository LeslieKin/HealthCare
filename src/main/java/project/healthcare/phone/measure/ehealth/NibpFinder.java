package project.healthcare.phone.measure.ehealth;

import project.healthcare.phone.measure.EhealthClient;
import com.wilimx.util.LogUtil;
import com.wilimx.util.TextUtil;

/**
 * 无创血压数据查找器
 * 
 * @author xiao.yl
 * created at 2014-08-04 16:00
 */
public class NibpFinder extends PackageFinder<double[]> {

  @Override
  protected boolean isTargetPackage(byte[] msg) {
    if (msg[3] == EhealthClient.Package.CONTROL) {
      LogUtil.i("", "><><><>: pick package [nibp control]");
      LogUtil.i("", "------ x -- find control package: " + TextUtil.readHexContent(msg));

      if (msg[4] == 0x13 && msg[5] == 0x03) {
        if (msg[6] == 0x01) {
          if (!mNibpRunningFound) {
            mNibpRunningFound = true;
          } else {
            mNibpStopFound = true;
          }
        }
      }
    } else if (msg[3] == EhealthClient.Package.MEASURE_DATA && mNibpStopFound && msg[4] == EhealthClient.Measure.NIBP) {
      LogUtil.i("", "><><><>: pick package [nibp]");
      return true;
    }
    return false;
  }

  @Override
  protected double[] parseResult(byte[] msg) {
    int systolic  = EHReader.readInt(msg,  8); // 4 + 4
    int diastolic = EHReader.readInt(msg, 10); // 6 + 4

    if (systolic >= SYSTOLIC_MIN && systolic <= SYSTOLIC_MAX
        && diastolic >= DIASTOLIC_MIN && diastolic <= DIASTOLIC_MAX) {
      return new double[] {
        systolic ,
        diastolic
      };
    }
    return null;
  }


  // 正在测量数据包查找状态
  private boolean mNibpRunningFound = false;

  // 结束测量数据包查找状态
  private boolean mNibpStopFound = false;

  private static final int SYSTOLIC_MIN = 40;
  private static final int SYSTOLIC_MAX = 270;
  private static final int DIASTOLIC_MIN = 10;
  private static final int DIASTOLIC_MAX = 215;

}

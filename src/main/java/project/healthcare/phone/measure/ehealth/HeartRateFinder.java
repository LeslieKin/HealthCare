package project.healthcare.phone.measure.ehealth;

import java.util.ArrayList;
import java.util.List;

import project.healthcare.phone.measure.EhealthClient;
import com.wilimx.util.LogUtil;

public class HeartRateFinder extends PackageFinder<double[]> {

  @Override
  protected boolean isTargetPackage(byte[] msg) {

  	if (msg[4] == EhealthClient.Measure.SPO2) {
      LogUtil.i("", "><><><>: pick package [hreart rate]");
  		//根据协议解析测量数据
    	double d =EHReader.readHeartRate(msg);

			if (d >= HEART_RATE_MIN && d <= HEART_RATE_MAX) {
			  mDataList.add(d);
			  ++mDataCount;
			  mErrorCount = 0;
			} else {
			  ++mErrorCount;
			}

			//出现-1000次数大于1，或有效数据测量大于20次
			if (isDataError() || mDataCount >= MAX_DATA_COUNT) {
				return true;
			}
    }
    return false;
  }

  private boolean isDataError() {
    return mErrorCount >= MAX_ERROR_COUNT;
  }

  @Override
  protected double[] parseResult(byte[] msg) {
    //把数组中出现次数最多的做为该心率测量值
  	return isDataError() ? null : new double[] {EHReader.getMaxcounts(mDataList)};
  }

  //不合法的数据出现的次数
  private int mErrorCount = 0;

  //有效数据测试次数
  private int mDataCount = 0;

  //遇到不合法数据前，把合法的数据保留
  private List<Double> mDataList = new ArrayList<Double>();

  private static final int HEART_RATE_MIN = 0;
  private static final int HEART_RATE_MAX = 500;

  private static final int MAX_ERROR_COUNT = 3;
  private static final int MAX_DATA_COUNT = 30;

}
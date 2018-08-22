package project.healthcare.phone.measure.ehealth;

import java.util.ArrayList;
import java.util.List;

import project.healthcare.phone.measure.EhealthClient;
import com.wilimx.util.LogUtil;

public class TempFinder extends PackageFinder<double[]> {

  @Override
  protected boolean isTargetPackage(byte[] msg) {

    if (msg[3] == EhealthClient.Package.MEASURE_DATA && msg[4] == EhealthClient.Measure.TEMP) {
      LogUtil.i("", "><><><>: pick package [temp]");
    	double d =EHReader.readTemperature(msg);

			if (d >= TEMP_MIN && d <= TEMP_MAX) {
			  mDataList.add(d);
			  ++mDataCount;
			  mErrorCount = 0;
			} else {
			  ++mErrorCount;
			}

			//出现-1000次数大于1，或有效数据测量大于60次
			if (isDataError() || mDataCount > MAX_DATA_COUNT) {
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
    //把数组中出现次数最多的做为该体温测量值，并保留小数点后两位
  	return isDataError() ? null : new double[] {Math.round(EHReader.getMaxcounts(mDataList) * 100) * 0.01d};
  }

  //不合法的数据出现的次数
  private int mErrorCount = 0;

  //有效数据测试次数
  private int mDataCount = 0;

  //遇到不合法数据前，把合法的数据保留
  private List<Double> mDataList = new ArrayList<Double>();

  private static final int TEMP_MIN = 25;
  private static final int TEMP_MAX = 45;

  private static final int MAX_ERROR_COUNT = 3;
  private static final int MAX_DATA_COUNT = 60;

}

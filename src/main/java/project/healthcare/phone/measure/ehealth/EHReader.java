package project.healthcare.phone.measure.ehealth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wilimx.util.BinaryUtil;


public class EHReader {

  /**
   * 读取包长度
   * 
   * @param low 低位
   * @param high 高位
   * @return 包长度
   */
  public static final int readInt(byte low, byte high) {
    return BinaryUtil.toInteger(high) << 8 | BinaryUtil.toInteger(low);
  }

  public static final float readFloat(byte data[], int position) {
    position += 3;
    return parseFloat(
      data[position--],
      data[position--],
      data[position--],
      data[position  ]);
  }

  public static final float parseFloat(byte b3, byte b2, byte b1, byte b0) {
    return Float.intBitsToFloat(b3 << 24 | (b2 << 16 & 0xFF0000) | (b1 << 8 & 0xFF00) | (b0 & 0xFF));
  }

  public static final int readInt(byte data[], int position) {
    return readInt(data[position], data[position + 1]);
  }

  //血压
  public static final double[] readBloodPressure(byte data[]) {
    return new double[] {
      EHReader.readInt(data,  8), // 4 + 4
      EHReader.readInt(data, 10)  // 6 + 4
    };
  }
  
  //血氧
  public static final double readBloodOxygen(byte data[]) {
      return EHReader.readInt(data, 262); // 258 + 4
  }
  
  //心率
	public static final double readHeartRate(byte data[]) {
			return EHReader.readInt(data, 264); // 260 + 4
	}
  
  //体温
	public static final double readTemperature(byte data[]) {
			return EHReader.readFloat(data, 6); // 2 + 4
	}
	
	/**
   * 获取量测数据数组中出现最多的数
   * @param arr
   * @return
   */
  public static double getMaxcounts(List<Double> dataList){
		if (dataList.size() > 0) {
			double[] array = new double[dataList.size()];
			for (int i = 0, j = dataList.size(); i < j; i++) {
				array[i] = dataList.get(i);
			}

			// map的key存放数组中存在的数字，value存放该数字在数组中出现的次数
			HashMap<Double, Double> map = new HashMap<Double, Double>();

			for (int i = 0; i < array.length; i++) {
				if (map.containsKey(array[i])) {
					double temp = map.get(array[i]);

					map.put(array[i], temp + 1);
				} else {
					map.put(array[i], 1.0);
				}
			}

			Collection<Double> count = map.values();

			// 找出map的value中最大的数字，也就是数组中数字出现最多的次数
			double maxCount = Collections.max(count);

			double num = 0;

			for (Map.Entry<Double, Double> entry : map.entrySet()) {
				// 得到value为maxCount的key，也就是数组中出现次数最多的数字
				if (maxCount == entry.getValue()) {
					num = entry.getKey();
				}
			}

			return num;
		} else {
			return 0.0;
		}
    
  }

}

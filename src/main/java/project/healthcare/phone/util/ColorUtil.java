package project.healthcare.phone.util;

import android.graphics.Color;

public class ColorUtil {

  /**
   * 调整颜色亮度
   * 
   * @param color 颜色值
   * @param lumPercent 亮度百分比
   * @return 调整结果
   */
  public static final int adjustLum(int color, float lumPercent) {
    return Color.rgb(
      adjustLumByBit(Color.red  (color), lumPercent),
      adjustLumByBit(Color.green(color), lumPercent),
      adjustLumByBit(Color.blue (color), lumPercent));
  }

  private static final int adjustLumByBit(int colorBit, float lumPercent) {
    return (int) (colorBit * lumPercent);
  }

}

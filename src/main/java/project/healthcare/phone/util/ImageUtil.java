package project.healthcare.phone.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工具
 * 
 * @author xiao.yl
 * created at 2014-04-10 15:36
 */
public final class ImageUtil {

  /**
   * 生成位图
   * 
   * @param byteArray 字节数组
   * @return 位图
   */
  public static final Bitmap makeBitmap(byte... byteArray) {
    if (byteArray != null) {
      return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    return null;
  }

  private ImageUtil() {}

}

package project.healthcare.phone.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

/**
 * 图片处理工具类
 * @author Cai.wh
 *
 */
public class RoundImageUtils {

  /**
   * 图片缩放
   * 
   * @param pBitmap
   * @param pW
   * @param pH
   * @return
   */
  public static Bitmap zoomBitmap(Bitmap pBitmap, int pW, int pH) {
    // 获取原图的宽
    int bitmapWidth = pBitmap.getWidth();
    // 获取原图的高
    int bitmapHeigth = pBitmap.getHeight();
    // 获取缩放比
    float scaleWidth = (float) pW / bitmapWidth;

    float scaleHeight = (float) pH / bitmapHeigth;

    // 创建Matrix矩阵对象
    Matrix Matrix = new Matrix();
    // 设置宽高的缩放比
    Matrix.setScale(scaleWidth, scaleHeight);

    // 对截原图的0，0坐标到_width，_heigth的图片进行_Matrix处理
    return Bitmap.createBitmap(pBitmap, 0, 0, bitmapWidth, bitmapHeigth, Matrix, true);
  }

  /**
   * 背景圆角图片
   * @return 图片
   */
  public static Bitmap bgRoundedCornerBitmap() {
    // 创建图片画布大小
    Bitmap bitmap = Bitmap.createBitmap(250,
        250, Config.ARGB_8888);
    // 创建画布
    Canvas canvas = new Canvas(bitmap);
    // 设置画布透明
    canvas.drawARGB(0, 0, 0, 0);
    // 创建画笔
    Paint bgPaint = new Paint();
    bgPaint.setColor(0xFFe1eddf);
    bgPaint.setAntiAlias(true);
    bgPaint.setShadowLayer(13,0,0,Color.GRAY);
    // 画与原图片大小一致的圆角矩形
    //   Rect _Rect = new Rect(0, 0, pBitmap.getWidth(), pBitmap.getHeight());
    //   RectF _RectF = new RectF(_Rect);
    //   canvas.drawRoundRect(_RectF, pRoundpx, pRoundpx, paint);
    canvas.drawCircle(125, 125, 115, bgPaint);
    return bitmap;
  }

  public static Bitmap fgRoundedCornerBitmap(Bitmap pBitmap) {
    // 创建图片画布大小
    Bitmap bitmap = Bitmap.createBitmap(250,
        250, Config.ARGB_8888);
    // 创建画布
    Canvas canvas = new Canvas(bitmap);
    // 设置画布透明
    canvas.drawARGB(0, 0, 0, 0);
    // 创建画笔
    Paint circlePaint = new Paint();
    // 抗锯齿
    circlePaint.setAntiAlias(true);
    // 画笔颜色透明
    circlePaint.setColor(0xff000000);
    // 画与原图片大小一致的圆角矩形
    Rect Rect = new Rect(0, 0, pBitmap.getWidth(), pBitmap.getHeight());
 //   RectF _RectF = new RectF(_Rect);
 //   canvas.drawRoundRect(_RectF, pRoundpx, pRoundpx, paint);
    canvas.drawCircle(125, 125, 115, circlePaint);
    // 设置下面张图片与上面张图片的交互模式
    circlePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    //画原图到画布
      canvas.drawBitmap(pBitmap, Rect, Rect, circlePaint);
    return bitmap;
  }
}

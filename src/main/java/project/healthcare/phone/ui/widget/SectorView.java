package project.healthcare.phone.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public abstract class SectorView extends View{
  /**
   * 圆形一周包含的角度值
   */
  public static final float ROUND_ANGLE = 360;
  /**
   * 默认初始绘制角度
   */
  public static final float DEFAULT_START_ANGLE = -90;

  /**
   * 画笔
   */
  private static Paint mPaint = null;
  /**
   * 视图颜色
   */
  private int mColor = Color.BLACK;
  /**
   * 上一次的视图颜色
   */
  private int mLastColor = mColor;
  /**
   * 扇形半径（视图高度、宽度的最小值 / 2）
   */
  private float mRadius  = 0;
  /**
   * 中间点横坐标值
   */
  private float mCenterX = 0;
  /**
   * 中间点纵坐标值
   */
  private float mCenterY = 0;
  /**
   * 浮点矩形区域范围
   */
  private RectF mRectF = null;
  /**
   * 整型矩形区域范围
   */
  private Rect mRect = null;
  /**
   * 扇形的初始绘制角度
   */
  private float mStartAngle = DEFAULT_START_ANGLE;
  /**
   * 扇形区域包含的角度范围
   */
  private float mSweepAngle = 0;
  /**
   * 画布实例
   */
  private Canvas mCanvas = null;
  /**
   * 视图是否已经初始化完成
   */
  private boolean initialized = false;

  static {
    // 初始化画笔
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither   (true);
    mPaint.setStyle(Style.FILL);
  }

  /**
   * 构造函数
   * @param context 上下文
   * @param attrs 参数列表
   */
  public SectorView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 设置视图颜色
   * @param color 视图颜色
   */
  public void setColor(int color) {
    mColor = color;
  }

  /**
   * 设置扇形区域的起始角度位置
   * @param startAngle 扇形区域的起始角度位置
   */
  public void setStartAngle(float startAngle) {
    mStartAngle = startAngle;
  }

  /**
   * 设置扇形区域包含的角度范围
   * @param sweepAngle 扇形区域包含的角度范围
   */
  public void setSweepAngle(float sweepAngle) {
    mSweepAngle = sweepAngle;
  }

  /**
   * 设置百分比扇形区域包含的角度范围
   * @param percent 百分比
   */
  public void setPercentSweepAngle(float percent) {
    mSweepAngle = ROUND_ANGLE * percent;
  }

  /**
   * 追加扇形区域包含的角度范围
   * @param sweepAngle 追加角度范围
   */
  public void appendSweepAngle(float sweepAngle) {
    mSweepAngle += sweepAngle;
  }

  /**
   * 绘制视图回调
   */
  public void onDraw(Canvas canvas) {
    // 缓存画布实例
    mCanvas = canvas;

    // 初始化
    if (!initialized) {
      initialize();
      initialized = true;
    }

    // 判断视图颜色是否改变
    if (mColor != mLastColor) {
      onColorChange(mColor);
      mLastColor = mColor;
    }

    // 执行图形绘制
    execDraw(canvas);

    // 清空画布实例
    mCanvas = null;
  }

  /**
   * 绘制扇形区域
   */
  protected void drawSector() {
    drawSector(mRadius);
  }

  /**
   * 绘制扇形区域
   * @param radius 扇形区域半径
   */
  protected void drawSector(float radius) {
    mCanvas.drawArc(getRectF(radius), mStartAngle, mSweepAngle, true, mPaint);
  }

  /**
   * 绘制圆
   * @param radius 需要绘制的圆的半径
   */
  protected void drawCircle(float radius) {
    mCanvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
  }

  /**
   * 绘制图片
   * @param bitmap 图片的位图实例
   * @param radius 绘制半径
   */
  protected void drawPicture(Bitmap bitmap, float radius) {
    if (bitmap != null) {
      mCanvas.drawBitmap(bitmap, getRect(bitmap),
        getRectF(bitmap.getWidth() / 2F), mPaint);
    }
  }

  /**
   * 绘制图片
   * @param resId  图片的资源ID
   * @param radius 绘制半径
   */
  protected void drawPicture(int resId, float radius) {
    drawPicture(BitmapFactory.decodeResource(getResources(), resId), radius);
  }

  /**
   * 绘制百分比圆
   * @param percent 需要绘制的圆的半径百分比
   */
  protected void drawPercentCircle(float percent) {
    drawCircle(mRadius * percent);
  }

  /**
   * 获取画笔
   * @return 画笔
   */
  protected Paint getPaint() {
    return mPaint;
  }

  /**
   * 获取圆环矩形区域
   * @param radius 圆环半径
   * @return 圆环矩形区域
   */
  protected RectF getRectF(float radius) {
    mRectF.set(mCenterX - radius, mCenterY - radius,
               mCenterX + radius, mCenterY + radius);
    return mRectF;
  }

  /**
   * 获取位图的矩形区域范围
   * @param bitmap 需要获取矩形区域范围的位图
   * @return 矩形区域范围
   */
  private Rect getRect(Bitmap bitmap) {
    mRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    return mRect;
  }

  /**
   * 获取百分比圆环矩形区域
   * @param percent 百分比
   * @return 百分比圆环矩形区域
   */
  protected RectF getPercentRectF(float percent) {
    return getRectF(mRadius * percent);
  }

  /**
   * 获取中心点横坐标
   * @return 中心点横坐标
   */
  protected float getCenterX() {
    return mCenterX;
  }

  /**
   * 获取中心点纵坐标
   * @return 中心点纵坐标
   */
  protected float getCenterY() {
    return mCenterY;
  }

  /**
   * 获取圆心半径
   * @return 圆心半径
   */
  protected float getRadius() {
    return mRadius;
  }

  /**
   * 获取扇形区域颜色
   * @return 扇形区域颜色
   */
  protected int getColor() {
    return mColor;
  }

  /**
   * 扇形区域颜色改变回调
   * @param newColor 新颜色
   */
  protected void onColorChange(int newColor) {
    mPaint.setColor(newColor);
  }

  /**
   * 执行图形绘制
   * @param canvas 画布
   */
  protected abstract void execDraw(Canvas canvas);

  /**
   * 初始化
   */
  private void initialize() {
    mCenterX = getWidth () / 2F;
    mCenterY = getHeight() / 2F;
    mRadius  = Math.min(mCenterX, mCenterY);
    mRectF   = new RectF();
    mRect    = new Rect ();
  }
}

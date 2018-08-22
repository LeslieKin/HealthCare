package project.healthcare.phone.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Map;

import project.healthcare.phone.R;
import project.healthcare.phone.constants.ScoreState;

public class CompositeItemScoreBoard extends SectorView{
  /**
   * 最外圈圆面宽度
   */
  private static final float OUT_CIRCLE_WIDTH  = 6;
  /**
   * 圆环圆面宽度
   */
  private static final float RING_CIRCLE_WIDTH = 2;

  /**
   * 当前分数
   */
  private int score;

  /**
   * 构造函数
   * @param context 上下文
   * @param attrs 参数列表
   */
  public CompositeItemScoreBoard(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 设置检测评分
   * @param score 检测评分
   * @return 单项检测项目分数面板本身
   */
  public CompositeItemScoreBoard setScore(int score) {
    setPercentSweepAngle(score / 100F);
    setColor(_scoreColorMap.get(ScoreState.get(score)));
    this.score = score;
    invalidate();
    return this;
  }

  /*
   * 执行视图绘制
   */
  @Override
  protected void execDraw(Canvas canvas) {
    Paint paint = getPaint();

    // 绘制背景层外圆圆面
    paint.setColor(getContext().getResources().getColor(ConfigColors.OUT_CIRCLE_COLOR));
    this.drawCircle(getRadius());

    float ringCircleRadius = getRadius() - OUT_CIRCLE_WIDTH;

    // 绘制背景层圆环圆面
    paint.setColor(getContext().getResources().getColor(ConfigColors.RING_CIRCLE_COLOR));
    this.drawCircle(ringCircleRadius);

    // 绘制扇形区域
    paint.setColor(getResources().getColor(getColor()));
    this.drawSector(ringCircleRadius);

    float innerCircleRadius = ringCircleRadius - RING_CIRCLE_WIDTH;

    // 绘制前影层内圆圆面
    paint.setColor(getContext().getResources().getColor(ConfigColors.INNER_CIRCLE_COLOR));
    this.drawCircle(innerCircleRadius);

    // 绘制检测项目分数文本
    float textSize = getRadius() / 1.28F;
    if (isEnabled()) {
      paint.setColor(getResources().getColor(getColor()));
    } else {
      paint.setColor(getResources().getColor(R.color.composite_socre_item_state_text));
    }
    paint.setTextSize(textSize);
    paint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText(String.valueOf((int) score),
      getCenterX(), getCenterY() + textSize / 2.6F, paint);
  }

  /**
   * 配置颜色
   * @author xiao.yl
   * created at 2013-09-12 17:39
   */
  private static class ConfigColors {
    /**
     * 外圆圆面颜色
     */
    public static final int OUT_CIRCLE_COLOR  ;
    /**
     * 圆环圆面颜色
     */
    public static final int RING_CIRCLE_COLOR ;
    /**
     * 内圆圆面颜色
     */
    public static final int INNER_CIRCLE_COLOR;

    static {
      // 初始化颜色配置
      OUT_CIRCLE_COLOR   = R.color.item_score_board_out_circle  ;
      RING_CIRCLE_COLOR  = R.color.item_score_board_ring_circle ;
      INNER_CIRCLE_COLOR = R.color.item_score_board_inner_circle;
    }
  }

  private static final Map<ScoreState, Integer> _scoreColorMap;
  static {
    _scoreColorMap = new HashMap<ScoreState, Integer>();

    _scoreColorMap.put(ScoreState.FINE  , R.color.composite_score_fine  );
    _scoreColorMap.put(ScoreState.NORMAL, R.color.composite_score_normal);
    _scoreColorMap.put(ScoreState.POOR  , R.color.composite_score_poor  );
  }
}

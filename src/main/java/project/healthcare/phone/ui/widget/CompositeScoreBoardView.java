package project.healthcare.phone.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import project.healthcare.phone.R;
import project.healthcare.phone.constants.ScoreState;
import project.healthcare.phone.util.ColorUtil;

public class CompositeScoreBoardView extends View {

  public CompositeScoreBoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    updateShaderColor();
  }

  /**
   * 设置得分
   * 
   * @param score 得分
   */
  public void setScore(int score) {
    if (score < MIN_SCORE) {
      score = MIN_SCORE;
    } else if (score > MAX_SCORE) {
      score = MAX_SCORE;
    }
    if (score != mScore) {
      mScore = score;
      mSweepAngle = score * MAX_ANGLE / MAX_SCORE;
      checkScoreState(score);
      postInvalidate();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    checkSize();
    if (!mIsInit) {
      init();
      mIsInit = true;
    }
    canvas.drawArc(mRectF, START_ANGLE, mSweepAngle, true, mPaint);
  }

  private static final float[] makeGrayPercents(int... grayValues) {
    if (grayValues != null) {
      int SIZE = grayValues.length;
      int MAX_GRAY = 0xFF;
      float MAX_GRAY_F = (float) MAX_GRAY;
      int grayValue;
      float result[] = new float[SIZE];

      for (int i = 0; i < SIZE; ++i) {
        grayValue = grayValues[i];
        if (grayValue < 0) {
          grayValue = 0;
        } else if (grayValue > MAX_GRAY) {
          grayValue = MAX_GRAY;
        }
        result[i] = grayValue / MAX_GRAY_F;
      }
      return result;
    }
    return null;
  }

  private final void init() {
    mPaint.setDither(true);
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Paint.Style.FILL);
    updatePaintColor();
  }

  private final void checkSize() {
    int width = getWidth();
    int height = getHeight();

    if (width != mWidth || height != mHeight) {
      onSizeChange(width, height);
    }
  }

  private final void checkScoreState(int score) {
    ScoreState scoreState = ScoreState.get(score);

    if (scoreState != mScoreState) {
      mScoreState = scoreState;
      updateShaderColor();
      updateShader();
      updatePaintColor();
    }
  }

  private final int getCurrentColor() {
    return getContext().getResources().getColor(_scoreColorMap.get(mScoreState));
  }

  private final void onSizeChange(int newWidth, int newHeight) {
    mWidth = newWidth;
    mHeight = newHeight;
    mCenterX = newWidth / 2F;
    mCenterY = newHeight / 2F;
    mRadius = Math.min(newWidth, newHeight) / 2F;
    mRectF.set(
      mCenterX - mRadius,
      mCenterY - mRadius,
      mCenterX + mRadius,
      mCenterY + mRadius);
    updateShader();
  }

  private final void updateShader() {
//    mPaint.setShader(new RadialGradient(
//      mCenterX, mCenterY, 1.24F, mShaderColors, COLOR_POSITIONS, TileMode.CLAMP));
  }

  private final void updateShaderColor() {
    int baseColor = getCurrentColor();
    int shaderColors[] = new int[LUM_PERCENTS.length];

    for (int i = 0, size = shaderColors.length; i < size; ++i) {
      shaderColors[i] = ColorUtil.adjustLum(baseColor, LUM_PERCENTS[i]);
    }
    mShaderColors = shaderColors;
  }

  private final void updatePaintColor() {
    mPaint.setColor(getCurrentColor());
  }

  private static final int MIN_SCORE = 0;
  private static final int MAX_SCORE = 100;

  private static final float START_ANGLE = -90F;
  private static final float MAX_ANGLE   = 360F;

  private static final float LUM_PERCENTS[] = makeGrayPercents(
    0x00,
    0xEB,
    0x51
  );
  private static final float COLOR_POSITIONS[] = new float[] {
    0.56F,
    0.70F,
    0.90F
  };
  private static final Map<ScoreState, Integer> _scoreColorMap;
  static {
    _scoreColorMap = new HashMap<ScoreState, Integer>();

    _scoreColorMap.put(ScoreState.FINE  , R.color.composite_board_fine  );
    _scoreColorMap.put(ScoreState.NORMAL, R.color.composite_board_normal);
    _scoreColorMap.put(ScoreState.POOR  , R.color.composite_board_poor  );
  }

  private ScoreState mScoreState = ScoreState.get(0);

  private float mCenterX = 0;
  private float mCenterY = 0;
  private float mRadius = 0;
  private int mWidth  = 0;
  private int mHeight = 0;

  private RectF mRectF = new RectF();

  private float mSweepAngle = 0F;

  private Paint mPaint = new Paint();

  private int mShaderColors[];

  private boolean mIsInit = false;
  private int mScore = 0;

}
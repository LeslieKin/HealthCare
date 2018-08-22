package project.healthcare.phone.ui.widget;

import com.wilimx.app.Session;

import project.healthcare.phone.assist.StepRandom;

/**
 * 综合得分动画演示器
 * 
 * @author xiao.yl
 * created at 2014-08-07 10:32
 */
public class CompositeScoreAnimator {

  public CompositeScoreAnimator() {
    mStepRandom = new StepRandom();
    mStepRandom.setTurns(20);
  }

  /**
   * 设置目标视图
   * 
   * @param view 目标视图
   */
  public CompositeScoreAnimator setTargetView(CompositeScoreBoardView view) {
    mScoreView = view;
    return this;
  }

  /**
   * 设置得分
   * 
   * @param score 得分
   */
  public CompositeScoreAnimator setScore(int score) {
    mTargetScore = score;
    mStepRandom.setMaxIndex(score);
    return this;
  }

  /**
   * 演示动画
   */
  public void animate() {
    if (mScoreView != null) {
      if (mAnimRunnable != null) {
        Session.getHandler().removeCallbacks(mAnimRunnable);
      }
      mAnimScore = 0;
      mAnimRunnable = new AnimRunnable();
      mScoreView.setScore(0);
      onAnimScoreChange(0);
      Session.getHandler().postDelayed(mAnimRunnable, ANIM_DURATION);
    }
  }

  /**
   * 取消动画演示
   */
  public void cancel() {
    if (mAnimRunnable != null) {
      Session.getHandler().removeCallbacks(mAnimRunnable);
      mAnimRunnable = null;
    }
  }

  /**
   * 动画得分更改回调
   * 
   * @param animScore 动画得分
   */
  protected void onAnimScoreChange(int animScore) {
  }

  /*
   * 更新动画得分
   */
  private final void updateAnimScore() {
    int nextScore = mAnimScore + mStepRandom.getStep();

    if (nextScore > mTargetScore) {
      nextScore = mTargetScore;
    }
    onAnimScoreChange(nextScore);
    mAnimScore = nextScore;
  }

  private static final int ANIM_DURATION = 20;

  private CompositeScoreBoardView mScoreView = null;

  private int mAnimScore = 0;

  private int mTargetScore = 0;

  private AnimRunnable mAnimRunnable = null;

  private final StepRandom mStepRandom;

  private final class AnimRunnable implements Runnable {
    @Override
    public void run() {
      updateAnimScore();
      if (mScoreView != null) {
        mScoreView.setScore(mAnimScore);
      }
      if (mAnimScore < mTargetScore) {
        Session.getHandler().postDelayed(this, ANIM_DURATION);
      }
    }
  }
}

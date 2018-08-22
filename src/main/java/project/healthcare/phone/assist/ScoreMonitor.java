package project.healthcare.phone.assist;

import project.healthcare.phone.constants.ScoreState;

/**
 * 得分监视器
 * 
 * @author xiao.yl
 * created at 2014-08-07 11:16
 */
public abstract class ScoreMonitor {

  /**
   * 更新得分
   * 
   * @param score 得分
   */
  public synchronized void updateScore(int score) {
    ScoreState state = ScoreState.get(score);

    if (mScoreState != state) {
      onStateChange(state);
      mScoreState = state;
    }
  }

  /**
   * 重置
   */
  public void reset() {
    mScoreState = null;
  }

  /**
   * 得分状态更改回调
   * 
   * @param newScoreState 新得分状态
   */
  protected abstract void onStateChange(ScoreState newScoreState);

  // 得分状态
  private ScoreState mScoreState = null;

}

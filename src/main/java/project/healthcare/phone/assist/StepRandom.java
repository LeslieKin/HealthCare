package project.healthcare.phone.assist;

public class StepRandom {

  /**
   * 设置step生成次数
   * 
   * @param turns step生成次数
   */
  public void setTurns(int turns) {
    if (turns > 0) {
      mTurns = turns;
    }
  }

  /**
   * 设置最大生成索引
   * 
   * @param index 最大生成索引
   */
  public void setMaxIndex(int index) {
    if (index >= 0) {
      mMaxIndex = index;
    }
  }

  /**
   * 获取step
   * 
   * @return step
   */
  public int getStep() {
    if (!mRandomStart) {
      init();
      mRandomStart = true;
    }
    ++mSpeedUpIndex;
    if (mSpeedUpIndex == mSpeedUpLength) {
      mSpeedUpIndex = 0;
      ++mStep;
    }
    if (mStep > mLeftIndex) {
      mStep = mLeftIndex;
    }
    mLeftIndex -= mStep;
    return mStep;
  }

  /**
   * 重置
   */
  public void reset() {
    mRandomStart = false;
  }

  /*
   * 初始化
   */
  private final void init() {
    mLeftIndex = mMaxIndex;

    if (mTurns != 0) {
      mStep = mMaxIndex / mTurns;

      if (mSpeedUpTurns != 0) {
        mStep -= mSpeedUpTurns / 2;
        mSpeedUpLength = mTurns / mSpeedUpTurns;
      } else {
        mSpeedUpLength = mMaxIndex;
      }
    } else {
      mStep = mMaxIndex;
      mSpeedUpLength = mMaxIndex;
    }
    mSpeedUpIndex = 0;
  }

  private int mStep  = 0;
  private int mTurns = 0;

  private int mSpeedUpTurns = 0;

  private int mSpeedUpLength = 0;
  private int mSpeedUpIndex  = 0;

  private int mLeftIndex = 0;
  private int mMaxIndex  = 0;

  private boolean mRandomStart = false;

}

package project.healthcare.phone.measure.ehealth;

import com.wilimx.util.LogUtil;

/**
 * 数据包查找器
 * 
 * @param <RESULT_TYPE> 结果类型
 * 
 * @author xiao.yl
 * created at 2014-08-04 15:17
 */
public abstract class PackageFinder<RESULT_TYPE> implements ContentFinder<RESULT_TYPE> {

  /**
   * 查找数据
   * 
   * @param data 数据包
   */
  public void find(byte[] msg) {
    if (!hasFound() && isTargetPackage(msg)) {
      LogUtil.i("", "><><><>: find target package");
      mFinalData = msg;
    }
  }

  /**
   * 判断最终数据是否查找完成
   * 
   * @return 判断结果
   */
  public boolean hasFound() {
    return mFinalData != null;
  }

  /**
   * 获取最终数据
   * 
   * @return 最终数据
   */
  public byte[] getFinalData() {
    return mFinalData;
  }

  /**
   * 获取数据结果
   * 
   * @return 数据结果
   */
  public RESULT_TYPE getResult() {
    if (mFinalData != null) {
      return parseResult(mFinalData);
    }
    return null;
  }

  /**
   * 重置
   */
  public void reset() {
    mFinalData = null;
  }

  /**
   * 判断数据包是否为最终数据包
   * 
   * @param data 数据包
   * @return 判断结果
   */
  protected abstract boolean isTargetPackage(byte[] msg);

  /**
   * 解析数据结果
   * 
   * @param data 数据包
   * @return 数据结果
   */
  protected abstract RESULT_TYPE parseResult(byte[] msg);

  // 最终数据
  private byte[] mFinalData = null;

}

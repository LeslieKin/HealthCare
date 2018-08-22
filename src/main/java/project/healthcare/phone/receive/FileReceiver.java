package project.healthcare.phone.receive;

import com.wilimx.http.ByteArrayDataReceiver;

/**
 * 字符串数据接收器
 * 
 * @author xiao.yl
 * created at 2014-06-07 10:50
 */
public abstract class FileReceiver<D> extends ByteArrayDataReceiver {

  /**
   * 获取数据
   * 
   * @return 数据
   */
  public final D getData() {
    return mData;
  }

  /**
   * 设置数据
   * 
   * @param  data 数据
   */
  public final void setData(D data) {
    mData = data;
  }

  // 数据
  private D mData;

}

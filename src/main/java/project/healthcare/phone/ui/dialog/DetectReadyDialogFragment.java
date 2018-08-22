package project.healthcare.phone.ui.dialog;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.measure.MeasureCompat;
import project.healthcare.phone.measure.MeasureFactory;
import project.healthcare.phone.page.DetectPreparePage;
import extend.wilimx.device.DeviceClient.OnConnectFinishListener;

/** 
 * 检测就绪对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-07-29 07:56
 */
public class DetectReadyDialogFragment extends FullscreenDialogFragment<DetectPreparePage>
    implements OnConnectFinishListener {

  /**
   * 设置检测类型
   * 
   * @param detectType 检测类型
   */
  public final void setDetectType(int detectType) {
    mDetectType = detectType;
    mMeasureCompat = MeasureFactory.getMeasureCompat(detectType);
  }

  @Override
  public void onAction(int action, Object data) {
    switch (action) {
    case PageAction.BACK:
      dismiss();
      break;
    }
  }

  @Override
  public void onConnectSuccess() {
    getPage().showConnectSucessState();
  }

  @Override
  public void onConnectFailed(int errorCode) {
    getPage().showConnectFailState();
  }

  @Override
  public void onDestroyView() {
    if (mMeasureCompat != null) {
      mMeasureCompat.disConnect();
      MeasureFactory.destroyMeasureCompat(mDetectType);
    }
    super.onDestroyView();
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_detect_ready;
  }

  @Override
  protected void onPageBuild(DetectPreparePage page) {
    if (mDetectType != -1) {
      page.showConnectState();
      page.updateDetectType(mDetectType);
    }
    if (mMeasureCompat == null) {
      page.showConnectFailState();
    } else {
      mMeasureCompat.setOnConnectFinishListener(this);
      mMeasureCompat.connect();
    }
  }


  // 检测类型
  private int mDetectType = -1;

  private MeasureCompat mMeasureCompat = null;

}

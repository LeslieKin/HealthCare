package project.healthcare.phone.ui.dialog;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.db.DetectDataQueryManager;
import project.healthcare.phone.page.DetectDetailsPage;

public class DetectDetailsDialogFragment extends RightSlideInDialogFragment<DetectDetailsPage>{

  /**
   * 设置检测类型
   * 
   * @param detectType 检测类型
   */
  public final void setDetectType(int detectType) {
    mDetectType = detectType;
  }

  @Override
  public void onAction(int action, Object data) {
    super.onAction(action, data);
    switch (action) {
    case PageAction.UPDATA_DETAILS:
      mDetectType = (Integer) data;
      updateView();
      break;
    }
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_detect_details;
  }

  @Override
  protected void onPageBuild(DetectDetailsPage page) {
    updateView();
  }

  //更新视图
  private void updateView() {
    getPage().updateDetectType(mDetectType);
    getPage().updateChart(detectDataQueryManager.findAllDetectDataAsc(mDetectType), mDetectType);
    getPage().updateDetectResult(detectDataQueryManager.findLastDetectData(mDetectType));
  }

  // 检测类型
  private int mDetectType = -1;

  private DetectDataQueryManager detectDataQueryManager = new DetectDataQueryManager();
}

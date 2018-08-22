package project.healthcare.phone.ui.dialog;

import project.healthcare.phone.R;
import project.healthcare.phone.page.ConfirmDialogPage;

import com.wilimx.app.BaseDialogFragment;

public abstract class ConfirmDialogFragment extends BaseDialogFragment<ConfirmDialogPage> {

  @Override
  protected void onPageBuild(ConfirmDialogPage page) {
    //设置对话框宽度和高度
    getDialog().getWindow().setLayout(getDimensionPxSize(R.dimen.width_dialog_confirm), getDimensionPxSize(R.dimen.height_dialog_confirm));
  }

  @Override
  protected final void onConfigStyle() {
    setStyle(STYLE_NORMAL, R.style.dialog_confirm);
  }

  /**
   * 获取屏幕对应像素值
   * @param id
   * @return
   */
  protected final int getDimensionPxSize(int id) {
    return getResources().getDimensionPixelSize(id);
  }
}

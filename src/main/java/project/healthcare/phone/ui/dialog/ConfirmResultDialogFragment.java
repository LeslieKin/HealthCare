package project.healthcare.phone.ui.dialog;

import com.wilimx.app.BaseDialogFragment;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.page.ConfirmRegisterResultPage;

public class ConfirmResultDialogFragment extends BaseDialogFragment<ConfirmRegisterResultPage>{

	/**
	   * 对话框确认事件监听器接口
	   * @author Cai.wh
	   */
	  public interface OnDialogConfirmListener {

	    /**
	     * 对话框确认回调
	     */
	    public void onDialogConfirm();
	  }
  
  public void setOnDialongConfirmListener(OnDialogConfirmListener listener) {
    onDialogConfirmListener = listener;
  }
	
  @Override
  protected int getContentResId() {
    return R.layout.dialog_confirm_register;
  }

  @Override
  protected void onPageBuild(ConfirmRegisterResultPage page) {
    //设置对话框宽度和高度
    getDialog().getWindow().setLayout(getDimensionPxSize(R.dimen.width_dialog_confirm), getDimensionPxSize(R.dimen.height_dialog_confirm));
  }

  @Override
  public void onAction(int action, Object data) {
    switch (action) {
    case PageAction.DISMISS:
      if (onDialogConfirmListener !=null) {
            onDialogConfirmListener.onDialogConfirm();
      }
      break;
    }
  }

  @Override
  protected void onConfigStyle() {
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
  
//对话框确认事件监听器接口
  private OnDialogConfirmListener onDialogConfirmListener;
}

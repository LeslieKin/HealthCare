package project.healthcare.phone.ui.dialog;

import android.os.Bundle;

import com.wilimx.app.BaseDialogFragment;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.page.ConfirmPhoneNumberPage;

/**
 * 确认所发送验证码的手机号码对话框
 * @author Cai.wh
 *
 */
public class ConfirmPhoneDialogFragment extends BaseDialogFragment<ConfirmPhoneNumberPage>{

  /**
   * 对话框确认事件监听器接口
   * @author Cai.wh
   */
  public interface OnDialogConfirmListener {

    /**
     * 对话框确认回调
     */
     void onDialogConfirm();

    /**
     * 对话框取消回调
     */
     void onDialogCancel();
  }

  public void setOnDialongConfirmListener(OnDialogConfirmListener listener) {
     onDialogConfirmListener = listener;
  }

  public ConfirmPhoneDialogFragment setPhoneNumberText(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  protected int getContentResId() {
    return R.layout.dialog_confirm_phone;
  }

  @Override
  protected Bundle makePageParams() {
    Bundle params = new Bundle();
    params.putString(CommonKeys.PHONE, phoneNumber);
    return params;
  }

  @Override
  protected void onPageBuild(ConfirmPhoneNumberPage page) {
    //设置对话框宽度和高度
    getDialog().getWindow().setLayout(getDimensionPxSize(R.dimen.width_dialog_confirm), getDimensionPxSize(R.dimen.height_dialog_confirm));
  }

  @Override
  public void onAction(int action, Object data) {
    switch (action) {
    case PageAction.COMFIRM:
      if (onDialogConfirmListener !=null) {
        onDialogConfirmListener.onDialogConfirm();
      }
      break;

    case PageAction.CANCEL:
      if (onDialogConfirmListener !=null) {
        onDialogConfirmListener.onDialogCancel();
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

  //手机号码
  private String phoneNumber = null;

  //对话框确认事件监听器接口
  private OnDialogConfirmListener onDialogConfirmListener;

}

package project.healthcare.phone.listener;

/**
 * 对话框完成监听器接口
 * 
 * @author xiao.yl
 * created at 2014-07-25 15:44
 */
public interface OnDialogFinishListener {

  /**
   * 对话框确认回调
   */
  void onDialogConfirm();

  /**
   * 对话框取消回调
   */
  void onDialogCancel();

}

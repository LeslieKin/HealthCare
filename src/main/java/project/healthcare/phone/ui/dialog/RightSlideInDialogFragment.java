package project.healthcare.phone.ui.dialog;

import android.app.Dialog;

import com.wilimx.app.Page;

import project.healthcare.phone.R;

/**
 * 右侧滑入对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-07-02 10:30
 */
public abstract class RightSlideInDialogFragment<PAGE extends Page>
    extends FullScreenUniqueDialogFragment<PAGE> {

  @Override
  protected final void onConfigDialog(Dialog dialog) {
    dialog.getWindow().setWindowAnimations(R.style.anim_right_slide_in);
  }
}

package project.healthcare.phone.ui.dialog;

import project.healthcare.phone.R;
import com.wilimx.app.BaseDialogFragment;
import com.wilimx.app.Page;

/**
 * 全屏对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-07-28 18:24
 */
public abstract class FullscreenDialogFragment<PAGE extends Page> extends BaseDialogFragment<PAGE> {

  @Override
  protected void onConfigStyle() {
    setStyle(STYLE_NO_TITLE, R.style.FullscreenDialog);
  }

}

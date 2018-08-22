package project.healthcare.phone.ui.dialog;

import java.util.HashMap;

import java.util.Map;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;

import android.support.v4.app.FragmentManager;

import com.wilimx.app.BaseDialogFragment;
import com.wilimx.app.Page;
import com.wilimx.util.DefaultUtil;

/**
 * 全屏唯一对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-07-02 10:16
 */
public abstract class FullScreenUniqueDialogFragment<PAGE extends Page> extends BaseDialogFragment<PAGE> {

  @Override
  public void show(FragmentManager manager, String tag) {
    if (!getDisplayStatus()) {
      _displayStatusMap.put(getClass(), true);
      super.show(manager, tag);
    }
  }

  @Override
  public void onAction(int action, Object data) {
    if (action == PageAction.BACK) {
      dismiss();
    }
  }

  @Override
  public void onDestroyView() {
    resetDisplayStatus();
    super.onDestroyView();
  }

  @Override
  protected final void onConfigStyle() {
    setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog);
  }

  /*
   * 获取显示状态
   * 
   * @return 显示状态
   */
  private final boolean getDisplayStatus() {
    return DefaultUtil.get(_displayStatusMap.get(getClass()), false);
  }

  /*
   * 重置显示状态
   */
  private final void resetDisplayStatus() {
    if (getDisplayStatus()) {
      _displayStatusMap.remove(getClass());
    }
  }

  // 显示状态映射
  @SuppressWarnings("rawtypes")
  private static final Map<Class<? extends FullScreenUniqueDialogFragment>, Boolean> _displayStatusMap
    = new HashMap<Class<? extends FullScreenUniqueDialogFragment>, Boolean>();

}

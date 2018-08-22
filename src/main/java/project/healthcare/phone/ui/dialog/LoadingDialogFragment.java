package project.healthcare.phone.ui.dialog;

import android.support.v4.app.Fragment;

import com.wilimx.app.BaseDialogFragment;

import java.lang.reflect.Field;

import project.healthcare.phone.R;
import project.healthcare.phone.page.LoadingDialogPage;

public class LoadingDialogFragment extends BaseDialogFragment<LoadingDialogPage>{

  @Override
  protected int getContentResId() {
    return R.layout.dialog_loading;
  }

  @Override  
  public void onDetach() {  
    super.onDetach();  
    try {
        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");  
        childFragmentManager.setAccessible(true);  
        childFragmentManager.set(this, null);  
    } catch (NoSuchFieldException e) {  
        throw new RuntimeException(e);  
    } catch (IllegalAccessException e) {  
        throw new RuntimeException(e);  
    }  
  }

  /**
   * 设置文本加载内容
   *
   * @param resid 文本资源ID
   * @return 加载对话框本身
   */
  public final LoadingDialogFragment setLoadTextContent(int resid) {
    mLoadTextContent = resid;
    return this;
  }

  
   /*登陆页面的更新数据加载*/
  @Override
  protected void onPageBuild(LoadingDialogPage page) {
    if (mLoadTextContent != -1) {
      getPage().setLoadingText(mLoadTextContent);
    } else {
      getPage().setLoadingText(R.string.data_loading);
    }
  }

  @Override
  protected void onConfigStyle() {
    setStyle(STYLE_NORMAL, R.style.Loading_Dialog);
  }

  // 文本加载内容
  private int mLoadTextContent = -1;
}

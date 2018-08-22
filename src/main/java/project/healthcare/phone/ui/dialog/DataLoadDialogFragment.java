package project.healthcare.phone.ui.dialog;

import android.content.Intent;
import android.os.Bundle;

import com.wilimx.app.BaseDialogFragment;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.model.Impl.DataLoadModelImpl;
import project.healthcare.phone.page.DataLoadPage;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.presenter.impl.DataLoadPresenterImpl;
import project.healthcare.phone.ui.activity.LoginActivity;
import project.healthcare.phone.ui.activity.SendVericodeActivity;
import project.healthcare.phone.view.IDataLoadView;

public class DataLoadDialogFragment extends BaseDialogFragment<DataLoadPage> implements IDataLoadView{

  private DataLoadPresenterImpl mDataLoadPresenter;

  /**
   * 碎片创建回调
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if(mDataLoadPresenter == null){
      mDataLoadPresenter = new DataLoadPresenterImpl();
      mDataLoadPresenter.bindView(this);
      mDataLoadPresenter.bindModel(new DataLoadModelImpl());
    }

    mDataLoadPresenter.init();
    mDataLoadPresenter.updateMemberData(Pref.getAppPreference().getLogCount());
  }

  @Override
  public void displayLoadFailed(int errorCode) {
    postLoadFailed(errorCode);
  }

  @Override
  public void displayLoadFinsh() {
    postLoadFinish();
  }

  public interface OnLoginFinish {

     void onLoginFinish();
  }

  public void setLoginFinish(OnLoginFinish onLoginFinish) {
    this.onLoginFinish = onLoginFinish;
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_data_load;
  }

  @Override
  protected void onPageBuild(DataLoadPage page) {
 //   mDataLoadPresenter.updateMemberData(Pref.getAppPreference().getLogCount());
  }

  @Override
  protected final void onConfigStyle() {
    setStyle(STYLE_NORMAL, R.style.Loading_Dialog);
  }


  /**
   * 发布加载失败事件
   * 
   * @param errorCode 错误码
   */
  private final void postLoadFailed(int errorCode) {
    getPage().showToast(R.string.info_data_load_failed);
  }

  /**
   * 发布加载结束
   */
  private final void postLoadFinish() {
    if (isContentViewAvailable()) {
      dismiss();
    }
    startActivity(new Intent(CommonActions.MAIN));
    if (onLoginFinish != null) {
      onLoginFinish.onLoginFinish();
    }
    if(LoginActivity.activity!=null){//新添
      LoginActivity.activity.finish();
    }
    if(SendVericodeActivity.activity!=null){
      SendVericodeActivity.activity.finish();
    }
  }

  private OnLoginFinish onLoginFinish = null;

  @Override
  public void onDestroyView() {
    mDataLoadPresenter.destory();
    super.onDestroyView();
  }
}

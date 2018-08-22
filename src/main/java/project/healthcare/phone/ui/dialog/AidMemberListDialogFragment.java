package project.healthcare.phone.ui.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wilimx.app.BaseDialogFragment;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.AidMemberListModelImpl;
import project.healthcare.phone.page.AidMemberListPage;
import project.healthcare.phone.presenter.impl.AidMemberPresenterImpl;
import project.healthcare.phone.view.IAidMemberListView;

public class AidMemberListDialogFragment extends BaseDialogFragment<AidMemberListPage>{

  private IAidMemberListView mAidMemberListView;
  private AidMemberPresenterImpl mPresenter;

  public AidMemberListDialogFragment(){
    Log.e("asdasdasdasd", "AidMemberListDialogFragment: ");
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  /**
   * 碎片创建回调
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if(mAidMemberListView==null){
        mAidMemberListView = getPage();
    }

    mAidMemberListView.bindRes(this.getView());

    if(mPresenter == null){
      mPresenter = new AidMemberPresenterImpl();
      mPresenter.bindView(mAidMemberListView);
      mPresenter.bindModel(new AidMemberListModelImpl());
    }

    mPresenter.init();
    mAidMemberListView.setAidMemFragment(this);

  }
  public void setData(JSONObject data) {
    mDataLists = data;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  public int getMid(){
    return mid;
  }

  @Override
  protected int getContentResId() {
    return R.layout.dialog_aid_member_list;
  }

  @Override
  protected void onPageBuild(AidMemberListPage page) {
    getPage().updateDate(mDataLists);
  }

  @Override
  protected void onConfigStyle() {
    setStyle(STYLE_NORMAL, R.style.dialog_confirm);
  }

  private JSONObject mDataLists = new JSONObject();

  private int mid;

  public Boolean whetherContentViewAvailable() {
    return isContentViewAvailable();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.destory();
  }
}

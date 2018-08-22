package project.healthcare.phone.ui.dialog;

import android.os.Bundle;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.AidRecordListModelImpl;
import project.healthcare.phone.page.AidListRecordPage;
import project.healthcare.phone.presenter.impl.AidRecordListPresenterImpl;
import project.healthcare.phone.view.IAidRecordListView;

public class AidRecordListDialogFragment extends RightSlideInDialogFragment<AidListRecordPage>{

  private IAidRecordListView mIAidRecordListView;
  private AidRecordListPresenterImpl mPresenter;

  public void setData(JSONObject data, int mid) {
    mDataLists = data;
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_aid_list_record;
  }

  @Override
  protected void onPageBuild(AidListRecordPage page) {
    getPage().updateDate(mDataLists);
  }

  /**
   * 碎片创建回调
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if(mIAidRecordListView == null){
      mIAidRecordListView = getPage();
    }

    mIAidRecordListView.bindRes(this.getView());

    if(mPresenter == null){
      mPresenter = new AidRecordListPresenterImpl();
      mPresenter.bindView(mIAidRecordListView);
      mPresenter.bindModel(new AidRecordListModelImpl());
    }

    mPresenter.init();
    mIAidRecordListView.setFragment(this);
  }

  private JSONObject mDataLists = new JSONObject();

  public Boolean whetherContentViewAvailable(){
        return isContentViewAvailable();
  }
}

package project.healthcare.phone.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.FillDetailModelImpl;
import project.healthcare.phone.page.FillDetailsPage;
import project.healthcare.phone.presenter.impl.FillDetailPresenterImpl;
import project.healthcare.phone.view.IFillDetailView;

public class FillDetailsActivity extends BaseActivity<FillDetailsPage>{

	private IFillDetailView mFillDetailView;
	private FillDetailPresenterImpl mPresenter;

	/**
	 * 注入MVP依赖
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(mFillDetailView==null){
			mFillDetailView = getPage();
		}

		mFillDetailView.bindRes(this);

		if(mPresenter ==null){
			mPresenter = new FillDetailPresenterImpl();
			mPresenter.bindView(mFillDetailView);
			mPresenter.bindModel(new FillDetailModelImpl());
		}

		mPresenter.init();
	}

	@Override
	protected void onPageBuild(FillDetailsPage page) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
	}

  	@Override
  	protected int getContentResId() {
    return R.layout.page_fill_details;
  }

	/**
	 * 销毁活动
	 * 管理Presenter的生命周期
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.destory();
	}
}

package project.healthcare.phone.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.ChangepswModelImpl;
import project.healthcare.phone.page.ChangepswPage;
import project.healthcare.phone.presenter.impl.ChangepswPresenterImpl;
import project.healthcare.phone.view.IChangepswView;

public class ChangepswActivity extends BaseActivity<ChangepswPage> {

    private IChangepswView mChangepswView;
    private ChangepswPresenterImpl mChangepswPresenter;

    /**
     * 注入MVP依赖
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mChangepswView == null) {
            mChangepswView = getPage();
        }

        mChangepswView.bindRes(this);

        if (mChangepswPresenter == null) {
            mChangepswPresenter = new ChangepswPresenterImpl();
            mChangepswPresenter.bindView(mChangepswView);
            mChangepswPresenter.bindModel(new ChangepswModelImpl());
        }

        mChangepswPresenter.init();
    }

    @Override
    protected void onPageBuild(ChangepswPage page) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_change_psw;
    }

    /**
     * 销毁活动
     * 管理Presenter的生命周期
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mChangepswPresenter != null)
            mChangepswPresenter.destory();
    }
}

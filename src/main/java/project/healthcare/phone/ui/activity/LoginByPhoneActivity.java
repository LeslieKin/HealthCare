package project.healthcare.phone.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.LgByPhoneModelImpl;
import project.healthcare.phone.page.LoginByPhonePage;
import project.healthcare.phone.presenter.impl.LgByPhonePresenterImpl;
import project.healthcare.phone.view.ILgByPhoneView;

public class LoginByPhoneActivity extends BaseActivity<LoginByPhonePage> {

    private ILgByPhoneView mLgByPhoneView;
    private LgByPhonePresenterImpl mPresenter;

    /**
     * 注入MVP依赖
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mLgByPhoneView == null) {
            mLgByPhoneView = getPage();
        }

        mLgByPhoneView.bindRes(this);

        if (mPresenter == null) {
            mPresenter = new LgByPhonePresenterImpl();
            mPresenter.bindView(mLgByPhoneView);
            mPresenter.bindModel(new LgByPhoneModelImpl());
        }

        mPresenter.init();
    }

    @Override
    protected void onPageBuild(LoginByPhonePage page) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_login_by_phone;
    }

    /**
     * 管理Presenter的生命周期
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destory();
    }
}

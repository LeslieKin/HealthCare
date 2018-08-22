package project.healthcare.phone.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.LoginModelImpl;
import project.healthcare.phone.page.LoginPage;
import project.healthcare.phone.presenter.impl.LoginPresenterImpl;
import project.healthcare.phone.view.ILoginView;

public class LoginActivity extends BaseActivity<LoginPage> {

    private ILoginView mLoginView;
    private LoginPresenterImpl mPresenter;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mLoginView == null) {
            mLoginView = getPage();
        }
        mLoginView.bindRes(this);

        if (mPresenter == null) {
            mPresenter = new LoginPresenterImpl();
            mPresenter.bindView(mLoginView);
            mPresenter.bindModel(new LoginModelImpl());
        }
      
        mPresenter.init();
    }


    @Override
    protected void onPageBuild(LoginPage page) {
        activity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        mPresenter.destory();
    }
}

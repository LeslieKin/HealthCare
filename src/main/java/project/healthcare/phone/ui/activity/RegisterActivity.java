package project.healthcare.phone.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.page.RegisterPage;
import project.healthcare.phone.view.IBaseAView;

public class RegisterActivity extends BaseActivity<RegisterPage> {

    public static Activity activity;
    private IBaseAView mRegisterView;

    /**
     * 注入MVP依赖
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mRegisterView == null) {
            mRegisterView = getPage();
        }
        mRegisterView.bindRes(this);

    }

    @Override
    protected void onPageBuild(RegisterPage page) {
        activity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_register;
    }

    @Override
    protected void onDestroy() {
        activity = null;
        super.onDestroy();
    }
}

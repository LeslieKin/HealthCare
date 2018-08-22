package project.healthcare.phone.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.page.ForgotpswPage;
import project.healthcare.phone.view.IBaseAView;

public class ForgotpswActivity extends BaseActivity<ForgotpswPage> {

    public static Activity activity;
    private IBaseAView mFgtpswView;

    /**
     * 注入MVP依赖
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mFgtpswView == null) {
            mFgtpswView = getPage();
        }

        mFgtpswView.bindRes(this);
    }


    @Override
    protected void onPageBuild(ForgotpswPage page) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        activity = this;
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_forgot_psw;
    }

    @Override
    protected void onDestroy() {
        activity = null;
        super.onDestroy();
    }
}

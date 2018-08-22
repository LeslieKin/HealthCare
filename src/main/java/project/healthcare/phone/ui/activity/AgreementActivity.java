package project.healthcare.phone.ui.activity;

import android.content.pm.ActivityInfo;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.page.AgreementPage;

public class AgreementActivity extends BaseActivity<AgreementPage> {

    @Override
    protected void onPageBuild(AgreementPage page) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_agreement;
    }

    @Override
    public void onAction(int action, Object data) {
        switch (action) {
            case PageAction.BACK:
                finish();
                break;
        }
    }
}

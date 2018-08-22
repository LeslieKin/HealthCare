package project.healthcare.phone.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.page.StartPage;


/**
 * Created by Li.jq on 2017/7/19.
 * 启动页
 */

public class StartPageActivity extends BaseActivity<StartPage> {

    @Override
    protected int getContentResId() {
        return R.layout.page_start;
    }

    @Override
    protected void onPageBuild(StartPage page) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!MainActivity.isStop) {
                    startActivity(new Intent(CommonActions.LOGIN));
                }
                finish();
            }
        }, 1500);
    }
}

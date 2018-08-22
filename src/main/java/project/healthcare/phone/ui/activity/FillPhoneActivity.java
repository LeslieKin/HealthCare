package project.healthcare.phone.ui.activity;

import android.content.pm.ActivityInfo;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.page.FillPhonePage;

public class FillPhoneActivity extends BaseActivity<FillPhonePage>{

  @Override
  protected void onPageBuild(FillPhonePage page) {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_fill_phone;
  }

}

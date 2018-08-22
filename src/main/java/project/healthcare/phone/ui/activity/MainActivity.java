package project.healthcare.phone.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.config.AppConstants;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.PrefKeys;
import project.healthcare.phone.page.MainActivityPage;
import project.healthcare.phone.preference.Pref;

public class MainActivity extends BaseActivity<MainActivityPage> implements OnSharedPreferenceChangeListener {

  public static Activity activity;
  private boolean backPressedFlag=true;
  public  static boolean isStop=false;

  @Override
  public void onBackPressed() {
    backPressedFlag=!backPressedFlag;
    if(!backPressedFlag){
      Toast.makeText(MainActivity.this,R.string.click_again,Toast.LENGTH_SHORT).show();
    }else{
      Intent i= new Intent(Intent.ACTION_MAIN);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.addCategory(Intent.CATEGORY_HOME);
      startActivity(i);
    }
  }

  @Override
  protected void onResume(){
    backPressedFlag=true;
    super.onResume();
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
      String key) {
    if (key.equals(PrefKeys.NEW_AID_INFO)) {
      if (Pref.getAppPreference().getIsNewAidInfo()) {
        getPage().showAidRedPoint();
      } else {
        getPage().hideAidRedPoint();
      }
    }
  }


  @Override
  protected void onNewIntent(Intent intent) {
    if (intent.getAction().equals(CommonActions.AID)) {
      getPage().setCurrentAidPage(intent.getExtras().getString(CommonKeys.AID_INFO));
    } else if (intent.getAction().equals(CommonActions.AID_CANCEL)){
      getPage().setAidPageCancel();
    }
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_main;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY, AppConstants.API_KEY);
    Pref.getAppPreference().registerPrefChangeListener(this);
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
  }

  @Override
  protected void onPageBuild(MainActivityPage page) {
    super.onPageBuild(page);
    activity = this;
    if (getIntent().getAction().equals(CommonActions.AID)) {
      getPage().setCurrentAidPage(getIntent().getExtras().getString(CommonKeys.AID_INFO));
    } else if (getIntent().getAction().equals(CommonActions.AID_CANCEL)){
      getPage().setAidPageCancel();
    }
    if (Pref.getAppPreference().getIsNewAidInfo()) {
      getPage().showAidRedPoint();
    }
    Session.init(this);
  }

  @Override
  protected void onPause(){
    super.onPause();
  }

  @Override
  public void onStop() {
    isStop=true;
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    Pref.getAppPreference().unregisterPrefChangeListener(this);
    MainActivityPage.times=0;
    activity = null;
    super.onDestroy();
  }
}

package project.healthcare.phone.ui.activity;

import com.baidu.mapapi.SDKInitializer;
import com.wilimx.app.BaseApplication;

public class HealthApplication extends BaseApplication{

  @Override
  public void onCreate() {
    super.onCreate();
    SDKInitializer.initialize(this);
  }
}

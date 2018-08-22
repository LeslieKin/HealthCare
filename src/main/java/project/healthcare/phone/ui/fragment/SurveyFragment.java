package project.healthcare.phone.ui.fragment;

import android.util.SparseIntArray;

import com.wilimx.tab_control.TabContentFragment;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.constants.UserInfo;
import project.healthcare.phone.db.CompositeDataQueryManager;
import project.healthcare.phone.db.DetectDataQueryManager;
import project.healthcare.phone.db.UserInfoQueryManager;
import project.healthcare.phone.page.MainActivityPage;
import project.healthcare.phone.page.SurveyFragmentPage;
import project.healthcare.phone.preference.Pref;


public class SurveyFragment extends TabContentFragment<SurveyFragmentPage>{

  @Override
  protected int getContentResId() {
    return R.layout.page_survey;
  }
  
  @Override
  public void onAction(int action, Object data) {
    switch (action) {
    case PageAction.MEASURE:
//      MainActivity.activity.finish();
//      startActivity(new Intent(CommonActions.MAIN));//这个功能没有实现，看得不是很懂。
      MainActivityPage.changeToDetect();//new add
      break;
    }
  }

  @Override
  protected void onPageBuild(SurveyFragmentPage page) {
    UserInfo userInfo = mUserInfoQueryManager.findMember(Pref.getAppPreference().getLogCount());

    //检测项目分数
    int[] dataTypes = userInfo.getDataTypes();
    SparseIntArray detectDataMap = new SparseIntArray();
    //判断dataTypes，新用户的dataTypes是null
    if(dataTypes!=null){
	    for (int i = 0 ;i < dataTypes.length; i++) {
	      detectDataMap.put(dataTypes[i], mQueryManager.findAvgDetectData(dataTypes[i])) ;
	    }
    }
    
    page.updataDetectData(detectDataMap);
    page.updateCompositeScore(mCompositeQueryManager.findLastestCompositeScore());
    page.updateCompositeTime(mCompositeQueryManager.findLastestCompositeTime());
  }

  private CompositeDataQueryManager mCompositeQueryManager = new CompositeDataQueryManager();

  private UserInfoQueryManager mUserInfoQueryManager = new UserInfoQueryManager();

  // 检测数据查询管理器
  private DetectDataQueryManager mQueryManager = new DetectDataQueryManager();

}

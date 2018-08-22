package project.healthcare.phone.page;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.wilimx.app.Page;
import com.wilimx.app.adapter.SimpleFragmentPagerAdapter;
import com.wilimx.widget.RadioTabSwitcher;
import com.wilimx.widget.RadioTabSwitcher.OnTabChangeListener;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.fragment.AidFragment;
import project.healthcare.phone.ui.fragment.DetectFragment;
import project.healthcare.phone.ui.fragment.QuestionFragment;
import project.healthcare.phone.ui.fragment.SetFragment;
import project.healthcare.phone.ui.fragment.SurveyFragment;


public class MainActivityPage extends Page implements OnTabChangeListener{

  @Override
  public void onTabChange(int tabId) {
    if (tabId == R.id.nav_aid) {
    }
  }

  public void showAidRedPoint() {
    setViewVisibility(R.id.icon_red_point, View.VISIBLE);
  }

  public void hideAidRedPoint() {
    setViewVisibility(R.id.icon_red_point, View.GONE);
  }

  /*
   * 设置当前页面为紧急救助页面
   */
  public void setCurrentAidPage(String aidInfo) {
    mViewPager.setCurrentItem(2);
    mAidFragment.updateAidInfo(aidInfo);
  }

  public void setAidPageCancel() {
    mViewPager.setCurrentItem(2);
    mAidFragment.showRecordList();
  }
  
  public static int times = 0;
  
  @Override
  protected void onPageInit() {

    if (mAdapter == null) {
      SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getFragmentManager());
      adapter.addFragments(
        new SurveyFragment(),
        new DetectFragment(),
        mAidFragment,
        new QuestionFragment(),
        new SetFragment()
      );
      mAdapter = adapter;
    }

    mViewPager = (ViewPager)findView(R.id.main_viewpager);
    mViewPager.setOffscreenPageLimit(OFF_SCRENN_PAGE_LIMIT);
    mViewPager.setAdapter(mAdapter);

    mTabSwitcher.setViewPager(mViewPager);
    mTabSwitcher.setTabGroup((RadioGroup)findView(R.id.main_tab));
    mTabSwitcher.setOnTabChangeListener(this);

    mRadioGroup=(RadioGroup)findView(R.id.main_tab);
    if(times!=0) {
    	mTabSwitcher.onCheckedChanged((RadioGroup)findView(R.id.main_tab), R.id.nav_detect);
    } else {
    	mTabSwitcher.onCheckedChanged((RadioGroup)findView(R.id.main_tab), R.id.nav_survey);
    	times=times+1;
    }

//    updateUserImage();
  }

  public static void changeToDetect(){
    mTabSwitcher.onCheckedChanged(mRadioGroup, R.id.nav_detect);
  }

   private int OFF_SCRENN_PAGE_LIMIT = 5;

  //页面视图适配器
  private SimpleFragmentPagerAdapter mAdapter = null;

//private RadioTabSwitcher mTabSwitcher = new RadioTabSwitcher();
  public static RadioTabSwitcher mTabSwitcher=new RadioTabSwitcher();//内存泄露？
  private static RadioGroup mRadioGroup;

  private ViewPager mViewPager;

  private AidFragment mAidFragment = new AidFragment();

}

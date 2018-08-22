package project.healthcare.phone.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wilimx.constants.Constants;

import project.healthcare.phone.R;
import project.healthcare.phone.config.DetectTypeConfig;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.constants.ScoreState;
import project.healthcare.phone.db.DetectData;
import project.healthcare.phone.ui.widget.CustomHorizontalScrollView;
import project.healthcare.phone.ui.widget.CustomHorizontalScrollView.OnScrollStopListener;
import project.healthcare.phone.ui.widget.DetectDetailChartView;

public class DetectDetailsPage extends TitleBackPage implements OnCheckedChangeListener, OnScrollStopListener, OnTouchListener{


  @Override
  public void onScrollStoped() {
  }

  @Override
  public void onScrollToLeftEdge() {
    setViewEnabled(R.id.button_previous_page, false);
    setViewEnabled(R.id.button_next_page, true);
  }

  @Override
  public void onScrollToRightEdge() {
    setViewEnabled(R.id.button_previous_page, true);
    setViewEnabled(R.id.button_next_page, false);
  }

  @Override
  public void onScrollMiddle() {
    setViewEnabled(R.id.button_previous_page, true);
    setViewEnabled(R.id.button_next_page, true);
  }


  @SuppressLint("ClickableViewAccessibility") @Override
  public boolean onTouch(View v, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_MOVE) {
      horizontalScrollView.startScrollerTask();
    }
    return false;
  }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
    horizontalScrollByNavButton(checkedId);
    switch (checkedId) {
    case R.id.nav_blood_pressure_button:
      mDetectType = DetectTypeConfig.BLOOD_PRESSURE.getTypeKey();
      break;

    case R.id.nav_heart_rate_button:
      mDetectType = DetectTypeConfig.HEART_RATE.getTypeKey();
      break;

    case R.id.nav_blood_oxygen_button:
      mDetectType = DetectTypeConfig.BLOOD_OXYGEN.getTypeKey();
      break;

    case R.id.nav_blood_sugar_button:
      mDetectType = DetectTypeConfig.BLOOD_SUGAR.getTypeKey();
      break;

    case R.id.nav_temperature_button:
      mDetectType = DetectTypeConfig.TEMP.getTypeKey();
      break;

    case R.id.nav_weight_button:
      mDetectType = DetectTypeConfig.WEIGHT.getTypeKey();
      break;
    }
    postAction(PageAction.UPDATA_DETAILS, mDetectType);
  }

  /**
   * 更新类型
   * @param detectType
   */
  public final void updateDetectType(int detectType) {
    setText(R.id.textview_title_name, DetectTypeConfig.get(detectType).getTitleRes());
    mDetectType = detectType;
    radioGroup = (RadioGroup)findView(R.id.radio_group_nav_item);
    ((RadioButton)radioGroup.getChildAt(detectType)).setChecked(true);
    horizontalScrollByNavButton(radioGroup.getChildAt(detectType).getId());
  }

  /**
   * 更新图表
   * @param datas
   * @param detectType
   */
  public final void updateChart(float[][] datas, int detectType) {
    LinearLayout container = (LinearLayout)findView(R.id.detail_chart_container);
    container.removeAllViews();
    DetectDetailChartView mDetailChartView = new DetectDetailChartView(getContext(), null);
    mDetailChartView.setDetectType(detectType);
    if (datas != null && datas.length != 0) {
      int Line = datas.length;
      int Column = (datas[0].length <= 20) ? datas[0].length : 20;
      float[][] temp = new float[Line][Column];
      for (int i = 0; i < datas.length; i++) {
        for (int j = 0; j < Column; j++) {
          temp[i][j] = datas[i][j];
        }
      }
      mDetailChartView.updateData(temp);
    }
    container.addView(mDetailChartView);
  }

  /**
   * 更新检测结果
   */
  public final void updateDetectResult(DetectData detectData) {
    if (detectData != null) {
      setText(R.id.details_score, "" + (int)detectData.getScore());
      setTextColor(R.id.details_score, getColorRes(ScoreState.get((int)detectData.getScore())));
      setText(R.id.details_result_describute, detectData.getComment());
      setText(R.id.details_comment, detectData.getSuggest());
      int length = detectData.getMeasureValues().length;
      String[] measureValues = new String[length];
      for (int i = 0; i < length; i++) {
        measureValues[i] = String.valueOf(detectData.getMeasureValues()[i]);
      }
      setText(R.id.details_result, getString(DetectTypeConfig.get(mDetectType).getCurrentValueRes(), measureValues));
    } else {
      setText(R.id.details_score, "0");
      setText(R.id.details_result, getString(R.string.default_health_state_no));
      setText(R.id.details_result_describute, Constants.EMPTY_TEXT);
      setText(R.id.details_comment, Constants.EMPTY_TEXT);
    }
  }

  @SuppressLint("ClickableViewAccessibility") @Override
  protected void onPageInit() {
    super.onPageInit();
    SetRadioButtonWidth(findView(R.id.button_previous_page));

    horizontalScrollView = (CustomHorizontalScrollView)findView(R.id.horizontal_scrollview);
    horizontalScrollView.setOnTouchListener(this);
    horizontalScrollView.SetOnScrollStopListener(this);

    bindClickEvents(
      R.id.button_previous_page,
      R.id.button_next_page );
  }

  @Override
  protected final void handleClickEvents(View v) {
    super.handleClickEvents(v);
    switch (v.getId()) {

    case R.id.button_previous_page:
      scrollToPreviousPage();
      break;

    case R.id.button_next_page:
      scrollToNextPage();
      break;
    }
  }

  /**
   * 获取组件视图宽度and添加体检项目复选框
   * @param view 视图 
   * @return
   */
  protected void SetRadioButtonWidth(final View view) {
    ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
    viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
      @Override
      public void onGlobalLayout() {
        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        //滚动条宽度=屏幕总长度-（两个按钮的宽度和按钮与屏幕两边间距的和）
        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        int horizontalScrollViewWidth = windowManager.getDefaultDisplay().getWidth() - 
            ((view.getWidth()+(view.getLeft()*2))*2);
        NAV_BUTTON_WIDTH = horizontalScrollViewWidth/ONE_PAGE_ITEM_COUNT;
        initViews();
      }
    });
  }

  /*
   * 初始化视图
   */
  private final void initViews() {
    radioGroup = (RadioGroup)findView(R.id.radio_group_nav_item);
    radioGroup.setOnCheckedChangeListener(this);
    for (int i = 0; i < radioGroup.getChildCount(); i++) {
      radioGroup.getChildAt(i).setLayoutParams(new RadioGroup.LayoutParams(NAV_BUTTON_WIDTH, LayoutParams.WRAP_CONTENT));
    }
  }

  /*
   * 横向滑动组件跟随导航单选项按钮滑动
   * @param id 单选项按钮id
   */
  private final void horizontalScrollByNavButton(int id) {
    for(int i = 0; i < radioGroup.getChildCount() ; i++) {
      if (radioGroup.getChildAt(i).getId() == id) {
        horizontalScrollView.startScrollerTask();
        horizontalScrollView.smoothScrollTo((NAV_BUTTON_WIDTH/2)+(i-2)*NAV_BUTTON_WIDTH, 0);
      }
    }
  }

  /*
   * 获取分数段颜色
   */
  private int getColorRes(ScoreState newScoreState) {
    switch (newScoreState) {
    case NORMAL:
      return R.color.composite_score_normal;

    case FINE:
      return R.color.composite_score_fine;
      
    case POOR:
      return R.color.composite_score_poor;

    }
    return R.color.composite_score_normal;
  }

  /*
   * 往后横向滑动一个单位的距离
   * @param viewId
   */
  private final void scrollToNextPage() {
      int position = horizontalScrollView.getScrollX() + (NAV_BUTTON_WIDTH);
      horizontalScrollView.startScrollerTask();
      //向左移动一个单选项宽度的距离
      horizontalScrollView.smoothScrollTo(position, 0);  
      //滑动到末端，下一页按钮设为不可用
  }

  /*
   * 往前横向滑动一个单位的距离
   * @param viewId
   */
  private final void scrollToPreviousPage() {
      
      int position = horizontalScrollView.getScrollX() - (NAV_BUTTON_WIDTH);
      horizontalScrollView.startScrollerTask();
     //向右移动一个单选项宽度的距离
      horizontalScrollView.smoothScrollTo(position, 0);
      //滑动到首端，上一页按钮设为不可用
  }

  private int mDetectType = -1;

  //一个单选项按钮的宽度
  private static int NAV_BUTTON_WIDTH;


  //单选项按钮组
  private RadioGroup radioGroup;

  // 横向滑动容器
  private CustomHorizontalScrollView horizontalScrollView;


  //横向滑动栏一页显示检测项目的个数
  private static int ONE_PAGE_ITEM_COUNT = 4;

}

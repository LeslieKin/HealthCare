package project.healthcare.phone.page;

import android.annotation.SuppressLint;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wilimx.app.ListItemPage;
import com.wilimx.app.Page;
import com.wilimx.app.adapter.SimpleListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.activity.MainActivity;
import project.healthcare.phone.assist.ScoreMonitor;
import project.healthcare.phone.config.DetectTypeConfig;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.constants.ScoreState;
import project.healthcare.phone.ui.dialog.DetectDetailsDialogFragment;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.ui.widget.CompositeItemScoreBoard;
import project.healthcare.phone.ui.widget.CompositeScoreAnimator;
import project.healthcare.phone.ui.widget.CompositeScoreBoardView;

public class SurveyFragmentPage extends Page{

  /**
   * 更新检测项目得分
   */
  public void updataDetectData(SparseIntArray map) {
    mDetectScoreMap = map;
    initCompositeItem();

  }

  /**
   * 更新检测综合得分
   */
  public void updateCompositeScore(int score) {
    mScoreAnimator.setScore(score).animate();
  }

  /**
   * 更新最后一次检测的时间
   */
  @SuppressLint("SimpleDateFormat") 
  public void updateCompositeTime(long time) {
    Date date = new Date(time);
    SimpleDateFormat format = new SimpleDateFormat(getString(R.string.format_date));
    String timeString = format.format(date);
    setText(R.id.text_view_last_detect_time, getString(R.string.format_last_detect_time, timeString));
  }

  @Override
  protected void onPageInit() {
    super.onPageInit();
    mScoreMonitor.reset();
    mScoreAnimator.setTargetView((CompositeScoreBoardView)findView(R.id.composite_score_board));
    bindClickEvents(
        R.id.button_detect_now);
    final ListView listView = (ListView)findView(R.id.list_item_survey);
    mItemSurveyAdapter.setItems(availableTypeList);
    View view = LayoutInflater.from(MainActivity.activity).inflate(R.layout.foot_view_layout, null);
    listView.addFooterView(view);
    listView.setAdapter(mItemSurveyAdapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      }
    });
  }

  
  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_detect_now:
      postAction(PageAction.MEASURE);//这个回调重写 的方法找错了？点击没有反应
      break;
    }
  }

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

  /**
   * 初始化项目分数
   */
  private void initCompositeItem() {
    int availableTypes[] = Pref.getAppPreference().getSupportTypes();
    if (availableTypes != null) {
      for (int i = 0; i< availableTypes.length; i++) {
        if (mDetectScoreMap.get(i, -1) != -1) {
          availableTypeList.add(true);
        } else {
        availableTypeList.add(false);
        }
      }
      mItemSurveyAdapter.setItems(availableTypeList);
      mItemSurveyAdapter.notifyDataSetChanged();
    }
  }

  private List<Boolean> availableTypeList = new ArrayList<Boolean>();

  @SuppressLint("UseSparseArrays") 
  private SparseIntArray mDetectScoreMap = new SparseIntArray();

  /**
   * 分数动画处理器
   */
  private CompositeScoreAnimator mScoreAnimator = new CompositeScoreAnimator(){
    
    protected void onAnimScoreChange(int animScore) {
      ((TextView)findView(R.id.text_composite_score)).setText(String.valueOf(animScore));
      mScoreMonitor.updateScore(animScore);
    };
  };

  /**
   * 分数管理器
   */
  private ScoreMonitor mScoreMonitor = new ScoreMonitor() {
    protected void onStateChange(ScoreState newScoreState) {
      TextView mTextView = (TextView)findView(R.id.text_composite_score);

      if (mTextView != null) {
        mTextView.setTextColor(getColor(getColorRes(newScoreState)));
      }
    };
  };



  private SimpleListAdapter<Boolean> mItemSurveyAdapter = new SimpleListAdapter<Boolean>() {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return mListItemPage.getView(position, convertView, parent);
    }

    private String getComment(int score) {
      String comment = getString(R.string.default_health_state_no);
      if (score < 60) {
        comment = getString(R.string.health_state_bad);
      } else if (score == 60 || score < 80){
        comment = getString(R.string.health_state_sort);
      } else {
        comment = getString(R.string.health_state_good);
      }
      return comment;
    }

    private ListItemPage mListItemPage = new ListItemPage() {

      @Override
      protected int getContentResId() {
        return R.layout.item_survey;
      }

      @Override
      protected void onInitListItem(final int position) {
        Boolean isAvail = getItem(position);
        DetectTypeConfig dectType = DetectTypeConfig.get(position);
        setViewEnabled(R.id.item_survey, isAvail);
        setViewEnabled(R.id.item_survey_analysis, isAvail);
        setViewEnabled(R.id.item_survey_tips, isAvail);
        int score = mDetectScoreMap.get(position);
        //分析描述
        setText(R.id.item_survey_analysis, getComment(score));
        //得分
        setText(R.id.item_survey_tips, getString(dectType.getTipFormatRes(), score));
        //图标
        ((ImageView)findView(R.id.icon_item)).setImageResource(dectType.getIconRes());
        //环形分数
        ((CompositeItemScoreBoard)findView(R.id.item_composite_score_board)).setScore(score);
        CompositeItemScoreBoard mScoreBoard = (CompositeItemScoreBoard)findView(R.id.item_composite_score_board);
        mScoreBoard.setScore(score);

        findView(R.id.item_survey).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            DetectDetailsDialogFragment fragment = new DetectDetailsDialogFragment();
            fragment.setDetectType(position);
            fragment.show(getFragmentManager(), null);
          }
        });

      }
    };
  };
}

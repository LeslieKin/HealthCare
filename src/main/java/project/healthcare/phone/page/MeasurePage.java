package project.healthcare.phone.page;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wilimx.app.Page;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.DetectTypeConfig;
import project.healthcare.phone.config.HealthStateConfig;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.constants.DetectType;
import project.healthcare.phone.http.AnalyseKeys;
import project.healthcare.phone.ui.dialog.MeasureDialogFragment;
import project.healthcare.phone.view.IMeasureDialogView;

import static project.healthcare.phone.R.string.format_result_systolic_blood_pressure;

/**
 * 测量页面
 * 
 * @author xiao.yl
 * created at 2014-07-29 11:22
 */
public class MeasurePage extends Page implements IMeasureDialogView{

  /**
   * 显示检测状态
   */
  public final void showDetectState() {
    Log.e(TAG, "showDetectState: " );
    enableView(R.id.detect_title_name);
    hideView(R.id.detect_result);
    setText(R.id.display_connection_status, R.string.state_measuring);
  }

  /**
   * 更新检测类型
   * 
   * @param detectType 检测类型
   */
  public final void updateDetectType(int detectType) {
    Log.e(TAG, "updateDetectType: ");
    mDetectType = detectType;
    mResultFormat = getResultFormat(detectType);
    setText(R.id.text_detect_type, DetectTypeConfig.get(detectType).getTitleRes());
    ((ImageView)findView(R.id.icon_detect_type))
    .setImageResource(DetectTypeConfig.get(detectType).getIconRes());
    setText(R.id.detect_operation_step, getOperationContent(detectType));
    TextView myDetectType=(TextView) findView(R.id.detect_type);
    TextView myPrepare=(TextView) findView(R.id.prepare_detect);
    myDetectType.setText(DetectTypeConfig.get(detectType).getTitleRes()); 
    myPrepare.setText(DetectTypeConfig.get(detectType).getTitleRes());
    setText(R.id.detect_type,myDetectType.getText()+"检测");
    setText(R.id.prepare_detect,"准备"+myPrepare.getText()+"检测,请按步骤操作!");  
  }

  /**
   * 更新检测结果
   * 
   * @param data 检测数据
   */
  public final void updateMeasureResult(Object data) {
    Log.e(TAG, "updateMeasureResult: ");
    if (data == null) {
      setText(R.id.display_connection_status, R.string.state_measure_fail);
      hideView(R.id.detect_result);
      hideView(R.id.bt_start_measuring);
      enableView(R.id.bt_restart_measuring);
    } 
    else {
      double[] result = (double[]) data;
      Log.e(TAG, "updateMeasureResult: data"+((double[]) data).toString() );

      if (mDetectType == DetectType.BLOOD_PRESSURE) {
        Log.e(TAG, "updateMeasureResult: mresultFormat"+mResultFormat );
//       setFormatText(R.id.diet_suggest, mResultFormat, result[0], result[1]);
        setFormatText(R.id.measure_suggest, mResultFormat, result[0], result[1]);//new add
      }
      else {
        Log.e(TAG, "updateMeasureResult: !!!!!mresultFormat"+mResultFormat );
//        setFormatText(R.id.diet_suggest, mResultFormat, result[0]);
        setFormatText(R.id.measure_suggest, mResultFormat, result[0]);//new add
      }
      setText(R.id.per_detect_result,R.string.default_measure_suggest);
      setText(R.id.measure_suggest,R.string.detect_measure_result_suggest);//new add
//      setText(R.id.diet_suggest,R.string.detect_measure_result_suggest);
//      setText(R.id.sports_suggest,R.string.default_measure_suggest);
      hideView(R.id.display_connection_status);
      showView(R.id.detect_result);
    }
    enableView(R.id.detect_title_name);
  }

  /**
   * 更新分析结果
   * 
   * @param data 分析结果
   */
  public final void updateAnalyseResult(JSONObject data) {
    Log.e(TAG, "updateAnalyseResult: ");

    if (data != null) {
      int healthState = data.optInt(AnalyseKeys.STATE_CODE, -1);

      if (healthState != -1) {
//        setText(R.id.sports_suggest, HealthStateConfig.get(healthState).getTitleRes());
        setText(R.id.measure_suggest, HealthStateConfig.get(healthState).getTitleRes());
      }
      Log.e(TAG, "updateAnalyseResult: data"+data.toString());
      setText(R.id.per_detect_result,data.optString(AnalyseKeys.SCORE));
      setText(R.id.measure_suggest,data.optString(AnalyseKeys.SUGGEST));
//    setText(R.id.diet_suggest,data.optString(AnalyseKeys.SUGGEST));//将SCORE改为SUGGEST
//    setText(R.id.sports_suggest, data.optString(AnalyseKeys.SUGGEST));
      hideView(R.id.bt_disable_start_measuring);
      hideView(R.id.bt_start_measuring);
      hideView(R.id.bt_disable_save_detect_result);
      enableViews(
        R.id.bt_save_detect_result,
        R.id.bt_restart_measuring,
        R.id.detect_title_name);
    } else {
      showToast(R.string.msg_get_analyse_result_failed);
      enableView(R.id.bt_restart_measuring);
      enableView(R.id.detect_title_name);
      disableView(R.id.bt_disable_save_detect_result);
      disableView(R.id.bt_save_detect_result);

    }
  }

  @Override
  public void onActionResponse(int action, Object data) {
    if (action == PageAction.SAVE_MEASURE_RESULT) {
      showToast((Boolean) data
        ? R.string.msg_save_analyse_result_success
        : R.string.msg_save_analyse_result_failed);
    } 
  }

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.bt_restart_measuring,
      R.id.bt_save_detect_result,
      R.id.detect_title_name);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.bt_restart_measuring:
        mFragment.dismiss();
      break;

    case R.id.bt_save_detect_result:
      postAction(PageAction.SAVE_MEASURE_RESULT);//为什么要提供一个 object参数
      mCallback.saveMeasureResult();
      break;

    case R.id.detect_title_name://初始化的时候点击该按钮没有反应
      mFragment.dismiss();
      break;
    }
  }

  /*
   * 获取结果格式
   * 
   * @param detectType 检测类型
   */
  private static final int getResultFormat(int detectType) {
    Log.e(TAG, "getResultFormat: ");
    switch (detectType) {
    case DetectType.BLOOD_PRESSURE:
      Log.e(TAG, "getResultFormat: "+format_result_systolic_blood_pressure);
      return format_result_systolic_blood_pressure;
     // return R.string.format_result_diastolic_blood_pressure;

    case DetectType.BLOOD_SUGAR:
      return R.string.format_result_blood_sugar;

    case DetectType.BLOOD_OXYGEN:
      return R.string.format_result_blood_oxygen;

    case DetectType.HEART_RATE:
      return R.string.format_result_heart_rate;

    case DetectType.TEMP:
      return R.string.format_result_temp;

    case DetectType.WEIGHT:
      return R.string.format_result_weight;
    }
    return -1;
  }


 
  /*
   * 获取操作内容
   */
  private static final int getOperationContent(int detectType) {
    switch (detectType) {
    case DetectType.BLOOD_PRESSURE:
      return R.string.operation_content_blood_pressure;

    case DetectType.BLOOD_SUGAR:
      return R.string.operation_content_blood_sugar;

    case DetectType.BLOOD_OXYGEN:
      return R.string.operation_content_blood_oxygen;

    case DetectType.HEART_RATE:
      return R.string.operation_content_heart_rate;

    case DetectType.TEMP:
      return R.string.operation_content_temp;

    case DetectType.WEIGHT:
      return R.string.operation_content_weight;
    }
    return -1;
  }

  public void setFragment(MeasureDialogFragment fragment){
      mFragment = fragment;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
      mCallback = callback;
  }

  private int mDetectType = -1;

  private int mResultFormat = -1;

  private final static String TAG="MeasurePage";

  private MeasureDialogFragment mFragment;

  private ActionCallback mCallback;
}


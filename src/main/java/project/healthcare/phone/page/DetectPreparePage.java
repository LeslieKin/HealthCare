package project.healthcare.phone.page;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import project.healthcare.phone.R;
import project.healthcare.phone.config.DetectTypeConfig;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.constants.DetectType;
import project.healthcare.phone.ui.dialog.MeasureDialogFragment;

import com.wilimx.app.Page;

/**
 * 检测准备页面
 * 
 * @author xiao.yl
 * created at 2014-07-29 07:56
 */
public class DetectPreparePage extends Page {

  /**
   * 显示连接状态
   */ 
  public final void showConnectState() {
    setText(R.id.display_connection_status, R.string.state_connecting);
    hideView(R.id.detect_result);
  }

  /**
   * 显示连接成功状态
   */
  public final void showConnectSucessState() {
    setText(R.id.display_connection_status, R.string.state_connect_success);
    enableView(R.id.bt_start_measuring);
    hideView(R.id.bt_disable_start_measuring);
  }

  /**
   * 显示连接失败状态
   */
  public final void showConnectFailState() {
	  setText(R.id.display_connection_status,R.string.state_connect_fail);
	  hideView(R.id.bt_start_measuring);

  } 

  /**
   * 更新检测类型
   * 
   * @param detectType 检测类型
   */
  public final void updateDetectType(int detectType) {
    mDetectType = detectType;
   
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

  @Override
  protected void onPageInit() {
    bindClickEvents(    	
      R.id.bt_start_measuring,
      R.id.detect_title_name,
      R.id.bt_save_detect_result);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.bt_start_measuring:
      if (mDetectType != -1) {
        MeasureDialogFragment fragment = new MeasureDialogFragment();
        fragment.setDetectType(mDetectType);
        showDialog(fragment);   	  
      }
      break;
    case R.id.detect_title_name:
      postAction(PageAction.BACK);
      break;
    case R.id.bt_disable_save_detect_result:  //这个可能还要继续修改
      if (mDetectType != -1) {
      }
      break;
    }
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

  private int mDetectType = -1;

}

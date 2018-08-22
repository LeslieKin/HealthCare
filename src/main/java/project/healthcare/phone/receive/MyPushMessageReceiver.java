package project.healthcare.phone.receive;

import java.util.List;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.TaskKeys;
import project.healthcare.phone.http.OperationResultDataReceiver;
import project.healthcare.phone.preference.Pref;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.wilimx.app.Session;
import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONRequestParams;
//import com.baidu.frontia.api.FrontiaPushMessageReceiver;

/**
 * 推送信息接收器
 * 
 * @author xiao.yl
 * created at 2014-06-16 16:01
 */
public class MyPushMessageReceiver extends PushMessageReceiver  {

  /*
   * Callback for PushManager.startWork
   * 
   * [errorCode == 0]:success
   * requestId: used for message track
   */
  @Override
  public void onBind(Context context, int errorCode, String appId,
      String userId, String channelId, String requestId) {
    Log.d("push", ">> bind <<");
    Log.e("push", "userId: " + userId);

    if (!TextUtils.isEmpty(userId)) {
      // save and bind pushId.
      Pref.getAppPreference().setPushId(userId);
      Session.addTask(TaskKeys.BIND_PUSH_ID);
      HttpUtil.post(
        ServerPath.BIND_PID,
        new JSONRequestParams()
          .setParams(CommonKeys.IDENTITY, Pref.getAppPreference().getLogCount())
          .setParams(CommonKeys.PHONE_ID, userId)
          .setParams(CommonKeys.DEVICE, 0),
        new OperationResultDataReceiver() {
          @Override
          protected void onSuccess(Boolean data) {
            if (data) {
              Session.data.putBoolean(CommonKeys.HAS_PUSH_ID_BIND, true);
            }
            Session.postAndClearTask(TaskKeys.BIND_PUSH_ID);
          }

          @Override
          protected void onFailed() {
            Session.postAndClearTask(TaskKeys.BIND_PUSH_ID);
          }
        });
    }
  }

  @Override
  public void onMessage(Context context, String message, String customContentString) {
    Log.d("push", ">> message <<");
    Log.e("push", "message: " + message);
    Log.e("push", "custom content: " + customContentString);
    PushMessage.showNotification(context, message);
  }

  /*
   * Callback for push message notification-click
   * 
   * customContentString: null|json_string
   */
  @Override
  public void onNotificationClicked(Context context, String title,
      String description, String customContentString) {
    Log.d("push", ">> notification click <<");
    Log.e("push", "custom content: " + customContentString);
  }

  /*
   * Callback for setTags
   * 
   * [errorCode == 0]:some of the tags set success
   * [otherwise]:all of the tags set failed
   * requestId: request id for cloud_push
   */
  @Override
  public void onSetTags(Context context, int errorCode,
      List<String> successTags, List<String> failTags, String requestId) {
  }

  /*
   * Callback for delTags
   * 
   * [errorCode == 0]:some of the tags del success
   * [otherwise]:all of the tags del failed
   * requestId: request id for cloud_push
   */
  @Override
  public void onDelTags(Context context, int errorCode,
      List<String> successTags, List<String> failTags, String requestId) {
    Log.d("push", ">> del tags <<");
  }

  /*
   * Callback for listTags
   * 
   * [errorCode == 0]:success
   * requestId: request id for cloud_push
   */
  @Override
  public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {
    Log.d("push", ">> list tags <<");
  }

  /*
   * Callback for PushManager.stopWork
   * 
   * [errorCode == 0]:success
   * requestId: request id for cloud_push
   */
  @Override
  public void onUnbind(Context context, int errorCode, String requestId) {
    Log.d("push", ">> unbind <<");
  }

@Override
public void onNotificationArrived(Context arg0, String arg1, String arg2,
		String arg3) {
	// TODO Auto-generated method stub
	
}

}

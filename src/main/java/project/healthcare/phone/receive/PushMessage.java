package project.healthcare.phone.receive;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.preference.Pref;

import com.wilimx.constants.Constants;
import com.wilimx.util.JSONUtil;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.SparseIntArray;

/**
 * 推送消息
 * 
 * @author xiao.yl
 * created at 2014-07-21 09:41
 */
public class PushMessage {

  /**
   * 显示通知
   * 
   * @param context 上下文
   * @param customContent 自定义内容
   */
  public static final void showNotification(Context context, String customContent) {
    JSONObject data = JSONUtil.parseJSON(customContent);
    JSONObject alertData = data.optJSONObject(KEY_MESSAGE);
    if (data != null) {
      if (data.optInt(CommonKeys.TYPE) == 0) {
        Pref.getAppPreference().setAidInfo(alertData.toString());
        Pref.getAppPreference().setIsNewAidInfo(true);
        NotificationManager manager = (NotificationManager) context
            .getSystemService(Context.NOTIFICATION_SERVICE);

        String title = makeAlertInfoTitle(alertData);
        manager.notify(1, makeAlertInfoNotification(context, title, alertData));
      } else {
        Pref.getAppPreference().setIsNewAidInfo(false);
        NotificationManager manager = (NotificationManager) context
            .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, makeCancleAlertInfoNotification(context, 
            alertData.optString(CommonKeys.TITLE), 
            alertData.optString(CommonKeys.CONTENT)));
      }
    }
  }

  /*
   * 生成救助信息通知
   */
  private static final Notification makeAlertInfoNotification(Context context, String title, JSONObject customData) {
    final String MESSAGE = makeAlertInfoContent(customData);
    Notification notification = new Notification();
    notification.icon = R.drawable.ic_launcher;
    notification.when = System.currentTimeMillis();
    notification.tickerText = MESSAGE;
    notification.defaults = Notification.DEFAULT_SOUND;
    notification.setLatestEventInfo(context,
        title,
        MESSAGE,
        PendingIntent.getActivity(context,
            0,
            new Intent(CommonActions.AID)
        .putExtra(CommonKeys.AID_INFO, customData.toString()),
            PendingIntent.FLAG_UPDATE_CURRENT));
    upgradeNotificationCount(1);
    return notification;
  }

  /*
   * 生成取消救助信息通知
   */
  private static final Notification makeCancleAlertInfoNotification(Context context, String title, String Content) {
    Notification notification = new Notification();
    notification.icon = R.drawable.ic_launcher;
    notification.when = System.currentTimeMillis();
    notification.tickerText = Content;
    notification.defaults |= Notification.DEFAULT_VIBRATE;
    notification.defaults |= Notification.DEFAULT_SOUND;
    notification.setLatestEventInfo(context,
        title,
        Content,
        PendingIntent.getActivity(context,
            0,
            new Intent(CommonActions.AID_CANCEL),
            PendingIntent.FLAG_UPDATE_CURRENT));
    upgradeNotificationCount(2);
    return notification;
  }

  /*
   * 生成救助标题
   */
  @SuppressLint("SimpleDateFormat") 
  private static String makeAlertInfoTitle(JSONObject data) {
    String title = Constants.EMPTY_TEXT;
    if (data != null) {
//      Date date = new Date(data.optLong(CommonKeys.TIME));
//      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH点mm分");
//      String time = simpleDateFormat.format(date);
      title = "微康紧急救助";
    }
    return title;
  }

  /*
   * 生成救助文本
   */
  @SuppressLint("SimpleDateFormat") 
  private static String makeAlertInfoContent(JSONObject data) {
    String title = Constants.EMPTY_TEXT;
    if (data != null) {
      title = data.optString(CommonKeys.NAME) + "于" + data.optString(CommonKeys.ADDRESS) + "发出紧急救助";
    }
    return title;
  }

  /**
   * 升级通知计数
   * 
   * @param type 通知类型
   */
  private static final void upgradeNotificationCount(int type) {
    synchronized (_notificationCountMap) {
      _notificationCountMap.put(type, _notificationCountMap.get(type) + 1);
    }
  }

  private PushMessage() {}

  private static final String KEY_MESSAGE = "custom_content"; // [json] 自定义内容

  // 通知技术映射
  private static final SparseIntArray _notificationCountMap = new SparseIntArray();

}

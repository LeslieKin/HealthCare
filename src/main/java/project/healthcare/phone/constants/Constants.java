package project.healthcare.phone.constants;

import project.healthcare.phone.R;

import android.content.Context;

public class Constants {

  public static interface AppType {
    int FAMILY    = 0;
    int PHONE     = 1;
    int COMMUNITY = 2;
  }

  public static interface CheckType {
    int USER_EXIST     = 0;
    int USER_NOT_EXIST = 1;
  }

  public static interface CountStatus {
    int USER_NOT_EXIST = 0;
    int INFO_LACK      = 1;
    int INFO_CHANGED   = 2;
    int INFO_COMPLETE  = 3;
    int LOCKED         = 4;
  }

  public static interface DetectType {
    int BLOOD_PRESSURE = 0;
    int HEART_RATE     = 1;
    int BLOOD_OXYGEN   = 2;
    int BLOOD_SUGAR    = 3;
    int TEMP           = 4;
    int WEIGHT         = 5;
  }

  public static interface ErrorCode {
    int PHONE_ERROR    = 0x00;
    int IDENTITY_ERROR = 0x01;
    int USER_NOT_EXIST = 0x10;
    int USER_EXIST     = 0x11;
    int ACCESS_FORBIT  = 0x12;
    int PASSWORD_ERROR = 0x20;
    int SERVER_ERROR   = 0x30;
    int NETWORK_ERROR  = 0x31;
    int TIMEOUT        = 0x40;
  }

  public static interface Gender {
    int MALE    = 0;
    int FEMALE  = 1;
    int UNKNOWN = 2;
  }

  public static interface MeasureType {
    int SYSTOLIC  = 0;
    int DIASTOLIC = 1;
  }

  public static interface ShareMode {
    int SHORT_MeSSAGE = 0;
    int MAIL          = 1;
    int WEIBO         = 2;
    int WECHAT        = 3;
    int QQ_ZONE       = 4;
  }

  public static interface HealthState {
    int GOOD   = 0;
    int NORMAL = 1;
    int POOL   = 2;
  }

  public static interface StatusCode {
    int SUCCESS = 0;
    int FAIL    = 1;
    int ERROR   = 2;
  }

  public static boolean isSuccess(int statusCode) {
    return statusCode == StatusCode.SUCCESS;
  }

  public static String obtainErrorMessage(Context context, int errorCode, int defMsgResid) {
    if (context == null) {
      return null;
    }
    return context.getString(getErrorMsgId(errorCode, defMsgResid));
  }

  public static Integer getErrorMsgId(int errorCode, Integer defMsgId) {
    switch (errorCode) {
    case ErrorCode.PHONE_ERROR:
      return R.string.tip_phone_error;

    case ErrorCode.IDENTITY_ERROR:
      return R.string.tip_identity_error;

    case ErrorCode.USER_NOT_EXIST:
      return R.string.tip_user_not_exist;

    case ErrorCode.USER_EXIST:
      return R.string.tip_user_exist;

    case ErrorCode.ACCESS_FORBIT:
      return R.string.tip_access_forbit;

    case ErrorCode.PASSWORD_ERROR:
      return R.string.tip_password_error;

    case ErrorCode.SERVER_ERROR:
      return R.string.tip_server_error;

    case ErrorCode.NETWORK_ERROR:
      return R.string.tip_network_error;

    case ErrorCode.TIMEOUT:
      return R.string.tip_timeout;
    }
    return defMsgId;
  }

  private Constants() {}

}

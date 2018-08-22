package project.healthcare.phone.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  public static final String format(Date date) {
    return _dateFormat.format(date);
  }

  private DateUtil() {}

  @SuppressLint("SimpleDateFormat")
  private static final SimpleDateFormat _dateFormat =
    new SimpleDateFormat("yyyy-MM-dd");

}

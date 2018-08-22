package project.healthcare.phone.util;

import java.util.List;

public class DataUtil {

  public static final String makeArrayString(double... array) {
    if (array != null && array.length != 0) {
      synchronized (mStringBuilder) {
        StringBuilder sb = mStringBuilder;
        sb.delete(0, sb.length());
  
        sb.append('[');
  
        for (double element : array) {
          sb
            .append(element)
            .append(',');
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(']');
        return sb.toString();
      }
    }
    return null;
  }

  public static final String makeArrayString(int... array) {
    if (array != null && array.length != 0) {
      synchronized (mStringBuilder) {
        StringBuilder sb = mStringBuilder;
        sb.delete(0, sb.length());

        sb.append('[');

        for (double element : array) {
          sb
            .append(element)
            .append(',');
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(']');
        return sb.toString();
      }
    }
    return null;
  }

  public static final String makeArrayString(List<Long> list) {
    if (list != null && list.size() != 0) {
      synchronized (mStringBuilder) {
        StringBuilder sb = mStringBuilder;
        sb.delete(0, sb.length());
  
        sb.append('[');
  
        for (long element : list) {
          sb
            .append(element)
            .append(',');
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(']');
        return sb.toString();
      }
    }
    return null;
  }

  private static final StringBuilder mStringBuilder = new StringBuilder();

}

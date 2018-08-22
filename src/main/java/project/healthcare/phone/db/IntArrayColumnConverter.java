package project.healthcare.phone.db;

import android.content.ContentValues;

import android.database.Cursor;

import com.wilimx.db.ColumnConverter;
import com.wilimx.db.annotation.DbColumnType;
import com.wilimx.db.constants.DbColumnTypes;

/**
 * double数组数据列转换器
 * 
 * @author xiao.yl
 * created at 2014-07-30 15:57
 */
@DbColumnType(DbColumnTypes.TEXT)
public class IntArrayColumnConverter extends ColumnConverter {

  @Override
  protected synchronized Object getColumnValueRaw(Object fieldValue) {
    int source[] = (int[]) fieldValue;

    if (source.length != 0) {
      StringBuilder sb = mDataBuilder;

      sb.delete(0, sb.length());

      for (int data : source) {
        sb
          .append(data)
          .append(',');
      }
      sb.delete(sb.length() - 1, sb.length());
      return sb.toString();
    }
    return null;
  }

  @Override
  protected Object getFieldValueRaw(Object columnValue) {
    String parts[] = ((String) columnValue).split(",");

    if (parts.length != 0) {
      final int SIZE = parts.length;
      int result[] = new int[SIZE];

      for (int i = 0; i < SIZE; ++i) {
        result[i] = Integer.parseInt(parts[i]);
      }
      return result;
    }
    return null;
  }

  @Override
  protected Object getFieldValueRaw(Cursor cursor, int columnIndex) {
    return getFieldValue(cursor.getString(columnIndex));
  }

  @Override
  protected void saveColumnValueRaw(Object columnValue, String columnName, ContentValues values) {
    values.put(columnName, (String) columnValue);
  }


  private final StringBuilder mDataBuilder = new StringBuilder();

}

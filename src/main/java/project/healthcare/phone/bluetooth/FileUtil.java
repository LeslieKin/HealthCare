package project.healthcare.phone.bluetooth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

/**
 * 文件工具
 * 
 * @author xiao.yl
 * created at 2013-10-21 17:56
 */
public final class FileUtil {

  /**
   * 复制文件
   * <p>WARNING: This method will close input stream while read finished.</p>
   * 
   * @param source 数据来源
   * @param target 目标文件
   */
  public static final void copy(InputStream source, File target) {
    OutputStream os = null;

    if (source == null || target == null) {
      return;
    }

    // Try to open target file output stream.
    try {
      os = new FileOutputStream(target);
    } catch (FileNotFoundException e) {
      Log.e(TAG, "target file " + target.getName() + " not found");
    }

    if (os != null) {
      byte buffer[] = new byte[BUFFER_LENGTH];
      int length;

      // Try to copy content of source into target.
      try {
        while ((length = source.read(buffer)) != -1) {
          os.write(buffer, 0, length);
        }
      } catch (IOException e) {
        Log.e(TAG, "copy file content with target '" + target.getAbsolutePath() + "' failed");
      } finally {
        // Try to close file output stream.
        try {
          os.close();
        } catch (IOException e) {
          Log.e(TAG, "close target file '" + target.getAbsolutePath() + "' failed");
        }
      }
    }

    // Try to close source inputSream.
    try {
      source.close();
    } catch (IOException e) {
      Log.e(TAG, "close source input sream failed");
    }
  }

  /**
   * 日志标签
   */
  private static final String TAG = "file util";
  /**
   * 缓冲区长度
   */
  private static final int BUFFER_LENGTH = 1024;

  private FileUtil() {}
}

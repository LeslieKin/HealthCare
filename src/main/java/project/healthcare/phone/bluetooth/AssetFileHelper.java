package project.healthcare.phone.bluetooth;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Asset文件辅助
 * 
 * @author xiao.yl
 * created at 2013-12-05 09:20
 */
public final class AssetFileHelper {
  /**
   * 构造函数
   *
   * @param folder 文件夹名称
   */
  public AssetFileHelper(String folder) {
    if (folder == null) {
      throw new IllegalArgumentException("invalid folder");
    }
    PAIRED_FOLDER = folder;
  }

  /**
   * 获取文件夹的绝对路径
   * <p>WARNING: This method may be a long-process method, it would better being
   * used at a separate thread.</p>
   * 
   * @param context 上下文
   * @return 文件夹的绝对路径
   */
  public final String getAbsolutePath(Context context) {
    // Check context.
    if (context == null) {
      throw new IllegalArgumentException("invalid context");
    }

    // Check and initialize folder path.
    if (APP_FILE_PATH == null) {
      String folderPath = context
        .getDir(PAIRED_FOLDER, Context.MODE_PRIVATE).getAbsolutePath();
      APP_FILE_PATH = folderPath.substring(0, folderPath.length() - PAIRED_FOLDER.length());
    }

    // Check and copy sub files.
    if (!checkFolder(context)) {
      copyFiles(context);
    }
    return APP_FILE_PATH + PAIRED_FOLDER;
  }

  /**
   * 获取已保存的绝对路径
   * 
   * @return 文件夹的绝对路径
   */
  public final String getSavedAbsolutePath() {
    if (APP_FILE_PATH != null) {
      return APP_FILE_PATH + PAIRED_FOLDER;
    }
    return null;
  }

  /**
   * 检查文件夹是否存在
   * 
   * @param context 上下文
   * @return 文件夹是否存在
   */
  private final boolean checkFolder(Context context) {
    String[] subFiles = context.getDir(PAIRED_FOLDER, Context.MODE_PRIVATE).list();
    return subFiles != null && subFiles.length > 0;
  }

  /**
   * 复制Asset目录下指定文件夹的子文件到应用私有目录下的相同名称文件夹内
   * 
   * @param context 上下文
   */
  private final void copyFiles(Context context) {
    AssetManager assetManager = context.getAssets();

    // Try to list files of paired folder under asset directory.
    String files[] = AssetUtil.deepFiles(assetManager, PAIRED_FOLDER);

    // Copy files to paired folder path.
    for (String file : files) {
      Log.w(TAG, "start copy file: " + file);
      try {
        FileUtil.copy(assetManager.open(file), new File(APP_FILE_PATH + file));
      } catch (IOException e) {
        Log.e(TAG, "copy asset file - " + file + " failed");
      }
    }
  }

  /**
   * 日志标签
   */
  private static final String TAG = "asset file helper";

  /**
   * 文件副本名称
   */
  private final String PAIRED_FOLDER;
  /**
   * 应用文件路径
   */
  private String APP_FILE_PATH = null;
}

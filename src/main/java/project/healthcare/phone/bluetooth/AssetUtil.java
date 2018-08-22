package project.healthcare.phone.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wilimx.constants.Constants;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Asset工具
 * 
 * @author xiao.yl
 * created at 2013-12-05 09:44
 */
public final class AssetUtil {
  /**
   * 列出Asset目录下指定文件目录的所有子文件相对路径
   * 
   * @param context 上下文
   * @param folder 需要列出子文件路径的文件夹
   * @return 所有子文件的相对路径
   */
  public static final String[] listFiles(Context context, String folder) {
    if (context != null) {
      return listFiles(context.getAssets(), folder);
    }
    return null;
  }

  /**
   * 列出Asset目录下指定文件目录的所有子文件相对路径
   * 
   * @param assetManager Asset管理器
   * @param folder 需要列出子文件路径的文件夹
   * @return 所有子文件的相对路径
   */
  public static final String[] listFiles(AssetManager assetManager, String folder) {
    if (assetManager != null && folder != null) {
      String files[] = null;

      // Try to list sub files of specific folder.
      try {
        files = assetManager.list(folder);
      } catch (IOException e) {
        Log.e(TAG, "list files of folder " + folder + " failed");
      }

      if (files != null) {
        List<String> result = new ArrayList<String>();
        String filePath;

        for (String subFile : files) {
          filePath = folder + '/' + subFile;

          if (!isFolder(assetManager, filePath)) {
            result.add(filePath);
          }
        }
        return result.toArray(Constants.EMPTY_STRING_ARRAY);
      }
    }
    return null;
  }

  /**
   * 列出Asset目录下指定文件目录的所有子文件及所有子目录的子文件的相对路径
   * 
   * @param context 上下文
   * @param folder 需要列出子文件路径的文件夹
   * @return 所有子文件的相对路径
   */
  public static final String[] deepFiles(Context context, String folder) {
    if (context != null) {
      return deepFiles(context.getAssets(), folder);
    }
    return null;
  }

  /**
   * 列出Asset目录下指定文件目录的所有子文件及所有子目录的子文件的相对路径
   * 
   * @param assetManager Asset管理器
   * @param folder 需要列出子文件路径的文件夹
   * @return 所有子文件的相对路径
   */
  public static final String[] deepFiles(AssetManager assetManager, String folder) {
    return deepFilesAsList(assetManager, folder).toArray(Constants.EMPTY_STRING_ARRAY);
  }

  public static final List<String> deepFilesAsList(AssetManager assetManager, String folder) {
    List<String> result = new ArrayList<String>();

    if (assetManager != null && folder != null) {
      String files[] = null;

      // Try to list sub files of specific folder.
      try {
        files = assetManager.list(folder);
      } catch (IOException e) {
        Log.e(TAG, "list files of folder " + folder + " failed");
      }

      if (files != null) {
        String filePath;

        for (String subFile : files) {
          filePath = folder + '/' + subFile;

          if (isFolder(assetManager, filePath)) {
            result.addAll(deepFilesAsList(assetManager, filePath));
          } else {
            result.add(filePath);
          }
        }
      }
    }
    return result;
  }

  /**
   * 判断指定文件夹是否为文件目录
   * 
   * @param assetManager Asset管理器
   * @param folder 需要判断的文件夹名称
   * @return 文件夹是否为文件目录
   */
  private static final boolean isFolder(AssetManager assetManager, String folder) {
    String files[] = null;

    // Try to list files of specific folder under asset directory.
    try {
      files = assetManager.list(folder);
    } catch (IOException e) {
    }

    if (files != null && files.length > 0) {
      return true;
    }
    return false;
  }

  private static final String TAG = "asset util";
}

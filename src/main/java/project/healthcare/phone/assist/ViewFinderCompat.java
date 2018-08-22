package project.healthcare.phone.assist;

import android.view.View;

/**
 * 视图查找器组件
 * 
 */
public interface ViewFinderCompat {

  /**
   * 通过视图ID查找视图
   * 
   * @param id 视图ID
   * @return 视图
   */
  View findViewById(int id);

  /**
   * 通过视图ID查找视图
   * 
   * @param id 视图ID
   * @param viewClass 视图类型
   * @return 视图
   */
  <T extends View> T findViewById(int id, Class<T> viewClass);

  /**
   * 通过视图ID、父视图ID查找视图
   * 
   * @param id
   * @param parentId
   * @return
   */
  View findViewById(int id, int parentId);

  /**
   * 通过视图ID、父视图ID查找视图
   * 
   * @param id
   * @param parentId
   * @param viewClass 视图类型
   * @return 视图
   */
  <T extends View> T findViewById(int id, int parentId, Class<T> viewClass);

}

package project.healthcare.phone.assist;

import android.app.Activity;
import android.view.View;

public class ViewFinder implements ViewFinderCompat{

  @Override
  public final View findViewById(int id) {
    return IMPL.findViewById(id);
  }

  @Override
  public final <T extends View> T findViewById(int id, Class<T> viewClass) {
    return IMPL.findViewById(id, viewClass);
  }

  @Override
  public final View findViewById(int id, int parentId) {
    return IMPL.findViewById(id, parentId);
  }

  @Override
  public final <T extends View> T findViewById(int id, int parentId, Class<T> viewClass) {
    return IMPL.findViewById(id, parentId, viewClass);
  }

  /**
   * 设置活动上下文
   * 
   * @param activity 活动
   */
  public final void setActivityContext(Activity activity) {
    if (activity == null) {
      if (IMPL != BLANK_VIEW_FINDER) {
        IMPL = BLANK_VIEW_FINDER;
      }
    } else {
      if (IMPL instanceof ActivityContextViewFinderCompat) {
        ((ActivityContextViewFinderCompat) IMPL).setContext(activity);
      } else {
        IMPL = new ActivityContextViewFinderCompat().setContext(activity);
      }
    }
  }

  /**
   * 设置视图上下文
   * 
   * @param view 试图上下文
   */
  public final void setViewContext(View view) {
    if (view == null) {
      if (IMPL != BLANK_VIEW_FINDER) {
        IMPL = BLANK_VIEW_FINDER;
      }
    } else {
      if (IMPL instanceof ViewContextViewFinderCompat) {
        ((ViewContextViewFinderCompat) IMPL).setContext(view);
      } else {
        IMPL = new ViewContextViewFinderCompat().setContext(view);
      }
    }
  }

  /**
   * 判断视图查找器是否包含上下文
   * 
   * @return 是否包含上下文
   */
  public final boolean hasContext() {
    return IMPL != BLANK_VIEW_FINDER;
  }


  /**
   * 空白视图查找器
   */
  private static final ViewFinderCompat BLANK_VIEW_FINDER = new BlankViewFinderCompat();

  /**
   * 视图查找器实例
   */
  private ViewFinderCompat IMPL = BLANK_VIEW_FINDER;


  /* ---------------- 空白视图查找器组件 ---------------- */
  /**
   * 空白视图查找器组件
   * 
   * @author xiao.yl
   * created at 2014-02-23 15:20
   */
  private static final class BlankViewFinderCompat implements ViewFinderCompat {

    @Override
    public final View findViewById(int id) {
      return null;
    }

    @Override
    public final <T extends View> T findViewById(int id, Class<T> viewClass) {
      return null;
    }

    @Override
    public final View findViewById(int id, int parentId) {
      return null;
    }

    @Override
    public final <T extends View> T findViewById(int id, int parentId, Class<T> viewClass) {
      return null;
    }

  }


  /* ------------- 基于视图的视图查找器组件 ------------- */
  /**
   * 基于视图的视图查找器组件
   * 
   * @param<CONTEXT> 上下文类型
   * 
   * @author xiao.yl
   * created at 2014-02-23 15:55
   */
  private static abstract class ViewBasedViewFinderCompat<CONTEXT> implements ViewFinderCompat {

    @Override
    public final <T extends View> T findViewById(int id, Class<T> viewClass) {
      return viewClass.cast(findViewById(id));
    }

    @Override
    public final View findViewById(int id, int parentId) {
      View parentView = findViewById(id);

      if (parentView != null) {
        return parentView.findViewById(id);
      }
      return null;
    }

    @Override
    public final <T extends View> T findViewById(int id, int parentId, Class<T> viewClass) {
      View parentView = findViewById(id);

      if (parentView != null) {
        return viewClass.cast(parentView.findViewById(id));
      }
      return null;
    }

    /**
     * 设置上下文
     * 
     * @param context 上下文
     */
    public final ViewBasedViewFinderCompat<CONTEXT> setContext(CONTEXT context) {
      mContext = context;
      return this;
    }

    /**
     * 获取上下文
     * 
     * @return 上下文
     */
    public final CONTEXT getContext() {
      return mContext;
    }


    /**
     * 上下文
     */
    private CONTEXT mContext = null;

  }


  /* ------------- 活动上下文视图查找器组件 ------------- */
  /**
   * 活动上下文视图查找器组件
   * 
   * @author xiao.yl
   * created at 2014-02-23 15:23
   */
  private static final class ActivityContextViewFinderCompat extends ViewBasedViewFinderCompat<Activity> {

    @Override
    public final View findViewById(int id) {
      return getContext().findViewById(id);
    }

  }


  /* ------------- 视图上下文视图查找器组件 ------------- */
  /**
   * 视图上下文视图查找器组件
   * 
   * @author xiao.yl
   * created at 2014-02-23 15:47
   */
  private static final class ViewContextViewFinderCompat extends ViewBasedViewFinderCompat<View> {

    @Override
    public final View findViewById(int id) {
      return getContext().findViewById(id);
    }

  }

}

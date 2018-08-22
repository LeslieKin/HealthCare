package project.healthcare.phone.assist;

import project.healthcare.phone.constants.Direction;

import com.wilimx.constants.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ViewHelper {


  /**
   * 构造函数
   */
  public ViewHelper() {
  }

  /**
   * 构造函数
   * 
   * @param activity 活动
   */
  public ViewHelper(Activity activity) {
    setActivityContext(activity);
  }

  /**
   * 构造函数
   * 
   * @param view 视图
   */
  public ViewHelper(View view) {
    setViewContext(view);
  }

  /**
   * 设置活动上下文
   * 
   * @param activity 活动
   * @return 视图帮助器本身
   */
  public final ViewHelper setActivityContext(Activity activity) {
    mViewFinder.setActivityContext(activity);
    mContext = activity;
    return this;
  }

  /**
   * 设置视图上下文
   * 
   * @param view 视图
   * @return 视图帮助器本身
   */
  public final ViewHelper setViewContext(View view) {
    mViewFinder.setViewContext(view);
    mContext = view == null ? null : view.getContext();
    return this;
  }

  /* View Related functions. */
  /**
   * 显示视图
   * 
   * @param viewId 需要显示的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper show(int... viewIds) {
    return setVisibility(View.VISIBLE, viewIds);
  }

  /**
   * 隐藏视图
   * 
   * @param viewId 需要显示的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper hide(int... viewIds) {
    return setVisibility(View.GONE, viewIds);
  }

  /**
   * 设置视图的可见性
   * 
   * @param visible 视图的可见性
   * @param viewId 需要设置可见性的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper setVisible(boolean visible, int... viewIds) {
    if (visible) {
      show(viewIds);
    } else {
      hide(viewIds);
    }
    return this;
  }

  /**
   * 设置视图的可见性
   * 
   * @param visibility 视图的可见性
   * @param viewId 需要设置可见性的所有视图ID
   * @return 视图帮助器本身
   * 
   * @see View.VISIBLE
   * @see View.INVISIBLE
   * @see View.GONE
   */
  public final ViewHelper setVisibility(int visibility, int... viewIds) {
    if (viewIds != null) {
      View v;

      for (int viewId : viewIds) {
        if ((v = mViewFinder.findViewById(viewId)) != null) {
          v.setVisibility(visibility);
        }
      }
    }
    return this;
  }

  /**
   * 判断视图是否可见
   * 
   * @param viewId 视图ID
   * @return 判断结果
   */
  public final boolean isVisible(int viewId) {
    View v = mViewFinder.findViewById(viewId);

    if (v != null) {
      return v.getVisibility() == View.VISIBLE;
    }
    return false;
  }

  /**
   * 设置视图可用
   * 
   * @param viewId 需要设置可用的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper enable(int... viewIds) {
    return setEnabled(true, viewIds);
  }

  /**
   * 设置视图不可用
   * 
   * @param viewId 需要设置不可用的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper disable(int... viewIds) {
    return setEnabled(false, viewIds);
  }

  /**
   * 设置视图的可用性
   * 
   * @param enabled 视图的可用性
   * @param viewIds 需要设置可用性的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper setEnabled(boolean enabled, int... viewIds) {
    if (viewIds != null) {
      View v;

      for (int viewId : viewIds) {
        if ((v = mViewFinder.findViewById(viewId)) != null) {
          v.setEnabled(enabled);
        }
      }
    }
    return this;
  }

  /**
   * 判断视图是否可用
   * 
   * @param viewId 视图ID
   * @return 判断结果
   */
  public final boolean isEnabled(int viewId) {
    View v = mViewFinder.findViewById(viewId);

    if (v != null) {
      return v.isEnabled();
    }
    return false;
  }

  /* Text Related functions. */
  /**
   * 获取文本内容
   * 
   * @param textId 文本视图ID
   * @return 文本内容
   */
  public final String getText(int textId) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      return v.getText().toString();
    }
    return Constants.EMPTY_TEXT;
  }

  /**
   * 设置文本内容
   * 
   * @param textId 文本视图ID
   * @param resid 文本内容资源ID
   * @return 视图帮助器本身
   */
  public final ViewHelper setText(int textId, int resid) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      v.setText(resid);
    }
    return this;
  }

  /**
   * 设置文本内容
   * 
   * @param textId 文本视图ID
   * @param content 文本内容
   * @return 视图帮助器本身
   */
  public final ViewHelper setText(int textId, CharSequence content) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      v.setText(content);
    }
    return this;
  }

  /**
   * 设置格式化文本内容
   * 
   * @param textId 文本视图ID
   * @param formatResid 格式文本内容资源ID
   * @param params 格式参数
   * @return 视图帮助器本身
   */
  public final ViewHelper setFormatText(int textId, int formatResid, Object... params) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null && mContext != null) {
      v.setText(mContext.getResources().getString(formatResid, params));
    }
    return this;
  }

  /**
   * 设置HTML文本内容
   * 
   * @param textId 文本视图ID
   * @param resid 文本内容资源ID
   * @return
   */
  public final ViewHelper setHTMLText(int textId, int resid) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null && mContext != null) {
      v.setText(Html.fromHtml(mContext.getResources().getString(textId)));
    }
    return this;
  }

  /**
   * 设置HTML文本内容
   * 
   * @param textId 文本视图ID
   * @param content 文本内容
   * @return 视图帮助器本身
   */
  public final ViewHelper setHTMLText(int textId, String content) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      v.setText(Html.fromHtml(content));
    }
    return this;
  }

  /**
   * 设置格式化HTML文本内容
   * 
   * @param textId 文本视图ID
   * @param formatResid 格式文本内容资源ID
   * @param params 格式参数
   * @return 视图帮助器本身
   */
  public final ViewHelper setFormatHTMLText(int textId, int formatResid, Object... params) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null && mContext != null) {
      v.setText(Html.fromHtml(mContext.getResources().getString(formatResid, params)));
    }
    return this;
  }

  /**
   * 设置文字视图周围的图片
   * 
   * @param textId 文本视图ID
   * @param direction 图片显示位置
   * @param resid 图片资源ID
   * @return 视图帮助器本身
   */
  public final ViewHelper setTextDrawable(int textId, Direction direction, int resid) {
    if (mContext != null) {
      setTextDrawable(textId, direction, mContext.getResources().getDrawable(resid));
    }
    return this;
  }

  /**
   * 设置文字视图周围的图片
   * 
   * @param textId 文本视图ID
   * @param direction 图片显示位置
   * @param drawable 可绘制图形
   * @return 视图帮助器本身
   */
  public final ViewHelper setTextDrawable(int textId, Direction direction, Drawable drawable) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      if (drawable != null) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
      }

      switch (direction) {
      case LEFT:
        v.setCompoundDrawables(drawable, null, null, null);
        break;

      case TOP:
        v.setCompoundDrawables(null, drawable, null, null);
        break;

      case RIGHT:
        v.setCompoundDrawables(null, null, drawable, null);
        break;

      case BOTTOM:
        v.setCompoundDrawables(null, null, null, drawable);
        break;
      }
    }
    return this;
  }

  /**
   * 缓存文本视图内容
   * 
   * @param textId 文本视图ID
   * @param sessionKey SESSION键名称
   * @return 视图帮助器本身
   */
  public final ViewHelper cacheText(int textId, String sessionKey) {
    TextView v = mViewFinder.findViewById(textId, TextView.class);

    if (v != null) {
      v.addTextChangedListener(SessionDataTextWatcher.init(sessionKey));
    }
    return this;
  }

  /* Compound Button Related functions. */
  /**
   * 选中视图
   * 
   * @param viewIds 需要选中的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper check(int... viewIds) {
    return setChecked(true, viewIds);
  }

  /**
   * 取消选中视图
   * 
   * @param viewIds 需要取消选中的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper uncheck(int... viewIds) {
    return setChecked(false, viewIds);
  }

  /**
   * 设置视图选中状态
   * 
   * @param checked 选中状态
   * @param viewIds 需要设置选中状态的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper setChecked(boolean checked, int... viewIds) {
    if (viewIds != null) {
      CompoundButton v;

      for (int viewId : viewIds) {
        if ((v = mViewFinder.findViewById(viewId, CompoundButton.class)) != null) {
          v.setChecked(checked);
        }
      }
    }
    return this;
  }

  /**
   * 获取视图的选中状态
   * 
   * @param viewId 视图ID
   * @return 视图的选中状态
   */
  public final boolean isChecked(int viewId) {
    CompoundButton v = mViewFinder.findViewById(viewId, CompoundButton.class);

    if (v != null) {
      return v.isChecked();
    }
    return false;
  }

  /* Event Related functions. */
  /**
   * 设置单击事件处理器
   * 
   * @param listener 单击事件处理器
   * @return 视图帮助器本身
   */
  public final ViewHelper setClickHandler(OnClickListener listener) {
    mClickHandler = listener;
    return this;
  }

  /**
   * 帮定视图单击事件
   * <p>使用这个方法之前，必须调用setClickHandler方法设置单击事件处理器</p>
   * 
   * @param viewIds 需要帮顶的所有视图ID
   * @return 视图帮助器本身
   */
  public final ViewHelper bindClickEvents(int... viewIds) {
    if (mClickHandler != null && viewIds != null) {
      View v;

      for (int viewId : viewIds) {
        if ((v = mViewFinder.findViewById(viewId)) != null) {
          v.setOnClickListener(mClickHandler);
        }
      }
    }
    return this;
  }

  /* other functions. */
  /**
   * 获取整数值
   * 
   * @param resid 整数内容资源ID
   * @return 整数值
   */
  public int getInteger(int resid) {
    if (mContext != null) {
      return Integer.parseInt(mContext.getResources().getString(resid), 10);
    }
    return 0;
  }


  // 视图查找器
  private final ViewFinder mViewFinder = new ViewFinder();

  // 上下文
  private Context mContext = null;

  // 单击事件处理器（监听器）
  private OnClickListener mClickHandler = null;
}

package project.healthcare.phone.assist;

import project.healthcare.phone.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

/**
 * 表单辅助
 * 
 * @author xiao.yl
 * created at 2014-05-13 17:37
 */
public class FormAssist {

  /**
   * 文本格式检查器
   * 
   * @author xiao.yl
   * created at 2014-05-13 15:23
   */
  public static interface TextFormatChecker {
    /**
     * 检查文本内容格式是否正确
     * 
     * @param checkItemId 文本视图ID
     * @param content 文本内容
     * @return 检查结果
     */
    boolean checkText(int checkItemId, String content);
  }

  /**
   * 添加文本检查项
   * 
   * @param id 文本视图ID
   * @param title 文本标题
   * @return 表单辅助本身
   */
  public FormAssist addTextCheckItem(int id, int title) {
    TextCheckItem checkItem = new TextCheckItem();
    checkItem.init(id, title);
    mCheckItemMap.put(id, checkItem);
    mBufferedCheckItem = checkItem;
    return this;
  }

  /**
   * 添加单选按钮检查项
   * 
   * @param groupId   单选按钮组ID
   * @param title     单选按钮标题ID
   * @param optionIds 单选按钮ID列表
   * @return 表单辅助本身
   */
  public FormAssist addRadioGroupCheckItem(int groupId, int title, int... optionIds) {
    mCheckItemMap.put(groupId, new RadioGroupCheckItem()
      .setOptionIds(optionIds)
      .init(groupId, title));
    return this;
  }

  /**
   * 添加选项集合检查项
   * 
   * @param groupId 选项组ID
   * @param title   选项组标题
   * @param max     最大同时选中选项个数
   * @param min     最小同时选中选项个数
   * @param optionIds 选项按钮ID列表
   * @return 表单辅助本身
   */
  public FormAssist addOptionGroupCheckItem(int groupId, int title, int max, int min, int... optionIds) {
    mCheckItemMap.put(groupId, new OptionGroupCheckItem()
      .initCheckCount(max, min)
      .init(groupId, title));
    return this;
  }

  /**
   * 添加选项集合检查项
   * 
   * @param groupId 选项组ID
   * @param title   选项组标题
   * @param optionIds 选项按钮ID列表
   * @return 表单辅助本身
   */
  public FormAssist addDefaultOptionGroupCheckItem(int groupId, int title, int... optionIds) {
    return addOptionGroupCheckItem(groupId, title, -1, 0, optionIds);
  }

  /**
   * 添加选项检查项
   * 
   * @param checkItemId 选项按钮ID
   * @param title 选项按钮标题
   * @return 表单辅助本身
   */
  public FormAssist addOptoinCheckItem(int checkItemId, int title) {
    return addOptoinCheckItem(checkItemId, title, -1);
  }

  /**
   * 添加选项检查项
   * 
   * @param checkItemId 选项按钮ID
   * @param title 选项按钮标题
   * @param customErrorFormat 自定义错误信息格式ID（只包含一个%s）
   * @return 表单辅助本身
   */
  public FormAssist addOptoinCheckItem(int checkItemId, int title, int customErrorFormat) {
    mCheckItemMap.put(checkItemId, new OptionCheckItem()
      .setCustomErrorFormat(customErrorFormat)
      .init(checkItemId, title));
    return this;
  }

  /**
   * 添加文本内容为空检查
   * 
   * @return 表单辅助本身
   */
  public FormAssist addEmptyCheck() {
    if (mBufferedCheckItem != null) {
      mBufferedCheckItem.setEmptyCheckEnabled(true);
    }
    return this;
  }

  /**
   * 添加最小长度检查
   * 
   * @param lengthId 长度ID
   * @return 表单辅助本身
   */
  public FormAssist addMinLengthCheck(int lengthId) {
    if (mBufferedCheckItem != null && mResources != null) {
      mBufferedCheckItem.setMinLength(Integer.parseInt(mResources.getString(lengthId), 10));
    }
    return this;
  }

  /**
   * 添加最大长度检查
   * 
   * @param lengthId 长度ID
   * @return 表单辅助本身
   */
  public FormAssist addMaxLengthCheck(int lengthId) {
    if (mBufferedCheckItem != null && mResources != null) {
      mBufferedCheckItem.setMaxLength(Integer.parseInt(mResources.getString(lengthId), 10));
    }
    return this;
  }

  /**
   * 添加文本格式检查
   * 
   * @param formatId 文本格式ID
   * @return 表单辅助本身
   */
  public FormAssist addFormatCheck(int formatId) {
    if (mBufferedCheckItem != null) {
      mBufferedCheckItem.setFormat(formatId);
    }
    return this;
  }

  /**
   * 添加文本格式检查
   * 
   * @param checker 文本格式检查器
   * @return 表单辅助本身
   */
  public FormAssist addFormatCheck(TextFormatChecker checker) {
    if (mBufferedCheckItem != null) {
      mBufferedCheckItem.setFormatChecker(checker);
    }
    return this;
  }

  /**
   * 设置内容为空错误信息模板
   * <p>包含一个%s</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setEmptyErrorPattern(int patternId) {
    mEmptyErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置最小长度错误信息模板
   * <p>包含一个%s和一个%d</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setMinLengthErrorPattern(int patternId) {
    mMinLengthErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置最大长度错误信息模板
   * <p>包含一个%s和一个%d</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setMaxLengthErrorPattern(int patternId) {
    mMaxLengthErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置格式错误信息模板
   * <p>包含一个%s</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setFormatErrorPattern(int patternId) {
    mFormatErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置最小选中选项按钮错误信息模板
   * <p>包含一个%s和一个%d</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setMinOptionErrorPattern(int patternId) {
    mMinOptionErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置最大选中选项按钮错误信息模板
   * <p>包含一个%s和一个%d</p>
   * 
   * @param patternId 模板ID
   * @return 表单辅助本身
   */
  public FormAssist setMaxOptionErrorPattern(int patternId) {
    mMaxOptionErrorPatternId = patternId;
    return this;
  }

  /**
   * 设置活动
   * 
   * @param activity 活动
   * @return 表单辅助本身
   */
  public FormAssist setActivity(Activity activity) {
    mViewHelper.setActivityContext(activity);
    setContext(activity);
    return this;
  }

  /**
   * 设置内容视图
   * 
   * @param contentView 内容视图
   * @return 表单辅助本身
   */
  public FormAssist setContentView(View contentView) {
    mViewHelper.setViewContext(contentView);
    if (contentView != null) {
      setContext(contentView.getContext());
    }
    return this;
  }

  /**
   * 检查表单内容是否正确填写
   * 
   * @return 检查结果
   */
  public boolean check() {
    for (int i = 0, size = mCheckItemMap.size(); i < size; ++i) {
      if (!mCheckItemMap.valueAt(i).check()) {
        mErrorInfo = mCheckItemMap.valueAt(i).getErrorInfo();
        return false;
      }
    }
    return true;
  }

  /**
   * 检查表单项是否正确填写
   * 
   * @param id 表单项ID
   * @return 检查结果
   */
  public boolean check(int id) {
    CheckItem checkItem = mCheckItemMap.get(id);
    boolean result = checkItem.check();

    if (!result) {
      mErrorInfo = checkItem.getErrorInfo();
    }
    return result;
  }

  /**
   * 获取错误信息
   * 
   * @return 错误信息
   */
  public String getErrorInfo() {
    return mErrorInfo;
  }

  /*
   * 设置上下文
   * 
   * @param context 上下文
   */
  private void setContext(Context context) {
    if (context == null) {
      mResources = null;
    } else {
      mResources = context.getResources();
    }
  }

  // 错误信息
  private String mErrorInfo = null;

  // 资源
  private Resources mResources = null;

  // 视图帮助器
  private ViewHelper mViewHelper = new ViewHelper();

  // 检查项映射
  private SparseArray<CheckItem> mCheckItemMap = new SparseArray<CheckItem>();

  // 内容为空错误信息模板ID
  private int mEmptyErrorPatternId     = R.string.format_empty_error;

  // 最小长度错误信息模板ID
  private int mMinLengthErrorPatternId = R.string.format_min_length_error;

  // 最大长度错误信息模板ID
  private int mMaxLengthErrorPatternId = R.string.format_max_length_error;

  // 格式错误信息模板ID
  private int mFormatErrorPatternId    = R.string.format_format_error;

  // 最小选项错误信息模板ID
  private int mMinOptionErrorPatternId = R.string.format_min_option_error;

  //最大选项错误信息模板ID
  private int mMaxOptionErrorPatternId = R.string.format_max_option_error;

  // 文本检查器缓存
  private TextCheckItem mBufferedCheckItem = null;

  /**
   * 检查项
   * 
   * @author xiao.yl
   * created at 2014-05-13 15:29
   */
  private abstract class CheckItem {

    /**
     * 检查表单项内容是否正确
     * 
     * @return 检查结果
     */
    abstract boolean check();

    /**
     * 初始化
     * 
     * @param checkItemId    检查项ID
     * @param checkItemTitle 检查项标题ID
     * @return 检查项本身
     */
    CheckItem init(int checkItemId, int checkItemTitle) {
      mCheckItemId = checkItemId;
      mCheckItemTitle = checkItemTitle;
      return this;
    }

    /**
     * 获取错误信息
     * 
     * @return 错误信息
     */
    String getErrorInfo() {
      return mErrorInfo;
    }

    /**
     * 获取检查项ID
     * 
     * @return 检查项ID
     */
    protected int getCheckItemId() {
      return mCheckItemId;
    }

    /**
     * 设置错误信息（无检查）
     * <p>模板中只包含一个%s</p>
     * 
     * @param patternId 模板ID
     */
    protected void setErrorInfoRaw(int patternId) {
      mErrorInfo = mResources.getString(patternId, mResources.getString(mCheckItemTitle));
    }

    /**
     * 生成错误信息（无检查）
     * <p>模板中包含一个%s和一个%d</p>
     * 
     * @param patternId 模板ID
     * @param count 计数
     */
    protected void setErrorInfoRaw(int patternId, int count) {
      mErrorInfo = mResources.getString(patternId, mResources.getString(mCheckItemTitle), count);
    }

    // 检查项ID
    private int mCheckItemId;

    // 检查项标题ID
    private int mCheckItemTitle;

    // 错误信息
    private String mErrorInfo;

  }


  /**
   * 文本检查项
   * 
   * @author xiao.yl
   * created at 2014-05-13 16:00
   */
  private class TextCheckItem extends CheckItem {

    @Override
    boolean check() {
      if (mResources != null) {
        String content = mViewHelper.getText(getCheckItemId());

        if (mEmptyCheckEnabled && TextUtils.isEmpty(content)) {
          setErrorInfoRaw(mEmptyErrorPatternId);
          return false;
        }
        if (mMinLength != -1 && (content == null || content.length() < mMinLength)) {
          setErrorInfoRaw(mMinLengthErrorPatternId, mMinLength);
          return false;
        }
        if (mMaxLength != -1 && (content == null || content.length() > mMaxLength)) {
          setErrorInfoRaw(mMaxLengthErrorPatternId, mMaxLength);
          return false;
        }
        if (mFormatId != -1) {
          if (content != null && !content.matches(mResources.getString(mFormatId))) {
            setErrorInfoRaw(mFormatErrorPatternId);
            return false;
          }
        } else if (mTextFormatChecker != null && !mTextFormatChecker.checkText(getCheckItemId(), content)) {
          setErrorInfoRaw(mFormatErrorPatternId);
          return false;
        }
        return true;
      }
      return false;
    }

    /**
     * 设置内容为空检查是否开启
     * 
     * @param b 内容为空检查是否开启
     * @return 文本检查项本身
     */
    TextCheckItem setEmptyCheckEnabled(boolean b) {
      mEmptyCheckEnabled = b;
      return this;
    }

    /**
     * 设置最小长度
     * 
     * @param length 最小长度
     * @return 文本检查项本身
     */
    TextCheckItem setMinLength(int length) {
      if (length > 0) {
        mMinLength = length;
      }
      return this;
    }

    /**
     * 设置最大长度
     * 
     * @param length 最大长度
     * @return 文本检查项本身
     */
    TextCheckItem setMaxLength(int length) {
      if (length > 0) {
        mMaxLength = length;
      }
      return this;
    }

    /**
     * 设置内容格式
     * 
     * @param formatId 内容格式ID
     * @return 文本检查项本身
     */
    TextCheckItem setFormat(int formatId) {
      if (formatId > 0) {
        mFormatId = formatId;
      }
      return this;
    }

    /**
     * 设置文本检查器
     * 
     * @param checker 文本检查器
     * @return 文本检查项本身
     */
    TextCheckItem setFormatChecker(TextFormatChecker checker) {
      mTextFormatChecker = checker;
      return this;
    }

    // 内容是否为空检查
    private boolean mEmptyCheckEnabled = false;

    // 最小长度
    private int mMinLength = -1;

    // 最大长度
    private int mMaxLength = -1;

    // 文本格式ID
    private int mFormatId = -1;

    // 文本检查器
    private TextFormatChecker mTextFormatChecker = null;

  }


  /**
   * 单选按钮集合检查项
   * 
   * @author xiao.yl
   * created at 2014-05-13 16:49
   */
  private class RadioGroupCheckItem extends CheckItem {

    @Override
    boolean check() {
      if (mOptionIds != null) {
        for (int id : mOptionIds) {
          if (mViewHelper.isChecked(id)) {
            return true;
          }
        }
      }
      if (mResources != null) {
        setErrorInfoRaw(mEmptyErrorPatternId);
      }
      return false;
    }

    /**
     * 设置选项ID列表
     * 
     * @param ids 选项ID列表
     * @return 单选按钮检查项本身
     */
    RadioGroupCheckItem setOptionIds(int... ids) {
      mOptionIds = ids;
      return this;
    }

    /**
     * 获取ID列表
     * 
     * @return ID列表
     */
    int[] getOptionIds() {
      return mOptionIds;
    }

    // 选项按钮ID列表
    private int mOptionIds[] = null;

  }

  /**
   * 选项组检查项
   * 
   * @author xiao.yl
   * created at 2014-05-13 17:12
   */
  private class OptionGroupCheckItem extends RadioGroupCheckItem {

    @Override
    boolean check() {
      int checkCount = 0;

      if (getOptionIds() != null) {
        for (int id : getOptionIds()) {
          if (mViewHelper.isChecked(id)) {
            ++checkCount;
          }
        }
      }
      if (mMinCheckCount != -1 && checkCount < mMinCheckCount) {
        if (mResources != null) {
          setErrorInfoRaw(checkCount == 0 ? mEmptyErrorPatternId : mMinOptionErrorPatternId);
        }
        return false;
      }
      if (mMaxCheckCount != -1 && checkCount > mMaxCheckCount) {
        setErrorInfoRaw(mMaxOptionErrorPatternId);
        return false;
      }
      return true;
    }

    /**
     * 初始化选中个数
     * 
     * @param min 最小选中个数
     * @param max 最大选中个数
     * @return 选项组检查项本身
     */
    OptionGroupCheckItem initCheckCount(int max, int min) {
      if (min >= 0) {
        mMinCheckCount = min;
      }
      if (max > 0) {
        mMaxCheckCount = max;
      }
      return this;
    }

    // 最小选中个数
    private int mMinCheckCount = -1;

    // 最大选中个数
    private int mMaxCheckCount = -1;

  }

  /**
   * 选项按钮检查项
   * 
   * @author xiao.yl
   * created at 2014-05-13 17:31
   */
  private class OptionCheckItem extends CheckItem {

    @Override
    boolean check() {
      if (mViewHelper.isChecked(getCheckItemId())) {
        return true;
      }
      if (mResources != null) {
        setErrorInfoRaw(
          mCustomErrorFromatId == -1 ? mEmptyErrorPatternId : mCustomErrorFromatId);
      }
      return false;
    }

    /**
     * 设置自定义错误信息格式ID
     * 
     * @param formatId 自定义错误信息格式ID
     * @return 选项按钮检查项本身
     */
    OptionCheckItem setCustomErrorFormat(int formatId) {
      if (formatId > 0) {
        mCustomErrorFromatId = formatId;
      }
      return this;
    }

    // 自定义错误信息格式ID
    private int mCustomErrorFromatId = -1;
  }

}

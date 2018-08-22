package project.healthcare.phone.ui.dialog;

import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.page.UserListPage;
import android.content.DialogInterface;
import android.os.Bundle;

import com.wilimx.app.BaseDialogFragment;
import com.wilimx.app.adapter.SimpleListAdapter;
import com.wilimx.constants.Constants;

import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 用户列表对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-04-04 11:49
 * @param <T>
 */
public final class UserListDialogFragment<T> extends BaseDialogFragment<UserListPage> {

    /**
     * 用户信息管理器接口
     * 
     * @author xiao.yl
     * created at 2014-04-04 15:22
     */
    public static interface UserInfoManager<T> {

      /**
       * 获取用户信息
       * 
       * @param userCount 用户账号
       * @return 用户信息
       */
      String getUserInfo(T userCount);

      /**
       * 删除用户
       * 
       * @param userCount 用户账号
       */
      void deleteUser(T userCount);

    }

    /**
     * 用户选中监听器接口
     */
    public static interface OnUserSelectedListener<T> {
      /**
       * 用户选中回调
       * 
       * @param userCount 用户账号
       */
      void onUserSelected(T userCount);
    }

    /**
     * 对话框消失
     */
    public static interface OnDialogDismissListener {
      
      void onDialogDismiss();
    }

    @Override
    public void dismiss() {
      mOnDialogDismissListener.onDialogDismiss();
      super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
      mOnDialogDismissListener.onDialogDismiss();
      super.onCancel(dialog);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      adjustWindowPosition();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
      if (mCountList.size() > 0) {
        super.show(manager, tag);
      }
    }

    /**
     * 设置对齐视图
     * 
     * @param alignView 对齐视图
     */
    public final void setAlignView(View alignView) {
      mAlignView = alignView;
    }

    /**
     * 设置用户信息管理器
     * 
     * @param manager 用户信息管理器
     */
    public final void setUserInfoManager(UserInfoManager<T> manager) {
      mUserInfoManager = manager;
    }

    /**
     * 设置用户选中监听器
     * 
     * @param 用户选中监听器
     */
    public final void setOnUserSelectedListener(OnUserSelectedListener<T> listener) {
      mUserSelectedListener = listener;
    }

    public final void setOnDialogDismissListener(OnDialogDismissListener listener) {
      mOnDialogDismissListener = listener;
    }

    /**
     * 设置账号列表
     * 
     * @param countList 账号列表
     */
    public final void setCountList(List<T> countList) {
      mCountList = countList;
    }

    @Override
    protected  final int getContentResId() {
      return R.layout.dialog_user_list;
    }
    @Override
    protected void onPageBuild(UserListPage page) {
      initUserList();
    }

    @Override
    protected void onConfigStyle() {
      setStyle(STYLE_NORMAL, R.style.Dialog);
    }

    /**
     * 初始化用户列表
     */
    private final void initUserList() {
      if (mUserListAdapter == null) {
        mUserListAdapter = new UserListAdapter();
        mUserListAdapter.setItems(mCountList);
      }

      ListView userList = (ListView) getView().findViewById(R.id.user_list);
      userList.setAdapter(mUserListAdapter);
    }

    /**
     * 调整窗口位置
     * 
     * @param dialog 对话框
     */
    private final void adjustWindowPosition() {
      if (mAlignView != null) {
        Window window  = getDialog().getWindow();
        LayoutParams p = window.getAttributes();
        int location[] = new int[2];

        mAlignView.getLocationOnScreen(location);
        float scale = getResources().getDisplayMetrics().density;
        p.y     = (int) (location[1] + mAlignView.getHeight() - (scale*25));
        p.width = mAlignView.getWidth();

        window.setGravity(Gravity.TOP);
        window.setAttributes(p);
      }
    }


    /**
     * 获取用户信息
     * 
     * @param userCount 用户账号
     * @return 用户信息
     */
    private final String getUserInfo(T userCount) {
      if (mUserInfoManager != null) {
        return mUserInfoManager.getUserInfo(userCount);
      }
      return Constants.EMPTY_TEXT;
    }

    /**
     * 删除用户
     * 
     * @param userCount 用户账号
     */
    private final void deleteUser(T userCount) {
      mUserListAdapter.removeItem(userCount);
      mUserListAdapter.notifyDataSetChanged();

      if (mUserInfoManager != null) {
        mUserInfoManager.deleteUser(userCount);
      }
      mCountList.remove(userCount);
      if (mCountList.isEmpty()) {
        dismiss();
      }
    }

    // 用户列表适配器
    private UserListAdapter mUserListAdapter = null;

    // 对齐视图
    private View mAlignView = null;

    //用户选中监听器
    private OnUserSelectedListener<T> mUserSelectedListener = null;

    // 用户信息管理器
    private UserInfoManager<T> mUserInfoManager = null;

    private OnDialogDismissListener mOnDialogDismissListener = null;

    // 账号列表
    private List<T> mCountList = null;

    // 用户账户选中监听器
    private final OnClickListener mUserCountSelectListener = new UserCountSelectListener();


    /**
     * 用户列表适配器
     * 
     * @author xiao.yl
     * created at 2014-04-04 14:34
     */
    private final class UserListAdapter extends SimpleListAdapter<T>
        implements OnClickListener {

      @Override
      public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
          convertView = View.inflate(parent.getContext(), R.layout.list_item_identity, null);
          convertView.setOnClickListener(mUserCountSelectListener);
        }
        T userCount = getItem(position);

        ((TextView) convertView.findViewById(R.id.textview_item_identity))
          .setText(getUserInfo(userCount));

        View deleteButton = convertView.findViewById(R.id.button_fast_delete);
        deleteButton.setOnClickListener(this);
        deleteButton.setTag(userCount);

        convertView.setTag(userCount);
        return convertView;
      }

      @SuppressWarnings("unchecked")
      @Override
      public final void onClick(View v) {
        deleteUser((T) v.getTag());
      }

    }

    /**
     * 用户账号选中监听器
     */
    private class UserCountSelectListener implements OnClickListener {

      @SuppressWarnings("unchecked")
      @Override
      public void onClick(View v) {
        if (mUserSelectedListener != null) {
          mUserSelectedListener.onUserSelected((T) v.getTag());
        }
        dismiss();
      }
    }
  }

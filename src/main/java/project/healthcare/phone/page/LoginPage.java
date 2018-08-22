package project.healthcare.phone.page;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.wilimx.app.Page;
import com.wilimx.data.BundleReader;

import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.assist.FormAssist;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.db.IdentityCount;
import project.healthcare.phone.db.IdentityCountQueryManager;
import project.healthcare.phone.ui.dialog.DataLoadDialogFragment;
import project.healthcare.phone.ui.dialog.DataLoadDialogFragment.OnLoginFinish;
import project.healthcare.phone.ui.dialog.LoadingDialogFragment;
import project.healthcare.phone.ui.dialog.UserListDialogFragment;
import project.healthcare.phone.ui.dialog.UserListDialogFragment.OnDialogDismissListener;
import project.healthcare.phone.ui.dialog.UserListDialogFragment.OnUserSelectedListener;
import project.healthcare.phone.ui.dialog.UserListDialogFragment.UserInfoManager;
import project.healthcare.phone.view.ILoginView;

public class LoginPage extends Page implements ILoginView {

    @Override
    public void setActionCallback(ActionCallback callback) {
        mCallback = callback;
    }

    /**
     * 开启数据加载
     */
    public void showDataLoadDialog() {
        DataLoadDialogFragment fragment = new DataLoadDialogFragment();
        fragment.setLoginFinish(new OnLoginFinish() {
            @Override
            public void onLoginFinish() {
                postLoginSuccessAction();
            }
        });
        showDialog(fragment);
    }

    /**
     * 设置身份信息
     *
     * @param miIdentityCount
     */
    public void setIdentityInfoText(IdentityCount miIdentityCount) {
        if (miIdentityCount == null) {
            setText(R.id.edittext_identity, "");
            setText(R.id.edittext_password, "");
        } else {
            setText(R.id.edittext_identity, miIdentityCount.getIdentity());
            setText(R.id.edittext_password, miIdentityCount.getPassword());
        }
    }

    @Override
    public void bindRes(Activity activity) {
        mActivity = activity;
    }

    @Override
    public String getIdentity() {
        return getText(R.id.edittext_identity);
    }

    @Override
    public String getPassword() {
        return getText(R.id.edittext_password);
    }

    @Override
    public Boolean getIsRemenberPsw() {
        return isChecked(R.id.checkbox_remember_password);
    }


    /**
     * 设置滚动文本
     *
     * @param resId
     */
    public void setLoadTextContent(int resId) {
        loadingTextResId = resId;
    }

    /**
     * 对话框显示隐藏
     *
     * @param show
     */
    public void showLoadingDialog(boolean show) {
        if (getFragmentManager() != null) {
            if (show) {
                mLoadingDialogFragment.setLoadTextContent(loadingTextResId).show(getFragmentManager(), null);
            } else {
                mLoadingDialogFragment.dismiss();
            }
        }
    }

    @Override
    protected void onPageInit() {

        bindClickEvents(
                R.id.button_login,
                R.id.button_register,
                R.id.textview_forget_password,
                R.id.button_login_by_phone
        );

        ((CheckBox) findView(R.id.check_box_shift_identity)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showDisplayIdentityList(mIdentityQueryManager.findAllIdentityCounts());
                }
            }
        });
    }

    @Override
    protected void handleClickEvents(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                if (mIdentityFormAssist == null) {
                    initIdentityFormAssist();
                }

                if (mIdentityFormAssist.check()) {
                    mCallback.onLogin(getIdentity(), getPassword());
                } else {
                    showToast(mIdentityFormAssist.getErrorInfo());
                }
                break;

            case R.id.button_register:
                mActivity.startActivity(new Intent(CommonActions.REGISTER));
                break;

            case R.id.button_login_by_phone:
                mActivity.startActivity(new Intent(CommonActions.LOG_IN_BY_PHONE));
                break;

            case R.id.textview_forget_password:
                mActivity.startActivity(new Intent(CommonActions.FORGOT_PSW));
                break;
        }
    }

    /**
     * 展示身份证列表
     */
    public void showDisplayIdentityList(List<IdentityCount> data) {
        mIdentityListDialog = new UserListDialogFragment<IdentityCount>();
        mIdentityListDialog.setAlignView(findView(R.id.edittext_identity));
        mIdentityListDialog.setCountList(data);
        mIdentityListDialog.setOnDialogDismissListener(new OnDialogDismissListener() {
            @Override
            public void onDialogDismiss() {
                setViewChecked(R.id.check_box_shift_identity, false);
            }
        });

        mIdentityListDialog.setUserInfoManager(new UserInfoManager<IdentityCount>() {
            @Override
            public String getUserInfo(IdentityCount userCount) {
                return userCount.getIdentity();
            }

            @Override
            public void deleteUser(IdentityCount userCount) {
                mIdentityQueryManager.delete(userCount);
                setIdentityInfoText(mIdentityQueryManager.findLastIdentityCount());
            }
        });

        mIdentityListDialog.setOnUserSelectedListener(new OnUserSelectedListener<IdentityCount>() {
            @Override
            public void onUserSelected(IdentityCount userCount) {
                setText(R.id.edittext_identity, userCount.getIdentity());
                setText(R.id.edittext_password, userCount.getPassword());
                mIdentityListDialog.dismiss();
            }
        });
        mIdentityListDialog.show(getFragmentManager(), null);
    }

    @Override
    protected void onPageContentUpdate(BundleReader paramReader) {
        if (paramReader.moveTo(CommonKeys.IDENTITY)) {
            setText(R.id.edittext_identity, paramReader.readString());
        }
        if (paramReader.moveTo(CommonKeys.PASSWORD)) {
            setText(R.id.edittext_password, paramReader.readString());
        }
        if (paramReader.moveTo(CommonKeys.REMEMBER_PASSWORD)) {
            setViewChecked(R.id.checkbox_remember_password, paramReader.readBoolean());
        }
    }

    /**
     * 初始化身份证表单辅助
     */
    private final void initIdentityFormAssist() {
        mIdentityFormAssist = new FormAssist()
                .setContentView(getPageContent())
                .addTextCheckItem(R.id.edittext_identity, R.string.identity)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_identity)
                .addTextCheckItem(R.id.edittext_password, R.string.password)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.min_length_password);
    }

    private void postLoginSuccessAction() {
        mActivity.finish();
    }

    private FormAssist mIdentityFormAssist = null;

    private LoadingDialogFragment mLoadingDialogFragment = new LoadingDialogFragment();

    private int loadingTextResId = R.string.data_loading;

    // 身份证号码列表对话框
    private UserListDialogFragment<IdentityCount> mIdentityListDialog = null;

    //绑定活动
    private Activity mActivity;

    //动作回调
    private ActionCallback mCallback;

    // 身份证账号查询器
    private IdentityCountQueryManager mIdentityQueryManager = new IdentityCountQueryManager();

}

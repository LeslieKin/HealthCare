package project.healthcare.phone.page;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.wilimx.app.Page;
import com.wilimx.constants.Constants;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.activity.LoginByPhoneActivity;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.SendVericodeType;
import project.healthcare.phone.ui.dialog.ConfirmPhoneDialogFragment;
import project.healthcare.phone.ui.dialog.LoadingDialogFragment;
import project.healthcare.phone.view.ILgByPhoneView;

public class LoginByPhonePage extends Page implements TextWatcher, ILgByPhoneView {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == PHONE_LENGTH) {
            setViewEnabled(R.id.button_next_step, true);
        } else {
            setViewEnabled(R.id.button_next_step, false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected void onPageInit() {
        bindClickEvents(
                R.id.button_title_back,
                R.id.button_delete_phone_number,
                R.id.button_next_step,
                R.id.textview_link_login_by_identity,
                R.id.textview_link_register);

        ((EditText) findView(R.id.edittext_phone)).addTextChangedListener(this);
    }

    @Override
    protected void handleClickEvents(View v) {
        switch (v.getId()) {
            case R.id.button_title_back:
                mActivity.finish();
                break;

            case R.id.button_delete_phone_number:
                setText(R.id.edittext_phone, Constants.EMPTY_TEXT);
                break;

            case R.id.button_next_step:
                mCallback.getIdentityInfo(getText(R.id.edittext_phone));
                break;

            case R.id.textview_link_login_by_identity:
                mActivity.finish();
                break;

            case R.id.textview_link_register:
                mActivity.startActivity(new Intent(CommonActions.REGISTER));
                break;
        }
    }

    @Override
    public void setActionCallback(ActionCallback callback) {
        mCallback = callback;
    }

    @Override
    public void bindRes(LoginByPhoneActivity activity) {
        mActivity = activity;
    }

    @Override
    public void dismissloadingDialogFragment() {
        loadingDialogFragment.dismiss();
    }

    @Override
    public void showConfirmDialogFragment() {
        final ConfirmPhoneDialogFragment fragment = new ConfirmPhoneDialogFragment();
        fragment.setPhoneNumberText(getText(R.id.edittext_phone));
        fragment.show(mActivity.getSupportFragmentManager(), null);
        fragment.setOnDialongConfirmListener(new ConfirmPhoneDialogFragment.OnDialogConfirmListener() {
            @Override
            public void onDialogConfirm() {
                //发送验证号码到手机
                mActivity.startActivity(new Intent(CommonActions.SEND_VERICODE).putExtra(CommonKeys.PHONE, getText(R.id.edittext_phone))
                        .putExtra(CommonKeys.TYPE, SendVericodeType.LOGIN));
                fragment.dismiss();
                mActivity.finish();//新添，处理页面逻辑
            }

            @Override
            public void onDialogCancel() {
                fragment.dismiss();
            }
        });
    }

    @Override
    public void showLoadingDialogFragment() {
        loadingDialogFragment.show(mActivity.getSupportFragmentManager(), null);
    }

    private int PHONE_LENGTH = 11;

    private LoginByPhoneActivity mActivity;

    private ActionCallback mCallback;

    private LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
}

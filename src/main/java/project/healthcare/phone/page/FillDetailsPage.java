package project.healthcare.phone.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wilimx.app.Page;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.activity.FillDetailsActivity;
import project.healthcare.phone.ui.activity.RegisterActivity;
import project.healthcare.phone.assist.FormAssist;
import project.healthcare.phone.db.bean.User;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.IRequestCodes;
import project.healthcare.phone.ui.dialog.ConfirmResultDialogFragment;
import project.healthcare.phone.view.IFillDetailView;

public class FillDetailsPage extends Page implements IFillDetailView {

    @Override
    protected void onPageInit() {
        bindClickEvents(
                R.id.button_title_back,
                R.id.button_confirm_register
        );
    }

    @Override
    protected void handleClickEvents(View v) {
        switch (v.getId()) {
            case R.id.button_title_back:
                mActivity.finish();
                break;

            case R.id.button_confirm_register:
                if (mInfoAssist == null) {
                    initInfoAssist();
                }

                if (mInfoAssist.check()) {
                    if (!getText(R.id.edittext_password).equals(getText(R.id.edittext_comfirm_password))) {
                        showToast(getString(R.string.different_password));
                    } else {
                        User user = new User();
                        user.setName(getText(R.id.edittext_real_name));
                        user.setIdentity(getText(R.id.edittext_identity));
                        user.setPhone(mActivity.getIntent().getExtras().get(CommonKeys.PHONE).toString());
                        user.setPassword(getText(R.id.edittext_comfirm_password));
                        mCallback.register(user);
                    }
                } else {
                    showToast(mInfoAssist.getErrorInfo());
                }
                break;
        }
    }

    private void initInfoAssist() {
        mInfoAssist = new FormAssist()
                .setContentView(getPageContent())
                .addTextCheckItem(R.id.edittext_real_name, R.string.name)
                .addEmptyCheck()
                .addTextCheckItem(R.id.edittext_identity, R.string.identity)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_identity)
                .addTextCheckItem(R.id.edittext_password, R.string.password)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_vericode)
                .addTextCheckItem(R.id.edittext_comfirm_password, R.string.password)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_vericode);
    }

    @Override
    public void setActionCallBack(ActionCallback callBack) {
        mCallback = callBack;
    }

    @Override
    public void bindRes(FillDetailsActivity activity) {
        mActivity = activity;
    }

    @Override
    public void displaySuccessResult() {
        Bundle params = new Bundle();
        params.putString(CommonKeys.NAME, getText(R.id.edittext_real_name));
        params.putString(CommonKeys.IDENTITY, getText(R.id.edittext_identity));
        params.putString(CommonKeys.PHONE, mActivity.getIntent().getExtras().get(CommonKeys.PHONE).toString());
        params.putString(CommonKeys.PASSWORD, getText(R.id.edittext_comfirm_password));
        Intent intent = new Intent();
        intent.putExtras(params);
        mActivity.setResult(IRequestCodes.REGISTER, intent);
        final ConfirmResultDialogFragment fragment = new ConfirmResultDialogFragment();
        fragment.show(mActivity.getSupportFragmentManager(), null);
        fragment.setOnDialongConfirmListener(new ConfirmResultDialogFragment.OnDialogConfirmListener() {
            @Override
            public void onDialogConfirm() {
                if(RegisterActivity.activity!=null){
                    RegisterActivity.activity.finish();
                }
                mActivity.finish();
            }
        });
    }

    private FormAssist mInfoAssist = null;

    private FillDetailsActivity mActivity;

    private ActionCallback mCallback;
}

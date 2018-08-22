package project.healthcare.phone.page;

import android.app.Activity;
import android.view.View;

import com.wilimx.app.Page;

import project.healthcare.phone.R;
import project.healthcare.phone.assist.FormAssist;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.view.IChangepswView;

public class ChangepswPage extends Page implements IChangepswView {

    @Override
    protected void onPageInit() {
        bindClickEvents(
                R.id.button_title_back,
                R.id.button_confirm_changepsw
        );
    }

    @Override
    protected void handleClickEvents(View v) {
        switch (v.getId()) {
            case R.id.button_title_back:
                mActivity.finish();
                break;

            case R.id.button_confirm_changepsw:
                if (mInfoAssist == null) {
                    initInfoAssist();
                }

                if (mInfoAssist.check()) {
                    if (!getText(R.id.edittext_password).equals(getText(R.id.edittext_comfirm_password))) {
                        showToast(getString(R.string.different_password));
                    } else {
                        mActionCallback.changePsw(mActivity.getIntent().getExtras().get(CommonKeys.PHONE).toString(),
                                getText(R.id.edittext_comfirm_password));
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
                .addTextCheckItem(R.id.edittext_password, R.string.password)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_vericode)
                .addTextCheckItem(R.id.edittext_comfirm_password, R.string.password)
                .addEmptyCheck()
                .addMinLengthCheck(R.string.length_vericode);
    }

    private FormAssist mInfoAssist = null;

    @Override
    public void setActionCallback(ActionCallback callback) {
        mActionCallback = callback;
    }

    @Override
    public void bindRes(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void finishActivity() {
        mActivity.finish();
    }

    private Activity mActivity;

    private ActionCallback mActionCallback;
}

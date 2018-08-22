package project.healthcare.phone.page;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.wilimx.app.Page;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.activity.ForgotpswActivity;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.SendVericodeType;
import project.healthcare.phone.ui.dialog.ConfirmPhoneDialogFragment;
import project.healthcare.phone.view.IBaseAView;

public class ForgotpswPage extends Page implements TextWatcher,IBaseAView {


  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    if (s.length() == PHONE_LENGTH) {
      setViewEnabled(R.id.button_next_step, true);
    } else {
      setViewEnabled(R.id.button_next_step, false);
    }
  }

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.button_title_back,
      R.id.button_next_step
    );

    ((EditText)findView(R.id.edittext_phone)).addTextChangedListener(this);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_title_back:
      mActivity.finish();
      break;

    case R.id.button_next_step:
      final ConfirmPhoneDialogFragment fragment = new ConfirmPhoneDialogFragment();
      final String phoneNumber = getText(R.id.edittext_phone);
      fragment.setPhoneNumberText(phoneNumber);
      fragment.show(mActivity.getSupportFragmentManager(), null);
      fragment.setOnDialongConfirmListener(new ConfirmPhoneDialogFragment.OnDialogConfirmListener() {
        @Override
        public void onDialogConfirm() {
          mActivity.startActivity(new Intent(CommonActions.SEND_VERICODE).putExtra(CommonKeys.PHONE, phoneNumber)
                  .putExtra(CommonKeys.TYPE, SendVericodeType.CHANGE_PSW));
          fragment.dismiss();
        }

        @Override
        public void onDialogCancel() {
          fragment.dismiss();
        }
      });
      break;
    }
  }

  private int PHONE_LENGTH = 11;

  @Override
  public void bindRes(Activity activity) {
      mActivity = (ForgotpswActivity)activity;
  }

  private ForgotpswActivity mActivity;
}

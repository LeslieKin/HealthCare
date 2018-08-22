package project.healthcare.phone.page;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.config.PageAction;
import android.view.View;

import com.wilimx.app.Page;
import com.wilimx.data.BundleReader;

public class ConfirmPhoneNumberPage extends Page{

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.button_cancel,
      R.id.button_confirm);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_confirm:
      postAction(PageAction.COMFIRM);
      break;

    case R.id.button_cancel:
      postAction(PageAction.CANCEL);
      break;
    }
  }

  @Override
  protected void onPageContentUpdate(BundleReader paramReader) {
    if (paramReader.moveTo(CommonKeys.PHONE)) {
      setText(R.id.textview_phone, paramReader.readString());
    }
  }
}

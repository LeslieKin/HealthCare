package project.healthcare.phone.page;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import android.view.View;

import com.wilimx.app.Page;

public class ConfirmRegisterResultPage extends Page{

  @Override
  protected void onPageInit() {
    
    bindClickEvents(
      R.id.button_dialog_confirm);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_dialog_confirm:
      postAction(PageAction.DISMISS);
      break;
    }
  }
}

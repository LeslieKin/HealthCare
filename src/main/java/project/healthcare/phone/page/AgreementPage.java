package project.healthcare.phone.page;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.wilimx.app.Page;

public class AgreementPage extends Page implements TextWatcher{
	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	
	@Override
	  protected void onPageInit() {
	    bindClickEvents(
	      R.id.button_title_back
	    );
	  }

	  @Override
	  protected void handleClickEvents(View v) {
	    switch (v.getId()) {
	    case R.id.button_title_back:
	      postAction(PageAction.BACK);
	      break;
	    }
	  }
	  
}

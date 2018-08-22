package project.healthcare.phone.ui.fragment;

import android.content.Intent;

import com.wilimx.app.BaseFragment;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.page.SetFragmentPage;

public class SetFragment extends BaseFragment<SetFragmentPage>{

  @Override
  protected int getContentResId() {
    return R.layout.page_set;
  }

  @Override
  public void onAction(int action, Object data) {
    switch (action) {
    case PageAction.LOGOUT:
      startActivity(new Intent(CommonActions.LOGIN));
      getActivity().finish();
      break;

    case PageAction.EXIT:
      Intent i= new Intent(Intent.ACTION_MAIN);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.addCategory(Intent.CATEGORY_HOME);
      startActivity(i);
      break;
    }
  }
}

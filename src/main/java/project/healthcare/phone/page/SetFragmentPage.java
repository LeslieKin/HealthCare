package project.healthcare.phone.page;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.ui.dialog.ContentDialogFragment;

import android.view.View;

import com.wilimx.app.Page;

public class SetFragmentPage extends Page{

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.nav_about_us,
      R.id.nav_instruction,
      R.id.nav_service_terms,
      R.id.nav_privacy,
      R.id.nav_logout,
      R.id.nav_exit);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.nav_about_us:
      ContentDialogFragment aboutUsFragment = new ContentDialogFragment();
      aboutUsFragment.setTitle(R.string.about_us);
      aboutUsFragment.setContent(R.string.content_about_us);
      aboutUsFragment.show(getFragmentManager(), null);
      break;

    case R.id.nav_instruction:
      ContentDialogFragment instructionFragment = new ContentDialogFragment();
      instructionFragment.setTitle(R.string.instruction);
      instructionFragment.setContent(R.string.content_instruction);
      instructionFragment.show(getFragmentManager(), null);
      break;

    case R.id.nav_service_terms:
      ContentDialogFragment serviceFragment = new ContentDialogFragment();
      serviceFragment.setTitle(R.string.service_terms);
      serviceFragment.setContent(R.string.content_service_terms);
      serviceFragment.show(getFragmentManager(), null);
      break;

    case R.id.nav_privacy:
      ContentDialogFragment privacyFragment = new ContentDialogFragment();
      privacyFragment.setTitle(R.string.privacy);
      privacyFragment.setContent(R.string.content_privacy);
      privacyFragment.show(getFragmentManager(), null);
      break;

    case R.id.nav_logout:
      postAction(PageAction.LOGOUT);
      break;

    case R.id.nav_exit:
      postAction(PageAction.EXIT);
      break;
    }
  }
}

package project.healthcare.phone.ui.dialog;

import project.healthcare.phone.R;
import project.healthcare.phone.page.ContentPage;

public class ContentDialogFragment extends RightSlideInDialogFragment<ContentPage>{

  public void setTitle(int titleResId) {
    this.titleResId = titleResId;
  }

  public void setContent(int contentResId) {
    this.contentResId = contentResId;
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_content;
  }

  @Override
  protected void onPageBuild(ContentPage page) {
    getPage().setTitle(titleResId);
    getPage().setContent(contentResId);
  }

  private int titleResId;

  private int contentResId;
}

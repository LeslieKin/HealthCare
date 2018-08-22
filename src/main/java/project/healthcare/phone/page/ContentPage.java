package project.healthcare.phone.page;

import project.healthcare.phone.R;

public class ContentPage extends TitleBackPage{

  public void setTitle(int titleResId) {
    setText(R.id.textview_title_name, titleResId);
  }

  public void setContent(int contentResId) {
    setText(R.id.textview_content, contentResId);
  }

}

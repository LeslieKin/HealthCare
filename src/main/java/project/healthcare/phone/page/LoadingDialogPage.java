package project.healthcare.phone.page;

import project.healthcare.phone.R;

import com.wilimx.app.Page;

public class LoadingDialogPage extends Page{

  /**
   * 设置进度文本
   * @param text
   */
  public void setLoadingText(int text) {
    setText(R.id.textview_dialog_loading, text);
  }
}

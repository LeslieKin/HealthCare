package project.healthcare.phone.page;

import project.healthcare.phone.R;
import project.healthcare.phone.config.PageAction;
import android.view.View;

import com.wilimx.app.Page;

/**
 * 标题返回页面
 * 
 * @author xiao.yl
 * created at 2014-07-02 10:35
 * 
 * @RequrieResource R.id.button_back
 * @RequireResource R.id.page_title
 * 
 * @see PageAction#BACK
 */
public class TitleBackPage extends Page {

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.button_title_back);
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_title_back:
      postAction(PageAction.BACK, null);
      break;
    }
  }

}

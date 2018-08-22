package project.healthcare.phone.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wilimx.app.BaseActivity;

import project.healthcare.phone.R;
import project.healthcare.phone.model.Impl.SendCodeModelImpl;
import project.healthcare.phone.page.SendVericodePage;
import project.healthcare.phone.presenter.impl.SendCodePresenterImpl;
import project.healthcare.phone.view.ISendCodeView;

public class SendVericodeActivity extends BaseActivity<SendVericodePage>{

   public static Activity activity;

    private ISendCodeView mSendCodeView;
    private SendCodePresenterImpl mSendCodePresenter;

    /**
     * 注入MVP依赖
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mSendCodeView==null){
            mSendCodeView = getPage();
        }

        mSendCodeView.bindRes(this);

        if(mSendCodePresenter ==null){
            mSendCodePresenter = new SendCodePresenterImpl();
            mSendCodePresenter.bindView(mSendCodeView);
            mSendCodePresenter.bindModel(new SendCodeModelImpl());
        }

        mSendCodePresenter.init();
    }

    @Override
  protected int getContentResId() {
    return R.layout.page_send_vericode;
  }


  @Override
  protected void onPageBuild(SendVericodePage page) {
    activity=this;
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
  }

    /**
     * 管理Presenter的生命周期
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        mSendCodePresenter.destory();
    }
}

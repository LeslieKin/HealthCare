package project.healthcare.phone.ui.dialog;

import android.os.Bundle;

import project.healthcare.phone.R;
import project.healthcare.phone.measure.MeasureCompat;
import project.healthcare.phone.measure.MeasureFactory;
import project.healthcare.phone.model.Impl.MeasureDialogModelImpl;
import project.healthcare.phone.page.MeasurePage;
import project.healthcare.phone.presenter.impl.MeasureDialogPresenterImpl;
import project.healthcare.phone.view.IMeasureDialogView;


/**
 * 测量对话框碎片
 * 
 * @author xiao.yl
 * created at 2014-07-29 11:34
 */
public class MeasureDialogFragment extends FullscreenDialogFragment<MeasurePage> {

    private IMeasureDialogView mMeasureDialogView;
    private MeasureDialogPresenterImpl mPresenter;

    /**
    * 碎片创建回调
    */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mMeasureDialogView ==null){
            mMeasureDialogView =getPage();
        }

        if(mPresenter == null){
            mPresenter = new MeasureDialogPresenterImpl();
            mPresenter.bindView(mMeasureDialogView);
            mPresenter.bindModel(new MeasureDialogModelImpl());
        }

        mPresenter.init();
    }

  /**
   * 设置检测类型
   *
   * @param detectType 检测类型
   */
  public final void setDetectType(int detectType) {
    mDetectType = detectType;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MeasureCompat compat = MeasureFactory.getMeasureCompat(mDetectType);

    if (compat != null) {
      compat.stop();
    }

    mPresenter.destory();
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_detect_ready;
  }

  @Override
  protected void onPageBuild(MeasurePage page) {
    if (mDetectType != -1) {
      page.showDetectState();
      page.updateDetectType(mDetectType);
    }
  }

  // 检测类型
  private int mDetectType = -1;
 }
 
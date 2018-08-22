package project.healthcare.phone.presenter.impl;

import com.wilimx.assist.TaskManager;

import project.healthcare.phone.R;
import project.healthcare.phone.model.ILgByPhoneModel;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.ILgByPhoneView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class LgByPhonePresenterImpl implements IBasePresenter<ILgByPhoneView,ILgByPhoneModel> ,
        ILgByPhoneView.ActionCallback,
        ILgByPhoneModel.DataCallback{

    private ILgByPhoneView mILgByPhoneView;
    private ILgByPhoneModel mILgByPhoneModel;

    @Override
    public void init() {
        if(mILgByPhoneView!=null){
            mILgByPhoneView.setActionCallback(this);
        }

        if(mILgByPhoneModel!=null){
            mILgByPhoneModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(ILgByPhoneView view) {
        mILgByPhoneView = view;
    }

    @Override
    public void bindModel(ILgByPhoneModel model) {
        mILgByPhoneModel = model;
    }

    @Override
    public void destory() {
        if(mILgByPhoneView!=null){
            mILgByPhoneView.setActionCallback(null);
        }

        if(mILgByPhoneModel!=null){
            mILgByPhoneModel.setDataCallback(null);
        }
    }

    @Override
    public void onSuccess(String identity) {
        mILgByPhoneView.dismissloadingDialogFragment();
        //如果手机已注册
        if (!identity.equals("null")) {
            Pref.getAppPreference().setLogCount(identity);
            //显示验证手机号码的对话框
            mILgByPhoneView.showConfirmDialogFragment();
        } else {
            //该手机号尚未注册
            mILgByPhoneView.showToast(R.string.no_register);
        }
        mTaskManager.finishTask();
    }

    @Override
    public void onFailed() {
        mILgByPhoneView.dismissloadingDialogFragment();
        //网络错误
        mILgByPhoneView.showToast(R.string.network_failed);
        mTaskManager.finishTask();
    }

    /**
     * 通过手机号获取身份证号码
     */
    @Override
    public void getIdentityInfo(final String phone) {
        if (mTaskManager.beginTask()) {
            mILgByPhoneView.showLoadingDialogFragment();
            mILgByPhoneModel.sendGetIdentityRequest(phone);
        }

    }

    private TaskManager mTaskManager = new TaskManager();
}

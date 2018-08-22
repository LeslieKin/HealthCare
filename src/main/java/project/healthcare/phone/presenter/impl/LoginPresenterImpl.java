package project.healthcare.phone.presenter.impl;

import android.os.Bundle;

import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.model.ILoginModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.ILoginView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class LoginPresenterImpl implements IBasePresenter<ILoginView,ILoginModel>,ILoginModel.DataCallback,ILoginView.ActionCallback{

    private ILoginView mILoginView;
    private ILoginModel mILoginModel;

    @Override
    public void init() {

        if(mILoginModel!=null){
            mILoginModel.setDataCallback(this);
        }

        if(mILoginView!=null){
            mILoginView.setActionCallback(this);
        }

        mILoginView.updatePageContent(makePageParms());
    }

    @Override
    public void bindView(ILoginView view) {
        mILoginView=view;
    }

    @Override
    public void bindModel(ILoginModel model) {
        mILoginModel=model;
    }

    @Override
    public void destory() {
        if(mILoginView!=null){
            mILoginView.setActionCallback(null);
        }

        if(mILoginModel!=null){
            mILoginModel.setDataCallback(null);
        }
    }

    public Bundle makePageParms() {
        return mILoginModel.getPageParams();
    }

    @Override
    public void onSuccess(JSONObject data) {
        mILoginView.showLoadingDialog(false);
        if (!data.isNull(CommonKeys.RESULT)) {
            if (data.optBoolean(CommonKeys.RESULT)) {
                mILoginModel.saveIdentityLogInfo(mILoginView.getIdentity(),mILoginView.getPassword(),
                        mILoginView.getIsRemenberPsw());//保存登陆信息
                mILoginModel.startDataUpdate();
                mILoginView.showDataLoadDialog();
            } else {
                mILoginView.showToast(R.string.info_password_error);
            }
        } else {
            if (!data.optBoolean(CommonKeys.STATE)) {
                mILoginView.showToast(data.optString(CommonKeys.INFO));
            }
        }
        mTaskManager.finishTask();
    }

    @Override
    public void onFailed() {
        mILoginView.showLoadingDialog(false);
        mTaskManager.finishTask();
    }

    @Override
    public void onLogin(final String userName, final String password) {
        if (mTaskManager.beginTask()){
            //显示正在登陆
            mILoginView.setLoadTextContent(R.string.login_loading);
            //显示数据加载
            mILoginView.showLoadingDialog(true);
            //发送登陆请求
            mILoginModel.sendLoginRequest(userName,password);
        }
    }

    //任务管理器
    private TaskManager mTaskManager = new TaskManager();
}

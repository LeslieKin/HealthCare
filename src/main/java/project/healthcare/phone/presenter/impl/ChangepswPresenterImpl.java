package project.healthcare.phone.presenter.impl;

import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.ui.activity.ForgotpswActivity;
import project.healthcare.phone.constants.CommonKeys;
import project.healthcare.phone.model.IChangepswModel;
import project.healthcare.phone.model.IChangepswModel.DataCallback;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IChangepswView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class ChangepswPresenterImpl implements IBasePresenter<IChangepswView,IChangepswModel> ,
        IChangepswView.ActionCallback,DataCallback{

    private IChangepswView mIChangepswView;
    private IChangepswModel mIChangepswModel;

    @Override
    public void init() {

        if(mIChangepswView!=null){
            mIChangepswView.setActionCallback(this);
        }

        if(mIChangepswModel!=null){
            mIChangepswModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IChangepswView view) {
        mIChangepswView=view;
    }

    @Override
    public void bindModel(IChangepswModel model) {
        mIChangepswModel=model;
    }

    @Override
    public void destory() {
        if(mIChangepswView!=null){
            mIChangepswView.setActionCallback(null);
        }

        if(mIChangepswModel!=null){
            mIChangepswModel.setDataCallback(null);
        }
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (data != null) {
            if (data.optBoolean(CommonKeys.RESULT)) {
                mIChangepswView.showToast("修改成功");
                ForgotpswActivity.activity.finish();
                mIChangepswView.finishActivity();
            }else{
                mIChangepswView.showToast("修改失败");
            }
        }
    }

    @Override
    public void onFailed() {
        mIChangepswView.showToast("修改失败");
        mTaskManager.finishTask();
    }

    @Override
    public void changePsw(String phone, String password) {
        if (mTaskManager.beginTask()) {
            mIChangepswModel.sendChangpswRequest(phone,password);
        }
    }

    //任务管理器
    private TaskManager mTaskManager = new TaskManager();
}

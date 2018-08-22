package project.healthcare.phone.presenter.impl;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.model.IAidModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IAidView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidPresenterImpl implements IBasePresenter<IAidView,IAidModel> ,
        IAidView.ActionCallback,
        IAidModel.DataCallback{

    private IAidView mIAidView;
    private IAidModel mIAidModel;

    @Override
    public void init() {

        if(mIAidView!=null){
            mIAidView.setActionCallback(this);
        }

        if(mIAidModel!= null){
            mIAidModel.inits();
            mIAidModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IAidView view) {
        mIAidView = view;
    }

    @Override
    public void bindModel(IAidModel model) {
        mIAidModel = model;
    }

    @Override
    public void destory() {

        if(mIAidView!=null){
            mIAidView.setActionCallback(null);
        }

        if(mIAidModel!= null){
            mIAidModel.setDataCallback(null);
        }
    }

    @Override
    public void getLocation(final Activity activity) {
        mIAidView.setLoadTextContent(R.string.request_aiding);
        mIAidView.showAidLoading();
        mIAidModel.setLocationClient(activity);
    }

    @Override
    public void getMemberList() {
        if (mTaskManager.beginTask(AID_RECORD_CONTACT)) {
            mIAidModel.sendGetMenberListReq();
        }
    }

    @Override
    public void getRecordList() {
        if (mTaskManager.beginTask(AID_RECORD_TASK)) {
           mIAidView.showLoadingDialogFragment();
           mIAidModel.sendGetRecordListReq();
        }
    }

    @Override
    public void onGRLSuccess(JSONObject data) {
        mIAidView.dismissLoadingDialog();
        mIAidView.displayGRLSuccussResult(data,mIAidModel.getMid());
        mTaskManager.finishTask(AID_RECORD_TASK);
    }

    @Override
    public void onGRLFailed() {
        mIAidView.dismissLoadingDialog();
        mTaskManager.finishTask(AID_RECORD_TASK);
    }

    @Override
    public void onGMLSuccess(JSONObject data) {
        mIAidView.dismissLoadingDialog();
        mIAidView.displayGMLSuccussResult(data,mIAidModel.getMid());
        mTaskManager.finishTask(AID_RECORD_CONTACT);
    }

    @Override
    public void onGMLFailed() {
        mIAidView.dismissLoadingDialog();
        mTaskManager.finishTask(AID_RECORD_CONTACT);
    }

    @Override
    public void onGARSuccess(JSONObject data) {
        mIAidView.dismissAidLoading();
        if (data.optBoolean(CommonKeys.RESULT)) {
            mIAidView.showToast(R.string.success_aid);
        } else {
            mIAidView.showToast(data.optString(CommonKeys.ERROR));
        }
        mTaskManager.finishTask(AID_TASK);
    }

    @Override
    public void onGARFailed() {
        mIAidView.dismissAidLoading();
        mTaskManager.finishTask(AID_TASK);
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        // 当不需要定位图层时关闭定位图层
        if (mTaskManager.beginTask(AID_TASK)) {
            mIAidModel.sendGetAidResultReq(location);
        }
        mIAidModel.stopLocationClient();
    }


    //任务管理器
    private TaskManager mTaskManager = new TaskManager();

    private static int AID_TASK = 1;

    private static int AID_RECORD_TASK = 2;

    private static int AID_RECORD_CONTACT = 3;

}

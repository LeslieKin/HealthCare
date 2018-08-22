package project.healthcare.phone.presenter.impl;

import android.os.Bundle;

import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.model.IAidRecordListModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IAidRecordListView;


/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidRecordListPresenterImpl implements IBasePresenter<IAidRecordListView, IAidRecordListModel>,
        IAidRecordListView.ActionCallback,
        IAidRecordListModel.DataCallback {


    private IAidRecordListView mIAidRecordListView;
    private IAidRecordListModel mIAidMemberListModel;

    @Override
    public void init() {

        if (mIAidRecordListView != null) {
            mIAidRecordListView.setActionCallback(this);
        }

        if (mIAidMemberListModel != null) {
            mIAidMemberListModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IAidRecordListView view) {
        mIAidRecordListView = view;
    }

    @Override
    public void bindModel(IAidRecordListModel model) {
        mIAidMemberListModel = model;
    }

    @Override
    public void destory() {

        if (mIAidRecordListView != null) {
            mIAidRecordListView.setActionCallback(null);
        }

        if (mIAidMemberListModel != null) {
            mIAidMemberListModel.setDataCallback(null);
        }
    }

    @Override
    public void cancelAid(Bundle params) {
        if (mTaskManager.beginTask()) {
            type = params.getInt(CommonKeys.TYPE);
            mIAidMemberListModel.sendCanaelAidRequest(params);
        }
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (data.optBoolean(CommonKeys.RESULT)) {
            if (type == 2) {
                mIAidRecordListView.showToast(R.string.cancel_aid_success);
            } else {
                mIAidRecordListView.showToast(R.string.handle_aid_success);
            }
            mIAidRecordListView.handleIsCVAvailable();
        } else {
            if (type == 2) {
                mIAidRecordListView.showToast(R.string.cancel_aid_failed);
            } else {
                mIAidRecordListView.showToast(R.string.handle_aid_failed);
            }
        }
        mTaskManager.finishTask();
    }

    @Override
    public void onFailed() {
        mIAidRecordListView.showToast(R.string.network_failed);
        mTaskManager.finishTask();
    }

    //任务管理器
    private TaskManager mTaskManager = new TaskManager();

    private int type;
}

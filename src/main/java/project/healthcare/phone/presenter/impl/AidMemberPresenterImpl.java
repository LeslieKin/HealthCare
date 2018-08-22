package project.healthcare.phone.presenter.impl;

import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.model.IAidMemberListModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IAidMemberListView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class AidMemberPresenterImpl implements IBasePresenter<IAidMemberListView, IAidMemberListModel>,
        IAidMemberListView.ActionCallback,
        IAidMemberListModel.DataCallback {

    private IAidMemberListView mIAidMemberListView;
    private IAidMemberListModel mIAidMemberListModel;

    @Override
    public void init() {

        if (mIAidMemberListView != null) {
            mIAidMemberListView.setActionCallback(this);
        }

        if (mIAidMemberListModel != null) {
            mIAidMemberListModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IAidMemberListView view) {
        mIAidMemberListView = view;
    }

    @Override
    public void bindModel(IAidMemberListModel model) {
        mIAidMemberListModel = model;
    }

    @Override
    public void destory() {

        if (mIAidMemberListView != null) {
            mIAidMemberListView.setActionCallback(null);
        }

        if (mIAidMemberListModel != null) {
            mIAidMemberListModel.setDataCallback(null);
        }
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (data.optBoolean(CommonKeys.RESULT)) {
            mIAidMemberListView.showToast(R.string.set_success);
            mIAidMemberListView.handleIsViewAvailable();
        } else {
            mIAidMemberListView.showToast(R.string.network_failed);
        }
        mTaskManager.finishTask();
    }

    @Override
    public void onFailed() {
        mTaskManager.finishTask();
    }

    @Override
    public void addMemberList(final int mid, final String data) {
        if (mTaskManager.beginTask()) {
            mIAidMemberListModel.sendAidMemberRequest(mid, data);
        }
    }

    //任务管理器
    private TaskManager mTaskManager = new TaskManager();
}

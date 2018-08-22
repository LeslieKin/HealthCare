package project.healthcare.phone.presenter.impl;

import com.wilimx.assist.TaskManager;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.db.bean.User;
import project.healthcare.phone.constants.CommonKeys;
import project.healthcare.phone.model.IFillDetailModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IFillDetailView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class FillDetailPresenterImpl implements IBasePresenter<IFillDetailView, IFillDetailModel>,
        IFillDetailView.ActionCallback, IFillDetailModel.DataCallback {

    private IFillDetailView mIFillDetailView;
    private IFillDetailModel mIFillDetailModel;

    @Override
    public void init() {
        if (mIFillDetailView != null) {
            mIFillDetailView.setActionCallBack(this);
        }

        if (mIFillDetailModel != null) {
            mIFillDetailModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IFillDetailView view) {
        mIFillDetailView = view;
    }

    @Override
    public void bindModel(IFillDetailModel model) {
        mIFillDetailModel = model;
    }

    @Override
    public void destory() {
        if (mIFillDetailView != null) {
            mIFillDetailView.setActionCallBack(null);
        }

        if (mIFillDetailModel != null) {
            mIFillDetailModel.setDataCallback(null);
        }
    }

    @Override
    public void register(User user) {
        if (mTaskManager.beginTask()) {
            mIFillDetailModel.sendRegisterRequest(user);
        } else {
            mIFillDetailView.showToast(R.string.input_phone);
        }
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (data != null) {
            if (data.optBoolean(CommonKeys.RESULT)) {
                mIFillDetailView.displaySuccessResult();
            }
        }
    }

    @Override
    public void onFailed() {
        mIFillDetailView.showToast("注册失败");
        mTaskManager.finishTask();
    }

    //任务管理器
    private TaskManager mTaskManager = new TaskManager();
}

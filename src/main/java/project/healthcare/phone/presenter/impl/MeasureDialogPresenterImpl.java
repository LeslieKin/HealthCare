package project.healthcare.phone.presenter.impl;

import com.wilimx.assist.TaskManager;
import com.wilimx.util.LogUtil;

import org.json.JSONObject;

import project.healthcare.phone.config.PageAction;
import project.healthcare.phone.model.IMeasureDialogModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IMeasureDialogView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class MeasureDialogPresenterImpl implements IBasePresenter<IMeasureDialogView,IMeasureDialogModel>
        ,IMeasureDialogModel.DataCallback
        ,IMeasureDialogView.ActionCallback{

    private IMeasureDialogView mIMeasureDialogView;
    private IMeasureDialogModel mIMeasureDialogModel;

    @Override
    public void init() {

        if(mIMeasureDialogView!=null){
            mIMeasureDialogView.setActionCallback(this);
        }

        if(mIMeasureDialogModel!=null){
            mIMeasureDialogModel.setDataCallback(this);
        }

        startMeasure();
    }

    @Override
    public void bindView(IMeasureDialogView view) {
        mIMeasureDialogView=view ;
    }

    @Override
    public void bindModel(IMeasureDialogModel model) {
        mIMeasureDialogModel = model;
    }

    @Override
    public void destory() {

        if(mIMeasureDialogView!=null){
            mIMeasureDialogView.setActionCallback(null);
        }

        if(mIMeasureDialogModel!=null){
            mIMeasureDialogModel.setDataCallback(null);
        }
    }

    // 任务管理器
    private TaskManager mTaskManager = new TaskManager();

    @Override
    public void onSMRSuccess(JSONObject data) {
        mIMeasureDialogModel.saveDetectResultInLocal();
        mIMeasureDialogModel.saveCompositeResultInLocal(data);
        mIMeasureDialogView.onActionResponse(PageAction.SAVE_MEASURE_RESULT, true);
        mTaskManager.finishTask();
    }

    @Override
    public void onSMRFailed() {
        mIMeasureDialogView.onActionResponse(PageAction.SAVE_MEASURE_RESULT, false);
        mTaskManager.finishTask();
    }

    @Override
    public void onAlSuccess(JSONObject data) {
        LogUtil.i("", data.toString());
        mIMeasureDialogModel.setAnalyseResult(data);
        mIMeasureDialogModel.setAnalyseResultId(data.optLong("id", -1L));
       mIMeasureDialogView.updateAnalyseResult(data);
    }

    @Override
    public void onAlFailed() {
        mIMeasureDialogModel.setAnalyseResult(null);
        mIMeasureDialogModel.setAnalyseResultId(-1L);
        mIMeasureDialogView.updateAnalyseResult(null);
    }

    @Override
    public void updateMeasureResult(Object data) {
        mIMeasureDialogView.updateMeasureResult(data);
    }

    @Override
    public void saveMeasureResult() {
        if (mTaskManager.beginTask()) {
            mIMeasureDialogModel.sendSaveMeasureResultReq();
        }
    }

    public void startMeasure() {
        mIMeasureDialogModel.startMeasure();
    }
}

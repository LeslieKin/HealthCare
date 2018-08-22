package project.healthcare.phone.presenter.impl;

import org.json.JSONArray;

import java.util.List;

import project.healthcare.phone.constants.UserInfo;
import project.healthcare.phone.db.DetectInfo;
import project.healthcare.phone.model.IDataLoadModel;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.IDataLoadView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class DataLoadPresenterImpl implements IBasePresenter<IDataLoadView, IDataLoadModel>, IDataLoadModel.DataCallback {

    private IDataLoadView mIDataLoadView;
    private IDataLoadModel mIDataLoadModel;

    @Override
    public void init() {

        if (mIDataLoadModel != null) {
            mIDataLoadModel.setDataCallback(this);
        }
    }

    @Override
    public void bindView(IDataLoadView view) {
        mIDataLoadView = view;
    }

    @Override
    public void bindModel(IDataLoadModel model) {
        mIDataLoadModel = model;
    }

    @Override
    public void destory() {
        if (mIDataLoadModel != null) {
            mIDataLoadModel.setDataCallback(this);
        }
    }

    @Override
    public void onLAVSuccess(JSONArray data) {
        Pref.getAppPreference().setSupportTypes(mIDataLoadModel.parseIntArray(data));
        mIDataLoadModel.sendUpdateDetectDataRequest(Pref.getAppPreference().getLogCount());
    }

    @Override
    public void onLAVFailed(int errorCode) {
        mIDataLoadView.displayLoadFailed(errorCode);
    }

    @Override
    public void onUDDSuccess(DetectInfo detectData, int errorCode) {
        if (detectData == null) {
            mIDataLoadView.displayLoadFailed(errorCode);
        } else {
            mIDataLoadModel.updateDetectData(detectData);
        }
    }

    @Override
    public void onUDDFailed(int errorCode) {
        mIDataLoadView.displayLoadFinsh();
    }

    @Override
    public void onUMDSuccess(List<UserInfo> userInfoList, int errorCode) {
        if (userInfoList == null) {
            mIDataLoadView.displayLoadFailed(errorCode);
        } else {
            mIDataLoadModel.updateMemberInfo(userInfoList);
        }
    }

    @Override
    public void onUMDFailed(int errorCode) {
        mIDataLoadView.displayLoadFailed(errorCode);
    }

    @Override
    public void toPostLoadFinsh() {
        mIDataLoadView.displayLoadFinsh();
    }

    @Override
    public void loadAvailabeTypes() {
        mIDataLoadModel.sendLoadTypesRequest();
    }

    public void updateMemberData(String identity) {
        mIDataLoadModel.sendUpdateMemberDataRequest(identity);
    }
}

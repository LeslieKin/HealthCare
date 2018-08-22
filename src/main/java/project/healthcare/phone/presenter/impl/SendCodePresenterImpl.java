package project.healthcare.phone.presenter.impl;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.SendVericodeType;
import project.healthcare.phone.constants.StatusCode;
import project.healthcare.phone.model.ISendCodeModel;
import project.healthcare.phone.presenter.IBasePresenter;
import project.healthcare.phone.view.ISendCodeView;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public class SendCodePresenterImpl implements IBasePresenter<ISendCodeView,ISendCodeModel> ,
        ISendCodeView.ActionCallback,
        ISendCodeModel.DataCallback{

    private ISendCodeView mISendCodeView;
    private ISendCodeModel mISendCodeModel;

    @Override
    public void init() {

        if(mISendCodeView!=null){
            mISendCodeView.inits();
            mISendCodeView.setActionCallback(this);
        }

        if(mISendCodeModel!=null){
            mISendCodeModel.setDataCallback(this);
        }

        mISendCodeView.onPageBuild();
    }

    @Override
    public void bindView(ISendCodeView view) {
        mISendCodeView=view;
    }

    @Override
    public void bindModel(ISendCodeModel model) {
        mISendCodeModel=model;
    }

    @Override
    public void destory() {
        if(mISendCodeView!=null){
            mISendCodeView.setActionCallback(null);
        }

        if(mISendCodeModel!=null){
            mISendCodeModel.setDataCallback(null);
        }
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (!data.isNull(CommonKeys.VERICODE)) {
            mISendCodeView.setVericodeTemp(data.optString(CommonKeys.VERICODE));
            mISendCodeView.showToast(data.optString(CommonKeys.VERICODE));
        } else if (data.optInt("statusCode") == StatusCode.FAIL) {
            mISendCodeView.stopTimer();
            if (type == SendVericodeType.REGISTER) {
                mISendCodeView.showToast(R.string.info_phone_already_reg);
            }
            if (type == SendVericodeType.LOGIN || type == SendVericodeType.CHANGE_PSW) {
                mISendCodeView.showToast("该手机号码尚未注册，请重新输入");
            }
        }
    }

    @Override
    public void onFailed() {
       mISendCodeView.stopTimer();
       mISendCodeView.showToast(R.string.info_get_vericode_failed);
    }

    @Override
    public void sendVericode(final String phoneNumber, final int type) {
        this.type=type;
        if (type == SendVericodeType.REGISTER) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                mISendCodeView.startTimer();
                mISendCodeModel.sendVericodeRequest(phoneNumber,1);
            } else {
                mISendCodeView.showToast(R.string.input_phone);
            }
        }

        if (type == SendVericodeType.LOGIN || type == SendVericodeType.CHANGE_PSW) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                mISendCodeView.startTimer();
                mISendCodeModel.sendVericodeRequest(phoneNumber,0);
            } else {
                mISendCodeView.showToast(R.string.input_phone);
            }
        }
    }

    @Override
    public void startDataUpdate() {
        mISendCodeModel.startDataUpdate();
    }

    private int type;
}

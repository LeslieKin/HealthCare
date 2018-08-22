package project.healthcare.phone.page;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.wilimx.app.Page;
import com.wilimx.assist.InverseTimer;
import com.wilimx.assist.TimerHandler;

import project.healthcare.phone.R;
import project.healthcare.phone.ui.activity.SendVericodeActivity;
import project.healthcare.phone.config.CommonActions;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.constants.SendVericodeType;
import project.healthcare.phone.ui.dialog.DataLoadDialogFragment;
import project.healthcare.phone.ui.dialog.DataLoadDialogFragment.OnLoginFinish;
import project.healthcare.phone.view.ISendCodeView;

import static android.R.attr.phoneNumber;

public class SendVericodePage extends Page implements ISendCodeView {

    private void updataTips(String phone) {
        setText(R.id.textview_tips_fill_in_vericode,
                getString(R.string.format_fill_verticode, encryptPhoneNumber(phone)));
    }

    /**
     * 设置验证码缓存
     */
    public void setVericodeTemp(String vericodeTemp) {
        this.vericodeTemp = vericodeTemp;
    }

    /**
     * 开始倒计时
     */
    public void startTimer() {
        mTimer = new InverseTimer();
        mTimer.setTimerCount(COUNT_DOWN_SECONDS);
        mTimer.addTimerHandler(new TimerHandler() {
            @Override
            protected void onTimerStop() {
                enableView(R.id.button_send_vericode_again);
                setText(R.id.button_send_vericode_again, R.string.send_vericode_again);
            }

            @Override
            protected void onStart() {
                disableView(R.id.button_send_vericode_again);
            }

            @Override
            protected void onRunning() {
                setText(R.id.button_send_vericode_again, getString(R.string.second_send_again, String.valueOf(getCount())));
            }
        });
        mTimer.start();
    }

    /**
     * 停止倒计时
     */
    public void stopTimer() {
        mTimer.stop();
    }

    @Override
    protected void onPageInit() {

        bindClickEvents(
                R.id.button_send_vericode_again,
                R.id.button_title_back,
                R.id.button_next_step
        );

        ((EditText) findView(R.id.edittext_vericode)).addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((s.toString()).equals(vericodeTemp)) {
                    setViewEnabled(R.id.button_next_step, true);
                } else {
                    setViewEnabled(R.id.button_next_step, false);
                }
            }
        });
    }

    @Override
    protected void handleClickEvents(View v) {
        switch (v.getId()) {
            case R.id.button_send_vericode_again:
                mCallback.sendVericode(mPhone, type);
                break;

            case R.id.button_next_step:
                //注册
                if (type == SendVericodeType.REGISTER) {
                    mActivity.startActivity(new Intent(CommonActions.FILL_DETAILS).putExtra(CommonKeys.PHONE, phoneNumber));
                    mActivity.finish();
                }
                //手机号登陆
                if (type == SendVericodeType.LOGIN) {
                //        startDataUpdate();
                    mCallback.startDataUpdate();
                    showDataLoadDialog();
                    // finish();
                }
                //修改密码
                if (type == SendVericodeType.CHANGE_PSW) {
                    mActivity.startActivity(new Intent(CommonActions.CHANGE_PSW).putExtra(CommonKeys.PHONE, phoneNumber));
                    mActivity.finish();
                }
                break;

            case R.id.button_title_back:
                mActivity.finish();
                break;
        }
    }

    /**
     * 手机号码加密
     */
    private final String encryptPhoneNumber(String phoneNumber) {
        return phoneNumber.replace(phoneNumber.subSequence(3, 7), PHONE_CODE);
    }

    private String PHONE_CODE = "****";

    //发送验证码倒计时
    private final int COUNT_DOWN_SECONDS = 30;

    //计时器
    private InverseTimer mTimer = null;

    //验证码缓存
    private String vericodeTemp = null;

    /**
     * 开启数据加载
     */
    public void showDataLoadDialog() {
        DataLoadDialogFragment fragment = new DataLoadDialogFragment();
        fragment.setLoginFinish(new OnLoginFinish() {
            @Override
            public void onLoginFinish() {
                // postLoginSuccessAction();
            }
        });
        showDialog(fragment);
    }

    private void postLoginSuccessAction() {
        // postAction(PageAction.BACK);  取消返回到登陆界面
    }

    @Override
    public void inits() {
        mPhone = mActivity.getIntent().getExtras().get(CommonKeys.PHONE).toString();
        type = mActivity.getIntent().getExtras().getInt(CommonKeys.TYPE);
    }

    @Override
    public void bindRes(Activity activity) {
        mActivity = (SendVericodeActivity) activity;
    }

    @Override
    public void setActionCallback(ActionCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onPageBuild() {
        updataTips(mPhone);
        mCallback.sendVericode(mPhone, type);
    }

    private SendVericodeActivity mActivity;

    private ActionCallback mCallback;

    private String mPhone;

    private int type;
}

package project.healthcare.phone.view;

import android.app.Activity;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface ISendCodeView {

    interface ActionCallback{

        /**
         * 发送验证码
         * @param phone 手机号码
         * @param type 发送类型
         */
        void sendVericode(String phone,int type);

        /**
         * 启动数据更新
         */
        void startDataUpdate();
    }

    void inits();

    void bindRes(Activity activity);

    void setActionCallback(ActionCallback callback);

    void showToast(int id);

    void showToast(String content);

    void startTimer();

    void stopTimer();

    /**
     * 设置验证码缓存
     * @param vericodeTemp 缓存验证码
     */
    void setVericodeTemp(String vericodeTemp);

    /**
     * 页面初始化动作
     */
    void onPageBuild();
}

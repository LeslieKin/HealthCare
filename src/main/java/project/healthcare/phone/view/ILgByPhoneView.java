package project.healthcare.phone.view;

import project.healthcare.phone.ui.activity.LoginByPhoneActivity;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface ILgByPhoneView {

    /**
     * 动作回调
     */
    interface ActionCallback{

        /**
         * 获取身份信息
         * @param phone 手机号
         */
        void getIdentityInfo(String phone);
    }

    /**
     * 设置动作回调
     * @param callback 回调
     */
    void setActionCallback(ActionCallback callback);

    /**
     * 绑定活动
     * @param activity 活动
     */
    void bindRes(LoginByPhoneActivity activity);

    /**
     * 提示
     * @param content 内容
     */
    void showToast(String content);

    /**
     * 提示
     * @param id 内容id
     */
    void showToast(int id);

    /**
     * 关闭加载对话框
     */
    void dismissloadingDialogFragment();

    void showConfirmDialogFragment();

    void showLoadingDialogFragment();
}

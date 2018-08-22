package project.healthcare.phone.view;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface ILoginView {

    /**
     * 动作回调
     */
    interface ActionCallback{

        /**
         * 登陆
         * @param userName 用户名
         * @param password 密码
         */
        void onLogin(String userName,String password);
    }

    /**
     * 设置动作回调
     * @param callback  回调
     */
    void setActionCallback(ActionCallback callback);

    /**
     * 绑定活动
     * @param activity 活动
     */
    void bindRes(Activity activity);

    /**
     * 获取身份证
     * @return 身份证
     */
    String getIdentity();

    /**
     * 获取密码
     * @return 密码
     */
    String getPassword();

    /**
     * 获取记住密码状态
     * @return 是否记住密码
     */
    Boolean getIsRemenberPsw();

    /**
     * 设置加载文字
     * @param id 文字id
     */
    void setLoadTextContent(int id);

    /**
     * 展示加载对话框
     * @param show 是否展示
     */
    void showLoadingDialog(boolean show);

    /**
     * 提示
     * @param content 提示内容
     */
    void showToast(String content);

    /**
     * 提示
     * @param id  内容Id
     */
    void showToast(int id);

    /**
     * 展示数据加载对话框
     */
    void showDataLoadDialog();

    /**
     * 更新页面内容
     * @param params 页面参数
     */
    void updatePageContent(Bundle params);
}

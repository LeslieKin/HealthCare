package project.healthcare.phone.view;

import android.app.Activity;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IChangepswView {

    /**
     * 动作回调
     * 修改密码
     */
    interface ActionCallback{

        void changePsw(String phone ,String password);
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
     * 提示
     * @param content 提示内容
     */
    void showToast(String content);

    /**
     * 提示
     * @param id  内容id
     */
    void showToast(int id);

    /**
     * 结束活动
     */
    void finishActivity();
}

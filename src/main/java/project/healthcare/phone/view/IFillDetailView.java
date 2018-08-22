package project.healthcare.phone.view;

import project.healthcare.phone.ui.activity.FillDetailsActivity;
import project.healthcare.phone.db.bean.User;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IFillDetailView {


    interface ActionCallback{

        /**
         * 注册
         * @param user 用户
         */
        void register(User user);
    }


    void setActionCallBack(ActionCallback callBack);

    /**
     * 绑定活动
     * @param activity 活动
     */
    void bindRes(FillDetailsActivity activity);

    /**
     * 提示
     * @param id 内容id
     */
    void showToast(int id);

    /**
     * 提示
     * @param content 内容
     */
    void showToast(String content);

    /**
     * 展示成功请求结果
     */
    void displaySuccessResult();
}



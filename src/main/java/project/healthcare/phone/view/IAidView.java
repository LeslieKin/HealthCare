package project.healthcare.phone.view;

import android.app.Activity;
import android.view.View;

import org.json.JSONObject;

import project.healthcare.phone.ui.fragment.AidFragment;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IAidView {

    interface ActionCallback{

        /**
         * 获取位置信息
         * @param activity 当前活动
         */
        void getLocation(Activity activity);

        /**
         * 获取紧急联系人列表
         */
        void getMemberList();

        /**
         * 获取急救记录
         */
        void getRecordList();
    }

    void setActionCallback(ActionCallback callback);

    void bindRes(View view);

    void setLoadTextContent(int id);

    void showAidLoading();

    void displayGRLSuccussResult(JSONObject data , int mid);

    void displayGMLSuccussResult(JSONObject data , int mid);

    void dismissLoadingDialog();

    void dismissAidLoading();

    void showLoadingDialogFragment();

    void showToast(int id);

    void showToast(String content);

    void setActivity(Activity activity);

    void setAidFragment(AidFragment aidFragment);
}

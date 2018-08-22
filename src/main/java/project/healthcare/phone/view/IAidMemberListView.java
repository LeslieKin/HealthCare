package project.healthcare.phone.view;

import android.view.View;

import project.healthcare.phone.ui.dialog.AidMemberListDialogFragment;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IAidMemberListView {

    interface ActionCallback{

        /**
         * 添加联系人
         */
        void addMemberList(int mid,String data);
    }

    void setActionCallback(ActionCallback actionCallback);

    void bindRes(View view);

    void setAidMemFragment(AidMemberListDialogFragment fragment);

    void showToast(int id);

    void showToast(String content);

    void handleIsViewAvailable();
}

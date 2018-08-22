package project.healthcare.phone.view;

import android.os.Bundle;
import android.view.View;

import project.healthcare.phone.ui.dialog.AidRecordListDialogFragment;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IAidRecordListView {

    interface ActionCallback{

        /**
         * 取消急救
         * @param params 急救状态信息
         */
        void cancelAid(Bundle params);

    }

    void setActionCallback(ActionCallback callback);

    void bindRes(View view);

    void showToast(int id);

    void showToast(String content);

    void handleIsCVAvailable();

    void setFragment(AidRecordListDialogFragment fragment);
}

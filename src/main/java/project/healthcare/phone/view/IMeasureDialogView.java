package project.healthcare.phone.view;

import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IMeasureDialogView {

    interface ActionCallback{

        void saveMeasureResult();
    }

    void setActionCallback(ActionCallback mCallback);

    void onActionResponse(int action,Object data);

    /**
     * 更新分析结果
     * @param data 分析结果
     */
    void updateAnalyseResult(JSONObject data);

    /**
     * 更新测量结果
     * @param data 测量结果
     */
    void updateMeasureResult(Object data);

}

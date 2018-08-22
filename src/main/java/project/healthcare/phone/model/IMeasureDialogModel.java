package project.healthcare.phone.model;


import org.json.JSONObject;

/**
 * @author li.jq
 * email:940655212@qq.com
 * @version 1.0
 */

public interface IMeasureDialogModel {

    interface DataCallback{

        /**
         * 请求成功
         * SMR : saveMeasureResult
         * @param data 返回数据
         */
        void onSMRSuccess(JSONObject data);

        void onSMRFailed();

        /**
         * 请求成功
         * Al : Analyse
         * @param data 返回数据
         */
        void onAlSuccess(JSONObject data);

        void onAlFailed();

        /**
         * 更新测量结果
         * @param data 结果
         */
        void updateMeasureResult(Object data);
    }

    void setDataCallback(DataCallback callback);

    /**
     * 发送保存测量信息请求
     */
    void sendSaveMeasureResultReq();

    /**
     * 发送分析测量结果请求
     * @param data 测量结果
     */
    void sendAnalyseReq(Object data);

    void saveDetectResultInLocal();

    void saveCompositeResultInLocal(JSONObject compositeResult);

    void setAnalyseResult(JSONObject data);

    void setAnalyseResultId(long id);

    void startMeasure();
}

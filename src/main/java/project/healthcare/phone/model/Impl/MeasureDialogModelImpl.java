package project.healthcare.phone.model.Impl;

import com.wilimx.http.HttpUtil;
import com.wilimx.http.JSONBasedDataReceiver;
import com.wilimx.http.JSONDataReceiver;
import com.wilimx.http.JSONRequestParams;
import com.wilimx.util.LogUtil;

import org.json.JSONObject;

import project.healthcare.phone.config.AppConstants;
import project.healthcare.phone.config.ServerPath;
import project.healthcare.phone.constants.SessionKeys;
import project.healthcare.phone.db.CompositeData;
import project.healthcare.phone.db.CompositeDataQueryManager;
import project.healthcare.phone.db.DetectData;
import project.healthcare.phone.db.DetectDataQueryManager;
import project.healthcare.phone.measure.MeasureCompat;
import project.healthcare.phone.measure.MeasureFactory;
import project.healthcare.phone.model.IMeasureDialogModel;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.ui.activity.Session;
import project.healthcare.phone.util.DataUtil;

/**
 * @author li.jq 940655212@qq.com
 *         Created on 2017/8/25.
 */

public class MeasureDialogModelImpl implements IMeasureDialogModel ,MeasureCompat.OnMeasureFinishListener{


    @Override
    public void setDataCallback(DataCallback callback) {
        mDataCallback=callback;
    }

    @Override
    public void sendSaveMeasureResultReq() {
        HttpUtil.post(ServerPath.SAVE_ANALYSIS,
                new JSONRequestParams()
                        .setParams("id", mAnalyseResultId),
                new SaveResultReceiver());
    }

    @Override
    public void sendAnalyseReq(Object data) {
        HttpUtil.post(ServerPath.ANALYSE,
                makeAnalyseParams(data),
                new AnalyseResultReceiver());
    }

    /**
   * 生成分析请求参数
   *
   * @param data 数据
   */
    private final JSONRequestParams makeAnalyseParams(Object data) {
        LogUtil.d("identity", Session.data.getString(SessionKeys.IDENTITY));
        return new JSONRequestParams()
                .setParams(IDENTITY, Session.data.getString(SessionKeys.IDENTITY))
                .setParams(EQUIPID , -1)
                .setParams(APPTYPE, AppConstants.APP_TYPE)
                .setParams(TYPEID, mDetectType)
                .setParams(DURATION, (System.currentTimeMillis() - mMeasureTime) / 1000)
                .setParams(PLACE, Pref.getAppPreference().getLocation())
                .setParams(VALUES, DataUtil.makeArrayString((double[]) data));
    }

    @Override
    public void onMeasureFinish(int detectType, Object data) {
        mMeasureResult = (double[]) data;
        mDataCallback.updateMeasureResult(data);

        if (data == null) {
            return;
        }

        sendAnalyseReq(data);
    }


    /**
    * 分析结果数据接收器
    */
    private final class AnalyseResultReceiver extends JSONDataReceiver {
        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onAlSuccess(data);
        }

        @Override
        protected void onFailed() {
           mDataCallback.onAlFailed();
        }
    }

    @Override
    public void saveDetectResultInLocal() {
        double[] measureValues = mMeasureResult;
        JSONObject analyseResult = mAnalyseResult;
        DetectData data = new DetectData();

        data.setRecordId(analyseResult.optLong("id"));
        data.setType(mDetectType);
        data.setTime(System.currentTimeMillis());
        data.setPlace(analyseResult.optString("place"));
        data.setMeasureValues(measureValues);
        data.setScore(analyseResult.optDouble("score"));
        data.setStateId(analyseResult.optInt("stateId"));
        data.setSuggest(analyseResult.optString("suggest"));
        data.setComment(analyseResult.optString("comment"));

        new DetectDataQueryManager().save(data);
    }

    @Override
    public void saveCompositeResultInLocal(JSONObject compositeResult) {
        CompositeData data = new CompositeData();

        data.setComment(compositeResult.optString("comment"));
        data.setPlace(compositeResult.optString("place"));
        data.setScore(compositeResult.optDouble("Score"));
        data.setStateId(compositeResult.optInt("stateId"));
        data.setSuggest(compositeResult.optString("suggest"));
        data.setTime(compositeResult.optLong("time"));

        new CompositeDataQueryManager().save(data);
    }

    @Override
    public void setAnalyseResult(JSONObject data) {
        mAnalyseResult = data;
    }

    @Override
    public void setAnalyseResultId(long id) {
        mAnalyseResultId = id;
    }

    @Override
    public void startMeasure() {
        MeasureCompat compat = MeasureFactory.getMeasureCompat(mDetectType);

        if (compat != null) {
            compat.startMeasure(mDetectType);
            compat.setOnMeasureFinishListener(this);
            mMeasureTime = System.currentTimeMillis();
        }
    }

    /**
    * 保存结果接收器
    */
    private final class SaveResultReceiver extends JSONBasedDataReceiver<JSONObject> {
        @Override
        protected JSONObject parseData(JSONObject data) {
            return data.optJSONObject("composite");
        }

        @Override
        protected void onSuccess(JSONObject data) {
            mDataCallback.onSMRSuccess(data);
        }

        @Override
        protected void onFailed() {
            mDataCallback.onSMRFailed();
        }
    }


    private DataCallback mDataCallback;

    // 分析结果ID
    private long mAnalyseResultId = -1L;

    // 测量结果
    private double mMeasureResult[] = null;

    // 分析结果
    private JSONObject mAnalyseResult = null;

    // 检测类型
    private int mDetectType = -1;

    // 测量时间
    private long mMeasureTime = 0;

    //身份证号
    public static final String IDENTITY = "identity";
    //设备号
    public static final String EQUIPID = "equipId";

    public static final String APPTYPE = "appType";

    public static final String TYPEID = "typeId";

    public static final String DURATION = "duration";

    public static final String PLACE = "place";

    public static final String VALUES ="values";


}

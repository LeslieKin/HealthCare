package project.healthcare.phone.receive;

import java.util.ArrayList;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.db.CompositeData;
import project.healthcare.phone.db.DetectData;
import project.healthcare.phone.db.DetectInfo;

import com.wilimx.http.JSONBasedDataReceiver;

/**
 * 检测数据数据接收器
 * 
 * @author xiao.yl
 * created at 2014-06-07 09:51
 */
public abstract class DetectDataReceiver extends JSONBasedDataReceiver<DetectInfo> {

  @Override
  protected DetectInfo parseData(JSONObject data) {
  DetectInfo detectData = null;
  try{
    if(data.get(CommonKeys.RESULT)!=null){
      detectData = new DetectInfo();
      JSONObject result = data.getJSONObject(CommonKeys.RESULT);
      if(result.getJSONArray(CommonKeys.COMPOSITE) != null){
        List<CompositeData> compositeInfoList = new ArrayList<CompositeData>();
        JSONArray compositeArray = result.getJSONArray(CommonKeys.COMPOSITE);
        for(int i=0,len=compositeArray.length(); i<len; i++){
          CompositeData compositeInfo = new CompositeData();
          JSONObject jsonComposite = compositeArray.getJSONObject(i);
          compositeInfo.setId(jsonComposite.getInt("id"));
          compositeInfo.setComment(jsonComposite.getString("comment"));
          compositeInfo.setPlace(jsonComposite.getString("place"));
          compositeInfo.setScore((float) jsonComposite.getDouble("score"));
          compositeInfo.setStateId(jsonComposite.getInt("stateId"));
          compositeInfo.setSuggest(jsonComposite.getString("suggest"));
          compositeInfo.setTime(jsonComposite.getLong("time"));
          compositeInfoList.add(compositeInfo);
        }
        detectData.setCompositeInfoList(compositeInfoList);
      }
      if(result.getJSONArray(CommonKeys.VALUES) != null){
        List<DetectData> mDetectInfoList = new ArrayList<DetectData>();
        JSONArray detectInfoSetArray = result.getJSONArray(CommonKeys.VALUES);
        for(int i=0, len=detectInfoSetArray.length(); i<len; i++){
          JSONObject detectInfoSet = detectInfoSetArray.getJSONObject(i);
          DetectData detectInfo = new DetectData();
          detectInfo.setId(detectInfoSet.getInt(CommonKeys.ID));
          detectInfo.setType(detectInfoSet.getInt(CommonKeys.TYPE_ID));
          detectInfo.setPlace(detectInfoSet.getString(CommonKeys.PLACE));
          detectInfo.setTime(detectInfoSet.getLong(CommonKeys.TIME));
          detectInfo.setScore(detectInfoSet.getLong(CommonKeys.SCORE));
          detectInfo.setStateId(detectInfoSet.getInt(CommonKeys.STATE_ID));
          detectInfo.setComment(detectInfoSet.getString(CommonKeys.COMMENT));
          detectInfo.setSuggest(detectInfoSet.getString(CommonKeys.SUGGEST));

          JSONArray measureValueArray = detectInfoSet.getJSONArray(CommonKeys.VALUES);
          double values[] = new double[measureValueArray.length()];
          for (int index = 0;index < measureValueArray.length(); index++) {
            values[index] = measureValueArray.getDouble(index);
          }
          detectInfo.setMeasureValues(values);
          mDetectInfoList.add(detectInfo);
        }
        detectData.setDetectInfoList(mDetectInfoList);
      }
    }
  }catch(JSONException e){
    detectData = null;
    e.printStackTrace();
  }
    return detectData;
  }

}

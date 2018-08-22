package project.healthcare.phone.page;

import project.healthcare.phone.R;
import com.baidu.mapapi.map.MapView;

public class MapPage extends TitleBackPage{

  /**
   * 获取地图组件
   * 
   * @return
   */
  public MapView getMapView() {
    MapView mMapView = (MapView)findView(R.id.bmapView);
    return mMapView;
  }
}

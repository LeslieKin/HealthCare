package project.healthcare.phone.ui.dialog;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.page.MapPage;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MapDialogFragment extends RightSlideInDialogFragment<MapPage> {

  public void updateMap(JSONObject location) {
    mAidDetails = location;
  }

  public void onDestroy() {
    super.onDestroy();
    // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
     getPage().getMapView().onDestroy();
  }

  @Override
  public void onResume() {
    super.onResume();
    // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
     getPage().getMapView().onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
     getPage().getMapView().onPause();
  }

  @Override
  protected int getContentResId() {
    return R.layout.page_map;
  }

  @Override
  protected void onPageBuild(MapPage page) {
    final BaiduMap mBaiduMap = getPage().getMapView().getMap();
    // 开启定位图层  
    mBaiduMap.setMyLocationEnabled(true);
    // 构造定位数据  
    MyLocationData locData = new MyLocationData.Builder()
      .latitude(mAidDetails.optDouble(CommonKeys.LATITUDE))
      .longitude(mAidDetails.optDouble(CommonKeys.LONGITUDE))
      .build();
    //设置定位数据
    mBaiduMap.setMyLocationData(locData);
  // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标） 
    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
        .fromResource(R.drawable.icon_gcoding);
    MyLocationConfiguration config = new MyLocationConfiguration(com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);  
    mBaiduMap.setMyLocationConfigeration(config);
    LatLng ll = new LatLng(mAidDetails.optDouble(CommonKeys.LATITUDE),
        mAidDetails.optDouble(CommonKeys.LONGITUDE));
    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
    mBaiduMap.animateMapStatus(u);

    View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_map_address, null);
    ((TextView)view.findViewById(R.id.address)).setText(mAidDetails.optString(CommonKeys.ADDRESS));
    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -47, null);
    mBaiduMap.showInfoWindow(mInfoWindow);
  }

  private JSONObject mAidDetails = new JSONObject();

  private InfoWindow mInfoWindow;
}

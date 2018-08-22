package project.healthcare.phone.page;

import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.wilimx.app.ListItemPage;
import com.wilimx.app.Page;
import com.wilimx.app.adapter.SimpleListAdapter;
import com.wilimx.constants.Position;

import java.util.ArrayList;
import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.config.DetectTypeConfig;
import project.healthcare.phone.constants.DetectType;
import project.healthcare.phone.ui.dialog.DetectReadyDialogFragment;

/*本页面代码需要重新写*/
//检测页面
public class DetectFragmentPage extends Page{

  protected void onPageInit() {
    List<ListItem> result = new ArrayList<ListItem>();
    ListItem item;
    //int detectType;
    
    for (int i = 0 ; i < 5; ++i) {
    	
      item = new ListItem();
      item.detectType = DetectTypeConfig.get(i).getTypeKey();
      item.titleRes = DetectTypeConfig.get(i).getTitleRes();
      item.iconRes = _iconResMap.get(i);
      result.add(item);
    } 
    
    mListAdapter.setItems(result);
    mListAdapter.notifyDataSetChanged();
    GridView gridView = (GridView)findView(R.id.gridView_detect_item);
    gridView.setAdapter(mListAdapter);
  }
 

  /*
   * 显示检测就绪对话框
   * 
   * @param detectType 检测类型
   */
  private final void displayDetectReadyDialog(int detectType) {
    DetectReadyDialogFragment fragment = new DetectReadyDialogFragment();
    fragment.setDetectType(detectType);
    showDialog(fragment);
  }


  private static final SparseIntArray _iconResMap;

  static {
    _iconResMap = new SparseIntArray();

    _iconResMap.put(DetectType.BLOOD_PRESSURE, R.drawable.icon_detect_blood_pressure_selector);
    _iconResMap.put(DetectType.BLOOD_SUGAR, R.drawable.icon_detect_blood_sugar_selector);
    _iconResMap.put(DetectType.BLOOD_OXYGEN, R.drawable.icon_detect_blood_oxygen_selector);
    _iconResMap.put(DetectType.HEART_RATE, R.drawable.icon_detect_heart_rate_selector);
    _iconResMap.put(DetectType.TEMP, R.drawable.icon_detect_temperture_selector);
    /*_iconResMap.put(DetectType.WEIGHT, R.drawable.icon_detect_weight_selector);*/
  }

  // 列表项单击事件监听器
  private final OnClickListener mListItemClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      displayDetectReadyDialog(mListAdapter.getItem((Integer) v.getTag()).detectType);
    }
  };
  
  //列表项适配器
  private class DetectItemAdapter extends SimpleListAdapter<ListItem> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return mListItemPage.getView(position, convertView, parent);
    }
    
  //列表项页面
    private ListItemPage mListItemPage = new ListItemPage() {

      @Override
      protected void onInitListItem(int position) {
        ListItem item = getItem(position);
        
        TextView v = findText(R.id.text_detect_type);
        v.setText(item.titleRes);
        v.setTag(position);
        v.setOnClickListener(mListItemClickListener);
        setDrawable(R.id.text_detect_type, Position.TOP, item.iconRes);
      }
      @Override
      protected int getContentResId() {
        return R.layout.item_detect;
      } 
      
    };
  }

  //列表项
  private final class ListItem {
	  
    // 检测类型
	  private int detectType;

    // 标题资源ID
    private int titleRes;

    // 图标资源ID
    private int iconRes;
/*    // 是否可用
    private boolean available;*/
  }

  DetectItemAdapter mListAdapter= new DetectItemAdapter();
  
}

package project.healthcare.phone.page;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.wilimx.app.ListItemPage;
import com.wilimx.app.Page;
import com.wilimx.app.adapter.SimpleListAdapter;
import com.wilimx.constants.Constants;
import com.wilimx.util.ListUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.ui.dialog.AidMemberListDialogFragment;
import project.healthcare.phone.view.IAidMemberListView;

public class AidMemberListPage extends Page implements IAidMemberListView{

  public void updateDate(JSONObject data) {
    JSONArray jsonArray = data.optJSONArray(CommonKeys.MEMBER_LIST);
    listItem = ListUtil.parseList(jsonArray);
    mAidListAdapter.setItems(listItem);
    mAidListAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onPageInit() {
    bindClickEvents(
        R.id.button_cancel,
        R.id.button_confirm);
    ListView listView = (ListView)findView(R.id.listview_aid_member);
    listView.setAdapter(mAidListAdapter);
    listView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        if (((CheckBox)view.findViewById(R.id.checkbox_aid_member)).isChecked()) {
          try {
            listItem.get(position).put(CommonKeys.IS_SET, 0);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else {
          try {
            listItem.get(position).put(CommonKeys.IS_SET, 1);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        mAidListAdapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {
    case R.id.button_confirm:
      String data = Constants.EMPTY_TEXT;
      boolean isFirst = true;
      for (JSONObject list : listItem) {
        if (list.optInt(CommonKeys.IS_SET) == 1) {
          if (isFirst) {
            data += list.optString(CommonKeys.ID);
            isFirst = false;
          } else {
            data += "," + list.optString(CommonKeys.ID);
          }
        }
      }
      mCallback.addMemberList(mAidFragment.getMid(),data);
      break;

    case R.id.button_cancel:
      mAidFragment.dismiss();
      break;
    }
  }

  private List<JSONObject> listItem = new ArrayList<JSONObject>();

  private SimpleListAdapter<JSONObject> mAidListAdapter = new SimpleListAdapter<JSONObject>() {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return page.getView(position, convertView, parent);
    }

    private ListItemPage page = new ListItemPage() {
      
      @Override
      protected int getContentResId() {
        return R.layout.list_aid_member;
      }

      @Override
      protected void onInitListItem(final int position) {
        final JSONObject data = getItem(position);
        String userName = data.optString(CommonKeys.USERNAME);
        setText(R.id.checkbox_aid_member, userName);
        if (data.optInt(CommonKeys.IS_SET) == 1 ) { 
          setViewChecked(R.id.checkbox_aid_member, true);
        } else {
          setViewChecked(R.id.checkbox_aid_member, false);
        }
      }
    };
  };

  @Override
  public void setActionCallback(ActionCallback actionCallback) {
    mCallback = actionCallback;
  }

  @Override
  public void bindRes(View view) {
      mView = view;
  }

  @Override
  public void setAidMemFragment(AidMemberListDialogFragment fragment) {
    mAidFragment=fragment;
  }

  @Override
  public void handleIsViewAvailable() {
    if (mAidFragment.whetherContentViewAvailable()) {
      mAidFragment.dismiss();
    }
  }

  private ActionCallback mCallback;

  private View mView;

  private AidMemberListDialogFragment mAidFragment;
}


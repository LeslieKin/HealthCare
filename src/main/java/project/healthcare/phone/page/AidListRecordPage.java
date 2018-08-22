package project.healthcare.phone.page;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wilimx.app.ListItemPage;
import com.wilimx.app.adapter.SimpleListAdapter;
import com.wilimx.util.ListUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.ui.dialog.AidRecordListDialogFragment;
import project.healthcare.phone.ui.dialog.MapDialogFragment;
import project.healthcare.phone.view.IAidRecordListView;

public class AidListRecordPage extends TitleBackPage implements IAidRecordListView{

  public void toggleState() {
    try {
      if (mState == 0) {
        listItem.get(mPosition).put(CommonKeys.STATE, 0);
      } else {
        listItem.get(mPosition).put(CommonKeys.STATE, 2);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    mAidListAdapter.notifyDataSetChanged();
  }

  public void updateDate(JSONObject data) {
    JSONArray jsonArray = data.optJSONArray(CommonKeys.AIDLIST);
    listItem = ListUtil.parseList(jsonArray);

    mAidListAdapter.setItems(listItem);
    mAidListAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onPageInit() {
    super.onPageInit();
    ListView listView = (ListView)findView(R.id.record_list);
    listView.setAdapter(mAidListAdapter);
  }

  private void postCancelAid(int id, int type, int mid) {
    Bundle params = new Bundle();
    params.putInt(CommonKeys.ID, id);
    params.putInt(CommonKeys.TYPE, type);
    params.putInt(CommonKeys.MID, mid);
    mActionCallback.cancelAid(params);
  }

  private int mPosition;

  private int mState;

  private List<JSONObject> listItem = new ArrayList<JSONObject>();

  private SimpleListAdapter<JSONObject> mAidListAdapter = new SimpleListAdapter<JSONObject>() {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return page.getView(position, convertView, parent);
    }

    private ListItemPage page = new ListItemPage() {
      
      @Override
      protected int getContentResId() {
        return R.layout.list_aid_record;
      }

      @Override
      protected void onInitListItem(final int position) {
        final JSONObject data = getItem(position);
        String userName = data.optString(CommonKeys.USERNAME);
        if (data.optInt(CommonKeys.IS_OWN) == 1) {
          setText(R.id.record_label, getString(R.string.format_request_aid_myself));
          setViewVisibility(R.id.cancle_aid_button, View.VISIBLE);
        } else {
          setText(R.id.record_label, getString(R.string.format_request_aid_no_myself, userName));
          setViewVisibility(R.id.cancle_aid_button, View.GONE);
        }

        if (data.optInt(CommonKeys.STATE) == 1) {
          setViewVisibility(R.id.already_handle_text, View.GONE);
          setViewVisibility(R.id.already_cancel_text, View.GONE);
          setViewVisibility(R.id.handle_aid_button, View.VISIBLE);
        } else if (data.optInt(CommonKeys.STATE) == 2){
          setViewVisibility(R.id.already_handle_text, View.GONE);
          setViewVisibility(R.id.already_cancel_text, View.VISIBLE);
          setViewVisibility(R.id.cancle_aid_button, View.GONE);
          setViewVisibility(R.id.handle_aid_button, View.GONE);
        } else {
          setViewVisibility(R.id.already_handle_text, View.VISIBLE);
          setViewVisibility(R.id.already_cancel_text, View.GONE);
          setViewVisibility(R.id.cancle_aid_button, View.GONE);
          setViewVisibility(R.id.handle_aid_button, View.GONE);
        }

        setText(R.id.record_address, data.optString(CommonKeys.ADDRESS));
        setText(R.id.record_time, data.optString(CommonKeys.TIME));
        findView(R.id.cancle_aid_button).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            postCancelAid(data.optInt(CommonKeys.ID), 2, data.optInt(CommonKeys.MID));
            mState = 2;
            mPosition = position;
          }
        });

        findView(R.id.handle_aid_button).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            postCancelAid(data.optInt(CommonKeys.ID), 0, data.optInt(CommonKeys.MID));
            mState = 0;
            mPosition = position;
          }
        });

        findView(R.id.aid_record_container).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            MapDialogFragment fragment = new MapDialogFragment();
            fragment.updateMap(data);
            fragment.show(getFragmentManager(), null);
          }
        });
      }

    };
  };

  @Override
  public void setActionCallback(ActionCallback callback) {
      mActionCallback =callback;
  }

  @Override
  public void bindRes(View view) {
      mView = view;
  }

  @Override
  public void handleIsCVAvailable() {
    if (mFragment.whetherContentViewAvailable()) {
        toggleState();
    }
  }

  @Override
  public void setFragment(AidRecordListDialogFragment fragment) {
      mFragment = fragment ;
  }

  private ActionCallback mActionCallback;

  private View mView;

  private AidRecordListDialogFragment mFragment;
}

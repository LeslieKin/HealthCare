package project.healthcare.phone.page;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.wilimx.app.Page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.config.CommonKeys;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.ui.dialog.AidMemberListDialogFragment;
import project.healthcare.phone.ui.dialog.AidRecordListDialogFragment;
import project.healthcare.phone.ui.dialog.LoadingDialogFragment;
import project.healthcare.phone.ui.dialog.MapDialogFragment;
import project.healthcare.phone.ui.fragment.AidFragment;
import project.healthcare.phone.view.IAidView;

public class AidPage extends Page implements IAidView{

  public void setAidTipsDefault() {
    setText(R.id.aid_tips, getString(R.string.click_aid_button));
  }

  public void updateAidInfo(JSONObject info) {
    mAidInfo = info;
    isAid = true;
    if (getPageContent() != null) {
      String tip = mAidInfo.optString(CommonKeys.NAME);
      setText(R.id.aid_tips, getString(R.string.format_request_aid, tip));
      findView(R.id.aid_details).setOnClickListener(onClickListener);
    }
  }

  @Override
  protected void onPageInit() {
    bindClickEvents(
      R.id.button_aid,
      R.id.button_add_contact,
      R.id.button_aid_record
    );
    if (isAid) {
      String tip = mAidInfo.optString(CommonKeys.NAME);
      setText(R.id.aid_tips, getString(R.string.format_request_aid, tip));
      findView(R.id.aid_details).setOnClickListener(onClickListener);
      isAid = false;
    }
  }

  @Override
  protected void handleClickEvents(View v) {
    switch (v.getId()) {

    case R.id.button_aid:
        mCallback.getLocation(mActivity);
      break;

    case R.id.button_add_contact:
        mCallback.getMemberList();
      break;

    case R.id.button_aid_record:
        mCallback.getRecordList();
      break;
    }
  }

  private OnClickListener onClickListener = new OnClickListener() {

    @Override
    public void onClick(View v) {
      MapDialogFragment mapFragment = new MapDialogFragment();
      mapFragment.updateMap(mAidInfo);
      mapFragment.show(getFragmentManager(), null);
      findView(R.id.aid_details).setOnClickListener(null);
      setAidTipsDefault();
      Pref.getAppPreference().setIsNewAidInfo(false);
    }
  };

  @Override
  public void setActionCallback(final ActionCallback callback) {
      mCallback=callback;
  }

  @Override
  public void bindRes(final View view) {
    this.mView = view;
  }

  @Override
  public void setLoadTextContent(int id) {
      mAidLoading.setLoadTextContent(R.string.request_aiding);
  }

  @Override
  public void showAidLoading() {
    mAidLoading.show(getChildFragmentManager(), null);
  }

  @Override
  public void displayGRLSuccussResult(final JSONObject data ,int mid) {
    if (mAidFragment.whetherContentViewAvailable()) {
      AidRecordListDialogFragment fragment = new AidRecordListDialogFragment();
      fragment.setData(data, mid);
      fragment.show(getFragmentManager(), null);
    }
  }

  @Override
  public void displayGMLSuccussResult(JSONObject data, int mid) {
    if (mAidFragment.whetherContentViewAvailable()) {
      AidMemberListDialogFragment fragment = new AidMemberListDialogFragment();
      fragment.setData(data);
      fragment.setMid(mid);
      try {
        JSONArray memlist=data.getJSONArray("memlist");
        if(memlist.length()==0){
          Toast.makeText(mView.getContext(),R.string.no_contact,Toast.LENGTH_SHORT).show();
        }else{
          fragment.show(getFragmentManager(), null);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public void dismissLoadingDialog() {
    mLoadingDialogFragment.dismiss();
  }

  @Override
  public void dismissAidLoading() {
    mAidLoading.dismiss();
  }

  @Override
  public void showLoadingDialogFragment() {
    mLoadingDialogFragment.show(getChildFragmentManager(), null);
  }

  public void getRecordList(){
    mCallback.getRecordList();
  }

  public void setActivity(final Activity activity){
    mActivity=activity;
  }

  @Override
  public void setAidFragment(final AidFragment aidFragment) {
      mAidFragment = aidFragment;
  }

  private JSONObject mAidInfo = new JSONObject();

  private boolean isAid = false;

  private LoadingDialogFragment mAidLoading = new LoadingDialogFragment();

  private LoadingDialogFragment mLoadingDialogFragment = new LoadingDialogFragment();

  private  View mView;

  private ActionCallback mCallback;

  private Activity mActivity;

  private AidFragment mAidFragment;

}

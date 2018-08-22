package project.healthcare.phone.ui.fragment;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;

import com.wilimx.app.BaseFragment;
import com.wilimx.util.JSONUtil;

import org.json.JSONObject;

import project.healthcare.phone.R;
import project.healthcare.phone.constants.PrefKeys;
import project.healthcare.phone.model.Impl.AidModelImpl;
import project.healthcare.phone.page.AidPage;
import project.healthcare.phone.preference.Pref;
import project.healthcare.phone.presenter.impl.AidPresenterImpl;
import project.healthcare.phone.view.IAidView;

public class AidFragment extends BaseFragment<AidPage> implements OnSharedPreferenceChangeListener {

    private IAidView mAidView;
    private AidPresenterImpl mAidPresenter;

    /**
     * 碎片创建回调
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        if (mAidView == null) {
            mAidView = getPage();
        }

        mAidView.bindRes(this.getView());

        if (mAidPresenter == null) {
            mAidPresenter = new AidPresenterImpl();
            mAidPresenter.bindView(mAidView);
            mAidPresenter.bindModel(new AidModelImpl());
        }

        mAidPresenter.init();
        mAidView.setAidFragment(this);
        mAidView.setActivity(getActivity());

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(PrefKeys.NEW_AID_INFO)) {
            if (Pref.getAppPreference().getIsNewAidInfo()) {
                updateAidInfo(Pref.getAppPreference().getAidInfo());
            } else {
                getPage().setAidTipsDefault();
            }
        }
    }

    public void showRecordList() {
        isShowRecordList = true;
        if (isContentViewAvailable()) {
            getPage().getRecordList();
        }
    }

    public void updateAidInfo(String aidInfo) {
        JSONObject info = JSONUtil.parseJSON(aidInfo);
        getPage().updateAidInfo(info);
    }

    @Override
    protected int getContentResId() {
        return R.layout.page_aid;
    }

    @Override
    protected void onPageBuild(AidPage page) {
        super.onPageBuild(page);
        if (Pref.getAppPreference().getIsNewAidInfo()) {
            updateAidInfo(Pref.getAppPreference().getAidInfo());
        }

        Pref.getAppPreference().registerPrefChangeListener(this);
        if (isShowRecordList) {
            getPage().getRecordList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Pref.getAppPreference().unregisterPrefChangeListener(this);
    }

    private boolean isShowRecordList = false;

    public Boolean whetherContentViewAvailable() {
        return isContentViewAvailable();
    }
}

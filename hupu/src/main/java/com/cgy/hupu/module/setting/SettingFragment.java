package com.cgy.hupu.module.setting;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.R;
import com.cgy.hupu.injector.HasComponent;
import com.cgy.hupu.module.BaseActivity;
import com.cgy.hupu.utils.CacheUtil;
import com.cgy.hupu.utils.FileUtil;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.utils.ToastUtil;
import com.squareup.otto.Bus;

import java.io.File;

import javax.inject.Inject;


/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 15:24
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private ListPreference pTextSize;//字体大小
    private Preference pPicSavePath;//图片保存路径
    private Preference pClearCache;
    private Preference pTheme;
    private ListPreference pThreadSort;
    private ListPreference pSwipeBackEdgeMode;//手势返回方向

    @Inject
    Bus mBus;
    @Inject
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingComponent.class.cast(((HasComponent<SettingComponent>)getActivity()).getComponent())
                .inject(this);
        addPreferencesFromResource(R.xml.setting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        pTextSize = (ListPreference) findPreference("pTextSize");
        pTextSize.setOnPreferenceChangeListener(this);
        setListSetting(Integer.parseInt(prefs.getString("pTextSize", "4")), R.array.textSizeNum,
                pTextSize);
        pPicSavePath = findPreference("pPicSavePath");
        pPicSavePath.setOnPreferenceClickListener(this);
        pPicSavePath.setSummary("/sdcard" + File.separator + SettingPrefUtil.getPicSavePath(mContext) + File.separator);

        pClearCache = findPreference("pClearCache");
        pClearCache.setOnPreferenceClickListener(this);
        pClearCache.setSummary(CacheUtil.getCacheSize(mContext));

        pThreadSort = (ListPreference) findPreference("pThreadSort");
        pThreadSort.setOnPreferenceChangeListener(this);
        setListSetting(Integer.parseInt(prefs.getString("pThreadSort", "0")), R.array.sortType,
                pThreadSort);

        pSwipeBackEdgeMode = (ListPreference) findPreference("pSwipeBackEdgeMode");
        pSwipeBackEdgeMode.setOnPreferenceChangeListener(this);
        setListSetting(Integer.parseInt(prefs.getString("pSwipeBackEdgeMode", "0")),
                R.array.swipeBackEdgeMode, pSwipeBackEdgeMode);

        pTheme = findPreference("pTheme");
        pTheme.setOnPreferenceClickListener(this);
        pTheme.setSummary(getResources().getStringArray(R.array.mdColorNames)[SettingPrefUtil.getThemeIndex(mContext)]);


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if ("pTextSize".equals(preference.getKey())) {
            setListSetting(Integer.parseInt(newValue.toString()), R.array.textSizeNum, pTextSize);
        } else if ("pThreadSort".equals(preference.getKey())) {
            setListSetting(Integer.parseInt(newValue.toString()), R.array.sortType, pThreadSort);
        } else if ("pSwipeBackEdgeMode".equals(preference.getKey())) {
            setListSetting(Integer.parseInt(newValue.toString()), R.array.swipeBackEdgeMode, pSwipeBackEdgeMode);
            ((BaseActivity)getActivity()).reload();
        } else if ("pOfflineCount".equals(preference.getKey())) {

        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if ("pPicSavePath".equals(preference.getKey())) {
            modifyImageSavePath();
        } else if ("pClearCache".equals(preference.getKey())) {
            cleanCache();
        } else if ("pTheme".equals(preference.getKey())) {
            ColorsDialogFragment.launch(getActivity());
        }
        return true;
    }

    private void cleanCache() {
        CacheUtil.cleanApplicationCache(mContext);
        Toast.makeText(getActivity(), "缓存清理成功", Toast.LENGTH_SHORT).show();
        pClearCache.setSummary(CacheUtil.getCacheSize(mContext));
    }

    private void setTextSize(int value) {
        String[] valueTitleArr = getResources().getStringArray(R.array.textSizeNum);
        pTextSize.setSummary(valueTitleArr[value]);
    }

    protected void setListSetting(int value, int hintId, ListPreference listPreference) {
        String[] valueTitleArr = getResources().getStringArray(hintId);
        listPreference.setSummary(valueTitleArr[value]);
    }

    private void modifyImageSavePath() {
        new MaterialDialog.Builder(getActivity()).title("修改图片保存路径")
                .input(null, SettingPrefUtil.getPicSavePath(mContext), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        if (TextUtils.isEmpty(charSequence)) {
                            ToastUtil.showToast("路径不能为空");
                            return;
                        }
                        String path = FileUtil.getSdcardPath() + File.separator + charSequence + File.separator;
                        File file = new File(path);
                        if (file.exists() || file.mkdirs()) {
                            SettingPrefUtil.setPicSavePath(mContext, charSequence.toString());
                            pPicSavePath.setSummary("/sdcard" + File.separator + charSequence + File.separator);
                            ToastUtil.showToast("更新成功");
                        } else {
                            ToastUtil.showToast("更新失败");
                        }
                    }
                })
                .negativeText("取消")
                .show();
    }
}

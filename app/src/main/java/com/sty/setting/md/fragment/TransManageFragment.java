package com.sty.setting.md.fragment;

import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.RingtonePreference;

import com.sty.setting.md.R;
import com.sty.setting.md.SysParam;
import com.sty.setting.md.app.MyApplication;

/**
 * Created by shity on 2017/8/11/0011.
 */

public class TransManageFragment extends BasePreferenceFragment {

    @Override
    protected int getResourceId() {
        return R.xml.pref_trans_para;
    }

    @Override
    protected void initPreference() {
        bindPreference(SysParam.StringParam.EDC_MERCHANT_ID);
        bindPreference(SysParam.StringParam.EDC_TERMINAL_ID);
        bindPreference(SysParam.BooleanParam.SETTLE_AUTO_LOGOUT);
        bindPreference(SysParam.BooleanParam.SETTLE_PRINT_DETAIL);
        bindPreference(SysParam.StringParam.COMM_TYPE);
        bindPreference(SysParam.NumberParam.COMM_TIMEOUT);
        bindPreference(SysParam.NumberParam.COMM_TRANS_TIMEOUT);
        bindPreference(SysParam.NumberParam.COMM_REQUERY_INTERVAL_TIME);
        bindPreference(SysParam.NumberParam.COMM_REQUERY_TIMEOUT);
    }

    @Override
    protected boolean onListPreferenceChanged(ListPreference preference, Object value, boolean isInitLoading) {
        String stringValue = value.toString();
        int index = preference.findIndexOfValue(stringValue);

        preference.setSummary(index >= 0 ? preference.getEntries()[index] : null);

        return true;
    }

    @Override
    protected boolean onCheckBoxPreferenceChanged(CheckBoxPreference preference, Object value, boolean isInitLoading) {
        return true;
    }

    @Override
    protected boolean onRingtonePreferenceChanged(RingtonePreference preference, Object value, boolean isInitLoading) {
        return false;
    }

    @Override
    protected boolean onEditTextPreferenceChanged(EditTextPreference preference, Object value, boolean isInitLoading) {
        String stringValue = value.toString();
        preference.setSummary(stringValue);
        return true;
    }

    @Override
    protected boolean onMultiSelectListPreferenceChanged(MultiSelectListPreference preference, Object value, boolean isInitLoading) {
        return false;
    }
}

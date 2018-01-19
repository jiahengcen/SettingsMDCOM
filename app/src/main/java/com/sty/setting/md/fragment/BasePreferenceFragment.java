package com.sty.setting.md.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.annotation.XmlRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sty.setting.md.R;
import com.sty.setting.md.SysParam;

public abstract class BasePreferenceFragment extends PreferenceFragment {
    protected static final String TAG = "PreferenceFragment";

    private boolean isFirst = true;

    private Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            if (preference instanceof ListPreference) {
                return onListPreferenceChanged((ListPreference) preference, value, isFirst);
            } else if (preference instanceof CheckBoxPreference) {
                return onCheckBoxPreferenceChanged((CheckBoxPreference) preference, value, isFirst);
            } else if (preference instanceof EditTextPreference) {
                return onEditTextPreferenceChanged((EditTextPreference) preference, value, isFirst);
            } else if (preference instanceof RingtonePreference) {
                return onRingtonePreferenceChanged((RingtonePreference) preference, value, isFirst);
            } else if (preference instanceof MultiSelectListPreference) {
                return onMultiSelectListPreferenceChanged((MultiSelectListPreference) preference, value, isFirst);
            } else {
                String stringValue = value.toString();
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getResourceId());
        isFirst = true;
        initPreference();
        isFirst = false;
    }

    /**
     * Added by Steven.T 2017-10-12 10:29:18
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.divider)); //设置背景颜色
        //设置作用padding使自定义控件的宽度能充满全屏
        listView.setPadding( 0, listView.getPaddingTop(), 0, listView.getPaddingBottom());
        listView.setDividerHeight(0); //取消自带的分割线，自己自定义
        return view;
    }

    protected void bindPreference(SysParam.NumberParam key) {
        bindPreference(key.toString());
    }

    protected void bindPreference(SysParam.StringParam key) {
        bindPreference(key.toString());
    }

    protected void bindPreference(SysParam.BooleanParam key) {
        bindPreference(key.toString());
    }

    protected void bindListPreference(SysParam.StringParam key, final CharSequence[] entries,
                                      final CharSequence[] entryValues) {
        if (entries == null || entryValues == null) {
            bindPreference(key.toString());
        } else {
            bindPreference(key.toString(), entries, entryValues);
        }
    }

    private void bindPreference(String key) {
        Preference preference = super.findPreference(key);
        preference.setOnPreferenceChangeListener(listener);
        if (preference instanceof CheckBoxPreference) {
            listener.onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext()).getBoolean(preference.getKey(), false));
        } else if(preference instanceof SwitchPreference){ //Added by Steven.T 2017-10-12 11:50:11
            listener.onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext()).getBoolean(preference.getKey(), false));
        } else if (!(preference instanceof MultiSelectListPreference)) {
            listener.onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
        }
    }

    private void bindPreference(String key, final CharSequence[] entries,
                                final CharSequence[] entryValues) {
        Preference preference = super.findPreference(key);
        preference.setOnPreferenceChangeListener(listener);
        if (preference instanceof ListPreference) {
            ((ListPreference) preference).setEntries(entries);
            ((ListPreference) preference).setEntryValues(entryValues);
            listener.onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
        }
    }

    @XmlRes
    protected abstract int getResourceId();

    protected abstract void initPreference();

    protected abstract boolean onListPreferenceChanged(ListPreference preference, Object value, boolean isInitLoading);

    protected abstract boolean onCheckBoxPreferenceChanged(CheckBoxPreference preference, Object value, boolean isInitLoading);

    protected abstract boolean onRingtonePreferenceChanged(RingtonePreference preference, Object value, boolean isInitLoading);

    protected abstract boolean onEditTextPreferenceChanged(EditTextPreference preference, Object value, boolean isInitLoading);

    protected abstract boolean onMultiSelectListPreferenceChanged(MultiSelectListPreference preference, Object value, boolean isInitLoading);
}

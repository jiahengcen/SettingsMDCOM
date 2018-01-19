package com.sty.setting.md;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sty.setting.md.app.MyApplication;

public class SysParam {

    private static final String TAG = "SysParam";

    public enum NumberParam {
        /**
         * 超时时间
         */
        COMM_TIMEOUT("COMM_TIMEOUT"),
        COMM_TRANS_TIMEOUT("COMM_TRANS_TIMEOUT"),
        COMM_REQUERY_INTERVAL_TIME("COMM_REQUERY_INTERVAL_TIME"),
        COMM_REQUERY_TIMEOUT("COMM_REQUERY_TIMEOUT");

        private String str;
        NumberParam(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public enum StringParam {
        /**
         * 通讯方式
         */
        COMM_TYPE("COMM_TYPE"),

        EDC_MERCHANT_ID("EDC_MERCHANT_ID"),
        EDC_TERMINAL_ID("EDC_TERMINAL_ID");

        private String str;
        StringParam(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public enum BooleanParam {
        SETTLE_PRINT_DETAIL("SETTLE_PRINT_DETAIL"),
        SETTLE_AUTO_LOGOUT("SETTLE_AUTO_LOGOUT");

        private String str;
        BooleanParam(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public static class Constant {
        /**
         * 通讯类型
         */
        public enum CommType {
            SYSTEM_ADAPTION("SYSTEM_ADAPTION"),
            MOBILE("MOBILE"),
            WIFI("WIFI"),
            DEMO("DEMO");

            private final String str;
            CommType(String str) {
                this.str = str;
            }

            @Override
            public String toString() {
                return str;
            }
        }

        private Constant() {
            //do nothing
        }
    }

    private static SysParam mSysParam;

    private SysParam() {
        load(); // 加载参数内容到SysParam中
    }

    public static synchronized SysParam getInstance() {
        if (mSysParam == null) {
            mSysParam = new SysParam();
        }
        return mSysParam;
    }

    // 系统参数加载，如果db中不存在则添加
    private void load() {
        // 设置默认参数值
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        Editor editor = sharedPreferences.edit();

        if (editor != null) {
            set(editor, NumberParam.COMM_TIMEOUT, 30);     //连接超时时间
            set(editor, NumberParam.COMM_TRANS_TIMEOUT, 10);        //交易超时时间T1
            set(editor, NumberParam.COMM_REQUERY_INTERVAL_TIME, 3); //查询间隔时间 T3
            set(editor, NumberParam.COMM_REQUERY_TIMEOUT, 5);       //再次查询超时时间 T4

            // 通讯方式
            set(editor, StringParam.COMM_TYPE, Constant.CommType.WIFI.toString());

            //商户参数
            set(editor, StringParam.EDC_MERCHANT_ID, "000000000000000");
            set(editor, StringParam.EDC_TERMINAL_ID, "00000000");

            //结算交易控制开关
            set(editor, BooleanParam.SETTLE_PRINT_DETAIL, true); //是否打印结算交易详情
            set(editor, BooleanParam.SETTLE_AUTO_LOGOUT, true); //是否自动签退

            editor.apply();
        }
    }

    public synchronized int get(NumberParam name) {
        return get(name, 0);
    }

    public synchronized int get(NumberParam name, int defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        String temp = sharedPreferences.getString(name.toString(), null);
        if (temp != null) {
            try {
                return Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                Log.w(TAG, "", e);
            }
        }
        return defValue;
    }

    public synchronized String get(StringParam name) {
        return get(name, null);
    }

    public synchronized String get(StringParam name, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        return sharedPreferences.getString(name.toString(), defValue);
    }

    public synchronized boolean get(BooleanParam name) {
        return get(name, false);
    }

    public synchronized boolean get(BooleanParam name, boolean defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        return sharedPreferences.getBoolean(name.toString(), defValue);
    }

    public synchronized void set(NumberParam name, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        Editor editor = sharedPreferences.edit();
        set(editor, name, value);
    }

    private synchronized void set(Editor editor, NumberParam name, int value) {
        editor.putString(name.toString(), String.valueOf(value));
        editor.apply();
    }

    public synchronized void set(NumberParam name, long value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        Editor editor = sharedPreferences.edit();
        set(editor, name, value);
    }

    private synchronized void set(Editor editor, NumberParam name, long value) {
        editor.putString(name.toString(), String.valueOf(value));
        editor.apply();
    }

    public synchronized void set(StringParam name, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        Editor editor = sharedPreferences.edit();
        set(editor, name, value);
    }

    private synchronized void set(Editor editor, StringParam name, String value) {
        editor.putString(name.toString(), value);
        editor.apply();
    }

    public synchronized void set(BooleanParam name, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp());
        Editor editor = sharedPreferences.edit();
        set(editor, name, value);
    }

    private synchronized void set(Editor editor, BooleanParam name, boolean value) {
        editor.putBoolean(name.toString(), value);
        editor.apply();
    }

}

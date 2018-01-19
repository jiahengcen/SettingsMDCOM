package com.sty.setting.md.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected static final String TAG = "The Fragment";

    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        context = view.getContext();
        initData();
        initView(view);

        return view;
    }

    /**
     * get layout ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView(View view);

    // AET-93
    @Override
    public final void onClick(View v) {
        onClickProtected(v);
    }

    protected void onClickProtected(View v) {
        //do nothing
    }
}

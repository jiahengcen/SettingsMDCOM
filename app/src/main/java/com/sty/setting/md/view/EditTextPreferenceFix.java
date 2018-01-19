package com.sty.setting.md.view;

import android.app.Activity;
import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class EditTextPreferenceFix extends EditTextPreference {

    public EditTextPreferenceFix(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextPreferenceFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreferenceFix(Context context) {
        this(context, null);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        getEditText().clearFocus();
        hideSysInput();
    }

    private void hideSysInput() {
        Window window = ((Activity) getContext()).getWindow();
        final View contentView = window.findViewById(Window.ID_ANDROID_CONTENT);

        if (contentView.getWindowToken() != null) {

            contentView.post(new Runnable() {
                @Override
                public void run() {
                   hideSystemKeyboard(getContext(), contentView);
                }
            });
        }
    }

    private void hideSystemKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

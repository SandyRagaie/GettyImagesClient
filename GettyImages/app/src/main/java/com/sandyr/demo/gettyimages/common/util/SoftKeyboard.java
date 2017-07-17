package com.sandyr.demo.gettyimages.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sandyr on 7/16/2017.
 */
public class SoftKeyboard {
    private static final SoftKeyboard ourInstance = new SoftKeyboard();
    private static Context mContext;
    public static SoftKeyboard getInstance(Context context) {
        mContext=context;
        return ourInstance;
    }

    private SoftKeyboard() {
    }

    public void hideSoftKeyboard() {
        if (((Activity)mContext).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) ((Activity)mContext).getSystemService(((Activity)mContext).INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(EditText search) {
        InputMethodManager imm = (InputMethodManager) ((Activity)mContext).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
    }
}

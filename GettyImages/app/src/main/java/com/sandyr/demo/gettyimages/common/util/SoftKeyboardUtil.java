package com.sandyr.demo.gettyimages.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sandyr on 7/16/2017.
 */
public class SoftKeyboardUtil {
    private static SoftKeyboardUtil ourInstance;

    public static SoftKeyboardUtil getInstance() {
        if(ourInstance == null){
            ourInstance = new SoftKeyboardUtil();
        }
        return ourInstance;
    }

    private SoftKeyboardUtil() {
    }

    public void hideSoftKeyboard(Context context) {
        if (((Activity) context).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }
}

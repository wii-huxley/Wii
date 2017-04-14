package com.huxley.wii.yl.common.utils;

import android.app.Activity;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 软键盘工具类
 * Created by huxley on 17/1/17.
 */
public class SoftInputUtil {

    /**
     * 弹出软键盘
     */
    public static void showSoftInput(Activity activity) {
        showSoftInput(activity, null);
    }

    /**
     * 弹出软键盘，并指定获取焦点的view
     */
    public static void showSoftInput(Activity activity, View view) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (view != null) inputMethodManager.showSoftInput(view, 0);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 跟随dialog一起弹出软键盘，并指定获取焦点的view
     */
    public static void showSoftInputByAlertDialog(AlertDialog alertDialog, Activity activity, View view) {
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            showSoftInput(activity, view);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                InputMethodManager manager = (InputMethodManager) (activity.getSystemService(INPUT_METHOD_SERVICE));
                if (manager != null) {
                    manager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }
}

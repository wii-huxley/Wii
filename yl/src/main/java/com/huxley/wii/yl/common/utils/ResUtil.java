package com.huxley.wii.yl.common.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.huxley.wii.yl.common.YlApp;

import java.util.Arrays;
import java.util.List;


/**
 * Resources 工具类
 * Created by huxley on 16/3/3.
 */
public class ResUtil {

    private static Resources      resources;
    private static DisplayMetrics sDisplayMetrics;
    private static int            widthPixels;
    private static int            heightPixels;

    private static Resources getResources() {
        if (resources== null) {
            resources = YlApp.getInstance().getResources();
        }
        return resources;
    }

    private static DisplayMetrics getDisplayMetrics() {
        if (sDisplayMetrics == null) {
            sDisplayMetrics = getResources().getDisplayMetrics();
        }
        return sDisplayMetrics;
    }

    public static int getScreenWidth() {
        if (widthPixels == 0) {
            widthPixels = getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    public static int getScreenHeight() {
        if (heightPixels == 0) {
            heightPixels = getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }

    public static String getString (@IdRes @StringRes int strResId) {
        return getResources().getString(strResId);
    }

    public static int getInt(@IdRes @StringRes int strResId){
        return Integer.valueOf(getString(strResId));
    }

    public static int getColor (@ColorRes int colorResId) {
        return ContextCompat.getColor(YlApp.getInstance(), colorResId);
    }

    public static float getDimen(@DimenRes int dimenResId) {
        return getResources().getDimension(dimenResId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(YlApp.getInstance(), drawableResId);
    }

    public static Bitmap getBitmap(@DrawableRes int drawableResId) {
        return BitmapFactory.decodeResource(getResources(), drawableResId);
    }

    public static String[] getStringArray(@ArrayRes int strAryResId) {
        return getResources().getStringArray(strAryResId);
    }

    public static List<String> getStrAry(@ArrayRes int strAryResId) {
        return Arrays.asList(getStringArray(strAryResId));
    }

    private String[][] getTwoDimensionalArray(@ArrayRes int strAryResId) {
        String[] array = getStringArray(strAryResId);
        String[][] twoDimensionalArray = null;
        for (int i = 0; i < array.length; i++) {
            String[] tempArray = array[i].split(",");
            if (twoDimensionalArray == null) {
                twoDimensionalArray = new String[array.length][tempArray.length];
            }
            System.arraycopy(tempArray, 0, twoDimensionalArray[i], 0, tempArray.length);
        }
        return twoDimensionalArray;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int spToPx(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics());
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    /**
     * 获取状态栏的高度
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dpToPx(25);
        }
        return result;
    }

    /**
     * 取导航栏高度
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dpToPx(40);
        }
        return result;
    }
}

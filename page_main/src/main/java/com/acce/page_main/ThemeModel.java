package com.acce.page_main;

import android.app.Activity;

import com.acce.page_main.bean.Category;
import com.huxley.wiitools.utils.ResUtils;

/**
 * Created by huxley on 2017/8/14.
 */
public class ThemeModel {

    private static ThemeModel instance;
    private        Category   mCategory;
    private        boolean    isModel;

    private ThemeModel() {
    }

    public static ThemeModel getInstance() {
        if (instance == null) {
            synchronized (ThemeModel.class) {
                if (instance == null) {
                    instance = new ThemeModel();
                }
            }
        }
        return instance;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
        isModel = category != null;
    }

    public String getTitle() {
        if (mCategory == null) {
            return ResUtils.getString(R.string.app_name);
        }
        return mCategory.title;
    }

    public int getDarkPrimaryColor() {
        if (mCategory == null) {
            return ResUtils.getColor(R.color.colorPrimaryDark);
        }
        return ResUtils.getColor(mCategory.theme.colorPrimaryDarkId);
    }

    public int getPrimaryColor() {
        if (mCategory == null) {
            return ResUtils.getColor(R.color.colorPrimary);
        }
        return ResUtils.getColor(mCategory.theme.colorPrimaryId);
    }

    public void setStatusBarColor(Activity activity) {
        activity.getWindow().setStatusBarColor(getDarkPrimaryColor());
    }

    public void clear() {
        setCategory(null);
    }
}
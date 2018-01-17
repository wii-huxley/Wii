package com.acce.page_main.bean;

import com.acce.page_main.R;

import java.io.Serializable;

/**
 * Created by huxley on 2017/4/10.
 */

public enum Theme implements Serializable{
    red(R.color.color_red_500, R.color.color_red_800,
            R.color.color_red_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    pink(R.color.color_pink_500, R.color.color_pink_800,
            R.color.color_pink_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    purple(R.color.color_purple_500, R.color.color_purple_800,
            R.color.color_purple_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    deep_purple(R.color.color_deep_purple_500, R.color.color_deep_purple_800,
            R.color.color_deep_purple_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    indigo(R.color.color_indigo_500, R.color.color_indigo_800,
            R.color.color_indigo_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    blue(R.color.color_blue_500, R.color.color_blue_800,
            R.color.color_blue_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    light_blue(R.color.color_light_blue_500, R.color.color_light_blue_800,
         R.color.color_light_blue_50, R.color.text_color_dark,
         R.color.color_blue_grey_600, 0),
    cyan(R.color.color_cyan_500, R.color.color_cyan_800,
            R.color.color_cyan_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    teal(R.color.color_teal_500, R.color.color_teal_800,
            R.color.color_teal_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    green(R.color.color_green_500, R.color.color_green_800,
            R.color.color_green_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    light_green(R.color.color_light_green_500, R.color.color_light_green_800,
            R.color.color_light_green_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    lime(R.color.color_lime_500, R.color.color_lime_800,
            R.color.color_lime_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    yellow(R.color.color_yellow_500, R.color.color_yellow_800,
            R.color.color_yellow_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    amber(R.color.color_amber_500, R.color.color_amber_800,
            R.color.color_amber_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    orange(R.color.color_orange_500, R.color.color_orange_800,
            R.color.color_orange_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    deep_orange(R.color.color_deep_orange_500, R.color.color_deep_orange_800,
            R.color.color_deep_orange_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    brown(R.color.color_brown_500, R.color.color_brown_800,
            R.color.color_brown_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0),
    grey(R.color.color_grey_500, R.color.color_grey_800,
            R.color.color_grey_50, R.color.text_color_dark,
            R.color.color_blue_grey_600, 0);


    public final int colorPrimaryId;
    public final int windowBackgroundColorId;
    public final int colorPrimaryDarkId;
    public final int textColorPrimaryId;
    public final int accentColorId;
    public final int styleId;

    Theme(final int colorPrimaryId, final int colorPrimaryDarkId,
          final int windowBackgroundColorId, final int textColorPrimaryId,
          final int accentColorId, final int styleId) {
        this.colorPrimaryId = colorPrimaryId;
        this.windowBackgroundColorId = windowBackgroundColorId;
        this.colorPrimaryDarkId = colorPrimaryDarkId;
        this.textColorPrimaryId = textColorPrimaryId;
        this.accentColorId = accentColorId;
        this.styleId = styleId;
    }
}

package com.huxley.wiisample.common;

import android.widget.ImageView;

import com.acce.page_main.utils.Tools;
import com.huxley.wiisample.R;
import com.huxley.wiisample.WiiYlApp;
import com.huxley.wiitools.utils.ResUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by huxley on 2017/8/23.
 */

public class ImageLoaderUtils {


    public static void setGankImage(ImageView image, String url) {
        Picasso.with(WiiYlApp.getInstance())
                .load(url)
                .resize(Tools.getScreenWidth() / 2 - ResUtils.dpToPx(10), Tools.getScreenHeight())
                .placeholder(R.drawable.img_empty)
                .error(R.drawable.img_empty)
                .centerInside()
                .into(image);
    }

    public static void setMovieImage(ImageView image, String url) {
        Picasso.with(WiiYlApp.getInstance())
                .load(url)
                .resize(Tools.getScreenWidth(), image.getHeight())
                .centerCrop()
                .into(image);
    }
}

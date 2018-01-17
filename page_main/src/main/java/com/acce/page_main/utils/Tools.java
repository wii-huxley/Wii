package com.acce.page_main.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acce.page_main.R;
import com.acce.page_main.ThemeModel;
import com.huxley.fragmentation.base.SupportFragment;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.yl.common.YlApp;
import com.huxley.yl.common.utils.doubleClick.OnYlClickListener;

import java.util.List;

import static android.support.v4.view.ViewCompat.animate;

/**
 * Created by huxley on 2017/8/14.
 */
public class Tools {

    public static String getDay(String day) {
        return day.substring(0, 10).replaceAll("-", "/");
    }

    public static int getScreenWidth() {
        return YlApp.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return YlApp.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static void setTitle(final SupportFragment fragment, FrameLayout toolbar, final View... llContent) {
        ThemeModel.getInstance().setStatusBarColor(fragment.getActivity());

        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(ThemeModel.getInstance().getTitle());
        tvTitle.setTransitionName(ThemeModel.getInstance().getTitle());
        tvTitle.setBackgroundColor(ThemeModel.getInstance().getPrimaryColor());
        tvTitle.setTextColor(Color.WHITE);
        final ImageView iv = (ImageView) toolbar.findViewById(R.id.iv_press_back);
        iv.setImageDrawable(ResUtils.tintDrawable(R.mipmap.ic_arrow_back_white_24dp, Color.WHITE));
        iv.setOnClickListener(new OnYlClickListener() {
            @Override
            public void onYlClick(View view) {
                fragment.onBackPressedSupport();
            }
        });
        for (View view : llContent) {
            view.setScaleX(0f);
            view.setScaleY(0f);
            view.setAlpha(0f);
        }
        for (View view : llContent) {
            animate(view).scaleX(1f).scaleY(1f).alpha(1f).setInterpolator(new FastOutSlowInInterpolator()).start();
        }
        fragment.setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                iv.setScaleX(0f);
                iv.setScaleY(0f);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                animate(iv).setStartDelay(ResUtils.getInteger(R.integer.anim_duration_medium)).scaleX(1f).scaleY(1f).alpha(1f).start();
            }
        });
    }

    public static void onBackPressedSupport(final SupportFragment fragment, FrameLayout toolbar, LinearLayout llContent, final FragmentActivity activity) {
        final ImageView iv = (ImageView) toolbar.findViewById(R.id.iv_press_back);
        ViewCompat.animate(llContent)
                .scaleX(.7f)
                .scaleY(.7f)
                .alpha(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
        ViewCompat.animate(iv)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(ResUtils.getInteger(R.integer.anim_duration_fast))
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(View view) {
                        ThemeModel.getInstance().clear();
                        ThemeModel.getInstance().setStatusBarColor(activity);
                        fragment.pop();
                    }
                }).start();
    }
}

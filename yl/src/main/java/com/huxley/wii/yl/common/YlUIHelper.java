package com.huxley.wii.yl.common;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huxley.wii.yl.R;
import com.huxley.wii.yl.common.utils.StringUtils;
import com.huxley.wii.yl.common.utils.doubleClick.OnYlClickListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.OnItemLongClickListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;

/**
 * Created by huxley on 17/1/21.
 */

public class YlUIHelper {

    private static final int INVALID = -1;

    public static View getView(Context context, int resourceId) {
        return getView(context, resourceId, null);
    }

    public static View getView(Context context, int resourceId, View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view != null) {
            return view;
        }
        if (resourceId != INVALID) {
            view = inflater.inflate(resourceId, null);
        }
        return view;
    }

    public static void setTextView(TextView textView, String content) {
        if (!StringUtils.isEmpty(content) && textView != null) {
            textView.setText(content);
        } else if (textView != null){
            textView.setText("");
        }
    }

    public static class BaseOtherView{

        public static Handler setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
            swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
            swipeRefreshLayout.setColorSchemeResources(R.color.color_amber_500, R.color.color_blue_500,
                    R.color.color_brown_500, R.color.color_cyan_500, R.color.color_green_500, R.color.color_grey_500,
                    R.color.color_indigo_500, R.color.color_lime_500, R.color.color_orange_500, R.color.color_teal_500);
            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_amber_50);
            return new Handler();
        }
    }

    public static class BaseToolbars {

        public static void setTitle(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity, String title) {
            toolbar.setTitle(title);
            activity.setSupportActionBar(toolbar);
        }

        public static void setSubtitle(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity, String title, String subtitle) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
            activity.setSupportActionBar(toolbar);
        }

        public static void setMenu(Toolbar toolbar, @MenuRes int menuId, Toolbar.OnMenuItemClickListener listener) {
            toolbar.inflateMenu(menuId);
            toolbar.setOnMenuItemClickListener(listener);
        }

        public static void setBack(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            toolbar.setNavigationOnClickListener(new OnYlClickListener() {
                @Override
                public void onYlClick(View view) {
                    activity.finish();
                }
            });
        }

        public static void setTitleAndBack(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity, String title) {
            setTitle(toolbar, activity, title);
            setBack(toolbar, activity);
        }

        public static void setTitleAndMenu(@NonNull Toolbar toolbar, String title, @MenuRes int menuId, Toolbar.OnMenuItemClickListener listener) {
            setMenu(toolbar, menuId, listener);
            toolbar.setTitle(title);
        }

        public static void setTitleAndBackAndMenu(@NonNull Toolbar toolbar, @NonNull final AppCompatActivity activity, String title, @MenuRes int menuId, Toolbar.OnMenuItemClickListener listener) {
            setMenu(toolbar, menuId, listener);
            toolbar.setTitle(title);
            setBack(toolbar, activity);
        }
    }

    public static class BaseDialogs {

        public HashMap<String, DialogPlus> sDialogPluss = new HashMap<>();

        public DialogPlus getBottom(Context context, String title, Holder holder, BaseAdapter adapter, OnItemClickListener listener, OnItemLongClickListener longListener) {
            if (sDialogPluss.containsKey(title)) {
                return sDialogPluss.get(title);
            } else {
                View headerView = getView(context, R.layout.dialog_header);
                ViewHolder headerHolder = new ViewHolder(context, headerView);
                headerHolder.setText(R.id.tv_title, title);
                return DialogPlus.newDialog(context)
                        .setExpanded(false)
                        .setCancelable(true)
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(adapter)
                        .setContentHolder(holder)
                        .setHeader(headerView)
                        .setOnItemClickListener(listener)
                        .setOnItemLongClickListener(longListener)
                        .create();
            }
        }

        public DialogPlus getBottom(Context context, String title, Holder holder, BaseAdapter adapter, OnItemClickListener listener) {
            if (sDialogPluss.containsKey(title)) {
                return sDialogPluss.get(title);
            } else {
                View headerView = getView(context, R.layout.dialog_header);
                ViewHolder headerHolder = new ViewHolder(context, headerView);
                headerHolder.setText(R.id.tv_title, title);
                return DialogPlus.newDialog(context)
                        .setExpanded(false)
                        .setCancelable(true)
                        .setGravity(Gravity.BOTTOM)
                        .setAdapter(adapter)
                        .setContentHolder(holder)
                        .setHeader(headerView)
                        .setOnItemClickListener(listener)
                        .create();
            }
        }

        public DialogPlus getCenter(Context context, String title, View contentView, String cancel,
                                    OnYlClickListener cancelListener, String sure, View.OnClickListener sureListener) {
            if (sDialogPluss.containsKey(title)) {
                return sDialogPluss.get(title);
            } else {
                View headerView = getView(context, R.layout.dialog_header);
                ViewHolder headerHolder = new ViewHolder(context, headerView);
                headerHolder.setText(R.id.tv_title, title);
                View footerView = getView(context, R.layout.dialog_footer);
                ViewHolder footerHolder = new ViewHolder(context, footerView);
                footerHolder.setText(R.id.tv_cancel, cancel);
                headerHolder.setOnClickListener(R.id.tv_cancel, cancelListener);
                footerHolder.setText(R.id.tv_sure, sure);
                headerHolder.setOnClickListener(R.id.tv_sure, sureListener);
                return DialogPlus.newDialog(context)
                        .setExpanded(false)
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(contentView))
                        .setHeader(headerView)
                        .setFooter(footerView)
                        .create();
            }
        }


        public DialogPlus getCenter(Context context, String title, View contentView, String cancel, String sure, OnClickListener listener) {
            if (sDialogPluss.containsKey(title)) {
                return sDialogPluss.get(title);
            } else {
                View headerView = getView(context, R.layout.dialog_header);
                ViewHolder headerHolder = new ViewHolder(context, headerView);
                headerHolder.setText(R.id.tv_title, title);
                View footerView = getView(context, R.layout.dialog_footer);
                ViewHolder footerHolder = new ViewHolder(context, footerView);
                footerHolder.setText(R.id.tv_cancel, cancel);
                footerHolder.setText(R.id.tv_sure, sure);
                return DialogPlus.newDialog(context)
                        .setExpanded(false)
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(contentView))
                        .setHeader(headerView)
                        .setOnClickListener(listener)
                        .setFooter(footerView)
                        .create();
            }
        }
    }
}

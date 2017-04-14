package com.huxley.wii.yl.common.utils.doubleClick;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;

/**
 * Created by huxley on 17/1/14.
 */

public abstract class OnRvClickListener implements MultiItemTypeAdapter.OnItemClickListener, Serializable {

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (DoubleClickHandler.isFastDoubleClick()) {
            return;
        }
        onRvItemClick(view, holder, position);
    }

    public abstract void onRvItemClick(View view, RecyclerView.ViewHolder holder, int position);

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (DoubleClickHandler.isFastDoubleClick()) {
            return true;
        }
        return onRvItemLongClick(view, holder, position);
    }

    public boolean onRvItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}

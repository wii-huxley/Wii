package com.huxley.wiisample.page.codekk.viewBinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.wiisample.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by huxley on 2017/9/16.
 */
public class CodekkProjectTitleViewBinder extends ItemViewBinder<String, CodekkProjectTitleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.codekk_item_project_title, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String title) {
        holder.tvTitle.setText(title);
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // set title full span
        layoutParams.setFullSpan(true);
        holder.itemView.setLayoutParams(layoutParams);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }


    }
}

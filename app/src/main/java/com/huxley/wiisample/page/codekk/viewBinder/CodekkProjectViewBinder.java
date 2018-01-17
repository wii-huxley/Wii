package com.huxley.wiisample.page.codekk.viewBinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.page_web.FinestWebView;
import com.huxley.wiisample.R;
import com.huxley.wiisample.model.netBean.CodekkProjectBean;
import com.huxley.wiitools.utils.ResUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by huxley on 2017/9/16.
 */
public class CodekkProjectViewBinder extends ItemViewBinder<CodekkProjectBean, CodekkProjectViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.codekk_item_project, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CodekkProjectBean codekkProjectBean) {
        holder.tvName.setText(codekkProjectBean.projectName);
        holder.tvContent.setText(codekkProjectBean.desc);
        holder.itemView.setOnClickListener(view -> new FinestWebView.Builder(holder.itemView.getContext())
                .urlColor(ResUtils.getColor(R.color.white))
                .setCustomAnimations(R.anim.h_fragment_enter, 0, 0, R.anim.h_fragment_exit)
                .titleColor(ResUtils.getColor(R.color.white))
                .iconDefaultColor(ResUtils.getColor(R.color.white))
                .titleDefault(codekkProjectBean.projectName)
                .show(codekkProjectBean.codeKKUrl));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvContent;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

package com.huxley.wiisample.page.markdownReader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.acce.page_main.ThemeModel;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.common.treeview.adapter.TreeListViewAdapter;
import com.huxley.wiisample.common.treeview.bean.Node;
import com.huxley.wiisample.model.localBean.KnowledgeBean;
import com.huxley.wiisample.page.markdownReader.detail.MarkDownReaderDetailFragment;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class MarkDownReaderFragment extends MvpSwipeBackFragment<MarkDownReaderContract.Present>
    implements MarkDownReaderContract.View {

    private KnowledgeAdapter mAdapter;
    private ArrayList<KnowledgeBean> knowledgeBeans;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.lv)
    ListView mLv;


    public static MarkDownReaderFragment newInstance() {
        Bundle args = new Bundle();
        MarkDownReaderFragment fragment = new MarkDownReaderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.mark_down_reader_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, ThemeModel.getInstance().getTitle());
        return attachToSwipeBack(mRootView);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }


    public void init() {
        mPresenter.getReaderList();
    }


    @Override
    protected MarkDownReaderContract.Present getPresenter() {
        return new MarkDownReaderContract.Present();
    }


    @Override
    public void getReaderListSuccess(ArrayList<KnowledgeBean> knowledgeBeans) {
        try {
            this.knowledgeBeans = knowledgeBeans;
            mLv.setAdapter(mAdapter = new KnowledgeAdapter(mLv, getContext(), knowledgeBeans, 1));
            mAdapter.setOnTreeNodeClickListener((node, position) -> {
                if (node.isLeaf()) {
                    for (KnowledgeBean knowledgeBean : knowledgeBeans) {
                        if (knowledgeBean.id == node.id) {
                            start(MarkDownReaderDetailFragment.newInstance(knowledgeBean));
                            break;
                        }
                    }
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static class KnowledgeAdapter<T> extends TreeListViewAdapter {

        public KnowledgeAdapter(ListView tree, Context context, List datas, int defaultExpandLevel)
            throws IllegalAccessException {
            super(tree, context, datas, defaultExpandLevel);
        }


        @Override
        public View getCovertView(Node node, int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.mark_down_reader_item_tree, parent, false);
                holder = new ViewHolder();
                holder.mIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.mText = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (node.icon == -1) {
                holder.mIcon.setVisibility(View.INVISIBLE);
            } else {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(node.icon);
            }
            holder.mText.setText(node.name);
            return convertView;
        }


        private class ViewHolder {
            ImageView mIcon;
            TextView mText;
        }
    }

}

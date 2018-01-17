package com.huxley.wiisample.common.treeview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.huxley.wiisample.common.treeview.bean.Node;
import com.huxley.wiisample.common.treeview.utils.TreeHelper;

import java.util.List;

/**
 *
 * Created by huxley on 16/6/24.
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context                 mContext;
    protected List<Node>              mAllNodes;
    protected List <Node>             mVisibleNodes;
    protected LayoutInflater          mInflater;
    protected ListView                mTree;
    protected OnTreeNodeClickListener mListener;

    public TreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        mTree = tree;
        mTree.setOnItemClickListener((parent, view, position, id) -> {
            expandOrCollapse(position);
            if (mListener != null) {
                mListener.onClick((Node) getItem(position), position);
            }
        });
    }

    /**
     * 点击收缩或展开
     */
    private void expandOrCollapse(int position) {
        Node n = mVisibleNodes.get(position);
        if (n != null) {
            if (n.isLeaf()) {
                return;
            }
            n.setExpand(!n.isExpand);
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = (Node) getItem(position);
        convertView = getCovertView(node, position, convertView, parent);
        //设置内边距
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        return convertView;
    }

    /**
     * 设置node的点击回调
     * @param listener
     */
    public void setOnTreeNodeClickListener(OnTreeNodeClickListener listener) {
        mListener = listener;
    }

    public interface OnTreeNodeClickListener{
        void onClick(Node node, int position);
    }

    public void addExtraNode(int position, String content) {
        //点击的node
        Node node = mVisibleNodes.get(position);
        //node的实际position
        int indexOf = mAllNodes.indexOf(node);
        //创建要添加的node，并设置关联关系
        Node extraNode = new Node(-1, node.id, content);
        extraNode.parent = node;
        node.children.add(extraNode);
        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

    public abstract View getCovertView(Node node, int position, View convertView, ViewGroup parent);
}

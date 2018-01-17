package com.huxley.wiisample.common.treeview.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeiJin01 on 2016/6/23.
 */
public class Node {

    public int id;

    /**
     * 根节点的pid = 0
     */
    public int pid = 0;

    public String name;

    /**
     * 树的层级
     */
    public int level;

    /**
     * 是否是展开
     */
    public boolean isExpand;

    public int icon;

    public Node parent;

    public List<Node> children = new ArrayList<>();

    public Node() {
        this(-1, -1, null);
    }

    public Node(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    /**
     * 是否是根节点
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前父节点的收缩状态
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand;
    }

    /**
     * 是否是叶子节点
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 得到当前节点的层级
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}

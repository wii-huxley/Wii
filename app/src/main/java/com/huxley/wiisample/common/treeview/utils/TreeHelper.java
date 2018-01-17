package com.huxley.wiisample.common.treeview.utils;


import com.huxley.wiisample.R;
import com.huxley.wiisample.common.treeview.annotation.TreeNodeId;
import com.huxley.wiisample.common.treeview.annotation.TreeNodeLabel;
import com.huxley.wiisample.common.treeview.annotation.TreeNodePid;
import com.huxley.wiisample.common.treeview.bean.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeiJin01 on 2016/6/23.
 */
public class TreeHelper {

    /**
     * 将用户的数据转换成树形数据
     * @param datas
     * @param <T>
     * @return
     */
    public static<T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node;
        for (T t : datas) {
            node = new Node();
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    node.id = field.getInt(t);
                }else if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);
                    node.pid = field.getInt(t);
                }else if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    node.name = (String) field.get(t);
                }
            }
            nodes.add(node);
        }

        //设置节点Node间的关联关系
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.pid == n.id) { // m 是 n 的 child
                    n.children.add(m);
                    m.parent = n;
                } else if (m.id == n.pid) { // n 是 m 的 child
                    m.children.add(n);
                    n.parent = m;
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 为Node设置图标
     * @param n
     */
    public static void setNodeIcon(Node n) {
        if (n.children.size() > 0 && n.isExpand) {
            n.icon = R.drawable.tree_ex;
        } else if (n.children.size() > 0 && !n.isExpand) {
            n.icon = R.drawable.tree_ec;
        } else {
            n.icon = -1;
        }
    }

    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertDatas2Nodes(datas);
        //获取树的根节点
        List<Node> rooNodes = getRootNodes(nodes);
        for (Node node : rooNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 把一个节点的所有孩子节点放入result
     * @param result
     * @param node
     * @param defaultExpandLevel
     * @param currentLevel
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        for (int i = 0; i < node.children.size(); i++) {
            addNode(result, node.children.get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * 从所有节点中过滤根节点
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 过滤出可见的节点
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }
}

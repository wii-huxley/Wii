package com.huxley.wii.yl.basePage.mvp;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huxley
 * Data: 2016/8/4.
 * <p>
 * 相同类型的实例只维护一个，P的实例第一次创建后会一直复用
 */
public class Mvp<P extends MvpPresenter> {

    private       ConcurrentHashMap<String, P> sInstanceMap;
    public static Mvp                          instance;

    private Mvp() {
    }

    public static Mvp getInstance() {
        synchronized (Mvp.class) {
            if (instance == null) {
                synchronized (Mvp.class) {
                    instance = new Mvp();
                }
            }
        }
        return instance;
    }

    /**
     * 实例化Map
     */
    private void initMap() {
        if (sInstanceMap == null) {
            sInstanceMap = new ConcurrentHashMap<>();
        }
    }

    /**
     * 注册Presenter层实例
     */
    private void registerPresenter(Class<P> pClass) {
        initMap();
        if (!sInstanceMap.containsKey(pClass.getName())) {
            try {
                P pInstance = (P) Class.forName(pClass.getName()).newInstance();
                sInstanceMap.put(pClass.getName(), pInstance);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Presenter层实例, 为null的话， 走创建流程
     */
    public P getPresenter(Class<P> pClass) {
        initMap();
        if (!sInstanceMap.containsKey(pClass.getName())) registerPresenter(pClass);
        return sInstanceMap.get(pClass.getName());
    }

    /**
     * 移除实例
     */
    void unRegister(Class clazz) {
        if (sInstanceMap == null) {
            return;
        }
        sInstanceMap.remove(clazz.getName());
    }
}

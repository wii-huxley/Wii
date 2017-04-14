package com.huxley.wii.yl.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by huxley on 17/1/16.
 */
public class ActivityStack {

    private static ActivityStack   instance;
    private static Stack<Activity> sActivityStack;

    private ActivityStack() {
    }

    public static ActivityStack get() {
        if (instance == null) {
            synchronized (ActivityStack.class) {
                if (instance == null) {
                    instance = new ActivityStack();
                }
            }
        }
        return instance;
    }

    public Stack<Activity> getActivityStack() {
        if (sActivityStack == null) {
            synchronized (ActivityStack.class) {
                if (sActivityStack == null) {
                    sActivityStack = new Stack<>();
                }
            }
        }
        return sActivityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void add(Activity activity) {
        getActivityStack().add(activity);
    }

    /**
     * 获取当前Activity
     */
    public Activity getCurrent() {
        return getActivityStack().lastElement();
    }

    /**
     * 移除指定的Activity
     */
    public void remove(Activity activity) {
        if (activity != null) {
            getActivityStack().remove(activity);
        }
    }

    /**
     * 移除并关闭指定的Activity
     */
    public void finish(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.onBackPressed();
            getActivityStack().remove(activity);
        }
    }

    /**
     * 移除并关闭指定之外的Activity
     */
    public void finishOther(Class<?> cls) {
        for (int i = 0, size = getActivityStack().size(); i < size; i++) {
            Activity activity = getActivityStack().get(i);
            if (null != activity) {
                if (!activity.getClass().equals(cls)) {
                    activity.finish();
                }
            }
        }
        getActivityStack().clear();
    }

    /**
     * 结束所有Activity
     */
    public void finishAll() {
        for (int i = 0, size = getActivityStack().size(); i < size; i++) {
            if (null != getActivityStack().get(i)) {
                getActivityStack().get(i).finish();
            }
        }
        getActivityStack().clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() throws BaseException {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
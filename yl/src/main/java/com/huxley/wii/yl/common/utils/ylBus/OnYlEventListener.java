package com.huxley.wii.yl.common.utils.ylBus;

import com.huxley.wii.yl.common.utils.reflect.ClassTypeReflect;

/**
 * Created by huxley on 17/1/16.
 */
public abstract class OnYlEventListener<T> {

    public Class<T> c;

    public OnYlEventListener() {
        this.c = ClassTypeReflect.getGenericClass(this);
    }

    public T onTransform(T event){
        return event;
    }

    public abstract void onEvent(T event);
}

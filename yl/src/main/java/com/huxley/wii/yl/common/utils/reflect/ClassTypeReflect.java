package com.huxley.wii.yl.common.utils.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 通过反射获取Class和Type.
 * Created by huxley on 17/1/11.
 */
public class ClassTypeReflect {

    private static Type getGenericType(Object obj, int index) {
        Type type = obj.getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) return Object.class;
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        if (index >= params.length || index < 0) throw new RuntimeException("Index outof bounds");
        if (!(params[index] instanceof Class)) return Object.class;
        return params[index];
    }

    public static Type getGenericType(Object obj) {
        return getGenericType(obj, 0);
    }

    /**
     * 获取父类泛型的类型
     */
    public static Class getGenericClass(Object obj, int index) {
        return (Class) getGenericType(obj, index);
    }

    public static Class getGenericClass(Object obj) {
        return getGenericClass(obj, 0);
    }
}

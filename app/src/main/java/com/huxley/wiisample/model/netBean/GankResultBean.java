package com.huxley.wiisample.model.netBean;

import java.io.Serializable;

/**
 * Created by huxley on 2017/8/23.
 */

public class GankResultBean<T> implements Serializable {

    public boolean error;
    public T       results;
}

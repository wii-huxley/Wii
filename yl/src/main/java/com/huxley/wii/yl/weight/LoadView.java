package com.huxley.wii.yl.weight;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 加载的View
 * Created by huxley on 17/1/17.
 */
public class LoadView extends FrameLayout {

    public LoadView(@NonNull Context context) {
        this(context, null);
    }

    public LoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

package com.huxley.wii.yl.common.utils.doubleClick;

import android.view.View;

/**
 * Created by huxley on 17/1/14.
 */

public abstract class OnYlClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (DoubleClickHandler.isFastDoubleClick()) {
            return;
        }
        onYlClick(view);
    }

    public abstract void onYlClick(View view);
}

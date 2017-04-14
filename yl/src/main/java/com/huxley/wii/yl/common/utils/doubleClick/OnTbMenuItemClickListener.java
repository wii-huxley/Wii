package com.huxley.wii.yl.common.utils.doubleClick;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by huxley on 2017/2/17.
 */

public abstract class OnTbMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (DoubleClickHandler.isFastDoubleClick()) {
            return true;
        }
        return onTbMenuItemClick(item);
    }

    public abstract boolean onTbMenuItemClick(MenuItem item);
}

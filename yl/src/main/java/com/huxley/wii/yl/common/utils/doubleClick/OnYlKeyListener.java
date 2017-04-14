package com.huxley.wii.yl.common.utils.doubleClick;

import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * Created by huxley on 17/1/16.
 */

public abstract class OnYlKeyListener implements DialogInterface.OnKeyListener{

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        return DoubleClickHandler.isFastDoubleClick() || onYlKey(dialogInterface, keyCode, keyEvent);
    }

    public abstract boolean onYlKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent);
}

package com.huxley.wii.yl.page;

/**
 * Created by huxley on 17/1/16.
 */
public interface IProgressDialogView {

    void show(String message, boolean shieldKey);

    void dismiss();
}

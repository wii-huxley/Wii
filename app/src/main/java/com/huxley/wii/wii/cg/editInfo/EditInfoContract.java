package com.huxley.wii.wii.cg.editInfo;

import com.huxley.wii.yl.basePage.IProgressDialogView;
import com.huxley.wii.yl.basePage.mvp.MvpPresenter;
import com.huxley.wii.yl.basePage.mvp.MvpView;

/**
 * 编辑歌曲信息contract
 * Created by huxley on 17/1/30.
 */
public class EditInfoContract {

    public interface View extends MvpView, IProgressDialogView {
    }

    public static class Present extends MvpPresenter<View> {
    }
}

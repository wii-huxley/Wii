package com.huxley.wiisample.page.picHandle;

import com.huxley.wiisample.common.CallBack;
import com.huxley.wiisample.model.SongModel;
import com.huxley.wiisample.model.bean.Song;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;
import java.util.List;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class PicHandleContract {

    public interface View extends IMvpView {
    }

    public static class Present extends IMvpPresenter<View> {

    }
}

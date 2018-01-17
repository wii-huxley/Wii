package com.huxley.wiisample.page.markdownReader.detail;

import com.huxley.wiisample.model.MarkDownModel;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;
import com.thefinestartist.utils.log.L;

import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class MarkDownReaderDetailContract {

    public interface View extends IMvpView {

        void loadDetailSuccess(String content);
    }

    public static class Present extends IMvpPresenter<View> {

        public void loadDetail(String path) {
            MarkDownModel.getInstance().getKnowledgeContent(path).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }
                @Override
                public void onError(Throwable e) {

                }
                @Override
                public void onNext(String content) {
                    getView().loadDetailSuccess(content);
                }
            });
        }
    }
}

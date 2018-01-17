package com.huxley.wiisample.page.markdownReader;

import com.huxley.wiisample.model.MarkDownModel;
import com.huxley.wiisample.model.localBean.KnowledgeBean;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class MarkDownReaderContract {

    public interface View extends IMvpView {
        void getReaderListSuccess(ArrayList<KnowledgeBean> knowledgeBeans);
    }

    public static class Present extends IMvpPresenter<View> {

        public void getReaderList() {
            MarkDownModel.getInstance().getKnowledges().subscribe(new Subscriber<ArrayList<KnowledgeBean>>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable e) {
                }
                @Override
                public void onNext(ArrayList<KnowledgeBean> knowledgeBeans) {
                    getView().getReaderListSuccess(knowledgeBeans);
                }
            });
        }
    }
}

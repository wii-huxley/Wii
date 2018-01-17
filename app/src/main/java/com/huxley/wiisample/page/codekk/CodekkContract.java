package com.huxley.wiisample.page.codekk;

import com.huxley.wiisample.model.CodekkModel;
import com.huxley.wiisample.model.netBean.CodekkHomeListBean;
import com.huxley.wiisample.model.netBean.CodekkProjectBean;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.IMvpPresenter;
import com.huxley.yl.page.mvp.IMvpView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.drakeet.multitype.Items;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 主页contract
 * Created by huxley on 17/1/30.
 */
public class CodekkContract {

    public interface View extends IMvpView {
        void setRefreshing(boolean refreshing);

        void getProjectListSuccess(Items items);
    }

    public static class Present extends IMvpPresenter<View> {

        int mCount = 1;
        List<CodekkProjectBean> projectArray;

        public void getProjectList(boolean isLoadMore) {
            if (!isLoadMore) {
                projectArray = new ArrayList<>();
                mCount = 1;
            }
            if (mCount <= 0) {
                return;
            }
            getView().setRefreshing(true);
            CodekkModel.getInstance().getProjectList(mCount)
                    .compose(formatProjectList())
                    .subscribe(new Subscriber<Items>() {
                        @Override
                        public void onCompleted() {
                            getView().setRefreshing(false);
                            mCount++;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            getView().setRefreshing(false);
                            throwable.printStackTrace();
                            WiiToast.error("加载失败");
                        }

                        @Override
                        public void onNext(Items items) {
                            getView().getProjectListSuccess(items);
                        }
                    });
        }

        public void startSearch(boolean isLoadMore, String query) {
            if (!isLoadMore) {
                projectArray = new ArrayList<>();
                mCount = 1;
            }
            if (mCount <= 0) {
                return;
            }
            getView().setRefreshing(true);
            CodekkModel.getInstance().searchProjectList(query, mCount)
                    .compose(formatProjectList())
                    .subscribe(new Subscriber<Items>() {
                        @Override
                        public void onCompleted() {
                            getView().setRefreshing(false);
                            mCount++;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            getView().setRefreshing(false);
                            throwable.printStackTrace();
                            WiiToast.error("加载失败");
                        }

                        @Override
                        public void onNext(Items items) {
                            getView().getProjectListSuccess(items);
                        }
                    });
        }

        Observable.Transformer<CodekkHomeListBean, Items> formatProjectList() {
            return tObservable -> tObservable.flatMap(
                    new Func1<CodekkHomeListBean, Observable<Items>>() {
                        @Override
                        public Observable<Items> call(CodekkHomeListBean result) {
                            if (!result.isEmpty()) {
                                projectArray.addAll(result.projectArray);
                            }
                            Items items = new Items();
                            HashMap<String, Boolean> dateMap = new HashMap<>();
                            for (CodekkProjectBean project : projectArray) {
                                String createTime = project.createTime.substring(0, 10);
                                if (!dateMap.containsKey(createTime)) {
                                    dateMap.put(createTime, true);
                                    items.add(createTime);
                                }
                                items.add(project);
                            }
                            return Observable.just(items);
                        }
                    }
            );
        }

        public void openSearch() {
            getView().getProjectListSuccess(new Items());
        }

        public void closeSearch() {
            getProjectList(false);
        }
    }
}

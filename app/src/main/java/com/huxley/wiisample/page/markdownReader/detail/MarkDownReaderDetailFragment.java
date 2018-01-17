package com.huxley.wiisample.page.markdownReader.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.model.localBean.KnowledgeBean;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class MarkDownReaderDetailFragment extends MvpSwipeBackFragment<MarkDownReaderDetailContract.Present> implements MarkDownReaderDetailContract.View {

    KnowledgeBean mKnowledgeBean;

    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    public static MarkDownReaderDetailFragment newInstance(KnowledgeBean knowledgeBean) {
        Bundle args = new Bundle();
        args.putSerializable("knowledge", knowledgeBean);
        MarkDownReaderDetailFragment fragment = new MarkDownReaderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.mark_down_reader_detail_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        mKnowledgeBean = (KnowledgeBean) getArguments().getSerializable("knowledge");
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, mKnowledgeBean.name);
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }

    public void init() {
        mPresenter.loadDetail(mKnowledgeBean.path);
    }

    @Override
    protected MarkDownReaderDetailContract.Present getPresenter() {
        return new MarkDownReaderDetailContract.Present();
    }

    @Override
    public void loadDetailSuccess(String content) {

        RichText.from(content) // 数据源
                .type(RichText.TYPE_MARKDOWN) // 数据格式,不设置默认是Html,使用fromMarkdown的默认是Markdown格式
//                .autoFix(true) // 是否自动修复，默认true
                .async(true) // 是否异步，默认false
//                .fix(imageFixCallback) // 设置自定义修复图片宽高
//                .noImage(true) // 不显示并且不加载图片
//                .imageClick(onImageClickListener) // 设置图片点击回调
//                .imageLongClick(onImageLongClickListener) // 设置图片长按回调
//                .urlClick(onURLClickListener) // 设置链接点击回调
//                .urlLongClick(onUrlLongClickListener) // 设置链接长按回调
//                .placeHolder(placeHolder) // 设置加载中显示的占位图
//                .error(errorImage) // 设置加载失败的错误图
                .into(mTvContent); // 设置目标TextView
    }
}

package com.huxley.wiisample.page.gank.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxley.page_web.FinestWebView;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.common.stickygridheaders.StickyGridHeadersGridView;
import com.huxley.wiisample.common.stickygridheaders.StickyGridHeadersSimpleAdapter;
import com.huxley.wiisample.model.netBean.GankBean;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.yl.common.utils.doubleClick.OnYlClickListener;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class GankDetailFragment extends MvpSwipeBackFragment<GankDetailContract.Present> implements GankDetailContract.View {


    List<GankBean> mGankList;
    LayoutInflater mInflater;
    Adapter        mAdapter;
    String         mDay;

    @BindView(R.id.toolbar)
    Toolbar                   mToolbar;
    @BindView(R.id.ll_content)
    LinearLayout              mLlContent;
    @BindView(R.id.sgv)
    StickyGridHeadersGridView mSgv;

    public static GankDetailFragment newInstance(String day) {
        Bundle args = new Bundle();
        GankDetailFragment fragment = new GankDetailFragment();
        args.putString("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.gank_detail_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        mDay = getArguments().getString("day");
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, mDay);
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRootView.getRootView().postDelayed(this::init, 300);
    }

    public void init() {
        mInflater = LayoutInflater.from(getContext());
        mGankList = new ArrayList<>();
        mSgv.setAdapter(mAdapter = new Adapter());
        mPresenter.getDay(mDay);
    }

    @Override
    public void getDaySuccess(ArrayList<GankBean> gankDetailBean) {
        mGankList.addAll(gankDetailBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected GankDetailContract.Present getPresenter() {
        return new GankDetailContract.Present();
    }

    class Adapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {
        @Override
        public int getCount() {
            return mGankList.size();
        }

        @Override
        public GankBean getItem(int position) {
            return mGankList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolderBody holderBody;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.gank_detail_item_body, parent, false);
                holderBody = new ViewHolderBody(convertView);
                convertView.setTag(holderBody);
            } else {
                holderBody = (ViewHolderBody) convertView.getTag();
            }
            holderBody.tvName.setText(getItem(position).desc);
            holderBody.tvName.setOnClickListener(new OnYlClickListener() {
                @Override
                public void onYlClick(View view) {
                    new FinestWebView.Builder(getActivity())
                            .urlColor(ResUtils.getColor(R.color.white))
                            .setCustomAnimations(R.anim.h_fragment_enter, 0, 0, R.anim.h_fragment_exit)
                            .titleColor(ResUtils.getColor(R.color.white))
                            .iconDefaultColor(ResUtils.getColor(R.color.white))
                            .titleDefault(getItem(position).desc)
                            .show(getItem(position).url);
//                    GankWebContentFragment gankWebContentFragment = GankWebContentFragment.newInstance(getItem(position).desc, getItem(position).url);
//                    start(gankWebContentFragment);
                }
            });
            return convertView;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            ViewHolderHeader holderHeader;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.gank_detail_item_header, parent, false);
                holderHeader = new ViewHolderHeader(convertView);
                convertView.setTag(holderHeader);
            } else {
                holderHeader = (ViewHolderHeader) convertView.getTag();
            }
            holderHeader.tvTitle.setText(getItem(position).type);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).type.hashCode();
        }

        class ViewHolderBody {
            @BindView(R.id.tv_name)
            TextView tvName;

            ViewHolderBody(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolderHeader {
            @BindView(R.id.tv_title)
            TextView tvTitle;

            ViewHolderHeader(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}

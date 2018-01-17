package com.huxley.wiisample.page.picHandle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.huxley.wiisample.R;
import com.huxley.wiisample.common.UIHelper;
import com.huxley.wiisample.page.picHandle.transformation.CartoonTransformation;
import com.huxley.wiisample.page.picHandle.transformation.SandTransformation;
import com.huxley.wiitools.commAdapter.rvadapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.MultiItemTypeAdapter;
import com.huxley.wiitools.commAdapter.rvadapter.base.ViewHolder;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class PicHandleFragment extends MvpSwipeBackFragment<PicHandleContract.Present>
    implements PicHandleContract.View {

    @BindView(R.id.toolbar)       Toolbar      mToolbar;
    @BindView(R.id.pv_pic)        PhotoView    mPvPic;
    @BindView(R.id.ll_sheet_root) LinearLayout mLlSheetRoot;
    @BindView(R.id.rv_type)       RecyclerView mRvType;
    @BindView(R.id.tv_type_title) TextView     mTvTypeTitle;

    BottomSheetBehavior behavior;
    List<MediaBean>     handleFiles;
    int                 selectPosition;


    public static PicHandleFragment newInstance() {
        Bundle            args     = new Bundle();
        PicHandleFragment fragment = new PicHandleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pic_handle_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return attachToSwipeBack(mRootView);
    }


    CommonAdapter adapter;


    protected void init(Bundle savedInstanceState) {
        UIHelper.Toolbars.setTitleAndBack(mToolbar, this, "图片处理");
        handleFiles = new ArrayList<>();
        List<String> types = new ArrayList<>();
        types.add("原图");
        types.add("黑白");
        types.add("沙");
        mRvType.setLayoutManager(
            new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false));
        mRvType.setAdapter(
            adapter = new CommonAdapter<String>(_mActivity, R.layout.pic_handle_item_type, types) {
                @Override
                protected void convert(ViewHolder holder, String type, final int position) {
                    ((CardView) holder.getView(R.id.cv)).setCardBackgroundColor(ResUtils.getColor(
                        position == selectPosition ? R.color.color_1abc9c : R.color.white));
                    holder.setTextColor(R.id.tv_type, ResUtils.getColor(
                        position == selectPosition ? R.color.white : R.color.color_1abc9c));
                    holder.setText(R.id.tv_type, type);
                }
            });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (handleFiles == null || handleFiles.isEmpty()) {
                    WiiToast.warn("未找到需要处理的图片");
                    return;
                }
                selectPosition = position;
                adapter.notifyDataSetChanged();
                BitmapTransformation bitmapTransformation = null;
                switch (position) {
                    case 1:
                        bitmapTransformation = new CartoonTransformation(_mActivity);
                        break;
                    case 2:
                        bitmapTransformation = new SandTransformation(_mActivity);
                        break;
                }
                showProgress("转换中...", false);
                DrawableRequestBuilder<String> builder = Glide.with(_mActivity)
                    .load(handleFiles.get(0).getOriginalPath())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            hideProgress();
                            return false;
                        }


                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            hideProgress();
                            return false;
                        }
                    });
                if (bitmapTransformation != null) {
                    builder = builder.bitmapTransform(bitmapTransformation);
                }
                builder.into(mPvPic);
            }


            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        behavior = BottomSheetBehavior.from(mLlSheetRoot);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mTvTypeTitle.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            ResUtils.getDrawable(R.mipmap.ic_close_type), null);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        mTvTypeTitle.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            ResUtils.getDrawable(R.mipmap.ic_open_type), null);
                        break;
                }
            }


            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pic_handle_menu_open_camera, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.item_open_camera).setOnMenuItemClickListener(
            item -> {
                openCamera();
                return true;
            });
    }


    private void openCamera() {
        RxGalleryFinal.with(_mActivity)
            .image()
            .imageLoader(ImageLoaderType.GLIDE)
            .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                @Override protected void onEvent(ImageMultipleResultEvent baseResultEvent)
                    throws Exception {
                    handleFiles.addAll(baseResultEvent.getResult());
                    Glide.with(_mActivity)
                        .load(handleFiles.get(0).getOriginalPath())
                        .into(mPvPic);
                    WiiToast.show("单选图片的回调");
                }
            })
            .openGallery();
    }


    @Override
    protected PicHandleContract.Present getPresenter() {
        return new PicHandleContract.Present();
    }


    @OnClick(R.id.fl) public void onViewClicked() {
        behavior.setState(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED
                          ? BottomSheetBehavior.STATE_COLLAPSED
                          : BottomSheetBehavior.STATE_EXPANDED);
    }
}

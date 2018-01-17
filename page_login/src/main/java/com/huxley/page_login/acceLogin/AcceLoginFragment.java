package com.huxley.page_login.acceLogin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huxley.page_login.R;
import com.huxley.page_login.R2;
import com.huxley.page_login.acceRegister.AcceRegisterFragment;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.SoftInputUtil;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.wiitools.view.dialog.CommonDialogFragment;
import com.huxley.wiitools.view.dialog.DialogFragmentHelper;
import com.huxley.yl.page.mvp.MvpModelFragment;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by huxley on 2017/4/8.
 */
public class AcceLoginFragment extends MvpModelFragment<AcceLoginContract.Present> implements AcceLoginContract.View {

    Class<?>     mHomeClazz;
    Serializable mCategories;

    @BindView(R2.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R2.id.iv_logo)
    ImageView    mIvLogo;
    @BindView(R2.id.iv_user_id)
    ImageView    mIvUserId;
    @BindView(R2.id.et_user_id)
    EditText     mEtUserId;
    @BindView(R2.id.iv_pwd)
    ImageView    mIvPwd;
    @BindView(R2.id.et_pwd)
    EditText     mEtPwd;

    private boolean isFirst     = true;
    private int     heightDiff  = 100;
    private float   mLlContentY = 100;
    private float   mIvLogoY    = 100;
    private boolean isHide;

    public static AcceLoginFragment newInstance(Serializable categories, Class<?> clazz) {
        Bundle args = new Bundle();
        AcceLoginFragment fragment = new AcceLoginFragment();
        args.putSerializable("clazz", clazz);
        args.putSerializable("categories", categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.acce_login_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return mRootView;
    }

    protected void init() {
        mHomeClazz = (Class<?>) getArguments().getSerializable("clazz");
        mCategories = getArguments().getSerializable("categories");
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isFirst) {
                    heightDiff = mRootView.getRootView().getHeight() - mRootView.getHeight() + 100;
                    mLlContentY = mLlContent.getY();
                    mIvLogoY = mIvLogo.getY();
                    isFirst = false;
                } else {
                    if (isKeyboardShown(mRootView) && !isHide) {
                        ViewCompat.animate(mIvLogo)
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(300)
                                .start();
                        ViewCompat.animate(mLlContent)
                                .translationY(mIvLogoY - mLlContentY)
                                .setDuration(300)
                                .start();
                        isHide = true;
                    } else if (!isKeyboardShown(mRootView) && isHide) {
                        ViewCompat.animate(mIvLogo)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(300)
                                .start();
                        ViewCompat.animate(mLlContent)
                                .translationY(0)
                                .setDuration(300)
                                .start();
                        isHide = false;
                    }
                }
            }
        });
    }

    private boolean isKeyboardShown(View rootView) {
        return rootView.getRootView().getHeight() - rootView.getHeight() > heightDiff;
    }

    @OnClick({R2.id.tv_register, R2.id.btn_login, R2.id.btn_login_wechat, R2.id.btn_login_sina})
    public void doClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            SoftInputUtil.hideSoftInput(getActivity());
            start(AcceRegisterFragment.newInstance(heightDiff, mLlContentY, mIvLogoY));
        } else if (id == R.id.btn_login) {
            login();
        } else if (id == R.id.btn_login_wechat) {
            loginWechat();
        } else if (id == R.id.btn_login_sina) {
            loginSina();
        }
    }

    @OnFocusChange({R2.id.et_user_id, R2.id.et_pwd})
    public void onFocusChange(View view, boolean b) {
        int i = view.getId();
        if (i == R.id.et_user_id) {
            mEtUserId.setHintTextColor(ResUtils.getColor(b ? R.color.light_sea_green_5 : R.color.iron));
            Drawable userIdDrawable = mIvUserId.getDrawable();
            userIdDrawable.setTint(ResUtils.getColor(b ? R.color.light_sea_green : R.color.iron));
            mIvUserId.setImageDrawable(userIdDrawable);
        } else if (i == R.id.et_pwd) {
            mEtPwd.setHintTextColor(ResUtils.getColor(b ? R.color.light_sea_green_5 : R.color.iron));
            Drawable pwdDrawable = mIvPwd.getDrawable();
            pwdDrawable.setTint(ResUtils.getColor(b ? R.color.light_sea_green : R.color.iron));
            mIvPwd.setImageDrawable(pwdDrawable);
        }
    }

    private void loginSina() {
        mPresenter.loginSina();
    }

    private void loginWechat() {
        mPresenter.LoginWechat();
    }

    private void login() {
        String userId = mEtUserId.getText().toString();
        String pwd = mEtPwd.getText().toString();
        if (userId.isEmpty()) {
            WiiToast.warn("请输入用户名");
            return;
        }
        if (pwd.isEmpty()) {
            WiiToast.warn("请输入密码");
            return;
        }
        mPresenter.login(userId, pwd);
    }

    @Override
    public void loginSuccess() {
        if (mHomeClazz == null) {
            loginBack();
        } else {
            Intent intent = new Intent(_mActivity, mHomeClazz);
            intent.putExtra("categories", mCategories);
            startActivity(intent);
            _mActivity.finish();
        }
    }

    private void loginBack() {
        Intent intent = new Intent();
        intent.putExtra("userName", "15527592662");
        _mActivity.setResult(RESULT_OK, intent);
        _mActivity.finish();
    }

    CommonDialogFragment commonDialogFragment;

    @Override
    public void showProgress(String msg, boolean isCancel) {
        if (commonDialogFragment == null) {
            commonDialogFragment = DialogFragmentHelper.showProgress(getChildFragmentManager(), msg, isCancel);
        } else {
            commonDialogFragment.show(getChildFragmentManager(), msg);
        }
    }

    @Override
    public void dismissProgress() {
        if (commonDialogFragment != null) {
            commonDialogFragment.dismiss();
        }
    }

    @Override
    protected AcceLoginContract.Present getPresenter() {
        return new AcceLoginContract.Present();
    }
}

package com.huxley.page_login.acceRegister;

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
import android.widget.TextView;

import com.huxley.page_login.R;
import com.huxley.page_login.R2;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.view.WiiToast;
import com.huxley.wiitools.view.dialog.CommonDialogFragment;
import com.huxley.wiitools.view.dialog.DialogFragmentHelper;
import com.huxley.yl.page.mvp.MvpSwipeBackFragment;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by huxley on 2017/4/8.
 */
public class AcceRegisterFragment extends MvpSwipeBackFragment<AcceRegisterContract.Present> implements AcceRegisterContract.View {

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
    @BindView(R2.id.iv_vcode)
    ImageView    mIvVcode;
    @BindView(R2.id.et_vcode)
    EditText     mEtVcode;
    @BindView(R2.id.tv_send_vcode)
    TextView     mTvSendVcode;

    private int   heightDiff  = 100;
    private float mLlContentY = 100;
    private float mIvLogoY    = 100;
    private boolean isHide;

    public static AcceRegisterFragment newInstance(int heightDiff, float llContentY, float ivLogoY) {
        Bundle args = new Bundle();
        AcceRegisterFragment fragment = new AcceRegisterFragment();
        args.putInt("heightDiff", heightDiff);
        args.putFloat("llContentY", llContentY);
        args.putFloat("ivLogoY", ivLogoY);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.acce_register_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return attachToSwipeBack(mRootView);
    }

    protected void init() {
        Bundle arguments = getArguments();
        heightDiff = arguments.getInt("heightDiff");
        mLlContentY = arguments.getFloat("llContentY");
        mIvLogoY = arguments.getFloat("ivLogoY");
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
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
        });
    }

    @OnClick({R2.id.iv_back, R2.id.btn_register, R2.id.tv_send_vcode})
    public void doClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            onBackPressedSupport();
        } else if (id == R.id.btn_register) {
            register();
        } else if (id == R.id.tv_send_vcode) {
            sendVcode();
        }
    }

    private void sendVcode() {
        String userId = mEtUserId.getText().toString();
        if (userId.isEmpty()) {
            WiiToast.warn("请输入手机或邮箱号");
            return;
        }
        mPresenter.sendVcode(userId);
    }

    private void register() {
        String userId = mEtUserId.getText().toString();
        String pwd = mEtPwd.getText().toString();
        String vcode = mEtVcode.getText().toString();
        if (userId.isEmpty()) {
            WiiToast.warn("请输入手机或邮箱号");
            return;
        }
        if (vcode.isEmpty()) {
            WiiToast.warn("请输入验证码");
            return;
        }
        if (pwd.isEmpty()) {
            WiiToast.warn("请设置密码");
            return;
        }
        mPresenter.register(userId, vcode, pwd);
    }

    @OnFocusChange({R2.id.et_user_id, R2.id.et_pwd, R2.id.et_vcode})
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
        } else if (i == R.id.et_vcode) {
            mEtVcode.setHintTextColor(ResUtils.getColor(b ? R.color.light_sea_green_5 : R.color.iron));
            Drawable pwdDrawable = mIvVcode.getDrawable();
            pwdDrawable.setTint(ResUtils.getColor(b ? R.color.light_sea_green : R.color.iron));
            mIvVcode.setImageDrawable(pwdDrawable);
        }
    }

    private boolean isKeyboardShown(View rootView) {
        return rootView.getRootView().getHeight() - rootView.getHeight() > heightDiff;
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    public void registerSuccess() {
        onBackPressedSupport();
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

    int time = 60;
    Runnable mRunnable;

    @Override
    public void sendVcodeSuccess() {
        time = 60;
        mTvSendVcode.setEnabled(false);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mTvSendVcode == null) {
                    return;
                }
                if (time != 0) {
                    mTvSendVcode.setText(MessageFormat.format("{0}S 后重新获取", time));
                    if (mRootView.getHandler() != null) {
                        mRootView.getHandler().postDelayed(mRunnable, 1000);
                    }
                    time--;
                } else {
                    mTvSendVcode.setText("发送验证码");
                    mTvSendVcode.setEnabled(true);
                }
            }
        };
        mRootView.getHandler().post(mRunnable);
    }

    @Override
    protected AcceRegisterContract.Present getPresenter() {
        return new AcceRegisterContract.Present();
    }
}

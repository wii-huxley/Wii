package com.huxley.wii.wii.cg.login;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.UIHelper;
import com.huxley.wii.yl.basePage.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends MvpActivity<LoginContract.Present> implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);

        ButterKnife.bind(this);
        UIHelper.Toolbars.setTitle(mToolbar, this, "登录");
    }
}

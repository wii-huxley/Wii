package com.huxley.page_login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.view.Window;

import com.huxley.fragmentation.anim.DefaultNoAnimator;
import com.huxley.fragmentation.anim.FragmentAnimator;
import com.huxley.page_login.acceLogin.AcceLoginFragment;
import com.huxley.yl.page.mvp.MvpActivity;

import java.io.Serializable;


/**
 * Created by huxley on 2017/4/10.
 */
public class LoginActivity extends MvpActivity<LoginContract.Present> implements LoginContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置使用TransitionManager进行动画，不设置的话系统会使用一个默认的TransitionManager
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //退出变换(ExitTransition)决定了A调用B的时候，A中的View是如何播放动画的
        getWindow().setExitTransition(new Fade());
        //进入变换(EnterTransition)决定了A调用B的时候，B中的View是如何播放动画的
        getWindow().setEnterTransition(new Fade());
        //返回变换(ReturnTransition)决定了在B返回A的时候，B中的View是如何播放动画的
        getWindow().setReturnTransition(new Fade());
        //再次进入变换(ReenterTransition)决定了在B返回A的时候，A中的View是如何播放动画的
        getWindow().setReenterTransition(new Fade());

        setContentView(R.layout.login_activity);

        Class<?> clazz = (Class<?>) getIntent().getSerializableExtra("clazz");
        Serializable categories = getIntent().getSerializableExtra("categories");
        if (savedInstanceState == null) {
            AcceLoginFragment loginFragment = AcceLoginFragment.newInstance(categories, clazz);
            loadRootFragment(R.id.login_container, loginFragment);
        }
    }

    @Override
    protected LoginContract.Present getPresenter() {
        return new LoginContract.Present();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }
}

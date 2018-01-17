package com.huxley.page_guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huxley.page_guide.bean.GuideBean;
import com.huxley.page_guide.simple.GuideSimpleFragment;
import com.huxley.yl.page.base.BaseActivity;

/**
 * Created by huxley on 2017/9/15.
 */

public class GuideActivity extends BaseActivity{

    GuideBean mGuideData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide_activity);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("guideData")) {
            mGuideData = (GuideBean) intent.getSerializableExtra("guideData");
        }
        if (savedInstanceState == null) {
            GuideSimpleFragment guideSimpleFragment = GuideSimpleFragment.newInstance(mGuideData);
            loadRootFragment(R.id.guide_container, guideSimpleFragment);
        }
    }
}

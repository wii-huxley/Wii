package com.huxley.wiisample.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.acce.page_main.HomeActivity;
import com.acce.page_main.bean.Category;
import com.acce.page_main.bean.Theme;
import com.huxley.page_login.LoginActivity;
import com.huxley.wiisample.R;
import com.huxley.wiisample.page.codekk.CodekkFragment;
import com.huxley.wiisample.page.dytt.DyttFragment;
import com.huxley.wiisample.page.gank.GankFragment;
import com.huxley.wiisample.page.github.GithubFragment;
import com.huxley.wiisample.page.harmonicaNotation.HarmonicaNotationFragment;
import com.huxley.wiisample.page.markdownReader.MarkDownReaderFragment;

import com.huxley.wiisample.page.picHandle.PicHandleFragment;
import com.huxley.wiisample.page.timeRhythm.TimeRhythmFragment;
import java.util.ArrayList;


/**
 * Created by huxley on 2017/4/10.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        getWindow().getDecorView().postDelayed(this::init, 1000);
    }

    private void init() {
        ArrayList<Category> categories = new ArrayList<>();
        // categories.add(new Category<>(HarmonicaNotationFragment.class, "口琴曲谱大全", Theme.deep_orange, 0, true));
        categories.add(new Category<>(GankFragment.class, "干货集中营", Theme.cyan, 0, true));
        categories.add(new Category<>(CodekkFragment.class, "Android\n开源项目集合", Theme.blue, 0, true));
        categories.add(new Category<>(DyttFragment.class, "电影天堂", Theme.red, 0, true));
        // categories.add(new Category<>(MarkDownReaderFragment.class, "MarkDown 阅读器", Theme.grey, 0, true));
        // categories.add(new Category<>(GithubFragment.class, "GitHub", Theme.teal, 0, true));
        // categories.add(new Category<>(PicHandleFragment.class, "图片处理", Theme.light_blue, 0, true));
        // categories.add(new Category<>(TimeRhythmFragment.class, "时间节奏", Theme.orange, 0, true));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("clazz", LoginActivity.class);
        intent.putExtra("categories", categories);
        startActivity(intent);
        finish();
    }
}

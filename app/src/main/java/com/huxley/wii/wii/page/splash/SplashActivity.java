package com.huxley.wii.wii.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.acce.page_main.HomeActivity;
import com.acce.page_main.model.bean.Category;
import com.acce.page_main.model.bean.Theme;
import com.huxley.wii.wii.R;
import com.huxley.wii.wii.page.harmonicaNotation.HarmonicaNotationFragment;
import com.huxley.wii.wii.page.smsBomb.SMSBombFragment;

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
        categories.add(new Category<>(HarmonicaNotationFragment.class, "口琴曲谱大全", Theme.amber, 0, true));
        categories.add(new Category<>(SMSBombFragment.class, "短信轰炸", Theme.amber, 0, true));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("categories", categories);
        startActivity(intent);
        finish();
    }
}

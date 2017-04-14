package com.huxley.wii.wii.page.smsBomb;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.widget.EditText;

import com.huxley.wii.wii.R;
import com.huxley.wii.wii.common.SP;
import com.huxley.wii.wii.model.bean.SMSBean;
import com.huxley.wii.yl.common.YlUIHelper;
import com.huxley.wii.yl.common.utils.StringUtils;
import com.huxley.wii.yl.common.utils.ToastHelper;
import com.huxley.wii.yl.page.mvp.MvpSwipeBackFragment;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 * Created by huxley on 17/1/30.
 */
public class SMSBombFragment extends MvpSwipeBackFragment<SMSBombContract.Present> implements SMSBombContract.View {

    public static final String REGEX_MOBILE_EXACT = "^((13[5-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}$";
    private             int    nowTimes           = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SMSBean smsBean = (SMSBean) msg.obj;
            smsBomb(smsBean.phoneNum, smsBean.content);
            show(MessageFormat.format("轰炸中，次数：{0}/{1}", nowTimes, smsBean.times), true);
            if (nowTimes >= smsBean.times) {
                dismiss();
                return;
            }
            nowTimes++;
            mHandler.sendMessageDelayed(mHandler.obtainMessage(0, smsBean), smsBean.interval * 1000);
        }
    };
    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.tv_phone_num)
    EditText mTvPhoneNum;
    @BindView(R.id.tv_times)
    EditText mTvTimes;
    @BindView(R.id.tv_content)
    EditText mTvContent;
    @BindView(R.id.tv_interval)
    EditText mTvInterval;

    public static SMSBombFragment newInstance() {

        Bundle args = new Bundle();

        SMSBombFragment fragment = new SMSBombFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.sms_bomb_fragment;
    }

    @Override
    public void init() {
        super.init();

        YlUIHelper.BaseToolbars.setTitle(mToolbar, _mActivity, "短信轰炸");
    }

    @OnClick(R.id.btn_start)
    public void onClick() {
        String phoneNum = mTvPhoneNum.getText().toString();
        String timesStr = mTvTimes.getText().toString();
        String content = mTvContent.getText().toString();
        String intervalStr = mTvInterval.getText().toString();
        if (StringUtils.isEmpty(phoneNum)) {
            exit(5);
            return;
        }
        if (StringUtils.isEmpty(timesStr)) {
            exit(5);
            return;
        }
        if (StringUtils.isEmpty(content)) {
            exit(5);
            return;
        }
        if (StringUtils.isEmpty(intervalStr)) {
            exit(5);
            return;
        }
        if (!phoneNum.matches(REGEX_MOBILE_EXACT)) {
            exit(6);
            return;
        }
        SMSBean smsBean = new SMSBean();
        smsBean.phoneNum = phoneNum;
        smsBean.content = content;
        smsBean.times = Integer.valueOf(timesStr);
        smsBean.interval = Long.valueOf(intervalStr);
        Message message = new Message();
        message.obj = smsBean;
        if (ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(_mActivity, new String[]{Manifest.permission.SEND_SMS}, 1001);
        } else {
            mHandler.sendMessage(message);
        }
    }

    private void smsBomb(String phoneNum, String content) {
        SmsManager smsManager = SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(content);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNum, null, text, null, null);
        }
    }

    private void exit(int type) {
        SP.SMSBomb.save("type", type);
        ToastHelper.showInfo("输入信息错误");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001:

                break;
        }
    }
}

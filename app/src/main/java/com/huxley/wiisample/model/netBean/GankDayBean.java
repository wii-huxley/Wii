package com.huxley.wiisample.model.netBean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huxley on 2017/8/23.
 */

public class GankDayBean implements Serializable {

    public ArrayList<GankBean> Android;
    public ArrayList<GankBean> App;
    public ArrayList<GankBean> iOS;
    @SerializedName("休息视频")
    public ArrayList<GankBean> restVideo;
    @SerializedName("前端")
    public ArrayList<GankBean> frontEnd;
    @SerializedName("瞎推荐")
    public ArrayList<GankBean> recommend;
    @SerializedName("福利")
    public ArrayList<GankBean> photo;
    @SerializedName("拓展资源")
    public ArrayList<GankBean> expandResources;
}

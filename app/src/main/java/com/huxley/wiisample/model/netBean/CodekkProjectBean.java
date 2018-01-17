package com.huxley.wiisample.model.netBean;

import com.huxley.wiitools.utils.GsonUtils;

import java.util.List;

/**
 *
 * Created by LeiJin01 on 2016/7/29.
 */
public class CodekkProjectBean {

    public String _id;
    public String authorName;
    public String authorUrl;
    public String bigType;
    public String committer;
    public String createTime;
    public String demoUrl;
    public String desc;
    public int expiredTimes;
    public boolean hide;
    public String lang;
    public String officialUrl;
    public String projectName;
    public String projectUrl;
    public boolean recommend;
    public String smallType;
    public String source;
    public String updateTime;
    public int usedTimes;
    public int voteUp;
    public String codeKKUrl;
    public List<TagsBean> tags;
    public String content;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }

    public static class TagsBean {
        public String createTime;
        public String name;
        public String userName;
        public String type;
        public String contentId;

        @Override
        public String toString() {
            return GsonUtils.get().toJson(this);
        }
    }
}

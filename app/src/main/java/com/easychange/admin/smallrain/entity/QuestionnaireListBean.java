package com.easychange.admin.smallrain.entity;

import android.app.Activity;

public class QuestionnaireListBean {
    String title;
    String content;
    Class activity;

    public QuestionnaireListBean(Class activity,String title,String content){
        setActivity(activity);
        setContent(content);
        setTitle(title);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public Class getActivity() {
        return activity;
    }
}

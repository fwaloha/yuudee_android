package com.easychange.admin.smallrain.entity;

import java.util.List;

/**
 * create 2018/10/16 0016
 * by 李
 * 页面注释：
 **/
public class QuestionListBean {
    public String title;
    public List<ChildBean> childBean;


    public QuestionListBean(){}
    public void setChildBean(List<ChildBean> childBean) {
        this.childBean = childBean;
    }

    public List<ChildBean> getChildBean() {
        return childBean;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public class ChildBean{
        String content;
        Boolean isTrue;

        public ChildBean(){}
        public ChildBean(String content,Boolean isTrue){
            setTrue(isTrue);
            setContent(content);
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTrue(Boolean aTrue) {
            isTrue = aTrue;
        }

        public Boolean getTrue() {
            return isTrue;
        }

        public String getContent() {
            return content;
        }
    }
}

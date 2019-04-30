package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import com.easychange.admin.smallrain.entity.NetChangeBean;
import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import bean.ChildMessageBean;

public class BalloonActivityTest {
    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<BalloonActivity> mActivityRule =
            new ActivityTestRule<>(BalloonActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        NetChangeBean bean = new NetChangeBean(false);
        Method BreakNetBean = FuncUtils.funcMethod(BalloonActivity.class,
                "BreakNetBean", NetChangeBean.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                BreakNetBean, bean);

        Method showPingguDialog = FuncUtils.funcMethod(BalloonActivity.class,
                "showPingguDialog");
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                showPingguDialog);

        ChildMessageBean childMessageBean = new ChildMessageBean();
        Method setPicNameFromWeb = FuncUtils.funcMethod(BalloonActivity.class,
                "setPicNameFromWeb", ChildMessageBean.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                setPicNameFromWeb, childMessageBean);
    }
}
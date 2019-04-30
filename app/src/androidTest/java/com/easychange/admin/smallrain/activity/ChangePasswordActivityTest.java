package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class ChangePasswordActivityTest {

    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<ChangePasswordActivity> mActivityRule =
            new ActivityTestRule<>(ChangePasswordActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
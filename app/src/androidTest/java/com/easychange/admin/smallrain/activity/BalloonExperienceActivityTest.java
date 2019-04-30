package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class BalloonExperienceActivityTest {
    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<BalloonExperienceActivity> mActivityRule =
            new ActivityTestRule<>(BalloonExperienceActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
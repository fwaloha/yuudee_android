package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class CompleteRegisterActivityTest {

    @Rule
    public ActivityTestRule<CompleteRegisterActivity> mActivityRule =
            new ActivityTestRule<>(CompleteRegisterActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class DongciActivityTest {

    @Rule
    public ActivityTestRule<DongciActivity> mActivityRule =
            new ActivityTestRule<>(DongciActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
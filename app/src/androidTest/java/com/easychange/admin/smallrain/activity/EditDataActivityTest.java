package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class EditDataActivityTest {

    @Rule
    public ActivityTestRule<EditDataActivity> mActivityRule =
            new ActivityTestRule<>(EditDataActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
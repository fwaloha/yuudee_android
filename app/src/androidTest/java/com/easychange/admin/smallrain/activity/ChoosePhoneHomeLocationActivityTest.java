package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class ChoosePhoneHomeLocationActivityTest {

    @Rule
    public ActivityTestRule<ChoosePhoneHomeLocationActivity> mActivityRule =
            new ActivityTestRule<>(ChoosePhoneHomeLocationActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();
    }
}
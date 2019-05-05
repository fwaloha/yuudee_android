package com.easychange.admin.smallrain.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CompleteRegisterActivityTest {

    @Rule
    public ActivityTestRule<CompleteRegisterActivity> mActivityRule =
            new ActivityTestRule<>(CompleteRegisterActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Espresso.onView(ViewMatchers.withId(R.id.tv_sure)).perform(ViewActions.click());
    }
}
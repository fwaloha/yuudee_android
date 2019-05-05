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
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Espresso.onView(ViewMatchers.withId(R.id.btn_register)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.layout_more)).perform(ViewActions.click());
    }
}
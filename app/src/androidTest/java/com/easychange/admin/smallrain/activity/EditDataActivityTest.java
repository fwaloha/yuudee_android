package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class EditDataActivityTest {

    @Rule
    public ActivityTestRule<EditDataActivity> mActivityRule =
            new ActivityTestRule<>(EditDataActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Method showFamilyDialog = FuncUtils.funcMethod(EditDataActivity.class,
                "showFamilyDialog", String.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                showFamilyDialog, "13800000000");

//        Espresso.onView(ViewMatchers.withId(R.id.tv_sure)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.img_home_right)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.img_home_left)).perform(ViewActions.click());
    }
}
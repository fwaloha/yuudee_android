package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class ChangePasswordActivityTest {

    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<ChangePasswordActivity> mActivityRule =
            new ActivityTestRule<>(ChangePasswordActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Method updatePassword = FuncUtils.funcMethod(ChangePasswordActivity.class,
                "updatePassword", String.class, String.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                updatePassword,"eee", "fff");

//        Espresso.onView(ViewMatchers.withId(R.id.img_home_left)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.tv_sure)).perform(ViewActions.click());
    }
}
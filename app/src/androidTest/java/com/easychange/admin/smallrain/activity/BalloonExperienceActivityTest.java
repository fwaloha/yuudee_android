package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class BalloonExperienceActivityTest {
    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<BalloonExperienceActivity> mActivityRule =
            new ActivityTestRule<>(BalloonExperienceActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Method showIsRemindDialog = FuncUtils.funcMethod(BalloonExperienceActivity.class,
                "showIsRemindDialog", String.class, String.class, String.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                showIsRemindDialog, "dd", "ddd", "ddd");

        Method showFamilyDialog = FuncUtils.funcMethod(BalloonExperienceActivity.class,
                "showFamilyDialog");
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                showFamilyDialog);
    }
}
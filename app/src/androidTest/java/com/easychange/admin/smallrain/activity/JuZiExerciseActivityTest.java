package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class JuZiExerciseActivityTest {

    @Rule
    public ActivityTestRule<JuZiExerciseActivity> mActivityRule =
            new ActivityTestRule<>(JuZiExerciseActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Method playLocalVoice = FuncUtils.funcMethod(JuZiExerciseActivity.class,
                "playLocalVoice", String.class, Boolean.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                playLocalVoice,"男-蓝鸟吃虫.MP3", false);
    }
}
package com.easychange.admin.smallrain;

import android.support.test.rule.ActivityTestRule;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        MainActivity.startActivityWithParmeter(mActivityRule.getActivity(), 1);

        Method initStatusBar = FuncUtils.funcMethod(MainActivity.class,
                "initStatusBar");
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                initStatusBar);
    }
}
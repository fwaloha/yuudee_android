package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.testN.FuncUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class ChoosePhoneHomeLocationActivityTest {

    @Rule
    public ActivityTestRule<ChoosePhoneHomeLocationActivity> mActivityRule =
            new ActivityTestRule<>(ChoosePhoneHomeLocationActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        Method qcellcoreList = FuncUtils.funcMethod(ChoosePhoneHomeLocationActivity.class,
                "qcellcoreList");
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                qcellcoreList);
    }
}
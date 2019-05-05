package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class PinTuAllFlyActivityTest {

    @Rule
    public ActivityTestRule<PinTuAllFlyActivity> mActivityRule =
            new ActivityTestRule<>(PinTuAllFlyActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

//        BaseBean<AssementReviewBean> assementReviewBeanBaseBean = new BaseBean<>();
//        assementReviewBeanBaseBean.data = new AssementReviewBean();
//        Method UpDate = FuncUtils.funcMethod(AssessActivity.class,
//                "UpDate", BaseBean.class);
//        FuncUtils.funcInvoke(mActivityRule.getActivity(),
//                UpDate,assementReviewBeanBaseBean);
    }
}
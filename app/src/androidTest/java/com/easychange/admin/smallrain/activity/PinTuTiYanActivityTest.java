package com.easychange.admin.smallrain.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class PinTuTiYanActivityTest {

    @Rule
    public ActivityTestRule<PinTuTiYanActivity> mActivityRule =
            new ActivityTestRule<>(PinTuTiYanActivity.class);

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
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
public class DongciActivityTest {

    @Rule
    public ActivityTestRule<DongciActivity> mActivityRule =
            new ActivityTestRule<>(DongciActivity.class);

    @Test
    public void test() {
        mActivityRule.getActivity().test();

//        BaseBean<AssementReviewBean> assementReviewBeanBaseBean = new BaseBean<>();
//        assementReviewBeanBaseBean.data = new AssementReviewBean();
//        Method UpDate = FuncUtils.funcMethod(DongciActivity.class,
//                "UpDate", BaseBean.class);
//        FuncUtils.funcInvoke(mActivityRule.getActivity(),
//                UpDate,assementReviewBeanBaseBean);

        Espresso.onView(ViewMatchers.withId(R.id.iv_home)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.fl_choose1)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.fl_choose2)).perform(ViewActions.click());

    }
}
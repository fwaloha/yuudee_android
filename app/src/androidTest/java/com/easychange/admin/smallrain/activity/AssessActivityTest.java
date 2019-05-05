package com.easychange.admin.smallrain.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.testN.FuncUtils;
import com.qlzx.mylibrary.bean.BaseBean;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import bean.AssementReviewBean;

@RunWith(AndroidJUnit4.class)
public class AssessActivityTest {

    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<AssessActivity> mActivityRule =
            new ActivityTestRule<>(AssessActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        mActivityRule.getActivity().test();

        mActivityRule.getActivity().getAbccccIsRemind("");

        Method setButtonColor = FuncUtils.funcMethod(AssessActivity.class,
                "setButtonColor", String.class, String.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                setButtonColor, "3", "1");

        BaseBean<AssementReviewBean> assementReviewBeanBaseBean = new BaseBean<>();
        assementReviewBeanBaseBean.data = new AssementReviewBean();
        Method UpDate = FuncUtils.funcMethod(AssessActivity.class,
                "UpDate", BaseBean.class);
        FuncUtils.funcInvoke(mActivityRule.getActivity(),
                UpDate,assementReviewBeanBaseBean);

//        // 指定输入框中输入文本，同时关闭键盘
//        onView(withId(R.id.operand_one_edit_text)).perform(typeText(operandOne),
//                closeSoftKeyboard());
//        onView(withId(R.id.operand_two_edit_text)).perform(typeText(operandTwo),
//                closeSoftKeyboard());

        // 获取特定按钮执行点击事件
        Espresso.onView(ViewMatchers.withId(R.id.iv_back)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.btn_abc)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.btn_pcdi)).perform(ViewActions.click());

//        // 获取文本框中显示的结果
//        onView(withId(R.id.operation_result_text_view)).check(matches(withText(expectedResult)));
    }
}
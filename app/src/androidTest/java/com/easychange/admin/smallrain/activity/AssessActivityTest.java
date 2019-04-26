package com.easychange.admin.smallrain.activity;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.qlzx.mylibrary.util.PreferencesHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author BySevenroup小小
 * @create 2019/4/20 11:59
 * @Description
 */
public class AssessActivityTest {

    private AssessActivity assessActivity;
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        assessActivity = new AssessActivity();
        appContext = InstrumentationRegistry.getTargetContext();


    }

    @After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void onCreate() {

    }

    @org.junit.Test
    public void onDestroy() {
    }

    @Test
    public void getAbccccIsRemind() {
        assessActivity.getAbccccIsRemind(new PreferencesHelper(appContext).getToken());
    }
}
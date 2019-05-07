package com.easychange.admin.smallrain.utils;

import android.app.Application;
import android.content.Context;

import com.easychange.admin.smallrain.BuildConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowLog.class}, constants = BuildConfig.class, sdk = 23)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.json.*", "sun.security.*", "javax.net.*"})
public abstract class BaseTestCase {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private static boolean hasInited = false;

    @Before
    public void setUp() {
//        ShadowLog.stream = System.out;
//        if (!hasInited) {
//            hasInited = true;
//        }
        MockitoAnnotations.initMocks(this);
    }

    public Application getApplication() {
        return RuntimeEnvironment.application;
    }

    public Context getContext() {
        return RuntimeEnvironment.application;
    }
}

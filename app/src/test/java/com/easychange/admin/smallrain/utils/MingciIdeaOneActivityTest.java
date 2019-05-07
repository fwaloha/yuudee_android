package com.easychange.admin.smallrain.utils;

import com.easychange.admin.smallrain.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import bean.MingciBean;
import mingci.MingciIdeaOneActivity;
import mingci.MingciOneActivity;

/**
 * gradle clean connectedAndroidTest
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MingciIdeaOneActivityTest extends BaseTestCase{

    @Test
    @PrepareForTest({MingciIdeaOneActivity.class, MingciBean.class})
    public void test() {
        Robolectric.buildActivity(MingciIdeaOneActivity.class).create().get();
        PowerMockito.mock(MingciIdeaOneActivity.class).startTime(1,1,1.0);
        PowerMockito.mock(MingciIdeaOneActivity.class).stopTime();
//        PowerMockito.mockStatic(AppUtil.class);
//        PowerMockito.when(AppUtil.getVersionName()).thenReturn("1.4.0");
//
//        PowerMockito.mockStatic(OAuthManager.class);
//        OAuthManager mockOAuth = PowerMockito.mock(OAuthManager.class);
//        PowerMockito.when(OAuthManager.getInstance()).thenReturn(mockOAuth);
//        PowerMockito.when(mockOAuth.getSargerasToken()).thenReturn("c97faa92-34ea-4248-a19e-9a9fb848b29b");
//
//        AppApplication.mInstance = getApplication();
//
//        PowerMockito.mockStatic(NetUtil.class);
//        PowerMockito.when(NetUtil.isNetworkConnected(AppApplication.getInstance())).thenReturn(true);
//
//        PreferenceUtil.init();
//        PersistentPreferenceUtil.init();
//
//        ComplaintActivity complaintActivity = Robolectric.buildActivity(ComplaintActivity.class).create().get();
//        assertNotNull(complaintActivity);
//        complaintActivity.jumpCompensate();
//        Intent expectedIntent = new Intent(complaintActivity, HelpActivity.class);
//        ShadowActivity shadowActivity = Shadows.shadowOf(complaintActivity);
//        Intent actualIntent = shadowActivity.getNextStartedActivity();
//        Assert.assertEquals(expectedIntent.getComponent().getClassName(), actualIntent.getComponent().getClassName());
    }

}
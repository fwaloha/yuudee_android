package com.easychange.admin.smallrain;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.easychange.admin.smallrain.activity.PinTuAllFlyActivity;
import com.easychange.admin.smallrain.utils.ForegroundCallbacks;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import com.qlzx.mylibrary.util.PreferencesHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import bean.DelayTimeBean;
import bean.DongciGuoGuan;
import bean.JuziChengzuGuan;
import bean.JuzifenjieGuoGuan;
import bean.MingciGuoGuan;
import cn.jpush.android.api.JPushInterface;
import dongci.DongciTestOneActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MyApplication extends Application {
    private static MyApplication application;
    public boolean isBunching;
    private static Handler mainHandler;//全局的handler
    //    public List<String> currentDongci80 = new ArrayList();
    public String[] currentDongci80 = new String[4];

    public String currentDongci80cishu = "";
    public DongciGuoGuan dongciGuoGuan;
    public MingciGuoGuan mingciGuoGuan;
    public JuzifenjieGuoGuan juzifenjieGuoGuan;
    private Timer timer;
    public JuziChengzuGuan juziChengzuGuan;


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //建议在测试阶段建议设置成true，发布时设置为fals
        CrashReport.initCrashReport(getApplicationContext(), "24523e10a7", true);
        application = this;
        //初始化mainHandler
        mainHandler = new Handler();

        initOkhttp();

        PreferencesHelper helper = new PreferencesHelper(this);
        helper.saveInt("sp", "dongciJinbi", 0);

        dongciGuoGuan = new DongciGuoGuan();
        mingciGuoGuan = new MingciGuoGuan();
        juziChengzuGuan = new JuziChengzuGuan();
        juzifenjieGuoGuan = new JuzifenjieGuoGuan();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

//        String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
//        Log.e("registrationID", registrationID);

        UMConfigure.init(this, "5bfaaccaf1f556067e000315"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setWeixin("wx29c790d4f2786062", "6f61834f3f5b4c90c357d0655f80b8c1");
        PlatformConfig.setQQZone("1106732218", "3Umf2GOQvvZjKVLY");

        boolean isFirstInstall = helper.getBoolean("xiaoyudi", "isFirstInstall", true);
        if(isFirstInstall){
            helper.saveBoolean("xiaoyudi", "isFirstInstall", false);
            helper.saveString("xiaoyudi", "currentGifListData",  "0,1,2,3,4,5,6,7,8,9");
        }

        ForegroundCallbacks.init(this);
        ForegroundCallbacks.get().addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
            }

            @Override
            public void onBecameBackground() {
            }
        });
    }

    public boolean isTwentyMinutes = false;

    //订阅事件，并指定该事件在什么线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MesssageEventBus(DelayTimeBean event) { // 显示接收的消息
        stopTime();

        isTwentyMinutes = true;

        if (timer == null) {
            timer = new Timer();
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isTwentyMinutes = false;
            }
        };
        timer.schedule(timerTask, 0, 20 * 60 * 1000);//1000ms执行一次
    }

    private void stopTime() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }


    private void initOkhttp() {

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//
//                //其他配置
//                .build();
//
//        OkHttpUtils.initClient(okHttpClient);

        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(80000L, TimeUnit.MILLISECONDS)// 原来设置10000L ，设置成8x 主要是防止下载时间过长
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    public static Context getGloableContext() {
        return application.getApplicationContext();
    }


    public static MyApplication getApplication() {
        return application;
    }
}


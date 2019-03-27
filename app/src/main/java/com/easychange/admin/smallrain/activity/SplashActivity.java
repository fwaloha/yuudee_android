package com.easychange.admin.smallrain.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.ForegroundCallbacks;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.LogUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bean.SplashPicBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import dongci.DongciTestOneActivity;
import http.RemoteApi;
import http.Setting;
import okhttp3.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guo on 2017/4/25.
 * 欢迎页
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splansh)
    ImageView ivSplansh;
    private PreferencesHelper preferencesHelper;
    private final int REQUIRES_PERMISSION = 0;
    private static final int REQUEST_CODE_SETTING = 300;
    private String token;
    private boolean isInState = true;
    private boolean noImg;
    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            AlertDialog.newBuilder(SplashActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("为保证APP的正常运行，需要以下权限:\n1.访问SD卡（选择图片等功能）\n2.调用摄像头（扫码下单等功能）\n3.打电话功能")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("依然拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rationale.cancel();
                        }
                    }).show();
        }
    };
    private boolean isYetExecuteGotoLogin = false;
    private boolean isYetExecuteGotoMain = false;
    private List<Activity> activityList;

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        closeAndroidPDialog();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ForegroundCallbacks.get().addListener(forListener);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();  //隐藏掉标题栏
        }
       // requestPermission();
        activityList = com.easychange.admin.smallrain.base.BaseActivity.getActivityList();
        if (activityList != null) {
            activityList.add(this);
        }

        LogUtil.d("SplashActivity token--" + token);
        getIvfromServe("1");

//        Intent intent = new Intent(SplashActivity.this, MingciOneActivity.class);
//        intent.putExtra("position", 9);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, JuZiFeiJieXunLianActivityFourClick.class);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, DongciTrainOneActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, DongciTrainOneActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, JuZiChengZuXunLianActivity.class);
//        intent.putExtra("position", 9);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, JuZiFeiJieXunLianActivityFourClick.class);
//        intent.putExtra("position", 9);
//        startActivity(intent);

//        Intent intent = new Intent(SplashActivity.this, DongciTrainOneActivity.class);
//        intent.putExtra("position", 9);
//        startActivity(intent);
    }

    private String isLogin = "1";
    private long milTime = 3000;
    private boolean isBJump = false;
    private CountDownTimer mCountDown = new CountDownTimer(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(1)) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("dis", "onTick() called with: millisUntilFinished = [" + millisUntilFinished + "]");
            milTime = millisUntilFinished;
        }

        @Override
        public void onFinish() {
//            toHome();
            Log.d("dis", "onTick() called with: millisUntilFinished = [" + "hhhhhhhhhhhhhh" + "]");
            if ("1".equals(isLogin)) {
               gotoMain();
            } else if ("2".equals(isLogin)) {
                gotoLogin();
            }

        }
    };

    ForegroundCallbacks.Listener forListener = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
            isInState = true;
            if (isBJump) {
                if ("1".equals(isLogin)) {
                    gotoMain();
                } else if ("2".equals(isLogin)) {
                    gotoLogin();
                }
            }

        }

        @Override
        public void onBecameBackground() {
            isInState = false;
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ForegroundCallbacks.get().removeListener(forListener);
        forListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null) {
            activityList.remove(this);
        }
        ForegroundCallbacks.get().removeListener(forListener);
        forListener = null;
        mCountDown.cancel();
        mCountDown.onFinish();
    }

    public void getIvfromServe(String type) {
        HttpHelp.getInstance().create(RemoteApi.class).getImagefromServer(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<SplashPicBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<SplashPicBean> imageBeanBaseBean) {
                        super.onNext(imageBeanBaseBean);
                        if (imageBeanBaseBean.code == 200) {
                            SplashPicBean splashPicBean = imageBeanBaseBean.data;
                            if (splashPicBean != null && !TextUtils.isEmpty(splashPicBean.getImage())) {
                                GlideUtil.display(SplashActivity.this, splashPicBean.getImage(), ivSplansh);
                            } else {
                                noImg = true;
                            }

                            getIsTokenUsefull(new PreferencesHelper(SplashActivity.this).getToken());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                     //   requestPermission();
                        getIsTokenUsefull(new PreferencesHelper(SplashActivity.this).getToken());
                    }
                });

//
//        String url = Setting.appStartImage();
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//
//        stringStringHashMap.put("type", type);
//
//        OkHttpUtils
//                .post().params(stringStringHashMap)
//                .url(url)//接口地址
//                .id(222)//XX接口的标识
//                .build()
//                .execute(new BaseStringCallback_Host(SplashActivity.this, this));
    }


    private void requestPermission() {
        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUIRES_PERMISSION)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            LogUtil.d("权限申请成功  onSucceed");
            switch (requestCode) {
                case REQUIRES_PERMISSION: {
//                    getIsTokenUsefull(new PreferencesHelper(SplashActivity.this).getToken());
                }
            }
        }


        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            LogUtil.d("权限申请成功  onFailed");
            switch (requestCode) {

                case REQUIRES_PERMISSION: {
                    // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                    if (AndPermission.hasAlwaysDeniedPermission(SplashActivity.this, deniedPermissions)) {
                        // 第一种：用默认的提示语。
//                AndPermission.defaultSettingDialog(ListenerActivity.this, REQUEST_CODE_SETTING).show();

                        // 第二种：用自定义的提示语。
                        AndPermission.defaultSettingDialog(SplashActivity.this, REQUEST_CODE_SETTING)
                                .setTitle("权限申请失败")
                                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                                .setPositiveButton("好，去设置")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ToastUtil.showToast(SplashActivity.this, "权限申请失败，无法进入app");
                                        finish();
                                    }
                                })
                                .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
                    } else {
                        ToastUtil.showToast(SplashActivity.this, "权限申请失败，无法进入app");
                        finish();
                    }

                    break;
                }
            }


        }
    };

    public void getIsTokenUsefull(String token) {
        String url = Setting.toAssess();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("token", token);

        Log.e("数据", "token" + token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(33333)//XX接口的标识
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(SplashActivity.this, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        (SplashActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                            @Override
                            public void run() {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String code = jsonObject.getString("code");
                                    String msg1 = (String) jsonObject.getString("msg");

                                    if (code.equals("200")) {
                                        isLogin = "1";
//                                        gotoMain();
                                    } else {
                                        isLogin = "2";
//                                        gotoLogin();
                                    }

                                    if (noImg) {
                                        mCountDown.onFinish();
                                    } else {
                                        mCountDown.start();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
    }


    private void gotoMain() {
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
        if (!isYetExecuteGotoMain) {
            isYetExecuteGotoMain = true;
            if (isInState) {
                ForegroundCallbacks.get().removeListener(forListener);
                forListener = null;
                Intent intent = new Intent(SplashActivity.this, BalloonActivity.class);
                startActivity(intent);
                finish();
            }
        }
 /*           }
        }, 5000);//2、APP启动页停留时间太短，（设置停留时间5s）*/
    }

    private void gotoLogin() {
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
        if (!isYetExecuteGotoLogin) {
            isYetExecuteGotoLogin = true;
            if (isInState) {
                ForegroundCallbacks.get().removeListener(forListener);
                forListener = null;
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
  /*          }
        }, 5000);*/
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                LogUtil.d("从设置回来");
               // requestPermission();
                break;
            }
        }
    }
}

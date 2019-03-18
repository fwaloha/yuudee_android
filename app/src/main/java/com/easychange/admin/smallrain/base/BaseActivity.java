package com.easychange.admin.smallrain.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.NetWorkUtils;
import com.githang.statusbar.StatusBarCompat;
import com.qlzx.mylibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lxk on 2017/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static List<Activity> activityList = new ArrayList<>();
    protected Context mContext;

    public static List<Activity> getActivityList() {
        return activityList;
    }

    public static void setActivityList(List<Activity> activityList) {
        BaseActivity.activityList = activityList;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = getApplicationContext();//主要是方便子类调用
        initStatusBar();
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();  //隐藏掉标题栏
        }
        if (activityList != null) {
            activityList.add(this);
        }
        if (!NetWorkUtils.isNetworkConnected(this)) {
            ToastUtil.showToast(this, "当前无网络连接，请检查设置");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //ViewHelp.getInstance().setTopPadding(getWindow().getDecorView(),getStatusBarHeight());
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.dialog_bg_color) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null) {
            activityList.remove(this);
        }
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
        }
    }

    private void initStatusBar() {
        //最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 全透明实现
            // getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}

package com.easychange.admin.smallrain.utils;

import android.app.Activity;
import android.content.Intent;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.activity.HomeActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.qlzx.mylibrary.common.Constants;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import bean.ExitLoginBean;

public class GoToLoginActivityUtils {

    /**
     * token失效了，调用这个方法
     * @param mContext
     */
    public static void tokenFailureLoginOut(Activity mContext) {

        PreferencesHelper preferencesHelper = new PreferencesHelper(MyApplication.getApplication());
        preferencesHelper.saveToken("");

        Intent intent = new Intent(MyApplication.getApplication(), HomeActivity.class);
        intent.putExtra("isCloseOtherActivity",true);
        mContext.startActivity(intent);
        ToastUtil.showToast(mContext, Constants.MISSTOKEN);
        EventBusUtil.post(new ExitLoginBean());
        mContext.finish();
    }

    public static void tokenLose(Activity mContest){
        PreferencesHelper preferencesHelper = new PreferencesHelper(MyApplication.getApplication());
        preferencesHelper.saveToken("");

        Intent intent = new Intent(MyApplication.getApplication(), HomeActivity.class);
        intent.putExtra("isCloseOtherActivity",true);
        mContest.startActivity(intent);
        EventBusUtil.post(new ExitLoginBean());
        mContest.finish();
    }
}

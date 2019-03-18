package com.easychange.admin.smallrain.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.views.CustomTopBarNew;
import com.qlzx.mylibrary.util.DialogUtil;


/**
 * Created by guo on 2017/11/15.
 * android:digits="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"
 */

public class WebActivitys extends AppCompatActivity {
    private WebView webView;
    private TextView tvMyBack;
//    private sg.zhihui.server.com.sh.app.widget.TitleBar titleBar;

    private String url;
    public DialogUtil mDialogUtil;
    public Dialog mBaseDialog;

//    保存按钮，回到案件Fragment
//    转发，弹出一个弹框。 然后就是微信分享，邮寄

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs);

//        Eyes.setStatusBarColor(WebActivitys.this, Color.parseColor("#70A0EC"));  //translucent 透明的

        webView = (WebView) findViewById(R.id.web_view);
        tvMyBack = (TextView) findViewById(R.id.tv_myback);
//        titleBar = (TitleBar) findViewById(R.id.tb_title);

//        CustomTopBarNew topbar = (CustomTopBarNew) findViewById(R.id.topbar);
//        topbar.setTitleColor(Color.parseColor("#FFFFFF"));
//        topbar.setTopbarBackgroundColor(Color.parseColor("#70A0EC"));
//        topbar.setonTopbarNewLeftLayoutListener(new CustomTopBarNew.OnTopbarNewLeftLayoutListener() {
//
//            public void onTopbarLeftLayoutSelected() {
//                finish();
//            }
//        });

        mDialogUtil = new DialogUtil(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
//        titleBar.setTitleText(title);
//        topbar.setTopbarTitle(title);

        //设置webview的屏幕适配
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.getJavaScriptCanOpenWindowsAutomatically();
//        dialog = new SharedDialog(this);

//        webView.addJavascriptInterface(this, "client");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingDialog("");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                tvMyBack.setVisibility(View.VISIBLE);
                hideLoadingDialog();
            }
        });

        webView.loadUrl(url);


    }


    public boolean isCanBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }

    public void showLoadingDialog(String msg) {
        if (msg != null) {
            if (mBaseDialog != null && mBaseDialog.isShowing()) {
                mBaseDialog.dismiss();
                mBaseDialog = null;
            }
            mBaseDialog = mDialogUtil.showLoading(msg);
        }
    }

    public void hideLoadingDialog() {
        if (mBaseDialog != null && mBaseDialog.isShowing()) {
            mBaseDialog.dismiss();
        }
    }


    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivitys.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /**
     * 从通知过来
     *
     * @param context
     * @param title
     * @param url
     */
    public static void startActivityFromInform(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivitys.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}

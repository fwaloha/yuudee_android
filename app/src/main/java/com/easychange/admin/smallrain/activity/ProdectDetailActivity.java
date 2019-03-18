package com.easychange.admin.smallrain.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;

import bean.ProductIntroductionBean;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProdectDetailActivity extends BaseActivity {

    private WebView mWebView;
    private String content = "";

    //这个是控制加载富文本时候图片适应屏幕
    private String js = "<script type=\"text/javascript\">" +

            "var imgs = document.getElementsByTagName('img');" + // 找到img标签

            "for(var i = 0; i<imgs.length; i++){" + // 逐个改变

            "imgs[i].style.width = '100%';" + // 宽度改为100%

            "imgs[i].style.height = 'auto';" +

            "}" + "</script>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodectmessage);
        mWebView = ((WebView) findViewById(R.id.wv));
        mWebView.setBackgroundColor(0);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置webview的屏幕适配
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.getJavaScriptCanOpenWindowsAutomatically();


        getProductIntroduction("2");
    }

    public void getProductIntroduction(String type) {
        HttpHelp.getInstance().create(RemoteApi.class).getProductIntroduction(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<ProductIntroductionBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<ProductIntroductionBean> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
                            ProductIntroductionBean productIntroductionBean = baseBean.data;

                            content = productIntroductionBean.getContent();

                            mWebView.loadDataWithBaseURL("", content + js, "text/html", "UTF-8", null);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
        }

}

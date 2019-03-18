package com.easychange.admin.smallrain.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.ChoosePhoneHomeLocationActivity;
import com.easychange.admin.smallrain.activity.PerfectionChildrenInfoActivity;
import com.easychange.admin.smallrain.adapter.SelectCityAdapter;
import com.easychange.admin.smallrain.base.BaseFragment;
import com.easychange.admin.smallrain.entity.CityListBean;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.CommonPopupWindow;
import com.google.gson.Gson;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import java.util.List;

import bean.LocationBean;
import bean.ProductIntroductionBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * admin  2018/8/24 wan
 * 产品介绍
 */
public class ProductFragment extends BaseFragment {
    @BindView(R.id.rl_popuwindow)
    RelativeLayout rlPopuwindow;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.wv)
    WebView mWebView;
    Unbinder unbinder;
    private Dialog mDialog;
    private CommonPopupWindow window;
    private String content = "";

    //这个是控制加载富文本时候图片适应屏幕
    private String js = "<script type=\"text/javascript\">" +

            "var imgs = document.getElementsByTagName('img');" + // 找到img标签

            "for(var i = 0; i<imgs.length; i++){" + // 逐个改变

            "imgs[i].style.width = '100%';" + // 宽度改为100%

            "imgs[i].style.height = 'auto';" +

            "}" + "</script>";


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.frag_product, null);
        unbinder = ButterKnife.bind(this, view);
        WebView mWebView = view.findViewById(R.id.wv);

        //设置webview的屏幕适配
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.getJavaScriptCanOpenWindowsAutomatically();

        mWebView.setBackgroundColor(0);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");

        getProductIntroduction("1");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"2".equals(((MainActivity)getActivity()).getIsRemind())){
                    return;
                }
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    protected void initLazyData() {

        // TODO: 2018/10/18 0018
//        wv.getSettings().setDefaultTextEncodingName("UTF-8");
////                                webView.loadData(data.getContent(), "text/html", "UTF-8");
//        wv.loadData("填写数据", "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void getProductIntroduction(String type) {
        HttpHelp.getInstance().create(RemoteApi.class).getProductIntroduction(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<ProductIntroductionBean>>(getActivity(), null) {
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

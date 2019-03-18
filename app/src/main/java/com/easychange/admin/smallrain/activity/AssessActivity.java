package com.easychange.admin.smallrain.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.common.Constants;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;

import bean.AssementReviewBean;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AssessActivity extends AppCompatActivity {
    Unbinder unbinder;
    private Dialog mDialog;
    private String abcIsRemind = "";
    private String pcdiIsRemind = "";
    private String abcurl = Constants.HOST+"xiaoyudi/pages/abcquestionnaire.html?token=%s";

    private String bbcurl = Constants.HOST+"xiaoyudi/pages/abcPresentation.html?token=%s&status=core";

    private String abcurltwo = Constants.HOST+"xiaoyudi/pages/abcquestionnaire.html?token=%s&status=%s";

    private String pcdiurl = Constants.HOST+"xiaoyudi/pages/pcdiRequired.html?token=%s";
    private String pcdiurltwo = Constants.HOST+"xiaoyudi/pages/pcdiRequired.html?token=%s&status=%s";
    private String pcdiurlresult = Constants.HOST+"xiaoyudi/pages/allResult.html?token=%s&status=core";
    private String pcdiurlrequire = Constants.HOST+"xiaoyudi/pages/requiredPresentation.html?token=%s&status=core";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assess);
        unbinder = ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();  //隐藏掉标题栏
        }
    }

    /***abcIsRemind：abc问卷状态 1:没有做问卷  2:已经做过问卷  3:做问卷中途退出
     * pcdiIsRemind ： pcdi问卷状态 1:没有做问卷 2:只做了必做问卷 3:做问卷中途退出 4:做完全部问卷
     * @param view
     */
    @OnClick({R.id.iv_back, R.id.btn_abc, R.id.btn_pcdi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(AssessActivity.this,BalloonActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_abc:
                if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
                    getAbcIsRemind(new PreferencesHelper(this).getToken());
                }
                break;
            case R.id.btn_pcdi:
                if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
                    getPcdiIsRemind(new PreferencesHelper(this).getToken());
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void getAbcIsRemind(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getAssessmentReview(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    Intent intent = new Intent(AssessActivity.this, PerfectionChildrenInfoActivity.class);
                                    startActivity(intent);
                                } else if ("2".equals(assementReviewBean.getIsRemind())) {
                                    abcIsRemind = assementReviewBean.getAbcIsRemind();
                                    Log.e("AssessFragment", abcIsRemind);
                                    switch (abcIsRemind) {
                                        case "1":
                                            abcurl = String.format(abcurl, new PreferencesHelper(AssessActivity.this).getToken());
                                            WebActivity.startActivity(AssessActivity.this, "", abcurl);
                                            break;
                                        case "2":
                                            bbcurl = String.format(bbcurl, new PreferencesHelper(AssessActivity.this).getToken());
                                            WebActivity.startActivity(AssessActivity.this, "", bbcurl);
                                            break;
                                        case "3":
                                            abcurltwo = String.format(abcurltwo, new PreferencesHelper(AssessActivity.this).getToken(), "1");
                                            WebActivity.startActivity(AssessActivity.this, "", abcurltwo);
                                            break;
                                     /*   case "4":
                                            abcurltwo = String.format(abcurltwo, new PreferencesHelper(getActivity()).getToken(), "core");
                                            WebActivity.startActivity(getActivity(), "", abcurltwo);
                                            break;*/
                                    }
                                }
                            }
                        }else if (assementReviewBeanBaseBean.code ==205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(AssessActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    public void getPcdiIsRemind(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getAssessmentReview(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    Intent intent = new Intent(AssessActivity.this, PerfectionChildrenInfoActivity.class);
                                    startActivity(intent);
                                } else if ("2".equals(assementReviewBean.getIsRemind())) {
                                    pcdiIsRemind = assementReviewBean.getPcdiIsRemind();
                                    Log.e("AssessFragment", pcdiIsRemind);
                                    switch (pcdiIsRemind) {
                                        case "1":
                                            pcdiurl = String.format(pcdiurl, new PreferencesHelper(AssessActivity.this).getToken());
                                            WebActivity.startActivity(AssessActivity.this, "", pcdiurl);
                                            break;
                                        case "2":
                                            //TODO 可能要加 core   ,到时候基础接口上要加下。
//                                            pcdiurlrequire = String.format(pcdiurlrequire,new PreferencesHelper(getActivity()).getToken(),"core");
                                            pcdiurlrequire = String.format(pcdiurlrequire, new PreferencesHelper(AssessActivity.this).getToken());
                                            WebActivity.startActivity(AssessActivity.this, "", pcdiurlrequire);
                                            break;
                                        case "3":
                                            pcdiurltwo = String.format(pcdiurltwo, new PreferencesHelper(AssessActivity.this).getToken(), "1");
                                            WebActivity.startActivity(AssessActivity.this, "", pcdiurltwo);
                                            break;
                                        case "4":
                                            pcdiurlresult = String.format(pcdiurlresult, new PreferencesHelper(AssessActivity.this).getToken());
                                            WebActivity.startActivity(AssessActivity.this, "", pcdiurlresult);
                                            break;
                                    }
                                }
                            }
                        }else if (assementReviewBeanBaseBean.code ==205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(AssessActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    private void showTitleDialog() {

        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.popuwindow_isremind, null);
        mDialog.setContentView(inflate);
        Window dialogwindow = mDialog.getWindow();
        dialogwindow.setGravity(Gravity.TOP);
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        // lp.windowAnimations = R.style.PopUpBottomAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.y = ScreenUtil.getBottomStatusHeight(PersionMessageActivity.this);
        dialogwindow.setAttributes(lp);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        Button btnExerpirence, btnfinishMessage;
        btnExerpirence = inflate.findViewById(R.id.btn_experience);
        btnfinishMessage = inflate.findViewById(R.id.btn_finishmessage);
        btnExerpirence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                finish();
            }
        });
        btnfinishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                PerfectionChildrenInfoActivity.startActivityWithnoParmeter(AssessActivity.this);
            }
        });

    }

}



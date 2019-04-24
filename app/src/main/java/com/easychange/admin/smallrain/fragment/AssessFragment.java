package com.easychange.admin.smallrain.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.AssessActivity;
import com.easychange.admin.smallrain.activity.PerfectionChildrenInfoActivity;
import com.easychange.admin.smallrain.activity.QuestionnaireDetailsActivity;
import com.easychange.admin.smallrain.activity.WebActivity;
import com.easychange.admin.smallrain.adapter.QuestionnaireAdapter;
import com.easychange.admin.smallrain.base.BaseFragment;
import com.easychange.admin.smallrain.entity.QuestionnaireListBean;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.google.gson.Gson;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.common.Constants;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.AssementReviewBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * admin  2018/8/24 wan
 */
public class AssessFragment extends BaseFragment {
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
    private String precisionStandardTime;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.frag_assess, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initLazyData() {


    }
    public static String getPrecisionStandardTime(){
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);

}

    /***abcIsRemind：abc问卷状态 1:没有做问卷  2:已经做过问卷  3:做问卷中途退出
     * pcdiIsRemind ： pcdi问卷状态 1:没有做问卷 2:只做了必做问卷 3:做问卷中途退出 4:做完全部问卷
     * @param view
     */
    @OnClick({R.id.iv_back, R.id.btn_abc, R.id.btn_pcdi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!"2".equals(((MainActivity)getActivity()).getIsRemind())){
                    return;
                }
                mActivity.finish();
                break;
            case R.id.btn_abc:
                if (!TextUtils.isEmpty(new PreferencesHelper(getActivity()).getToken())) {
                    getAbcIsRemind(new PreferencesHelper(getActivity()).getToken());
                }
                break;
            case R.id.btn_pcdi:
                if (!TextUtils.isEmpty(new PreferencesHelper(getActivity()).getToken())) {
                    getPcdiIsRemind(new PreferencesHelper(getActivity()).getToken());
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
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            precisionStandardTime = getPrecisionStandardTime();

                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    Intent intent = new Intent(getActivity(), PerfectionChildrenInfoActivity.class);
                                    getActivity().startActivity(intent);
                                    ToastUtil.showToast(getActivity(),"完善儿童信息后，继续做问卷");
                                } else if ("2".equals(assementReviewBean.getIsRemind())) {
                                    abcIsRemind = assementReviewBean.getAbcIsRemind();
                                    pcdiIsRemind = assementReviewBean.getPcdiIsRemind();

                                    switch (abcIsRemind) {
                                        case "1":
                                            abcurl = String.format(abcurl, new PreferencesHelper(getActivity()).getToken());
                                            WebActivity.startActivity(getActivity(), "", abcurl+"&v="+precisionStandardTime);
                                            break;
                                        case "2":
                                            bbcurl = String.format(bbcurl, new PreferencesHelper(getActivity()).getToken());
                                            WebActivity.startActivity(getActivity(), "", bbcurl+"&v="+precisionStandardTime);
                                            break;
                                        case "3":
                                            abcurltwo = String.format(abcurltwo, new PreferencesHelper(getActivity()).getToken(), "1");
                                            WebActivity.startActivity(getActivity(), "", abcurltwo+"&v="+precisionStandardTime);
                                            break;
                                     /*   case "4":
                                            abcurltwo = String.format(abcurltwo, new PreferencesHelper(getActivity()).getToken(), "core");
                                            WebActivity.startActivity(getActivity(), "", abcurltwo);
                                            break;*/
                                    }
                                }
                            }
                        }else if (assementReviewBeanBaseBean.code ==205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(getActivity());
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
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            precisionStandardTime = getPrecisionStandardTime();

                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    Intent intent = new Intent(getActivity(), PerfectionChildrenInfoActivity.class);
                                    getActivity().startActivity(intent);
                                    ToastUtil.showToast(getActivity(),"完善儿童信息后，继续做问卷");
                                } else if ("2".equals(assementReviewBean.getIsRemind())) {
                                    pcdiIsRemind = assementReviewBean.getPcdiIsRemind();
                                    pcdiurl = String.format(pcdiurl, new PreferencesHelper(getActivity()).getToken());
                                    switch (pcdiIsRemind) {
                                        case "1":

                                            WebActivity.startActivity(getActivity(), "", pcdiurl+"&v="+precisionStandardTime);
                                            break;
                                        case "2":
                                            //TODO 可能要加 core   ,到时候基础接口上要加下。
//                                            pcdiurlrequire = String.format(pcdiurlrequire,new PreferencesHelper(getActivity()).getToken(),"core");
                                            pcdiurlrequire = String.format(pcdiurlrequire, new PreferencesHelper(getActivity()).getToken());

                                            WebActivity.startActivity(getActivity(), "", pcdiurlrequire+"&v="+precisionStandardTime);
                                            break;
                                        case "3":
                                            pcdiurltwo = String.format(pcdiurltwo, new PreferencesHelper(getActivity()).getToken(), "1");

                                            WebActivity.startActivity(getActivity(), "", pcdiurltwo+"&v="+precisionStandardTime);
                                            break;
                                        case "4":
                                            pcdiurlresult = String.format(pcdiurlresult, new PreferencesHelper(getActivity()).getToken());

                                            WebActivity.startActivity(getActivity(), "", pcdiurlresult+"&v="+precisionStandardTime);
                                            break;
                                    }
                                }
                            }
                        }else if (assementReviewBeanBaseBean.code == 205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(getActivity());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


    private void showTitleDialog() {

        mDialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popuwindow_isremind, null);
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
                getActivity().finish();
            }
        });
        btnfinishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                PerfectionChildrenInfoActivity.startActivityWithnoParmeter(getActivity());
            }
        });

    }

}

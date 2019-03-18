package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.adapter.DayRvAdapter;
import com.easychange.admin.smallrain.adapter.MonthRvAdapter;
import com.easychange.admin.smallrain.adapter.WeekRvAdapter;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.entity.LuckBackRoomListviewBean;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.views.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.ChildMessageBean;
import bean.CommenBean;
import bean.DayData;
import bean.GetFortifierBean;
import bean.LookLatelyBean;
import bean.MonthData;
import bean.TranningFileMonthBean;
import bean.UserBean;
import bean.WeekData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dongci.DongciTestOneActivity;
import dongci.DongciTrainOneActivity;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create 2018/10/20 0020
 * 图表页面
 **/
public class TrainActivity extends BaseActivity implements AsyncRequest {
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.img_user_data)
    CircleImageView imgUserData;
    @BindView(R.id.can_content_view)
    RecyclerView canContentView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.iv_day)
    ImageView ivDay;
    @BindView(R.id.ll_day)
    LinearLayout llDay;
    @BindView(R.id.iv_week)
    ImageView ivWeek;
    @BindView(R.id.ll_week)
    LinearLayout llWeek;
    @BindView(R.id.iv_month)
    ImageView ivMonth;
    @BindView(R.id.ll_month)
    LinearLayout llMonth;
    //    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private DayRvAdapter dayRvAdapter;
    private WeekRvAdapter weekRvAdapter;
    private MonthRvAdapter monthRvAdapter;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_product);
        ButterKnife.bind(this);

        position = getIntent().getIntExtra("position", 1);
//        module	是	string	模块类型（1 名词 2 动词 3 句子成组 4句子分解）
        dayInfo(position + "");
        weekInfo(position + "");
        monthInfo(position + "");

        Log.e("数据", "position" + position + "");
        initData();

        initPicName();
    }

    private void initData() {
        dayRvAdapter = new DayRvAdapter(canContentView);
        weekRvAdapter = new WeekRvAdapter(canContentView);
        monthRvAdapter = new MonthRvAdapter(canContentView);

        canContentView.setLayoutManager(new LinearLayoutManager(this));
//        canContentView.addItemDecoration(new RecycleViewDivider(this));
        canContentView.setAdapter(dayRvAdapter);

//        schedule	int	总通关进度
//        countTime	int	总学习时长（已秒为单位）

    }

    @OnClick({R.id.ll_day, R.id.ll_week, R.id.ll_month, R.id.img_home_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_day:  //

                ivDay.setImageResource(R.drawable.day_select);//不会变形
                ivWeek.setImageResource(R.drawable.week);//不会变形
                ivMonth.setImageResource(R.drawable.month);//不会变形

                canContentView.setAdapter(dayRvAdapter);
                break;
            case R.id.ll_week:    //
                ivDay.setImageResource(R.drawable.day);//不会变形
                ivWeek.setImageResource(R.drawable.week_select);//不会变形
                ivMonth.setImageResource(R.drawable.month);//不会变形

                canContentView.setAdapter(weekRvAdapter);

                break;
            case R.id.ll_month:
                ivDay.setImageResource(R.drawable.day);//不会变形
                ivWeek.setImageResource(R.drawable.week);//不会变形
                ivMonth.setImageResource(R.drawable.month_select);//不会变形

                canContentView.setAdapter(monthRvAdapter);

                break;
            case R.id.img_home_right:
                finish();
                break;
        }
    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(1)) {//日返回数据

            String result = (String) data;
            (TrainActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
//                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {

                            Gson gson = new Gson();
//                            schedule	int	总通关进度
//                            countTime	int	总学习时长（已秒为单位）
//                            studyTime	int	学习时长（已秒为单位）
                            DayData model = gson.fromJson(result,
                                    new TypeToken<DayData>() {
                                    }.getType());

//                            schedule	int	总通关进度
//                            countTime	int	总学习时长（已秒为单位）
//                            DecimalFormat df = new DecimalFormat("#.00");
                            DecimalFormat df = new DecimalFormat("0.00");
                            String format = df.format((float) model.getData().getCountTime() / 60);
                            int length = format.length();
//                            String schedule = model.getData().getSchedule() + "";
//                            int length1 = schedule.length();

                            DecimalFormat dfNoSmallNum = new DecimalFormat("0");
                            String progress = dfNoSmallNum.format((float) model.getData().getSchedule() * 100) + "%";
                            int length1 = progress.length();

//                          （1 名词 2 动词 3 句子成组 4句子分解）
                            String titleText = null;
                            if (position == 1) {
                                titleText = "自学了" + format + "分钟 的名词短语结构通关进度" + progress + "（单项通关）";
                            } else if (position == 2) {
                                titleText = "自学了" + format + "分钟 的动词短语结构通关进度" + progress + "（单项通关）";
                            } else if (position == 3) {
                                titleText = "自学了" + format + "分钟 的句子成组短语结构通关进度" + progress + "（单项通关）";
                            } else if (position == 4) {
                                titleText = "自学了" + format + "分钟 的句子分解短语结构通关进度" + progress + "（单项通关）";
                            }

                            SpannableStringBuilder spannable = new SpannableStringBuilder(titleText);
                            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#14101e")), 3, 3 + length
                                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            if (position == 1 || position == 2) {
                                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#14101e")), 17 + length, 17 + length + length1
                                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            } else if (position == 3 || position == 4) {
                                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#14101e")), 19 + length, 19 + length + length1
                                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }

                            tvTitle.setText(spannable);


                            List<DayData.DataBean.ResultListBean> resultList = model.getData().getResultList();

                            if (null != resultList && resultList.size() != 0) {
                                dayRvAdapter.setData(resultList);
                            }

                        }
//                        ToastUtil.showToast(TrainActivity.this, msg1);
                    } catch (
                            JSONException e)

                    {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//周返回数据

            String result = (String) data;
            (TrainActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            WeekData model = gson.fromJson(result,
                                    new TypeToken<WeekData>() {
                                    }.getType());

                            List<WeekData.DataBean.ResultListBean> resultList = model.getData().getResultList();

                            if (null != resultList && resultList.size() != 0) {
//                                |countTime |int |学习时长（一秒为单位） |
//                                |accuracy |double |每次测试的正确率 |
                                weekRvAdapter.setData(resultList);
                            }

                        }
//                        ToastUtil.showToast(TrainActivity.this, msg1);
                    } catch (
                            JSONException e)

                    {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(3)) {//月返回数据

            String result = (String) data;
            (TrainActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            MonthData model = gson.fromJson(result,
                                    new TypeToken<MonthData>() {
                                    }.getType());

                            List<MonthData.DataBean.ResultListBean> resultList = model.getData().getResultList();

                            if (null != resultList && resultList.size() != 0) {
//                                |countTime |int |学习时长（一秒为单位） |
//                                |accuracy |double |每次测试的正确率 |
//                                List<MonthData.DataBean.ResultListBean> resultListBeans = resultList.get(0);

//                                一年分12个月 每个月份四到五周
                                monthRvAdapter.setData(resultList);
                            }
                        }
//                        ToastUtil.showToast(DongciTestOneActivity.this, msg1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {
        (TrainActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                Log.e("数据", var3);
                ToastUtil.showToast(mContext, var3 + "");

            }
        });
    }

    /**
     *
     */
    private void dayInfo(String module) {
        PreferencesHelper helper = new PreferencesHelper(TrainActivity.this);
        String token = helper.getToken();

        String url = Setting.dayInfo();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);
        stringStringHashMap.put("scene", "2");

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(TrainActivity.this, this));
    }

    private void weekInfo(String module) {
        PreferencesHelper helper = new PreferencesHelper(TrainActivity.this);
        String token = helper.getToken();

        String url = Setting.weekInfo();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);
        stringStringHashMap.put("scene", "2");

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(TrainActivity.this, this));
    }

    private void monthInfo(String module) {
        PreferencesHelper helper = new PreferencesHelper(TrainActivity.this);
        String token = helper.getToken();

        String url = Setting.monthInfo();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);
        stringStringHashMap.put("scene", "2");

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(TrainActivity.this, this));
    }


    public void initPicName() {
        getChildMessageBean(new PreferencesHelper(TrainActivity.this).getToken());
    }


    public void setPicNameFromWeb(ChildMessageBean childMessageBean) {
        String string = childMessageBean.getXydChild().getName();
//        Log.e("initNameonfromweb", string);
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            String mobile = childMessageBean.getXydChild().getPhoneNumber();

//            Log.e("booloon", mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));
        }

        if (TextUtils.isEmpty(childMessageBean.getXydChild().getPhoto())) {
            String chilSex = childMessageBean.getXydChild().getSex();
            if ("0".equals(chilSex)) {//儿童性别（0：男 1：女）
                GlideUtil.display(this, R.drawable.nan111, imgUserData);
            } else {
                GlideUtil.display(this, R.drawable.nv111, imgUserData);
            }
        } else {
            String photo = childMessageBean.getXydChild().getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。

//            Log.e("photourl1", photo);

            GlideUtil.display(this, photo, imgUserData);
        }
    }

    public void setPicNameFromLocal(PreferencesHelper myhelper) {
        UserBean userBean = new Gson().fromJson(new PreferencesHelper(this).getUserInfo(), UserBean.class);
        if (userBean != null) {
            String string = myhelper.getString("sp", "nickname",
                    "");
            if (TextUtils.isEmpty(string) || string.equals("null")) {
                String mobile = myhelper.getPhoneNum();
            }

            if (TextUtils.isEmpty(userBean.getChilPhoto())) {
                String chilSex = userBean.getChilSex();
                if (chilSex.equals("0")) {//儿童性别（0：男 1：女）
                    GlideUtil.display(this, R.drawable.nan111, imgUserData);
                } else {
                    GlideUtil.display(this, R.drawable.nv111, imgUserData);
                }
            } else {
                String photo = myhelper.getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。

                GlideUtil.display(this, photo, imgUserData);
            }
        }
    }

    /**
     * 获取儿童信息
     *
     * @param token
     * @return
     */
    public void getChildMessageBean(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getChildMessage(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<ChildMessageBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<ChildMessageBean> childMessageBeanBaseBean) {
                        super.onNext(childMessageBeanBaseBean);
                        if (childMessageBeanBaseBean.code == 200) {
                            ChildMessageBean childMessageBean = childMessageBeanBaseBean.data;
                            if (childMessageBean != null) {
                                setPicNameFromWeb(childMessageBean);
                            } else {
                                setPicNameFromLocal(new PreferencesHelper(TrainActivity.this));
                            }
                        } else if (childMessageBeanBaseBean.code == 205 || childMessageBeanBaseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(TrainActivity.this);
                        } else {
                            setPicNameFromLocal(new PreferencesHelper(TrainActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


}

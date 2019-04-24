package com.easychange.admin.smallrain.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.base.BaseDialog;
import com.easychange.admin.smallrain.entity.CustomsClearanceSuccessBean;
import com.easychange.admin.smallrain.entity.NetChangeBean;
import com.easychange.admin.smallrain.receiver.NetReceiver;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.UMUtils;
import com.easychange.admin.smallrain.views.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import bean.AssementReviewBean;
import bean.ChildMessageBean;
import bean.DongciBean;
import bean.ExitLoginBean;
import bean.FinishHomeActivityBean;
import bean.HeadChangeBean;
import bean.JuZiChengZu;
import bean.JuZiFenJieBean;
import bean.LookLatelyBean;
import bean.LookLatelyTwoBean;
import bean.MingciBean;
import bean.NickNameChangeBean;
import bean.PushBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dongci.DongciTestOneActivity;
import dongci.DongciTrainOneActivity;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import juzi.JuZiChengZuCiShiLianActivity;
import juzi.JuZiChengZuXunLianActivity;
import juzi.JuZiFeiJieCiShiActivityFourClick;
import juzi.JuZiFeiJieXunLianActivityFourClick;
import mingci.MingciIdeaOneActivity;
import mingci.MingciOneActivity;
import mingci.MingciOneExperienceActivity;
import mingci.MingciTestOneActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * describe:  儿童首页
 */
public class BalloonActivity extends BaseActivity implements AsyncRequest {
    //18811496406   qwe123
//    18135697075   a123456
//18811496406 qwe123
//            18210700961
//            duxujie

    //    16601215921   123456 潘的号码
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_jiazhang)
    LinearLayout llJiazhang;
    @BindView(R.id.fl_root)
    FrameLayout fl_root;
    @BindView(R.id.iv_mingci)
    ImageView iv_mingci;
    @BindView(R.id.iv_dongci)
    ImageView iv_dongci;
    @BindView(R.id.iv_juzi)
    ImageView iv_juzi;
    private float flutterHeight;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES};
    private int x;
    private int y;
    private double exitTime;
    private MyApplication application11;
    private LookLatelyBean model;

    //    private int currentClickPosition = 0;
    private String module = "";

    private String messageRemind = "因为每个孩子的能力不同，为了使每个孩子都按照自己的节奏进步," +
            "新雨滴提供了个性化的训练和指导方案，在继续学习前，需要了" +
            "解孩子的个人信息和基础能力，请家长先完善训练儿童个人信息和问卷评估。";
    private String messageAbc = "因为每个孩子的能力不同，为了使每个孩子都按照自己的节奏进步," +
            "新雨滴提供了个性化的训练和指导方案，在继续学习前，需要了" +
            "解孩子的基础能力，请家长先完成问卷评估。";
    private ScaleAnimation scaleAnimation_small;
    private ScaleAnimation scaleAnimation_big;
    private int currentChoosePosition = -1;
    private boolean isFinished = false;

    private String currentVerbScene;
    private int currentVerbLength;
    private String currentVerbGroupId;

    private String currentNounScene;
    private int currentNounLength;
    private String currentNounGroupId;

    private String currentSentenceGroupScene;
    private int currentSentenceGroupLength;
    private String currentSentenceGroupGroupId = "";

    private String currentSentenceDecompositionScene;
    private int currentSentenceDecompositionLength;
    private String currentSentenceDecompositionGroupId = "";
    private ObjectAnimator cuttentTransYAnim;

    private Boolean isIntoCoursewareActivity = false;//是否进入课件页面

    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();
    boolean isNetConnect = true;
    boolean isShowAccess = true;
    String remindType = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balloon);
        ButterKnife.bind(this);
        EventBusUtil.register(this);
        Log.e("myactivity", "BalloonActivity");
        UMUtils.getDeviceInfo(this);
        Log.i("liubiao", "onCreate: "+new PreferencesHelper(this).getToken());

        scaleAnimation_big = (ScaleAnimation) AnimationUtils.loadAnimation(BalloonActivity.this, R.anim.scale_big);
        scaleAnimation_small = (ScaleAnimation) AnimationUtils.loadAnimation(BalloonActivity.this, R.anim.scale_small);
        initPicName();
        initAnimator();
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, permissions, 1001);
        }
        application11 = (MyApplication) getApplication();
        getSystemStatistics();
//        网络监听
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);

//        25.训练完的气球缩小比例修改

//        左上角的“xiaoming”按钮，数据取注册时的“儿童昵称”字段，如果尚未填写儿童昵称，
//        显示填写的“注册手机号，但隐藏中间4位”；头像取家长设置页面中上传的儿童头像，如果没有上传头像的，根据性别选择默认男女孩头像。
        EventBus.getDefault().postSticky(new FinishHomeActivityBean());


    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void CustomsClearanceSuccessBean(CustomsClearanceSuccessBean event) {//通关成功的回调
        if ("2".equals(checking)) {
            showPingguDialog();
        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(NetChangeBean event) {//断网
        isNetConnect = event.netStatus;
        if (!mReceiver.isConnected()) {
            //iv_juzi.setClickable(true);
            iv_juzi.setTag(true);
            //iv_mingci.setClickable(true);
            iv_mingci.setTag(true);
            // iv_dongci.setClickable(true);
            iv_dongci.setTag(true);
            ToastUtil.showToast(this, "当前网络已断开");
        } else {
            isIntoCoursewareActivity = false;
        }
    }

    private void showPingguDialog() {
        int height1 = MyUtils.dip2px(BalloonActivity.this, 270);

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_pinggu)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, height1)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();

        TextView tvTitle = dialog.getView(R.id.tv_title);
        TextView tvMessage = dialog.getView(R.id.tv_message);
        TextView tvCommit = dialog.getView(R.id.tv_commit);
        tvMessage.setText("恭喜您！" + tvName.getText().toString() + "在全部课程完成后，如再次完成全部必做和选做部分，" +
                "将获得幼童学习进展的对比评估，以便您了解幼童的学习成果，以后在生活中进行更好的巩固和学习。");

        dialog.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalloonActivity.this, AssessActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void initPicName() {
        getChildMessageBean(new PreferencesHelper(BalloonActivity.this).getToken());
    }


    public void setPicNameFromWeb(ChildMessageBean childMessageBean) {
        String string = childMessageBean.getXydChild().getName();
//        Log.e("initNameonfromweb", string);
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            String mobile = childMessageBean.getXydChild().getPhoneNumber();
            tvName.setText(mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));
//            Log.e("booloon", mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));

        } else {
            tvName.setText(string);
            tvName.setEllipsize(TextUtils.TruncateAt.END);
            tvName.setMaxEms(5);
            tvName.setMaxLines(1);
        }

        if (TextUtils.isEmpty(childMessageBean.getXydChild().getPhoto())) {
            String chilSex = childMessageBean.getXydChild().getSex();
            if ("0".equals(chilSex)) {//儿童性别（0：男 1：女）
                new PreferencesHelper(BalloonActivity.this).saveSex("0");
                GlideUtil.display(this, R.drawable.nan111, ivHead);
            } else {
                new PreferencesHelper(BalloonActivity.this).saveSex("1");
                GlideUtil.display(this, R.drawable.nv111, ivHead);
            }
        } else {
            String photo = childMessageBean.getXydChild().getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。
            GlideUtil.display(this, photo, ivHead);
        }
    }

    public void setPicNameFromLocal(PreferencesHelper myhelper) {
        UserBean userBean = new Gson().fromJson(new PreferencesHelper(this).getUserInfo(), UserBean.class);
        if (userBean != null) {
            String string = myhelper.getString("sp", "nickname",
                    "");
            if (TextUtils.isEmpty(string) || string.equals("null")) {
                String mobile = myhelper.getPhoneNum();
                tvName.setText(mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));
//                Log.e("booloon", mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));
            } else {
                tvName.setText(string);
            }

            if (TextUtils.isEmpty(myhelper.getPhoto()) || "null".equals(myhelper.getPhoto())) {
                String chilSex = myhelper.getSaveSex();
                if (chilSex.equals("0")) {//儿童性别（0：男 1：女）
                    GlideUtil.display(this, R.drawable.nan111, ivHead);
                } else {
                    GlideUtil.display(this, R.drawable.nv111, ivHead);
                }
            } else {
                String photo = myhelper.getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。

                GlideUtil.display(this, photo, ivHead);
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
                                setPicNameFromLocal(new PreferencesHelper(BalloonActivity.this));
                            }
                        } else if (childMessageBeanBaseBean.code == 205 || childMessageBeanBaseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(BalloonActivity.this);
                        } else {
                            setPicNameFromLocal(new PreferencesHelper(BalloonActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ExitLoginBean(ExitLoginBean event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NickNameChangeBean(NickNameChangeBean event) {
        String string = new PreferencesHelper(BalloonActivity.this).getString("sp", "nickname",
                "");
        if (!TextUtils.isEmpty(string) && string != null) {
            tvName.setText(string);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HeadChangeBean(HeadChangeBean event) {
        String photo = new PreferencesHelper(BalloonActivity.this).getPhoto();
        Log.e("photourl2", photo);
        GlideUtil.display(this, photo, ivHead);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        EventBusUtil.unregister(this);
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PushBean(PushBean event) {
        Intent intent = new Intent(BalloonActivity.this, AssessActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isIntoCoursewareActivity = false;
        getSystemStatistics();
        if (!isShowAccess) {
            if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
                getIsRemindDialog(new PreferencesHelper(this).getToken());
            }
        }
        isShowAccess = false;
    }

    @OnClick({R.id.iv_head, R.id.ll_jiazhang, R.id.iv_mingci, R.id.iv_dongci, R.id.iv_juzi})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.ll_jiazhang:
                if (isNetConnect) {
                    showFamilyDialog();
                } else {
                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                }
                break;
            case R.id.iv_mingci:
                if (!isNetConnect) {
                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                }
                break;
            case R.id.iv_dongci:
                if (!isNetConnect) {
                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                }
                break;
            case R.id.iv_juzi:
                if (!isNetConnect) {
                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                }
                break;
        }
    }


    /**
     *
     */
    private void getSystemStatistics() {
        PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
        String token = helper.getToken();

        String url = Setting.getSystemStatistics();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        Log.e("getSystemStatistics", "stringStringHashMap: " + stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(BalloonActivity.this, this));
    }

    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(2)) {//名词

            String result = (String) data;
            (BalloonActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");

                        if (code.equals("200")) {
//                            JSONObject data1 = jsonObject.getJSONObject("nounTraining");
//                            Log.e("数据", data1.toString());
                            Gson gson = new Gson();
                            MingciBean model = gson.fromJson(result,
                                    new TypeToken<MingciBean>() {
                                    }.getType());

                            if (currentNounLength != -1 && !TextUtils.isEmpty(currentNounScene)) {
//                                scene	是	string	学习场景 1训练 2测试 3意义
                                if (currentNounScene.equals("1")) {
                                    if (isCanlIntoNextActivity()) return;
                                    Intent intent = new Intent(BalloonActivity.this, MingciOneActivity.class);
                                    intent.putExtra("position", currentNounLength);
                                    intent.putExtra("model", model);
                                    if (TextUtils.isEmpty(currentNounGroupId)) {
                                        intent.putExtra("groupId", "");
                                    } else {
                                        intent.putExtra("groupId", currentNounGroupId);
                                    }
                                    if (isNetConnect) {
                                        startActivity(intent);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }

                                } else {
                                    if (isCanlIntoNextActivity()) return;
                                    Intent intent = new Intent(BalloonActivity.this, MingciTestOneActivity.class);
                                    intent.putExtra("position", currentNounLength);
                                    intent.putExtra("model", model);

                                    if (TextUtils.isEmpty(currentNounGroupId)) {
                                        intent.putExtra("groupId", "");
                                    } else {
                                        intent.putExtra("groupId", currentNounGroupId);
                                    }
                                    if (isNetConnect) {
                                        startActivity(intent);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else if (object.equals(1)) {//动词

            String result = (String) data;
            (BalloonActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");

                        if (code.equals("200")) {

                            Gson gson = new Gson();
                            DongciBean dongciBean = gson.fromJson(result,
                                    new TypeToken<DongciBean>() {
                                    }.getType());

                            if (currentVerbLength != -1 && !TextUtils.isEmpty(currentVerbScene)) {
//                                scene	是	string	学习场景 1训练 2测试 3意义
                                if (currentVerbScene.equals("1")) {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent11 = new Intent(BalloonActivity.this, DongciTrainOneActivity.class);
                                    intent11.putExtra("position", currentVerbLength);
                                    intent11.putExtra("model", dongciBean);
                                    intent11.putExtra("groupId", currentVerbGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent11);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                } else {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent = new Intent(BalloonActivity.this, DongciTestOneActivity.class);
//                        intent.putExtra("position", 9);
                                    intent.putExtra("position", currentVerbLength);
                                    intent.putExtra("model", dongciBean);
                                    intent.putExtra("groupId", currentVerbGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else if (object.equals(4)) {//标记那个接口

            String result = (String) data;
            (BalloonActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//                            JSONObject data1 = jsonObject.getJSONObject("data");
//                            Log.e("数据", data1.toString());
                            Gson gson = new Gson();
                            JuZiChengZu juzibean = gson.fromJson(result,
                                    new TypeToken<JuZiChengZu>() {
                                    }.getType());

                            if (currentSentenceGroupLength != -1 && !TextUtils.isEmpty(currentSentenceGroupScene)) {
//                                scene	是	string	学习场景 1训练 2测试 3意义
                                if (currentSentenceGroupScene.equals("1")) {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent11 = new Intent(BalloonActivity.this, JuZiChengZuXunLianActivity.class);
                                    intent11.putExtra("position", currentSentenceGroupLength);
                                    intent11.putExtra("model", juzibean);
                                    intent11.putExtra("groupId", currentSentenceGroupGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent11);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                } else {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent = new Intent(BalloonActivity.this, JuZiChengZuCiShiLianActivity.class);
                                    intent.putExtra("position", currentSentenceGroupLength);
                                    intent.putExtra("model", juzibean);
                                    intent.putExtra("groupId", currentSentenceGroupGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            });
        } else if (object.equals(5)) {//

            String result = (String) data;
            (BalloonActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            JuZiFenJieBean juzibean = gson.fromJson(result,
                                    new TypeToken<JuZiFenJieBean>() {
                                    }.getType());

                            if (currentSentenceDecompositionLength != -1 && !TextUtils.isEmpty(currentSentenceDecompositionScene)) {
//                                scene	是	string	学习场景 1训练 2测试 3意义
                                if (currentSentenceDecompositionScene.equals("1")) {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent11 = new Intent(BalloonActivity.this, JuZiFeiJieXunLianActivityFourClick.class);
                                    intent11.putExtra("position", currentSentenceDecompositionLength);
                                    intent11.putExtra("juzibean", juzibean);
                                    intent11.putExtra("groupId", currentSentenceDecompositionGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent11);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                } else {
                                    if (isCanlIntoNextActivity()) return;

                                    Intent intent = new Intent(BalloonActivity.this, JuZiFeiJieCiShiActivityFourClick.class);
                                    intent.putExtra("position", currentSentenceDecompositionLength);
                                    intent.putExtra("juzibean", juzibean);
                                    intent.putExtra("groupId", currentSentenceDecompositionGroupId);
                                    if (isNetConnect) {
                                        startActivity(intent);
                                    } else {
                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                    }
                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        } else if (object.equals(3)) {//
            String result = (String) data;
            (BalloonActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    String code = null;
                    String dataStr = null;

                    try {
                        jsonObject = new JSONObject(result);
                        code = jsonObject.getString("code");

                        dataStr = (String) jsonObject.getString("data");

                        if (code.equals("200")) {
                            if (dataStr == null) {   //data是null
                                //   iv_mingci.setClickable(true);
                                iv_mingci.setTag(true);
                                //    iv_dongci.setClickable(false);
                                iv_dongci.setTag(false);
                                // iv_juzi.setClickable(false);
                                iv_juzi.setTag(false);
                                iv_dongci.setImageResource(R.drawable.dongci_dark);
                                iv_juzi.setImageResource(R.drawable.juzi_dark);

                                if (null != cuttentTransYAnim) {
                                    cuttentTransYAnim.end();
                                }
                                startFlutterAnimator(iv_mingci, 1);
                                if (currentChoosePosition != -1) {
                                    if (currentChoosePosition == 0) {
                                        smallAnima(iv_mingci);
                                    } else if (currentChoosePosition == 1) {
                                        smallAnima(iv_dongci);
                                    } else {
                                        smallAnima(iv_juzi);
                                    }
                                }

                                bigAnima(iv_mingci);
                                currentChoosePosition = 0;

                                iv_dongci.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (view.getTag() != null && !(boolean) view.getTag()) {
                                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                            return;
                                        }
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    }
                                });

                                iv_juzi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (view.getTag() != null && !(boolean) view.getTag()) {
                                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                            return;
                                        }
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    }
                                });

                                iv_mingci.setOnTouchListener(null);
                                iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        ImageView imageView = ((ImageView) view);
                                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                        switch (motionEvent.getAction()) {

                                            case MotionEvent.ACTION_UP:
                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                    return true;
                                                }
                                                int x = (int) motionEvent.getX();
                                                int y = (int) motionEvent.getY();
                                                if (x > bitmap.getWidth()) {
                                                    x = bitmap.getWidth() - 1;
                                                }
                                                if (y > bitmap.getHeight()) {
                                                    y = bitmap.getHeight() - 2;
                                                }
                                                if (x < 0) {
                                                    x = 1;
                                                }
                                                if (y <= 0) {
                                                    y = 1;
                                                }
                                                int pixel = bitmap.getPixel(x, y);
                                                int redValue = Color.red(pixel);
                                                Log.e("redValue", "redValue" + redValue + "");
                                                if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                } else {
                                                    if (isCanlIntoNextActivity()) return true;

                                                    Intent intent = new Intent(BalloonActivity.this, MingciOneActivity.class);
                                                    if (isNetConnect) {
                                                        startActivity(intent);
                                                    } else {
                                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                    }
                                                }
                                                break;
                                        }
                                        return true;
                                    }
                                });

                            } //data是null结束

                            //data不为null

//                            两种情况 一种是data=0 一种是data对应好多键值对
//                            只有我上面说的两种情况 没有data=1的情况 会有data字典,里面的module=1的情况

//                            data=0 从头做
                            iv_mingci.setOnClickListener(null);
                            iv_dongci.setOnClickListener(null);
                            iv_juzi.setOnClickListener(null);

                            if (!TextUtils.isEmpty(dataStr) && dataStr.equals("0")) {//data是0
                                // iv_mingci.setClickable(true);
                                iv_mingci.setTag(true);
                                //  iv_dongci.setClickable(false);
                                iv_dongci.setTag(false);
                                //  iv_juzi.setClickable(false);
                                iv_juzi.setTag(false);
                                iv_dongci.setImageResource(R.drawable.dongci_dark);
                                iv_juzi.setImageResource(R.drawable.juzi_dark);

                                if (currentChoosePosition != -1) {
                                    if (currentChoosePosition == 0) {
                                        smallAnima(iv_mingci);
                                    } else if (currentChoosePosition == 1) {
                                        smallAnima(iv_dongci);
                                    } else {
                                        smallAnima(iv_juzi);
                                    }
                                }

                                if (null != cuttentTransYAnim) {
                                    cuttentTransYAnim.end();
                                }
                                startFlutterAnimator(iv_mingci, 1);
                                bigAnima(iv_mingci);
                                currentChoosePosition = 0;

                                iv_dongci.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (view.getTag() != null && !(boolean) view.getTag()) {
                                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                            return;
                                        }
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    }
                                });

                                iv_juzi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (view.getTag() != null && !(boolean) view.getTag()) {
                                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                            return;
                                        }
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    }
                                });
                                if (isFinished) {//已完成
                                    Log.i("liubiao", "已完善儿童信息"+isFinished);
                                    iv_mingci.setOnTouchListener(null);
                                    iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            ImageView imageView = ((ImageView) view);
                                            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                            switch (motionEvent.getAction()) {

                                                case MotionEvent.ACTION_UP:
                                                    if (view.getTag() != null && !(boolean) view.getTag()) {
                                                        ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                        return true;
                                                    }
                                                    int x = (int) motionEvent.getX();
                                                    int y = (int) motionEvent.getY();
                                                    if (x > bitmap.getWidth()) {
                                                        x = bitmap.getWidth() - 1;
                                                    }
                                                    if (y > bitmap.getHeight()) {
                                                        y = bitmap.getHeight() - 2;
                                                    }
                                                    if (x < 0) {
                                                        x = 1;
                                                    }
                                                    if (y <= 0) {
                                                        y = 1;
                                                    }
                                                    int pixel = bitmap.getPixel(x, y);
                                                    int redValue = Color.red(pixel);
                                                    Log.e("redValue", "redValue" + redValue + "");
                                                    if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                    } else {
                                                        if (isCanlIntoNextActivity()) return true;

                                                        Intent intent = new Intent(BalloonActivity.this, MingciOneActivity.class);
                                                        if (isNetConnect) {
                                                            startActivity(intent);
                                                        } else {
                                                            ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                        }
                                                    }
                                                    break;
                                            }
                                            return true;
                                        }
                                    });

                                } else {
                                    Log.i("liubiao", "没有完善"+isFinished);
                                    iv_mingci.setOnTouchListener(null);
                                    iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            ImageView imageView = ((ImageView) view);
                                            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                            switch (motionEvent.getAction()) {

                                                case MotionEvent.ACTION_UP:
                                                    if (view.getTag() != null && !(boolean) view.getTag()) {
                                                        ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                        return true;
                                                    }
                                                    int x = (int) motionEvent.getX();
                                                    int y = (int) motionEvent.getY();
                                                    if (x > bitmap.getWidth()) {
                                                        x = bitmap.getWidth() - 1;
                                                    }
                                                    if (y > bitmap.getHeight()) {
                                                        y = bitmap.getHeight() - 2;
                                                    }
                                                    if (x < 0) {
                                                        x = 1;
                                                    }
                                                    if (y <= 0) {
                                                        y = 1;
                                                    }
                                                    int pixel = bitmap.getPixel(x, y);
                                                    int redValue = Color.red(pixel);
                                                    Log.e("redValue", "redValue" + redValue + "");
                                                    if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                    } else {
                                                        if (isCanlIntoNextActivity()) return true;

                                                        PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
                                                        helper.saveInt("sp", "mingciJinbi_tiyan", 0);//金币

                                                        Intent intent = new Intent(BalloonActivity.this, MingciOneExperienceActivity.class);
                                                        if (isNetConnect) {
                                                            startActivity(intent);
//                                                            finish();
                                                        } else {
                                                            ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                        }
                                                    }
                                                    break;
                                            }
                                            return true;
                                        }
                                    });

                                    return;//这个是进入体验的，所以不在走下面的逻辑
                                }//是不是完善了儿童信息

                                Gson gson = new Gson();
                                LookLatelyTwoBean model1 = gson.fromJson(result,
                                        new TypeToken<LookLatelyTwoBean>() {
                                        }.getType());

                                String noun = model1.getList().getNoun();
                                if (noun.equals("2")) {
//
                                    iv_mingci.setOnTouchListener(null);
                                    iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            ImageView imageView = ((ImageView) view);
                                            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                            switch (motionEvent.getAction()) {

                                                case MotionEvent.ACTION_UP:
                                                    if (view.getTag() != null && !(boolean) view.getTag()) {
                                                        ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                        return true;
                                                    }
                                                    int x = (int) motionEvent.getX();
                                                    int y = (int) motionEvent.getY();
                                                    if (x > bitmap.getWidth()) {
                                                        x = bitmap.getWidth() - 1;
                                                    }
                                                    if (y > bitmap.getHeight()) {
                                                        y = bitmap.getHeight() - 2;
                                                    }
                                                    if (x < 0) {
                                                        x = 1;
                                                    }
                                                    if (y <= 0) {
                                                        y = 1;
                                                    }
                                                    int pixel = bitmap.getPixel(x, y);
                                                    int redValue = Color.red(pixel);
                                                    Log.e("redValue", "redValue" + redValue + "");
                                                    if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                    } else {
                                                        currentNounScene = "2";
                                                        currentNounLength = 0;
                                                        currentNounGroupId = "";

                                                        getNoun();
                                                    }
                                                    break;
                                            }
                                            return true;
                                        }
                                    });
                                }

                                if (model1.getGroupTraining().size() != 0) {
                                    for (int i = 0; i < model1.getGroupTraining().size(); i++) {
//                                        data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义
                                        LookLatelyTwoBean.GroupTrainingBean groupTrainingBean = model1.getGroupTraining().get(i);
                                        String module = groupTrainingBean.getModule();
                                        String scene = groupTrainingBean.getScene();
                                        int length = groupTrainingBean.getLength();
                                        int groupId = 0;
                                        groupId = groupTrainingBean.getId();

                                        if (module.equals("1")) {

                                            int finalGroupId = groupId;
//
                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentNounScene = scene;
                                                                currentNounLength = length;
                                                                currentNounGroupId = finalGroupId + "";

                                                                getNoun();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });

                                        }

                                    }
                                }//getGroupTraining有数值
                                return;
                            } else {//data不是0

                                if (code.equals("200")) {

                                    model = null;
                                    Gson gson = new Gson();
                                    model = gson.fromJson(result,
                                            new TypeToken<LookLatelyBean>() {
                                            }.getType());

//                            data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义

//                            groupTraining返回当前答题的小进度,做到第几道题了,
//                              例:module=2,scene=1,length=1 当前动词模块进度为训练做了1道

//                            list返回4个课件模块当前该做训练还是测试或意义测试 1训练,2测试,3意义测试
//                            noun	string	名词 学习子模块 1训练 2测试 3意义
//                            verb	string	动词 学习子模块 1训练 2测试 3意义
//                            group	string	句子成组 学习子模块 1训练 2测试 3意义
//                            sentence	string	句子分解 学习子模块 1训练 2测试 3意义
                                    //iv_mingci.setClickable(true);
                                    iv_mingci.setTag(true);
                                    // iv_dongci.setClickable(true);
                                    iv_dongci.setTag(true);
                                    // iv_juzi.setClickable(true);
                                    iv_juzi.setTag(true);
                                    module = model.getData().getModule();
                                    if (module.equals("1")) {
                                        // iv_dongci.setClickable(false);
                                        iv_dongci.setTag(false);
                                        //  iv_juzi.setClickable(false);
                                        iv_juzi.setTag(false);
                                        iv_dongci.setImageResource(R.drawable.dongci_dark);
                                        iv_juzi.setImageResource(R.drawable.juzi_dark);

                                        if (null != cuttentTransYAnim) {
                                            cuttentTransYAnim.end();
                                        }
                                        startFlutterAnimator(iv_mingci, 1);
                                        bigAnima(iv_mingci);
                                        currentChoosePosition = 0;
                                        iv_dongci.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                    return;
                                                }
                                                showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                            }
                                        });

                                        iv_juzi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                    return;
                                                }
                                                showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                            }
                                        });
                                    } else if (module.equals("2")) {
//                                首页展示名词气球缩小停止晃动，动词气球去灰，并且有晃动效果
                                        //iv_dongci.setClickable(true);
                                        iv_dongci.setTag(true);
                                        iv_dongci.setImageResource(R.drawable.dongci_yellow);

                                        //   iv_juzi.setClickable(false);
                                        iv_juzi.setTag(false);
                                        iv_juzi.setImageResource(R.drawable.juzi_dark);

                                        if (currentChoosePosition != -1) {
                                            if (currentChoosePosition == 0) {
                                                smallAnima(iv_mingci);
                                            } else if (currentChoosePosition == 1) {
                                                smallAnima(iv_dongci);
                                            } else {
                                                smallAnima(iv_juzi);
                                            }
                                        }

                                        if (null != cuttentTransYAnim) {
                                            cuttentTransYAnim.end();
                                        }
                                        startFlutterAnimator(iv_dongci, 2);
                                        bigAnima(iv_dongci);
                                        currentChoosePosition = 1;

                                        iv_juzi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                    return;
                                                }
                                                showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                            }
                                        });
                                    } else if (module.equals("3")) {
                                        //   iv_juzi.setClickable(true);
                                        iv_juzi.setTag(true);
                                        iv_juzi.setImageResource(R.drawable.juzi_yellow);
                                        //  iv_dongci.setClickable(true);
                                        iv_dongci.setTag(true);
                                        iv_dongci.setImageResource(R.drawable.dongci_yellow);

                                        if (currentChoosePosition != -1) {
                                            if (currentChoosePosition == 0) {
                                                smallAnima(iv_mingci);
                                            } else if (currentChoosePosition == 1) {
                                                smallAnima(iv_dongci);
                                            } else {
                                                smallAnima(iv_juzi);
                                            }
                                        }

                                        if (null != cuttentTransYAnim) {
                                            cuttentTransYAnim.end();
                                        }
                                        startFlutterAnimator(iv_juzi, 3);
                                        bigAnima(iv_juzi);
                                        currentChoosePosition = 2;
                                    } else {
                                        // iv_juzi.setClickable(true);
                                        iv_juzi.setTag(true);
                                        iv_juzi.setImageResource(R.drawable.juzi_yellow);
                                        //  iv_dongci.setClickable(true);
                                        iv_dongci.setTag(true);
                                        iv_dongci.setImageResource(R.drawable.dongci_yellow);

                                        if (currentChoosePosition != -1) {
                                            if (currentChoosePosition == 0) {
                                                smallAnima(iv_mingci);
                                            } else if (currentChoosePosition == 1) {
                                                smallAnima(iv_dongci);
                                            } else {
                                                smallAnima(iv_juzi);
                                            }
                                        }

                                        if (null != cuttentTransYAnim) {
                                            cuttentTransYAnim.end();
                                        }
                                        startFlutterAnimator(iv_juzi, 3);
                                        bigAnima(iv_juzi);
                                        currentChoosePosition = 2;
                                    }
                                    //名词
                                    String currentModule = model.getData().getModule();
                                    String currentNnoun = model.getList().getNoun();
                                    Log.i("liubiao", "不知道"+currentModule);
                                    if (currentModule.equals("1")) {
                                        intoCommonMingci();
                                    }

                                    if (currentNnoun.equals("2")) {
                                        intoFirstTestMingci();
                                    } else if (currentNnoun.equals("3")) {
                                        intoCommonMingciIdea();
                                    }
                                    if (model.getGroupTraining().size() != 0) {
                                        for (int i = 0; i < model.getGroupTraining().size(); i++) {
//                                        data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义
                                            LookLatelyBean.GroupTrainingBean groupTrainingBean = model.getGroupTraining().get(i);
                                            String module = groupTrainingBean.getModule();
                                            String scene = groupTrainingBean.getScene();
                                            int length = groupTrainingBean.getLength();
                                            int groupId = 0;
                                            groupId = groupTrainingBean.getId();

                                            String currentNoun = model.getList().getNoun();
                                            if (currentNoun.equals("3")) {

                                                iv_mingci.setOnTouchListener(null);
                                                iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                    @Override
                                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                                        ImageView imageView = ((ImageView) view);
                                                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                        switch (motionEvent.getAction()) {

                                                            case MotionEvent.ACTION_UP:
                                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                    return true;
                                                                }
                                                                int x = (int) motionEvent.getX();
                                                                int y = (int) motionEvent.getY();
                                                                if (x > bitmap.getWidth()) {
                                                                    x = bitmap.getWidth() - 1;
                                                                }
                                                                if (y > bitmap.getHeight()) {
                                                                    y = bitmap.getHeight() - 2;
                                                                }
                                                                if (x < 0) {
                                                                    x = 1;
                                                                }
                                                                if (y <= 0) {
                                                                    y = 1;
                                                                }
                                                                int pixel = bitmap.getPixel(x, y);
                                                                int redValue = Color.red(pixel);
                                                                Log.e("redValue", "redValue" + redValue + "");
                                                                if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                                } else {
                                                                    if (isCanlIntoNextActivity())
                                                                        return true;

                                                                    Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                                                                    if (isNetConnect) {
                                                                        startActivity(intent);
                                                                    } else {
                                                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                                    }
                                                                }
                                                                break;
                                                        }
                                                        return true;
                                                    }
                                                });

                                            }
                                            if (module.equals("1") && scene.equals("3")) {

                                                int finalGroupId1 = groupId;

                                                iv_mingci.setOnTouchListener(null);
                                                iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                    @Override
                                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                                        ImageView imageView = ((ImageView) view);
                                                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                        switch (motionEvent.getAction()) {

                                                            case MotionEvent.ACTION_UP:
                                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                    return true;
                                                                }
                                                                int x = (int) motionEvent.getX();
                                                                int y = (int) motionEvent.getY();
                                                                if (x > bitmap.getWidth()) {
                                                                    x = bitmap.getWidth() - 1;
                                                                }
                                                                if (y > bitmap.getHeight()) {
                                                                    y = bitmap.getHeight() - 2;
                                                                }
                                                                if (x < 0) {
                                                                    x = 1;
                                                                }
                                                                if (y <= 0) {
                                                                    y = 1;
                                                                }
                                                                int pixel = bitmap.getPixel(x, y);
                                                                int redValue = Color.red(pixel);
                                                                Log.e("redValue", "redValue" + redValue + "");
                                                                if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                                } else {
                                                                    if (isCanlIntoNextActivity())
                                                                        return true;

                                                                    Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                                                                    intent.putExtra("groupId", finalGroupId1 + "");
                                                                    intent.putExtra("position", length);
                                                                    if (isNetConnect) {
                                                                        startActivity(intent);
                                                                    } else {
                                                                        ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                                    }
                                                                }
                                                                break;
                                                        }
                                                        return true;
                                                    }
                                                });

                                            } else if (module.equals("1")) {
                                                int finalGroupId = groupId;
//
                                                iv_mingci.setOnTouchListener(null);
                                                iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                    @Override
                                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                                        ImageView imageView = ((ImageView) view);
                                                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                        switch (motionEvent.getAction()) {

                                                            case MotionEvent.ACTION_UP:
                                                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                    return true;
                                                                }
                                                                int x = (int) motionEvent.getX();
                                                                int y = (int) motionEvent.getY();
                                                                if (x > bitmap.getWidth()) {
                                                                    x = bitmap.getWidth() - 1;
                                                                }
                                                                if (y > bitmap.getHeight()) {
                                                                    y = bitmap.getHeight() - 2;
                                                                }
                                                                if (x < 0) {
                                                                    x = 1;
                                                                }
                                                                if (y <= 0) {
                                                                    y = 1;
                                                                }
                                                                int pixel = bitmap.getPixel(x, y);
                                                                int redValue = Color.red(pixel);
                                                                Log.e("redValue", "redValue" + redValue + "");
                                                                if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                                } else {
                                                                    currentNounScene = scene;
                                                                    currentNounLength = length;
                                                                    currentNounGroupId = finalGroupId + "";

                                                                    getNoun();
                                                                }
                                                                break;
                                                        }
                                                        return true;
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    //动词
                                    if (currentModule.equals("2")) {//currentModule.equals("2")  开始

                                        intoCommonMingci();

                                        if (currentNnoun.equals("2")) {
                                            intoFirstTestMingci();
                                        } else if (currentNnoun.equals("3")) {
                                            intoCommonMingciIdea();
                                        }
                                        String currentVerb = model.getList().getVerb();
                                        intoCommonDongci();

                                        if (currentVerb.equals("2")) {
                                            intoFirstTestDongci();
                                        }
                                    }//currentModule.equals("2")  结束

                                    for (int i = 0; i < model.getGroupTraining().size(); i++) {
//                                        data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义
                                        LookLatelyBean.GroupTrainingBean groupTrainingBean = model.getGroupTraining().get(i);
                                        String module = groupTrainingBean.getModule();
                                        String scene = groupTrainingBean.getScene();
                                        int length = groupTrainingBean.getLength();
                                        String groupId = "";
                                        groupId = groupTrainingBean.getId() + "";

                                        String currentNoun = model.getList().getNoun();
                                        if (currentNoun.equals("3")) {

                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                if (isCanlIntoNextActivity())
                                                                    return true;

                                                                Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                                                                if (isNetConnect) {
                                                                    startActivity(intent);
                                                                } else {
                                                                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                                }
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });

                                        }

                                        if (module.equals("1") && scene.equals("3")) {
                                            String finalGroupId11 = groupId;

                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                if (isCanlIntoNextActivity())
                                                                    return true;

                                                                Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                                                                intent.putExtra("groupId", finalGroupId11 + "");
                                                                intent.putExtra("position", length);
                                                                if (isNetConnect) {
                                                                    startActivity(intent);
                                                                } else {
                                                                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                                }
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });

                                        } else if (module.equals("1")) {
                                            String finalGroupId = groupId;
//
                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentNounScene = scene;
                                                                currentNounLength = length;
                                                                currentNounGroupId = finalGroupId + "";

                                                                getNoun();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });
                                        }

                                        if (module.equals("2")) {
                                            String finalGroupId = groupId;

                                            iv_dongci.setOnTouchListener(null);
                                            iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentVerbScene = scene;
                                                                currentVerbLength = length;
                                                                currentVerbGroupId = finalGroupId;

                                                                getVerb();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });
                                            break;
                                        }
                                    }

//                           句子
//                            data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
                                    String currentMmodule = model.getData().getModule();
                                    String group = model.getList().getGroup();
                                    String sentence = model.getList().getSentence();

                                    if (currentMmodule.equals("4")) {
                                        intoCommonMingci();
                                        if (currentNnoun.equals("2")) {
                                            intoFirstTestMingci();
                                        } else if (currentNnoun.equals("3")) {
                                            intoCommonMingciIdea();
                                        }

                                        dongCiBtnClickEvent();

                                        if (currentMmodule.equals("4") && sentence.equals("1")) {

                                            iv_juzi.setOnTouchListener(null);
                                            iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
//
                                                                currentSentenceDecompositionScene = "1";
                                                                currentSentenceDecompositionLength = 0;
                                                                currentSentenceDecompositionGroupId = "";

                                                                getSentenceResolve();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });

                                        } else if (currentMmodule.equals("4") && sentence.equals("2")) {

                                            iv_juzi.setOnTouchListener(null);
                                            iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentSentenceDecompositionScene = "2";
                                                                currentSentenceDecompositionLength = 0;
                                                                currentSentenceDecompositionGroupId = "";

                                                                getSentenceResolve();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });

                                        }
                                    }//这个是句子分解的

//                                    这是句子成组的
                                    if (currentMmodule.equals("3") || currentMmodule.equals("5")) {

                                        intoCommonMingci();
                                        if (currentNnoun.equals("2")) {
                                            intoFirstTestMingci();
                                        } else if (currentNnoun.equals("3")) {
                                            intoCommonMingciIdea();
                                        }

                                        dongCiBtnClickEvent();

                                        iv_juzi.setOnTouchListener(null);
                                        iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                                ImageView imageView = ((ImageView) view);
                                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                switch (motionEvent.getAction()) {

                                                    case MotionEvent.ACTION_UP:
                                                        if (view.getTag() != null && !(boolean) view.getTag()) {
                                                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                            return true;
                                                        }
                                                        int x = (int) motionEvent.getX();
                                                        int y = (int) motionEvent.getY();
                                                        if (x > bitmap.getWidth()) {
                                                            x = bitmap.getWidth() - 1;
                                                        }
                                                        if (y > bitmap.getHeight()) {
                                                            y = bitmap.getHeight() - 2;
                                                        }
                                                        if (x < 0) {
                                                            x = 1;
                                                        }
                                                        if (y <= 0) {
                                                            y = 1;
                                                        }
                                                        int pixel = bitmap.getPixel(x, y);
                                                        int redValue = Color.red(pixel);
                                                        Log.e("redValue", "redValue" + redValue + "");
                                                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                        } else {
                                                            if (isCanlIntoNextActivity())
                                                                return true;

                                                            Intent intent = new Intent(BalloonActivity.this, JuZiChengZuXunLianActivity.class);
                                                            if (isNetConnect) {
                                                                startActivity(intent);
                                                            } else {
                                                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");

                                                            }
                                                        }
                                                        break;
                                                }
                                                return true;
                                            }
                                        });

                                        if (currentMmodule.equals("3") && group.equals("2")) {

                                            iv_juzi.setOnTouchListener(null);
                                            iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentSentenceGroupScene = "2";
                                                                currentSentenceGroupLength = 0;
                                                                currentSentenceGroupGroupId = "";

                                                                getSentencegroup();
                                                            }
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });
                                        }
                                    }

                                    for (int i = 0; i < model.getGroupTraining().size(); i++) {
//                                        data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义
                                        LookLatelyBean.GroupTrainingBean groupTrainingBean = model.getGroupTraining().get(i);
                                        String module = groupTrainingBean.getModule();
                                        String scene = groupTrainingBean.getScene();
                                        int length = groupTrainingBean.getLength();
                                        String groupId = "";
                                        groupId = groupTrainingBean.getId() + "";

                                        if (module.equals("1") && scene.equals("3")) {//名词的历史记录
                                            String finalGroupId11 = groupId;

                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                if (isCanlIntoNextActivity())
                                                                    return true;

                                                                Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                                                                intent.putExtra("groupId", finalGroupId11 + "");
                                                                intent.putExtra("position", length);
                                                                if (isNetConnect) {
                                                                    startActivity(intent);
                                                                } else {
                                                                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                                                                }
                                                            }
                                                    }
                                                    return true;
                                                }
                                            });

                                        } else if (module.equals("1")) {
                                            String finalGroupId = groupId;
//
                                            iv_mingci.setOnTouchListener(null);
                                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentNounScene = scene;
                                                                currentNounLength = length;
                                                                currentNounGroupId = finalGroupId + "";

                                                                getNoun();
                                                            }
                                                    }
                                                    return true;
                                                }
                                            });
                                        }

                                        if (module.equals("2")) {//动词的历史记录
                                            String finalGroupId = groupId;

                                            iv_dongci.setOnTouchListener(null);
                                            iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentVerbScene = scene;
                                                                currentVerbLength = length;
                                                                currentVerbGroupId = finalGroupId;

                                                                getVerb();
                                                            }

                                                    }
                                                    return true;
                                                }
                                            });
                                        }

                                        if (module.equals("3")) {
                                            String finalGroupId = groupId;

                                            iv_juzi.setOnTouchListener(null);
                                            iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {
                                                                currentSentenceGroupScene = scene;
                                                                currentSentenceGroupLength = length;
                                                                currentSentenceGroupGroupId = finalGroupId;

                                                                getSentencegroup();

                                                            }
                                                    }
                                                    return true;
                                                }
                                            });

                                        } else if (module.equals("4")) {

                                            String finalGroupId1 = groupId;

                                            iv_juzi.setOnTouchListener(null);
                                            iv_juzi.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                                @Override
                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                    ImageView imageView = ((ImageView) view);
                                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                                    switch (motionEvent.getAction()) {

                                                        case MotionEvent.ACTION_UP:
                                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                                return true;
                                                            }
                                                            int x = (int) motionEvent.getX();
                                                            int y = (int) motionEvent.getY();
                                                            if (x > bitmap.getWidth()) {
                                                                x = bitmap.getWidth() - 1;
                                                            }
                                                            if (y > bitmap.getHeight()) {
                                                                y = bitmap.getHeight() - 2;
                                                            }
                                                            if (x < 0) {
                                                                x = 1;
                                                            }
                                                            if (y <= 0) {
                                                                y = 1;
                                                            }
                                                            int pixel = bitmap.getPixel(x, y);
                                                            int redValue = Color.red(pixel);
                                                            Log.e("redValue", "redValue" + redValue + "");
                                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                                            } else {

                                                                currentSentenceDecompositionScene = scene;
                                                                currentSentenceDecompositionLength = length;
                                                                currentSentenceDecompositionGroupId = finalGroupId1;

                                                                getSentenceResolve();
                                                            }
                                                    }
                                                    return true;
                                                }
                                            });
                                        }
                                    }// model.getGroupTraining().size()  for循环结束
//
                                }
                            }
                        }//data是0
                    } catch (JSONException e) {//这一般不会走的
                        e.printStackTrace();
                        String s = e.toString();

                        if (s.contains("No value for data")) {
//                        没有完善儿童信息
                            // iv_mingci.setClickable(true);
                            iv_mingci.setTag(true);
                            //iv_dongci.setClickable(false);
                            iv_dongci.setTag(false);
                            // iv_juzi.setClickable(false);
                            iv_juzi.setTag(false);
                            iv_dongci.setImageResource(R.drawable.dongci_dark);
                            iv_juzi.setImageResource(R.drawable.juzi_dark);

                            if (null != cuttentTransYAnim) {
                                cuttentTransYAnim.end();
                            }
                            startFlutterAnimator(iv_mingci, 1);

                            iv_dongci.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (view.getTag() != null && !(boolean) view.getTag()) {
                                        ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                        return;
                                    }
                                    showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                }
                            });

                            iv_juzi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (view.getTag() != null && !(boolean) view.getTag()) {
                                        ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                        return;
                                    }
                                    showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                }
                            });

                            iv_mingci.setOnTouchListener(null);
                            iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    ImageView imageView = ((ImageView) view);
                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                    switch (motionEvent.getAction()) {

                                        case MotionEvent.ACTION_UP:
                                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                                return true;
                                            }
                                            int x = (int) motionEvent.getX();
                                            int y = (int) motionEvent.getY();
                                            if (x > bitmap.getWidth()) {
                                                x = bitmap.getWidth() - 1;
                                            }
                                            if (y > bitmap.getHeight()) {
                                                y = bitmap.getHeight() - 2;
                                            }
                                            if (x < 0) {
                                                x = 1;
                                            }
                                            if (y <= 0) {
                                                y = 1;
                                            }
                                            int pixel = bitmap.getPixel(x, y);
                                            int redValue = Color.red(pixel);
                                            Log.e("redValue", "redValue" + redValue + "");
                                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                            } else {
                                                if (isCanlIntoNextActivity()) return true;

                                                PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
                                                helper.saveInt("sp", "mingciJinbi_tiyan", 0);//金币

                                                Intent intent1 = new Intent(BalloonActivity.this, MingciOneExperienceActivity.class);
                                                intent1.putExtra("position", 0);
                                                if (isNetConnect) {
                                                    startActivity(intent1);
                                                    // finish();
                                                } else {
                                                    ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");

                                                }
                                            }
                                            break;
                                    }
                                    return true;
                                }
                            });
                            return;
                        }
                    }
                }
            });
        }

    }

    private void intoFirstTestDongci() {
        iv_dongci.setOnTouchListener(null);
        iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            currentVerbScene = "2";
                            currentVerbLength = 0;
                            currentVerbGroupId = "";

                            getVerb();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void intoCommonDongci() {
        iv_dongci.setOnTouchListener(null);
        iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            if (isCanlIntoNextActivity())
                                return true;

                            Intent intent11 = new Intent(BalloonActivity.this, DongciTrainOneActivity.class);
                            if (isNetConnect) {
                                startActivity(intent11);
                            } else {
                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void intoCommonMingciIdea() {
        iv_mingci.setOnTouchListener(null);
        iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            if (isCanlIntoNextActivity())
                                return true;

                            PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
                            helper.saveString("xiaoyudi", "currentMingciIdeaNumber", "0");

                            Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
                            if (isNetConnect) {
                                startActivity(intent);
                            } else {
                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void intoFirstTestMingci() {
        iv_mingci.setOnTouchListener(null);
        iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            currentNounScene = "2";
                            currentNounLength = 0;
                            currentNounGroupId = "";

                            getNoun();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void intoCommonMingci() {
        iv_mingci.setOnTouchListener(null);
        iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            if (isCanlIntoNextActivity())
                                return true;

                            Intent intent1 = new Intent(BalloonActivity.this, MingciOneActivity.class);
                            if (isNetConnect) {
                                startActivity(intent1);
                            } else {
                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    //是否能进入下一个页面
    private boolean isCanlIntoNextActivity() {
        if (!isNetConnect) {
            return false;
        }

        if (isIntoCoursewareActivity) {
            return true;
        }
        isIntoCoursewareActivity = true;
        return false;
    }

    private void dongCiBtnClickEvent() {
        iv_dongci.setOnTouchListener(null);
        iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView imageView = ((ImageView) view);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        if (view.getTag() != null && !(boolean) view.getTag()) {
                            ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                            return true;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (x > bitmap.getWidth()) {
                            x = bitmap.getWidth() - 1;
                        }
                        if (y > bitmap.getHeight()) {
                            y = bitmap.getHeight() - 2;
                        }
                        if (x < 0) {
                            x = 1;
                        }
                        if (y <= 0) {
                            y = 1;
                        }
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        Log.e("redValue", "redValue" + redValue + "");
                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                        } else {
                            if (isCanlIntoNextActivity())
                                return true;
                            Intent intent11 = new Intent(BalloonActivity.this, DongciTrainOneActivity.class);
                            if (isNetConnect) {
                                startActivity(intent11);
                            } else {
                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
                            }
                        }
                        break;
                }
                return true;
            }
        });

        String verb = model.getList().getVerb();
        if (verb.equals("2")) {

            iv_dongci.setOnTouchListener(null);
            iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ImageView imageView = ((ImageView) view);
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    switch (motionEvent.getAction()) {

                        case MotionEvent.ACTION_UP:
                            if (view.getTag() != null && !(boolean) view.getTag()) {
                                ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                return true;
                            }
                            int x = (int) motionEvent.getX();
                            int y = (int) motionEvent.getY();
                            if (x > bitmap.getWidth()) {
                                x = bitmap.getWidth() - 1;
                            }
                            if (y > bitmap.getHeight()) {
                                y = bitmap.getHeight() - 2;
                            }
                            if (x < 0) {
                                x = 1;
                            }
                            if (y <= 0) {
                                y = 1;
                            }
                            int pixel = bitmap.getPixel(x, y);
                            int redValue = Color.red(pixel);
                            Log.e("redValue", "redValue" + redValue + "");
                            if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                            } else {
                                currentVerbScene = "2";
                                currentVerbLength = 0;
                                currentVerbGroupId = "";

                                getVerb();
                            }
                            break;
                    }
                    return true;
                }
            });
        }

        for (int i = 0; i < model.getGroupTraining().size(); i++) {
//                             data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
//                            scene	是	string	学习场景 1训练 2测试 3意义
            LookLatelyBean.GroupTrainingBean groupTrainingBean = model.getGroupTraining().get(i);
            String module = groupTrainingBean.getModule();
            String scene = groupTrainingBean.getScene();
            int length = groupTrainingBean.getLength();
            String groupId = "";
            groupId = groupTrainingBean.getId() + "";

            if (module.equals("2")) {
                String finalGroupId = groupId;
//
                iv_dongci.setOnTouchListener(null);
                iv_dongci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ImageView imageView = ((ImageView) view);
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                        switch (motionEvent.getAction()) {

                            case MotionEvent.ACTION_UP:
                                if (view.getTag() != null && !(boolean) view.getTag()) {
                                    ToastUtil.showToast(BalloonActivity.this, "暂未解锁");
                                    return true;
                                }
                                int x = (int) motionEvent.getX();
                                int y = (int) motionEvent.getY();
                                if (x > bitmap.getWidth()) {
                                    x = bitmap.getWidth() - 1;
                                }
                                if (y > bitmap.getHeight()) {
                                    y = bitmap.getHeight() - 2;
                                }
                                if (x < 0) {
                                    x = 1;
                                }
                                if (y <= 0) {
                                    y = 1;
                                }
                                int pixel = bitmap.getPixel(x, y);
                                int redValue = Color.red(pixel);
                                Log.e("redValue", "redValue" + redValue + "");
                                if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {

                                } else {
                                    currentVerbScene = scene;
                                    currentVerbLength = length;
                                    currentVerbGroupId = finalGroupId;

                                    getVerb();
                                }
                                break;
                        }
                        return true;
                    }
                });

                break;
            }
        }

    }

    private void startFlutterAnimator(ImageView view, int i) {
        int screenHeight = MyUtils.getScreenHeight(this);

        float v = 0;

        if (i == 1) {
            v = -(screenHeight / 3.6f);
        } else if (i == 2) {
            v = -(screenHeight / 6.0f);
        } else {
            v = -(screenHeight / 2.2f);
        }
        cuttentTransYAnim = ObjectAnimator.ofFloat(view, "translationY", v + 10, v - 10);
        cuttentTransYAnim.setDuration(2000);
        cuttentTransYAnim.setRepeatMode(ObjectAnimator.REVERSE);
        cuttentTransYAnim.setRepeatCount(-1);
        cuttentTransYAnim.start();

    }


    private void initAnimator() {
        iv_mingci.setImageResource(R.drawable.mingci_yellow);
        iv_dongci.setImageResource(R.drawable.dongci_yellow);
        iv_juzi.setImageResource(R.drawable.juzi_yellow);


        int screenWidth = MyUtils.getScreenWidth(this);
        int screenHeight = MyUtils.getScreenHeight(this);

        int marginleft = MyUtils.dip2px(this, 90);
        flutterHeight = screenHeight / 3.6f;
//ImageView view, float x, float y, long duration,final int position
        startAnimator(iv_mingci, marginleft + screenWidth / 12, -(screenHeight / 3.6f), 1300, 0);
        startAnimator(iv_dongci, marginleft + screenWidth / 12 * 4.3f, -(screenHeight / 6.0f), 1700, 1);
        startAnimator(iv_juzi, (float) (marginleft + screenWidth / 12 * 7.4), -(screenHeight / 2.2f), 2000, 2);
    }

    private void startAnimator(ImageView view, float x, float y, long duration,
                               final int position) {
//        translationX：下个位置大于上个上个位置时，向右移动，反之向左移动；
//        translationY：下个位置大于上个上个位置时，向下移动，反之向上移动。
//        translationX：0f-> -300f，向左；-300f-> 0f，向右。
//        在2s内，沿x轴左移300个像素，然后再右移300个像素
//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -300f, 0f);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(view, "translationX", 0, x);
        transXAnim.setDuration(duration);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", 0, y);
        transYAnim.setDuration(duration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(transXAnim, transYAnim);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (position == 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//
                        }
                    }, 500);
                }
            }
        });
    }

    private void smallAnima(ImageView iv_mingci) {
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_mingci, "scaleX", 1.0f, 0.90909f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_mingci, "scaleY", 1.0f, 0.90909f);

        animatorSetsuofang.setDuration(500);
//        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());//动画不设置Interpolator属性即为默认值，匀速
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();
    }

    private void bigAnima(ImageView iv_mingci) {
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画  1.1f);  0.90909f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_mingci, "scaleX", 1f, 1.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_mingci, "scaleY", 1f, 1.1f);

        animatorSetsuofang.setDuration(500);
//        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());//动画不设置Interpolator属性即为默认值，匀速
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();
    }


    public void getIsRemindDialog(String token) {
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

//                                如果需要弹框提醒判断提醒类型( 1 :正常定时提醒2:通关提醒)
                                remindType = assementReviewBean.getRemindType();
                                Log.i("liubiao", "完善状态值"+assementReviewBean.getIsRemind());
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    String message = getResources().getString(R.string.new_password);
                                    Spanned ss = Html.fromHtml(message);
                                    // 未完善儿童信息，弹框让完善儿童信息
                                    showIsRemindDialog("完善训练儿童信息", messageRemind, "去完善");
                                    isFinished = false;
                                } else if ("2".equals(assementReviewBean.getIsRemind())) {
                                    Log.i("liubiao", "进来完善赋值里面"+assementReviewBean.getIsRemind());

                                    isFinished = true;

                                    if ("0".equals(assementReviewBean.getIsRecord())) {
                                        // 没有填写过问卷，弹框完善问卷
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    } else if ("1".equals(assementReviewBean.getIsRecord())) {
                                        if ("1".equals(assementReviewBean.getAbcIsRemind()) || "1".equals(assementReviewBean.getPcdiIsRemind())||
                                                "3".equals(assementReviewBean.getAbcIsRemind()) || "3".equals(assementReviewBean.getPcdiIsRemind())) {
                                            // 月度弹框，
                                            if ("1".equals(assementReviewBean.getRemindType())) {
                                                showIsRemindDialog("定期问卷评估提醒", "messageMonth", "去评估");
                                            } else if ("2".equals(assementReviewBean.getRemindType())) {// 通关填写问卷提醒
                                                showPingguDialog();
                                            }
                                        }
                                        isFinished = true;

                                    }


                                }
                            }
                        } else if (assementReviewBeanBaseBean.code == 205 || assementReviewBeanBaseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(BalloonActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


    private void showIsRemindDialog(String title, String message, String aboutbutton) {
        int height1 = MyUtils.dip2px(BalloonActivity.this, 270);

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_isremind)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, height1)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();

        TextView tvTitle = dialog.getView(R.id.tv_title);
        TextView tvMessage = dialog.getView(R.id.tv_message);
        TextView tvCommit = dialog.getView(R.id.tv_commit);

        tvTitle.setText(title);
        if ("messageMonth".equals(message)) {
            tvMessage.setText(Html.fromHtml(getResources().getString(R.string.messagemonth, tvName.getText().toString())));
        } else {
            tvMessage.setText(message);
        }
        tvCommit.setText(aboutbutton);


        dialog.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("去完善".equals(aboutbutton)) {
                    Intent intent = new Intent(BalloonActivity.this, PerfectionChildrenInfoActivity.class);
                    intent.putExtra("registertype", "mainback");
                    startActivityForResult(intent, 300);
                    dialog.dismiss();
                } else if ("去评估".equals(aboutbutton)) {
                    Intent intent = new Intent(BalloonActivity.this, AssessActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK) {
            Log.e("initNameonactivity", tvName.getText().toString());
            initPicName();
        }
    }


    private void showFamilyDialog() {
//        1~100之间的，随机的
        int height1 = MyUtils.dip2px(BalloonActivity.this, 270);

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_family1)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, height1)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();

//        家长中心答题窗口：系统随机出（5×5到15×15）以内的乘除法整除题目
//        该值大于等于0.0且小于1.0，即取值范围是[0.0,1.0)的左闭右开区间
        x = (int) (Math.random() * 11 + 5);
        y = (int) (Math.random() * 11 + 5);

        TextView tv_design_formulas = (TextView) dialog.getView(R.id.tv_design_formulas);
        tv_design_formulas.setText(x + " x " + y + " =  ? ");

        EditText et_daan = (EditText) dialog.getView(R.id.et_daan);

        dialog.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_daan.getText().toString())) {
                    String trim = et_daan.getText().toString().trim();

                    if ((x * y + "").equals(trim)) {
                        Intent intent = new Intent(BalloonActivity.this, MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        ToastUtil.showToast(mContext, "输入错误");
                    }
//                    // TODO: 2018/11/3 0003
//                    Intent intent = new Intent(BalloonActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                removeAllActivitys();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

    }

    /**
     * 名词
     */
    private void getVerb() {
        PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
        String token = helper.getToken();

        String url = Setting.getVerb();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(BalloonActivity.this, this));
    }

    /**
     * 名词
     */
    private void getNoun() {
        PreferencesHelper helper = new PreferencesHelper(BalloonActivity.this);
        String token = helper.getToken();
        Log.e("数据", "token" + token);
        String url = Setting.getNoun();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(BalloonActivity.this, this));
    }

    /**
     * 句子成组
     */
    private void getSentencegroup() {
        String url = Setting.getSentencegroup();
//        sentenceGroupTraining	Object	句子成组训练
//        sentenceGroupTest	Object	句子成组测试
//        helptime	Object	辅助时长
        OkHttpUtils
                .post()
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .tag(BalloonActivity.this)
                .build()
                .execute(new BaseStringCallback_Host(BalloonActivity.this, this));
    }

    private void getSentenceResolve() {
        String url = Setting.getSentenceResolve();
        OkHttpUtils
                .post()
                .url(url)//接口地址
                .id(5)//XX接口的标识
                .tag(BalloonActivity.this)
                .build()
                .execute(new BaseStringCallback_Host(BalloonActivity.this, this));
    }

}
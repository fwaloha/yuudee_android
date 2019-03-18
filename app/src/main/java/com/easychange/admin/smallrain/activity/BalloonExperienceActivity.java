package com.easychange.admin.smallrain.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.base.BaseDialog;
import com.easychange.admin.smallrain.entity.NetChangeBean;
import com.easychange.admin.smallrain.receiver.NetReceiver;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.utils.MyUtils;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import bean.AssementReviewBean;
import bean.ChildMessageBean;
import bean.ExitLoginBean;
import bean.HeadChangeBean;
import bean.LookLatelyBean;
import bean.LookLatelyTwoBean;
import bean.NickNameChangeBean;
import bean.PushBean;
import bean.RegisterDestroyOtherBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dongci.DongciTrainOneActivity;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import juzi.JuZiChengZuXunLianActivity;
import juzi.JuZiFeiJieXunLianActivityFourClick;
import mingci.MingciIdeaOneActivity;
import mingci.MingciOneActivity;
import mingci.MingciOneExperienceActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * describe:  儿童首页
 */
public class BalloonExperienceActivity extends BaseActivity {
//18811496406   qwe123
//    18135697075   a123456

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
    private PreferencesHelper helper;
    //    private int currentClickPosition = 0;
    private String module = "";
    private String messageRemind = "因为每个孩子的能力不同，为了使每个孩子都按照自己的节奏进步," +
            "新雨滴提供了个性化的训练和指导方案，在继续学习前，需要了" +
            "解孩子的个人信息和基础能力，请家长先完善训练儿童个人信息和问卷评估。";
    private String messageAbc = "因为每个孩子的能力不同，为了使每个孩子都按照自己的节奏进步," +
            "新雨滴提供了个性化的训练和指导方案，在继续学习前，需要了" +
            "解孩子的基础能力，请家长先完成问卷评估。";
    private boolean isFirstregister;
    private Boolean isIntoCoursewareActivity = false;//是否进入课件页面
    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();
    boolean isNetConnect = true;
    boolean isShowAccess = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balloon);
        ButterKnife.bind(this);
        Log.e("myactivity", "BalloonActivity");
        isFirstregister = getIntent().getBooleanExtra("isFirstregister", false);

        initPicName();
        initAnimator();
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, permissions, 1001);
        }
        application11 = (MyApplication) getApplication();
        EventBusUtil.register(this);
        //        网络监听
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    //是否能进入下一个页面
    private boolean isCanlIntoNextActivity() {
        if(!isNetConnect){
            return false;
        }

        if (isIntoCoursewareActivity) {
            return true;
        }
        isIntoCoursewareActivity = true;
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RegisterDestroyOtherBean(RegisterDestroyOtherBean event) {
        finish();
    }
    public void initPicName() {
        getChildMessageBean(new PreferencesHelper(BalloonExperienceActivity.this).getToken());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(NetChangeBean event) {//断网
        isNetConnect = event.netStatus;
        if (!mReceiver.isConnected()){
            ToastUtil.showToast(this,"当前网络已断开");
        }else {
            isIntoCoursewareActivity=false;
        }
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
        }
        if (TextUtils.isEmpty(childMessageBean.getXydChild().getPhoto())) {
            String chilSex = childMessageBean.getXydChild().getSex();
            if ("0".equals(chilSex)) {//儿童性别（0：男 1：女）
                new PreferencesHelper(BalloonExperienceActivity.this).saveSex("0");
                GlideUtil.display(this, R.drawable.nan111, ivHead);
            } else {
                new PreferencesHelper(BalloonExperienceActivity.this).saveSex("1");
                GlideUtil.display(this, R.drawable.nv111, ivHead);
            }
        } else {
            String photo = childMessageBean.getXydChild().getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。

//            Log.e("photourl1", photo);

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

            if (TextUtils.isEmpty(myhelper.getPhoto())) {
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
                                setPicNameFromLocal(new PreferencesHelper(BalloonExperienceActivity.this));
                            }
                        } else {
                            setPicNameFromLocal(new PreferencesHelper(BalloonExperienceActivity.this));
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
        String string = new PreferencesHelper(BalloonExperienceActivity.this).getString("sp", "nickname",
                "");
        if (!TextUtils.isEmpty(string) && string != null) {
            tvName.setText(string);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HeadChangeBean(HeadChangeBean event) {
        String photo = new PreferencesHelper(BalloonExperienceActivity.this).getPhoto();
        GlideUtil.display(this, photo, ivHead);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HeadChangeBean(RegisterDestroyOtherBean event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PushBean(PushBean event) {
        Intent intent = new Intent(BalloonExperienceActivity.this, AssessActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isShowAccess) {
            if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
                getIsRemindDialog(new PreferencesHelper(this).getToken());
            }
        }
        isShowAccess = false ;
//        iv_dongci.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(view.getTag()!=null &&!(boolean) view.getTag()){
//
//                    return;
//                }
//                showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
//            }
//        });
//
//        iv_juzi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(view.getTag()!=null &&!(boolean) view.getTag()){
//                    ToastUtil.showToast(BalloonActivity.this,"暂未解锁");
//                    return;
//                }
//                showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
//            }
//        });
    }

    @OnClick({R.id.iv_head, R.id.ll_jiazhang, R.id.iv_mingci, R.id.iv_dongci, R.id.iv_juzi})
    public void onClick(View view) {
        boolean isTwentyMinutes = application11.isTwentyMinutes;

        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.ll_jiazhang:
                if (isNetConnect){
                    showFamilyDialog();
                }else {
                    ToastUtil.showToast(BalloonExperienceActivity.this,"当前网络已断开");
                }
                break;
            case R.id.iv_mingci:
                if (isCanlIntoNextActivity()) return;

                PreferencesHelper helper = new PreferencesHelper(BalloonExperienceActivity.this);
                helper.saveInt("sp", "mingciJinbi_tiyan", 0);//金币

                Intent intent1 = new Intent(BalloonExperienceActivity.this, MingciOneExperienceActivity.class);
                intent1.putExtra("position", 0);
                if (isNetConnect) {
                    startActivity(intent1);
                    finish();
                }else {
                    ToastUtil.showToast(BalloonExperienceActivity.this,"当前网络已断开");
                }
                break;
            case R.id.iv_dongci:
                ToastUtil.showToast(BalloonExperienceActivity.this,"暂未解锁");
               // showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                break;
            case R.id.iv_juzi:
                ToastUtil.showToast(BalloonExperienceActivity.this,"暂未解锁");
               // showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                break;
        }
    }


    private void startFlutterAnimator(ImageView view) {
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", -flutterHeight, -flutterHeight - 30);
        transYAnim.setDuration(1000);
        transYAnim.setRepeatMode(ObjectAnimator.REVERSE);
        transYAnim.setRepeatCount(-1);
        transYAnim.start();
    }

    private void startAnimator(ImageView view, float x, float y, long duration, final int position) {
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
                            startFlutterAnimator(iv_mingci);
                        }
                    }, 500);
                }
            }
        });
    }

    private void initAnimator() {
        iv_mingci.setImageResource(R.drawable.mingci_yellow);

        iv_dongci.setImageResource(R.drawable.dongci_dark);
        iv_juzi.setImageResource(R.drawable.juzi_dark);
        int screenWidth = MyUtils.getScreenWidth(this);
        int screenHeight = MyUtils.getScreenHeight(this);
        int marginleft = MyUtils.dip2px(this, 100);
        flutterHeight = screenHeight / 3.6f;
        startAnimator(iv_mingci, marginleft + screenWidth / 11, -(screenHeight / 3.6f), 1300, 0);
        startAnimator(iv_dongci, marginleft + screenWidth / 11 * 4.3f, -(screenHeight / 5.0f), 1700, 1);
        startAnimator(iv_juzi, marginleft + screenWidth / 11 * 7, -(screenHeight / 2.2f), 2000, 2);
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
//                                是否需要完善儿童个人信息(1:未完善 2:已完善)
                                if ("1".equals(assementReviewBean.getIsRemind())) {
                                    String message = getResources().getString(R.string.new_password);
                                    Spanned ss = Html.fromHtml(message);
                                    // 未完善儿童信息，弹框让完善儿童信息
                                    showIsRemindDialog("完善训练儿童信息", messageRemind, "去完善");

                                } else if ("2".equals(assementReviewBean.getIsRemind())) {

                                    if ("0".equals(assementReviewBean.getIsRecord())) {
                                        // 没有填写过问卷，弹框完善问卷
                                        showIsRemindDialog("问卷评估提醒", messageAbc, "去评估");
                                    } else if ("1".equals(assementReviewBean.getIsRecord())) {
                                        if ("1".equals(assementReviewBean.getAbcIsRemind()) || "1".equals(assementReviewBean.getPcdiIsRemind())) {
                                            // 月度弹框，
                                            showIsRemindDialog("定期问卷评估提醒", "messageMonth", "去评估");
                                        }
                                    }

                                }

                            }
                        }else if (assementReviewBeanBaseBean.code ==205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(BalloonExperienceActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


    private void showIsRemindDialog(String title, String message, String aboutbutton) {
        int height1 = MyUtils.dip2px(BalloonExperienceActivity.this, 270);

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
                    Intent intent = new Intent(BalloonExperienceActivity.this, PerfectionChildrenInfoActivity.class);
                    intent.putExtra("registertype", "mainback");
                    startActivityForResult(intent, 300);
                    dialog.dismiss();
                } else if ("去评估".equals(aboutbutton)) {
                    Intent intent = new Intent(BalloonExperienceActivity.this, AssessActivity.class);
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
            initPicName();
        }
    }

    private void showFamilyDialog() {
//        1~100之间的，随机的
        int height1 = MyUtils.dip2px(BalloonExperienceActivity.this, 270);

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
                        if (isFirstregister) {
                            Intent intent = new Intent(BalloonExperienceActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else {
                        ToastUtil.showToast(mContext, "输入错误");
                    }

                    dialog.dismiss();
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
}

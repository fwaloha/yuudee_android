package com.easychange.admin.smallrain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import bean.ToyListBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;

/**
 * 拼图页面  家长体验
 */
public class PinTuTiYanActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_pin1)
    ImageView ivPin1;
    @BindView(R.id.iv_pin2)
    ImageView ivPin2;
    @BindView(R.id.iv_pin3)
    ImageView ivPin3;
    @BindView(R.id.iv_pin4)
    ImageView ivPin4;
    @BindView(R.id.iv_pin5)
    ImageView ivPin5;
    @BindView(R.id.iv_pin6)
    ImageView ivPin6;
    @BindView(R.id.iv_pin7)
    ImageView ivPin7;
    @BindView(R.id.iv_pin8)
    ImageView ivPin8;
    @BindView(R.id.iv_pin9)
    ImageView ivPin9;
    @BindView(R.id.iv_gif)
    ImageView iv_gif;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.iv_xiaolian)
    ImageView ivXiaolian;
    private int anInt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_tu);
        ButterKnife.bind(this);
        ivHome.setOnClickListener(this);

        MyApplication application = (MyApplication) getApplication();
        application.isBunching = true;
        PreferencesHelper helper = new PreferencesHelper(PinTuTiYanActivity.this);

        anInt1 = getIntent().getIntExtra("anInt1", 0);
        tvMoney.setText("x  " + anInt1);
//        Glide.with(this).load(R.drawable.pintu_bg0).into(iv_gif);
        setGifAnimation();

        feichu(1);
    }

    private int N = 10;

    private void setGifAnimation() {
        //随机产生一个大于等于0不等于500（不包含500）的正整数
//        Random generate = new Random();
//        int nextInt = generate.nextInt(500);

        //生成0-N之间的随机数，包括0，不包括N。
        Random rand = new Random();
        int currentGifCornerMarkInt = rand.nextInt(N);

        int currentPintuId = 0;
        if (currentGifCornerMarkInt == 0) {
            currentPintuId = R.drawable.pintu_bg0;
        } else if (currentGifCornerMarkInt == 1) {
            currentPintuId = R.drawable.pintu_bg1;
        } else if (currentGifCornerMarkInt == 2) {
            currentPintuId = R.drawable.pintu_bg2;
        } else if (currentGifCornerMarkInt == 3) {
            currentPintuId = R.drawable.pintu_bg3;
        } else if (currentGifCornerMarkInt == 4) {
            currentPintuId = R.drawable.pintu_bg4;
        } else if (currentGifCornerMarkInt == 5) {
            currentPintuId = R.drawable.pintu_bg5;
        } else if (currentGifCornerMarkInt == 6) {
            currentPintuId = R.drawable.pintu_bg6;
        } else if (currentGifCornerMarkInt == 7) {
            currentPintuId = R.drawable.pintu_bg7;
        } else if (currentGifCornerMarkInt == 8) {
            currentPintuId = R.drawable.pintu_bg8;
        } else if (currentGifCornerMarkInt == 9) {
            currentPintuId = R.drawable.pintu_bg9;
        }

        Glide.with(this).load(currentPintuId).into(iv_gif);
    }

    private void feichu(int dangqian_pintu_xiabiao) {

        for (int i = 1; i < 10; i++) {

            if (dangqian_pintu_xiabiao > i) {
                if (i == 1) {
                    ivPin1.setVisibility(View.GONE);
                } else if (i == 2) {
                    ivPin2.setVisibility(View.GONE);
                } else if (i == 3) {
                    ivPin3.setVisibility(View.GONE);
                } else if (i == 4) {
                    ivPin4.setVisibility(View.GONE);
                } else if (i == 5) {
                    ivPin5.setVisibility(View.GONE);
                } else if (i == 6) {
                    ivPin6.setVisibility(View.GONE);
                } else if (i == 7) {
                    ivPin7.setVisibility(View.GONE);
                } else if (i == 8) {
                    ivPin8.setVisibility(View.GONE);
                } else {
                    ivPin9.setVisibility(View.GONE);
                }
            }

        }
        ImageView ivPin;
        if (dangqian_pintu_xiabiao == 1) {
            ivPin = ivPin1;
        } else if (dangqian_pintu_xiabiao == 2) {
            ivPin = ivPin2;
        } else if (dangqian_pintu_xiabiao == 3) {
            ivPin = ivPin3;
        } else if (dangqian_pintu_xiabiao == 4) {
            ivPin = ivPin4;
        } else if (dangqian_pintu_xiabiao == 5) {
            ivPin = ivPin5;
        } else if (dangqian_pintu_xiabiao == 6) {
            ivPin = ivPin6;
        } else if (dangqian_pintu_xiabiao == 7) {
            ivPin = ivPin7;
        } else if (dangqian_pintu_xiabiao == 8) {
            ivPin = ivPin8;
        } else {
            ivPin = ivPin9;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator obx = ObjectAnimator.ofFloat(ivPin, "translationX", 1600);
                ObjectAnimator oby = ObjectAnimator.ofFloat(ivPin, "translationY", -1600);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(obx, oby);
                set.setDuration(2000);
                set.start();

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation, boolean isReverse) {


                    }
                });

            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMoney.setText("x " +0);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(PinTuTiYanActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        }, 30 * 1000);//7.强化物页面应该停留30s

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                finish();
                break;
        }
    }

    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(1)) {//标记那个接口

            String result = (String) data;
            (PinTuTiYanActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            ToyListBean model = gson.fromJson(result,
                                    new TypeToken<ToyListBean>() {
                                    }.getType());
//                            他写的这个数组,有几个元素,那么当前就剩余几块积木,number代表当前剩余的积木的编号,编号为1-9


                            int size = model.getData().size();
                            int i = 10 - size;
                            feichu(i);

                        } else {
                        }
                        ToastUtil.showToast(PinTuTiYanActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//修改儿童积木状态
            String result = (String) data;
            (PinTuTiYanActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                        }
                        ToastUtil.showToast(PinTuTiYanActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(3)) {//标记那个接口

            String result = (String) data;
            (PinTuTiYanActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//                            Gson gson = new Gson();
//                            ToyListBean model = gson.fromJson(result,
//                                    new TypeToken<ToyListBean>() {
//                                    }.getType());
////                            他写的这个数组,有几个元素,那么当前就剩余几块积木,number代表当前剩余的积木的编号,编号为1-9
//
//
//                            int size = model.getData().size();
//                            int i = 10 - size;
//                            feichu(i);

                        }
                        ToastUtil.showToast(PinTuTiYanActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

    }
}

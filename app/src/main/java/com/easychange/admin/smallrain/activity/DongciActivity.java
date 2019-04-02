package com.easychange.admin.smallrain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.AnimationHelper;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.IndicatorView;
import com.easychange.admin.smallrain.views.WaveCircleView;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;

public class DongciActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {

    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_content1)
    ImageView ivContent1;
    @BindView(R.id.iv_content2)
    ImageView ivContent2;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_paint)
    TextView tvPaint;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.fl_root)
    FrameLayout flRoot;
    @BindView(R.id.iv_choose1)
    ImageView ivChoose1;
    @BindView(R.id.tv_choose1)
    TextView tvChoose1;
    @BindView(R.id.ll_choose1)
    LinearLayout llChoose1;
    @BindView(R.id.iv_choose2)
    ImageView ivChoose2;
    @BindView(R.id.tv_choose2)
    TextView tvChoose2;
    @BindView(R.id.ll_choose2)
    LinearLayout llChoose2;
    @BindView(R.id.waveCirlceView)
    WaveCircleView waveCirlceView;
    @BindView(R.id.waveCirlceView2)
    WaveCircleView waveCirlceView2;
    @BindView(R.id.fl_choose1)
    FrameLayout fl_choose1;
    @BindView(R.id.fl_choose2)
    FrameLayout fl_choose2;
    @BindView(R.id.iv_point)
    ImageView iv_point;
    @BindView(R.id.iv_point2)
    ImageView iv_point2;
    private AnimationDrawable frameAnim;
    private int PROGRESS;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                PROGRESS++;
                if (PROGRESS == 3) {
                    if (frameAnim != null && !frameAnim.isRunning()) {
                        frameAnim.start();
                    }
                } else if (PROGRESS == 7) {
                    if (frameAnim != null && frameAnim.isRunning()) {
                        handler.removeMessages(1);
                        frameAnim.stop();
                        playLocalVoice("男-吃什么.MP3", false);
                        canClick = true;
                        return;
                    }
                }
                Message message = handler.obtainMessage(1);
                handler.sendMessageDelayed(message, 1000);
            }
        }
    };
    private MediaPlayer player;
    private int clickPosition;
    private boolean canClick = false;
    private int position;
    private List<Integer> imagesList = new ArrayList<>();
    private AnimationDrawable frameAnim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongci);
        ButterKnife.bind(this);
        fl_choose1.setOnClickListener(this);
        fl_choose2.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-吃.MP3", true);

            }
        }, 2000);

        frameAnim = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        frameAnim.addFrame(getResources().getDrawable(R.drawable.eat1), 500);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.eat2), 500);
        frameAnim.addFrame(getResources().getDrawable(R.drawable.eat3), 500);
        frameAnim.setOneShot(true);//设置帧动画是否只运行一次
        ivImg.setBackgroundDrawable(frameAnim);

        position = getIntent().getIntExtra("position", 0);
        initView();

        getVerb();
    }


    /**
     * 名词
     */
    private void getVerb() {
        PreferencesHelper helper = new PreferencesHelper(DongciActivity.this);
        String token = helper.getToken();
        Log.e("数据", "tokegetVerbn"+token);

        String url = Setting.getVerb();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(DongciActivity.this, this));
    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(1)) {//标记那个接口

            String result = (String) data;
            (DongciActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            JSONObject data1 = jsonObject.getJSONObject("data");
                            Log.e("数据", data1.toString());
                        } else {
                        }
                        ToastUtil.showToast(DongciActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {
        (DongciActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(DongciActivity.this, errorMessage);
            }
        });

    }

    private void initView() {
        if (position == 0) {

        } else if (position == 1) {
            llIndicator.setSelectedPosition(1);
            ivChoose1.setImageResource(R.drawable.binggan);
            ivChoose2.setImageResource(R.drawable.cai);
            tvChoose1.setText("饼干");
            tvChoose2.setText("菜");
        } else if (position == 2) {
            llIndicator.setSelectedPosition(2);
            ivChoose1.setImageResource(R.drawable.cao);
            ivChoose2.setImageResource(R.drawable.chong);
            tvChoose1.setText("草");
            tvChoose2.setText("虫");
        }
    }

    private void doAnim() {

        int duration = 0;
        for (int i = 0; i < frameAnim.getNumberOfFrames(); i++) {
            duration += frameAnim.getDuration(i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnim.stop();
                frameAnim.start();
            }
        }, duration);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnim.selectDrawable(0);      //选择当前动画的第一帧，然后停止
                frameAnim.stop();

                ivImg.setBackgroundDrawable(frameAnim);
                playLocalVoice("男-吃什么.MP3", false);

            }
        }, duration * 2);

        frameAnim.start();
    }

    private void startEat(List<Integer> images) {
        frameAnim1 = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        for (int i = 0; i < images.size(); i++) {
            frameAnim1.addFrame(getResources().getDrawable(images.get(i)), 500);
        }
        frameAnim1.setOneShot(false);
        ivImg.setBackgroundDrawable(frameAnim1);
        frameAnim1.start();
    }

    private void playLocalVoice(String videoName, boolean isFirst) {
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (isFirst) {
                        doAnim();
                    } else {
                        if (videoName.contains("吃什么")) {
                            canClick = true;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                finish();
                break;
            case R.id.fl_choose1:
                if (!canClick) {
                    return;
                }

                clickPosition = 0;
                if (position == 0) {
                    tvContent.setText("蛋糕");
                    playLocalVoice("男-蛋糕.MP3", false);
                } else if (position == 1) {
                    tvContent.setText("饼干");
                    playLocalVoice("男-饼干.MP3", false);
                } else if (position == 2) {
                    tvContent.setText("草");
                    playLocalVoice("男-草.MP3", false);
                }
                fl_choose1.setClickable(false);
                AnimationHelper.startScaleAnimation(mContext, fl_choose1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int screenWidth = MyUtils.getScreenWidth(mContext);
                        int screenHeight = MyUtils.getScreenHeight(mContext);
                        ObjectAnimator sax = ObjectAnimator.ofFloat(fl_choose1, "scaleX", 1f, 0.6f);
                        ObjectAnimator say = ObjectAnimator.ofFloat(fl_choose1, "scaleY", 1f, 0.4f);
                        int marginleft = (screenWidth - MyUtils.dip2px(MyApplication.getGloableContext(), 200)) / 2;
                        int x = screenWidth - marginleft - MyUtils.dip2px(MyApplication.getGloableContext(), 40 + 140 + 90 - (70 + 45));
                        ObjectAnimator obx = ObjectAnimator.ofFloat(fl_choose1, "translationX", x);
                        //因为图片透明边距的问题微调
                        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 350 + 20 + 140 + 55 - (70 + 27.5f))
                                - MyUtils.getStatusBarHeight(MyApplication.getGloableContext());
                        ObjectAnimator oby = ObjectAnimator.ofFloat(fl_choose1, "translationY", -y);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                tvContent.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tvContent, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();
                                fl_choose1.setVisibility(View.GONE);
                                ivContent2.setVisibility(View.INVISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mergeText();
                                    }
                                }, 1000);
                            }
                        });
                    }
                }, 1000);
                break;
            case R.id.fl_choose2:
                if (!canClick) {
                    return;
                }

                clickPosition = 1;
                if (position == 0) {
                    tvContent.setText("冰激凌");
                    playLocalVoice("男-冰激凌.MP3", false);
                } else if (position == 1) {
                    tvContent.setText("菜");
                    playLocalVoice("男-菜.MP3", false);
                } else if (position == 2) {
                    tvContent.setText("虫");
                    playLocalVoice("男-虫.MP3", false);
                }
                fl_choose2.setClickable(false);
                AnimationHelper.startScaleAnimation(mContext, fl_choose2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int screenWidth = MyUtils.getScreenWidth(mContext);
                        int screenHeight = MyUtils.getScreenHeight(mContext);
                        ObjectAnimator sax = ObjectAnimator.ofFloat(fl_choose2, "scaleX", 1f, 0.6f);
                        ObjectAnimator say = ObjectAnimator.ofFloat(fl_choose2, "scaleY", 1f, 0.4f);
                        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 350 + 20 + 140 + 55 - (70 + 27.5f))
                                - MyUtils.getStatusBarHeight(MyApplication.getGloableContext());
                        int x = MyUtils.dip2px(MyApplication.getGloableContext(), (140 - 90) / 2);
                        ObjectAnimator obx = ObjectAnimator.ofFloat(fl_choose2, "translationX", -x);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(fl_choose2, "translationY", -y);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                tvContent.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tvContent, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();
                                fl_choose2.setVisibility(View.GONE);
                                ivContent2.setVisibility(View.INVISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mergeText();
                                    }
                                }, 1000);
                            }
                        });
                    }
                }, 1000);
                break;
        }
    }

    private void mergeText() {
        if (position == 0) {
            if (clickPosition == 0) {
                imagesList.clear();
                imagesList.add(R.drawable.chidangao1);
                imagesList.add(R.drawable.chidangao2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            } else {
                imagesList.clear();
                imagesList.add(R.drawable.chibingjiling1);
                imagesList.add(R.drawable.chibingjiling2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            }
        } else if (position == 1) {
            if (clickPosition == 0) {
                imagesList.clear();
                imagesList.add(R.drawable.chibinggan1);
                imagesList.add(R.drawable.chibinggan2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            } else {
                imagesList.clear();
                imagesList.add(R.drawable.chicai1);
                imagesList.add(R.drawable.chicai2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            }
        } else if (position == 2) {
            if (clickPosition == 0) {
                imagesList.clear();
                imagesList.add(R.drawable.chicao1);
                imagesList.add(R.drawable.chicao2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            } else {
                imagesList.clear();
                imagesList.add(R.drawable.chichong1);
                imagesList.add(R.drawable.chichong2);
                imagesList.add(R.drawable.eat1);
                startEat(imagesList);
            }
        }

        if (position == 0) {
            if (clickPosition == 0) {
                playLocalVoice("男-吃蛋糕.MP3", false);
            } else {
                playLocalVoice("男-吃冰激凌.MP3", false);
            }
        } else if (position == 1) {
            if (clickPosition == 0) {
                playLocalVoice("男-吃饼干.MP3", false);
            } else {
                playLocalVoice("男-吃菜.MP3", false);
            }
        } else if (position == 2) {
            if (clickPosition == 0) {
                playLocalVoice("男-吃草.MP3", false);
            } else {
                playLocalVoice("男-吃虫.MP3", false);
            }
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvPaint.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tvPaint.setLayoutParams(layoutParams);
        tvPaint.setBackground(null);

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) tvContent.getLayoutParams();
        layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams2.leftMargin = 0;
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvContent.setLayoutParams(layoutParams2);
        tvContent.setBackground(null);

        tvPaint.post(new Runnable() {
            @Override
            public void run() {
                rlRoot.setBackgroundResource(R.drawable.painttext_bg);
                int i = (MyUtils.dip2px(DongciActivity.this, 190) - (tvPaint.getWidth() + tvContent.getWidth())) / 2;
                int paintX = MyUtils.dip2px(DongciActivity.this, 85) - tvPaint.getWidth();
                int contentX = MyUtils.dip2px(DongciActivity.this, 85 - 20) - tvContent.getWidth();
                TranslateAnimation tr = new TranslateAnimation(-contentX, -i, 0, 0);
                tr.setDuration(1000);
                tr.setFillAfter(true);
                tvContent.startAnimation(tr);

                TranslateAnimation tr2 = new TranslateAnimation(paintX, i, 0, 0);
                tr2.setDuration(1000);
                tr2.setFillAfter(true);
                tvPaint.startAnimation(tr2);
                tr2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlRoot.setBackground(null);
                        flRoot.setBackgroundResource(R.drawable.faguang_bg);
                        //发光背景缩小一些
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) flRoot.getLayoutParams();
                        layoutParams1.width = MyUtils.dip2px(MyApplication.getGloableContext(), 150);
                        flRoot.setLayoutParams(layoutParams1);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (position == 0) {
                                    Intent intent = new Intent(DongciActivity.this, DongciActivity.class);
                                    intent.putExtra("position", 1);
                                    startActivity(intent);
                                } else if (position == 1) {
                                    Intent intent = new Intent(DongciActivity.this, DongciActivity.class);
                                    intent.putExtra("position", 2);
                                    startActivity(intent);
                                } else if (position == 2) {
                                    Intent intent = new Intent(DongciActivity.this, LetsTestActivity.class);
                                    intent.putExtra("type", "dongci");
                                    startActivity(intent);
                                }
                                finish();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (player != null)
            player.stop();
        freeAnimationDrawable(frameAnim);
        freeAnimationDrawable(frameAnim1);
    }

    /**
     * 释放AnimationDrawable占用的内存
     *
     *
     * */
    private void freeAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable == null) return;
        animationDrawable.stop();
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); ++i){
            Drawable frame = animationDrawable.getFrame(i);
            if (frame instanceof BitmapDrawable) {
                ((BitmapDrawable)frame).getBitmap().recycle();
            }
            frame.setCallback(null);
        }

        animationDrawable.setCallback(null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

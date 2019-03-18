package com.easychange.admin.smallrain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.AnimationHelper;
import com.easychange.admin.smallrain.utils.GetDrawable;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.IndicatorView;
import com.easychange.admin.smallrain.views.WaveCircleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import bean.JuziBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;

/**
 * Created by chenlipeng on 2018/10/20 0020
 * describe:  句子结构页面练习
 */
public class JuZiExerciseActivityTemp extends BaseActivity implements AsyncRequest {


    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.ll_text_bg_parent)
    LinearLayout ll_text_bg_parent;
    @BindView(R.id.ll_text_parent)
    LinearLayout ll_text_parent;
    @BindView(R.id.ll_click_layout)
    LinearLayout llClickLayout;
    @BindView(R.id.ll_text_parent_bg)
    LinearLayout llTextParentBg;
    private MediaPlayer player;
    private AnimationDrawable frameAnim;

    private Handler handler = new Handler();
    private int currentSize = 2;
    //    private int clickPosition = 0;
    private View rightChildTextTwo;
    private View leftChildTextOne;

    private boolean isTwoMove = false;
    private boolean isOneMove = false;
    private TextView tv_content1;
    private TextView tv_content2;
    private int currentPracticePosition = 0;
    private AnimationDrawable frameAnim1;
    private List<JuziBean.SentenceGroupTrainingBean> sentenceGroupTraining;
    private JuziBean.SentenceGroupTrainingBean sentenceGroupTrainingBean;
    private int grapWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_zi);
        ButterKnife.bind(this);

        currentPracticePosition = getIntent().getIntExtra("currentPracticePosition", 0);
        grapWidth = MyUtils.dip2px(JuZiExerciseActivityTemp.this, 26);
//        if (currentPracticePosition == 0) {
////            String startSlideshow = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541750869&di=f2c590055e4eb2542d81bc0df0d1ef06&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F1%2F5791e7d3b1a02.jpg";
////            Drawable drawable = GetDrawable.loadImage(JuZiExerciseActivity.this, startSlideshow);
////            frameAnim1 = new AnimationDrawable();
//            // 为AnimationDrawable添加动画帧
//
////            frameAnim1.addFrame(drawable, 500);
////            frameAnim1.addFrame(drawable, 500);
////            frameAnim1.addFrame(drawable, 500);
////            frameAnim1 = new AnimationDrawable();
////            // 为AnimationDrawable添加动画帧
////            frameAnim1.addFrame(getResources().getDrawable(R.drawable.bingjiling2), 500);
////            frameAnim1.addFrame(getResources().getDrawable(R.drawable.bingjiling3), 500);
////            frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat1), 500);
//            frameAnim1.setOneShot(false);
//            ivImg.setBackgroundDrawable(frameAnim1);
//
//        } else if (currentPracticePosition == 1) {
//            frameAnim1 = new AnimationDrawable();
//            // 为AnimationDrawable添加动画帧
//            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong1), 500);
//            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong2), 500);
//            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong3), 500);
//            frameAnim1.setOneShot(false);
//            ivImg.setBackgroundDrawable(frameAnim1);
//
//        }
        if (isFirstInto) {
            getVerb();
        } else {//第二次
            frameAnim1 = new AnimationDrawable();
            // 为AnimationDrawable添加动画帧
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong1), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong2), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.lanniao_chichong3), 500);
            frameAnim1.setOneShot(false);
            ivImg.setBackgroundDrawable(frameAnim1);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playLocalVoice("男-谁在干什么.MP3", true);
                    doAnim();
                }
            }, 2000);

            initTextBg(sentenceGroupTrainingBean);
        }


        llIndicator.setSelectedPosition(currentPracticePosition);

    }


    /**
     * 初始化文本背景
     */
    private void initTextBg(JuziBean.SentenceGroupTrainingBean sentenceGroupTrainingBean) {

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_bg_big, null);
            if (i == 0) {
                ll_text_bg_parent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(grapWidth, 0, 0, 0);
                inflate.setLayoutParams(lp);
                ll_text_bg_parent.addView(inflate);
            }
        }

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_layout_big, null);
            if (i == 0) {
                ll_text_parent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(grapWidth, 0, 0, 0);
                inflate.setLayoutParams(lp);
                ll_text_parent.addView(inflate);
            }
        }//for结束

        leftChildTextOne = ll_text_parent.getChildAt(0);
        rightChildTextTwo = ll_text_parent.getChildAt(1);

        tv_content1 = (TextView) leftChildTextOne.findViewById(R.id.tv_content);
        tv_content2 = (TextView) rightChildTextTwo.findViewById(R.id.tv_content);

        tv_content1.setText(sentenceGroupTrainingBean.getCardOneChar());
        tv_content2.setText(sentenceGroupTrainingBean.getCardTwoChar());
//点击布局
        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic, null);
            if (i == 0) {
                llClickLayout.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                if (currentSize != 4) {
                    lp.setMargins(25, 0, 0, 0);
                }
                inflate.setLayoutParams(lp);
                llClickLayout.addView(inflate);
            }
        }
        View childAt0 = llClickLayout.getChildAt(0);
        View childAt11 = llClickLayout.getChildAt(1);

        ImageView iv_click_pic1 = (ImageView) childAt0.findViewById(R.id.iv_click_pic);
        ImageView iv_click_pic2 = (ImageView) childAt11.findViewById(R.id.iv_click_pic);

        if (sentenceGroupTrainingBean.getCardOneChar().equals("吃")) {
            initAnimationEat(iv_click_pic1);
        } else if (sentenceGroupTrainingBean.getCardOneChar().equals("穿")) {
            initAnimationWear(iv_click_pic1);
        } else if (sentenceGroupTrainingBean.getCardOneChar().equals("洗")) {
            initAnimationWash(iv_click_pic1);
        } else {
            GlideUtil.display(JuZiExerciseActivityTemp.this, sentenceGroupTrainingBean.getCardOneImage(), iv_click_pic1);
        }

        if (sentenceGroupTrainingBean.getCardTwoChar().equals("吃")) {
            initAnimationEat(iv_click_pic2);
        } else if (sentenceGroupTrainingBean.getCardTwoChar().equals("穿")) {
            initAnimationWear(iv_click_pic2);

        } else if (sentenceGroupTrainingBean.getCardTwoChar().equals("洗")) {
            initAnimationWash(iv_click_pic2);
        } else {
            GlideUtil.display(JuZiExerciseActivityTemp.this, sentenceGroupTrainingBean.getCardTwoImage(), iv_click_pic2);
        }

        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        TextView tv_content22 = (TextView) llClickLayout.getChildAt(1).findViewById(R.id.tv_choose2);
        tv_content11.setText(sentenceGroupTrainingBean.getCardTwoChar());
        tv_content22.setText(sentenceGroupTrainingBean.getCardOneChar());

        final View childAt = llClickLayout.getChildAt(0);//第一个
        childAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOneMove) {//代表第一个文本是不是展示出来了
                    return;
                }
                player.stop();
//
                playLocalVoice("男-" + sentenceGroupTrainingBean.getCardTwoChar() + ".MP3", false);
//                把小手的布局隐藏掉
//                View childAt = llClickLayout.getChildAt(0);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                rl_hand.setVisibility(View.GONE);//小手布局隐藏掉

                childAt.setClickable(false);
                AnimationHelper.startScaleAnimation(mContext, childAt);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator sax = ObjectAnimator.ofFloat(childAt, "scaleX", 1f, 0.8f);
                        ObjectAnimator say = ObjectAnimator.ofFloat(childAt, "scaleY", 1f, 0.5f);

                        int height = (int) (childAt.getHeight() * 0.25);
                        int width = (int) (childAt.getWidth() * 0.1);
                        int left = childAt.getLeft();

//                        int topLeft = rightChildTextTwo.getLeft();
//                        int distance_x = childAt.getTop();
//                        int distance_y = topLeft - left;

                        int topLeft = leftChildTextOne.getLeft();

                        int distance_x = childAt.getTop();
                        int distance_y = topLeft - left;

//                        float curTranslationX = childAt.getTranslationX();
//                        float curTranslationY = childAt.getTranslationY();

                        ObjectAnimator obx = ObjectAnimator.ofFloat(childAt, "translationX", distance_y - width);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(childAt, "translationY", -distance_x - height);

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();//点击布局向上飞的动画

                        isTwoMove = true;

                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                ll_text_parent.setVisibility(View.VISIBLE);//文字的父布局
                                childAt.setVisibility(View.INVISIBLE);//移动的view

                                View text_bg = ll_text_bg_parent.getChildAt(1);//自己的背景
                                text_bg.setVisibility(View.INVISIBLE);

                                View tv_content1 = ll_text_parent.getChildAt(1);//自己文本
                                tv_content1.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

//                                        chiDangaoAnim(sentenceGroupTrainingBean.getStartSlideshow(),
//                                                sentenceGroupTrainingBean.getStartSlideshow(),
//                                                sentenceGroupTrainingBean.getStartSlideshow());
                                        mergeText();
                                    }
                                }, 1000);


                            }
                        });
                    }
                }, 1000);
            }
        });

        final View childAt1 = llClickLayout.getChildAt(1);//第二个
        childAt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null != player) {
                    player.stop();
                }
//                if (currentPracticePosition == 0) {
//                    playLocalVoice("男-吃.MP3", false);
//                } else if (currentPracticePosition == 1) {
//                    playLocalVoice("男-蓝鸟.MP3", false);
//                }
                playLocalVoice("男-" + sentenceGroupTrainingBean.getCardOneChar() + ".MP3", false);

                //  把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(1);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                rl_hand.setVisibility(View.GONE);

                childAt1.setClickable(false);

                AnimationHelper.startScaleAnimation(mContext, childAt1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator sax = ObjectAnimator.ofFloat(childAt1, "scaleX", 1f, 0.8f);
                        ObjectAnimator say = ObjectAnimator.ofFloat(childAt1, "scaleY", 1f, 0.5f);

                        int height = (int) (childAt1.getHeight() * 0.25);
                        int width = (int) (childAt1.getWidth() * 0.1);

                        int distance_x = childAt1.getTop();
                        int left = childAt1.getLeft();

                        int topLeft = leftChildTextOne.getLeft();
                        int distance_y = topLeft - left;//负数

                        float curTranslationX = childAt1.getTranslationX();
                        float curTranslationY = childAt1.getTranslationY();


                        ObjectAnimator obx = ObjectAnimator.ofFloat(childAt1, "translationX", curTranslationX, distance_y - width);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(childAt1, "translationY", curTranslationY, -distance_x - height);

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();

                        isOneMove = true;
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                ll_text_parent.setVisibility(View.VISIBLE);//文字的父布局，展示出来

                                childAt1.setVisibility(View.INVISIBLE);//点击的移动布局

                                View text_bg = ll_text_bg_parent.getChildAt(0);//自己的背景
                                text_bg.setVisibility(View.INVISIBLE);//自己的背景展示

                                View tv_content0 = ll_text_parent.getChildAt(0);
                                tv_content0.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content0, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();

                                View tv_content1 = ll_text_parent.getChildAt(1);

                                if (tv_content1.getVisibility() == View.VISIBLE) {
                                    tv_content1.setVisibility(View.INVISIBLE);
                                }

                                final RelativeLayout rl_hand = llClickLayout.getChildAt(0).findViewById(R.id.rl_hand);
                                final WaveCircleView wave_cirlce_view = llClickLayout.getChildAt(0).findViewById(R.id.wave_cirlce_view);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        rl_hand.setVisibility(View.VISIBLE);
                                        wave_cirlce_view.startWave();

                                    }
                                }, 3000);

                            }
                        });
                    }
                }, 1000);
            }
        });

    }

    private void initAnimationEat(ImageView iv_click_pic1) {
        AnimationDrawable frameAnim1 = new AnimationDrawable();
//            // 为AnimationDrawable添加动画帧
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong1), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong2), 500);
        frameAnim1.setOneShot(false);
        iv_click_pic1.setBackground(frameAnim1);
        frameAnim1.start();
    }

    private void initAnimationWear(ImageView iv_click_pic1) {
        AnimationDrawable frameAnim1 = new AnimationDrawable();
//            // 为AnimationDrawable添加动画帧
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong1), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong2), 500);
        frameAnim1.setOneShot(false);
        iv_click_pic1.setBackground(frameAnim1);
        frameAnim1.start();
    }

    private void initAnimationWash(ImageView iv_click_pic1) {
        AnimationDrawable frameAnim1 = new AnimationDrawable();
//            // 为AnimationDrawable添加动画帧
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong1), 500);
        frameAnim1.addFrame(getResources().getDrawable(R.drawable.niaochichong2), 500);
        frameAnim1.setOneShot(false);
        iv_click_pic1.setBackground(frameAnim1);
        frameAnim1.start();
    }

    /**
     * 名词
     */
    private void getVerb() {
        PreferencesHelper helper = new PreferencesHelper(JuZiExerciseActivityTemp.this);
        String token = helper.getToken();
        Log.e("数据", "tokegetVerbn" + token);

        String url = Setting.getSentencegroup();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(JuZiExerciseActivityTemp.this, this));
    }

    private Boolean isFirstInto = true;

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
            (JuZiExerciseActivityTemp.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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

                            isFirstInto = false;
                            Gson gson = new Gson();
                            JuziBean model = gson.fromJson(result,
                                    new TypeToken<JuziBean>() {
                                    }.getType());

                            sentenceGroupTraining = model.getSentenceGroupTraining();
                            sentenceGroupTrainingBean = sentenceGroupTraining.get(0);
//                            "id": 1,
//                                    "startSlideshow": "http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg",
//                                    "cardOneImage": "http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg",
//                                    "cardOneChar": "吃",
//                                    "cardOneRecord": null,
//                                    "cardTwoImage": "http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg",
//                                    "cardTwoChar": "饭",
//                                    "cardTwoRecord": null,
//                                    "groupChar": "吃饭",
//                                    "groupRecord": null,
//                                    "createTime": 1540886848000,
//                                    "updateTime": null,
//                                    "states": "1"
                            // TODO: 2018/11/2 0002 动态
                            String startSlideshow = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541750869&di=f2c590055e4eb2542d81bc0df0d1ef06&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F1%2F5791e7d3b1a02.jpg";
                            Drawable drawable = GetDrawable.loadImage(JuZiExerciseActivityTemp.this, startSlideshow);
                            frameAnim1 = new AnimationDrawable();
                            // 为AnimationDrawable添加动画帧
                            frameAnim1.addFrame(drawable, 500);
                            frameAnim1.addFrame(drawable, 500);
                            frameAnim1.addFrame(drawable, 500);
                            frameAnim1.setOneShot(false);
                            ivImg.setBackgroundDrawable(frameAnim1);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    playLocalVoice("男-谁在干什么.MP3", true);
                                    doAnim();
                                }
                            }, 2000);

                            initTextBg(sentenceGroupTrainingBean);
                        }

//                        ToastUtil.showToast(JuZiExerciseActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {
        (JuZiExerciseActivityTemp.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(JuZiExerciseActivityTemp.this, errorMessage);
            }
        });

    }

    private void mergeText() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = tv_content1.getWidth();

                TextPaint paint = tv_content1.getPaint();
                paint.setTextSize(tv_content1.getTextSize());
                float textWidth = paint.measureText(tv_content1.getText().toString());//这个方法能把文本所占宽度衡量出来.
//                int i = MyUtils.dip2px(JuZiExerciseActivity.this, 80) - (int) textWidth;
                int i1 = width - (int) textWidth;

                paint = tv_content2.getPaint();
                paint.setTextSize(tv_content2.getTextSize());
                float textWidth2 = paint.measureText(tv_content2.getText().toString());//这个方法能把文本所占宽度衡量出来.
//                int i2 = MyUtils.dip2px(JuZiExerciseActivity.this, 80) - (int) textWidth2;
                int i2 = width - (int) textWidth2;

                int i3 = (rightChildTextTwo.getLeft() - leftChildTextOne.getLeft() - leftChildTextOne.getWidth());
                int distance_x = (i3 + i1 / 2 + i2 / 2) / 2 - 41;//15是调节用的

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftChildTextOne.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                leftChildTextOne.setLayoutParams(layoutParams);
                leftChildTextOne.setBackground(null);
//
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) rightChildTextTwo.getLayoutParams();
                layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.leftMargin = 0;
                rightChildTextTwo.setLayoutParams(layoutParams2);
                rightChildTextTwo.setBackground(null);

                RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) ll_text_parent.getLayoutParams();
                ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parentLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parent.setLayoutParams(ll_text_parentLayoutParams);
                ll_text_parent.setBackgroundColor(JuZiExerciseActivityTemp.this.getResources().getColor(R.color.white));

                ObjectAnimator obx = ObjectAnimator.ofFloat(leftChildTextOne, "translationX", distance_x);
                obx.setDuration(1000);
                obx.start();

                ObjectAnimator obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX", -distance_x);
                obx1.setDuration(1000);
                obx1.start();
                obx1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ll_text_parent.setBackground(null);

                        int width = ll_text_parent.getWidth();
                        int height = ll_text_parent.getHeight();
                        RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) llTextParentBg.getLayoutParams();
//                        int i3 = +MyUtils.dip2px(JuZiExerciseActivity.this, 14);
                        ll_text_parentLayoutParams.width = width;
                        ll_text_parentLayoutParams.height = height;
                        llTextParentBg.setLayoutParams(ll_text_parentLayoutParams);

                        llTextParentBg.setVisibility(View.VISIBLE);

                        llTextParentBg.setBackgroundResource(R.drawable.flash_png);

                        ObjectAnimator sax = ObjectAnimator.ofFloat(llTextParentBg, "scaleX", 1f, 0.7f);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax);
                        set.setDuration(800);
                        set.start();

                        player.stop();
                        if (currentPracticePosition == 0) {
//                            chiDangaoAnim(R.drawable.bingjiling2, R.drawable.bingjiling3, R.drawable.eat1);
                            playLocalVoice("男-男孩吃冰激凌.MP3", false);
                        } else if (currentPracticePosition == 1) {
//                            chiDangaoAnim(R.drawable.lanniao_chichong1, R.drawable.lanniao_chichong2, R.drawable.lanniao_chichong3);
                            playLocalVoice("男-蓝鸟吃虫.MP3", false);
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinish) {
                                    return;
                                }

                                currentPracticePosition++;
                                if (currentPracticePosition < 2) {
                                    Intent intent = new Intent(JuZiExerciseActivityTemp.this, JuZiExerciseActivityTemp.class);
                                    intent.putExtra("currentPracticePosition", currentPracticePosition);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(JuZiExerciseActivityTemp.this, LetsTestActivity.class);
                                    intent.putExtra("type", "juzi");
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        }, 2000);
                    }
                });

            }
        }, 1000);


    }

    private boolean isFinish = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isFinish = true;
    }

    @OnClick({R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                finish();
                isFinish = true;
                break;
        }
    }

    /**
     * 中间图片位置的动画 逐帧动画
     *
     * @param lanniao_chichong1
     * @param lanniao_chichong2
     * @param lanniao_chichong3
     */
    private void chiDangaoAnim(int lanniao_chichong1, int lanniao_chichong2, int lanniao_chichong3) {
        frameAnim = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        frameAnim.addFrame(getResources().getDrawable(lanniao_chichong1), 500);
        frameAnim.addFrame(getResources().getDrawable(lanniao_chichong2), 500);
        frameAnim.addFrame(getResources().getDrawable(lanniao_chichong3), 500);
        frameAnim.setOneShot(false);
        ivImg.setBackgroundDrawable(frameAnim);
        frameAnim.start();
    }

    /**
     * 一开始大图动画播放2变
     */
    private void doAnim() {
        int duration = 0;
        for (int i = 0; i < frameAnim1.getNumberOfFrames(); i++) {
            duration += frameAnim1.getDuration(i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnim1.stop();
                frameAnim1.start();
            }
        }, duration);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnim1.selectDrawable(1);      //选择当前动画的第一帧，然后停止
                frameAnim1.stop();

            }
        }, duration * 2);

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
                public void onCompletion(MediaPlayer mp) {
                    frameAnim1.stop();

                    if (isFirst) {
                        View childAt = llClickLayout.getChildAt(1);
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rl_hand.setVisibility(View.VISIBLE);
                                wave_cirlce_view.startWave();

                            }
                        }, 3000);
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

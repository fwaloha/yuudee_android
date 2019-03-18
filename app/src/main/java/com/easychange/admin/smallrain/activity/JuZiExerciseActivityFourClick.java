package com.easychange.admin.smallrain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.AnimationHelper;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.CircleBarTime;
import com.easychange.admin.smallrain.views.IndicatorView;
import com.easychange.admin.smallrain.views.WaveCircleView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenlipeng on 2018/10/20 0020
 * describe:  句子结构页面练习
 */
public class JuZiExerciseActivityFourClick extends BaseActivity {


    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.ll_text_parent)
    LinearLayout ll_text_parent;
    @BindView(R.id.ll_text_bg_parent)
    LinearLayout ll_text_bg_parent;
    @BindView(R.id.ll_click_layout)
    LinearLayout llClickLayout;
    @BindView(R.id.cb)
    CircleBarTime cb;
    @BindView(R.id.ll_text_parent_bg)
    LinearLayout llTextParentBg;
    private MediaPlayer player;
    private AnimationDrawable frameAnim;

    private Handler handler = new Handler();
    private int currentSize = 4;
    private View rightChildTextTwo;
    private View rightChildTextOne;

    private boolean isOneMove = false;
    private boolean isTwoMove = false;
    private boolean isThreeMove = false;
    private boolean isFourMove = false;

    private TextView tv_content1;
    private TextView tv_content2;
    private View rightChildTextThree;
    private View rightChildTextFour;
    private TextView tv_content3;
    private TextView tv_content4;
    private View text_bg;

    private int currentPracticePosition = 0;

    private Timer timer;
    private TimerTask timerTask;

    private double loopRate;
    private double loopTime;
    private int currentLoopTime = 0;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                cb.setProgress((float) loopRate * currentLoopTime);
                currentLoopTime++;
            } else if (msg.what == 2) {

                View childAt = llClickLayout.getChildAt((int) msg.obj);

                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                rl_hand.setVisibility(View.VISIBLE);
                wave_cirlce_view.startWave();
            }
        }
    };
    private AnimationDrawable frameAnim1;

    /**
     * 开始自动减时
     *
     * @param i
     */
    private void startTime(final int i) {
        if (timer == null) {
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {

                if (currentLoopTime <= loopTime) {

                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = i;
                    mHandler.sendMessage(message);//发送消息
                } else {

                    Message message = Message.obtain();
                    message.what = 2;
                    message.obj = i;
                    mHandler.sendMessage(message);//发送消息

                    stopTime();
                }

            }
        };
        timer.schedule(timerTask, 0, executeInterval);//1000ms执行一次
    }


    private void stopTime() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    private int executeInterval = 100;//循环间隔

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_zi_four);
        ButterKnife.bind(this);

        cb.setProgress(0);

        loopTime = 4000 / executeInterval;//循环次数
        loopRate = 100.00 / loopTime;//每次循环，圆环走的度数

        currentPracticePosition = getIntent().getIntExtra("currentPracticePosition", 0);

//        playLocalVoice("男-谁在干什么.MP3", true);

        if (currentPracticePosition == 0) {
            frameAnim1 = new AnimationDrawable();
            // 为AnimationDrawable添加动画帧
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.chidanggao11), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.chidanggao2), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.chidanggao3), 500);
            frameAnim1.setOneShot(false);
            ivImg.setBackgroundDrawable(frameAnim1);

        } else if (currentPracticePosition == 1) {
            frameAnim1 = new AnimationDrawable();
            // 为AnimationDrawable添加动画帧
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.bingan1), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.bingan2), 500);
            frameAnim1.addFrame(getResources().getDrawable(R.drawable.bingan3), 500);
            frameAnim1.setOneShot(false);
            ivImg.setBackgroundDrawable(frameAnim1);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-谁在干什么.MP3", true);
                doAnim();
            }
        }, 2000);

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_bg, null);
            if (i == 0) {
                ll_text_bg_parent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(25, 0, 0, 0);
                inflate.setLayoutParams(lp);
                ll_text_bg_parent.addView(inflate);
            }
        }

        for (
                int i = 0;
                i < currentSize; i++)

        {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_layout, null);
            if (i == 0) {
                ll_text_parent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(25, 0, 0, 0);
                inflate.setLayoutParams(lp);
                ll_text_parent.addView(inflate);
            }
        }//for结束

        rightChildTextOne = ll_text_parent.getChildAt(0);
        rightChildTextTwo = ll_text_parent.getChildAt(1);
        rightChildTextThree = ll_text_parent.getChildAt(2);
        rightChildTextFour = ll_text_parent.getChildAt(3);

        tv_content1 = (TextView) rightChildTextOne.findViewById(R.id.tv_content);
        tv_content2 = (TextView) rightChildTextTwo.findViewById(R.id.tv_content);
        tv_content3 = (TextView) rightChildTextThree.findViewById(R.id.tv_content);
        tv_content4 = (TextView) rightChildTextFour.findViewById(R.id.tv_content);


        if (currentPracticePosition == 0)

        {
            tv_content1.setText("男");
            tv_content2.setText("孩");
            tv_content3.setText("吃");
            tv_content4.setText("蛋糕");
        } else if (currentPracticePosition == 1)

        {
            tv_content1.setText("男");
            tv_content2.setText("孩");
            tv_content3.setText("吃");
            tv_content4.setText("饼干");
        }

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout, null);
            ImageView iv_click_pic = (ImageView) inflate.findViewById(R.id.iv_click_pic);
            if (i == 0) {
                if (currentPracticePosition == 0) {
                    iv_click_pic.setImageDrawable(JuZiExerciseActivityFourClick.this.getResources().getDrawable(R.drawable.dangao));//
                } else if (currentPracticePosition == 1) {
                    iv_click_pic.setImageDrawable(JuZiExerciseActivityFourClick.this.getResources().getDrawable(R.drawable.binggan));//
                }

            } else if (i == 1) {
                iv_click_pic.setImageDrawable(JuZiExerciseActivityFourClick.this.getResources().getDrawable(R.drawable.nan));//
            } else if (i == 2) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chi1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chi2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chi3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
//                iv_click_pic.setImageDrawable(JuZiExerciseActivityFourClick.this.getResources().getDrawable(R.drawable.chi1));//
            } else if (i == 3) {
                iv_click_pic.setImageDrawable(JuZiExerciseActivityFourClick.this.getResources().getDrawable(R.drawable.hai));//
            }

            if (i == 0) {
                llClickLayout.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

//                if (currentSize != 4) {
//                lp.setMargins(2, 0, 0, 0);
//                }
                inflate.setLayoutParams(lp);
                llClickLayout.addView(inflate);
            }

        }

        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        TextView tv_content22 = (TextView) llClickLayout.getChildAt(1).findViewById(R.id.tv_choose2);
        TextView tv_content33 = (TextView) llClickLayout.getChildAt(2).findViewById(R.id.tv_choose2);
        TextView tv_content44 = (TextView) llClickLayout.getChildAt(3).findViewById(R.id.tv_choose2);

        if (currentPracticePosition == 0)

        {
            tv_content11.setText("蛋糕");
            tv_content22.setText("男");
            tv_content33.setText("吃");
            tv_content44.setText("孩");
        } else if (currentPracticePosition == 1)

        {
            tv_content11.setText("饼干");
            tv_content22.setText("男");
            tv_content33.setText("吃");
            tv_content44.setText("孩");
        }


        //点击事件
        for (
                int i = 0; i < llClickLayout.getChildCount(); i++)

        {
            final View childAt = llClickLayout.getChildAt(i);//第几个
            final int currentPosition = i;

            int finalI = i;
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (currentPosition == 0) {
                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                            return;
                        }
                        if (!isTwoMove) {//代表第一个文本是不是展示出来了
                            return;
                        }
                        if (!isThreeMove) {//代表第一个文本是不是展示出来了
                            return;
                        }

                    } else if (currentPosition == 1) {

                        cb.setProgress((float) 0);
                        currentLoopTime = 0;

                        if (currentLoopTime <= loopTime) {
                            stopTime();
                        }


                    } else if (currentPosition == 2) {
                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                            return;
                        }
                        if (!isTwoMove) {//代表第一个文本是不是展示出来了
                            return;
                        }

                        cb.setProgress((float) 0);
                        currentLoopTime = 0;

                        if (currentLoopTime <= loopTime) {
                            stopTime();
                        }

                    } else {
                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                            return;
                        }

                        cb.setProgress((float) 0);
                        currentLoopTime = 0;

                        if (currentLoopTime <= loopTime) {
                            stopTime();
                        }


                    }

                    //  把小手的布局隐藏掉
                    View childAt = llClickLayout.getChildAt(finalI);
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    rl_hand.setVisibility(View.GONE);

                    childAt.setClickable(false);

                    AnimationHelper.startScaleAnimation(mContext, childAt);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator sax = ObjectAnimator.ofFloat(childAt, "scaleX", 1f, 1f);
                            ObjectAnimator say = ObjectAnimator.ofFloat(childAt, "scaleY", 1f, 0.6f);


                            int height = (int) (childAt.getHeight() * 0.2);
                            int distance_x = childAt.getTop();

                            int left = childAt.getLeft() + 12;//10调节作用

//                            4132
//                            1234
//                            tv_content11.setText("冰激凌");
//                            tv_content22.setText("男");
//                            tv_content33.setText("吃");
//                            tv_content44.setText("孩");

                            int distance_y;//水平方向
                            if (currentPosition == 0) {
                                int topLeft = rightChildTextFour.getLeft();
                                distance_y = topLeft - left;

                                isFourMove = true;

                                player.stop();
                                if (currentPracticePosition == 0) {
                                    playLocalVoice("男-蛋糕.MP3", false);
                                } else if (currentPracticePosition == 1) {
                                    playLocalVoice("男-饼干.MP3", false);
                                }

                            } else if (currentPosition == 1) {
                                int topLeft = rightChildTextOne.getLeft();
                                distance_y = topLeft - left;
                                isOneMove = true;

                                player.stop();
                                playLocalVoice("男-男.MP3", false);
                                startTime(3);
                            } else if (currentPosition == 2) {
                                int topLeft = rightChildTextThree.getLeft();
                                distance_y = topLeft - left;
                                isThreeMove = true;

                                player.stop();
                                playLocalVoice("男-吃.MP3", false);
                                startTime(0);
                            } else {
                                int topLeft = rightChildTextTwo.getLeft();
                                distance_y = topLeft - left;
                                isTwoMove = true;

                                player.stop();
                                playLocalVoice("男-孩.MP3", false);

                                startTime(2);
                            }

                            float curTranslationX = childAt.getTranslationX();
                            float curTranslationY = childAt.getTranslationY();

                            ObjectAnimator obx = ObjectAnimator.ofFloat(childAt, "translationX", curTranslationX, distance_y);
                            ObjectAnimator oby = ObjectAnimator.ofFloat(childAt, "translationY", curTranslationY, -distance_x - height);

                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(sax, say, obx, oby);
                            set.setDuration(2000);
                            set.start();

                            set.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ll_text_parent.setVisibility(View.VISIBLE);//文字的父布局
                                    childAt.setVisibility(View.INVISIBLE);//手指点击的view(移动的view)
                                    //                            4132
                                    //                            1234
                                    View tv_content1;
                                    if (currentPosition == 0) {
                                        text_bg = ll_text_bg_parent.getChildAt(3);//自己的背景
                                        tv_content1 = ll_text_parent.getChildAt(3);//自己文本
                                    } else if (currentPosition == 1) {
                                        View tv_content11 = ll_text_parent.getChildAt(1);//
                                        View tv_content12 = ll_text_parent.getChildAt(2);//
                                        View tv_content13 = ll_text_parent.getChildAt(3);//

                                        tv_content11.setVisibility(View.INVISIBLE);
                                        tv_content12.setVisibility(View.INVISIBLE);
                                        tv_content13.setVisibility(View.INVISIBLE);

                                        text_bg = ll_text_bg_parent.getChildAt(0);//自己的背景
                                        tv_content1 = ll_text_parent.getChildAt(0);//自己文本
//                                        startTime(3);
                                    } else if (currentPosition == 2) {
                                        text_bg = ll_text_bg_parent.getChildAt(2);//自己的背景
                                        tv_content1 = ll_text_parent.getChildAt(2);//自己文本

//                                        startTime(0);

                                    } else {
                                        text_bg = ll_text_bg_parent.getChildAt(1);//自己的背景
                                        tv_content1 = ll_text_parent.getChildAt(1);//自己文本
//                                        startTime(2);
                                    }

                                    text_bg.setVisibility(View.INVISIBLE);//吧自己的背景 隐藏掉
                                    tv_content1.setVisibility(View.VISIBLE);//自己文本

                                    //透明度渐变显示
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                    animator.setDuration(1000);
                                    animator.start();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (currentPosition == 0) {
                                                if (currentPracticePosition == 0) {
                                                    chiDangaoAnim(R.drawable.chidanggao11, R.drawable.chidanggao2, R.drawable.chidanggao3);
                                                } else if (currentPracticePosition == 1) {
                                                    chiDangaoAnim(R.drawable.bingan1, R.drawable.bingan2, R.drawable.bingan3);
                                                }
                                                mergeText();
                                            }
                                        }
                                    }, 1000);


                                }
                            });
                        }
                    }, 1000);


                }
            });
        }
        llIndicator.setSelectedPosition(currentPracticePosition);

    }

    private void mergeText() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//像素
                int screenWidth = MyUtils.getScreenWidth(mContext);
                int gapWidth = screenWidth - (rightChildTextOne.getLeft() * 2) - rightChildTextOne.getWidth() * 4;//4个item的间距

                int width = tv_content1.getWidth();
                TextPaint paint = tv_content1.getPaint();
                paint.setTextSize(tv_content1.getTextSize());
                float textWidth = paint.measureText(tv_content1.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i1 = width - (int) textWidth;

                width = tv_content2.getWidth();
                paint = tv_content2.getPaint();
                paint.setTextSize(tv_content2.getTextSize());
                textWidth = paint.measureText(tv_content2.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i2 = width - (int) textWidth;

                width = tv_content3.getWidth();
                paint = tv_content3.getPaint();
                paint.setTextSize(tv_content3.getTextSize());
                textWidth = paint.measureText(tv_content3.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i3 = width - (int) textWidth;

                width = tv_content4.getWidth();
                paint = tv_content4.getPaint();
                paint.setTextSize(tv_content4.getTextSize());
                textWidth = paint.measureText(tv_content4.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i4 = width - (int) textWidth;

//                位置和长度的单位是像素。
                final int distance_x1 = (gapWidth / 3 + i2 / 2 + i3 / 2) / 2 - 15;//15是调节用的

                final int distance_x_left = distance_x1 + gapWidth / 3 + i1 / 2 + i2 / 2 - 27;//27是调节用的
                final int distance_x_right = distance_x1 + gapWidth / 3 + i3 / 2 + i4 / 2 - 27;//27是调节用的

                LinearLayout.LayoutParams
                        layoutParams = (LinearLayout.LayoutParams) rightChildTextOne.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                rightChildTextOne.setLayoutParams(layoutParams);
                rightChildTextOne.setBackground(null);

                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) rightChildTextTwo.getLayoutParams();
                layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.leftMargin = 0;
                rightChildTextTwo.setLayoutParams(layoutParams2);
                rightChildTextTwo.setBackground(null);

                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) rightChildTextThree.getLayoutParams();
                layoutParams3.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams3.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                rightChildTextThree.setLayoutParams(layoutParams);
                rightChildTextThree.setBackground(null);

                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) rightChildTextFour.getLayoutParams();
                layoutParams4.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams4.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams4.leftMargin = 0;
                rightChildTextFour.setLayoutParams(layoutParams2);
                rightChildTextFour.setBackground(null);

                RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) ll_text_parent.getLayoutParams();
                ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parentLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parent.setLayoutParams(ll_text_parentLayoutParams);
                ll_text_parent.setBackgroundColor(JuZiExerciseActivityFourClick.this.getResources().getColor(R.color.white));


                ObjectAnimator obx = ObjectAnimator.ofFloat(rightChildTextOne, "translationX", distance_x_left);
                obx.setDuration(1000);
                obx.start();

                ObjectAnimator obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX", distance_x1);
                obx1.setDuration(1000);
                obx1.start();

                ObjectAnimator obx2 = ObjectAnimator.ofFloat(rightChildTextThree, "translationX", -distance_x1);
                obx2.setDuration(1000);
                obx2.start();

                ObjectAnimator obx3 = ObjectAnimator.ofFloat(rightChildTextFour, "translationX", -distance_x_right);
                obx3.setDuration(1000);
                obx3.start();

                obx3.addListener(new AnimatorListenerAdapter() {
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

//                        RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) ll_text_parent.getLayoutParams();
//                        ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                        ll_text_parentLayoutParams.height = height;
//                        ll_text_parent.setLayoutParams(ll_text_parentLayoutParams);
//
//                        ll_text_parent.setBackgroundResource(R.drawable.flash_png);

//                        ObjectAnimator obx = ObjectAnimator.ofFloat(rightChildTextOne, "translationX", distance_x_left - 5);
//                        ObjectAnimator obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX", distance_x1 - 2);
//                        ObjectAnimator obx2 = ObjectAnimator.ofFloat(rightChildTextThree, "translationX", -distance_x1 + 2);
//                        ObjectAnimator obx3 = ObjectAnimator.ofFloat(rightChildTextFour, "translationX", -distance_x_right + 5);
//
//                        ObjectAnimator sax = ObjectAnimator.ofFloat(ll_text_parent, "scaleX", 1f, 0.7f);
//                        ObjectAnimator say = ObjectAnimator.ofFloat(ll_text_parent, "scaleY", 1f, 1.0f);
//
//                        AnimatorSet set = new AnimatorSet();
//                        set.playTogether(sax, say, obx, obx1, obx2, obx3);
//                        set.setDuration(400);
//                        set.start();
                        player.stop();
                        if (currentPracticePosition == 0) {
//                            chiDangaoAnim(R.drawable.chidanggao11, R.drawable.chidanggao2, R.drawable.chidanggao3);
                            playLocalVoice("男-男孩吃饼干.MP3", false);
                        } else if (currentPracticePosition == 1) {
//                            chiDangaoAnim(R.drawable.bingan1, R.drawable.bingan2, R.drawable.bingan3);
                            playLocalVoice("男-男孩吃饼干.MP3", false);
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentPracticePosition++;

                                if (currentPracticePosition < 2) {
                                    Intent intent = new Intent(JuZiExerciseActivityFourClick.this, JuZiExerciseActivityFourClick.class);
                                    intent.putExtra("currentPracticePosition", currentPracticePosition);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(JuZiExerciseActivityFourClick.this, PinTuActivity.class);
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


    @OnClick({R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                finish();
                break;


            case R.id.fl_choose1:

                break;
//            case R.id.iv_home:
//                finish();
//                break;
//            case R.id.iv_home:
//                finish();
//                break;
        }
    }

    /**
     * 中间图片位置的动画 逐帧动画
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

    private void playLocalVoice(String videoName, Boolean isFirst) {
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
                        startTime(1);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

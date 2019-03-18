package juzi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.LetsTestToTrainActivity;
import com.easychange.admin.smallrain.activity.PinTuActivity;
import com.easychange.admin.smallrain.activity.PinTuAllFlyActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.entity.BreakNetBean;
import com.easychange.admin.smallrain.utils.AnimationHelper;
import com.easychange.admin.smallrain.utils.ForegroundCallbacks;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.ScreenListener;
import com.easychange.admin.smallrain.views.CircleBarTime;
import com.easychange.admin.smallrain.views.IndicatorView;
import com.easychange.admin.smallrain.views.WaveCircleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import bean.GetFortifierBean;
import bean.JuZiChengZu;
import bean.LookLatelyBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenlipeng on 2018/10/20 0020
 * describe:  句子成组训练   2个卡片
 * 训练和测试
 * 成组是2个卡片
 * 分解是4个卡片
 */
public class JuZiChengZuCiShiLianActivityOld extends BaseActivity implements AsyncRequest {

    //这个语音，没有测试
    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.ll_text_parent_bg)
    LinearLayout llTextParentBg;
    @BindView(R.id.ll_text_parent)
    LinearLayout llTextParent;
    @BindView(R.id.ll_text_bg_parent)
    LinearLayout llTextBgParent;
    @BindView(R.id.ll_click_layout)
    LinearLayout llClickLayout;
    @BindView(R.id.cb)
    CircleBarTime cb;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.iv_jinbi_bottom)
    ImageView ivJinbiBottom;
    @BindView(R.id.rl_tiankongkuang)
    RelativeLayout rlTiankongkuang;
    private MediaPlayer player;

    private AnimationDrawable frameAnim;

    private int currentSize = 2;
    private View rightChildTextTwo;
    private View leftChildTextOne;

    private boolean isTwoMove = false;
    private boolean isOneMove = false;
    private TextView tv_content1;
    private TextView tv_content2;
    private AnimationDrawable frameAnim1;
    private JuZiChengZu juzibean;
    private boolean isQuitActivity = false;

    private Handler handler = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    Drawable drawable = new BitmapDrawable(bitmap);

                    // 为AnimationDrawable添加动画帧
                    frameAnim1.addFrame(drawable, 500);
                    if (frameAnim1.getNumberOfFrames() == 3) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim();
                    }
                    break;
                case 2:
                    byte[] bytes1 = (byte[]) msg.obj;
                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    Drawable drawable1 = new BitmapDrawable(bitmap1);

                    // 为AnimationDrawable添加动画帧
                    frameAnim1.addFrame(drawable1, 500);

                    if (frameAnim1.getNumberOfFrames() == 3) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim();
                    }
                    break;
                case 3://
                    byte[] bytes2 = (byte[]) msg.obj;
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    Drawable drawable2 = new BitmapDrawable(bitmap2);

                    frameAnim1.addFrame(drawable2, 500);

                    if (frameAnim1.getNumberOfFrames() == 3) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim();
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private int position = 0;
    private JuZiChengZu.SentenceGroupTestBean sentenceGroupTrainingBean;
    private boolean isOrder;
    private Timer timer;
    private int executeInterval = 100;
    private int currentLoopTime = 0;
    private int loopTimeOne;
    private double loopRateOne;
    private int loopTimeTwo;
    private double loopRateTwo;
    private int currentFirstPotision;
    private int currentTwoPotision;
    private ImageView currentIv_click_pic;
    private int split1length = 3;
    private PreferencesHelper helper;
    private MyApplication application;
    private MediaPlayer mediaPlayer;
    private ScreenListener screenListener;
    private boolean isFirstInto = true;
    private boolean isTwoInto = true;

    private boolean noPlayTwoCardVoice = false;

    /**
     * 异步get,直接调用
     */
    private void asyncGet(String IMAGE_URL, final int i) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get().url(IMAGE_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                if (response.isSuccessful()) {
                    if (i == 0) {
                        message.what = 1;
                        message.obj = response.body().bytes();
                        handler.sendMessage(message);
                    }
                    if (i == 1) {
                        message.what = 2;
                        message.obj = response.body().bytes();
                        handler.sendMessage(message);
                    }
                    if (i == 2) {
                        message.what = 3;
                        message.obj = response.body().bytes();
                        handler.sendMessage(message);
                    }
                } else {
                    handler.sendEmptyMessage(3);
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                double obj = (double) msg.obj;

                cb.setProgress((float) obj * currentLoopTime);
                currentLoopTime++;
            } else if (msg.what == 2) {
                View childAt = llClickLayout.getChildAt((int) msg.obj);

                String tagPosition = (String) childAt.getTag();
                if (tagPosition.equals("1")) {
                    if (childAt.isClickable()) {
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                        rl_hand.setVisibility(View.VISIBLE);
                        wave_cirlce_view.startWave();
                    }

                } else {
                    if (childAt.isClickable()) {
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                        rl_hand.setVisibility(View.VISIBLE);
                        wave_cirlce_view.startWave();
                    }
                }
            }
        }
    };

    /**
     * 开始自动减时
     *
     * @param currentClick
     * @param loopTime     循环次数
     */
    private void startTime(final int currentClick, int loopTime, double loopRate) {
        if (timer == null) {
            timer = new Timer();
        }
        currentLoopTime = 0;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (currentLoopTime <= loopTime) {

                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = loopRate;
                    if (mHandler != null) {
                        mHandler.sendMessage(message);//发送消息
                    }
                } else {

                    Message message = Message.obtain();
                    message.what = 2;
                    message.obj = currentClick;
                    if (mHandler != null) {
                        mHandler.sendMessage(message);//发送消息
                    }
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

    List<String> voiceListData = new ArrayList<>();
    private boolean readyPlayNextOrderOne = false;
    private boolean readyPlayNextOrderTwo = false;
    private boolean playNextCardVoice = false;

    private void setDataIntoView() {
        llMoney.setVisibility(View.VISIBLE);

        getFortifier();

        frameAnim1 = new AnimationDrawable();

        int fristAssistTime = juzibean.getSentenceGroupTest().get(position).getCardOneTime();
        int secondAssistTime = juzibean.getSentenceGroupTest().get(position).getCardTwoTime();

        loopTimeOne = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateOne = 100.00 / loopTimeOne;//每次循环，圆环走的度数

        loopTimeTwo = secondAssistTime * 1000 / executeInterval;//循环次数
        loopRateTwo = 100.00 / loopTimeTwo;//每次循环，圆环走的度数

        frameAnim1 = new AnimationDrawable();

        sentenceGroupTrainingBean = juzibean.getSentenceGroupTest().get(position);//每条数据

        coursewareId = sentenceGroupTrainingBean.getId();
        name = sentenceGroupTrainingBean.getGroupChar();


        String startSlideshow = sentenceGroupTrainingBean.getStartSlideshow();
        String[] split = startSlideshow.split(",");
        for (int i = 0; i < 3; i++) {
            asyncGet(split[i], i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-谁在干什么.MP3", true);
            }
        }, 100);

        int grapWidth = MyUtils.dip2px(JuZiChengZuCiShiLianActivityOld.this, 26);
        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(JuZiChengZuCiShiLianActivityOld.this).inflate(R.layout.text_bg_big, null);
            if (i == 0) {
                llTextBgParent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(grapWidth, 0, 0, 0);
                inflate.setLayoutParams(lp);
                llTextBgParent.addView(inflate);
            }
        }

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(JuZiChengZuCiShiLianActivityOld.this).inflate(R.layout.text_layout_big, null);
            if (i == 0) {
                llTextParent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(grapWidth, 0, 0, 0);
                inflate.setLayoutParams(lp);
                llTextParent.addView(inflate);
            }
        }//for结束
        leftChildTextOne = llTextParent.getChildAt(0);
        rightChildTextTwo = llTextParent.getChildAt(1);

        tv_content1 = (TextView) leftChildTextOne.findViewById(R.id.tv_content);
        tv_content2 = (TextView) rightChildTextTwo.findViewById(R.id.tv_content);
        tv_content1.setText(sentenceGroupTrainingBean.getCardOneChar());
        tv_content2.setText(sentenceGroupTrainingBean.getCardTwoChar());

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic_mingci_ceshi_xiugai, null);
//            View inflate = LayoutInflater.from(JuZiChengZuCiShiLianActivity.this).inflate(R.layout.click_layout_two_click_pic, null);
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
            ImageView iv_click_pic = (ImageView) inflate.findViewById(R.id.iv_click_pic);

            String cardImage = sentenceGroupTrainingBean.getList().get(i).getCardImage();
            boolean contains = cardImage.contains(",");
            if (!contains) {
                GlideUtil.display(JuZiChengZuCiShiLianActivityOld.this, cardImage, iv_click_pic);
            } else {
                String[] split1 = cardImage.split(",");
                getNetClickImage(iv_click_pic, split1);
            }

        }//for结束
        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        TextView tv_content22 = (TextView) llClickLayout.getChildAt(1).findViewById(R.id.tv_choose2);

        tv_content11.setText(sentenceGroupTrainingBean.getList().get(0).getCardChar());
        tv_content22.setText(sentenceGroupTrainingBean.getList().get(1).getCardChar());

        String firstCardColorChar = sentenceGroupTrainingBean.getCardOneChar();
        String currentClickCardColorChar = sentenceGroupTrainingBean.getList().get(0).getCardChar();
        if (currentClickCardColorChar.equals(firstCardColorChar)) {
            isOrder = true;
        }

        voiceListData.add("谁在干什么");
        if (firstCardColorChar.equals(currentClickCardColorChar)) {
            llClickLayout.getChildAt(0).setTag("0");
            llClickLayout.getChildAt(1).setTag("1");
            currentFirstPotision = 0;
            currentTwoPotision = 1;
            voiceListData.add(sentenceGroupTrainingBean.getList().get(0).getCardRecord());
            voiceListData.add(sentenceGroupTrainingBean.getList().get(1).getCardRecord());
        } else {
            llClickLayout.getChildAt(1).setTag("0");
            llClickLayout.getChildAt(0).setTag("1");
            currentFirstPotision = 1;
            currentTwoPotision = 0;

            voiceListData.add(sentenceGroupTrainingBean.getList().get(1).getCardRecord());
            voiceListData.add(sentenceGroupTrainingBean.getList().get(0).getCardRecord());
        }


        final View childAt = llClickLayout.getChildAt(0);//第一个
        childAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOrder) {
                    long l = -(currentClickOneStartTime - System.currentTimeMillis()) / 1000;

                    if (l >= 50) {
                        l = 1;
                    }
                    stayTimeList = l + "";
                } else {
                    if (!isTwoMove) {
                        //正确小手辅助出现
                        View childAt = llClickLayout.getChildAt(1);
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                            rl_hand.setVisibility(View.VISIBLE);
                            pass = "0";
                            final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                            wave_cirlce_view.startWave();
                        }
                        return;
                    }
                    long l = -(currentClickTwoStartTime - System.currentTimeMillis()) / 1000;
                    if (l >= 50) {
                        l = 1;
                    }
                    stayTimeList = stayTimeList + "," + l + "";
                }

                String tagPosition = (String) childAt.getTag();
                if (tagPosition.equals("0")) {
                    stopTime();
                    currentClickTwoStartTime = System.currentTimeMillis();
                } else if (tagPosition.equals("1")) {
                    stopTime();
                }
                isOneMove = true;

                if (voiceListData.size() == 3) {//代表上一个没有播完
                    readyPlayNextOrderOne = true;

                } else if (voiceListData.size() == 2) {//代表上一个播完
                    if (null != mediaPlayer) {
                        if (mediaPlayer.isPlaying()) {
                            playNextCardVoice = true;
                        } else {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    } else {
                        playLocalVoiceOnLine(voiceListData.get(0));
                    }

                } else if (voiceListData.size() == 1) {//代表上一个播完

                    playLocalVoiceOnLine(voiceListData.get(0));
                }

//                把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(0);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {
                    pass = "0";
                } else {
                    pass = "1";
                }

                rl_hand.setVisibility(View.GONE);

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

                        int distance_x;
                        int distance_y;
                        int currentPosition;

                        if (isOrder) {
                            int topLeft = leftChildTextOne.getLeft();
                            distance_x = childAt.getTop();
                            distance_y = topLeft - left;
                            currentPosition = 0;
                        } else {
                            int topLeft = rightChildTextTwo.getLeft();
                            distance_x = childAt.getTop();
                            distance_y = topLeft - left;
                            currentPosition = 1;

                        }

                        ObjectAnimator obx = ObjectAnimator.ofFloat(childAt, "translationX", distance_y - width);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(childAt, "translationY", -distance_x - height);

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();

                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                if (tagPosition.equals("0")) {
                                    cb.setProgress(0);
                                    startTime(currentTwoPotision, loopTimeTwo, loopRateTwo);
                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
                                    }

                                }

                                llTextParent.setVisibility(View.VISIBLE);//文字的父布局

                                childAt.setVisibility(View.INVISIBLE);//移动的view


                                View text_bg = llTextBgParent.getChildAt(currentPosition);//自己的背景
                                text_bg.setVisibility(View.INVISIBLE);

                                View tv_content1 = llTextParent.getChildAt(currentPosition);//自己文本
                                tv_content1.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();

                                if (isOrder) {
                                    View tv_content11 = llTextParent.getChildAt(1);

                                    if (tv_content11.getVisibility() == View.VISIBLE) {
                                        tv_content11.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                }

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isOrder) {

                                            if (pass.equals("1")) {//是否通过 1 是 0 否
                                                ivJinbiBottom.setVisibility(View.VISIBLE);

                                                int moneyTop = llMoney.getTop();
                                                int tiankongkuangTop = rlTiankongkuang.getTop();
                                                int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(JuZiChengZuCiShiLianActivityOld.this, 32);

                                                int screenWidth = MyUtils.getScreenWidth(JuZiChengZuCiShiLianActivityOld.this) / 3;

                                                ObjectAnimator obx = ObjectAnimator.ofFloat(ivJinbiBottom, "translationX", 0f, screenWidth, 0f);
                                                obx.setDuration(2000);

                                                ObjectAnimator obx1 = ObjectAnimator.ofFloat(ivJinbiBottom, "translationY", 0, (float) -(i * 0.8), (float) -(i));
                                                obx1.setDuration(2000);

                                                ObjectAnimator sax = ObjectAnimator.ofFloat(ivJinbiBottom, "scaleX", 1f, 4f, 1f);
                                                ObjectAnimator say = ObjectAnimator.ofFloat(ivJinbiBottom, "scaleY", 1f, 4f, 1f);
                                                AnimatorSet set = new AnimatorSet();
                                                set.playTogether(sax, obx1, obx, say);
                                                set.setDuration(2000);
                                                set.setInterpolator(new AccelerateDecelerateInterpolator());
                                                set.start();
                                                set.addListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        ivJinbiBottom.setVisibility(View.GONE);

                                                        if (Integer.parseInt(pass) == 1) {//pass	是	string	是否通过 1 是 0 否
                                                            int currentGold = gold + 1;
                                                            tvMoney.setText("x " + currentGold);
                                                        }

                                                    }
                                                });
                                            }
                                            if (!noPlayTwoCardVoice) {
                                                mergeText();
                                            }
                                            doAnim();
                                        }

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

                if (isOrder) {
                    if (!isOneMove) {
                        //正确小手辅助出现
                        View childAt = llClickLayout.getChildAt(0);
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                            rl_hand.setVisibility(View.VISIBLE);
                            pass = "0";

                            final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                            wave_cirlce_view.startWave();
                        }
                        return;
                    }
                    long l = -(currentClickTwoStartTime - System.currentTimeMillis()) / 1000;
                    if (l >= 50) {
                        l = 1;
                    }
                    stayTimeList = stayTimeList + "," + l + "";
                } else {
                    long l = -(currentClickOneStartTime - System.currentTimeMillis()) / 1000;
                    if (l >= 50) {
                        l = 1;
                    }
                    stayTimeList = l + "";
                }

                String tagPosition = (String) childAt1.getTag();
                if (tagPosition.equals("0")) {
                    stopTime();
                    currentClickTwoStartTime = System.currentTimeMillis();
                } else if (tagPosition.equals("1")) {
                    stopTime();
                }
                isTwoMove = true;

                if (voiceListData.size() == 3) {//代表上一个没有播完
                    readyPlayNextOrderTwo = true;

                } else if (voiceListData.size() == 2) {//代表上一个播完
                    if (null != mediaPlayer) {
                        if (mediaPlayer.isPlaying()) {
                            playNextCardVoice = true;
                        } else {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    } else {
                        playLocalVoiceOnLine(voiceListData.get(0));
                    }


                } else if (voiceListData.size() == 1) {//代表上一个播完
                    playLocalVoiceOnLine(voiceListData.get(0));
                }

                //  把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(1);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {
                    pass = "0";
                }
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

                        int left = childAt1.getLeft();

                        int distance_x = childAt1.getTop();
                        int distance_y;
                        int currentPosition;//上面融合文本对应的地方

                        if (isOrder) {
                            int topLeft = rightChildTextTwo.getLeft();
                            distance_y = topLeft - left;//负数
                            currentPosition = 1;
                        } else {
                            int topLeft = leftChildTextOne.getLeft();
                            distance_y = topLeft - left;//负数
                            currentPosition = 0;
                        }


                        float curTranslationX = childAt1.getTranslationX();
                        float curTranslationY = childAt1.getTranslationY();

                        ObjectAnimator obx = ObjectAnimator.ofFloat(childAt1, "translationX", curTranslationX, distance_y - width);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(childAt1, "translationY", curTranslationY, -distance_x - height);

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();

                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                if (tagPosition.equals("0")) {
                                    cb.setProgress(0);
                                    startTime(currentTwoPotision, loopTimeTwo, loopRateTwo);

                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
                                    }
                                }

                                llTextParent.setVisibility(View.VISIBLE);//文字的父布局，展示出来

                                childAt1.setVisibility(View.INVISIBLE);//点击的移动布局

                                View text_bg = llTextBgParent.getChildAt(currentPosition);//自己的背景
                                text_bg.setVisibility(View.INVISIBLE);//自己的背景展示

                                View tv_content0 = llTextParent.getChildAt(currentPosition);
                                tv_content0.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content0, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();

                                if (isOrder) {
                                } else {
                                    View tv_content1 = llTextParent.getChildAt(1);

                                    if (tv_content1.getVisibility() == View.VISIBLE) {
                                        tv_content1.setVisibility(View.INVISIBLE);
                                    }
                                }

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOrder) {
                                            if (pass.equals("1")) {//是否通过 1 是 0 否
                                                ivJinbiBottom.setVisibility(View.VISIBLE);

                                                int moneyTop = llMoney.getTop();
                                                int tiankongkuangTop = rlTiankongkuang.getTop();
                                                int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(JuZiChengZuCiShiLianActivityOld.this, 32);

                                                int screenWidth = MyUtils.getScreenWidth(JuZiChengZuCiShiLianActivityOld.this) / 3;

                                                ObjectAnimator obx = ObjectAnimator.ofFloat(ivJinbiBottom, "translationX", 0f, screenWidth, 0f);
                                                obx.setDuration(2000);

                                                ObjectAnimator obx1 = ObjectAnimator.ofFloat(ivJinbiBottom, "translationY", 0, (float) -(i * 0.8), (float) -(i));
                                                obx1.setDuration(2000);

                                                ObjectAnimator sax = ObjectAnimator.ofFloat(ivJinbiBottom, "scaleX", 1f, 4f, 1f);
                                                ObjectAnimator say = ObjectAnimator.ofFloat(ivJinbiBottom, "scaleY", 1f, 4f, 1f);
                                                AnimatorSet set = new AnimatorSet();
                                                set.playTogether(sax, obx1, obx, say);
                                                set.setDuration(2000);
                                                set.setInterpolator(new AccelerateDecelerateInterpolator());
                                                set.start();
                                                set.addListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        ivJinbiBottom.setVisibility(View.GONE);

                                                        if (Integer.parseInt(pass) == 1) {//pass	是	string	是否通过 1 是 0 否
                                                            int currentGold = gold + 1;
                                                            tvMoney.setText("x " + currentGold);
                                                        }

                                                    }
                                                });
                                            }
                                            if (!noPlayTwoCardVoice) {
                                                mergeText();
                                            }
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

    private int coursewareId;//课件id
    private String startTime;
    private String name;
    private String pass = "1";//是否通过 1 是 0 否
    private String stayTimeList = "";
    private String stayTime;
    private String groupId = "";
    private long startTimeMillis;
    private long currentClickOneStartTime;
    private long currentClickTwoStartTime;

    /**
     * 添加儿童训练测试课件结果
     * * @return token    是	string	用户标识
     * coursewareId	是	int	课件id
     * scene	是	string	学习场景 1训练 2测试 3意义
     * module	是	string	训练测试模块 1名词 2动词 3句子组成 4句子分解
     * startTime	是	data	开始时间
     * name	是	string	课件名字 例：男孩吃苹果
     * pass	是	string	是否通过 1 是 0 否
     * stayTimeList	否	string	停留时间 逗号分隔 例”5,5,6,7”
     * disTurbName	否	string	干扰课件名称 只有名词意义才统计
     * errorType	否	string	错误类型 1干扰形容词 2干扰名词 3预选形容词 4预选名词 只有名词意义级别才统计错误类型
     * stayTime	是	int	总停留时间
     * groupId	是	int	当前组课件记录表id
     */
    private void addTrainingResult(String coursewareId, String startTime
            , String name, String pass, String stayTimeList, String disTurbName, String errorType
            , String stayTime, String groupId) {
        PreferencesHelper helper = new PreferencesHelper(JuZiChengZuCiShiLianActivityOld.this);
        String token = helper.getToken();
        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "2");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "3");//1名词 2动词 3句子组成 4句子分解
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
        stringStringHashMap.put("stayTime", stayTime);
        stringStringHashMap.put("groupId", groupId);
        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());

        if (isFinish) {
            return;
        }
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .tag(JuZiChengZuCiShiLianActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiChengZuCiShiLianActivityOld.this, this));
    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(2)) {//标记那个接口

            String result = (String) data;
            (JuZiChengZuCiShiLianActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            if (isFinish) {
                                return;
                            }
                            groupId = (String) jsonObject.getString("groupId");
                            getSystemStatistics();
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(24)) {//

            String result = (String) data;
            (JuZiChengZuCiShiLianActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (isFinish) {
                            return;
                        }

                        if (code.equals("200")) {
                            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                            foregroundCallbacks = null;

                            screenListener.unregisterListener();
                            screenListener = null;

                            Gson gson = new Gson();
                            LookLatelyBean model1 = gson.fromJson(result,
                                    new TypeToken<LookLatelyBean>() {
                                    }.getType());

                            position = position + 1;

                            if (position < juzibean.getSentenceGroupTraining().size()) {

                                if (pass.equals("1")) {//是否通过 1 是 0 否

                                    addFortifier("3", "0");
                                    ++gold;
                                }

                                Intent intent = new Intent(JuZiChengZuCiShiLianActivityOld.this, JuZiChengZuCiShiLianActivityOld.class);
                                intent.putExtra("groupId", groupId);

                                intent.putExtra("position", position);
                                intent.putExtra("model", juzibean);
                                startActivity(intent);
                                finish();
                            } else {//从新开始


                                if (pass.equals("1")) {//是否通过 1 是 0 否

                                    addFortifier("3", "0");
                                    ++gold;
                                }
//                                这个是6组的，6组都要80%对吧，如果成组的3组训练测试，老是不能连续3个80%。是不是，
//                                先循环好成组的啊。等成组的3个百分之80.过了再次循环分解的啊

//                                1在做名词 2在做动词  3句子成组 4句子分解 5 全部通关
                                String module = model1.getData().getModule();
                                if (module.equals("4")) {
//                                    名词,动词,句子分解通关,进入通关成功的页面,3S返回首页  句子分组通关后进入通关成功的页面3S自动进入句子分解模块

                                    Intent intent1 = new Intent(JuZiChengZuCiShiLianActivityOld.this, PinTuAllFlyActivity.class);
                                    intent1.putExtra("anInt1", gold);
                                    intent1.putExtra("juzichengzu", true);
                                    startActivity(intent1);
                                    finish();
                                    return;
                                }
                                if (gold >= 10) {//达到了10个金币
                                    Intent intent = new Intent(JuZiChengZuCiShiLianActivityOld.this, PinTuActivity.class);
                                    intent.putExtra("anInt1", gold);
                                    intent.putExtra("juzichengzu", true);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(JuZiChengZuCiShiLianActivityOld.this, LetsTestToTrainActivity.class);
                                    intent.putExtra("type", "juzichengzu");
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(3)) {//强化物添加金币

            String result = (String) data;
            (JuZiChengZuCiShiLianActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(4)) {//强化物添加金币

            String result = (String) data;
            (JuZiChengZuCiShiLianActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {

                            Gson gson = new Gson();
                            GetFortifierBean model = gson.fromJson(result,
                                    new TypeToken<GetFortifierBean>() {
                                    }.getType());
                            for (int i = 0; i < model.getData().size(); i++) {
                                String module = model.getData().get(i).getModule();
                                int gold = model.getData().get(i).getGold();

//                                1名词 2动词 3句子组成 4句子分解
                                if (module.equals("3")) {
                                    JuZiChengZuCiShiLianActivityOld.this.gold = gold;
                                    tvMoney.setText("x " + gold);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private int gold = 0;

    /**
     *
     */
    private void getSystemStatistics() {
        PreferencesHelper helper = new PreferencesHelper(JuZiChengZuCiShiLianActivityOld.this);
        String token = helper.getToken();

        String url = Setting.getSystemStatistics();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        if (isFinish) {
            return;
        }

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(24)//XX接口的标识
                .tag(JuZiChengZuCiShiLianActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiChengZuCiShiLianActivityOld.this, this));
    }

    /**
     *
     */
    private void getFortifier() {
        PreferencesHelper helper = new PreferencesHelper(JuZiChengZuCiShiLianActivityOld.this);
        String token = helper.getToken();

        String url = Setting.getFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .tag(JuZiChengZuCiShiLianActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiChengZuCiShiLianActivityOld.this, this));
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     *               1名词 2动词 3句子组成 4句子分解
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(JuZiChengZuCiShiLianActivityOld.this);
        String token = helper.getToken();

//             module	是	string	模块
//        state	是	string	1减少 0添加
        String url = Setting.addFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);
        stringStringHashMap.put("state", state);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .tag(JuZiChengZuCiShiLianActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiChengZuCiShiLianActivityOld.this, this));
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_zi_chengzu_cishi);
        ButterKnife.bind(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();
        position = getIntent().getIntExtra("position", 0);
        juzibean = (JuZiChengZu) getIntent().getSerializableExtra("model");
        groupId = getIntent().getStringExtra("groupId");

        if (null == juzibean) {
        } else {
            setDataIntoView();
        }

        llIndicator.setSelectedPosition(position);

        ForegroundCallbacks.get().addListener(foregroundCallbacks);
        setScreenLock();

        EventBusUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        finish();
    }

    private boolean isShouldAddTrainingResult = false;

    ForegroundCallbacks.Listener foregroundCallbacks = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
//                  preferencesHelper.saveIsBackground(false);
            isQuitActivity = false;

            if (isFirstInto) {
                isFirstInto = false;
            } else {
                if (voiceListData.size() == 2) {
                    if (noPlayTwoCardVoice) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                        playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), true);
                    }
                } else if (voiceListData.size() == 1) {
                    if (isOneMove && !isTwoMove) {//对的

                    } else if (isOneMove && isTwoMove) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                        playLocalVoiceOnLineOnScreenChange(voiceListData.get(0));
                    }
                } else if (voiceListData.size() == 0) { //对的  2中情况第一个是2个语音都说完了。第二个是1个语音说完了，另1个语音说了一半
                    if (isShouldAddTrainingResult) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
                }
            }
        }

        @Override
        public void onBecameBackground() {
//                   preferencesHelper.saveIsBackground(true);
            isQuitActivity = true;

            if (null != player && player.isPlaying()) {
                if (voiceListData.size() > 0) {
                    voiceListData.remove(0);
                }
                player.stop();
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                if (voiceListData.size() > 0) {
                    voiceListData.remove(0);
                }
                mediaPlayer.stop();
            }

            if (isOneMove && isTwoMove) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                if (voiceListData.size() == 2) {
                    noPlayTwoCardVoice = true;
                }
            }
        }
    };


    private void setScreenLock() {
//        screenListener
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("onUserPresent", "onUserPresent");
            }

            @Override
            public void onScreenOn() {
                Log.e("onScreenOn", "onScreenOn");
                isQuitActivity = false;

                if (isTwoInto) {
                    isTwoInto = false;
                } else {
                    if (voiceListData.size() == 2) {
                        if (noPlayTwoCardVoice) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                            playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), true);
                        }
                    } else if (voiceListData.size() == 1) {
                        if (isOneMove && !isTwoMove) {//对的

                        } else if (isOneMove && isTwoMove) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                            playLocalVoiceOnLineOnScreenChange(voiceListData.get(0));
                        }
                    } else if (voiceListData.size() == 0) { //对的  2中情况第一个是2个语音都说完了。第二个是1个语音说完了，另1个语音说了一半
                        if (isShouldAddTrainingResult) {
                            stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                            addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                    , stayTime, groupId);
                        }
                    }
                }
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreenOff", "onScreenOff");
                isQuitActivity = true;

                if (null != player && player.isPlaying()) {
                    if (voiceListData.size() > 0) {
                        voiceListData.remove(0);
                    }
                    player.stop();
                }

                if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                    if (voiceListData.size() > 0) {
                        voiceListData.remove(0);
                    }
                    mediaPlayer.stop();
                }

                if (isOneMove && isTwoMove) {//点击2个了。正在播放第一个。移除了第一个。进来的时候播放第二个。  对的
                    if (voiceListData.size() == 2) {
                        noPlayTwoCardVoice = true;
                    }
                }
            }
        });
    }

    private void mergeText() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (null != player)
                    player.stop();

                playLocalVoiceOnLine(sentenceGroupTrainingBean.getGroupRecord());

                String tv_content1Str = tv_content1.getText().toString();
                String tv_content2Str = tv_content2.getText().toString();
                int width = tv_content1.getWidth();

                TextPaint paint = tv_content1.getPaint();
                paint.setTextSize(tv_content1.getTextSize());
                float textWidth = paint.measureText(tv_content1.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i1 = width - (int) textWidth;

                paint = tv_content2.getPaint();
                paint.setTextSize(tv_content2.getTextSize());
                float textWidth2 = paint.measureText(tv_content2.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i2 = width - (int) textWidth2;

                int i3 = (rightChildTextTwo.getLeft() - leftChildTextOne.getLeft() - leftChildTextOne.getWidth());
                int distance_x = (i3 + i1 / 2 + i2 / 2) / 2 - 38;//15是调节用的

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftChildTextOne.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                leftChildTextOne.setLayoutParams(layoutParams);
                leftChildTextOne.setBackground(null);

                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) rightChildTextTwo.getLayoutParams();
                layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams2.leftMargin = 0;
                rightChildTextTwo.setLayoutParams(layoutParams2);
                rightChildTextTwo.setBackground(null);

                RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) llTextParent.getLayoutParams();
                ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parentLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                llTextParent.setLayoutParams(ll_text_parentLayoutParams);
                llTextParent.setBackgroundColor(JuZiChengZuCiShiLianActivityOld.this.getResources().getColor(R.color.white));

                ObjectAnimator obx1 = null;
                if (tv_content1Str.length() == tv_content2Str.length()) {
                    ObjectAnimator obx = ObjectAnimator.ofFloat(leftChildTextOne, "translationX",
                            distance_x);
                    obx.setDuration(1000);
                    obx.start();

                    obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX",
                            -distance_x);
                    obx1.setDuration(1000);
                    obx1.start();
                }
                if (tv_content1Str.length() < tv_content2Str.length()) {

                    int i = tv_content2Str.length() - tv_content1Str.length();

                    ObjectAnimator obx = ObjectAnimator.ofFloat(leftChildTextOne, "translationX",
                            distance_x - i * 12);
                    obx.setDuration(1000);
                    obx.start();

                    obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX",
                            -distance_x - i * 12);
                    obx1.setDuration(1000);
                    obx1.start();
                }

                obx1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        playBgVoice();
                        llTextParent.setBackground(null);

                        int width = llTextParent.getWidth();
                        int height = llTextParent.getHeight();
                        RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) llTextParentBg.getLayoutParams();
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

                        frameAnim1.stop();
                        frameAnim1.start();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (!isQuitActivity) {
                                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                            , stayTime, groupId);
                                } else {
                                    isShouldAddTrainingResult = true;
                                }

                            }
                        }, 2000);
                    }
                });

            }
        }, 1000);
    }

    private void playBgVoice() {
        int max = 8;
        Random random = new Random();
        int i1 = random.nextInt(max) + 1;
        if (i1 == 1) {
            playLocalVoiceBg("22729087_christmas-piano-o-holy-night_by_prostorecords_preview.mp3");
        } else if (i1 == 2) {
            playLocalVoiceBg("22729087_christmas-piano-o-holy-night_by_prostorecords_preview_1.mp3");
        } else if (i1 == 3) {
            playLocalVoiceBg("22729087_christmas-piano-o-holy-night_by_prostorecords_preview_2.mp3");
        } else if (i1 == 4) {
            playLocalVoiceBg("22729087_christmas-piano-o-holy-night_by_prostorecords_preview_3.mp3");
        } else if (i1 == 5) {
            playLocalVoiceBg("22729161_beautiful-christmas-advertising-background_by_ikoliks_previessw_4.mp3");
        } else if (i1 == 6) {
            playLocalVoiceBg("22729161_beautiful-christmas-advertising-background_by_ikoliks_preview_1.mp3");
        } else if (i1 == 7) {
            playLocalVoiceBg("22729161_beautiful-christmas-advertising-background_by_ikoliks_preview_2.mp3");
        } else if (i1 == 8) {
            playLocalVoiceBg("22729161_beautiful-christmas-advertising-background_by_ikoliks_preview_4.mp3");
        } else if (i1 == 9) {
            playLocalVoiceBg("22729161_beautiful-christmas-advertising-background_by_ikoliks_preview_5.mp3");
        }
    }

    private void playLocalVoiceBg(String videoName) {
        if (isQuitActivity) {
            return;
        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();

            //3 准备播放
            player.prepareAsync();
            //3.1 设置一个准备完成的监听
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFinish = false;

    @Override
    public void onBackPressed() {

        isQuitActivity = true;
        OkHttpUtils.getInstance().cancelTag(this);

        isFinish = true;

        ForegroundCallbacks.get().removeListener(foregroundCallbacks);
        foregroundCallbacks = null;

        screenListener.unregisterListener();
        screenListener = null;

        handler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }

    @OnClick({R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                isQuitActivity = true;
                OkHttpUtils.getInstance().cancelTag(this);

                isFinish = true;

                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                foregroundCallbacks = null;

                screenListener.unregisterListener();
                screenListener = null;
                handler.removeCallbacksAndMessages(null);

                finish();
                break;
            case R.id.fl_choose1:
                break;
        }
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

                View childAt = llClickLayout.getChildAt(0);
                View childAt1 = llClickLayout.getChildAt(1);

                if (childAt.isClickable() && childAt1.isClickable()) {
                    currentClickOneStartTime = System.currentTimeMillis();
                    startTime(currentFirstPotision, loopTimeOne, loopRateOne);
                }

            }
        }, duration * 2);

        frameAnim1.start();
    }

    private void playLocalVoice(String videoName, boolean isFirst) {
        if (isQuitActivity) {
            return;
        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放

            //3 准备播放
            player.prepareAsync();
            //3.1 设置一个准备完成的监听
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });
//            player.prepare();
//            player.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    frameAnim1.stop();
                    if (voiceListData.size() == 3) {//播完了
                        voiceListData.remove(0);

                        if (readyPlayNextOrderOne & !readyPlayNextOrderTwo) {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                        if (!readyPlayNextOrderOne & readyPlayNextOrderTwo) {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }

                        if (readyPlayNextOrderOne & readyPlayNextOrderTwo) {
                            playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), false);

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在线
     *
     * @param videoName
     */
    private void playLocalVoiceOnLine(String videoName) {
        if (isQuitActivity) {
            return;
        }
        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() == 2) {//第二个语音说完了
                voiceListData.remove(0);

                if (playNextCardVoice) {//已经点击了，就播放第三个。
                    playLocalVoiceOnLine(voiceListData.get(0));
                }
            } else if (voiceListData.size() == 1) {//第二个语音说完了
                voiceListData.remove(0);
            }
            return;
        }
        //1 初始化mediaplayer
        mediaPlayer = new MediaPlayer();
        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径
        try {
            mediaPlayer.setDataSource(videoName);
            //3 准备播放
            mediaPlayer.prepareAsync();
            //3.1 设置一个准备完成的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    if (voiceListData.size() == 2) {//第二个语音说完了
                        voiceListData.remove(0);

                        if (playNextCardVoice) {//已经点击了，就播放第三个。
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    } else if (voiceListData.size() == 1) {//第二个语音说完了
                        voiceListData.remove(0);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playLocalVoiceOnLineOnOrderClickAllCard(String videoName, boolean isShouldMerge) {
        if (isQuitActivity) {
            return;
        }
        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() == 2) {//第二个语音说完了
                voiceListData.remove(0);

                playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), isShouldMerge);
            } else if (voiceListData.size() == 1) {//第二个语音说完了
                voiceListData.remove(0);

                if (isShouldMerge) {
                    mergeText();
                }
            }
            return;
        }
        //1 初始化mediaplayer
        player = new MediaPlayer();
        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径
        try {
            player.setDataSource(videoName);
            //3 准备播放
            player.prepareAsync();
            //3.1 设置一个准备完成的监听
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (voiceListData.size() == 2) {//第二个语音说完了
                        voiceListData.remove(0);

                        playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), isShouldMerge);
                    } else if (voiceListData.size() == 1) {//第二个语音说完了
                        voiceListData.remove(0);

                        if (isShouldMerge) {
                            mergeText();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private AnimationDrawable frameAnim2 = new AnimationDrawable();

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);

        if (null != foregroundCallbacks) {
            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;

            screenListener.unregisterListener();
            screenListener = null;
        }

        handler.removeCallbacksAndMessages(null);
        if (player != null) {
            player.stop();
            player.release();//释放播放器占用的资源
            player = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();//释放播放器占用的资源
            mediaPlayer = null;
        }
//        应该是做题的时候，中途退出，20分钟内，进来的时候，显示的刚才的做题位置，如果超过20分钟就从第一个课件开始做
        if (null != bitmap1) {
            bitmap1.recycle();
            bitmap1 = null;
        }
        if (null != bitmap2) {
            bitmap2.recycle();
            bitmap2 = null;
        }
        if (null != bitmap) {
            bitmap.recycle();
            bitmap = null;
        }

        isQuitActivity = true;
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap;

    private Handler handler1 = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    Drawable drawable = new BitmapDrawable(bitmap);

                    // 为AnimationDrawable添加动画帧
                    frameAnim2.addFrame(drawable, 500);

                    if (frameAnim2.getNumberOfFrames() == split1length) {
                        frameAnim2.setOneShot(false);
                        currentIv_click_pic.setBackgroundDrawable(frameAnim2);

                        frameAnim2.start();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 得到网络的图片
     *
     * @param iv_click_pic
     * @param split1
     */
    private void getNetClickImage(ImageView iv_click_pic, String[] split1) {
        currentIv_click_pic = iv_click_pic;
        split1length = split1.length;
        for (int j = 0; j < split1.length; j++) {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().get().url(split1[j]).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = handler1.obtainMessage();
                    if (response.isSuccessful()) {
                        message.what = 1;
                        message.obj = response.body().bytes();

                        handler1.sendMessage(message);

                    }
                }
            });
        }//for结束

        frameAnim1.setOneShot(false);
        iv_click_pic.setBackground(frameAnim1);
        frameAnim1.start();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void playLocalVoiceOnLineOnScreenChange(String videoName) {
        if (isQuitActivity) {
            return;
        }
        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() != 0) {
                voiceListData.remove(0);
            }

            if (isShouldAddTrainingResult) {
                stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                        , stayTime, groupId);
            }
            return;
        }
        //1 初始化mediaplayer
        mediaPlayer = new MediaPlayer();
        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径
        try {

            mediaPlayer.setDataSource(videoName);
            //3 准备播放
            mediaPlayer.prepareAsync();
            //3.1 设置一个准备完成的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    if (voiceListData.size() != 0) {
                        voiceListData.remove(0);
                    }

                    if (isShouldAddTrainingResult) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

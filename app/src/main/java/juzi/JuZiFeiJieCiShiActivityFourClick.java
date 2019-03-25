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
import android.widget.FrameLayout;
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
import com.easychange.admin.smallrain.utils.BitmapUtils;
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
import bean.JuZiFenJieBean;
import bean.JuzifenjieGuoGuan;
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
 * describe:  句子分解测试   4个卡片
 * 训练和测试
 * 成组是2个卡片
 * 分解是4个卡片
 */
public class JuZiFeiJieCiShiActivityFourClick extends BaseActivity implements AsyncRequest {

    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.cb)
    CircleBarTime cb;
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
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.iv_jinbi_bottom)
    ImageView ivJinbiBottom;
    @BindView(R.id.rl_tiankongkuang)
    RelativeLayout rlTiankongkuang;
    @BindView(R.id.iv_xiaolian)
    ImageView ivXiaolian;
    @BindView(R.id.fL_big_pic)
    FrameLayout fLBigPic;
    private MediaPlayer player;
    private MediaPlayer playerBg;


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

    private Timer timer;
    private TimerTask timerTask;

    private double loopRate;
    private double loopTime;
    private int currentLoopTime = 0;


    private AnimationDrawable frameAnim1 = new AnimationDrawable();
    private JuZiFenJieBean juzibean;
    private JuZiFenJieBean.CosentenceResolveTestdeBean sentenceResolveTraining;


    private boolean isShouldMergeText = false;
    private boolean isPerformOverMergeText = false;
    private int executeInterval = 100;//循环间隔
    private Handler handler = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
//                    java.lang.OutOfMemoryError: Failed to allocate a 1000012 byte allocation with 249528 free bytes and 243KB until OOM
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Drawable drawable = new BitmapDrawable(bitmap);

                    // 为AnimationDrawable添加动画帧
                    frameAnim1.addFrame(drawable, 500);

                    if (frameAnim1.getNumberOfFrames() == split.length) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim(false);
                    }
                    break;
                case 2:
                    byte[] bytes1 = (byte[]) msg.obj;
                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    Drawable drawable1 = new BitmapDrawable(bitmap1);

                    // 为AnimationDrawable添加动画帧
                    frameAnim1.addFrame(drawable1, 500);

                    if (frameAnim1.getNumberOfFrames() == split.length) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim(false);
                    }
                    break;
                case 3://
                    byte[] bytes2 = (byte[]) msg.obj;
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    Drawable drawable2 = new BitmapDrawable(bitmap2);

                    frameAnim1.addFrame(drawable2, 500);

                    if (frameAnim1.getNumberOfFrames() == split.length) {
                        frameAnim1.setOneShot(false);
                        ivImg.setBackground(frameAnim1);

                        doAnim(false);
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private int position = 0;
    private long oneTime;
    private long twoTime;
    private long threeTime;
    private long fourTime;
    private MediaPlayer mediaPlayer;
    private boolean FristVoiceFinished = false;
    private ScreenListener l;
    private boolean isFirstInto = true;
    private ScreenListener.ScreenStateListener screenStateListener;
    private ScreenListener screenListener;
    private boolean isExecuteThe = false;
    private String[] split;

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
                    handler.sendEmptyMessage(4);
                }
            }
        });
    }

    private JuzifenjieGuoGuan juzifenjieGuoGuan;
    private PreferencesHelper helper;
    private MyApplication application;
    private boolean isPlayingVoice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_zi_four_ceshi);
        ButterKnife.bind(this);
        voiceHandler.sendEmptyMessage(1);


        position = getIntent().getIntExtra("position", 0);
        juzibean = (JuZiFenJieBean) getIntent().getSerializableExtra("juzibean");
        groupId = getIntent().getStringExtra("groupId");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

        ForegroundCallbacks.get().addListener(foregroundCallbacks);
        setScreenLock();

        EventBusUtil.register(this);

        cb.setProgress(0);

        loopTime = 4000 / executeInterval;//循环次数
        loopRate = 100.00 / loopTime;//每次循环，圆环走的度数

        if (null == juzibean) {
            return;
        }

        application = (MyApplication) getApplication();
        juzifenjieGuoGuan = application.juzifenjieGuoGuan;

        llMoney.setVisibility(View.VISIBLE);

        getFortifier();
        setDataIntoView();

        llIndicator.setSelectedPosition(position);

        EventBusUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        ReleasePlayer();
        finish();
    }

    ForegroundCallbacks.Listener foregroundCallbacks = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
            isExitCurrentActivity = false;

            if (isFirstInto) {
                isFirstInto = false;
            } else {

                if (isExecuteThe && !isYetUploadData) {
                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                            , stayTime, groupId);
                } else if (voiceListData.size() == 0) {
//                        课件做完，上方动画放大缩小，并且所有音频播放完了，没有立即进入下一课件
                    if (isShouldMergeText) {
                        if (!isPerformOverMergeText) {
                            mergeText();
                        }
                    }
                }
            }
        }

        @Override
        public void onBecameBackground() {
            isExitCurrentActivity = true;

            if (null != player && player.isPlaying()) {
                if (voiceListData.size() > 0) {
                    voiceListData.remove(0);
                }
                player.stop();
                isPlayingVoice = false;
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                if (voiceListData.size() > 0) {
                    voiceListData.remove(0);
                }
                mediaPlayer.stop();
                isPlayingVoice = false;
            }
        }
    };

    private void setScreenLock() {
        screenStateListener = new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("onUserPresent", "onUserPresent");
            }

            @Override
            public void onScreenOn() {
                isExitCurrentActivity = false;

                if (isFirstInto) {
                    isFirstInto = false;
                } else {
                    if (isExecuteThe && !isYetUploadData) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    } else if (voiceListData.size() == 0) {
//                        课件做完，上方动画放大缩小，并且所有音频播放完了，没有立即进入下一课件
                        if (isShouldMergeText) {
                            if (!isPerformOverMergeText) {
                                mergeText();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScreenOff() {
                isExitCurrentActivity = true;

                if (null != player && player.isPlaying()) {
                    if (voiceListData.size() > 0) {
                        voiceListData.remove(0);
                    }
                    player.stop();
                    isPlayingVoice = false;
                }

                if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                    if (voiceListData.size() > 0) {
                        voiceListData.remove(0);
                    }
                    mediaPlayer.stop();
                    isPlayingVoice = false;
                }
            }
        };
        screenListener = new ScreenListener(this);
        screenListener.begin(screenStateListener);
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
                if (tagPosition.equals("3")) {
                    if (childAt.isClickable()) {
                        final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                        final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                        rl_hand.setVisibility(View.VISIBLE);
                        wave_cirlce_view.startWave();
                    }

                } else {
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                    rl_hand.setVisibility(View.VISIBLE);
                    wave_cirlce_view.startWave();
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
        timerTask = new TimerTask() {
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

    private double loopRateOne;
    private double loopRateTwo;
    private double loopRateThree;
    private double loopRateFour;
    private int loopTimeOne;
    private int loopTimeTwo;
    private int loopTimeThree;
    private int loopTimeFour;
    private int currentFirstPotision = 0;
    private int currentTwoPotision = 0;
    private int currentThreePotision = 0;
    private int currentFourPotision = 0;

    private void setDataIntoView() {

        int fristAssistTime = juzibean.getCosentenceResolveTestde().get(position).getCardOneTime();
        int secondAssistTime = juzibean.getCosentenceResolveTestde().get(position).getCardTwoTime();
        int threeAssistTime = juzibean.getCosentenceResolveTestde().get(position).getCardThreeTime();
        int fourAssistTime = juzibean.getCosentenceResolveTestde().get(position).getCardFourTime();
        loopTimeOne = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateOne = 100.00 / loopTimeOne;//每次循环，圆环走的度数

        loopTimeTwo = secondAssistTime * 1000 / executeInterval;//循环次数
        loopRateTwo = 100.00 / loopTimeTwo;//每次循环，圆环走的度数

        loopTimeThree = threeAssistTime * 1000 / executeInterval;//循环次数
        loopRateThree = 100.00 / loopTimeThree;//每次循环，圆环走的度数

        loopTimeFour = fourAssistTime * 1000 / executeInterval;//循环次数
        loopRateFour = 100.00 / loopTimeFour;//每次循环，圆环走的度数

        sentenceResolveTraining = juzibean.getCosentenceResolveTestde().get(position);//每条数据

        coursewareId = sentenceResolveTraining.getId();
        name = sentenceResolveTraining.getGroupChar();

        String startSlideshow = sentenceResolveTraining.getStartSlideshow();
        split = startSlideshow.split(",");
        for (int i = 0; i < split.length; i++) {
            asyncGet(split[i], i);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                oneTime = System.currentTimeMillis();
                playLocalVoice("男-谁在干什么.MP3", true);

            }
        }, 2000);

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_bg, null);
            if (i == 0) {
                llTextBgParent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(25, 0, 0, 0);
                inflate.setLayoutParams(lp);
                llTextBgParent.addView(inflate);
            }
        }

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_layout, null);
            if (i == 0) {
                llTextParent.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(25, 0, 0, 0);
                inflate.setLayoutParams(lp);
                llTextParent.addView(inflate);
            }
        }//for结束

        rightChildTextOne = llTextParent.getChildAt(0);
        rightChildTextTwo = llTextParent.getChildAt(1);
        rightChildTextThree = llTextParent.getChildAt(2);
        rightChildTextFour = llTextParent.getChildAt(3);

        tv_content1 = (TextView) rightChildTextOne.findViewById(R.id.tv_content);
        tv_content2 = (TextView) rightChildTextTwo.findViewById(R.id.tv_content);
        tv_content3 = (TextView) rightChildTextThree.findViewById(R.id.tv_content);
        tv_content4 = (TextView) rightChildTextFour.findViewById(R.id.tv_content);

        tv_content1.setText(sentenceResolveTraining.getCardOneChar());
        tv_content2.setText(sentenceResolveTraining.getCardTwoChar());
        tv_content3.setText(sentenceResolveTraining.getCardThreeChar());
        tv_content4.setText(sentenceResolveTraining.getCardFourChar());

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_top, null);
            ImageView iv_click_pic = (ImageView) inflate.findViewById(R.id.iv_click_pic);
            TextView tv_content11 = (TextView) inflate.findViewById(R.id.tv_choose2);
            tv_content11.setText(sentenceResolveTraining.getList().get(i).getCardChar());

            String cardChar1 = sentenceResolveTraining.getList().get(i).getCardChar();

//            吃喝玩穿洗，这5个是固定的
            if (cardChar1.contains("吃")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar1.contains("穿")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar1.contains("洗")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar1.contains("玩")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar1.contains("喝")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else {
                GlideUtil.display(JuZiFeiJieCiShiActivityFourClick.this, sentenceResolveTraining.getList().get(i).getCardImage(), iv_click_pic);
            }
            String cardChar = sentenceResolveTraining.getList().get(i).getCardChar();
            String cardOneChar = sentenceResolveTraining.getCardOneChar();
            String cardTwoChar = sentenceResolveTraining.getCardTwoChar();
            String cardThreeChar = sentenceResolveTraining.getCardThreeChar();
            String cardFourChar = sentenceResolveTraining.getCardFourChar();

            if (cardChar.equals(cardOneChar)) {
                inflate.setTag("0");
                currentFirstPotision = i;
            } else if (cardChar.equals(cardTwoChar)) {
                inflate.setTag("1");
                currentTwoPotision = i;
            } else if (cardChar.equals(cardThreeChar)) {
                inflate.setTag("2");
                currentThreePotision = i;

            } else if (cardChar.equals(cardFourChar)) {
                inflate.setTag("3");
                currentFourPotision = i;
            }

            if (i == 0) {
                llClickLayout.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                inflate.setLayoutParams(lp);
                llClickLayout.addView(inflate);
            }


        }//for结束

        //点击事件
        for (int i = 0; i < llClickLayout.getChildCount(); i++) {
            final View childAt = llClickLayout.getChildAt(i);//第几个
            View rl_click_layout = childAt.findViewById(R.id.rl_click_layout);
            final int currentPosition = i;

            int finalI = i;
            rl_click_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tagPosition = (String) childAt.getTag();
                    if (isOneMove && tagPosition.equals("0")) {//
                        return;
                    }
                    if (isTwoMove && tagPosition.equals("1")) {//
                        return;
                    }
                    if (isThreeMove && tagPosition.equals("2")) {//
                        return;
                    }
                    if (isFourMove && tagPosition.equals("3")) {//
                        return;
                    }

                    if (tagPosition.equals("0")) {

                        stopTime();
                        twoTime = System.currentTimeMillis();
                        long l = -(oneTime - System.currentTimeMillis()) / 1000;
                        if (l >= 50) {
                            l = 1;
                        }
                        stayTimeList = l + "";
                    } else if (tagPosition.equals("1")) {
                        if (!isOneMove) {//代表第一个文本是不是展示出来了

                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentFirstPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";
                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }
                        stopTime();
                        threeTime = System.currentTimeMillis();

                        long l = -(twoTime - System.currentTimeMillis()) / 1000;
                        if (l >= 50) {
                            l = 1;
                        }
                        stayTimeList = stayTimeList + "," + l + "";
                    } else if (tagPosition.equals("2")) {

                        if (!isOneMove) {//代表第一个文本是不是展示出来了

                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentFirstPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";
                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }
                        if (!isTwoMove) {//代表第一个文本是不是展示出来了
                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentTwoPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";

                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }
                        stopTime();
                        fourTime = System.currentTimeMillis();

                        long l = -(threeTime - System.currentTimeMillis()) / 1000;
                        if (l >= 50) {
                            l = 1;
                        }
                        stayTimeList = stayTimeList + "," + l + "";
                    } else if (tagPosition.equals("3")) {

                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentFirstPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";
                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }
                        if (!isTwoMove) {//代表第一个文本是不是展示出来了
                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentTwoPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";
                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }
                        if (!isThreeMove) {//代表第一个文本是不是展示出来了
                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentThreePotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";
                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();
                            }
                            return;
                        }

                        long l = -(fourTime - System.currentTimeMillis()) / 1000;
                        if (l >= 50) {
                            l = 1;
                        }
                        stayTimeList = stayTimeList + "," + l + "";
                    }

                    //  把小手的布局隐藏掉
                    View childAt = llClickLayout.getChildAt(finalI);
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

//                    是否通过 1 是 0 否
                    if (rl_hand.getVisibility() == View.VISIBLE) {
                        pass = "0";
                    }

                    rl_hand.setVisibility(View.GONE);

                    childAt.setClickable(false);

                    AnimationHelper.startScaleAnimation(mContext, childAt);

                    String oneRecord1 = sentenceResolveTraining.getCardOneRecord();
                    String twoRecord1 = sentenceResolveTraining.getCardTwoRecord();
                    String threeRecord1 = sentenceResolveTraining.getCardThreeRecord();
                    String fourRecord1 = sentenceResolveTraining.getCardFourRecord();

                    if (tagPosition.equals("0")) {
                        if (isOneMove)
                            return;

                        voiceListData.add(oneRecord1);
                        isOneMove = true;
                    } else if (tagPosition.equals("1")) {
                        if (isTwoMove)
                            return;

                        voiceListData.add(twoRecord1);
                        isTwoMove = true;
                    } else if (tagPosition.equals("2")) {
                        if (isThreeMove)
                            return;

                        voiceListData.add(threeRecord1);
                        isThreeMove = true;
                    } else if (tagPosition.equals("3")) {
                        if (isFourMove)
                            return;

                        voiceListData.add(fourRecord1);
                        isFourMove = true;

                        cb.setProgress(0);
                        stopTime();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator sax = ObjectAnimator.ofFloat(childAt, "scaleX", 1f, 1f);
                            ObjectAnimator say = ObjectAnimator.ofFloat(childAt, "scaleY", 1f, 0.6f);

                            int height = (int) (childAt.getHeight() * 0.2);
//                            int distance_x = childAt.getTop();
                            int distance_x = childAt.getTop() + MyUtils.dip2px(JuZiFeiJieCiShiActivityFourClick.this, 50);

                            int left = childAt.getLeft() + 12;//10调节作用
                            int distance_y;
                            int topLeft = 0;
                            if (tagPosition.equals("0")) {
                                topLeft = rightChildTextOne.getLeft();
                            } else if (tagPosition.equals("1")) {
                                topLeft = rightChildTextTwo.getLeft();
                            } else if (tagPosition.equals("2")) {
                                topLeft = rightChildTextThree.getLeft();
                            } else if (tagPosition.equals("3")) {
                                topLeft = rightChildTextFour.getLeft();
                            }
                            distance_y = topLeft - left;

                            float curTranslationX = childAt.getTranslationX();
                            float curTranslationY = childAt.getTranslationY();

                            ObjectAnimator obx = ObjectAnimator.ofFloat(childAt, "translationX", curTranslationX, distance_y);
                            ObjectAnimator oby = ObjectAnimator.ofFloat(childAt, "translationY", curTranslationY, -distance_x - height);

                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(sax, say, obx, oby);
                            set.setDuration(2000);
                            set.start();

                            set.addListener(new AnimatorListenerAdapter() {
                                /**
                                 * @param animation
                                 */
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    if (tagPosition.equals("0")) {

                                        cb.setProgress(0);
                                        startTime(currentTwoPotision, loopTimeTwo, loopRateTwo);

                                        if (isTwoMove) {
                                            stopTime();
                                        }
                                    } else if (tagPosition.equals("1")) {
                                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                                            return;
                                        }
                                        cb.setProgress(0);
                                        startTime(currentThreePotision, loopTimeThree, loopRateThree);

                                        if (isThreeMove) {
                                            stopTime();
                                        }

                                    } else if (tagPosition.equals("2")) {

                                        if (!isOneMove) {//代表第一个文本是不是展示出来了
                                            return;
                                        }
                                        if (!isTwoMove) {//代表第一个文本是不是展示出来了
                                            return;
                                        }
                                        cb.setProgress(0);
                                        startTime(currentFourPotision, loopTimeFour, loopRateFour);

                                        if (isFourMove) {
                                            stopTime();
                                        }
                                    } else if (tagPosition.equals("3")) {

                                        if (!isOneMove) {//
                                            return;
                                        }
                                        if (!isTwoMove) {//
                                            return;
                                        }
                                        if (!isThreeMove) {//
                                            return;
                                        }

                                        cb.setProgress(0);
                                    }

                                    llTextParent.setVisibility(View.VISIBLE);//文字的父布局
                                    childAt.setVisibility(View.INVISIBLE);//手指点击的view(移动的view)

                                    View text_bg = llTextBgParent.getChildAt(Integer.parseInt(tagPosition));//自己的背景
                                    View tv_content1 = llTextParent.getChildAt(Integer.parseInt(tagPosition));//自己文本

                                    text_bg.setVisibility(View.INVISIBLE);//吧自己的背景 隐藏掉
                                    tv_content1.setVisibility(View.VISIBLE);//自己文本


                                    if (tagPosition.equals("0")) {
                                        View tv_content11 = llTextParent.getChildAt(1);//自己文本
                                        tv_content11.setVisibility(View.INVISIBLE);//自己文本

                                        View tv_content12 = llTextParent.getChildAt(2);//自己文本
                                        tv_content12.setVisibility(View.INVISIBLE);//自己文本

                                        View tv_content13 = llTextParent.getChildAt(3);//自己文本
                                        tv_content13.setVisibility(View.INVISIBLE);//自己文本
                                    }

                                    //透明度渐变显示
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                    animator.setDuration(1000);
                                    animator.start();
                                    new Handler().
                                            postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (tagPosition.equals("3")) {

                                                        if (pass.equals("1")) {//是否通过 1 是 0 否
                                                            ivJinbiBottom.setVisibility(View.VISIBLE);

                                                            int moneyTop = llMoney.getTop();
                                                            int tiankongkuangTop = rlTiankongkuang.getTop();
                                                            int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(JuZiFeiJieCiShiActivityFourClick.this, 32);

                                                            int screenWidth = MyUtils.getScreenWidth(JuZiFeiJieCiShiActivityFourClick.this) / 3;

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
                                                                        ivXiaolian.setVisibility(View.VISIBLE);
                                                                    }
                                                                }
                                                            });
                                                        }

                                                        if (!isShouldMergeText) {
                                                            isShouldMergeText = true;
                                                            if (voiceListData.size() == 0) {
                                                                if (!isPerformOverMergeText) {
                                                                    mergeText();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }, 1000);
                                }
                            });
                        }
                    }, 0);
                }
            });
        }
    }

    //    提交句子分解的时候，module的值传的有问题，应该传到后台的是4而不是5
    private void mergeText() {

        isPerformOverMergeText = true;
        playLocalVoiceOnLineGroupVoice(sentenceResolveTraining.getGroupRecord());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int screenWidth = MyUtils.getScreenWidth(mContext);
                int gapWidth = screenWidth - (rightChildTextOne.getLeft() * 2) - rightChildTextOne.getWidth() * 4;//4个item的间距

                String tv_content1Str = tv_content1.getText().toString();
                String tv_content2Str = tv_content2.getText().toString();
                String tv_content3Str = tv_content3.getText().toString();
                String tv_content4Str = tv_content4.getText().toString();

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

                RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) llTextParent.getLayoutParams();
                ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parentLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                llTextParent.setLayoutParams(ll_text_parentLayoutParams);
                llTextParent.setBackgroundColor(JuZiFeiJieCiShiActivityFourClick.this.getResources().getColor(R.color.white));

                int currentMaxLength = tv_content1Str.length();
                int currentMinLength = tv_content1Str.length();
                //0代表currentMaxLength是左边的2个卡片的
                //1代表currentMaxLength是右边的2个卡片的
                int orientation = 0;

                for (int i = 2; i < 5; i++) {
                    if (i == 2) {
                        if (currentMaxLength < tv_content2Str.length()) {
                            currentMaxLength = tv_content2Str.length();
                            orientation = 0;
                        } else {
                            currentMinLength = tv_content2Str.length();
                        }
                    }
                    if (i == 3) {
                        if (currentMaxLength < tv_content3Str.length()) {
                            currentMaxLength = tv_content3Str.length();
                            orientation = 1;
                        } else {
                            currentMinLength = tv_content3Str.length();
                        }
                    }
                    if (i == 4) {
                        if (currentMaxLength < tv_content4Str.length()) {
                            currentMaxLength = tv_content4Str.length();
                            orientation = 1;
                        } else {
                            currentMinLength = tv_content4Str.length();
                        }
                    }
                }

                int i = currentMaxLength - currentMinLength;
                if (orientation == 0) {
                    i = -(currentMaxLength - currentMinLength);
                } else {
                    i = (currentMaxLength - currentMinLength);
                }

//                int offsetDistance = 12;//偏移距离  当文字个数不一样的时候
                int offsetDistance = MyUtils.dip2px(JuZiFeiJieCiShiActivityFourClick.this, 10);

                ObjectAnimator obx3 = null;

                ObjectAnimator obx = ObjectAnimator.ofFloat(rightChildTextOne, "translationX", distance_x_left - i * offsetDistance);
                obx.setDuration(1000);
                obx.start();

                ObjectAnimator obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX", distance_x1 - i * offsetDistance);
                obx1.setDuration(1000);
                obx1.start();

                ObjectAnimator obx2 = ObjectAnimator.ofFloat(rightChildTextThree, "translationX", -distance_x1 - i * offsetDistance);
                obx2.setDuration(1000);
                obx2.start();

                obx3 = ObjectAnimator.ofFloat(rightChildTextFour, "translationX", -distance_x_right - i * offsetDistance);
                obx3.setDuration(1000);
                obx3.start();

                obx3.addListener(new AnimatorListenerAdapter() {
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

                        playBgVoice();

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
                                isAnimationFinished = true;

                                if (isBackgroundVoiceFinished && !isYetUploadData && !isExitCurrentActivity) {
                                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                            , stayTime, groupId);
                                }

                            }
                        }, duration * 2);

                        frameAnim1.start();
                        isExecuteThe = true;
                    }
                });

            }
        }, 300);


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
        if (isExitCurrentActivity) {
            isBackgroundVoiceFinished = true;
            return;
        }
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            //关键语句
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(false);//循环播放

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isExitCurrentActivity) {
                        isBackgroundVoiceFinished = true;
                        return;
                    }
                    // 4 开始播放
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    isBackgroundVoiceFinished = true;

                    if (isAnimationFinished && !isYetUploadData && !isExitCurrentActivity) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer1, int i, int i1) {
                    isBackgroundVoiceFinished = true;
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                //关键语句
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }
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
    private boolean isExitCurrentActivity = false;//是否销毁了当前activity
    private boolean isFinishedActivity = false;

    private boolean isAnimationFinished = false;
    private boolean isBackgroundVoiceFinished = false;
    private boolean isYetUploadData = false;

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
        PreferencesHelper helper = new PreferencesHelper(JuZiFeiJieCiShiActivityFourClick.this);
        String token = helper.getToken();

        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "2");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "4");//1名词 2动词 3句子组成 4句子分解
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
        stringStringHashMap.put("stayTime", stayTime);
        stringStringHashMap.put("groupId", groupId);
        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());

//        if (isExitCurrentActivity) {
//            return;
//        }
        if (isFinishedActivity) {
            return;
        }

        isYetUploadData = true;

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .tag(JuZiFeiJieCiShiActivityFourClick.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiFeiJieCiShiActivityFourClick.this, this));
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
            (JuZiFeiJieCiShiActivityFourClick.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
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
        if (object.equals(24)) {//强化物添加金币

            String result = (String) data;
            (JuZiFeiJieCiShiActivityFourClick.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (isFinishedActivity) {
                            return;
                        }

                        if (code.equals("200")) {
                            if (null != screenStateListener) {
                                screenListener.unregisterListener();
                                screenStateListener = null;

                                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                                foregroundCallbacks = null;
                            }

                            Gson gson = new Gson();
                            LookLatelyBean model1 = gson.fromJson(result,
                                    new TypeToken<LookLatelyBean>() {
                                    }.getType());


                            position = position + 1;

                            if (position < juzibean.getCosentenceResolveTestde().size()) {

                                if (pass.equals("1")) {//是否通过 1 是 0 否
                                    addFortifier("4", "0");
                                    ++gold;
                                }
                                if (isExitCurrentActivity) {
                                    return;
                                }
                                ReleasePlayer();
                                Intent intent = new Intent(JuZiFeiJieCiShiActivityFourClick.this, JuZiFeiJieCiShiActivityFourClick.class);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("position", position);
                                intent.putExtra("juzibean", juzibean);
                                startActivity(intent);
                                finish();
                            } else {
                                if (pass.equals("1")) {//是否通过 1 是 0 否
                                    addFortifier("4", "0");
                                    ++gold;
                                }
//                                这个是6组的，6组都要80%对吧，如果成组的3组训练测试，老是不能连续3个80%。是不是，
//                                先循环好成组的啊。等成组的3个百分之80.过了再次循环分解的啊


//                                1在做名词 2在做动词  3句子成组 4句子分解 5 全部通关
                                String module = model1.getData().getModule();
                                if (module.equals("5")) {
                                    if (isExitCurrentActivity) {
                                        return;
                                    }
//                                    名词,动词,句子分解通关,进入通关成功的页面,3S返回首页  句子分组通关后进入通关成功的页面3S自动进入句子分解模块
                                    ReleasePlayer();
                                    Intent intent1 = new Intent(JuZiFeiJieCiShiActivityFourClick.this, PinTuAllFlyActivity.class);
                                    intent1.putExtra("anInt1", gold);
                                    intent1.putExtra("juzifenjie", true);
                                    startActivity(intent1);
                                    finish();
                                    return;
                                }

                                if (gold >= 10) {//达到了10个金币
                                    if (isExitCurrentActivity) {
                                        return;
                                    }
                                    ReleasePlayer();
                                    Intent intent = new Intent(JuZiFeiJieCiShiActivityFourClick.this, PinTuActivity.class);
                                    intent.putExtra("anInt1", gold);
                                    intent.putExtra("juzifenjie", true);
                                    intent.putExtra("bean", juzifenjieGuoGuan);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (isExitCurrentActivity) {
                                        return;
                                    }
                                    ReleasePlayer();

                                    Intent intent = new Intent(JuZiFeiJieCiShiActivityFourClick.this, LetsTestToTrainActivity.class);
                                    intent.putExtra("type", "juzifenjie");
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
            (JuZiFeiJieCiShiActivityFourClick.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(4)) {//强化物添加金币

            String result = (String) data;
            (JuZiFeiJieCiShiActivityFourClick.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
                                if (module.equals("4")) {
                                    if (gold != 0) {
                                        JuZiFeiJieCiShiActivityFourClick.this.gold = gold;
                                        tvMoney.setText("x " + gold);
                                        ivXiaolian.setVisibility(View.VISIBLE);
                                    } else {
                                        ivXiaolian.setVisibility(View.GONE);
                                    }

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
        PreferencesHelper helper = new PreferencesHelper(JuZiFeiJieCiShiActivityFourClick.this);
        String token = helper.getToken();

        String url = Setting.getSystemStatistics();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        if (isFinishedActivity) {
            return;
        }

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(24)//XX接口的标识
                .tag(JuZiFeiJieCiShiActivityFourClick.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiFeiJieCiShiActivityFourClick.this, this));
    }

    /**
     *
     */
    private void getFortifier() {
        PreferencesHelper helper = new PreferencesHelper(JuZiFeiJieCiShiActivityFourClick.this);
        String token = helper.getToken();

        String url = Setting.getFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .tag(JuZiFeiJieCiShiActivityFourClick.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiFeiJieCiShiActivityFourClick.this, this));
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     *               1名词 2动词 3句子组成 4句子分解
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(JuZiFeiJieCiShiActivityFourClick.this);
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
                .tag(JuZiFeiJieCiShiActivityFourClick.this)
                .build()
                .execute(new BaseStringCallback_Host(JuZiFeiJieCiShiActivityFourClick.this, this));
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

    }

    @OnClick({R.id.iv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                ReleasePlayer();
                isFinishedActivity = true;
                isExitCurrentActivity = true;

                if (null != screenStateListener) {
                    screenListener.unregisterListener();
                    screenStateListener = null;

                    ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                    foregroundCallbacks = null;
                }
                finish();
                break;


            case R.id.fl_choose1:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        ReleasePlayer();
        isFinishedActivity = true;
        isExitCurrentActivity = true;

        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }
        super.onBackPressed();
    }

    List<String> voiceListData = new ArrayList<>();

    private void doAnim(Boolean isMergeText) {
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

                if (!isMergeText) {
                    currentClickOneStartTime = System.currentTimeMillis();
                    startTime(currentFirstPotision, loopTimeOne, loopRateOne);
                }


            }
        }, duration * 2);

        frameAnim1.start();
    }


    private void playLocalVoice(String videoName, Boolean isFirst) {
        if (isExitCurrentActivity) {
            return;
        }
//        if (player != null) {
//            player.stop();
//            //关键语句
//            player.reset();
//            player.release();
//            player = null;
//        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
//            player.prepare();
//            player.start();

            //3 准备播放
            player.prepareAsync();
            //3.1 设置一个准备完成的监听
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isExitCurrentActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                    frameAnim1.stop();
                    FristVoiceFinished = true;

                }
            });

            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
                //关键语句
                player.reset();
                player.release();
                player = null;
            }
            Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }
    }

    private Handler voiceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {

                if (FristVoiceFinished) {
                    if (voiceListData.size() != 0) {
                        if (!isPlayingVoice && !isExitCurrentActivity) {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    }
                }

                voiceHandler.sendEmptyMessage(1);
            }

        }
    };

    /**
     * 在线
     *
     * @param videoName
     */
    private void playLocalVoiceOnLine(String videoName) {
        isPlayingVoice = true;

        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() != 0) {
                voiceListData.remove(0);
            }

            if (voiceListData.size() == 0) {
//                        课件做完，上方动画放大缩小，并且所有音频播放完了，没有立即进入下一课件
                if (isShouldMergeText) {
                    if (!isPerformOverMergeText) {
                        mergeText();
                    }
                }
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
                    if (isExitCurrentActivity) {
                        isPlayingVoice = false;
                        return;
                    }

                    // 4 开始播放
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    isPlayingVoice = false;

                    if (voiceListData.size() != 0) {
                        voiceListData.remove(0);
                    }

                    if (voiceListData.size() == 0) {
//                        课件做完，上方动画放大缩小，并且所有音频播放完了，没有立即进入下一课件
                        if (isShouldMergeText) {
                            if (!isPerformOverMergeText) {
                                mergeText();
                            }
                        }
                    }


                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlaye1r, int i, int i1) {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                //关键语句
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }

    }

    private void playLocalVoiceOnLineGroupVoice(String videoName) {
        if (isExitCurrentActivity) {
            return;
        }

        if (TextUtils.isEmpty(videoName)) {
            return;
        }
        if (null == player) {
            player = new MediaPlayer();
        }
//
        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径
        try {
            player.setDataSource(videoName);
            //3 准备播放
            player.prepareAsync();
            //3.1 设置一个准备完成的监听
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isExitCurrentActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                }
            });

            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
                //关键语句
                player.reset();
                player.release();
                player = null;
            }
            Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        Log.e("aaa", "onDestroy: ");
        EventBusUtil.unregister(this);

        isFinishedActivity = true;
        isExitCurrentActivity = true;
        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }

        handler.removeCallbacksAndMessages(null);
        voiceHandler.removeCallbacksAndMessages(null);
        ReleasePlayer();
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

        OkHttpUtils.getInstance().cancelTag(this);

        super.onDestroy();
    }

    /**
     * 释放播放器资源
     */
    private void ReleasePlayer(MediaPlayer player) {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            //关键语句
            player.reset();
            player.release();
            player = null;
        }
    }

    /**
     * 释放播放器资源
     */
    private void ReleasePlayer() {
//        BitmapUtils.tryRecycleAnimationDrawable(frameAnim1);
//        if (player != null) {
//            player.stop();
//            //关键语句
//            player.reset();
//            player.release();
//            player = null;
//        }
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            //关键语句
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap;

}


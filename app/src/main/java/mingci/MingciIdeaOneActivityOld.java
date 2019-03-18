package mingci;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.LetsTestActivity;
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
import com.qlzx.mylibrary.util.ToastUtil;
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

import bean.MingciBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenlipeng on 2018/11/5 0005
 * describe:  名词意义页面  4选择2个
 */
public class MingciIdeaOneActivityOld extends BaseActivity implements View.OnClickListener, AsyncRequest {

    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.cb)
    CircleBarTime cb;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
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
    private MediaPlayer player;

    private boolean isMediaPlayerCompletion = false;//标识。一开始的语言已经结束了
    private List<String> voiceListData = new ArrayList<>();
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            isMediaPlayerCompletion = true;
            //进度开启

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

            Animation animation = android.view.animation.AnimationUtils.loadAnimation(MingciIdeaOneActivityOld.this, R.anim.anim_scale_pic);
            ivImg.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    View childAt = llClickLayout.getChildAt(0);
                    View childAt1 = llClickLayout.getChildAt(1);

                    if (childAt.isClickable() && childAt1.isClickable()) {
                        currentClickOneStartTime = System.currentTimeMillis();
                        startTime(currentFirstPotision, loopTimeFirst, loopRateFirst);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    };
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

//                        错误类型 1干扰形容词 2干扰名词 3预选形容词 4预选名词 只有名词意义级别才统计错误类型
                        if (TextUtils.isEmpty(errorType))
                            errorType = "4";
                    }
                } else {
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                    rl_hand.setVisibility(View.VISIBLE);
                    wave_cirlce_view.startWave();

                    errorType = " 3";
//                    String cardAdjectiveChar = nounSenseBean.getList().get((int) msg.obj).getCardAdjectiveChar();
//                    String isAdj = nounSenseBean.getList().get((int) msg.obj).getIsAdj();
//                    String cardAdjectiveChar1 = nounSenseBean.getCardAdjectiveChar();
//                    String cardNounChar = nounSenseBean.getCardNounChar();
                }
            }
        }
    };
    private Timer timer;
    private int currentLoopTime;
    private int executeInterval = 100;
    private int currentFirstPotision;
    private int currentTwoPotision;
    private int loopTimeFirst;
    private double loopRateFirst;
    private int loopTimeSecond;
    private double loopRateSecond;
    private String disTurbName = "";
    private String errorType = "";
    private boolean playNextCardVoice = false;
    private boolean readyPlayNextOrderOne = false;
    private boolean readyPlayNextOrderTwo = false;
    private boolean isExitCurrentActivity = false;
    private ScreenListener screenListener;
    private ScreenListener.ScreenStateListener screenStateListener;

    private boolean isOneMove = false;
    private boolean isTwoMove = false;
    private MediaPlayer mediaPlayer;
    private boolean isPlayingVoice = false;
    private boolean isFinishedActivity = false;
    private boolean isExecuteThe = false;
    private boolean isShouldMerget = false;


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

    //[步骤]
//
//    进入名词结构意义测试，点击正确的图片
//
//[结果]
//
//    点击正确的图片，进度圈没有暂停，图片进入上方框内才暂停
//[期望]
//
//    只要点击正确的图片，进度圈就暂停
//    10.	测试级，意义级，所有进度圈计时，是在上方静态图片放大突出并缩回原形后开始计时的，计时时间取值后台设置
    private int position = 0;
    private int currentSize = 2;
    private int bottomClickSize = 4;

    private View leftChildTextOne;
    private View rightChildTextTwo;
    private TextView tv_content1;
    private TextView tv_content2;
    private MingciBean.NounSenseBean nounSenseBean;
    private boolean isFrontYetClick = false;
    private MingciBean model;

    private boolean isFirstInto = true;
    private boolean noPlayTwoCardVoice = false;
    private boolean isTwoInto = false;

    private boolean isAnimationFinished = false;
    private boolean isBackgroundVoiceFinished = false;
    private boolean isYetUploadData = false;
    private boolean isShouldAddTrainingResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingci_diea_one1);
        ButterKnife.bind(this);

        ivHome.setOnClickListener(this);
        position = getIntent().getIntExtra("position", 0);
        groupId = getIntent().getStringExtra("groupId");
        model = (MingciBean) getIntent().getSerializableExtra("model");
//        groupId = getIntent().getIntExtra("groupId", 0) + "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

        setScreenLock();
        ForegroundCallbacks.get().addListener(foregroundCallbacks);
        EventBusUtil.register(this);

        if (null != model) {
            setDataIntoView();
        } else {
            getNoun();
        }
        llIndicator.setSelectedPosition(position);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        finish();
    }

    ForegroundCallbacks.Listener foregroundCallbacks = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
            isExitCurrentActivity = false;

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
                    } else if (isShouldMerget) {
                        mergeText();
                    }

                }
            }
        }

        @Override
        public void onBecameBackground() {
            isExitCurrentActivity = true;
            if (null != player && player.isPlaying()) {
                if (voiceListData.size() != 0) {
                    voiceListData.remove(0);
                }
                player.stop();
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                if (voiceListData.size() != 0) {
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
        screenStateListener = new ScreenListener.ScreenStateListener() {
            @Override
            public void onUserPresent() {
                Log.e("onUserPresent", "onUserPresent");
            }

            @Override
            public void onScreenOn() {
                isExitCurrentActivity = false;

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
                isExitCurrentActivity = true;
                if (null != player && player.isPlaying()) {
                    if (voiceListData.size() != 0) {
                        voiceListData.remove(0);
                    }
                    player.stop();
                }

                if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                    if (voiceListData.size() != 0) {
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
        screenListener = new ScreenListener(this);
        screenListener.begin(screenStateListener);
    }

    /**
     * 名词
     */
    private void getNoun() {
        PreferencesHelper helper = new PreferencesHelper(MingciIdeaOneActivityOld.this);
        String token = helper.getToken();
        Log.e("数据", "token" + token);
        String url = Setting.getNoun();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .tag(MingciIdeaOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciIdeaOneActivityOld.this, this));
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
            (MingciIdeaOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//                            JSONObject data1 = jsonObject.getJSONObject("nounTraining");
//                            Log.e("数据", data1.toString());
                            Gson gson = new Gson();
                            model = gson.fromJson(result,
                                    new TypeToken<MingciBean>() {
                                    }.getType());

                            setDataIntoView();

                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (object.equals(2)) {//上传结果
            String result = (String) data;
            (MingciIdeaOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (isExitCurrentActivity) {
                            return;
                        }

                        if (code.equals("200")) {
                            if (isFinishedActivity) {
                                return;
                            }

                            if (null != screenStateListener) {
                                screenListener.unregisterListener();
                                screenStateListener = null;

                                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                                foregroundCallbacks = null;
                            }

                            List<MingciBean.NounSenseBean> nounSense = model.getNounSense();
                            position++;
                            if (position < nounSense.size()) {

                                groupId = (String) jsonObject.getString("groupId");

                                Intent intent = new Intent(MingciIdeaOneActivityOld.this, MingciIdeaOneActivityOld.class);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("position", position);
                                intent.putExtra("model", model);
                                startActivity(intent);
                                finish();
                            } else {
//                                7.做完两组名词意义应该直接返回首页

                                PreferencesHelper helper = new PreferencesHelper(MingciIdeaOneActivityOld.this);
                                String currentMingciIdeaNumber = helper.getString("xiaoyudi", "currentMingciIdeaNumber", "0");
                                if (Integer.parseInt(currentMingciIdeaNumber) == 0) {//做了第一遍了

                                    helper.saveString("xiaoyudi", "currentMingciIdeaNumber", "1");

//                                    1在做名词 2在做动词  3句子成组 4句子分解 5 全部通关
                                    Intent intent = new Intent(MingciIdeaOneActivityOld.this, LetsTestActivity.class);
                                    intent.putExtra("type", "mingciyiyi");
                                    startActivity(intent);
                                    finish();
                                } else if (Integer.parseInt(currentMingciIdeaNumber) == 1) {//做了第二遍了
                                    helper.saveString("xiaoyudi", "currentMingciIdeaNumber", "0");
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
    }

    private void setDataIntoView() {
        nounSenseBean = model.getNounSense().get(position);

        coursewareId = nounSenseBean.getId();
        name = nounSenseBean.getGroupChar();

        asyncGet(nounSenseBean.getGroupImage(), 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-这是什么样的东西.MP3");
            }
        }, 1000);
        voiceListData.add("他在干什么");

        int fristAssistTime = nounSenseBean.getFristAssistTime();
        int secondAssistTime = nounSenseBean.getSecondAssistTime();
        loopTimeFirst = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateFirst = 100.00 / loopTimeFirst;//每次循环，圆环走的度数

        loopTimeSecond = secondAssistTime * 1000 / executeInterval;//循环次数
        loopRateSecond = 100.00 / loopTimeSecond;//每次循环，圆环走的度数

        int grapWidth = MyUtils.dip2px(MingciIdeaOneActivityOld.this, 26);
        for (int i = 0; i < currentSize; i++) {//文字背景
            View inflate = LayoutInflater.from(MingciIdeaOneActivityOld.this).inflate(R.layout.text_bg_big, null);
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

        for (int i = 0; i < currentSize; i++) {//文字
            View inflate = LayoutInflater.from(MingciIdeaOneActivityOld.this).inflate(R.layout.text_layout_big, null);
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

        tv_content1.setText(nounSenseBean.getCardAdjectiveChar());
        tv_content2.setText(nounSenseBean.getCardNounChar());

        for (int i = 0; i < bottomClickSize; i++) {//下面4个按钮
            View inflate = LayoutInflater.from(MingciIdeaOneActivityOld.this).inflate(R.layout.click_layout, null);
            if (i == 0) {
                llClickLayout.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                if (bottomClickSize != 4) {
                    lp.setMargins(25, 0, 0, 0);
                }
                inflate.setLayoutParams(lp);
                llClickLayout.addView(inflate);
            }
        }

        voiceListData.add(nounSenseBean.getCardAdjectiveRecord());
        voiceListData.add(nounSenseBean.getCardNounRecord());
        Log.e("testvoicemingciidea", nounSenseBean.getCardAdjectiveRecord());
        Log.e("testvoicemingciidea", nounSenseBean.getCardNounRecord());

        for (int j = 0; j < bottomClickSize; j++) {
            ImageView iv_click_pic = (ImageView) llClickLayout.getChildAt(j).findViewById(R.id.iv_click_pic);
            GlideUtil.display(MingciIdeaOneActivityOld.this, nounSenseBean.getList().get(j).getCardAdjectiveImage(), iv_click_pic);

            TextView tv_content11 = (TextView) llClickLayout.getChildAt(j).findViewById(R.id.tv_choose2);
            tv_content11.setText(nounSenseBean.getList().get(j).getCardAdjectiveChar());

//                                设置tag
            View childAt = llClickLayout.getChildAt(j);
            String cardAdjectiveChar1 = nounSenseBean.getCardAdjectiveChar();//前面文本
            String cardNounChar = nounSenseBean.getCardNounChar();//后面文本

            String cardAdjectiveChar = nounSenseBean.getList().get(j).getCardAdjectiveChar();//自己的文本

            if (cardAdjectiveChar.equals(cardAdjectiveChar1)) {
                childAt.setTag("0");
                currentFirstPotision = j;
            }
            if (cardAdjectiveChar.equals(cardNounChar)) {
                childAt.setTag("1");
                currentTwoPotision = j;
            }

        }

        initClick();
    }


    private void initClick() {
        //点击事件
        for (int i = 0; i < llClickLayout.getChildCount(); i++) {
            final View childAt = llClickLayout.getChildAt(i);//第几个
            final int currentPosition = i;

            int finalI = i;
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cardAdjectiveChar = nounSenseBean.getList().get(currentPosition).getCardAdjectiveChar();
                    String isAdj = nounSenseBean.getList().get(currentPosition).getIsAdj();

                    String cardAdjectiveChar1 = nounSenseBean.getCardAdjectiveChar();
                    String cardNounChar = nounSenseBean.getCardNounChar();

                    if (!cardAdjectiveChar.equals(cardAdjectiveChar1) && !cardAdjectiveChar.equals(cardNounChar)) {
//                        string	错误类型 1干扰形容词 2干扰名词 3预选形容词 4预选名词 只有名词意义级别才统计错误类型
                        if (isAdj.equals("1")) {//1是形容词。0为名词
                            errorType = " 1";
                            disTurbName = cardAdjectiveChar;
                        } else {
                            errorType = "2";
                            disTurbName = cardAdjectiveChar;
                        }
                        if (!isFrontYetClick) {
                            View childAt1 = llClickLayout.getChildAt(currentFirstPotision);
                            if (childAt1.isClickable()) {
                                final RelativeLayout rl_hand = childAt1.findViewById(R.id.rl_hand);
                                final WaveCircleView wave_cirlce_view = childAt1.findViewById(R.id.wave_cirlce_view);

                                wave_cirlce_view.startWave();
                                rl_hand.setVisibility(View.VISIBLE);
                            }

                        } else {
                            View childAt1 = llClickLayout.getChildAt(currentTwoPotision);
                            if (childAt1.isClickable()) {
                                final RelativeLayout rl_hand = childAt1.findViewById(R.id.rl_hand);
                                final WaveCircleView wave_cirlce_view = childAt1.findViewById(R.id.wave_cirlce_view);

                                wave_cirlce_view.startWave();
                                rl_hand.setVisibility(View.VISIBLE);
                            }
                        }
                        return;
                    }

                    String currentIntoLocation = (String) childAt.getTag();
                    if (currentIntoLocation.equals("0")) {
                        isFrontYetClick = true;//是否第一个已经点击了

                    } else {
                        if (!isFrontYetClick) {

                            //正确小手辅助出现
                            View childAt = llClickLayout.getChildAt(currentFirstPotision);
                            final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                            if (rl_hand.getVisibility() == View.GONE) {//是否通过 1 是 0 否
                                rl_hand.setVisibility(View.VISIBLE);
                                pass = "0";

                                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                wave_cirlce_view.startWave();

                                if (TextUtils.isEmpty(errorType))
                                    errorType = "4";
                            }
                            return;
                        }
                    }

                    String tagPosition = (String) childAt.getTag();
                    if (tagPosition.equals("0")) {
                        stopTime();
                        currentClickTwoStartTime = System.currentTimeMillis();

                        long l = -(currentClickOneStartTime - System.currentTimeMillis()) / 1000;

                        if (l > 50) {
                            l = 1;
                        }
                        stayTimeList = l + "";
                        isOneMove = true;
                    }

                    if (tagPosition.equals("1")) {
                        long l = (System.currentTimeMillis() - currentClickTwoStartTime) / 1000;
                        if (l > 50) {
                            l = 1;
                        }
                        stayTimeList = stayTimeList + "," + l + "";

                        stopTime();
                        isTwoMove = true;
                    }

                    childAt.setClickable(false);
                    AnimationHelper.startScaleAnimation(mContext, childAt);

                    if (voiceListData.size() == 3) {//代表上一个没有播完

                        if (currentIntoLocation.equals("0")) {//播放不同的音乐
                            readyPlayNextOrderOne = true;
                        } else {
                            readyPlayNextOrderTwo = true;
                        }

                    } else if (voiceListData.size() == 2) {//代表上一个播完
                        if (null != player && player.isPlaying()) {
                            playNextCardVoice = true;
                        } else {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    } else if (voiceListData.size() == 1) {//代表上一个播完
                        playLocalVoiceOnLine(voiceListData.get(0));
                    }

                    //  把小手的布局隐藏掉
                    View childAt = llClickLayout.getChildAt(finalI);
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    if (rl_hand.getVisibility() == View.VISIBLE) {
                        pass = "0";
//                        错误类型 1干扰形容词 2干扰名词 3预选形容词 4预选名词 只有名词意义级别才统计错误类型
//                        if (isAdj.equals("1")) {//1是形容词。0为名词
//                            errorType = " 3";
//                        } else {
//                            errorType = "4";
//                        }
                    }

//                3预选形容词  第一张卡片，超时了。
//                4预选名词    第一张正确的卡片没有点，直接点击了第二个。或者是第一个卡片点击了，但是第二张卡片超时了。
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
                            int distance_y;

                            String currentIntoLocation = (String) childAt.getTag();
                            if (currentIntoLocation.equals("0")) {
                                int topLeft = leftChildTextOne.getLeft();
                                distance_y = topLeft - left;
                            } else {
                                int topLeft = rightChildTextTwo.getLeft();
                                distance_y = topLeft - left;
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
                                /**
                                 * @param animation
                                 */
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    llTextParent.setVisibility(View.VISIBLE);//文字的父布局
                                    childAt.setVisibility(View.INVISIBLE);//手指点击的view(移动的view)

                                    View text_bg = llTextBgParent.getChildAt(Integer.parseInt(currentIntoLocation));//自己的背景
                                    View tv_content1 = llTextParent.getChildAt(Integer.parseInt(currentIntoLocation));//自己文本

                                    text_bg.setVisibility(View.INVISIBLE);//吧自己的背景 隐藏掉
                                    tv_content1.setVisibility(View.VISIBLE);//自己文本

                                    if (currentIntoLocation.equals("0")) {
//                                        View text_bg11 = llTextBgParent.getChildAt(1);//自己的背景
                                        View tv_content11 = llTextParent.getChildAt(1);//自己文本
//                                        text_bg11.setVisibility(View.INVISIBLE);//吧自己的背景 隐藏掉
                                        tv_content11.setVisibility(View.INVISIBLE);//自己文本

                                    }

                                    if (tagPosition.equals("0")) {
                                        cb.setProgress(0);
                                        startTime(currentTwoPotision, loopTimeSecond, loopRateSecond);

                                        View childAt = llClickLayout.getChildAt(0);
                                        View childAt1 = llClickLayout.getChildAt(1);

                                        if (!childAt.isClickable() && !childAt1.isClickable()) {
                                            cb.setProgress(0);
                                            stopTime();
                                        }
                                    }

                                    if (tagPosition.equals("1")) {
                                        cb.setProgress(0);
                                        stopTime();
                                    }

                                    //透明度渐变显示
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                    animator.setDuration(1000);
                                    animator.start();
                                    new Handler().
                                            postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

//                                                    Log.e("数据", "run: initClick Handler ");
                                                    String currentIntoLocation = (String) childAt.getTag();
                                                    if (null != currentIntoLocation && currentIntoLocation.equals("1")) {
                                                        if (!noPlayTwoCardVoice) {
//                                                            Log.e("数据", "run: initClick mergeText 前面");

                                                            if (!isExitCurrentActivity) {
                                                                mergeText();
                                                            } else {
                                                                isShouldMerget = true;
                                                            }
                                                        }
                                                    }

                                                }
                                            }, 2500);
                                }
                            });
                        }
                    }, 1000);
                }
            });
        }


    }

    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {
        (MingciIdeaOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(MingciIdeaOneActivityOld.this, errorMessage);
            }
        });

    }

    /*这个方法是。切换到后台，然后切换回来。播放语音的
     */
    private void playLocalVoiceOnLineOnScreenChange(String videoName) {

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
                    if (isExitCurrentActivity) {
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

    private void playLocalVoiceOnLineOnOrderClickAllCard(String videoName, Boolean isShouldMerge) {
        if (isExitCurrentActivity) {
            return;
        }
        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() == 1) {//
                voiceListData.remove(0);

                if (isShouldMerge) {
                    if (!isExitCurrentActivity) {
                        mergeText();
                    } else {
                        isShouldMerget = true;
                    }
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
                    if (voiceListData.size() == 2) {//第二个语音说完了
                        voiceListData.remove(0);

                        playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), isShouldMerge);
                    } else if (voiceListData.size() == 1) {//第二个语音说完了
                        voiceListData.remove(0);

                        if (isShouldMerge) {
                            if (!isExitCurrentActivity) {
                                mergeText();
                            } else {
                                isShouldMerget = true;
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在线
     *
     * @param videoName
     */
    private void playLocalVoiceOnLine(String videoName) {
        if (isExitCurrentActivity) {
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

    private void playLocalVoice(String videoName) {
        if (isExitCurrentActivity) {
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
            player.setOnCompletionListener(completionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                OkHttpUtils.getInstance().cancelTag(MingciIdeaOneActivityOld.this);
                isExitCurrentActivity = true;
                isFinishedActivity = true;

                if (null != screenStateListener) {
                    screenListener.unregisterListener();
                    screenStateListener = null;

                    ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                    foregroundCallbacks = null;
                }
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        OkHttpUtils.getInstance().cancelTag(MingciIdeaOneActivityOld.this);
        isExitCurrentActivity = true;
        isFinishedActivity = true;

        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }
        super.onBackPressed();
    }

    private void mergeText() {
//        另外，儿童选择完正确的2个答案后，其余的2个错误答案要被清除
//                笔消失动画
        String cardAdjectiveChar1 = nounSenseBean.getCardAdjectiveChar();
        String cardNounChar = nounSenseBean.getCardNounChar();

        for (int i = 0; i < llClickLayout.getChildCount(); i++) {

            String cardAdjectiveChar = nounSenseBean.getList().get(i).getCardAdjectiveChar();
            if (!cardAdjectiveChar.equals(cardAdjectiveChar1) && !cardAdjectiveChar.equals(cardNounChar)) {
                View childAt = llClickLayout.getChildAt(i);
                AnimationHelper.startPaintGoneAnimation(MingciIdeaOneActivityOld.this, childAt);
            }

        }
        AnimationHelper.startScaleAnimation(MingciIdeaOneActivityOld.this, ivImg);
//        AnimationHelper.startPaintGoneAnimation(MingciIdeaOneActivity.this, ll_choose4);
//        player.stop();
        playLocalVoiceOnLine(nounSenseBean.getGroupRecord());

        AnimationHelper.startScaleAnimation(MingciIdeaOneActivityOld.this, ivImg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playBgVoice();
                String tv_content1Str = tv_content1.getText().toString();
                String tv_content2Str = tv_content2.getText().toString();

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

                RelativeLayout.LayoutParams ll_text_parentLayoutParams = (RelativeLayout.LayoutParams) llTextParent.getLayoutParams();
                ll_text_parentLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                ll_text_parentLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                llTextParent.setLayoutParams(ll_text_parentLayoutParams);
                llTextParent.setBackgroundColor(MingciIdeaOneActivityOld.this.getResources().getColor(R.color.white));

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
                        llTextParent.setBackground(null);

                        int width = llTextParent.getWidth();
                        int height = llTextParent.getHeight();
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

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (!isExitCurrentActivity) {
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
        if (isExitCurrentActivity) {
            return;
        }
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
        } catch (Exception e) {
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
        PreferencesHelper helper = new PreferencesHelper(MingciIdeaOneActivityOld.this);
        String token = helper.getToken();
        if (groupId == null) {
            groupId = "";
        }

        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "3");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "1");//1名词 2动词 3句子组成 4句子分解
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
        stringStringHashMap.put("disTurbName", disTurbName);
        stringStringHashMap.put("errorType", errorType);
        stringStringHashMap.put("stayTime", stayTime);
        stringStringHashMap.put("groupId", groupId);
        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());

        if (isFinishedActivity) {
            return;
        }

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .tag(MingciIdeaOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciIdeaOneActivityOld.this, this));
    }


    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }
        isFinishedActivity = true;
        isExitCurrentActivity = true;

        handler.removeCallbacksAndMessages(null);
        if (player != null)
            player.stop();
        super.onDestroy();
    }

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
                    if (i == 1) {
                        message.what = 1;
                        message.obj = response.body().bytes();
                        handler.sendMessage(message);
                    }

                } else {
                    handler.sendEmptyMessage(3);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        /**
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    BitmapDrawable bd = new BitmapDrawable(bitmap);//// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
                    ivImg.setImageDrawable(bd);

                    break;
                case 3://失败
                    break;
                default:
                    break;
            }
        }
    };


}

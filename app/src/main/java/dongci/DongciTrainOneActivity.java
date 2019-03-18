package dongci;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.LetsTestActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.entity.BreakNetBean;
import com.easychange.admin.smallrain.entity.CustomsClearanceSuccessBean;
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

import bean.DongciBean;
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
 * Created by chenlipeng on 2018/11/7 0007
 * describe:  动词训练  是一个，只跑到右边
 * DongciActivity
 */
public class DongciTrainOneActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {
    private static final String TAG = "DongciTrainOneActivity";
//2. 儿童只需完成两组意义测试级就结束，无论其正确率如何。之后进入动词训练解锁。进入动词短语训练级。
//            3. 动词结构训练：动词对应的录音（玩：玩什么、穿：穿什么、喝：喝什么）   j
//            4. 通关成功页面怎么都是第一关的（通关成功的四个页面应该都给你们了）
//            5. 句子分解：做课件中途退出，回到首页后还有录音播放
//6. 句子分解：字体融合后点击退出，返回首页之后自动进入课件了   j
//7. 句子分解测试：有一组“女孩玩积木”的录音播放有问题
//8. 句子分解：个别的课件播放录音不全
//9. 家长中心：所有课件都通关后训练档案页面不显示
//10. 家长中心：修改手机号首次发送验证码，手机号接收不到
//11. 家长中心：修改手机号，输入错误的验证码后，再输入正确的验证码提示错误

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
    @BindView(R.id.fL_big_pic)
    FrameLayout fLBigPic;
    @BindView(R.id.rl_my)
    RelativeLayout rlMy;
    @BindView(R.id.iv_click_pic)
    ImageView ivClickPic;
    @BindView(R.id.tv_choose2)
    TextView tvChoose2;
    @BindView(R.id.ll_choose2)
    LinearLayout llChoose2;
    @BindView(R.id.wave_cirlce_view)
    WaveCircleView waveCirlceView;
    @BindView(R.id.iv_point)
    ImageView ivPoint;
    @BindView(R.id.rl_hand)
    RelativeLayout rlHand;
    private AnimationDrawable frameAnim;

    private MediaPlayer player;
    private int position = 0;
    private List<Drawable> imagesList = new ArrayList<>();
    private AnimationDrawable frameAnim1;
    private DongciBean dongciBean;
    private int helpTime;
    private int currentSize = 2;
    private View leftChildTextOne;
    private View rightChildTextTwo;
    private TextView tv_content1;
    private TextView tv_content2;
    private DongciBean.VerbTrainingBean verbTrainingBean;
    private Timer timer;
    private int currentLoopTime = 0;
    private int executeInterval = 100;
    private int loopTimeOne;
    private double loopRateOne;
    private int coursewareId;//课件id
    private String startTime;
    private String name;
    private String pass = "1";//是否通过 1 是 0 否
    private String stayTimeList = "";
    private String stayTime;
    private String groupId = "";
    private long startTimeMillis;
    private long currentClickOneStartTime;
    private int length;
    private String scene;
    private String[] split;
    private boolean middleVoice_Finished = false;
    private MediaPlayer mediaPlayer;
    private boolean isClickCard = false;
    private String verbChar = "";
    private boolean isFinishedActivity = false;

    private boolean isQuitActivity = false;
    private ScreenListener l;
    private Timer timerDelay10s;
    private boolean shouldJumpToNextPage = false;
    private String goToNextActivityData = "";
    private long currentPlayTime;
    private AnimatorSet set;
    private boolean isFirstInto = true;
    private boolean isTwoInto = true;
    private boolean isIntoPlayCardRecordMethod = false;
    private boolean isCardRecordFinished = false;
    private ScreenListener.ScreenStateListener screenStateListener;
    private ScreenListener screenListener;
    private boolean isMergeText = false;//标志  是否融合了文字
    private boolean nowPlayingCardRecord = false;//标志  是否正在播放卡片的录音
    //    private boolean isYetPlayerVerbRecord = false;//是否已经播了动词
    private boolean isVerbRecordFinished = false;//是否已经播了动词
    private boolean isPlayingVerbRecord = false;
    private boolean preparePlayerVerRecord = false;
    private boolean flyAnimationIsFinished = false;
    private boolean isPlayCardRecord = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongci_xunlian);
        ButterKnife.bind(this);

        DisplayMetrics dm = new DisplayMetrics();
        DongciTrainOneActivity.this.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int dwidth = dm.widthPixels;
        int dheight = dm.heightPixels;

        Log.e(TAG, "onCreate: " + dheight);
//        1812  华为
//       2128   小米长屏幕
//        1768   杜许杰手机

        ivHome.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

        position = getIntent().getIntExtra("position", 0);
        dongciBean = (DongciBean) getIntent().getSerializableExtra("model");
        length = getIntent().getIntExtra("length", -1);

        scene = getIntent().getStringExtra("scene");
        groupId = getIntent().getStringExtra("groupId");

        if (length != -1 && !TextUtils.isEmpty(scene)) {
            groupId = getIntent().getStringExtra("groupId");
            position = length;
            getVerb();
            return;
        }

        if (null != dongciBean) {
            setData();
        } else {
            getVerb();
        }

        setScreenLock();
        ForegroundCallbacks.get().addListener(foregroundCallbacks);
//        1）点击卡片，当下方两张卡片飞到上方空框内时，点击右上方退出按钮，
// 回到首页，录音还会播放，播放完录音后，自动进入下一课件。

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
            isQuitActivity = false;
            if (isFirstInto) {
                isFirstInto = false;
            } else {

                if (!isCardRecordFinished && !isMergeText && isClickCard) {
                    playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
                } else if (isCardRecordFinished = true && !isMergeText && isClickCard) {
                    mergeText();
                }

            }
        }

        @Override
        public void onBecameBackground() {//后台
            isQuitActivity = true;

            if (null != player && player.isPlaying()) {
                player.stop();
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying() && nowPlayingCardRecord) {
                mediaPlayer.stop();
                isCardRecordFinished = true;
            }


        }
    };

//    10、动词训练，“吃什么”的语音，10s内如果没有点击，就会播放，如果一直没有点击，那就每隔20s播放一次。
//    跳转页面功能
//    记录走到那里了。  如果走到这里了。 后台状态  。禁止走。  这时候切换到前台。接着走。
//    如果没有走到这里。 不做处理。
//    语音问题。
//    正在播放，后台状态。不播放了。切换回来，接着播放。

    private void setScreenLock() {
        screenStateListener = new ScreenListener.ScreenStateListener() {

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

                    if (!isCardRecordFinished && !isMergeText && isClickCard) {
                        playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
                    } else if (isCardRecordFinished = true && !isMergeText && isClickCard) {
//                        1切到后台，自己进入到下一个页面
                        mergeText();
                    }
                }
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreenOff", "onScreenOff");
                isQuitActivity = true;

                if (null != player && player.isPlaying()) {
                    player.stop();
                }

                if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if (null != mediaPlayer && mediaPlayer.isPlaying() && nowPlayingCardRecord) {
                    mediaPlayer.stop();
                    isCardRecordFinished = true;
                }

            }
        };

        screenListener = new ScreenListener(DongciTrainOneActivity.this);
        screenListener.begin(screenStateListener);
    }

//    进入测试后，语音说完，进度圈开始计时，点击正确的卡片
//    点击正确的卡片后，进度圈计时没有暂停，
//    点击正确的卡片后，进度圈计时暂停，正确的卡片入筐后，进度圈清空从新计时

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
        PreferencesHelper helper = new PreferencesHelper(DongciTrainOneActivity.this);
        String token = helper.getToken();
        Log.e("数据", "tokegetVerbn" + token);
        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "1");
        stringStringHashMap.put("module", "2");
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
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
                .build()
                .execute(new BaseStringCallback_Host(DongciTrainOneActivity.this, this));
    }


    /**
     * 名词
     */
    private void getVerb() {
        PreferencesHelper helper = new PreferencesHelper(DongciTrainOneActivity.this);
        String token = helper.getToken();

        String url = Setting.getVerb();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(DongciTrainOneActivity.this, this));
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
            (DongciTrainOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {

                            Gson gson = new Gson();
                            dongciBean = gson.fromJson(result,
                                    new TypeToken<DongciBean>() {
                                    }.getType());


                            if (length != -1 && !TextUtils.isEmpty(scene)) {
                                if (scene.equals("1")) {
                                    position = length;
                                    setData();
                                } else {
                                    screenListener.unregisterListener();
                                    screenStateListener = null;

                                    ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                                    foregroundCallbacks = null;

                                    stopTimerTask20s();

                                    Intent intent = new Intent(DongciTrainOneActivity.this, DongciTestOneActivity.class);
                                    intent.putExtra("position", length);
                                    intent.putExtra("model", dongciBean);
                                    intent.putExtra("groupId", groupId);
                                    startActivity(intent);
                                    ReleasePlayer();
                                    finish();
                                }
                                return;
                            }

                            setData();
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//标记那个接口

            String result = (String) data;
            (DongciTrainOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

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

                            stopTimerTask20s();
                            ++position;

                            if (position < dongciBean.getVerbTraining().size()) {

                                groupId = (String) jsonObject.getString("groupId");

                                Intent intent = new Intent(DongciTrainOneActivity.this, DongciTrainOneActivity.class);
                                intent.putExtra("groupId", groupId);

                                intent.putExtra("position", position);
                                intent.putExtra("model", dongciBean);
                                startActivity(intent);
                                ReleasePlayer();
                                finish();
                            } else {
                                Intent intent = new Intent(DongciTrainOneActivity.this, LetsTestActivity.class);
                                intent.putExtra("type", "dongci");
                                intent.putExtra("model", dongciBean);

                                startActivity(intent);
                                ReleasePlayer();
                                finish();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                double obj = (double) msg.obj;

                cb.setProgress((float) obj * currentLoopTime);
                currentLoopTime++;
            } else if (msg.what == 2) {

                View childAt = llClickLayout.getChildAt((int) msg.obj);

                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);

                View childAt1 = llClickLayout.getChildAt(0);
                if (childAt1.isClickable()) {
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

        currentClickOneStartTime = System.currentTimeMillis();

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

    TimerTask task10S = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!middleVoice_Finished) {
                        if ("吃什么".contains(verbChar)) {
                            playLocalVoice("男-吃什么.MP3", false);
                        } else if ("玩什么".contains(verbChar)) {
                            playLocalVoice("女-玩什么.MP3", false);
                        } else if ("喝什么".contains(verbChar)) {
                            playLocalVoice("女-喝什么.MP3", false);
                        } else if ("穿什么".contains(verbChar)) {
                            playLocalVoice("女-穿什么.MP3", false);
                        } else if ("洗什么".contains(verbChar)) {
                            playLocalVoice("女-洗什么.MP3", false);
                        } else {
                            playLocalVoice("男-吃什么.MP3", false);
                        }
                    }

                }
            });
        }
    };


    TimerTask task20sPlayXxWhat = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!isClickCard) {
                        if (!isQuitActivity) {
                            if ("吃什么".contains(verbChar)) {
                                playLocalVoice("男-吃什么.MP3", false);
                            } else if ("玩什么".contains(verbChar)) {
                                playLocalVoice("女-玩什么.MP3", false);
                            } else if ("喝什么".contains(verbChar)) {
                                playLocalVoice("女-喝什么.MP3", false);
                            } else if ("穿什么".contains(verbChar)) {
                                playLocalVoice("女-穿什么.MP3", false);
                            } else if ("洗什么".contains(verbChar)) {
                                playLocalVoice("女-洗什么.MP3", false);
                            } else {
                                playLocalVoice("男-吃什么.MP3", false);
                            }
                        }
                    } else {
                        stopTimerTask20s();
                    }

                }
            });
        }
    };

    private void stopTimerTask20s() {

        if (task10S != null) {
            task10S.cancel();
            task10S = null;
        }

        if (task20sPlayXxWhat != null) {
            task20sPlayXxWhat.cancel();
            task20sPlayXxWhat = null;
        }

        if (timerDelay10s != null) {
            timerDelay10s.cancel();
            timerDelay10s.purge();
            timerDelay10s = null;
        }

    }

    public void setData() {
        fLBigPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View childAt = llClickLayout.getChildAt(0);
                if (childAt.isClickable()) {
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                    pass = "0";
                    rl_hand.setVisibility(View.VISIBLE);
                    wave_cirlce_view.startWave();
                }
            }
        });
//        9.分解测试：点击错误的卡片，小手负责出现在错误的卡片上（点击错误的卡片，小手辅助应该出现在正确的卡片上）
        ivImg.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         View childAt = llClickLayout.getChildAt(0);
                                         if (childAt.isClickable()) {
                                             final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                                             final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                                             pass = "0";
                                             rl_hand.setVisibility(View.VISIBLE);
                                             wave_cirlce_view.startWave();
                                         }
                                     }
                                 }
        );
        helpTime = dongciBean.getHelptime().getHelpTime();

        loopTimeOne = helpTime * 1000 / executeInterval;//循环次数
        loopRateOne = 100.00 / loopTimeOne;//每次循环，圆环走的度数

        List<DongciBean.VerbTrainingBean> verbTraining = dongciBean.getVerbTraining();
        verbTrainingBean = verbTraining.get(position);//每条数据

        coursewareId = verbTrainingBean.getId();
        name = verbTrainingBean.getGroupChar();

        String startSlideshow = verbTrainingBean.getStartSlideshow();
        split = startSlideshow.split(",");
        frameAnim = new AnimationDrawable();

        for (int i = 0; i < split.length; i++) {
            asyncGet(split[i], i);
        }

        timerDelay10s = new Timer();

        verbChar = verbTrainingBean.getVerbChar();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {//播放动词
                if (!preparePlayerVerRecord) {
                    preparePlayerVerRecord = true;
                    playVerbRecord(verbTrainingBean.getVerbRecord(), true, false);
                }
//                playVerbRecord(verbTrainingBean.getVerbRecord(), true, false);
                try {
                    timerDelay10s.schedule(task10S, 1000 * 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                10、动词训练，“吃什么”的语音，10s内如果没有点击，就会播放，如果一直没有点击，那就每隔20s播放一次。
                try {
                    timerDelay10s.schedule(task20sPlayXxWhat, 1000 * 20, 1000 * 20);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1000 * 11);

        llIndicator.setSelectedPosition(position);

        int grapWidth = MyUtils.dip2px(DongciTrainOneActivity.this, 26);
        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(DongciTrainOneActivity.this).inflate(R.layout.text_bg_big, null);
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
        View childAt1 = llTextBgParent.getChildAt(1);
        childAt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View childAt = llClickLayout.getChildAt(0);
                if (childAt.isClickable()) {
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                    final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                    pass = "0";
                    rl_hand.setVisibility(View.VISIBLE);
                    wave_cirlce_view.startWave();
                }
            }
        });

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(DongciTrainOneActivity.this).inflate(R.layout.text_layout_big, null);
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

        tv_content1.setText(verbTrainingBean.getVerbChar());
        tv_content2.setText(verbTrainingBean.getCardChar());

//        for (int i = 0; i < 1; i++) {//点击的布局
//            View inflate = LayoutInflater.from(DongciTrainOneActivity.this).inflate(R.layout.click_layout_two_click_pic_big, null);
//            llClickLayout.addView(inflate);//第一个view不用设置间隔
//
//            ImageView iv_click_pic = (ImageView) inflate.findViewById(R.id.iv_click_pic);
//            GlideUtil.display(DongciTrainOneActivity.this, verbTrainingBean.getCardImage(), iv_click_pic);
//        }//for循环结束
        GlideUtil.display(DongciTrainOneActivity.this, verbTrainingBean.getCardImage(), ivClickPic);

        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        tv_content11.setText(verbTrainingBean.getCardChar());

        llTextParent.setVisibility(View.VISIBLE);//文字的父布局

        View text_bg = llTextBgParent.getChildAt(0);//自己的背景
        text_bg.setVisibility(View.INVISIBLE);

        View tv_content1 = llTextParent.getChildAt(0);//自己文本
        tv_content1.setVisibility(View.VISIBLE);

        tv_content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                12.	所有的训练级，点击了页面的大图或填空处，正确答案处出现小手辅助。否则2S辅助

                View childAt = llClickLayout.getChildAt(0);
                if (childAt.isClickable()) {
                    final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);
                    final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                    pass = "0";
                    rl_hand.setVisibility(View.VISIBLE);
                    wave_cirlce_view.startWave();
                }
            }

        });

//        女-喝什么
        View tv_content111 = llTextParent.getChildAt(1);//自己文本
        tv_content111.setVisibility(View.INVISIBLE);
//                            点击事件
//        12.	所有的训练级，点击了页面的大图或填空处，正确答案处出现小手辅助。否则2S辅助
        final View childAt = llClickLayout.getChildAt(0);//第一个
        childAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClickCard = true;

                //播放名词
                if (!isVerbRecordFinished) {//没完成
                    if (!preparePlayerVerRecord) {
                        preparePlayerVerRecord = true;
                        playVerbRecord(verbTrainingBean.getVerbRecord(), true, false);
                    }
                } else if (isVerbRecordFinished) {
                    playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
                }

                stopTimerTask20s();

                //把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(0);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {
                    pass = "0";
                }

                rl_hand.setVisibility(View.GONE);

                childAt.setClickable(false);

                AnimationHelper.startScaleAnimation(mContext, childAt);

                long l = -(currentClickOneStartTime - System.currentTimeMillis()) / 1000;
                if (l >= 50) {
                    l = 1;
                }
                stayTimeList = l + "";

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator sax = ObjectAnimator.ofFloat(childAt, "scaleX", 1f, 0.40f);
                        ObjectAnimator say = ObjectAnimator.ofFloat(childAt, "scaleY", 1f, 0.48f);

                        int height = (int) (childAt.getHeight() * 0.20);
                        int width = (int) (childAt.getWidth() * 0.30);
                        int left = childAt.getLeft();

                        int distance_x;
                        int distance_y;

                        distance_x = childAt.getTop();
                        int topLeft = rightChildTextTwo.getLeft();
                        distance_y = topLeft - left;

//                        int textBgWidth = MyUtils.dip2px(DongciTrainOneActivity.this, 0);
                        ObjectAnimator obx = ObjectAnimator.ofFloat(childAt, "translationX", distance_y - width);
                        ObjectAnimator oby = ObjectAnimator.ofFloat(childAt, "translationY", -distance_x - height);

                        set = new AnimatorSet();
                        set.playTogether(sax, say, obx, oby);
                        set.setDuration(2000);
                        set.start();


                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                childAt.setVisibility(View.INVISIBLE);//移动的view

                                View text_bg = llTextBgParent.getChildAt(1);//自己的背景
                                text_bg.setVisibility(View.INVISIBLE);

                                View tv_content1 = llTextParent.getChildAt(1);//自己文本
                                tv_content1.setVisibility(View.VISIBLE);
                                //透明度渐变显示
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_content1, "alpha", 0.5f, 1f);
                                animator.setDuration(1000);
                                animator.start();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        flyAnimationIsFinished = true;

                                        if (!isQuitActivity && !isMergeText && isCardRecordFinished && flyAnimationIsFinished) {
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

    private Bitmap bitmap2;
    private Bitmap bitmap1;
    private Bitmap bitmap;
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
                    frameAnim.addFrame(drawable, 500);

                    imagesList.add(drawable);
                    if (imagesList.size() == split.length) {
                        frameAnim.setOneShot(true);//设置帧动画是否只运行一次
                        ivImg.setBackgroundDrawable(frameAnim);

                    }
                    break;
                case 2:
                    byte[] bytes1 = (byte[]) msg.obj;
                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    Drawable drawable1 = new BitmapDrawable(bitmap1);

                    frameAnim.addFrame(drawable1, 500);

                    imagesList.add(drawable1);
                    if (imagesList.size() == split.length) {
                        frameAnim.setOneShot(true);//设置帧动画是否只运行一次
                        ivImg.setBackgroundDrawable(frameAnim);


                    }
                    break;
                case 3://
                    byte[] bytes2 = (byte[]) msg.obj;
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    Drawable drawable2 = new BitmapDrawable(bitmap2);
                    frameAnim.addFrame(drawable2, 500);

                    imagesList.add(drawable2);

                    if (imagesList.size() == split.length) {
                        frameAnim.setOneShot(true);//设置帧动画是否只运行一次
                        ivImg.setBackgroundDrawable(frameAnim);


                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {
        (DongciTrainOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(DongciTrainOneActivity.this, "出错了");
            }
        });

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

            }
        }, duration * 2);

        frameAnim.start();
    }

    private void startFirstAnimation(List<Drawable> images) {
        frameAnim1 = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        for (int i = 0; i < images.size(); i++) {
            frameAnim1.addFrame((images.get(i)), 500);
        }
        frameAnim1.setOneShot(false);
        ivImg.setBackgroundDrawable(frameAnim1);

        int duration = 0;
        for (int i = 0; i < frameAnim1.getNumberOfFrames(); i++) {
            duration += frameAnim1.getDuration(i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameAnim1.stop();
            }
        }, duration);

        frameAnim1.start();
    }

    private void playLocalVoiceBg(String videoName) {
        if (isQuitActivity) {
            return;
        }
//        if (player != null) {
//            if (player.isPlaying()) {
//                player.stop();
//            }
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
                    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
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

    private void playLocalVoice(String videoName, boolean isFirst) {
        if (isQuitActivity) {
            return;
        }
//        if (player != null) {
//            if (player.isPlaying()) {
//                player.stop();
//            }
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
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                    startTime(0, loopTimeOne, loopRateOne);
                    middleVoice_Finished = true;

                    if (isClickCard && !isCardRecordFinished && !isIntoPlayCardRecordMethod) {//播放了吃什么之后，判断一下，名词是否应该播放一下
                        playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
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

    /**
     * 播放动词
     *
     * @param videoName
     * @param isFirst
     */
    private void playVerbRecord(String videoName, boolean isFirst, Boolean CardRecord) {
        Log.e("声音", "playVerbRecord " + videoName);
        isVerbRecordFinished = false;
        isPlayingVerbRecord = false;

        if (isQuitActivity) {
            return;
        }

        if (isFirst) {
            doAnim();
        }

        if (TextUtils.isEmpty(videoName)) {
            isVerbRecordFinished = true;
            isPlayingVerbRecord = false;
            if (isFirst) {
                if (isClickCard && !isCardRecordFinished) {
                    playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
                }

            }
            return;
        }
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//            }
//            //关键语句
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
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
                    isVerbRecordFinished = false;
                    isPlayingVerbRecord = true;
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
                    isVerbRecordFinished = true;
                    isPlayingVerbRecord = false;
                    if (isFirst) {
                        if (isClickCard && !isCardRecordFinished) {
                            playLocalVoiceOnLine(verbTrainingBean.getCardRecord(), false, true);
                        }

                    }
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer1, int i, int i1) {
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

    /**
     * 在线
     *
     * @param videoName
     * @param isFirst
     */
    private void playLocalVoiceOnLine(String videoName, boolean isFirst, Boolean CardRecord) {
        Log.e("声音", "playLocalVoiceOnLine " + videoName);
        if (isQuitActivity) {
            if (CardRecord) {
                isCardRecordFinished = false;

            }
            return;
        }

        if (isFirst) {
            doAnim();
        }

        if (TextUtils.isEmpty(videoName)) {
            if (CardRecord) {
                isCardRecordFinished = true;
                nowPlayingCardRecord = false;

                if (!isQuitActivity) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isCardRecordFinished && !isMergeText && flyAnimationIsFinished) {
                                mergeText();
                            }
                        }
                    }, 1000);
                }
            }
            return;
        }

        isIntoPlayCardRecordMethod = true;

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
                        if (CardRecord) {

                            isCardRecordFinished = false;
                            nowPlayingCardRecord = true;
                        }
                        return;
                    }
                    // 4 开始播放
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {
//                    ReleasePlayer(mediaPlayer);

                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    if (CardRecord) {
                        isCardRecordFinished = true;
                        nowPlayingCardRecord = false;

                        if (!isQuitActivity) {

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isCardRecordFinished && !isMergeText && flyAnimationIsFinished) {
                                        mergeText();
                                    }
                                }
                            }, 1000);
                        }
                    }

                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer1, int i, int i1) {
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:

                if (null != screenStateListener) {
                    screenListener.unregisterListener();
                    screenStateListener = null;

                    ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                    foregroundCallbacks = null;
                }

                isQuitActivity = true;
                isFinishedActivity = true;

                stopTimerTask20s();
                ReleasePlayer();
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {

        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }
        stopTimerTask20s();

        isQuitActivity = true;
        isFinishedActivity = true;
        ReleasePlayer();
        super.onBackPressed();
    }

    private void mergeText() {

        isMergeText = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (null != player) {
//                    player.stop();
//                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playLocalVoiceOnLine(verbTrainingBean.getGroupRecord(), false, false);
                    }
                }, 1000);
                String tv_content1Str = tv_content1.getText().toString();
                String tv_content2Str = tv_content2.getText().toString();

                int width = tv_content1.getWidth();//px

                TextPaint paint = tv_content1.getPaint();
                paint.setTextSize(tv_content1.getTextSize());
                float textWidth = paint.measureText(tv_content1.getText().toString());//这个方法能把文本所占宽度衡量出来.   //单位是px
                int i1 = width - (int) textWidth;

                paint = tv_content2.getPaint();
                paint.setTextSize(tv_content2.getTextSize());
                float textWidth2 = paint.measureText(tv_content2.getText().toString());//这个方法能把文本所占宽度衡量出来.
                int i2 = width - (int) textWidth2;

                int i3 = (rightChildTextTwo.getLeft() - leftChildTextOne.getLeft() - leftChildTextOne.getWidth());
                int distance_x = (i3 + i1 / 2 + i2 / 2) / 2 - 38;//15是调节用的  //像素

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftChildTextOne.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                leftChildTextOne.setLayoutParams(layoutParams);
                leftChildTextOne.setBackground(null);//feizi

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
                llTextParent.setBackgroundColor(DongciTrainOneActivity.this.getResources().getColor(R.color.white));

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
//                        28.	每做完一组测试或训练都会出现过渡页
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

                        startFirstAnimation(imagesList);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                                addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                        , stayTime, groupId);

                            }
                        }, 3000);
                    }
                });

            }
        }, 100);
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

    /**
     * 回收每一帧的图片，释放内存资源
     *  * 取出AnimationDrawable中的每一帧逐个回收，并且设置Callback为null
     */
    private static void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }


    @Override
    protected void onDestroy() {
        Log.e("aaa", "onDestroy: ");

        if (null != screenStateListener) {
            screenListener.unregisterListener();
            screenStateListener = null;

            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;
        }

        EventBusUtil.unregister(this);

        stopTimerTask20s();

        handler.removeCallbacksAndMessages(null);
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
//        tryRecycleAnimationDrawable(frameAnim);
//        tryRecycleAnimationDrawable(frameAnim1);

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

}

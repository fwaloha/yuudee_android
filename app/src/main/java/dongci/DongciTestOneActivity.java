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

import bean.DongciBean;
import bean.GetFortifierBean;
import bean.LookLatelyBean;
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
 * describe:  动词训练  2个
 * DongciTestActivity
 */
public class DongciTestOneActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {
    //    进入测试后，语音说完，进度圈开始计时，点击正确的卡片
//    点击正确的卡片后，进度圈计时没有暂停，
//    点击正确的卡片后，进度圈计时暂停，正确的卡片入筐后，进度圈清空从新计时
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

    @BindView(R.id.iv_jinbi)
    ImageView ivJinbi;

    @BindView(R.id.rl_tiankongkuang)
    RelativeLayout rlTiankongkuang;
    @BindView(R.id.iv_jinbi_bottom)
    ImageView ivJinbiBottom;
    private int mTotalProgress = 100;
    private int mCurrentProgress = 0;
    private boolean isCorrect;
    private MediaPlayer player;

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
                        return;
                    }
                }
                Message message = handler.obtainMessage(1);
                handler.sendMessageDelayed(message, 1000);
            }
        }
    };
    private AnimationDrawable frameAnim;
    private int position = 0;
    private List<Drawable> imagesList = new ArrayList<>();
    private DongciBean dongciBean;
    private int currentSize = 2;
    private View leftChildTextOne;
    private View rightChildTextTwo;
    private DongciBean.VerbTestBean verbTestBean;//一条数据
    private boolean isOrder = false;
    private boolean isTwoMove = false;
    private boolean isOneMove = false;
    private TextView tv_content1;
    private TextView tv_content2;
    //    private List<DongciBean.VerbTestBean> verbTest;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                double obj = (double) msg.obj;

                cb.setProgress((float) obj * currentLoopTime);
                currentLoopTime++;
            } else if (msg.what == 2) {

                int obj = (int) msg.obj;
                View childAt = llClickLayout.getChildAt(obj);

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

    //    融合
    private Timer timer;
    private int currentLoopTime = 0;
    private int executeInterval = 100;
    private int loopTimeOne;
    private double loopRateOne;
    private int currentFirstPotision;
    private int currentTwoPotision;
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
    private PreferencesHelper helper;
    private MyApplication applicationContext;
    private int gold = 0;
    private int loopTimeTwo;
    private double loopRateTwo;
    private MediaPlayer mediaPlayer;
    private boolean readyPlayNext = false;
    private boolean playNextCardVoice = false;
    private boolean readyPlayNextOrderOne = false;
    private boolean readyPlayNextOrderTwo = false;
    private boolean isQuitActivity = false;
    private boolean isFienshedActivity = false;
    private boolean isPlayingVoice = false;

    private ScreenListener.ScreenStateListener screenStateListener;
    private ScreenListener screenListener;
    private boolean isFirstInto = true;
    private boolean isTwoInto = true;

    private boolean isEndAnimFinished = false;
    private boolean isMergeVoiceFinished = false;
    private boolean isShouldMerget = false;
    private boolean FristVoiceFinished = false;
    private boolean isShouldMergeText = false;
    private boolean isPerformOverMergeText = false;
    private boolean isExecuteThe = false;
    private boolean isYetUploadData = false;
    private boolean isBackgroundFinished = false;
    private boolean isAnimationFinished = false;
//    private ForegroundCallbacks.Listener foregroundCallbacks;

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
        PreferencesHelper helper = new PreferencesHelper(DongciTestOneActivity.this);
        String token = helper.getToken();

        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "2");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "2");//1名词 2动词 3句子组成 4句子分解
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
//        stringStringHashMap.put("disTurbName", disTurbName);
//        stringStringHashMap.put("errorType", errorType);
        stringStringHashMap.put("stayTime", stayTime);
        stringStringHashMap.put("groupId", groupId);

        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());
        if (isFienshedActivity) {
            return;
        }
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(DongciTestOneActivity.this, this));

        isYetUploadData = true;
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
            (DongciTestOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(24)) {//得到刚才做题的进度

            String result = (String) data;
            (DongciTestOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            if (null != foregroundCallbacks) {
                                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                                foregroundCallbacks = null;

                                screenListener.unregisterListener();
                                screenStateListener = null;
                            }

                            if (isFienshedActivity) {
                                return;
                            }
                            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                            foregroundCallbacks = null;

                            Gson gson = new Gson();
                            LookLatelyBean model = gson.fromJson(result,
                                    new TypeToken<LookLatelyBean>() {
                                    }.getType());

                            position = position + 1;//0---9

                            if (position < dongciBean.getVerbTest().size()) {
                                if (pass.equals("1")) {//是否通过 1 是 0 否

//                                    1名词 2动词 3句子组成 4句子分解
//                                    state	是	string	1减少 0添加
//                                     1名词 2动词 3句子组成 4句子分解
                                    addFortifier("2", "0");
                                    ++gold;
                                }

                                Intent intent = new Intent(DongciTestOneActivity.this, DongciTestOneActivity.class);
                                intent.putExtra("groupId", groupId);

                                intent.putExtra("position", position);
                                intent.putExtra("model", dongciBean);
                                startActivity(intent);
                                ReleasePlayer();
                                finish();

                            } else {//从新开始  新的一轮了

                                if (pass.equals("1")) {//是否通过 1 是 0 否
//                                    1名词 2动词 3句子组成 4句子分解
                                    addFortifier("2", "0");
                                    ++gold;
                                }

                                LookLatelyBean.AgainModuleBean againModule = model.getAgainModule();
                                if (againModule.getModule2().equals("1")) {//通关了。
                                    Intent intent1 = new Intent(DongciTestOneActivity.this, PinTuAllFlyActivity.class);
                                    intent1.putExtra("anInt1", gold);
                                    intent1.putExtra("dongci", true);
                                    startActivity(intent1);
                                    ReleasePlayer();
                                    finish();
                                } else {

                                    if (gold >= 10) {//达到了10个金币
                                        Intent intent = new Intent(DongciTestOneActivity.this, PinTuActivity.class);
                                        intent.putExtra("anInt1", gold);
                                        intent.putExtra("dongci", true);

                                        startActivity(intent);
                                        ReleasePlayer();
                                        finish();

//                                    2） 通关后的，我再次做了一组训练/测试，进入强化物是所有模块都飞出去了并且还会进入通关成功页面。（与名词逻辑流程一致）
                                    } else {

                                        Intent intent = new Intent(DongciTestOneActivity.this, LetsTestToTrainActivity.class);
                                        intent.putExtra("type", "dongci_test_train");
                                        startActivity(intent);
                                        ReleasePlayer();
                                        finish();
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
        if (object.equals(3)) {//强化物添加金币

            String result = (String) data;
            (DongciTestOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
        if (object.equals(4))

        {//强化物添加金币

            String result = (String) data;
            (DongciTestOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
                                if (module.equals("2")) {

                                    if (gold != 0) {
                                        DongciTestOneActivity.this.gold = gold;
                                        tvMoney.setText("x " + gold);
                                        ivJinbi.setVisibility(View.VISIBLE);
                                    } else {
                                        ivJinbi.setVisibility(View.GONE);
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

    /**
     *
     */
    private void getSystemStatistics() {
        PreferencesHelper helper = new PreferencesHelper(DongciTestOneActivity.this);
        String token = helper.getToken();

        String url = Setting.getSystemStatistics();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(24)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(DongciTestOneActivity.this, this));
    }

    /**
     *
     */
    private void getFortifier() {
        PreferencesHelper helper = new PreferencesHelper(DongciTestOneActivity.this);
        String token = helper.getToken();

        String url = Setting.getFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(DongciTestOneActivity.this, this));
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     *               1名词 2动词 3句子组成 4句子分解
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(DongciTestOneActivity.this);
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
                .build()
                .execute(new BaseStringCallback_Host(DongciTestOneActivity.this, this));
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

    }

    List<String> voiceListData = new ArrayList<>();
    private boolean isShouldAddTrainingResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongci_ceshi);
        ButterKnife.bind(this);
        ivHome.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

        playLocalVoice("男-他在干什么.MP3");
        position = getIntent().getIntExtra("position", 0);
        dongciBean = (DongciBean) getIntent().getSerializableExtra("model");
        groupId = getIntent().getStringExtra("groupId");

        if (null == dongciBean) {
            return;
        }

        applicationContext = MyApplication.getApplication();

        int fristAssistTime = dongciBean.getVerbTest().get(position).getCardOneTime();
        int secondAssistTime = dongciBean.getVerbTest().get(position).getCardTwoTime();

        loopTimeOne = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateOne = 100.00 / loopTimeOne;//每次循环，圆环走的度数

        loopTimeTwo = secondAssistTime * 1000 / executeInterval;//循环次数
        loopRateTwo = 100.00 / loopTimeTwo;//每次循环，圆环走的度数

        verbTestBean = dongciBean.getVerbTest().get(position);

        coursewareId = verbTestBean.getId();
        name = verbTestBean.getGroupChar();

        llIndicator.setSelectedPosition(position);

        String startSlideshow = verbTestBean.getStartSlideshow();
        String[] split = startSlideshow.split(",");
        frameAnim = new AnimationDrawable();

        for (int i = 0; i < split.length; i++) {
            try {
                asyncGet(split[i], i);
            } catch (Exception e) {

            }
        }

        int grapWidth = MyUtils.dip2px(DongciTestOneActivity.this, 26);
        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_bg_big, null);
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
            View inflate = LayoutInflater.from(this).inflate(R.layout.text_layout_big, null);
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

        tv_content1.setText(verbTestBean.getVerbChar());
        tv_content2.setText(verbTestBean.getCardChar());

        for (int i = 0; i < currentSize; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic_mingci_ceshi_xiugai, null);
//            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic, null);
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

            String cardChar = verbTestBean.getList().get(i).getCardChar();

//            吃喝玩穿洗，这5个是固定的
            if (cardChar.contains("吃")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.eat3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar.contains("穿")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.chuan3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar.contains("洗")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.xi3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar.contains("玩")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.wan3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else if (cardChar.contains("喝")) {
                AnimationDrawable frameAnim1 = new AnimationDrawable();
                // 为AnimationDrawable添加动画帧
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he1), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he2), 500);
                frameAnim1.addFrame(getResources().getDrawable(R.drawable.he3), 500);
                frameAnim1.setOneShot(false);
                iv_click_pic.setBackground(frameAnim1);
                frameAnim1.start();
            } else {
                GlideUtil.display(DongciTestOneActivity.this, verbTestBean.getList().get(i).getCardImage(), iv_click_pic);
            }
        }//点击事件结束

        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        TextView tv_content22 = (TextView) llClickLayout.getChildAt(1).findViewById(R.id.tv_choose2);

        tv_content11.setText(verbTestBean.getList().get(0).getCardChar());
        tv_content22.setText(verbTestBean.getList().get(1).getCardChar());

        String currentClickCardColorChar = verbTestBean.getList().get(0).getCardChar();
        String firstCardColorChar = verbTestBean.getVerbChar();
        if (currentClickCardColorChar.equals(firstCardColorChar)) {
            isOrder = true;
        }

        if (firstCardColorChar.equals(currentClickCardColorChar)) {
            llClickLayout.getChildAt(0).setTag("0");
            llClickLayout.getChildAt(1).setTag("1");
            currentFirstPotision = 0;
            currentTwoPotision = 1;

        } else {
            llClickLayout.getChildAt(1).setTag("0");
            llClickLayout.getChildAt(0).setTag("1");
            currentFirstPotision = 1;
            currentTwoPotision = 0;

        }
//        进入测试后，语音说完，进度圈开始计时，点击正确的卡片
//        点击事件
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

                            final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                            wave_cirlce_view.startWave();
                            pass = "0";
                        }

                        return;
                    }

                    long l = (System.currentTimeMillis() - currentClickTwoStartTime) / 1000;
                    if (l >= 50) {
                        l = 1;
                    }
                    stayTimeList = stayTimeList + "," + l + "";
                }
                String tagPosition = (String) childAt.getTag();
                if (tagPosition.equals("0")) {
                    stopTime();
                    currentClickTwoStartTime = System.currentTimeMillis();
                } else {
                    stopTime();
                }

                voiceListData.add(verbTestBean.getList().get(0).getCardRecord());
                Log.e("voiceList", "onClick: " + verbTestBean.getList().get(0).getCardRecord());
                isOneMove = true;

//                把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(0);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {//是否通过 1 是 0 否
                    pass = "0";
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
                                            zhanxian_xiaolian_donghua();
//                                            if (!noPlayTwoCardVoice) {
//                                                mergeText();
//                                            }
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
                }, 1000);
            }
        });
//1不自动进入。2不自动跳出来。
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

                            final WaveCircleView wave_cirlce_view = childAt.findViewById(R.id.wave_cirlce_view);
                            wave_cirlce_view.startWave();
                            pass = "0";
                        }
                        return;
                    }
                    long l = (System.currentTimeMillis() - currentClickTwoStartTime) / 1000;

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
                } else {
                    stopTime();
                }
                voiceListData.add(verbTestBean.getList().get(1).getCardRecord());
                Log.e("voiceList", "onClick: " + verbTestBean.getList().get(1).getCardRecord());

                isTwoMove = true;

                //  把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(1);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {//是否通过 1 是 0 否
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
                                            zhanxian_xiaolian_donghua();
//                                            if (!noPlayTwoCardVoice) {
//                                                mergeText();
//                                            }

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
                }, 1000);
            }
        });
        llMoney.setVisibility(View.VISIBLE);

        getFortifier();
        setScreenLock();

        ForegroundCallbacks.get().addListener(foregroundCallbacks);
        EventBusUtil.register(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        ReleasePlayer();
        finish();
    }

    private Handler voiceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {

                if (FristVoiceFinished) {
                    if (voiceListData.size() != 0) {
                        if (!isPlayingVoice && !isQuitActivity) {
                            playLocalVoiceOnLineNew(voiceListData.get(0));
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
    private void playLocalVoiceOnLineNew(String videoName) {
        Log.e("录音", "playLocalVoiceOnLineNew: " + videoName);
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
            isPlayingVoice = false;
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
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer1, int i, int i1) {
//                    ReleasePlayer(mediaPlayer);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    } Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (IOException e) {
//            ReleasePlayer(mediaPlayer);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                //关键语句
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }

    }

    ForegroundCallbacks.Listener foregroundCallbacks = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
//                  preferencesHelper.saveIsBackground(false);
            isQuitActivity = false;
            if (isFirstInto) {
                isFirstInto = false;
            } else {
                if (isExecuteThe && !isYetUploadData) {
                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                            , stayTime, groupId);
                }
            }
        }

        @Override
        public void onBecameBackground() {
//                   preferencesHelper.saveIsBackground(true);
            isQuitActivity = true;
            if (null != playerDoWhatThing && playerDoWhatThing.isPlaying()) {

                playerDoWhatThing.stop();
                isPlayingVoice = false;
                FristVoiceFinished = true;
            }
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
                Log.e("onScreenOn", "onScreenOn");
                isQuitActivity = false;
                if (isTwoInto) {
                    isTwoInto = false;
                } else {
                    if (isExecuteThe && !isYetUploadData) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
                }
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreenOff", "onScreenOff");
                isQuitActivity = true;

                if (null != playerDoWhatThing && playerDoWhatThing.isPlaying()) {

                    playerDoWhatThing.stop();
                    isPlayingVoice = false;
                    FristVoiceFinished = true;
                }
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

    private void zhanxian_xiaolian_donghua() {
        if (pass.equals("1")) {//是否通过 1 是 0 否
            ivJinbiBottom.setVisibility(View.VISIBLE);

            int moneyTop = llMoney.getTop();
            int tiankongkuangTop = rlTiankongkuang.getTop();
            int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(DongciTestOneActivity.this, 32);

            int screenWidth = MyUtils.getScreenWidth(DongciTestOneActivity.this) / 3;

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
                        ivJinbi.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
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
                Message message = handler1.obtainMessage();
                if (response.isSuccessful()) {
                    if (i == 0) {
                        message.what = 1;
                        message.obj = response.body().bytes();
                        handler1.sendMessage(message);
                    }
                    if (i == 1) {
                        message.what = 2;
                        message.obj = response.body().bytes();
                        handler1.sendMessage(message);
                    }
                    if (i == 2) {
                        message.what = 3;
                        message.obj = response.body().bytes();
                        handler1.sendMessage(message);
                    }
                } else {
                    handler1.sendEmptyMessage(4);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.e("aaa", "onDestroy: ");
        if (null != foregroundCallbacks) {
            ForegroundCallbacks.get().removeListener(foregroundCallbacks);
            foregroundCallbacks = null;

            screenListener.unregisterListener();
            screenStateListener = null;
        }

        handler.removeCallbacksAndMessages(null);
        ReleasePlayer();

        if (null != bitmap1) {
            bitmap1.recycle();
            bitmap1 = null;
        }
        if (null != bitmap2) {
            bitmap2.recycle();
            bitmap2 = null;
        }
        if (null != bitmap3) {
            bitmap3.recycle();
            bitmap3 = null;
        }

        super.onDestroy();
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
//
    }

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Handler handler1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes1 = (byte[]) msg.obj;
                    bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    Drawable drawable1 = new BitmapDrawable(bitmap1);


                    imagesList.add(drawable1);
                    if (imagesList.size() == 3) {
                        doAnim(imagesList);
                    }
                    break;
                case 2:
                    byte[] bytes2 = (byte[]) msg.obj;
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    Drawable drawable2 = new BitmapDrawable(bitmap2);

                    imagesList.add(drawable2);
                    if (imagesList.size() == 3) {
                        doAnim(imagesList);
                    }
                    break;
                case 3://
                    byte[] bytes3 = (byte[]) msg.obj;
                    bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                    Drawable drawable3 = new BitmapDrawable(bitmap3);

                    imagesList.add(drawable3);

                    if (imagesList.size() == 3) {
                        doAnim(imagesList);
                    }

                    break;
                default:
                    break;
            }
        }
    };

    private void doAnim(List<Drawable> images) {
        frameAnim = new AnimationDrawable();
        // 为AnimationDrawable添加动画帧
        for (int i = 0; i < images.size(); i++) {
            frameAnim.addFrame((images.get(i)), 500);
        }
        frameAnim.setOneShot(false);
        ivImg.setBackgroundDrawable(frameAnim);

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
                frameAnim.stop();

                View childAt = llClickLayout.getChildAt(0);
                View childAt1 = llClickLayout.getChildAt(1);

                if (childAt.isClickable() && childAt1.isClickable()) {
                    currentClickOneStartTime = System.currentTimeMillis();
                    startTime(currentFirstPotision, loopTimeOne, loopRateOne);
                }

            }
        }, duration * 2);

        frameAnim.start();
    }

    private MediaPlayer playerDoWhatThing;

    //10.	测试级，意义级，所有进度圈计时，是在上方静态图片放大突出并缩回原形后开始计时的，计时时间取值后台设置
    private void playLocalVoice(String videoName) {

        voiceHandler.sendEmptyMessage(1);
        if (isQuitActivity) {
            FristVoiceFinished = true;
            return;
        }
//        if (playerDoWhatThing != null) {
//            if (playerDoWhatThing.isPlaying()) {
//                playerDoWhatThing.stop();
//            }
//            //关键语句
//            playerDoWhatThing.reset();
//            playerDoWhatThing.release();
//            playerDoWhatThing = null;
//        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            playerDoWhatThing = new MediaPlayer();
            playerDoWhatThing.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            playerDoWhatThing.setLooping(false);//循环播放
//            player.prepare();
//            player.start();

            //3 准备播放
            playerDoWhatThing.prepareAsync();
            //3.1 设置一个准备完成的监听
            playerDoWhatThing.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        FristVoiceFinished = true;
                        return;
                    }
                    // 4 开始播放
                    playerDoWhatThing.start();
                }
            });

            playerDoWhatThing.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    FristVoiceFinished = true;
//                    ReleasePlayer(playerDoWhatThing);

                    if (playerDoWhatThing != null) {
                        if (playerDoWhatThing.isPlaying()) {
                            playerDoWhatThing.stop();
                        }
                        //关键语句
                        playerDoWhatThing.reset();
                        playerDoWhatThing.release();
                        playerDoWhatThing = null;
                    }
                }
            });

            playerDoWhatThing.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                    ReleasePlayer(playerDoWhatThing);
                    if (playerDoWhatThing != null) {
                        if (playerDoWhatThing.isPlaying()) {
                            playerDoWhatThing.stop();
                        }
                        //关键语句
                        playerDoWhatThing.reset();
                        playerDoWhatThing.release();
                        playerDoWhatThing = null;
                    }Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
//            ReleasePlayer(playerDoWhatThing);
            if (playerDoWhatThing != null) {
                if (playerDoWhatThing.isPlaying()) {
                    playerDoWhatThing.stop();
                }
                //关键语句
                playerDoWhatThing.reset();
                playerDoWhatThing.release();
                playerDoWhatThing = null;
            } Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }
    }


    private void playLocalVoiceBg(String videoName) {

        if (isQuitActivity) {
            return;
        }
//        if (playerDoWhatThing != null) {
//            if (playerDoWhatThing.isPlaying()) {
//                playerDoWhatThing.stop();
//            }
//            //关键语句
//            playerDoWhatThing.reset();
//            playerDoWhatThing.release();
//            playerDoWhatThing = null;
//        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            playerDoWhatThing = new MediaPlayer();
            playerDoWhatThing.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            playerDoWhatThing.setLooping(false);//循环播放
//            player.prepare();
//            player.start();

            //3 准备播放
            playerDoWhatThing.prepareAsync();
            //3.1 设置一个准备完成的监听
            playerDoWhatThing.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    playerDoWhatThing.start();


                }
            });

            playerDoWhatThing.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    isBackgroundFinished = true;

                    if (isAnimationFinished && !isYetUploadData && !isQuitActivity) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
//                    ReleasePlayer(playerDoWhatThing);
                    if (playerDoWhatThing != null) {
                        if (playerDoWhatThing.isPlaying()) {
                            playerDoWhatThing.stop();
                        }
                        //关键语句
                        playerDoWhatThing.reset();
                        playerDoWhatThing.release();
                        playerDoWhatThing = null;
                    }}
            });
            playerDoWhatThing.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                    ReleasePlayer(playerDoWhatThing);
                    if (playerDoWhatThing != null) {
                        if (playerDoWhatThing.isPlaying()) {
                            playerDoWhatThing.stop();
                        }
                        //关键语句
                        playerDoWhatThing.reset();
                        playerDoWhatThing.release();
                        playerDoWhatThing = null;
                    }  Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
//            ReleasePlayer(playerDoWhatThing);
            if (playerDoWhatThing != null) {
                if (playerDoWhatThing.isPlaying()) {
                    playerDoWhatThing.stop();
                }
                //关键语句
                playerDoWhatThing.reset();
                playerDoWhatThing.release();
                playerDoWhatThing = null;
            } Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
//                tryRecycleAnimationDrawable(frameAnim);
                OkHttpUtils.getInstance().cancelTag(this);

                screenListener.unregisterListener();
                screenStateListener = null;

                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                foregroundCallbacks = null;

                isQuitActivity = true;
                isFienshedActivity = true;
                ReleasePlayer();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        OkHttpUtils.getInstance().cancelTag(this);

        screenListener.unregisterListener();
        screenStateListener = null;

        ForegroundCallbacks.get().removeListener(foregroundCallbacks);
        foregroundCallbacks = null;

        isQuitActivity = true;
        isFienshedActivity = true;
        ReleasePlayer();
        super.onBackPressed();
    }

    private void mergeText() {
//        11.	所有的测试级，点击了错误答案时，正确答案处出现小手辅助。否则，5S辅助
//        情况一：若进入页面，选择错误卡片，正确的卡片小手辅助，（比如：黑猫，本来应该点击黑，但是我第一次选择了猫，黑卡片要小手辅助，）
        isPerformOverMergeText = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                playLocalVoiceOnLineGroupRecord(verbTestBean.getGroupRecord() + "", false);
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
                llTextParent.setBackgroundColor(DongciTestOneActivity.this.getResources().getColor(R.color.white));

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
                            distance_x - i * 10);
                    obx.setDuration(1000);
                    obx.start();

                    obx1 = ObjectAnimator.ofFloat(rightChildTextTwo, "translationX",
                            -distance_x - i * 10);
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

//                        doAnimEnd(imagesList);//    startEat(imagesList);

                        frameAnim = new AnimationDrawable();
                        // 为AnimationDrawable添加动画帧
                        for (int i = 0; i < imagesList.size(); i++) {
                            frameAnim.addFrame((imagesList.get(i)), 500);
                        }
                        frameAnim.setOneShot(false);
                        ivImg.setBackgroundDrawable(frameAnim);

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
                                frameAnim.stop();

                                isAnimationFinished = true;

                                if (isBackgroundFinished && !isYetUploadData && !isQuitActivity) {
                                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                            , stayTime, groupId);
                                }
                            }
                        }, duration * 2);

                        frameAnim.start();

                        isExecuteThe = true;
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


    /**
     * 在线播放
     *
     * @param videoName
     * @param isFirst
     */
    private void playLocalVoiceOnLineGroupRecord(String videoName, boolean isFirst) {

        if (TextUtils.isEmpty(videoName)) {
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
                    }      }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer1, int i, int i1) {
//                    ReleasePlayer(mediaPlayer);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }    Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (IOException e) {
//            ReleasePlayer(mediaPlayer);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                //关键语句
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }    Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }

    }
}

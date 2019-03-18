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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import bean.DelayTimeBean;
import bean.GetFortifierBean;
import bean.LookLatelyBean;
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
 * describe:  名词测试  2个选项的
 */
public class MingciTestOneActivityOld extends BaseActivity implements View.OnClickListener, AsyncRequest {

    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
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
    @BindView(R.id.cb)
    CircleBarTime cb;
    @BindView(R.id.fL_big_pic)
    FrameLayout fLBigPic;
    @BindView(R.id.iv_jinbi_bottom)
    ImageView ivJinbiBottom;
    @BindView(R.id.rl_tiankongkuang)
    RelativeLayout rlTiankongkuang;

    private MediaPlayer player;

    List<String> voiceListData = new ArrayList<>();

    private Boolean isFirstVoiceFinished = false;//是不是第一个声音完成
    //    private boolean readyPlayNextOrderThree=false;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            isFirstVoiceFinished = true;

            if (voiceListData.size() == 3) {//播完了
                voiceListData.remove(0);

                if (readyPlayNextOrderOne & !readyPlayNextOrderTwo) {
                    playLocalVoiceOnLineOnOrder(voiceListData.get(0));
                }
                if (!readyPlayNextOrderOne & readyPlayNextOrderTwo) {
                    playLocalVoiceOnLineOnOrder(voiceListData.get(0));
                }

                if (readyPlayNextOrderOne & readyPlayNextOrderTwo) {
                    playLocalVoiceOnLineOnOrderClickAllCard(voiceListData.get(0), false);

                }
            }


            //缩放
            Animation animation = AnimationUtils.loadAnimation(MingciTestOneActivityOld.this, R.anim.anim_scale_pic);
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
                        startTime(currentFirst, loopTimeOne, loopRateOne);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    };
    private int position = 0;
    private MingciBean model;
    private MingciBean.NounTestBean nounTestBean;
    private int currentSize = 2;
    private View leftChildTextOne;
    private View rightChildTextTwo;
    private TextView tv_content1;
    private TextView tv_content2;
    private boolean isOrder = false;
    private boolean isOneMove = false;
    private boolean isTwoMove = false;
    private Timer timer;
    private int currentLoopTime;
    private int executeInterval = 100;
    private int loopTimeOne;
    private double loopRateOne;
    private int currentFirst = 0;
    private int currentSecond = 1;
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
    private int gold = 0;
    private int loopTimeTwo;
    private double loopRateTwo;
    private MediaPlayer mediaPlayer;
    private boolean readyPlayNextOrderOne = false;
    private boolean readyPlayNextOrderTwo = false;

    private boolean playNextCardVoice = false;
    private boolean isFinish = false;
    private boolean isQuitActivity = false;
    private ScreenListener screenListener;
    private boolean isFirstInto = true;
    private boolean noPlayTwoCardVoice = false;
    private boolean isTwoInto = false;
    private boolean isShouldAddTrainingResult = false;
//    screenListener

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
     * <p>
     * :{coursewareId=30, groupId=, pass=1, token=68QyHPlhEGqwEvUrtWMqXQ==,
     * stayTime=0, name=吃虫, startTime=2018年11月14日 09:30:03, module=2, stayTimeList=1542133310986, scene=1}
     */
    private void addTrainingResult(String coursewareId, String startTime
            , String name, String pass, String stayTimeList, String disTurbName, String errorType
            , String stayTime, String groupId) {
        PreferencesHelper helper = new PreferencesHelper(MingciTestOneActivityOld.this);
        String token = helper.getToken();

        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "2");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "1");//1名词 2动词 3句子组成 4句子分解
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
                .tag(MingciTestOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciTestOneActivityOld.this, this));
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
            (MingciTestOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(24)) {//

            String result = (String) data;
            (MingciTestOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                /**
                 *
                 */
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

                            String dataStr = (String) jsonObject.getString("data");

                            if (null != foregroundCallbacks) {
                                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                                foregroundCallbacks = null;

                                screenListener.unregisterListener();
                                screenListener = null;
                            }

                            if (!TextUtils.isEmpty(dataStr) && dataStr.equals("0")) {//代表是没通过一次
                                position = position + 1;

                                if (position < model.getNounTest().size()) {

                                    if (pass.equals("1")) {//是否通过 1 是 0 否
                                        addFortifier("1", "0");
                                        gold = gold + 1;
                                        helper.saveInt("sp", "mingciJinbi_1", gold);//金币
                                    }
                                    //  while (isbgmusicover) {
                                    Intent intent = new Intent(MingciTestOneActivityOld.this, MingciTestOneActivityOld.class);
                                    intent.putExtra("groupId", groupId);

                                    intent.putExtra("position", position);
                                    intent.putExtra("model", model);
                                    startActivity(intent);
                                    finish();
                                    //   }
                                } else {//从新开始

                                    if (pass.equals("1")) {//是否通过 1 是 0 否
                                        addFortifier("1", "0");
                                        gold = gold + 1;
                                        helper.saveInt("sp", "mingciJinbi_1", gold);//金币
                                    }

                                    if (gold >= 10) {//达到了10个金币
                                        Intent intent = new Intent(MingciTestOneActivityOld.this, PinTuActivity.class);
                                        intent.putExtra("anInt1", gold);
                                        intent.putExtra("mingci", true);

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MingciTestOneActivityOld.this, LetsTestToTrainActivity.class);
                                        intent.putExtra("type", "mingci");
                                        startActivity(intent);
                                        finish();
                                    }

//                                    if (gold >= 10) {//达到了10个金币
//                                        Intent intent = new Intent(MingciTestOneActivity.this, PinTuActivity.class);
//                                        intent.putExtra("anInt1", gold);
//                                        intent.putExtra("mingci", true);
//
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        Intent intent = new Intent(MingciTestOneActivity.this, LetsTestToTrainActivity.class);
//                                        intent.putExtra("type", "mingci");
//                                        startActivity(intent);
//                                        finish();
//                                    }

                                }
                            } else {//data不在是0了
                                Gson gson = new Gson();
                                LookLatelyBean model1 = gson.fromJson(result,
                                        new TypeToken<LookLatelyBean>() {
                                        }.getType());

                                position = position + 1;
                                if (position < model.getNounTest().size()) {
//
                                    if (pass.equals("1")) {//是否通过 1 是 0 否
                                        addFortifier("1", "0");
                                        gold = gold + 1;
                                        helper.saveInt("sp", "mingciJinbi_1", gold);//金币
                                    }
                                    //  while (isbgmusicover) {
                                    Intent intent = new Intent(MingciTestOneActivityOld.this, MingciTestOneActivityOld.class);
                                    intent.putExtra("groupId", groupId);

                                    intent.putExtra("position", position);
                                    intent.putExtra("model", model);
                                    startActivity(intent);
                                    finish();
                                    //   }

                                } else {//从新开始
                                    if (pass.equals("1")) {//是否通过 1 是 0 否
                                        addFortifier("1", "0");
                                        gold = gold + 1;
                                        helper.saveInt("sp", "mingciJinbi_1", gold);//金币
                                    }

//                                1在做名词 2在做动词  3句子成组 4句子分解 5 全部通关
                                    String module = model1.getData().getModule();
//                                    int passNumber = model1.getData().getPassNumber();//0开始

//                                    againModule里面对应四个模块的通关状态,playerModule里面对应四个模块是否是再次通关
//                                    againModule       当为0，接着做。当时1的时候，通关了。再次提交一组名词训练的时候，变成0.
//                                    playerModule      如果是0，一次也没有通关，如果是1，代表再次通关

                                    LookLatelyBean.AgainModuleBean againModule = model1.getAgainModule();
                                    if (againModule.getModule1().equals("1")) {//通关了。
                                        Intent intent1 = new Intent(MingciTestOneActivityOld.this, PinTuAllFlyActivity.class);
                                        intent1.putExtra("anInt1", gold);
                                        intent1.putExtra("mingci", true);
                                        startActivity(intent1);
                                        finish();

                                    } else {
                                        if (gold >= 10) {//达到了10个金币
                                            Intent intent = new Intent(MingciTestOneActivityOld.this, PinTuActivity.class);
                                            intent.putExtra("anInt1", gold);
                                            intent.putExtra("mingci", true);

                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(MingciTestOneActivityOld.this, LetsTestToTrainActivity.class);
                                            intent.putExtra("type", "mingci");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

//                                    先进入强化物页面，并且遮挡动画的模块全部飞走消失，然后进入通关页面
//                                    if (module.equals("2")) {//表示名词训练测试通关了
////                                    名词,动词,句子分解通关,进入通关成功的页面,3S返回首页  句子分组通关后进入通关成功的页面3S自动进入句子分解模块
//
//                                        Intent intent1 = new Intent(MingciTestOneActivity.this, PinTuAllFlyActivity.class);
//                                        intent1.putExtra("anInt1", gold);
//                                        intent1.putExtra("mingci", true);
//                                        startActivity(intent1);
//                                        finish();
//
//                                        return;
//                                    }
//
//                                    if (gold >= 10) {//达到了10个金币
//                                        Intent intent = new Intent(MingciTestOneActivity.this, PinTuActivity.class);
//                                        intent.putExtra("anInt1", gold);
//                                        intent.putExtra("mingci", true);
//
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        Intent intent = new Intent(MingciTestOneActivity.this, LetsTestToTrainActivity.class);
//                                        intent.putExtra("type", "mingci");
//                                        startActivity(intent);
//                                        finish();
//                                    }
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
            (MingciTestOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
            (MingciTestOneActivityOld.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
                                if (module.equals("1")) {
//    {"msg":"查询成功！","code":200,"data":[{"id":16,"gold":20,"module":"1","userId":5,"createTime":1542437839000,"updateTime":1542444820000}]}

                                    MingciTestOneActivityOld.this.gold = gold;
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


    private void getFortifier() {
        PreferencesHelper helper = new PreferencesHelper(MingciTestOneActivityOld.this);
        String token = helper.getToken();

        String url = Setting.getFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .tag(MingciTestOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciTestOneActivityOld.this, this));
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     *               1名词 2动词 3句子组成 4句子分解
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(MingciTestOneActivityOld.this);
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
                .tag(MingciTestOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciTestOneActivityOld.this, this));
    }

    //    /**
//     *
//     */
    private void getSystemStatistics() {
        PreferencesHelper helper = new PreferencesHelper(MingciTestOneActivityOld.this);
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
                .tag(MingciTestOneActivityOld.this)
                .build()
                .execute(new BaseStringCallback_Host(MingciTestOneActivityOld.this, this));
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingci_test);
        ButterKnife.bind(this);

        player = new MediaPlayer();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-这是什么样的东西.MP3");
            }
        }, 70);

        position = getIntent().getIntExtra("position", 0);
        model = (MingciBean) getIntent().getSerializableExtra("model");
        groupId = getIntent().getStringExtra("groupId");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

//        Gson gson = new Gson();
//        model = gson.fromJson(sdfadfas,
//                new TypeToken<MingciBean>() {
//                }.getType());

        List<MingciBean.NounTestBean> nounTest = model.getNounTest();
        nounTestBean = nounTest.get(position);

        coursewareId = nounTestBean.getId();
        name = nounTestBean.getGroupChar();

        if (null == nounTestBean) {
            return;
        }

        ivHome.setOnClickListener(this);
        initView();

        helper = new PreferencesHelper(MingciTestOneActivityOld.this);

        setScreenLock();

        ForegroundCallbacks.get().addListener(foregroundCallbacks);
        EventBusUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        finish();
    }

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
            isQuitActivity = true;
            if (null != player && player.isPlaying()) {
                voiceListData.remove(0);
                player.stop();
            }

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                voiceListData.remove(0);
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
                    voiceListData.remove(0);
                    player.stop();
                }

                if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                    voiceListData.remove(0);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    Drawable drawable = new BitmapDrawable(bitmap);
                    // 为AnimationDrawable添加动画帧
                    ivImg.setImageDrawable(drawable);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 异步get,直接调用
     */
    private void asyncGet(String IMAGE_URL, ImageView ivImg) {
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
                    message.what = 1;
                    message.obj = response.body().bytes();
                    handler.sendMessage(message);
                } else {
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
                if (tagPosition.equals("0")) {
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


    //    测试用自己的时间，只有训练采用helptime
    private void initView() {

        voiceListData.add("这是什么");
        llMoney.setVisibility(View.VISIBLE);
        getFortifier();

        llIndicator.setSelectedPosition(position);

        int fristAssistTime = nounTestBean.getFristAssistTime();
        int secondAssistTime = nounTestBean.getSecondAssistTime();

        loopTimeOne = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateOne = 100.00 / loopTimeOne;//每次循环，圆环走的度数

        loopTimeTwo = secondAssistTime * 1000 / executeInterval;//循环次数
        loopRateTwo = 100.00 / loopTimeTwo;//每次循环，圆环走的度数

        String startSlideshow = nounTestBean.getGroupImage();
        asyncGet(startSlideshow, ivImg);

        int grapWidth = MyUtils.dip2px(MingciTestOneActivityOld.this, 26);
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

        tv_content1.setText(nounTestBean.getCardColorChar());
        tv_content2.setText(nounTestBean.getCardWireChar());

        for (int i = 0; i < currentSize; i++) {
//            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic, null);
//            click_layout_two_click_pic_mingci_ceshi_xiugai_one
            View inflate = LayoutInflater.from(this).inflate(R.layout.click_layout_two_click_pic_mingci_ceshi_xiugai_one, null);

            if (i == 0) {
                inflate.setClickable(true);
                llClickLayout.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                if (currentSize != 4) {
                    lp.setMargins(25, 0, 0, 0);
                }
                inflate.setLayoutParams(lp);
                inflate.setClickable(true);
                llClickLayout.addView(inflate);
            }
            ImageView iv_click_pic = (ImageView) inflate.findViewById(R.id.iv_click_pic);

            if (i == 0) {
                GlideUtil.display(MingciTestOneActivityOld.this, nounTestBean.getList().get(0).getCardColorImage(), iv_click_pic);
            } else if (i == 1) {
                GlideUtil.display(MingciTestOneActivityOld.this, nounTestBean.getList().get(1).getCardColorImage(), iv_click_pic);
            }
        }

        TextView tv_content11 = (TextView) llClickLayout.getChildAt(0).findViewById(R.id.tv_choose2);
        TextView tv_content22 = (TextView) llClickLayout.getChildAt(1).findViewById(R.id.tv_choose2);

        tv_content11.setText(nounTestBean.getList().get(0).getCardColorChar());
        tv_content22.setText(nounTestBean.getList().get(1).getCardColorChar());

        String currentClickCardColorChar = nounTestBean.getList().get(0).getCardColorChar();
        String firstCardColorChar = nounTestBean.getCardColorChar();
        if (currentClickCardColorChar.equals(firstCardColorChar)) {
            isOrder = true;
        }

        String cardChar = nounTestBean.getList().get(0).getCardColorChar();
        String cardOneChar = nounTestBean.getCardColorChar();

        if (cardChar.equals(cardOneChar)) {
            llClickLayout.getChildAt(0).setTag("0");
            llClickLayout.getChildAt(1).setTag("1");

            currentFirst = 0;
            currentSecond = 1;

            voiceListData.add(nounTestBean.getList().get(0).getCardColorRecord());
            voiceListData.add(nounTestBean.getList().get(1).getCardColorRecord());

            Log.e("testvoicemingcitestone", nounTestBean.getList().get(0).getCardColorRecord());
            Log.e("testvoicemingcitestone", nounTestBean.getList().get(1).getCardColorRecord());

        } else {
            llClickLayout.getChildAt(1).setTag("0");
            llClickLayout.getChildAt(0).setTag("1");

            currentFirst = 1;
            currentSecond = 0;

            voiceListData.add(nounTestBean.getList().get(1).getCardColorRecord());
            voiceListData.add(nounTestBean.getList().get(0).getCardColorRecord());
        }

        final View childAt = llClickLayout.getChildAt(0);//第一个
        View viewById = childAt.findViewById(R.id.rl_card_layout);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOneMove) {
                    return;
                }

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
                String tag = (String) childAt.getTag();
                if (tag.equals("0")) {

                    stopTime();
                    currentClickTwoStartTime = System.currentTimeMillis();
                } else {
                    stopTime();
                }

                isOneMove = true;

                if (voiceListData.size() == 3) {//代表上一个没有播完
                    readyPlayNextOrderOne = true;
                } else if (voiceListData.size() == 2) {//代表上一个播完
                    if (player.isPlaying()) {
                        playNextCardVoice = true;
                    } else {
                        playLocalVoiceOnLineOnOrder(voiceListData.get(0));
                    }

                } else if (voiceListData.size() == 1) {//代表上一个播完

                    playLocalVoiceOnLineOnOrder(voiceListData.get(0));
                }


//                把小手的布局隐藏掉
                View childAt = llClickLayout.getChildAt(0);
                final RelativeLayout rl_hand = childAt.findViewById(R.id.rl_hand);

                if (rl_hand.getVisibility() == View.VISIBLE) {
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

                        int height = (int) (childAt.getHeight() * 0.40);
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
//
                                    if (tv_content11.getVisibility() == View.VISIBLE) {
                                        tv_content11.setVisibility(View.INVISIBLE);
                                    }
                                }
                                if (tag.equals("0")) {

                                    cb.setProgress(0);
                                    startTime(currentSecond, loopTimeTwo, loopRateTwo);

                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
                                    }
                                } else {
                                    cb.setProgress(0);

                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
                                    }
                                }
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (!isOrder) {
                                            if (pass.equals("1")) {//是否通过 1 是 0 否
                                                ivJinbiBottom.setVisibility(View.VISIBLE);

                                                int moneyTop = llMoney.getTop();
                                                int tiankongkuangTop = rlTiankongkuang.getTop();
                                                int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(MingciTestOneActivityOld.this, 32);

                                                int moneyLeft = llMoney.getLeft();
                                                int tiankongkuangLeft = rlTiankongkuang.getLeft();
                                                int iLeft = tiankongkuangLeft - moneyLeft;

                                                int screenWidth = MyUtils.getScreenWidth(MingciTestOneActivityOld.this) / 3;

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
                                                        if (Integer.parseInt(pass) == 1) {//pass	是	string	是否通过 1 是 0 否
                                                            int currentGold = gold + 1;
                                                            tvMoney.setText("x " + currentGold);
                                                        }

                                                        ivJinbiBottom.setVisibility(View.GONE);
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


        final View childAt1 = llClickLayout.getChildAt(1);//第二个
        View viewById1 = childAt1.findViewById(R.id.rl_card_layout);
        viewById1.setOnClickListener(new View.OnClickListener() {
            //        childAt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTwoMove) {
                    return;
                }

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
                String tag = (String) childAt1.getTag();
                if (tag.equals("0")) {

                    stopTime();
                    currentClickTwoStartTime = System.currentTimeMillis();
                } else {
                    stopTime();
                }
                isTwoMove = true;
                if (voiceListData.size() == 3) {//代表上一个没有播完

                    readyPlayNextOrderTwo = true;

                } else if (voiceListData.size() == 2) {//代表上一个播完

                    if (player.isPlaying()) {
                        playNextCardVoice = true;
                    } else {
                        playLocalVoiceOnLineOnOrder(voiceListData.get(0));
                    }

                } else if (voiceListData.size() == 1) {//代表上一个播完

                    playLocalVoiceOnLineOnOrder(voiceListData.get(0));
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

                        int height = (int) (childAt1.getHeight() * 0.40);
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

                                if (tag.equals("0")) {

                                    cb.setProgress(0);
                                    startTime(currentSecond, loopTimeTwo, loopRateTwo);


                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
                                    }
                                } else {
                                    cb.setProgress(0);

                                    View childAt = llClickLayout.getChildAt(0);
                                    View childAt1 = llClickLayout.getChildAt(1);

                                    if (!childAt.isClickable() && !childAt1.isClickable()) {
                                        stopTime();
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
                                                int i = tiankongkuangTop - moneyTop - MyUtils.dip2px(MingciTestOneActivityOld.this, 32);

                                                int screenWidth = MyUtils.getScreenWidth(MingciTestOneActivityOld.this) / 3;

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
                                                        if (Integer.parseInt(pass) == 1) {//pass	是	string	是否通过 1 是 0 否
                                                            int currentGold = gold + 1;
                                                            tvMoney.setText("x " + currentGold);
                                                        }
                                                        ivJinbiBottom.setVisibility(View.GONE);
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

    private void playLocalVoiceOnLineOnOrderClickAllCard(String videoName, Boolean isShouldMerge) {

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

    private void playLocalVoiceOnLineOnOrder(String videoName) {

        if (TextUtils.isEmpty(videoName)) {

            if (voiceListData.size() == 2) {//第二个语音说完了
                voiceListData.remove(0);

                if (playNextCardVoice) {//已经点击了，就播放第三个。
                    playLocalVoiceOnLineOnOrder(voiceListData.get(0));
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
                    if (isQuitActivity) {
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });

            if (voiceListData.size() == 2) {//第二个语音说完了
                voiceListData.remove(0);
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//
                    if (voiceListData.size() == 2) {//第二个语音说完了
                        voiceListData.remove(0);

                        if (playNextCardVoice) {//已经点击了，就播放第三个。
                            playLocalVoiceOnLineOnOrder(voiceListData.get(0));
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

    /**
     * 在线
     *
     * @param videoName
     */
    private void playLocalVoiceOnLineGroupRecord(String videoName) {

        if (TextUtils.isEmpty(videoName)) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playLocalVoice(String videoName) {
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);

            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();
            player.setOnCompletionListener(completionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private boolean isbgmusicover;

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:

                isQuitActivity = true;
                isFinish = true;

                ForegroundCallbacks.get().removeListener(foregroundCallbacks);
                foregroundCallbacks = null;
                screenListener.unregisterListener();
                screenListener = null;

                OkHttpUtils.getInstance().cancelTag(MingciTestOneActivityOld.this);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        isQuitActivity = true;
        isFinish = true;

        ForegroundCallbacks.get().removeListener(foregroundCallbacks);
        foregroundCallbacks = null;
        screenListener.unregisterListener();
        screenListener = null;

        OkHttpUtils.getInstance().cancelTag(MingciTestOneActivityOld.this);
        super.onBackPressed();
    }

    private void mergeText() {

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
                llTextParent.setBackgroundColor(MingciTestOneActivityOld.this.getResources().getColor(R.color.white));

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

                //缩放
                Animation animation = AnimationUtils.loadAnimation(MingciTestOneActivityOld.this, R.anim.anim_scale_pic);
                ivImg.startAnimation(animation);

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

                        playLocalVoiceOnLineGroupRecord(nounTestBean.getGroupRecord());

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
                        }, 5000);
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
        } else {

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


    @Override
    protected void onDestroy() {
        isQuitActivity = true;
        isFinish = true;

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

        if (null != screenListener) {
            screenListener.unregisterListener();
            screenListener = null;
        }
        EventBusUtil.unregister(this);
        EventBusUtil.post(new DelayTimeBean());
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
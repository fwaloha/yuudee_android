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
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.ScreenListener;
import com.easychange.admin.smallrain.views.CircleBarTime;
import com.easychange.admin.smallrain.views.DrawImgView;
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
import bean.MingciBean;
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
 * Created by chenlipeng on 2018/11/2 0002
 * describe:  名词训练1  笔晃动
 */
public class MingciOneActivity extends BaseActivity implements AsyncRequest {
    @BindView(R.id.iv_pause)
    ImageView ivPause;
    @BindView(R.id.cb)
    CircleBarTime cb;
    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.drawImg)
    DrawImgView drawImg;
    @BindView(R.id.fl_pic_kuang)
    FrameLayout flPicKuang;
    @BindView(R.id.ll_text_parent_bg)
    LinearLayout llTextParentBg;
    @BindView(R.id.ll_text_parent)
    LinearLayout llTextParent;
    @BindView(R.id.rl_tiankongkuang)
    RelativeLayout rlTiankongkuang;
    @BindView(R.id.ll_text_bg_parent)
    LinearLayout llTextBgParent;
    @BindView(R.id.iv_paint)
    ImageView ivPaint;
    @BindView(R.id.tv_paint)
    TextView tvPaint;
    @BindView(R.id.waveCirlceView)
    WaveCircleView waveCirlceView;
    @BindView(R.id.iv_point)
    ImageView ivPoint;
    @BindView(R.id.fl_point)
    RelativeLayout flPoint;

    //    黑猫，本来应该点击黑，但是我第一次选择了猫，黑卡片要小手辅助

    //    重现步骤
//[步骤]
//    在名词课件测试页面，点击上方大图，文字和文字框，
//            [结果]
//    下方正确的卡片出现小手辅助
//[期望]
//    点击上方大图，文字和文字框，下方卡片不应该出现小手辅助
//
//｛逻辑认真看｝
//    所有的测试级，点击了错误答案时，正确答案处出现小手辅助。否则，5S辅助
//    情况一：若进入页面，选择错误卡片，正确的卡片小手辅助，（比如：黑猫，本来应该点击黑，但是我第一次选择了猫，黑卡片要小手辅助，）
//    情况二：如果进入页面什么都没有点击，超过了规定时间，与之前逻辑一样，小手辅助出现。

    private int[] paintList = {
            R.drawable.red_paint,
            R.drawable.black_paint,
            R.drawable.white_paint,
            R.drawable.huabi_bufaguang,
            R.drawable.green_paint,
            R.drawable.gray_paint,
            R.drawable.blue_paint,
            R.drawable.qingse
    };
    /**
     *
     */
    private int[] paintLightList = {
            R.drawable.red_paint_guang,
            R.drawable.black_paint_guang,
            R.drawable.white_paint_guang,
            R.drawable.huabi_faguang,
            R.drawable.green_paint_guang,
            R.drawable.gray_paint_guang,
            R.drawable.blue_faguang,
            R.drawable.qingse_faguang
    };
    private boolean isClicked = false;
    private boolean clickable = false;

    private boolean isSetBackground = false;
    private boolean isSetCanvasImg = false;
    private Handler handler = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    drawImg.setcanvasImg(bitmap);//上面的图片
//                    drawImg.setBackground(null);//画笔动了之后的图片
//                    drawImg.postInvalidate();
                    isSetCanvasImg = true;
                    showFirstAnimation();
//                    ToastUtil.showToast(mContext, "setcanvasImg");
                    break;
                case 2:
                    byte[] bytes1 = (byte[]) msg.obj;
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                    Drawable drawable = new BitmapDrawable(bitmap1);
                    drawImg.setBackground(drawable);//画笔动了之后的图片
                    drawImg.postInvalidate();
//                    ToastUtil.showToast(mContext, "setBackground");
                    isSetBackground = true;
                    showFirstAnimation();
                    break;
                case 3://失败
                    break;
                default:
                    break;
            }
        }
    };
    private int length;
    private String scene;
    private boolean readyPlayNext = false;
    private boolean isFinish = false;
    private boolean isQuitActivity = false;
    private ScreenListener screenListener;
    private int currentPosition;
    private boolean yetMergeText = false;
    private View leftChildTextOne;
    private View rightChildTextTwo;
    private TextView tv_content1;
    private TextView tv_content2;
    private int translationX_distance;//右边文本。移动的距离
    private boolean isAnimationFinished = false;
    private boolean isBackgroundFinished = false;
    private boolean isYetUploadData = false;
    private ObjectAnimator obx1;
    private long currentPlayTime;
    private boolean isShouldMerge = false;
    private String TAG = "ddd";

    /**
     * 展示一开始的动画
     */
    private void showFirstAnimation() {
        if (isSetCanvasImg && isSetBackground) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playLocalVoiceOnLine(voiceListData.get(0));
                }
            }, 300);


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //语音完了之后缩放
                    Animation animation = AnimationUtils.loadAnimation(MingciOneActivity.this, R.anim.anim_scale_pic);
                    drawImg.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            clickable = true;
                            //缩放完了之后发光摇晃
                            if (!isClicked) {

                                if (nounTrainingBean.getColorPenChar().equals("黄")) {
                                    ivPaint.setImageResource(paintLightList[3]);
                                } else if (nounTrainingBean.getColorPenChar().equals("红")) {
                                    ivPaint.setImageResource(paintLightList[0]);
                                } else if (nounTrainingBean.getColorPenChar().equals("黑")) {
                                    ivPaint.setImageResource(paintLightList[1]);
                                } else if (nounTrainingBean.getColorPenChar().equals("白")) {
                                    ivPaint.setImageResource(paintLightList[2]);
                                } else if (nounTrainingBean.getColorPenChar().equals("绿")) {
                                    ivPaint.setImageResource(paintLightList[4]);
                                } else if (nounTrainingBean.getColorPenChar().equals("灰")) {
                                    ivPaint.setImageResource(paintLightList[5]);
                                } else if (nounTrainingBean.getColorPenChar().equals("蓝")) {
                                    ivPaint.setImageResource(paintLightList[6]);
                                } else if (nounTrainingBean.getColorPenChar().equals("青")) {
                                    ivPaint.setImageResource(paintLightList[7]);
                                }

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isClicked) {
                                            startRotateAnimation(ivPaint);//笔晃动
                                        }
                                    }
                                }, 0);


                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
            }, 3000);

        }
    }

    private AnimatorSet paintRotateSet;
    private MediaPlayer player;
    private int position = 0;
    private boolean isFirstInto = true;//是否第一次请求数据
    private boolean isTwoInto = true;//是否第一次请求数据

    private MingciBean.NounTrainingBean nounTrainingBean;
    private MingciBean model;
    private Timer timer;
    private int currentLoopTime;
    private int executeInterval = 100;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                double obj = (double) msg.obj;

                cb.setProgress((float) obj * currentLoopTime);
                currentLoopTime++;
            } else if (msg.what == 2) {

                if (!yetClickPint) {
                    flPoint.setVisibility(View.VISIBLE);
                    waveCirlceView.startWave();
                }

            }
        }
    };
    private int loopTimeFirst;
    private double loopRateFirst;
    private boolean yetClickPint = false;
    private MediaPlayer mediaPlayer;

    /**
     * 开始自动减时
     *
     * @param loopTime 循环次数
     * @param loopRate
     */
    private void startTime(int loopTime, double loopRate) {
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
        PreferencesHelper helper = new PreferencesHelper(MingciOneActivity.this);
        String token = helper.getToken();
//        Log.e("数据", "tokegetVerbn"+token);

        if (TextUtils.isEmpty(groupId)) {
            groupId = "";
        }
        String url = Setting.addTrainingResult();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("coursewareId", coursewareId);
        stringStringHashMap.put("scene", "1");// 1训练 2测试 3意义
        stringStringHashMap.put("module", "1");//1名词 2动词 3句子组成 4句子分解
        stringStringHashMap.put("startTime", startTime);
        stringStringHashMap.put("name", name);
        stringStringHashMap.put("pass", pass);

        stringStringHashMap.put("stayTimeList", stayTimeList);
//        stringStringHashMap.put("disTurbName", disTurbName);
//        stringStringHashMap.put("errorType", errorType);
        stringStringHashMap.put("stayTime", stayTime);
        stringStringHashMap.put("groupId", groupId);
        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());

//        if (isFinish) {
//            return;
//        }

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(MingciOneActivity.this, this));

        isYetUploadData = true;
    }

    //    你现在课件的问题是，进入页面，轮廓图线变大小后，才出现录音
//    这个是正确的课件顺序：
//    进入页面，首先传入录音：“苹果！”同时3s内播放苹果切图放大突出到边框并缩小回原始大小。
//    然后下方的红笔发光左右抖动（示意儿童点击），此时儿童点击红笔或下方文字，传入录音“红”。
//    你对照着你自己写的看看不就知道了

    List<String> voiceListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingci);
        ButterKnife.bind(this);

        position = getIntent().getIntExtra("position", 0);

        model = (MingciBean) getIntent().getSerializableExtra("model");
        groupId = getIntent().getStringExtra("groupId");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date();
        startTime = simpleDateFormat.format(date);
        startTimeMillis = System.currentTimeMillis();

        //1 初始化mediaplayer
        mediaPlayer = new MediaPlayer();

        length = getIntent().getIntExtra("length", -1);
        scene = getIntent().getStringExtra("scene");
        initListener();

        if (length != -1 && !TextUtils.isEmpty(scene)) {

            position = length;
            getNoun();
            return;
        }
//        Gson gson = new Gson();
//        model = gson.fromJson(sadfklsdf,
//                new TypeToken<MingciBean>() {
//                }.getType());
//
//        if(position<1){
//            position = 1;
//        }

        if (null == model) {
            getNoun();
        } else {
            initDataToView();
        }

        setScreenLock();

        ForegroundCallbacks.get().addListener(foregroundCallbackslistener);
        EventBusUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BreakNetBean(BreakNetBean event) {//断网
        ReleasePlayer();
        finish();
    }

    ForegroundCallbacks.Listener foregroundCallbackslistener = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {

            if (isFirstInto) {
                isFirstInto = false;
                return;
            }
            isQuitActivity = false;

            if (!yetMergeText && isShouldMerge) {
                mergeText();
            } else if (isShouldMerge && !isYetUploadData) {
                stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                        , stayTime, groupId);
            }
        }

        @Override
        public void onBecameBackground() {
            isQuitActivity = true;

            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                if (voiceListData.size() != 0) {
                    voiceListData.remove(0);
                }
                mediaPlayer.stop();
            }

            if (null != player && player.isPlaying()) {
                player.stop();
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
                if (isTwoInto) {
                    isTwoInto = false;
                    return;
                }

                Log.e("onScreenOn", "onScreenOn");
                isQuitActivity = false;

                if (!yetMergeText && isShouldMerge) {
                    mergeText();
                } else if (isShouldMerge && !isYetUploadData) {
                    stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                    addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                            , stayTime, groupId);
                }
            }

            @Override
            public void onScreenOff() {
                isQuitActivity = true;

                try {
                    if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                        if (voiceListData.size() != 0) {
                            voiceListData.remove(0);
                        }
                        mediaPlayer.stop();
                    }

                    if (null != player && player.isPlaying()) {
                        player.stop();
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 名词
     */
    private void getNoun() {
        PreferencesHelper helper = new PreferencesHelper(MingciOneActivity.this);
        String token = helper.getToken();
        Log.e("数据", "token" + token);
        String url = Setting.getNoun();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(MingciOneActivity.this, this));
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
            (MingciOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    isFirstInto = false;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            model = gson.fromJson(result,
                                    new TypeToken<MingciBean>() {
                                    }.getType());


                            if (length != -1 && !TextUtils.isEmpty(scene)) {
//                                scene	是	string	学习场景 1训练 2测试 3意义
                                if (scene.equals("1")) {
                                    position = length;
                                    initDataToView();
                                } else {

                                    Intent intent = new Intent(MingciOneActivity.this, MingciTestOneActivity.class);
//                        intent.putExtra("position", 9);
                                    intent.putExtra("position", length);
                                    intent.putExtra("model", model);

                                    if (TextUtils.isEmpty(groupId)) {
                                        intent.putExtra("groupId", "");
                                    } else {
                                        intent.putExtra("groupId", groupId);
                                    }

                                    startActivity(intent);
                                    ReleasePlayer();

                                    finish();
                                }
                                return;
                            }

                            initDataToView();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(2)) {//标记那个接口

            String result = (String) data;
            (MingciOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");
//                        {"msg":"添加成功！","code":200,"groupId":720}

                        if (code.equals("200")) {
//                            if (isFinish) {
//                                return;
//                            }

                            if (null != foregroundCallbackslistener) {
                                ForegroundCallbacks.get().removeListener(foregroundCallbackslistener);
                                foregroundCallbackslistener = null;

                                screenListener.unregisterListener();
                                screenListener = null;
                            }

                            int nounSense = model.getNounTraining().size();//10
                            position = position + 1;//0到9
                            if (position < nounSense) {
                                groupId = (String) jsonObject.getString("groupId");

                                Intent intent = new Intent(MingciOneActivity.this, MingciOneActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("model", model);
                                intent.putExtra("groupId", groupId);

                                startActivity(intent);
                                ReleasePlayer();

                                finish();
                            } else {
                                Intent intent = new Intent(MingciOneActivity.this, LetsTestActivity.class);
                                intent.putExtra("type", "mingci");
                                intent.putExtra("model", model);

                                startActivity(intent);
                                ReleasePlayer();

                                finish();
                            }


                        } else {
                        }
//                        ToastUtil.showToast(MingciOneActivity.this, msg1);

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
    private void initDataToView() {
//        id int 主键
//                            Wire_image	varchar(255)	线框图片
//                            Wire_char	varchar(20)	线框图文字
//                            Wire_record	varchar(255)	线框图录音
//                            Color_pen_char	varchar(20)	颜色笔文字
//                            Color_pen_record	varchar(255)	颜色笔录音
//                            group_image	varchar(255)	组合图片
//                            group_record	varchar(255)	组合录音
//                            create_time	Timestamp	创建时间
//                            update_time	Timestamp	更新时间
//                            states	varchar(20)	状态（1：正常 2：删除）
        nounTrainingBean = model.getNounTraining().get(position);

        coursewareId = nounTrainingBean.getId();
        name = nounTrainingBean.getGroupWord();
        int currentSize = 2;

        int grapWidth = MyUtils.dip2px(MingciOneActivity.this, 26);
        for (int i = 0; i < currentSize; i++) {//背景
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


        for (int i = 0; i < currentSize; i++) {//文本
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

        tv_content1.setText(nounTrainingBean.getColorPenChar());
        tv_content2.setText(nounTrainingBean.getWireChar());

        View tv_content1 = llTextParent.getChildAt(0);//自己文本
        tv_content1.setVisibility(View.INVISIBLE);

        View tv_content2 = llTextParent.getChildAt(1);//自己文本

        ViewTreeObserver viewTreeObserver = tv_content1.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                llTextParent.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int left1 = tv_content1.getLeft();
                int left2 = tv_content2.getLeft();
                int textWidth = tv_content2.getWidth();

                int dance = left2 - left1 - textWidth;

                translationX_distance = textWidth / 2 + dance / 2;
                ObjectAnimator obx = ObjectAnimator.ofFloat(tv_content2, "translationX", -translationX_distance);
                obx.setDuration(10);
                obx.start();
            }
        });
//        int textWidth = tv_content2.getWidth();
//        int tv_content_width = MyUtils.dip2px(MingciOneActivity.this, 110);
//        int dance = tv_content2.getLeft() - left1-tv_content_width;
//        int left = tv_content2.getLeft();
//        int screenWidth = MyUtils.getScreenWidth(MingciOneActivity.this);
////        translationX_distance = tv_content_width / 2 + left - screenWidth / 2;
//
//        translationX_distance = tv_content_width / 2+dance/2;
//        ObjectAnimator obx = ObjectAnimator.ofFloat(tv_content2, "translationX", -translationX_distance );
//        obx.setDuration(10);
//        obx.start();

        tv_content2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!yetClickPint) {
                    if (flPoint.getVisibility() == View.GONE) {
                        pass = "0";
                        flPoint.setVisibility(View.VISIBLE);
                        waveCirlceView.startWave();
                    }
                }
            }
        });

        tvPaint.setText(nounTrainingBean.getColorPenChar());

        drawImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!yetClickPint) {
                    if (flPoint.getVisibility() == View.GONE) {
                        pass = "0";
                        flPoint.setVisibility(View.VISIBLE);
                        waveCirlceView.startWave();
                    }
                }
                return true;
            }
        });
        if (nounTrainingBean.getColorPenChar().equals("黄")) {
            ivPaint.setImageResource(paintList[3]);
        } else if (nounTrainingBean.getColorPenChar().equals("红")) {
            ivPaint.setImageResource(paintList[0]);
        } else if (nounTrainingBean.getColorPenChar().equals("黑")) {
            ivPaint.setImageResource(paintList[1]);
        } else if (nounTrainingBean.getColorPenChar().equals("白")) {
            ivPaint.setImageResource(paintList[2]);
        } else if (nounTrainingBean.getColorPenChar().equals("绿")) {
            ivPaint.setImageResource(paintList[4]);
        } else if (nounTrainingBean.getColorPenChar().equals("灰")) {
            ivPaint.setImageResource(paintList[5]);
        } else if (nounTrainingBean.getColorPenChar().equals("蓝")) {
            ivPaint.setImageResource(paintList[6]);
        } else if (nounTrainingBean.getColorPenChar().equals("青")) {
            ivPaint.setImageResource(paintList[7]);
        }

        llIndicator.setSelectedPosition(position);

        int fristAssistTime = model.getTime().getHelpTime();
        loopTimeFirst = fristAssistTime * 1000 / executeInterval;//循环次数
        loopRateFirst = 100.00 / loopTimeFirst;//每次循环，圆环走的度数


//       wireImage---线框图片(素描)    groupImage---组合图片(彩绘)
        asyncGet(nounTrainingBean.getWireImage(), 1);
        asyncGet(nounTrainingBean.getGroupImage(), 2);

        voiceListData.add(nounTrainingBean.getWireRecord());
        voiceListData.add(nounTrainingBean.getColorPenRecord());
        Log.e("testvoicemingcione", nounTrainingBean.getWireRecord());
        Log.e("testvoicemingcione", nounTrainingBean.getColorPenRecord());
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        if (hasFocus) {
//            int left1 = tv_content1.getLeft();
//            int left2 = tv_content2.getLeft();
//            int textWidth = tv_content2.getWidth();
//
//            int dance = left2 - left1-textWidth;
//
//            translationX_distance = textWidth / 2+dance/2;
//            ObjectAnimator obx = ObjectAnimator.ofFloat(tv_content2, "translationX", -translationX_distance );
//            obx.setDuration(10);
//            obx.start();
//        }
//        super.onWindowFocusChanged(hasFocus);
//    }

    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {
        (MingciOneActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
//                ToastUtil.showToast(MingciOneActivity.this, errorMessage);
            }
        });

    }

    //12.	所有的训练级，点击了页面的大图或填空处，正确答案处出现小手辅助。否则2S辅助
    private void initListener() {
        drawImg.setOverBack(new DrawImgView.OverBack() {
            @Override
            public void second() {
                drawImg.move2Path();
            }

            @Override
            public void three() {
                drawImg.move3Path();
            }

            @Override
            public void onEnd() {
//                笔消失动画
                AnimationHelper.startPaintGoneAnimation(MingciOneActivity.this, ivPaint);

                if (!yetMergeText && !isQuitActivity) {
                    mergeText();
                } else {
                    //没有合并
                    isShouldMerge = true;
                }

            }
        });
    }

    private void playLocalVoiceBg(String videoName) {
        if (isQuitActivity) {
            isBackgroundFinished = true;
            return;
        }
//        if (null != player) {
//            player.stop();
//            player.reset();
//            player.release();
//        }
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
                        isBackgroundFinished = true;
                        return;
                    }
                    // 4 开始播放
                    player.start();
                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    ReleasePlayer(player);

                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }
                    isBackgroundFinished = true;

                    if (isAnimationFinished && !isYetUploadData && !isQuitActivity) {
                        stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                        addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                , stayTime, groupId);
                    }
                }
            });

            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                    ReleasePlayer(player);
                    if (player != null) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        //关键语句
                        player.reset();
                        player.release();
                        player = null;
                    }  Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
//            ReleasePlayer(player);
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
                //关键语句
                player.reset();
                player.release();
                player = null;
            }Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }
    }

    private void playLocalVoiceOnLine(String videoName) {
        if (isQuitActivity) {
            return;
        }
        if (TextUtils.isEmpty(videoName)) {
            if (voiceListData.size() == 2) {
                voiceListData.remove(0);

                if (readyPlayNext) {
                    playLocalVoiceOnLine(voiceListData.get(0));
                }
            } else if (voiceListData.size() == 1) {
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
//                    ReleasePlayer(mediaPlayer);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    } if (voiceListData.size() == 2) {
                        voiceListData.remove(0);

                        if (readyPlayNext) {
                            playLocalVoiceOnLine(voiceListData.get(0));
                        }
                    } else if (voiceListData.size() == 1) {
                        voiceListData.remove(0);
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
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
        } catch (Exception e) {
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

    /**
     * 在线
     *
     * @param videoName
     * @param b
     */
    private void playLocalVoiceOnLine(String videoName, boolean b) {
        if (isQuitActivity) {
            return;
        }
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
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    ReleasePlayer(mediaPlayer);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }}
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                    ReleasePlayer(mediaPlayer);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        //关键语句
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }   Log.e("bofang", "onError: i" + i + "i1" + i1);
                    return false;
                }
            });
        } catch (Exception e) {
//            ReleasePlayer(mediaPlayer);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                //关键语句
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } Log.e("bofang", "playLocalVoiceOnLineGroupRecord: IOException" + e.toString());
            e.printStackTrace();
        }


    }

    //    小手出现的时间是在画笔摇摆停止后，开始计时的，计时时间取值后台给的时间
    private void mergeText() {
        yetMergeText = true;

        playLocalVoiceOnLine(nounTrainingBean.getGroupRecord(), false);//播放总的录音

//        形容词修饰名词的名词短语的交互过程：进入页面，首先传入录音：“苹果！”同时3s内播放苹果切图放大突出到边框并缩小回原始大小。
//        然后下方的红笔发光左右抖动（示意儿童点击），此时儿童点击红笔或下方文字，传入录音“红”。
//        红笔开始涂在苹果上，同时红字及方框移动到苹果前面，红笔涂苹果4下涂满，涂满后红笔消失，此时红字和苹果字两个方框融合成红苹果，
//          同时传入录音“红苹果”，同时红苹果放大突出再返回。
//        播放完翻页进入下一张课件。
//        小手出现的时间是在画笔摇摆停止后，开始计时的，计时时间取值后台给的时间
//        如果儿童5s内没有点击红，红笔上会出现指圈提示辅助，直到儿童点击红笔继续。

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
        int distance_x = (i3 + i1 / 2 + i2 / 2) / 2 - 37;//15是调节用的  //像素

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
        llTextParent.setBackgroundColor(MingciOneActivity.this.getResources().getColor(R.color.white));

        obx1 = null;
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

                //语音完了之后缩放
                Animation animation1 = AnimationUtils.loadAnimation(MingciOneActivity.this, R.anim.anim_scale_pic);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isAnimationFinished = true;

                        if (isBackgroundFinished && !isYetUploadData && !isQuitActivity) {
                            stayTime = (System.currentTimeMillis() - startTimeMillis) / 1000 + "";
                            addTrainingResult(coursewareId + "", startTime, name, pass, stayTimeList, "", ""
                                    , stayTime, groupId);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                drawImg.startAnimation(animation1);
            }
        });
    }

    private void playBgVoice() {
//        random.nextInt(max)表示生成[0,max]之间的随机数
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

    @Override
    public void onBackPressed() {
        ReleasePlayer();
        isQuitActivity = true;
        isFinish = true;

        OkHttpUtils.getInstance().cancelTag(MingciOneActivity.this);

        ForegroundCallbacks.get().removeListener(foregroundCallbackslistener);
        foregroundCallbackslistener = null;
        screenListener.unregisterListener();
        screenListener = null;

        super.onBackPressed();
    }


    @OnClick({R.id.iv_pause, R.id.iv_home, R.id.iv_paint, R.id.fl_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                ReleasePlayer();

                isQuitActivity = true;
                isFinish = true;

                OkHttpUtils.getInstance().cancelTag(MingciOneActivity.this);

                ForegroundCallbacks.get().removeListener(foregroundCallbackslistener);
                foregroundCallbackslistener = null;
                screenListener.unregisterListener();
                screenListener = null;

                finish();
                break;
            case R.id.iv_paint:
            case R.id.fl_point:

                if (yetClickPint) {
                    return;
                }
                if (paintRotateSet != null)
                    paintRotateSet.cancel();//点击画笔的时候要停止晃动

                isClicked = true;//画笔动的时候，点击，不展示小手

                if (voiceListData.size() == 2) {//代表上一个没有播完

                    readyPlayNext = true;
                } else if (voiceListData.size() == 1) {//代表上一个播完
                    playLocalVoiceOnLine(voiceListData.get(0));
                }


                if (flPoint.getVisibility() == View.VISIBLE) {
                    pass = "0";
                }

                flPoint.setVisibility(View.GONE);

                startmovePaint();//笔飞上去
                startmoveText();//黄字飞上来

                yetClickPint = true;
                long l = -(currentClickOneStartTime - System.currentTimeMillis()) / 1000;

                if (l >= 50) {
                    l = 1;
                }
                stayTimeList = l + "";
                break;
        }
    }


    private void startmoveText() {
        if (tvPaint == null) return;
        //下面黄字飞上来
        int x1 = MyUtils.dip2px(this, (85 - 35) / 2 + 25);
//        int screenHeight = MyUtils.getScreenHeight(MyApplication.getGloableContext());

        int pxY = MyUtils.dip2px(this, 24) / 2;
        int y = tvPaint.getTop() - (rlTiankongkuang.getTop() + pxY);

        startTextMoveAnimation(tvPaint, x1, y);

        View tv_content2 = llTextParent.getChildAt(1);//自己文本
        ObjectAnimator obx = ObjectAnimator.ofFloat(tv_content2, "translationX", -translationX_distance / 10 + 20);
        obx.setDuration(1000);
        obx.start();
    }


    /*文字飞的动画*/
    public void startTextMoveAnimation(@NonNull final View view, int x, int y) {
        ObjectAnimator obx = ObjectAnimator.ofFloat(view, "translationX", -x);
        obx.setDuration(1000);
        ObjectAnimator oby = ObjectAnimator.ofFloat(view, "translationY", -y);
        oby.setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.52857f);
        scaleY.setDuration(1000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.72857f);
        scaleX.setDuration(1000);
        obx.start();
        oby.start();
        scaleX.start();
        scaleY.start();
        scaleY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (view != null)
                view.setVisibility(View.GONE);
                View tv_content1 = llTextParent.getChildAt(0);//自己文本
                tv_content1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }


    /*笔飞上去*/
    private void startmovePaint() {
        AnimationHelper.startPaintMoveAnimation(ivPaint, new AnimationHelper.AnimationEndInterface() {
            @Override
            public void onAnimationEnd(View view) {
                drawImg.movePath();
                ivPaint.setClickable(false);
                AnimationHelper.startPaintDrawAnimation(ivPaint);//模拟笔画画的动画
            }
        });
    }

    /*笔晃动动画*/
    public void startRotateAnimation(View view) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(view, "rotation", 0f, -15f);
        ObjectAnimator rotation2 = ObjectAnimator.ofFloat(view, "rotation", -15f, 15f);
        ObjectAnimator rotation3 = ObjectAnimator.ofFloat(view, "rotation", 15f, -15f);
        ObjectAnimator rotation4 = ObjectAnimator.ofFloat(view, "rotation", -15f, 0f);
        paintRotateSet = new AnimatorSet();
        paintRotateSet.playSequentially(rotation1, rotation2, rotation3, rotation4);
        paintRotateSet.setDuration(1000);
        paintRotateSet.start();
        paintRotateSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

//                10.	训练级（进度圈隐藏），测试级，意义级，所有进度圈计时，是在上方静态图片放大突出并缩回原形后开始计时的，计时时间取值后台设置
                currentClickOneStartTime = System.currentTimeMillis();
                startTime(loopTimeFirst, loopRateFirst);

            }
        });
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
    protected void onDestroy() {
        ReleasePlayer();
        EventBusUtil.unregister(this);
        drawImg.recycle();
        if (null != foregroundCallbackslistener) {
            ForegroundCallbacks.get().removeListener(foregroundCallbackslistener);
            foregroundCallbackslistener = null;

            screenListener.unregisterListener();
            screenListener = null;
        }

        isFinish = true;
        isQuitActivity = true;
        handler.removeCallbacksAndMessages(null);
        handler = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
//        调用MediaPlay的stop()停止播放，然后再调用release()。后面一个不是必需的，只是为了释放资源

        EventBusUtil.post(new DelayTimeBean());
        super.onDestroy();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 异步get,直接调用
     */
    private void asyncGet(String IMAGE_URL, final int i) {
        if (TextUtils.isEmpty(IMAGE_URL)) {

            return;
        }
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get().url(IMAGE_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( handler == null) return;
                Message message = handler.obtainMessage();
                if (response.isSuccessful()) {
                    if (i == 1) {
                        message.what = 1;
                        message.obj = response.body().bytes();
                        if (null != handler) {
                            handler.sendMessage(message);
                        }
                    }
                    if (i == 2) {
                        message.what = 2;
                        message.obj = response.body().bytes();
                        if (null != handler) {
                            handler.sendMessage(message);
                        }
                    }
                } else {
                    if (null != handler) {
                        handler.sendEmptyMessage(3);
                    }
                }
            }
        });
    }
}


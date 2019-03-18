package com.easychange.admin.smallrain.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import bean.LookLatelyBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;

/**
 * Created by chenlipeng on 2018/11/13 0013
 * describe:  通关成功页面(拼图全都飞出去)
 */
public class PinTuAllFlyActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_pin1)
    ImageView ivPin1;
    @BindView(R.id.iv_pin2)
    ImageView ivPin2;
    @BindView(R.id.iv_pin3)
    ImageView ivPin3;
    @BindView(R.id.iv_pin4)
    ImageView ivPin4;
    @BindView(R.id.iv_pin5)
    ImageView ivPin5;
    @BindView(R.id.iv_pin6)
    ImageView ivPin6;
    @BindView(R.id.iv_pin7)
    ImageView ivPin7;
    @BindView(R.id.iv_pin8)
    ImageView ivPin8;
    @BindView(R.id.iv_pin9)
    ImageView ivPin9;
    @BindView(R.id.iv_gif)
    ImageView iv_gif;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    private int anInt1;
    private boolean dongci = false;
    private boolean mingci = false;
    private boolean juzichengzu = false;
    private boolean juzifenjie = false;
    private String currentType = "1";
    private LookLatelyBean.PlayerModuleBean playerModule;
    private PreferencesHelper helper;
    private Handler handler;
    private boolean isDestroyActivity = false;
//    private LookLatelyBean.AgainModuleBean againModule;

    //    情况一：四个模块训练级中一进来点击大图或者填空处，视为错误操作，正确答案处要出现小手辅助
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_tu);
        ButterKnife.bind(this);
        ivHome.setOnClickListener(this);

        handler = new Handler();

        helper = new PreferencesHelper(PinTuAllFlyActivity.this);
        getSystemStatistics();

        MyApplication application = (MyApplication) getApplication();
        application.isBunching = true;

        anInt1 = getIntent().getIntExtra("anInt1", 0);

        dongci = getIntent().getBooleanExtra("dongci", false);
        mingci = getIntent().getBooleanExtra("mingci", false);
        juzichengzu = getIntent().getBooleanExtra("juzichengzu", false);
        juzifenjie = getIntent().getBooleanExtra("juzifenjie", false);

        if (anInt1 != 0 && mingci) {
            currentType = "1";
        } else if (anInt1 != 0 && dongci) {
            currentType = "2";
        } else if (anInt1 != 0 && juzichengzu) {
            currentType = "3";
        } else if (anInt1 != 0 && juzifenjie) {
            currentType = "4";
        }

//        3、最后一组进入强化物（遮挡模块全部飞出去的强化物），没有减去笑脸。
        addFortifier(currentType, "1");
//        if (anInt1 >= 10) {
        tvMoney.setText("x " + anInt1);
//        }

        clearanceReset(currentType);

        feichu(1);

        String currentGifCornerMark = helper.getString("xiaoyudi", "currentGifCornerMark", "-1");
        if (!currentGifCornerMark.equals("-1")) {

            int currentGifCornerMarkInt = Integer.parseInt(currentGifCornerMark);
            int currentPintuId = 0;
            if (currentGifCornerMarkInt == 0) {
                currentPintuId = R.drawable.pintu_bg0;
            } else if (currentGifCornerMarkInt == 1) {
                currentPintuId = R.drawable.pintu_bg1;
            } else if (currentGifCornerMarkInt == 2) {
                currentPintuId = R.drawable.pintu_bg2;
            } else if (currentGifCornerMarkInt == 3) {
                currentPintuId = R.drawable.pintu_bg3;
            } else if (currentGifCornerMarkInt == 4) {
                currentPintuId = R.drawable.pintu_bg4;
            } else if (currentGifCornerMarkInt == 5) {
                currentPintuId = R.drawable.pintu_bg5;
            } else if (currentGifCornerMarkInt == 6) {
                currentPintuId = R.drawable.pintu_bg6;
            } else if (currentGifCornerMarkInt == 7) {
                currentPintuId = R.drawable.pintu_bg7;
            } else if (currentGifCornerMarkInt == 8) {
                currentPintuId = R.drawable.pintu_bg8;
            } else if (currentGifCornerMarkInt == 9) {
                currentPintuId = R.drawable.pintu_bg9;
            }

            Glide.with(this).load(currentPintuId).into(iv_gif);
        } else {
            String currentGifListData = helper.getString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");
            if (TextUtils.isEmpty(currentGifListData)) {
                helper.saveString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");
                currentGifListData = helper.getString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");
            }

            String[] splitListData = currentGifListData.split(",");
            //    生成0-N之间的随机数，包括0，不包括N。
            int randNum = rand.nextInt(splitListData.length);

            String currentChooseGifCornerMark = splitListData[randNum];

            int[] tempsplitListData = new int[splitListData.length];
            for (int i = 0; i < splitListData.length; i++) {
                tempsplitListData[i] = Integer.parseInt(splitListData[i]);
            }

            int[] getNewListData = delete(randNum, tempsplitListData);//调用delete方法
            String tempGifListData = "";
            for (int i = 0; i < getNewListData.length; i++) {
                if (getNewListData.length - 1 == i) {
                    tempGifListData = tempGifListData + getNewListData[i];
                } else {
                    tempGifListData = tempGifListData + getNewListData[i] + ",";
                }
            }

            helper.saveString("xiaoyudi", "currentGifListData", tempGifListData);

            helper.saveString("xiaoyudi", "currentGifCornerMark", currentChooseGifCornerMark + "");

            int currentGifCornerMarkInt = Integer.parseInt(currentChooseGifCornerMark);
            int currentPintuId = 0;

            if (currentGifCornerMarkInt == 0) {
                currentPintuId = R.drawable.pintu_bg0;
            } else if (currentGifCornerMarkInt == 1) {
                currentPintuId = R.drawable.pintu_bg1;
            } else if (currentGifCornerMarkInt == 2) {
                currentPintuId = R.drawable.pintu_bg2;
            } else if (currentGifCornerMarkInt == 3) {
                currentPintuId = R.drawable.pintu_bg3;
            } else if (currentGifCornerMarkInt == 4) {
                currentPintuId = R.drawable.pintu_bg4;
            } else if (currentGifCornerMarkInt == 5) {
                currentPintuId = R.drawable.pintu_bg5;
            } else if (currentGifCornerMarkInt == 6) {
                currentPintuId = R.drawable.pintu_bg6;
            } else if (currentGifCornerMarkInt == 7) {
                currentPintuId = R.drawable.pintu_bg7;
            } else if (currentGifCornerMarkInt == 8) {
                currentPintuId = R.drawable.pintu_bg8;
            } else if (currentGifCornerMarkInt == 9) {
                currentPintuId = R.drawable.pintu_bg9;
            }

            Glide.with(this).load(currentPintuId).into(iv_gif);

        }
    }

    /**
     * token	是	string	登录标识
     * module	是	string	模块 1名词 2动词 3句子组成 4句子分解’
     */
    private void clearanceReset(String module) {
        PreferencesHelper helper = new PreferencesHelper(PinTuAllFlyActivity.this);
        String token = helper.getToken();

        String url = Setting.clearanceReset();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);

        Log.e("数据", stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(5)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuAllFlyActivity.this, this));
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(PinTuAllFlyActivity.this);
        String token = helper.getToken();

//             module	是	string	模块
//        state	是	string	1减少 0添加
        String url = Setting.addFortifier();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);
        stringStringHashMap.put("state", state);

        Log.e("数据", stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(4)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuAllFlyActivity.this, this));
    }

    /**
     *
     */
    private void getSystemStatistics() {
        PreferencesHelper helper = new PreferencesHelper(PinTuAllFlyActivity.this);
        String token = helper.getToken();

        String url = Setting.getSystemStatistics();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuAllFlyActivity.this, this));
    }

    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(3)) {//
//
            String result = (String) data;
            (PinTuAllFlyActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    String code = null;
                    String dataStr = null;

                    try {
                        jsonObject = new JSONObject(result);
                        code = jsonObject.getString("code");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            LookLatelyBean model = gson.fromJson(result,
                                    new TypeToken<LookLatelyBean>() {
                                    }.getType());
//                            againModule里面对应四个模块的通关状态,playerModule里面对应四个模块是否是再次通关
//                            playerModule      如果是0，一次也没有通关，如果是1，代表再次通关
                            playerModule = model.getPlayerModule();
//                            againModule = model.getAgainModule();


                        }
                    } catch (JSONException e) {//这一般不会走的
                        e.printStackTrace();
                        String s = e.toString();
                    }
                }
            });
        } else if (object.equals(4)) {//
            String result = (String) data;
            (PinTuAllFlyActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");

                        if (code.equals("200")) {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else if (object.equals(5)) {//
            String result = (String) data;
            (PinTuAllFlyActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
//                    { "msg": "操作成功！",
//                            "code": 200 }
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");

                        if (code.equals("200")) {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void feichu(int dangqian_pintu_xiabiao) {

        int dangqian_pintu_xiabiao1 = dangqian_pintu_xiabiao;
        for (int i = 1; i < 10; i++) {
            ImageView ivPin;
            if (dangqian_pintu_xiabiao1 == 1) {
                ivPin = ivPin1;
            } else if (dangqian_pintu_xiabiao1 == 2) {
                ivPin = ivPin2;
            } else if (dangqian_pintu_xiabiao1 == 3) {
                ivPin = ivPin3;
            } else if (dangqian_pintu_xiabiao1 == 4) {
                ivPin = ivPin4;
            } else if (dangqian_pintu_xiabiao1 == 5) {
                ivPin = ivPin5;
            } else if (dangqian_pintu_xiabiao1 == 6) {
                ivPin = ivPin6;
            } else if (dangqian_pintu_xiabiao1 == 7) {
                ivPin = ivPin7;
            } else if (dangqian_pintu_xiabiao1 == 8) {
                ivPin = ivPin8;
            } else {
                ivPin = ivPin9;
            }
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            ObjectAnimator obx = ObjectAnimator.ofFloat(ivPin, "translationX", 1600);
            ObjectAnimator oby = ObjectAnimator.ofFloat(ivPin, "translationY", -1600);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(obx, oby);
            set.setDuration(500 * dangqian_pintu_xiabiao1);
            set.start();
            dangqian_pintu_xiabiao1++;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMoney.setText("x " + 0);
            }
        },2000);

        handler.postDelayed(runnable, 1000 * 60);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isDestroyActivity) return;

            if (anInt1 != 0 && mingci) {
                String player1 = playerModule.getPlayer1();
//                String module1 = againModule.getModule1();
                if (TextUtils.isEmpty(player1)) {
                    player1 = "0";
                }

                if (player1.equals("0")) {//1再次通关 0非

                    if (setCurrentGifCornerNum()) return;

                    Intent intent1 = new Intent(PinTuAllFlyActivity.this, PassATestActivity.class);
                    startActivity(intent1);
                    finish();

                } else {
                    finish();
                }

            }

            if (anInt1 != 0 && dongci) {
//                    data--module通关到哪,1名词,2动词,3,句子成组,4句子分解
                String player2 = playerModule.getPlayer2();
//                String module2 = againModule.getModule2();
                if (TextUtils.isEmpty(player2)) {
                    player2 = "0";
                }

                if (player2.equals("0")) {//1再次通关 0非
                    if (setCurrentGifCornerNum()) return;

                    Intent intent1 = new Intent(PinTuAllFlyActivity.this, PassATestActivity.class);
                    intent1.putExtra("isIntoJuziFenjie", false);
                    intent1.putExtra("currentPosition", 1);
                    startActivity(intent1);

                    finish();
                } else {
                    finish();
                }

            }
            if (anInt1 != 0 && juzichengzu) {

                String player3 = playerModule.getPlayer3();
//                String module3 = againModule.getModule3();
                if (TextUtils.isEmpty(player3)) {
                    player3 = "0";
                }

                if (player3.equals("0")) {//1再次通关 0非
                    if (setCurrentGifCornerNum()) return;

                    Intent intent1 = new Intent(PinTuAllFlyActivity.this, PassATestActivity.class);
                    intent1.putExtra("isIntoJuziFenjie", true);
                    intent1.putExtra("currentPosition", 2);
                    startActivity(intent1);

                    finish();

                } else {
                    finish();
                }


            }

            if (anInt1 != 0 && juzifenjie) {
                String player4 = playerModule.getPlayer4();
//                String module4 = againModule.getModule4();

                if (TextUtils.isEmpty(player4)) {
                    player4 = "0";
                }

                if (player4.equals("0")) {//1再次通关 0非
                    if (setCurrentGifCornerNum()) return;

                    Intent intent1 = new Intent(PinTuAllFlyActivity.this, PassATestActivity.class);
                    intent1.putExtra("isIntoJuziFenjie", false);
                    intent1.putExtra("currentPosition", 3);
                    startActivity(intent1);

                    finish();

                } else {
                    finish();
                }

            }
        }
    };//通关了，强化物，全部飞出去。停留1分钟。

    private boolean setCurrentGifCornerNum() {
        String currentGifListData = helper.getString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");

        if (currentGifListData.equals("")) {
            helper.saveString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");
            currentGifListData = helper.getString("xiaoyudi", "currentGifListData", "0,1,2,3,4,5,6,7,8,9");
        }

        String[] splitListData = currentGifListData.split(",");
        int randNum = rand.nextInt(splitListData.length);
        String currentChooseGifCornerMark = splitListData[randNum];

        int[] tempsplitListData = new int[splitListData.length];
        for (int i = 0; i < splitListData.length; i++) {
            tempsplitListData[i] = Integer.parseInt(splitListData[i]);
        }

        int[] getNewListData = delete(randNum, tempsplitListData);//调用delete方法

        String tempGifListData = "";
        for (int i = 0; i < getNewListData.length; i++) {
            if (getNewListData.length - 1 == i) {
                tempGifListData = tempGifListData + getNewListData[i];
            } else {
                tempGifListData = tempGifListData + getNewListData[i] + ",";
            }
        }
        helper.saveString("xiaoyudi", "currentGifListData", tempGifListData);
        helper.saveString("xiaoyudi", "currentGifCornerMark", currentChooseGifCornerMark + "");
        return false;
    }

    //    生成0-N之间的随机数，包括0，不包括N。
    private static final int N = 11;
    Random rand = new Random();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                isDestroyActivity = true;
                handler.removeCallbacks(runnable);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        isDestroyActivity = true;
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

    /**
     * 删除方式1
     */
    public int[] delete(int index, int array[]) { //数组的删除其实就是覆盖前一位
        int[] arrNew = new int[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }


    @Override
    public void RequestError(Object var1, int var2, String var3) {
    }

    @Override
    protected void onDestroy() {
        isDestroyActivity = true;
        super.onDestroy();
    }
}

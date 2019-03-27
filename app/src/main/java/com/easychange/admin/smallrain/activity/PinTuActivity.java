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

import bean.DongciGuoGuan;
import bean.JuziChengzuGuan;
import bean.JuzifenjieGuoGuan;
import bean.MingciGuoGuan;
import bean.ToyListBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;

/**
 * 拼图页面
 */
public class PinTuActivity extends BaseActivity implements View.OnClickListener, AsyncRequest {
    @BindView(R.id.iv_xiaolian)
    ImageView ivXiaolian ;
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
    private boolean dongci;
    private boolean mingci;
    private boolean juzichengzu;
    private boolean juzifenjie;
    private String currentType = "1";
    private Handler handler;

    //    情况一：四个模块训练级中一进来点击大图或者填空处，视为错误操作，正确答案处要出现小手辅助
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_tu);
        ButterKnife.bind(this);
        ivHome.setOnClickListener(this);

        handler = new Handler();

        MyApplication application = (MyApplication) getApplication();
        application.isBunching = true;
        PreferencesHelper helper = new PreferencesHelper(PinTuActivity.this);

        anInt1 = getIntent().getIntExtra("anInt1", 0);
        dongci = getIntent().getBooleanExtra("dongci", false);
//        intent.putExtra("anInt1", anInt1);
//        intent.putExtra("mingci", true);
        mingci = getIntent().getBooleanExtra("mingci", false);
        juzichengzu = getIntent().getBooleanExtra("juzichengzu", false);
        juzifenjie = getIntent().getBooleanExtra("juzifenjie", false);

        MyApplication application1 = (MyApplication) getApplication();

        if (anInt1 != 0 && mingci) {
            currentType = "1";

            MingciGuoGuan mingciGuoGuan1 = application1.mingciGuoGuan;
            int dangqian_pintu_xiabiao = mingciGuoGuan1.dangqian_pintu_xiabiao;

            if (dangqian_pintu_xiabiao == 9) {
                mingciGuoGuan1.dangqian_pintu_xiabiao = 1;
            } else {
                mingciGuoGuan1.dangqian_pintu_xiabiao = mingciGuoGuan1.dangqian_pintu_xiabiao + 1;
            }

            toyList(1 + "");
            addFortifier(currentType, "1");
            tvMoney.setText("x " + (anInt1));
        }

        if (anInt1 != 0 && dongci) {
            currentType = "2";
//            DongciGuoGuan dongciGuoGuan = (DongciGuoGuan) getIntent().getSerializableExtra("bean");
//            int dangqian_pintu_xiabiao = dongciGuoGuan.dangqian_pintu_xiabiao;

            DongciGuoGuan dongciGuoGuan = application1.dongciGuoGuan;
            int dangqian_pintu_xiabiao = dongciGuoGuan.dangqian_pintu_xiabiao;

            if (dangqian_pintu_xiabiao == 9) {
                dongciGuoGuan.dangqian_pintu_xiabiao = 1;
            } else {
                dongciGuoGuan.dangqian_pintu_xiabiao = dongciGuoGuan.dangqian_pintu_xiabiao + 1;
            }

            toyList(2 + "");
//            feichu(dangqian_pintu_xiabiao);
            addFortifier(currentType, "1");
            tvMoney.setText("x " + (anInt1));
        }

        if (anInt1 != 0 && juzichengzu) {
            currentType = "3";

            JuziChengzuGuan juziChengzuGuan = application1.juziChengzuGuan;
            int dangqian_pintu_xiabiao = juziChengzuGuan.dangqian_pintu_xiabiao;

            if (dangqian_pintu_xiabiao == 9) {
                juziChengzuGuan.dangqian_pintu_xiabiao = 1;
            } else {
                juziChengzuGuan.dangqian_pintu_xiabiao = juziChengzuGuan.dangqian_pintu_xiabiao + 1;
            }

            toyList(3 + "");
//            feichu(dangqian_pintu_xiabiao);
            addFortifier(currentType, "1");
            tvMoney.setText("x " + (anInt1));
        }

        if (anInt1 != 0 && juzifenjie) {
            currentType = "4";

            JuzifenjieGuoGuan juzifenjieGuoGuan = application1.juzifenjieGuoGuan;
            int dangqian_pintu_xiabiao = juzifenjieGuoGuan.dangqian_pintu_xiabiao;

            if (dangqian_pintu_xiabiao == 9) {
                juzifenjieGuoGuan.dangqian_pintu_xiabiao = 1;
            } else {
                juzifenjieGuoGuan.dangqian_pintu_xiabiao = juzifenjieGuoGuan.dangqian_pintu_xiabiao + 1;
            }

            toyList(4 + "");
//            feichu(dangqian_pintu_xiabiao);
            addFortifier(currentType, "1");
            tvMoney.setText("x " + (anInt1));
        }

        String currentGifCornerMark = helper.getString("xiaoyudi", "currentGifCornerMark", "-1");
        if (currentGifCornerMark.equals("-1")) {

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

        } else {

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
        }


//        Glide.with(this).load(R.drawable.pintu_bg0).into(iv_gif);
    }


    Random rand = new Random();

    //    强化物gif动画的逻辑
    private void feichu(int number, int dangqian_pintu_xiabiao) {

        for (int i = 1; i < 10; i++) {

            if (dangqian_pintu_xiabiao > i) {
                if (i == 1) {
                    ivPin1.setVisibility(View.GONE);
                } else if (i == 2) {
                    ivPin2.setVisibility(View.GONE);
                } else if (i == 3) {
                    ivPin3.setVisibility(View.GONE);
                } else if (i == 4) {
                    ivPin4.setVisibility(View.GONE);
                } else if (i == 5) {
                    ivPin5.setVisibility(View.GONE);
                } else if (i == 6) {
                    ivPin6.setVisibility(View.GONE);
                } else if (i == 7) {
                    ivPin7.setVisibility(View.GONE);
                } else if (i == 8) {
                    ivPin8.setVisibility(View.GONE);
                } else {
                    ivPin9.setVisibility(View.GONE);
                }
            }

        }
        ImageView ivPin;
        if (dangqian_pintu_xiabiao == 1) {
            ivPin = ivPin1;
        } else if (dangqian_pintu_xiabiao == 2) {
            ivPin = ivPin2;
        } else if (dangqian_pintu_xiabiao == 3) {
            ivPin = ivPin3;
        } else if (dangqian_pintu_xiabiao == 4) {
            ivPin = ivPin4;
        } else if (dangqian_pintu_xiabiao == 5) {
            ivPin = ivPin5;
        } else if (dangqian_pintu_xiabiao == 6) {
            ivPin = ivPin6;
        } else if (dangqian_pintu_xiabiao == 7) {
            ivPin = ivPin7;
        } else if (dangqian_pintu_xiabiao == 8) {
            ivPin = ivPin8;
        } else {
            ivPin = ivPin9;
        }
//        number	是	int	积木第n块
//        module	是	string	1名词 2动词 3句子组成 4句子分解
        toyUseToy(number + "", currentType);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator obx = ObjectAnimator.ofFloat(ivPin, "translationX", 1600);
                ObjectAnimator oby = ObjectAnimator.ofFloat(ivPin, "translationY", -1600);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(obx, oby);
                set.setDuration(2000);
                set.start();
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMoney.setText("x " + (anInt1 - 10));
                if ((anInt1-10)==0){
//                    ivXiaolian.setVisibility(View.GONE);
//                    tvMoney.setVisibility(View.GONE);
                } else {
                    ivXiaolian.setVisibility(View.VISIBLE);
                    tvMoney.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);

        handler.postDelayed(runnable, 1000 * 30);//强化物，飞出1个积木。停留10秒。
//        12.05 16.金币累计十个进入强化物停留时间为30s后在跳转页面，全部通关后，60s后在跳转页面

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (anInt1 != 0 && dongci) {
                Intent intent = new Intent(PinTuActivity.this, LetsTestToTrainActivity.class);
                intent.putExtra("type", "dongci_test_train");
                startActivity(intent);
                finish();

//                    Intent intent = new Intent(PinTuActivity.this, DongciTrainOneActivity.class);
////                            intent.putExtra("position", 9);
//                    startActivity(intent);
//                    finish();
            }

            if (anInt1 != 0 && mingci) {
                Intent intent = new Intent(PinTuActivity.this, LetsTestToTrainActivity.class);
                intent.putExtra("type", "mingci");
                startActivity(intent);
                finish();
//                    Intent intent = new Intent(PinTuActivity.this, MingciOneActivity.class);
//
//                    startActivity(intent);
//                    finish();
            }
            if (anInt1 != 0 && juzichengzu) {
                Intent intent = new Intent(PinTuActivity.this, LetsTestToTrainActivity.class);
                intent.putExtra("type", "juzichengzu");
                startActivity(intent);
                finish();

//                    Intent intent = new Intent(PinTuActivity.this, JuZiChengZuXunLianActivity.class);
////                    intent.putExtra("position", 9);
//                    startActivity(intent);
//                    finish();
            }

            if (anInt1 != 0 && juzifenjie) {
                Intent intent = new Intent(PinTuActivity.this, LetsTestToTrainActivity.class);
                intent.putExtra("type", "juzifenjie");
                startActivity(intent);
                finish();

//                    Intent intent = new Intent(PinTuActivity.this, LetsTestToTrainActivity.class);
//                    intent.putExtra("type", "juzichengzu");
//                    startActivity(intent);
//                    finish();

//                    Intent intent = new Intent(PinTuActivity.this, JuZiFeiJieXunLianActivityFourClick.class);
////                    intent.putExtra("position", 9);
//                    startActivity(intent);
//                    finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                handler.removeCallbacks(runnable);
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

    /**
     * @param module
     * @param state  token	是	string	用户信息
     *               module	是	string	模块
     *               state	是	string	1减少 0添加
     */
    private void addFortifier(String module, String state) {
        PreferencesHelper helper = new PreferencesHelper(PinTuActivity.this);
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
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuActivity.this, this));
    }

    /**
     * 修改儿童积木状态
     */
    private void toyUseToy(String number, String module) {
//        token	是	string	用户信息
//        number	是	int	积木第n块
//        module	是	string	1名词 2动词 3句子组成 4句子分解
        PreferencesHelper helper = new PreferencesHelper(PinTuActivity.this);
        String token = helper.getToken();

        String url = Setting.toyUseToy();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("number", number);
        stringStringHashMap.put("module", module);


        Log.e("shu", "toyUseToy: " + stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuActivity.this, this));
    }

    /**
     * 查询儿童剩余积木数
     *
     * @param module
     */
    private void toyList(String module) {
//        token 是 string 用户x信息
//     *module 是 string 1 名词 2 动词 3 句子组成 4 句子分解
//                * number int 第n块积木
        PreferencesHelper helper = new PreferencesHelper(PinTuActivity.this);
        String token = helper.getToken();

        String url = Setting.toyList();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("module", module);

        Log.e("shu", "toyList: " + stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PinTuActivity.this, this));
    }

    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(1)) {//查询儿童剩余积木数

            String result = (String) data;
            (PinTuActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            ToyListBean model = gson.fromJson(result,
                                    new TypeToken<ToyListBean>() {
                                    }.getType());
//                            他写的这个数组,有几个元素,那么当前就剩余几块积木,number代表当前剩余的积木的编号,编号为1-9

                            int size = model.getData().size();
                            int flyAwayPosition = 10 - size;
                            if (size != 0) {
//                                Random rand =new Random(25);
//                                int i;
//                                i=rand.nextInt(100);
//                                初始化时25并没有起直接作用（注意：不是没有起作用）,rand.nextInt(100);中的100是随机数的上限,产生的随机数为0-100的整数,不包括100。

                                Random rand = new Random(25);
                                int currentPosition;
                                currentPosition = rand.nextInt(model.getData().size());

                                ToyListBean.DataBean dataBean = model.getData().get(currentPosition);
                                int number = dataBean.getNumber();

                                feichu(number, flyAwayPosition);
                            }

                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//修改儿童积木状态
            String result = (String) data;
            (PinTuActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(3)) {//标记那个接口

            String result = (String) data;
            (PinTuActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

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

}

package com.easychange.admin.smallrain.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.entity.CustomsClearanceSuccessBean;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;

import bean.JuzifenjieGuoGuan;
import butterknife.BindView;
import butterknife.ButterKnife;
import juzi.JuZiFeiJieXunLianActivityFourClick;

/**
 * Created by chenlipeng on 2018/11/13 0013
 * describe:  通关成功页面
 */
public class PassATestActivity extends AppCompatActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private Handler handler = new Handler();
    private MediaPlayer player;

    private void playLocalVoice(String videoName) {
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();
//            player.setOnCompletionListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_atest);
        ButterKnife.bind(this);
//
        playLocalVoice("t通关成功.MP3");

//        名词,动词,句子分解通关,进入通关成功的页面,3S返回首页  句子分组通关后进入通关成功的页面3S自动进入句子分解模块
        boolean isIntoJuziFenjie = getIntent().getBooleanExtra("isIntoJuziFenjie", false);

        int currentPosition = getIntent().getIntExtra("currentPosition", 0);
        if (currentPosition == 0) {
            int passtest1 = R.drawable.passtest1;
            ivPic.setBackgroundResource(passtest1);

        } else if (currentPosition == 1) {
            int passtest1 = R.drawable.passtest2;
            ivPic.setBackgroundResource(passtest1);
//            GlideUtil.display(PassATestActivity.this, R.drawable.passtest2, ivPic);
        } else if (currentPosition == 2) {
            int passtest1 = R.drawable.passtest3;
            ivPic.setBackgroundResource(passtest1);
//            GlideUtil.display(PassATestActivity.this, R.drawable.passtest3, ivPic);
        } else {
            int passtest1 = R.drawable.passtest4;
            ivPic.setBackgroundResource(passtest1);
//            GlideUtil.display(PassATestActivity.this, R.drawable.passtest4, ivPic);
        }

        if (!isIntoJuziFenjie) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (currentPosition == 3) {
                        EventBusUtil.post(new CustomsClearanceSuccessBean());//通关成功
                    }
                    finish();
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PreferencesHelper helper = new PreferencesHelper(PassATestActivity.this);
                    helper.saveInt("sp", "juzifenjieJinbi", 0);//金币

                    MyApplication application = (MyApplication) getApplication();
                    JuzifenjieGuoGuan juzifenjieGuoGuan = application.juzifenjieGuoGuan;

                    juzifenjieGuoGuan.currentPosition = 0;
                    juzifenjieGuoGuan.one.clear();
                    juzifenjieGuoGuan.two.clear();
                    juzifenjieGuoGuan.three.clear();

                    Intent intent = new Intent(PassATestActivity.this, JuZiFeiJieXunLianActivityFourClick.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);


        }
    }

    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param imageName
     * @return
     */
    public int getResource(String imageName) {
        Context ctx = getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }
}

package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;

import bean.DongciBean;
import bean.DongciGuoGuan;
import bean.JuZiChengZu;
import bean.JuZiFenJieBean;
import bean.MingciBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import dongci.DongciTestOneActivity;
import juzi.JuZiChengZuCiShiLianActivity;
import juzi.JuZiFeiJieCiShiActivityFourClick;
import mingci.MingciIdeaOneActivity;
import mingci.MingciTestOneActivity;
import mingci.MingciTestOneExperienceActivity;

/**
 * Created by chenlipeng on 2018/12/2 0002
 * describe:  过渡页
 */
public class LetsTestActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private int TIME = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                TIME--;
                if (TIME != 0)
                    tvCount.setText(TIME + "");
                if (TIME > 0) {
                    Message message = handler.obtainMessage(1);
                    handler.sendMessageDelayed(message, 1000);
                } else {
                    String type = getIntent().getStringExtra("type");
//
                    if (type.equals("mingciyiyi")) {

                        Intent intent = new Intent(LetsTestActivity.this, MingciIdeaOneActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (type.equals("mingci_yiyan")) {
//                        Intent intent = new Intent(LetsTestActivity.this, MingciTestActivity.class);
//                        startActivity(intent);
                        MingciBean model = (MingciBean) getIntent().getSerializableExtra("model");

                        Intent intent = new Intent(LetsTestActivity.this, MingciTestOneExperienceActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("model", model);
                        startActivity(intent);
                        finish();
                    } else if (type.equals("mingci")) {
//                        Intent intent = new Intent(LetsTestActivity.this, MingciTestActivity.class);
//                        startActivity(intent);
                        MingciBean model = (MingciBean) getIntent().getSerializableExtra("model");

                        Intent intent = new Intent(LetsTestActivity.this, MingciTestOneActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("model", model);
                        startActivity(intent);
                        finish();
                    } else if (type.equals("dongci")) {
//                        Intent intent = new Intent(LetsTestActivity.this, DongciTestActivity.class);
//                        startActivity(intent);
                        Intent intent = new Intent(LetsTestActivity.this, DongciTestOneActivity.class);
                        DongciBean model = (DongciBean) getIntent().getSerializableExtra("model");
//                        intent.putExtra("position", 9);
                        intent.putExtra("position", 0);
                        intent.putExtra("model", model);

                        startActivity(intent);
                        finish();
                    } else if (type.equals("juzi")) {
//                        Intent intent = new Intent(LetsTestActivity.this, JuZiExerciseActivityFourClick.class);
//                        startActivity(intent);
                        Intent intent = new Intent(LetsTestActivity.this, JuZiChengZuCiShiLianActivity.class);
                        JuZiChengZu model = (JuZiChengZu) getIntent().getSerializableExtra("model");
//                        intent.putExtra("position", 9);
                        intent.putExtra("position", 0);
                        intent.putExtra("model", model);

                        startActivity(intent);
                    } else if (type.equals("juzifenjie")) {
//                        Intent intent = new Intent(LetsTestActivity.this, JuZiExerciseActivityFourClick.class);
//                        startActivity(intent);
                        Intent intent = new Intent(LetsTestActivity.this, JuZiFeiJieCiShiActivityFourClick.class);
                        JuZiFenJieBean juzibean = (JuZiFenJieBean) getIntent().getSerializableExtra("juzibean");
//                        intent.putExtra("position", 9);
                        intent.putExtra("position", 0);
                        intent.putExtra("juzibean", juzibean);

                        startActivity(intent);
                    }
                    finish();
                }
            }
        }
    };
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
        setContentView(R.layout.activity_lets_test);
        ButterKnife.bind(this);
        playLocalVoice("让我们测试一下吧.MP3");

        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

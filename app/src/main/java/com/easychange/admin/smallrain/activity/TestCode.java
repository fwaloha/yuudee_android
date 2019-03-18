package com.easychange.admin.smallrain.activity;

import com.easychange.admin.smallrain.R;

public class TestCode {

    /*
    这个方法是。从后台切换回来的时候。执行这个方法
     */
//    private void playLocalVoiceOnLineOnScreenChange(String videoName, int i) {
//        isVoiceStarting = true;
//        if (TextUtils.isEmpty(videoName)) {
//            return;
//        }
//        //1 初始化mediaplayer
//        mediaPlayer = new MediaPlayer();
//        //2 设置到播放的资源位置 path 可以是网络 路径 也可以是本地路径
//        try {
//            if (TextUtils.isEmpty(videoName)) {
//                return;
//            }
//
//            mediaPlayer.setDataSource(videoName);
//            //3 准备播放
//            mediaPlayer.prepareAsync();
//            //3.1 设置一个准备完成的监听
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    if (isExitCurrentActivity) {
//                        isVoiceStarting = false;
//                        return;
//                    }
//                    // 4 开始播放
//                    mediaPlayer.start();
//                }
//            });
//
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//
//                    if (voiceListData.size() != 0) {
//                        voiceListData.remove(0);
//                    }
//                    if (isExitCurrentActivity) {
//                        isVoiceStarting = false;
//                        return;
//                    }
//
//                    if (voiceListData.size() == 0) {
//                        isVoiceStarting = false;
//                        return;
//                    }
//
//                    if (i == 1) {
//                        playLocalVoiceOnLineOnScreenChange(voiceListData.get(0), 0);
//                    }
//
//                    if (i == 2) {
//                        playLocalVoiceOnLineOnScreenChange(voiceListData.get(0), i - 1);
//                    }
//
//                    if (i == 3) {
//                        playLocalVoiceOnLineOnScreenChange(voiceListData.get(0), i - 1);
//                    }
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    ________________________________________________________________________________________
//    private Boolean sdfsdf = true;
//
//    private void setData() {
//        if (sdfsdf) {
//            sdfsdf = false;
//            module = "1";
//            currentChoosePosition = 1;
//        } else {
//            int i = Integer.parseInt(module);
//            module = (++i) + "";
//        }
//
//        if (module.equals("1")) {
//            iv_dongci.setClickable(false);
//            iv_juzi.setClickable(false);
//            iv_dongci.setImageResource(R.drawable.dongci_dark);
//            iv_juzi.setImageResource(R.drawable.juzi_dark);
//
//            if (null != cuttentTransYAnim) {
//                cuttentTransYAnim.end();
//            }
//            startFlutterAnimator(iv_mingci, 1);
//            bigAnima(iv_mingci);
//            currentChoosePosition = 0;
//
//        } else if (module.equals("2")) {
////                                首页展示名词气球缩小停止晃动，动词气球去灰，并且有晃动效果
//            iv_dongci.setClickable(true);
//            iv_dongci.setImageResource(R.drawable.dongci_yellow);
//
//            iv_juzi.setClickable(false);
//            iv_juzi.setImageResource(R.drawable.juzi_dark);
//
//            if (currentChoosePosition != -1) {
//                if (currentChoosePosition == 0) {
//                    smallAnima(iv_mingci);
//                } else if (currentChoosePosition == 1) {
//                    smallAnima(iv_dongci);
//                } else {
//                    smallAnima(iv_juzi);
//                }
//            }
//
//            if (null != cuttentTransYAnim) {
//                cuttentTransYAnim.end();
//            }
//            startFlutterAnimator(iv_dongci, 2);
//            bigAnima(iv_dongci);
//            currentChoosePosition = 1;
//
//        } else if (module.equals("3")) {
//            iv_juzi.setClickable(true);
//            iv_juzi.setImageResource(R.drawable.juzi_yellow);
//            iv_dongci.setClickable(true);
//            iv_dongci.setImageResource(R.drawable.dongci_yellow);
//
//            if (currentChoosePosition != -1) {
//                if (currentChoosePosition == 0) {
//                    smallAnima(iv_mingci);
//                } else if (currentChoosePosition == 1) {
//                    smallAnima(iv_dongci);
//                } else {
//                    smallAnima(iv_juzi);
//                }
//            }
//
//            if (null != cuttentTransYAnim) {
//                cuttentTransYAnim.end();
//            }
//            startFlutterAnimator(iv_juzi, 3);
//            bigAnima(iv_juzi);
//            currentChoosePosition = 2;
//        }
//    }
}

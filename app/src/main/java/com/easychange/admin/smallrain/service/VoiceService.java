package com.easychange.admin.smallrain.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class VoiceService extends Service {
    private MyBinder mBinder = new MyBinder();

    //初始化MediaPlayer
    public MediaPlayer player = new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {

    }
}

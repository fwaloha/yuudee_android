package com.easychange.admin.smallrain.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenlipeng on 2018/11/2 0002.
 * describe :
 */

public class GetDrawable {

    public static Drawable loadImage(Activity context, String url) {
        final Drawable[] drawable = new Drawable[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(context)
                            .load(url)
                            .downloadOnly(500, 500)
                            .get();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                    drawable[0] = new BitmapDrawable(bitmap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return drawable[0];
    }


}

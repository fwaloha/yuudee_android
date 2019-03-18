package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.qlzx.mylibrary.util.ToastUtil;

import mingci.MingciIdeaOneActivity;

public class tempCode {
//       if (module.equals("1") && scene.equals("3")) {
//        String finalGroupId11 = groupId;
//
//        iv_mingci.setOnTouchListener(null);
//        iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                ImageView imageView = ((ImageView) view);
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//
//                switch (motionEvent.getAction()) {
//
//                    case MotionEvent.ACTION_UP:
//                        int x = (int) motionEvent.getX();
//                        int y = (int) motionEvent.getY();
//                        if (x > bitmap.getWidth()) {
//                            x = bitmap.getWidth() - 1;
//                        }
//                        if (y > bitmap.getHeight()) {
//                            y = bitmap.getHeight() - 2;
//                        }
//                        if (x < 0) {
//                            x = 1;
//                        }
//                        if (y <= 0) {
//                            y = 1;
//                        }
//                        int pixel = bitmap.getPixel(x, y);
//                        int redValue = Color.red(pixel);
//                        Log.e("redValue", "redValue" + redValue + "");
//                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {
//
//                        } else {
//                            if (isCanlIntoNextActivity())
//                                return true;
//
//                            Intent intent = new Intent(BalloonActivity.this, MingciIdeaOneActivity.class);
//                            intent.putExtra("groupId", finalGroupId11 + "");
//                            intent.putExtra("position", length);
//                            if (isNetConnect) {
//                                startActivity(intent);
//                            } else {
//                                ToastUtil.showToast(BalloonActivity.this, "当前网络已断开");
//                            }
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
//
//    } else if (module.equals("1")) {
//        String finalGroupId = groupId;
////
//        iv_mingci.setOnTouchListener(null);
//        iv_mingci.setOnTouchListener(new View.OnTouchListener() {//主要是防止点击图片透明的地方，不执行点击事件
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                ImageView imageView = ((ImageView) view);
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//
//                switch (motionEvent.getAction()) {
//
//                    case MotionEvent.ACTION_UP:
//                        int x = (int) motionEvent.getX();
//                        int y = (int) motionEvent.getY();
//                        if (x > bitmap.getWidth()) {
//                            x = bitmap.getWidth() - 1;
//                        }
//                        if (y > bitmap.getHeight()) {
//                            y = bitmap.getHeight() - 2;
//                        }
//                        if (x < 0) {
//                            x = 1;
//                        }
//                        if (y <= 0) {
//                            y = 1;
//                        }
//                        int pixel = bitmap.getPixel(x, y);
//                        int redValue = Color.red(pixel);
//                        Log.e("redValue", "redValue" + redValue + "");
//                        if (redValue == 0 || redValue == 109 || redValue == 108 || redValue == 107) {
//
//                        } else {
//                            currentNounScene = scene;
//                            currentNounLength = length;
//                            currentNounGroupId = finalGroupId + "";
//
//                            getNoun();
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
//    }

}

package com.easychange.admin.smallrain.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;

public class AnimationHelper {

    private static AnimatorSet paintRotateSet;

    /*刚进入页面时课件的缩放动画*/
    public static void startScaleAnimation(Context context, View view) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_scale_pic);
        view.startAnimation(animation);
    }

    /*刚进入页面时的笔晃动动画*/
    public static void startRotateAnimation(View view) {
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
    }

    /*笔消失动画*/
    public static void startPaintGoneAnimation(Context context, View view) {
        //        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_picgone);
        //        view.startAnimation(animation);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(500);
        animator.start();
    }

    /*笔飞到图片上的动画*/
    public static void startPaintMoveAnimation(final View view, final AnimationEndInterface animationEndInterface) {
        int screenWidth = MyUtils.getScreenWidth(MyApplication.getGloableContext());
        int screenHeight = MyUtils.getScreenHeight(MyApplication.getGloableContext());
        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 130) - view.getHeight();
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, -45f);
        ObjectAnimator obx = ObjectAnimator.ofFloat(view, "translationX", screenWidth / 3f);
        ObjectAnimator oby = ObjectAnimator.ofFloat(view, "translationY", -y);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, obx, oby);
        set.setDuration(1500);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationEndInterface.onAnimationEnd(view);
            }
        });
    }

    /*模拟笔画画的动画*/
    public static void startPaintDrawAnimation(View view) {
        int screenWidth = MyUtils.getScreenWidth(MyApplication.getGloableContext());
        int screenHeight = MyUtils.getScreenHeight(MyApplication.getGloableContext());
        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 130) - view.getHeight();
        ObjectAnimator oby = ObjectAnimator.ofFloat(view, "translationY", -y + 20, -y + 80, -y + 140, -y + 180);
        ObjectAnimator obx = ObjectAnimator.ofFloat(view, "translationX", screenWidth / 3f + 60, screenWidth / 3f);
        obx.setRepeatCount(1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(oby, obx);
        set.setDuration(1200);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
    }

    /*文字飞的动画*/
    public static void startTextMoveAnimation(View view, int x, int y) {
        ObjectAnimator obx = ObjectAnimator.ofFloat(view, "translationX", -x);
        obx.setDuration(1000);
        ObjectAnimator oby = ObjectAnimator.ofFloat(view, "translationY", -y);
        oby.setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.42857f);
        scaleY.setDuration(1000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.42857f);
        scaleX.setDuration(1000);
        obx.start();
        oby.start();
        scaleX.start();
        scaleY.start();
    }

    /*文字合并的动画*/
    public static void startTextMergeAnimation(View view, int width) {
        ObjectAnimator obx = ObjectAnimator.ofFloat(view, "translationX", width);
        obx.setDuration(1000);
        obx.start();
    }

    public interface AnimationEndInterface {
        void onAnimationEnd(View view);
    }

}

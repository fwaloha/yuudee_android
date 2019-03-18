package com.easychange.admin.smallrain.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.MyUtils;

import java.text.DecimalFormat;


/**
 * 自定义View: 真实现圆形进度条
 *
 * @author 张晓飞
 */
public class CircleBar extends View {

    private float progress = 0;// 当前进度
    private float max = 100; // 最大进度
    int color = getResources().getColor(R.color.roundColor);
    private int roundColor = color; // 圆环的颜色
    private int roundProgressColor = Color.RED;// 圆环进度的颜色
    private float roundWidth = MyUtils.dip2px(MyApplication.getGloableContext(),20); // 圆环的宽度
    private int textSize = 16;// 文字的大小
    private int textColor = Color.parseColor("#24CFFD"); // 文字的颜色
    private Paint paint;

    private float viewWidth;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {//在分线程执行的
        //对进度限制
        if (progress < 0) {
            progress = 0;
        } else if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        //更新View
        postInvalidate();
    }

    public float getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public CircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆环
        float center = viewWidth / 2;
        float radius = center - roundWidth / 2;//半径
        paint.setColor(roundColor);//颜色
        paint.setStrokeWidth(roundWidth);//画笔的宽度
        paint.setStyle(Style.STROKE);//空心的
        canvas.drawCircle(center, center, radius, paint);
        //绘制圆弧
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(roundWidth / 2, roundWidth / 2, viewWidth - roundWidth / 2, viewWidth - roundWidth / 2);
        canvas.drawArc(oval, 270, (progress / 100) * 360, false, paint);
        //绘制文本
        //更新画笔
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(MyUtils.sp2px(MyApplication.getGloableContext(),textSize));//19sp
        //文本      "schedule":1.00,
//        String text = progress / 100 + "%";

        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format((float) progress);
        String text = format + "%";

        //得到文本的宽高
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int textWidth = bounds.width();
        int textHeight = bounds.height();
        //文本显示的左下角的坐标
        float x = center - textWidth / 2;
        float y = center + textHeight / 2;
        //绘制
        canvas.drawText(text, x, y, paint);
    }

}

package com.easychange.admin.smallrain.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.MyUtils;


/**
 * 自定义View: 真实现圆形进度条
 *
 * @author 张晓飞
 */
public class CircleBarTime extends View {

    private float progress = 0;// 当前进度
    private float max = 100; // 最大进度
    int color = getResources().getColor(R.color.roundColor_time);
    int color_bg = Color.parseColor("#C5AE8A");
    private int roundColor = color_bg; // 圆环的颜色
    private int roundProgressColor = color;// 圆环进度的颜色
    private float roundWidth = MyUtils.dip2px(MyApplication.getGloableContext(),10);; // 圆环的宽度
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

//    public int getTextSize() {
//        return textSize;
//    }
//
//    public void setTextSize(int textSize) {
//        this.textSize = textSize;
//    }
//
//    public int getTextColor() {
//        return textColor;
//    }
//
//    public void setTextColor(int textColor) {
//        this.textColor = textColor;
//    }

    public CircleBarTime(Context context, AttributeSet attrs) {
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

//        oval :指定圆弧的外轮廓矩形区域。
//        startAngle: 圆弧起始角度，单位为度。
//        sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
//        useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
        canvas.drawArc(oval, 270, (progress / 100) * 360, false, paint);
        //绘制文本
        //更新画笔
//        paint.setStrokeWidth(0);
//        paint.setColor(textColor);
//        paint.setTextSize(textSize);//19sp
//        //文本
//        String text = progress / 100 + "%";
//        //得到文本的宽高
//        Rect bounds = new Rect();
//        paint.getTextBounds(text, 0, text.length(), bounds);
//        int textWidth = bounds.width();
//        int textHeight = bounds.height();
        //文本显示的左下角的坐标
//        float x = center - textWidth / 2;
//        float y = center + textHeight / 2;
        //绘制
//        canvas.drawText(text, x, y, paint);
    }

}

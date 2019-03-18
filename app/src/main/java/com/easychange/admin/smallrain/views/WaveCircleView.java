package com.easychange.admin.smallrain.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.easychange.admin.smallrain.R;

import java.util.ArrayList;
import java.util.List;

public class WaveCircleView extends View {
    private static final int DEF_VIEW_SIZE = 60;
    private static final int UNIT_TIME = 800;

    private Point circelCenterPoint;
    private int maxRadius;
    private ArrayList<Integer> waveColors;

    private List<Paint> paints;
    private List<CircleRadiuEntity> circleRadiusList;

    public WaveCircleView(Context context) {
        this(context, null);
    }

    public WaveCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTools();
    }

    private void initTools() {
        paints = new ArrayList<>();
        circleRadiusList = new ArrayList<>();
        waveColors = new ArrayList<>();
        /*#04CEFE*/
//        waveColors.add(ContextCompat.getColor(getContext(), android.R.color.white));
//        waveColors.add(ContextCompat.getColor(getContext(), R.color.white_60));
//        waveColors.add(ContextCompat.getColor(getContext(), R.color.white_20));
        waveColors.add(ContextCompat.getColor(getContext(), R.color.blue_50));
        waveColors.add(ContextCompat.getColor(getContext(), R.color.blue_50));
        waveColors.add(ContextCompat.getColor(getContext(), R.color.blue_50));

        for (int paintColor : waveColors) {
            Paint tempPaint = new Paint();
            tempPaint.setColor(paintColor);
            tempPaint.setAntiAlias(true);
            tempPaint.setStrokeJoin(Paint.Join.ROUND);
            tempPaint.setStrokeCap(Paint.Cap.ROUND);
            tempPaint.setStyle(Paint.Style.STROKE);
            tempPaint.setStrokeWidth(13);
            paints.add(tempPaint);

            CircleRadiuEntity c = new CircleRadiuEntity();
            circleRadiusList.add(c);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circelCenterPoint = new Point(w / 2, h / 2);
        maxRadius = (int)(Math.min(w, h) / 2.5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthMeaure;
        int heightMeaure;
//        1.对于EXACTLY模式.确定就是说控件已经确定大小了.也就是控件在布局这样写.
//        ndroid:layout_width="100dp"//或者
//        android:layout_width="match_parent"
//        对于确定控件大小,就不需要计算了.

//        2.对于AT_MOST模式.不确定大小.就需要自己计算了.对于不同的自定义view.
//                看实际需要计算.就现在自定义Textview的举例.就是文字占的控件多大.就给控件设置宽高为多大.

        if (widthMeasureMode == MeasureSpec.AT_MOST || widthMeasureMode == MeasureSpec.UNSPECIFIED) {
            widthMeaure = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_VIEW_SIZE, getResources().getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeaure, MeasureSpec.EXACTLY);
        }

        if (heightMeasureMode == MeasureSpec.AT_MOST || heightMeasureMode == MeasureSpec.UNSPECIFIED) {
            heightMeaure = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_VIEW_SIZE, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeaure, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paints.size(); i++) {
            Paint currentCirclePaint = paints.get(i);
            int currentCircleRadius = circleRadiusList.get(i).getRadius();
            canvas.drawCircle(circelCenterPoint.x, circelCenterPoint.y, currentCircleRadius, currentCirclePaint);
        }
    }

    public void startWave() {
        for (int i = 0; i < waveColors.size(); i++) {
            ValueAnimator valueAnimatorC = ValueAnimator.ofFloat(0F, 1F);
            final int finalI = i;
            valueAnimatorC.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float aFloat = Float.valueOf(animation.getAnimatedValue().toString());
                    circleRadiusList.get(finalI).setRadius((int) (maxRadius * aFloat));
                    invalidate();
                }
            });
            valueAnimatorC.setRepeatCount(ValueAnimator.INFINITE);
//            valueAnimatorC.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimatorC.setDuration(UNIT_TIME * waveColors.size());
            valueAnimatorC.setStartDelay(i * UNIT_TIME);
            valueAnimatorC.start();
        }
    }

    class CircleRadiuEntity {
        int radius;

        int getRadius() {
            return radius;
        }

        void setRadius(int radius) {
            this.radius = radius;
        }
    }
}

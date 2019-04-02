package com.easychange.admin.smallrain.views;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.easychange.admin.smallrain.R;

/**
 * admin  2018/8/10 wan
 */
public class DrawImgView extends View {
    private Context context;
    private int paintwidth;
    private int paintcolor;
    private int width;
    private Path path;
    private Bitmap bitmap;
    private int height;
    private Bitmap newbitmap;
    private int[] bmpixels;
    private Canvas canvas;
    private Paint paint;
    private int hascanvasimg;
    private int paintimg;
    private OverBack overBack;


    public void setOverBack(OverBack overBack) {
        this.overBack = overBack;
    }

    public DrawImgView(Context context) {
        super(context);
        this.context = context;
        initAttr(null, 0);
    }

    public DrawImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttr(attrs, 0);
    }

    public DrawImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttr(attrs, defStyleAttr);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        //从attrs文件中取出各个属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.drawimg, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.drawimg_paintwidth:    //画笔宽度
                    paintwidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.drawimg_paintcolor:    //画笔颜色
                    paintcolor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.drawimg_canvasimg:     //画板图片
                    hascanvasimg = a.getResourceId(attr, -1);
//                    hascanvasimg =-1
                    ;
                    break;
                case R.styleable.drawimg_paintimg:     //
                    paintimg = a.getResourceId(attr, -1);
                    break;
            }
        }
        //设置默认画笔宽度
        if (paintwidth == -1) {
            paintwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
        }
        //取出bitmap
        if (hascanvasimg != -1) {
            bitmap = BitmapFactory.decodeResource(getResources(), hascanvasimg);
        }
        if (paintimg != -1) {
//            Bitmap paintbitmap = BitmapFactory.decodeResource(getResources(), paintimg);
        }

        //onmeasure可能走多次，ondraw创建对象更不好 所以把画笔路径new在这里
        path = new Path();
    }

    public void setcanvasImg(Bitmap bitmap) {
        releaseBitmap(this.bitmap);
        this.bitmap = bitmap;
//        必须先调用 requestLayout() 方法再调用 invalidate （）方法
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量模式_宽
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        //测量模式_高
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        //宽度
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        //高度
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);

        //设置view宽度
        //如果布局中给出了准确的宽度，直接使用宽度，否则设置图片宽度为view宽度
        if (widthmode == MeasureSpec.EXACTLY) {
            width = widthsize;
        } else {
            if (hascanvasimg != -1) {
                //如果设置了图片,使用图片宽
                width = bitmap.getWidth();
            } else {
                //没有设置图片并且也没给准确的view宽高 设置一个宽默认值
                width = 500;
            }
        }
        //设置view高度同上
        if (heightmode == MeasureSpec.EXACTLY) {
            height = heightsize;
        } else {
            if (hascanvasimg != -1) {
                height = bitmap.getHeight();
            } else {
                height = 500;
            }
        }
        //重新设置view的宽高
        setMeasuredDimension(width, height);

        //设置画布以及画笔
        newpaint();
    }

    /**
     * onmeasure常见方法
     * 1) getchildcount()：获取子view的数量；
     * 2) getchildat(i)：获取第i个子控件；
     * 3) subview.getlayoutparams().width/height：设置或获取子控件的宽或高；
     * 4) measurechild(child, widthmeasurespec, heightmeasurespec)：测量子view的宽高；
     * 5) child.getmeasuredheight/width()：执行完measurechild()方法后就可以通过这种方式获取子view的宽高值；
     * 6) getpaddingleft/right/top/bottom()：获取控件的四周内边距；
     * 7) setmeasureddimension(width, height)：重新设置控件的宽高。如果写了这句代码，就需要删除“super. onmeasure(widthmeasurespec, heightmeasurespec);”这行代码。
     */

//    java.lang.OutOfMemoryError: Failed to allocate a 2808012 byte allocation with 1325328 free bytes and 1294KB until OOM
//    at com.easychange.admin.smallrain.views.DrawImgView.newpaint(DrawImgView.java:172)
//    at com.easychange.admin.smallrain.views.DrawImgView.onMeasure(DrawImgView.java:155)
    private void newpaint() {
        //根据参数创建一个新的bitmap
        releaseBitmap(newbitmap);
        newbitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //保存bitmap中所有像素点的数组
        bmpixels = new int[newbitmap.getWidth() * newbitmap.getHeight()];
        //new带参的canvas,其中的bitmap参数 必须通过createbitmap得到；
        //否则会报错：illegalstateexception : immutable bitmap passed to canvas constructor
        canvas = new Canvas(newbitmap);
        if (hascanvasimg == -1) {
            //如果没有设置图片，则默认用灰色覆盖
            canvas.drawColor(Color.GRAY);
        } else {
            //把设置的图片缩放到view大小
            bitmap = zoombitmap(this.bitmap, width, height);
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        // 准备绘制刮卡线条的画笔
        paint = new Paint();
        paint.setColor(paintcolor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintwidth);
        //设置是否使用抗锯齿功能，抗锯齿功能会消耗较大资源，绘制图形的速度会减慢
        paint.setAntiAlias(true);
        //设置是否使用图像抖动处理，会使图像颜色更加平滑饱满，更加清晰
        paint.setDither(true);
        //当设置画笔样式为stroke或fill_or_stroke时，设置笔刷的图形样式
        paint.setStrokeCap(Paint.Cap.ROUND);
        //设置绘制时各图形的结合方式
        paint.setStrokeJoin(Paint.Join.ROUND);
        //设置图形重叠时的处理方式
        /**
         * src_in：取两层绘制交集。显示上层
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    //这个ondraw方法只有一句代码，意思是在手指移动的同时把画板图片绘制出来
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float[] mPoint;
    private float[] mTan;

    @Override
    protected void onDraw(Canvas canvas) {
        if (newbitmap !=null)
        canvas.drawBitmap(newbitmap, 0, 0, null);

        if (mPath == null) {
            return;
        }


        //        canvas.rotate(mDdegrees+=2, getWidth()/2, getHeight()/2);
        //        canvas.drawPath(mPath, paint);
        //
        //        float degress = (float) Math.toDegrees(Math.atan2(mTan[1], mTan[0]));
        //        Matrix matrix = new Matrix();
        //        matrix.postRotate(degress, paintbitmap.getWidth() / 2, paintbitmap.getHeight() / 2);
        //        matrix.postTranslate(mPoint[0] - paintbitmap.getWidth() / 2, mPoint[1] - paintbitmap.getHeight() / 2);
        //        canvas.drawBitmap(paintbitmap, matrix, null);

        super.onDraw(canvas);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator()); //插值器
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(distance, mPoint, mTan);
                invalidate();
            }
        });
        animator.start();
    }


    //将指定图片缩放到指定宽高，返回新的图片bitmap对象
    public static Bitmap zoombitmap(Bitmap bm, int newwidth, int newheight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scalewidth = ((float) newwidth) / width;
        float scaleheight = ((float) newheight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scalewidth, scaleheight);
        // 得到新的图片

        Bitmap r = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return r;
    }

    /*项目需求是不能手指触摸涂鸦，所以我就把这里面的代码注释了*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currx = (int) event.getX();
        int curry = (int) event.getY();
        Log.e("======>>", "currx " + currx + " curry " + curry);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下时，设置线条的起始点准备绘制
                //                path.moveTo(currx, curry);
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动时，绘制路径
                //                path.lineTo(currx, curry);
                break;
            case MotionEvent.ACTION_UP:
        }
        // 绘制线条，请求重绘整个控件
        //        canvas.drawPath(path, paint);
        //请求view树进行重绘，即draw()方法，如果视图的大小发生了变化，还会调用layout()方法。
        //        invalidate();
        return true;
    }
    private int currentXDistance=8;
    private int currentYDistance=40;

    public void movePath() {
        path.moveTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()));

        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
//                    可以看出lineTo是直接 起点（100,100）与 终点（100,200） 2点相连    x横y竖
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentYDistance*1, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        overBack.second();
                    }
                }
            }, i * 20);
        }

        //        path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()),
        //                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics()));
        //       200 -> 0         20 -> 180
        //        path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
        //                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, context.getResources().getDisplayMetrics()));
        //
        //        path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()),
        //                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()));
        //
        //        path.close();

        canvas.drawPath(path, paint);
        //        invalidate();

    }

    public void move2Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentYDistance*2, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        overBack.three();
                    }
                }
            }, i *10);
        }
    }

    public void move3Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentYDistance*3, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
                        move4Path();
//                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }
    public void move4Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentYDistance*4, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
                        move5Path();
//                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }

    public void move5Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentYDistance*5, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
//                        move6Path();
                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }

    public void move6Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * 14, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
                        move7Path();
//                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }

    public void move7Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * 16, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
                        move8Path();
//                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }

    public void move8Path() {
        for (int i = 1; i < 41; i++) {
            final int finalI = i;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    path.lineTo(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * currentXDistance, context.getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, finalI * 18, context.getResources().getDisplayMetrics()));
                    canvas.drawPath(path, paint);
                    invalidate();
                    if (finalI == 40) {
                        //把设置的图片缩放到view大小
                        overBack.onEnd();
                    }
                }
            }, i * 10);
        }
    }
    public void setPath(Path path) {
        mPath = path;
        mPathMeasure = new PathMeasure(path, false);
        mPoint = new float[2];
        mTan = new float[2];
    }

    public interface OverBack {
        void second();

        void three();

        void onEnd();
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public void recycle() {
        releaseBitmap(bitmap);
        releaseBitmap(newbitmap);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseBitmap(bitmap);
        releaseBitmap(newbitmap);
    }
}

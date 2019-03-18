package com.easychange.admin.smallrain.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easychange.admin.smallrain.R;

public class IndicatorView extends LinearLayout {
    private final Context context;
    private int count = 10;
    private int selectedPosition;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selctedPosition) {
        this.selectedPosition = selctedPosition;
        removeAllViews();
        drawPoint();
    }

    public IndicatorView(Context context) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        drawPoint();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        drawPoint();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        drawPoint();
    }

    private void drawPoint() {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 10;
            layoutParams.gravity=Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(layoutParams);
            if (i == selectedPosition) {
                layoutParams.gravity=Gravity.TOP;
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.progress_logo);
            } else if (i < selectedPosition) {
                imageView.setImageResource(R.drawable.point_blue);
            } else {
                imageView.setImageResource(R.drawable.point_dark);
            }
            addView(imageView);
        }
    }

}
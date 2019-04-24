package com.easychange.admin.smallrain.utils;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.easychange.admin.smallrain.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by chenlipeng on 2018/10/18 0018.
 * describe :
 */

public class LineChartManager {
    private LineChart lineChart;
    private YAxis leftAxis; //左边Y轴
    private YAxis rightAxis; //右边Y轴
    private XAxis xAxis; //X轴

    public LineChartManager(LineChart mLineChart) {
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
        initLineChart();
    }

    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        // 是否可以缩放
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        //显示边界
        lineChart.setDrawBorders(false);
        //设置动画效果
        lineChart.animateY(1000, Easing.EasingOption.Linear);
        lineChart.animateX(1000, Easing.EasingOption.Linear);

        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTextSize(11f);
//        //显示位置
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(true);
        legend.setEnabled(false);

        //XY轴的设置 //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false); //
        leftAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);//不绘制x轴网线 //
        leftAxis.setDrawGridLines(false);//不绘制Y周网线 }

        xAxis.setAxisLineWidth(2);//设置轴线宽度
        leftAxis.setAxisLineWidth(2);//设置轴线宽度

        xAxis.setDrawLabels(true);
        leftAxis.setDrawLabels(true);

        xAxis.setAxisLineColor(Color.BLACK); //设置轴线的轴的颜色。
        leftAxis.setAxisLineColor(Color.BLACK); //设置轴线的轴的颜色。
        xAxis.setTextSize(12); //设置轴标签的文字大小。。
        leftAxis.setTextSize(12); //设置轴标签的文字大小。。


    }

    /**
     * 初始化曲线 每一个LineDataSet代表一条线
     *
     * @param lineDataSet
     * @param color
     * @param mode        折线图是否填充
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, boolean mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
//        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setValueTextSize(9f);

        //设置折线图填充
      //  lineDataSet.setDrawFilled(mode);//填充曲线下方的区域\
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    //    lineDataSet.setDrawFilled(true);

    }

    /**
     * 展示折线图(一条)
     *
     * @param mContext
     * @param xAxisValues
     * @param yAxisValues
     * @param label
     * @param color
     */
    public void showLineChart(Context mContext, List<Float> xAxisValues, List<Float> yAxisValues, String label, int color) {
        ArrayList<Integer> colors = new ArrayList<Integer>();

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new Entry(xAxisValues.get(i), yAxisValues.get(i)));

            if (yAxisValues.get(i) == 0.0) {
                colors.add(Color.TRANSPARENT);
            }else {
                colors.add(color);
            }
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        //线模式为圆滑曲线（默认折线）
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

//        lineDataSet.setCircleHoleColor(color);
        //设置圆心半径
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        initLineDataSet(lineDataSet, color, false);

//        setValueTextColor(int color) : 设置 DataSets 数据对象包含的数据的值文本的颜色。
//        setValueTextSize(float size) : 设置 DataSets 数据对象包含的数据的值文本的大小（单位是dp）。
        lineDataSet.setValueTextColors(colors);
        lineDataSet.setValueTextSize(11);

        lineDataSet.setCircleColors(colors);
//        if(Collections.max(yAxisValues) == 0){
            lineDataSet.setColor(Color.parseColor("#00ffffff"));//线条颜色
//        }
//        lineDataSet.setColor(color);//线条颜色

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size());
        lineChart.setData(data);
    }

    /**
     * 展示折线图(一条)
     *
     * @param mContext
     * @param xAxisValues
     * @param yAxisValues
     * @param label
     * @param color
     */
    public void showLineChart(List<Float> xAxisValues, List<Float> yAxisValues, String label, int color) {
        ArrayList<Integer> colors = new ArrayList<Integer>();

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new Entry(xAxisValues.get(i), yAxisValues.get(i)));

            if (yAxisValues.get(i) == 0.0) {
                colors.add(Color.TRANSPARENT);
            }else {
                colors.add(color);
            }
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        //线模式为圆滑曲线（默认折线）
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

//        lineDataSet.setCircleHoleColor(color);
        //设置圆心半径
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        initLineDataSet(lineDataSet, color, false);

//        setValueTextColor(int color) : 设置 DataSets 数据对象包含的数据的值文本的颜色。
//        setValueTextSize(float size) : 设置 DataSets 数据对象包含的数据的值文本的大小（单位是dp）。
        lineDataSet.setValueTextColors(colors);
        lineDataSet.setValueTextSize(11);

        lineDataSet.setCircleColors(colors);
//        if(Collections.max(yAxisValues) == 0){
//        lineDataSet.setColor(Color.parseColor("#00ffffff"));//线条颜色
//        }
        lineDataSet.setColor(color);//线条颜色


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size());
        lineChart.setData(data);
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }
    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxisOnForce(float max, float min, int labelCount,boolean force) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, force);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, force);
        lineChart.invalidate();
    }
    public void setLineColor(String color){
        //设置曲线颜色

    }
    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value!=0?"":(int)value+"";
            }
        });
        lineChart.invalidate();
    }
    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXOverllAxis(float max, float min, int labelCount,List<Float> list) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, false);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//               try{
//                   return ((int)((float)list.get((int) value)))+"";
//               }catch (Exception e){
//                   return "";
//               }
//
//            }
//        });
        lineChart.invalidate();
//        setAxisMaximum(float max)：为这个轴设置一个自定义的最大值。如果设置,这个值不会自动根据所提供的数据计算。
//        setAxisMinimum(float min)：为这个轴设置一个自定义的最小值。如果设置,这个值不会自动根据所提供的数据计算。
    }
    public void setXAxisWeek(String time) {
        String[] weekData = new String[8];
//        xAxis.setAxisMaximum(max);
//        xAxis.setAxisMinimum(min);
//        2018-12-10
        String substringtime = time.substring(5);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        try {
            weekData[0] = ("0");
            weekData[1] = (substringtime. replaceFirst("-","月"))+"日";
            for (int i = 2; i < 8; i++) {
                Date date = sdf.parse(substringtime);

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, i - 1);
                String  data = sdf.format(c.getTime());
                weekData[i] = (data. replaceFirst("-","月"))+"日";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter();
//        indexAxisValueFormatter.setValues(weekData);
        xAxis.setTextSize(6);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return  value!=0&&value%2==0?"":weekData[(int)value] +"";
            }
        });

        xAxis.setLabelCount(8, true);
        xAxis.setCenterAxisLabels(false);//设置标签居中
        lineChart.invalidate();
    }

    public void setXAxisMonth() {
//        xAxis.setAxisMaximum(max);
//        xAxis.setAxisMinimum(min);

        String[] weekData = new String[6];
        weekData[0] = ("0");

        weekData[1] = ("第一周");
        weekData[2] = ("第二周");
        weekData[3] = ("第三周");
        weekData[4] = ("第四周");
        weekData[5] = ("第五周");

        IndexAxisValueFormatter indexAxisValueFormatter = new IndexAxisValueFormatter();
        indexAxisValueFormatter.setValues(weekData);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return weekData[(int)value] +"";
            }
        });

        xAxis.setLabelCount(6, true);
        xAxis.setCenterAxisLabels(false);//设置标签居中
        xAxis.setTextSize(10);
        lineChart.invalidate();
    }

    public class MyXFormatter implements IAxisValueFormatter {
        private String[] mValues;

        public MyXFormatter(String[] values) {
            this.mValues = values;
        }

        private static final String TAG = "MyXFormatter";

        @Override
        public String getFormattedValue(float value, AxisBase axis) { // "value" represents the position of the label on the axis (x or y)
            Log.d(TAG, "----->getFormattedValue: " + value);
            return mValues[(int) value % mValues.length];
        }
    }

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(2f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText("sdfdsg等发光点");
        description.setPosition(120, 50);

        Description description1 = new Description();
        description1.setText(str);

        lineChart.setDescription(description);
        lineChart.setDescription(description1);

        lineChart.invalidate();
    }

}
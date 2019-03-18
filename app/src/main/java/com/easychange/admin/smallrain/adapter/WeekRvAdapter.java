package com.easychange.admin.smallrain.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.utils.LineChartManager;
import com.easychange.admin.smallrain.views.ChartView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.qlzx.mylibrary.util.TimeUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.TimeList;
import bean.WeekData;

/**
 * Created by chenlipeng on 2018/10/18 0018
 * describe:  周的适配器（图表）
 */
public class WeekRvAdapter extends RecyclerViewAdapter<WeekData.DataBean.ResultListBean> {

    private Intent intent;


    public WeekRvAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_week);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, final WeekData.DataBean.ResultListBean model) {
        viewHolderHelper.getView(R.id.ll_item_week_root).setVisibility(View.VISIBLE);

        LineChart linechart1 = (LineChart) viewHolderHelper.getView(R.id.linechart1);
        LineChart linechart2 = (LineChart) viewHolderHelper.getView(R.id.linechart2);
        ChartView chartViewone = (ChartView) viewHolderHelper.getView(R.id.chartviewweekone);
        TextView tvMinute = (TextView) viewHolderHelper.getView(R.id.tv_minute);
        initChart1(linechart1, "#24CFFD", model, 1, viewHolderHelper);
        initChart2(linechart2, "#cd4744", model, 2, viewHolderHelper, tvMinute);

        // initChartViewtwo(model,viewHolderHelper);

        if (model.getTimeList().size() != 0) {
            TimeList timeList = model.getTimeList().get(0);
            TimeList timeList1 = model.getTimeList().get(model.getTimeList().size() - 1);


            if (position == 0) {
                viewHolderHelper.setText(R.id.tv_week, "最近一周");
            } else if (position == 1) {
                viewHolderHelper.setText(R.id.tv_week, "上一周（" + timeList.getTime() + "至" + timeList1.getTime() + "）");
            } else {
                viewHolderHelper.setText(R.id.tv_week, "（" + timeList.getTime() + "至" + timeList1.getTime() + "）");

            }
        } else {
            viewHolderHelper.setText(R.id.tv_week, "");
        }

    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    List<Float> yValues ;
    String[] weekData ;
    private void initChart1(LineChart lineChart1, String s, WeekData.DataBean.ResultListBean model, int position, ViewHolderHelper viewHolderHelper) {
        String time = null;
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);

        long weekFirstDay = model.getWeekFirstDay();
        time = stampToDate(weekFirstDay + "");
        Log.e("shuju", time + "");



        if (position == 1) {
            addWeekY(yValues,time,weekFirstDay,weekData);
            for (int j = 0; j < model.getAccuracyList().size(); j++) {
//            yValues.add((float) (Math.random() * 80));
                for (int i = 0; i < weekData.length; i++) {
                    if (weekData[i].equals(model.getAccuracyList().get(j).getTime())) {
                        double accuracy = model.getAccuracyList().get(j).getAccuracy();
                        yValues.set(i,(float) accuracy * 100);
                        continue;
                    }
                }
            }


        } else {
            //设置y轴的数据()
            addWeekY(yValues,time,weekFirstDay,weekData);
            for (int j = 0; j < model.getTimeList().size(); j++) {


                for (int i = 0; i < weekData.length; i++) {
                    if (weekData[i].equals(model.getTimeList().get(j).getTime())) {
                        int countTime = model.getTimeList().get(j).getCountTime();
                        DecimalFormat df = new DecimalFormat("0.00");

                        yValues.set(i,Float.parseFloat(df.format((float) countTime / 60)));
                        continue;
                    }
                }
            }
//            if (model.getTimeList().size() != 0) {
//                time = model.getTimeList().get(0).getTime();
//            }
        }




        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(s));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");

        if (yValues.size() == 0) {
            return;
        }

        if (TextUtils.isEmpty(time)) {
            return;
        }

        //创建单条折线的图表
        lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
//        lineChartManager1.setYAxisOnForce((int)((Collections.max(yValues) / 5) + 1) * 5, 0, 2, true);
        lineChartManager1.setDescription("");
        lineChartManager1.setXAxisWeek(time.split(" ")[0]);//2019-01-14 00:00:00
//        lineChartManager1.setXAxis(xValues.size(), 0, xValues.size());
        lineChartManager1.setYAxisOnForce(100, 0, 6, true);
        setLintChart((ViewGroup) viewHolderHelper.getView(R.id.ll_linear), yValues);
    }
    private void addWeekY(List<Float> yValues,String time,long weekFirstDay,String[] weekData){
        //设置y轴的数据()
        yValues = new ArrayList<>();
        weekData = new String[8];
//        xAxis.setAxisMaximum(max);
//        xAxis.setAxisMinimum(min);
//        2018-12-10
//            String substringtime = time.substring(5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            weekData[0] = ("0");
            int j = time.indexOf(" ");
            weekData[1] = time.substring(0,j);;
            long lt = new Long(weekFirstDay);

            for (int i = 2; i < 8; i++) {
                Date date = new Date(lt);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, i - 1);
                String data = sdf.format(c.getTime());
                weekData[i] = data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {
            yValues.add((float) 0);
        }
        yValues.add(0, (float) 0);
        this.yValues =yValues;
        this.weekData = weekData;
    }
    private void setLintChart(ViewGroup viewGroup, List<Float> yValues) {
        for (int i = 0; i < (viewGroup).getChildCount(); i++) {
            if (i == 0) {
                ((TextView) (viewGroup).getChildAt(i)).setText((int) (viewGroup.getId() == R.id.ll_linear ? 100 : (int) ((Collections.max(yValues) / 5) + 1) * 5) + "");
            } else if (((TextView) (viewGroup).getChildAt(i - 1)).getText().toString() != "") {
                if (viewGroup.getId() == R.id.ll_linear) {
                    ((TextView) (viewGroup).getChildAt(i)).setText(100 / 2 + "");
                } else {
                    ((TextView) (viewGroup).getChildAt(i)).setText(
                            ((int) ((Collections.max(yValues) / 5) + 1) * (5 - i)) + "");
                }
            } else {
                ((TextView) (viewGroup).getChildAt(i)).setText("");
                ((TextView) (viewGroup).getChildAt(i)).setVisibility(View.GONE);
            }
        }
    }

    private void initChart2(LineChart lineChart1, String s, WeekData.DataBean.ResultListBean model, int position, ViewHolderHelper viewHolderHelper, TextView tvMinute) {
        String time = null;
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);

        long weekFirstDay = model.getWeekFirstDay();
        time = stampToDate(weekFirstDay + "");
        Log.e("shuju", time + "");

//        List<Float> yValues;
//        if (position == 1) {
//            //设置y轴的数据()
////            yValues = new ArrayList<>();
//            for (int j = 0; j < model.getTimeList().size(); j++) {
//                double accuracy = model.getTimeList().get(j).getAccuracy();
//                yValues.add((float) accuracy * 100);
//            }
////            if (model.getTimeList().size() != 0) {
////                time = model.getTimeList().get(0).getTime();
////            }
//
//        } else {
            //设置y轴的数据()
//            yValues = new ArrayList<>();
            boolean b = false;
            for (int j = 0; j < model.getTimeList().size(); j++) {
                if (model.getTimeList().get(j).getCountTime() > 3600) {
                    b = true;
                    break;
                }
            }
            //说明大于一个小时
            if (b) {
                tvMinute.setText("学习时间 /小时");
                addWeekY(yValues,time,weekFirstDay,weekData);
                for (int j = 0; j < model.getTimeList().size(); j++) {
                    for (int i = 0; i < weekData.length; i++) {
                        if (weekData[i].equals(model.getTimeList().get(j).getTime())) {
                            yValues.set(i,TimeUtil.formatTime((float) model.getTimeList().get(j).getCountTime()));
                            continue;
                        }
                    }
                }

            } else { //否则小于
                tvMinute.setText("学习时间 /分钟");
//                for (int i = 0; i < model.getTimeList().size() && !b; i++) {
//
//                    int countTime = model.getTimeList().get(i).getCountTime();
//
//                    DecimalFormat df = new DecimalFormat("0.00");
//
//                    yValues.add(Float.parseFloat(df.format((float) countTime / 60)));
//                    b = false;
//
//                }
                addWeekY(yValues,time,weekFirstDay,weekData);
                for (int j = 0; j < model.getTimeList().size()&& !b; j++) {


                    for (int i = 0; i < weekData.length; i++) {
                        if (weekData[i].equals(model.getTimeList().get(j).getTime())) {
                            int countTime = model.getTimeList().get(j).getCountTime();

                            DecimalFormat df = new DecimalFormat("0.00");
                            yValues.set(i,Float.parseFloat(df.format((float) countTime / 60)));
                            b = false;
                            continue;
                        }
                    }
                }
            }
    //    }

//        for (int i = yValues.size(); i < 7; i++) {
//            yValues.add((float) 0);
//        }
//        yValues.add(0, (float) 0);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(s));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");

        if (yValues.size() == 0) {
            return;
        }

        if (TextUtils.isEmpty(time)) {
            return;
        }

        //创建单条折线的图表
        lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
        lineChartManager1.setDescription("");
        lineChartManager1.setXAxisWeek(time.split(" ")[0]);//2019-01-14 00:00:00
//        lineChartManager1.setXAxis(xValues.size(), 0, xValues.size());
        lineChartManager1.setYAxisOnForce(((int) (Collections.max(yValues) / 5) + 1) * 5, 0, 6, true);
        setLintChart((ViewGroup) viewHolderHelper.getView(R.id.ll_linear1), yValues);
    }


/*
    private void initChartViewone(WeekData.DataBean.ResultListBean model, ViewHolderHelper viewHolderHelper) {
        String time = null;
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);

        long weekFirstDay = model.getWeekFirstDay();
        time = stampToDate(weekFirstDay + "");
        Log.e("shuju", time + "");

        List<Float> yValues;
        if (position == 1) {
            //设置y轴的数据()
            yValues = new ArrayList<>();
            for (int j = 0; j < model.getTimeList().size(); j++) {
//            yValues.add((float) (Math.random() * 80));
                double accuracy = model.getTimeList().get(j).getAccuracy();
                yValues.add((float) accuracy * 100);
            }
//            if (model.getTimeList().size() != 0) {
//                time = model.getTimeList().get(0).getTime();
//            }

        } else {
            //设置y轴的数据()
            yValues = new ArrayList<>();
            for (int j = 0; j < model.getTimeList().size(); j++) {
//            yValues.add((float) (Math.random() * 80));
//                |countTime |int |学习时长（一秒为单位） |
                int countTime = model.getTimeList().get(j).getCountTime();
                yValues.add((float) countTime / 60);
            }
//            if (model.getTimeList().size() != 0) {
//                time = model.getTimeList().get(0).getTime();
//            }
        }

        for (int i = yValues.size(); i < 7; i++) {
            yValues.add((float) 0);
        }
        yValues.add(0, (float) 0);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(s));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");

        if (yValues.size() == 0) {
            return;
        }

        if (TextUtils.isEmpty(time)) {
            return;
        }

        //创建单条折线的图表
        lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
        lineChartManager1.setYAxisOnForce(100, 0, 2, true);
        lineChartManager1.setDescription("");
        lineChartManager1.setXAxisWeek(time.split(" ")[0]);//2019-01-14 00:00:00
        lineChartManager1.setYAxis(100, 0, 3);
    }
*/

    private void initChartViewtwo(WeekData.DataBean.ResultListBean model, ViewHolderHelper viewHolderHelper) {
        ChartView chartViewtwo = (ChartView) viewHolderHelper.getView(R.id.chartviewweektwo);
        //x轴坐标对应的数据
        List<String> xValuetwo = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValuetwo = new ArrayList<>();
        //折线对应的数据
        Map<String, Integer> valuetwo = new HashMap<>();

        List<Float> yValues;


        String time = null;

        long weekFirstDay = model.getWeekFirstDay();
        time = stampToDate(weekFirstDay + "");
        Log.e("shuju", time + "");


        //设置y轴的数据()
        yValues = new ArrayList<>();
        for (int j = 0; j < model.getTimeList().size(); j++) {
            int countTime = model.getTimeList().get(j).getCountTime();

            DecimalFormat df = new DecimalFormat("0.00");

            yValues.add(Float.parseFloat(df.format((float) countTime / 60)));
        }

        for (int i = 0; i <= ((int) (Collections.max(yValues) / 5) + 1); i++) {
            yValuetwo.add(i * 5);
        }


        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }


        if (TextUtils.isEmpty(time)) {
            return;
        }

        setWeekData(time.split(" ")[0], xValuetwo);

        chartViewtwo.setValue(valuetwo, xValuetwo, yValuetwo);

    }


    public void setWeekData(String time, List<String> xValue) {
        String[] weekData = new String[8];

        String substringtime = time.substring(5);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        try {
            weekData[0] = ("0");
            weekData[1] = (substringtime);
            for (int i = 2; i < 8; i++) {
                Date date = sdf.parse(substringtime);

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, i - 1);

                weekData[i] = (sdf.format(c.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < weekData.length; i++) {
            xValue.add(weekData[i]);
        }

    }


}

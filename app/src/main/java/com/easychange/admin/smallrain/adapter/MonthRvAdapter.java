package com.easychange.admin.smallrain.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.utils.LineChartManager;
import com.github.mikephil.charting.charts.LineChart;
import com.qlzx.mylibrary.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bean.AccuracyList;
import bean.MonthData;
import bean.TimeList;
import bean.TranningFileMonthBean;

/**
 * Created by chenlipeng on 2018/10/18 0018
 * describe:  月的适配器（图表）
 */
public class MonthRvAdapter extends RecyclerViewAdapter<MonthData.DataBean.ResultListBean> {

    private Intent intent;


    public MonthRvAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_month);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, final MonthData.DataBean.ResultListBean model) {

        LineChart linechart1 = (LineChart) viewHolderHelper.getView(R.id.linechart1);
        LineChart linechart2 = (LineChart) viewHolderHelper.getView(R.id.linechart2);
        TextView tvMinute = viewHolderHelper.getView(R.id.tv_minute);
//        initChart1(linechart1, "#24CFFD");
//        initChart1(linechart2, "#cd4744");
        initChart1(linechart1, "#24CFFD", model, 1, viewHolderHelper, tvMinute);
        initChart1(linechart2, "#cd4744", model, 2, viewHolderHelper, tvMinute);

        viewHolderHelper.setText(R.id.tv_month, model.getMonth() + "月");
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

    private void initChart1(LineChart lineChart1, String s, MonthData.DataBean.ResultListBean model, int position, ViewHolderHelper viewHolderHelper, TextView tvMinute) {
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);


        List<Float> yValues;
        ArrayList xValues;
        if (position == 1) {
            //设置y轴的数据()
            yValues = new ArrayList<>();
            for (int i = yValues.size(); i < 5; i++) {
                yValues.add((float) 0);
            }
            yValues.add(0, (float) 0);
            for (int j = 0; j < model.getList().size(); j++) {
//            yValues.add((float) (Math.random() * 80));
                MonthData.DataBean.ResultListBean.ListBean accuracyList = model.getList().get(j);
                if (accuracyList.getAccuracyList().size() != 0) {
                    AccuracyList accuracyList1 = accuracyList.getAccuracyList().get(0);
                    yValues.set(j+1,(float) accuracyList1.getAccuracy() * 100);
                }
            }

        } else {
            //设置y轴的数据()
            yValues = new ArrayList<>();
            //设置y轴的数据()
            yValues = new ArrayList<>();
            boolean b = false;
            for (int i = yValues.size(); i < 5; i++) {
                yValues.add((float) 0);
            }
            yValues.add(0, (float) 0);
            for (int j = 0; j < model.getList().size(); j++) {
                MonthData.DataBean.ResultListBean.ListBean accuracyList = model.getList().get(j);
                if (accuracyList.getTimeList().size() != 0) {
                    TimeList accuracyList1 = accuracyList.getTimeList().get(0);
                    if (accuracyList1.getCountTime() > 3600) {
                        b = true;
                        break;
                    }
                }

            }
            //说明大于一个小时
            if (b) {
                tvMinute.setText("学习时间 /小时");
                for (int j = 0; j < model.getList().size(); j++) {
                    MonthData.DataBean.ResultListBean.ListBean accuracyList = model.getList().get(j);
                    if (accuracyList.getTimeList().size() != 0) {
                        TimeList accuracyList1 = accuracyList.getTimeList().get(0);
                        yValues.set(j+1,TimeUtil.formatTime((float) accuracyList1.getCountTime()));
                    }
                }
            } else{ //否则小于
                tvMinute.setText("学习时间 /分钟");

                for (int j = 0; j < model.getList().size(); j++) {
                    MonthData.DataBean.ResultListBean.ListBean accuracyList = model.getList().get(j);
                    if (accuracyList.getTimeList().size() != 0) {
                        TimeList accuracyList1 = accuracyList.getTimeList().get(0);
                        yValues.set(j+1,(float) accuracyList1.getCountTime() / 60);
                    }
                }
            }
        }


//        for (int i = yValues.size(); i < 5; i++) {
//            yValues.add((float) 0);
//        }
//        yValues.add(0, (float) 0);
        //设置x轴的数据
        xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }


        if (yValues.size() == 0) {
            return;
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(s));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");

        //创建单条折线的图表
        lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
        // lineChartManager1.setYAxis(100, 0, 3);
        lineChartManager1.setYAxisOnForce((int) ((Collections.max(yValues) / 5) + 1) * 5, 0, 2, true);
        lineChartManager1.setDescription("");
        //  lineChartManager1.setXAxis(10, 0, xValues.size());
        lineChartManager1.setXAxisMonth();
        setLintChart(position == 1 ? viewHolderHelper.getView(R.id.ll_linear) : viewHolderHelper.getView(R.id.ll_linear1), yValues);
    }
}

package com.easychange.admin.smallrain.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.utils.LineChartManager;
import com.easychange.admin.smallrain.utils.StringAxisValueFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.qlzx.mylibrary.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bean.DayData;

/**
 * Created by chenlipeng on 2018/10/18 0018
 * describe:  日的适配器（图表）
 */
public class DayRvAdapter extends RecyclerViewAdapter<DayData.DataBean.ResultListBean> {

    private Intent intent;
    List<Entry> values;
    List<String> valuesStr;

    public DayRvAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_day);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, final DayData.DataBean.ResultListBean model) {
        LineChart linechart1 = (LineChart) viewHolderHelper.getView(R.id.linechart1);
        initChart1(linechart1, model, viewHolderHelper,viewHolderHelper.getView(R.id.rl_lcView));

        viewHolderHelper.getView(R.id.ll_1).setVisibility(View.GONE);
        viewHolderHelper.getView(R.id.ll_2).setVisibility(View.GONE);
        viewHolderHelper.getView(R.id.ll_3).setVisibility(View.GONE);
        viewHolderHelper.getView(R.id.ll_4).setVisibility(View.GONE);
        viewHolderHelper.getView(R.id.ll_5).setVisibility(View.GONE);
        viewHolderHelper.setText(R.id.tv_1, "");
        viewHolderHelper.setText(R.id.tv_2, "");
        viewHolderHelper.setText(R.id.tv_3, "");
        viewHolderHelper.setText(R.id.tv_4, "");
        viewHolderHelper.setText(R.id.tv_5, "");

        DecimalFormat df = new DecimalFormat("0");
        for (int j = 0; j < model.getDayResultList().size(); j++) {
            String progress = df.format((float) model.getDayResultList().get(j).getAccuracy() * 100) + "%";
            if (j == 0) {
                viewHolderHelper.getView(R.id.ll_1).setVisibility(View.VISIBLE);
                viewHolderHelper.setText(R.id.tv_1, progress);
            } else if (j == 1) {
                viewHolderHelper.getView(R.id.ll_2).setVisibility(View.VISIBLE);
                viewHolderHelper.setText(R.id.tv_2, progress);
            } else if (j == 2) {
                viewHolderHelper.getView(R.id.ll_3).setVisibility(View.VISIBLE);
                viewHolderHelper.setText(R.id.tv_3, progress);
            } else if (j == 3) {
                viewHolderHelper.getView(R.id.ll_4).setVisibility(View.VISIBLE);
                viewHolderHelper.setText(R.id.tv_4, progress);
            } else if (j == 4) {
                viewHolderHelper.getView(R.id.ll_5).setVisibility(View.VISIBLE);
                viewHolderHelper.setText(R.id.tv_5, progress);
            }
        }
        float studyTime = model.getStudyTime();
        String split = model.getStr().substring(0, 10);
        split = split.replaceFirst("-","年");
        split = split.replaceFirst("-","月")+"日";
//        studyTime
        viewHolderHelper.setText(R.id.tv_title, split + "(学习时间"+ TimeUtil.formatTimeS(model.getStudyTime())+(model.getStudyTime()>=3600?"小时":model.getStudyTime()>=60?"分钟":"秒")+")");

        viewHolderHelper.getView(R.id.ll_look_more).setVisibility(View.INVISIBLE);
        if (model.getDayResultList().size() >= 5) {
            viewHolderHelper.getView(R.id.ll_look_more).setVisibility(View.VISIBLE);
        }

        String finalSplit = split;
        viewHolderHelper.getView(R.id.ll_look_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.getDayResultList().size() >= 5) {
                    showDialog(model, finalSplit, studyTime);
                }
            }
        });

    }

    private void showDialog(DayData.DataBean.ResultListBean model, String split, float studyTime) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.look_more_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle)
                .setView(view).create();

        mDialog.setCancelable(true);

        final ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);

        TextView tv_data_time = (TextView) view.findViewById(R.id.tv_data_time);
        TextView tv_study_time = (TextView) view.findViewById(R.id.tv_study_time);
        tv_data_time.setText(split + "（学习时间 ");
        tv_study_time.setText( TimeUtil.formatTimeS(studyTime)+(studyTime>=3600?"小时":studyTime>=60?"分钟":"秒")+ "");

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);


        LookMoreRvAdapter adapter = new LookMoreRvAdapter(rv);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);

        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < model.getDayResultList().size(); i++) {
            objects.add(model.getDayResultList().get(i).getAccuracy() + "");
        }
        adapter.setData(objects);
        mDialog.show();

        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.7);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.5


        mDialog.getWindow().setAttributes(p);     //设置生效
    }

    private void initChart1(LineChart lineChart1, DayData.DataBean.ResultListBean model, ViewHolderHelper viewHolderHelper,View view) {
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);

        List<Float> yValues;
        //设置y轴的数据()
        yValues = new ArrayList<>();
        for (int j = 0; j < model.getDayResultList().size(); j++) {
//            yValues.add((float) (Math.random() * 80));
            double accuracy = model.getDayResultList().get(j).getAccuracy();
            yValues.add((float) accuracy * 100);
        }
        if (yValues.size() == 0) {

//            yValues.add(0, (float) 0);
//            //设置x轴的数据
//            ArrayList<Float> xValues = new ArrayList<>();
//            for (int i = 0; i < yValues.size(); i++) {//10组数据
//                xValues.add((float) i);
//            }
//
//            //颜色集合
//            List<Integer> colors = new ArrayList<>();
//            colors.add(Color.parseColor("#24CFFD"));
//
//            //线的名字集合
//            List<String> names = new ArrayList<>();
//            names.add("折线一");
//
//            //创建单条折线的图表
//            lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
//            lineChartManager1.setYAxisOnForce(100, 0, 2, true);
//            lineChartManager1.setDescription("");
//            lineChartManager1.setXAxis(xValues.size(), 0, xValues.size());
//            for (int i = 0; i < ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildCount(); i++) {
//                if (i == 0) {
//                    ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText(100 + "");
//                }else if(i%2 ==0 && i< 4){
//                    ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText(
//                            50+"");
//                }else {
//                    ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText("");
//                }
//
//            }
            view.setVisibility(View.GONE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        yValues.add(0, (float) 0);
        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {//10组数据
            xValues.add((float) i);
        }

        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#24CFFD"));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");


        //创建单条折线的图表
        lineChartManager1.showLineChart(mContext, xValues, yValues, names.get(0), colors.get(0));
        lineChartManager1.setYAxis(100, 0.1f, 5);
        for (int i = 0; i < ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildCount(); i++) {
            if (i == 0) {
                ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText(100 + "");
            }else if(i%2 ==0 && i< 4){
                ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText(
                        50+"");
            }else {
                ((TextView) ((ViewGroup) viewHolderHelper.getView(R.id.ll_linear)).getChildAt(i)).setText("");
            }

        }
        lineChartManager1.setDescription("");
        lineChartManager1.setXAxis(xValues.size()-1>10?xValues.size()-1:10, 0, xValues.size()-1>10?xValues.size()-1:10);

//          axisLeft.setAxisMaxValue( ); //最大值
//        setLineChart(lineChart1, model);
    }
}

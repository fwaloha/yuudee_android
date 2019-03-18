package com.easychange.admin.smallrain.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.adapter.SelectCityAdapter;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.entity.CityListBean;
import com.easychange.admin.smallrain.views.CustomTopBarNew;
import com.easychange.admin.smallrain.views.MyLetterAlistView;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bean.LocationBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by chenlipeng on 2018/10/29 0029
 * describe:  获取手机号归属地数据列表
 */
public class ChoosePhoneHomeLocationActivity extends BaseActivity {


    @BindView(R.id.select_city_list_view)
    ListView selectCityListView;
    @BindView(R.id.select_city_letter_list_view)
    MyLetterAlistView selectCityLetterListView;
    // 右侧A-Z字母列表

    private List<CityListBean> dataList = new ArrayList<CityListBean>();

    private PinyinSort pinyinSort;
    // dialog text
    private TextView overlay;
    // 估计是弹出dialog线程
    private OverlayThread overlayThread;
    private Handler handler;
    private SelectCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_city_activity);
        ButterKnife.bind(this);

        CustomTopBarNew topbar = (CustomTopBarNew) findViewById(R.id.topbar);
        topbar.setTopbarTitle("选择手机号归属地");
        topbar.setTitleColor(Color.parseColor("#000000"));
        topbar.setTopbarBackgroundColor(Color.parseColor("#ffffff"));
        topbar.setonTopbarNewLeftLayoutListener(new CustomTopBarNew.OnTopbarNewLeftLayoutListener() {
            @Override
            public void onTopbarLeftLayoutSelected() {
                finish();
            }
        });

        pinyinSort = new PinyinSort();

        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        selectCityLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        selectCityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phonePrefix = dataList.get(i).getPhonePrefix();
                String cityName = dataList.get(i).getCityName();
                String cityId = dataList.get(i).getCityId();

                Intent intent = new Intent();
                intent.putExtra("phonePrefix", phonePrefix);
                intent.putExtra("cityName", cityName);
                intent.putExtra("cityid",cityId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        qcellcoreList();
    }

    /**
     * 获取手机号归属地数据列表
     */
    private void qcellcoreList() {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).qcellcoreList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<List<LocationBean>>>(this, null) {
                    @Override
                    public void onNext(BaseBean<List<LocationBean>> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {

                            List<LocationBean> data = baseBean.data;
                            for (int i = 0; i < data.size(); i++) {

                                LocationBean locationBean = data.get(i);
                                for (int j = 0; j < locationBean.getList().size(); j++) {
                                    CityListBean cityListBean = new CityListBean();
                                    cityListBean.setCityName(locationBean.getList().get(j).getName());
                                    cityListBean.setAlphaCode(locationBean.getList().get(j).getCityType());
                                    cityListBean.setPhonePrefix(locationBean.getList().get(j).getPhonePrefix());
                                    cityListBean.setCityId(String.valueOf(locationBean.getList().get(j).getId()));

                                    dataList.add(cityListBean);
                                }

                            }
                            adapter = new SelectCityAdapter(ChoosePhoneHomeLocationActivity.this, dataList);
                            selectCityListView.setAdapter(adapter);

                        } else {
                            ToastUtil.showToast(ChoosePhoneHomeLocationActivity.this, baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

//    private void setData(List<CityListBean> listData) {
//
//
//        dataList.addAll(listData);
//        // 根据a-z进行排序源数据
//        Collections.sort(dataList, pinyinSort);
//
//        adapter.notifyDataSetChanged();
//    }

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {

        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class PinyinSort implements Comparator<CityListBean> {

        @Override
        public int compare(CityListBean cityListBean, CityListBean t1) {
            if (cityListBean.getAlphaCode().equals("@")
                    || t1.getAlphaCode().equals("定位城市") || t1.getAlphaCode().equals("最近访问城市")) {
                return 1;
            } else if (cityListBean.getAlphaCode().equals("定位城市")
                    || cityListBean.getAlphaCode().equals("最近访问城市")
                    || t1.getAlphaCode().equals("@")) {
                return -1;
            } else if (cityListBean.getAlphaCode().equals("定位城市")
                    || t1.getAlphaCode().equals("最近访问城市")
                    ) {
                return -1;
            } else {
                return cityListBean.getAlphaCode().compareTo(t1.getAlphaCode());
            }
        }

    }

    // 右侧A-Z字母监听
    private class LetterListViewListener implements
            MyLetterAlistView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {

            // 该字母首次出现的位置
            int position = adapter.getPositionForSection(s.charAt(0));

            if (position != -1) {

                selectCityListView.setSelection(position);
                overlay.setText(dataList.get(position).getAlphaCode());
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1500);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }
}


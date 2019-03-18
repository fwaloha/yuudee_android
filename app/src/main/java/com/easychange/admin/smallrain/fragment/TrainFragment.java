package com.easychange.admin.smallrain.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.adapter.DayRvAdapter;
import com.easychange.admin.smallrain.adapter.MonthRvAdapter;
import com.easychange.admin.smallrain.adapter.WeekRvAdapter;
import com.easychange.admin.smallrain.base.BaseFragment;
import com.easychange.admin.smallrain.entity.LuckBackRoomListviewBean;
import com.easychange.admin.smallrain.views.CircleImageView;
import com.easychange.admin.smallrain.views.RecycleViewDivider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenlipeng on 2018/10/17 0017.
 * describe :训练档案
 */

public class TrainFragment extends BaseFragment {
    //          CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener
    Unbinder unbinder;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.img_user_data)
    CircleImageView imgUserData;
    @BindView(R.id.can_content_view)
    RecyclerView canContentView;
    //    @BindView(R.id.can_refresh_header)
//    ClassicRefreshView canRefreshHeader;
//    @BindView(R.id.can_refresh_footer)
//    ClassicRefreshView canRefreshFooter;
//    @BindView(R.id.refresh)
//    CanRefreshLayout refresh;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.iv_day)
    ImageView ivDay;
    @BindView(R.id.ll_day)
    LinearLayout llDay;
    @BindView(R.id.iv_week)
    ImageView ivWeek;
    @BindView(R.id.ll_week)
    LinearLayout llWeek;
    @BindView(R.id.iv_month)
    ImageView ivMonth;
    @BindView(R.id.ll_month)
    LinearLayout llMonth;
//    @BindView(R.id.refreshLayout)
//    com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
//    @BindView(R.id.rl_empty)
//    RelativeLayout rlEmpty;
    private DayRvAdapter dayRvAdapter;
    private WeekRvAdapter weekRvAdapter;
    private MonthRvAdapter monthRvAdapter;

    private List<LuckBackRoomListviewBean> baseList = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.train_product, null);

        return view;
    }
//    DecimalFormat df = new DecimalFormat("#.00");
    @Override
    protected void initLazyData() {
//        showDialog();
        dayRvAdapter = new DayRvAdapter(canContentView);
        weekRvAdapter = new WeekRvAdapter(canContentView);
        monthRvAdapter = new MonthRvAdapter(canContentView);

        canContentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        canContentView.addItemDecoration(new RecycleViewDivider(getActivity()));
        canContentView.setAdapter(dayRvAdapter);

//        refresh.setOnLoadMoreListener(this);
//        refresh.setOnRefreshListener(this);
//        refresh.setMaxFooterHeight(DensityUtil.dp2px(getActivity(), 150));
//        refresh.setStyle(0, 0);
//        canRefreshHeader.setPullStr("下拉刷新");
//        canRefreshHeader.setReleaseStr("松开刷新");
//        canRefreshHeader.setRefreshingStr("加载中");
//        canRefreshHeader.setCompleteStr("");
//
//        canRefreshFooter.setPullStr("上拉加载更多");
//        canRefreshFooter.setReleaseStr("松开刷新");
//        canRefreshFooter.setRefreshingStr("加载中");
//        canRefreshFooter.setCompleteStr("");

//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//            }
//        });

//        getListData();
    }

    @OnClick({R.id.ll_day, R.id.ll_week, R.id.ll_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_day:  //

                ivDay.setImageResource(R.drawable.day_select);//不会变形
                ivWeek.setImageResource(R.drawable.week);//不会变形
                ivMonth.setImageResource(R.drawable.month);//不会变形

                canContentView.setAdapter(dayRvAdapter);
                break;
            case R.id.ll_week:    //
                ivDay.setImageResource(R.drawable.day);//不会变形
                ivWeek.setImageResource(R.drawable.week_select);//不会变形
                ivMonth.setImageResource(R.drawable.month);//不会变形

                canContentView.setAdapter(weekRvAdapter);

                break;
            case R.id.ll_month:
                ivDay.setImageResource(R.drawable.day);//不会变形
                ivWeek.setImageResource(R.drawable.week);//不会变形
                ivMonth.setImageResource(R.drawable.month_select);//不会变形

                canContentView.setAdapter(monthRvAdapter);

                break;
        }
    }

//    public void getListData() {
//        baseList.clear();
//
//        for (int i = 0; i < 12; i++) {
//            LuckBackRoomListviewBean myOrderListviewBean = new LuckBackRoomListviewBean();
//            baseList.add(myOrderListviewBean);
//        }
//        dayRvAdapter.setData(baseList);
//        weekRvAdapter.setData(baseList);
//        monthRvAdapter.setData(baseList);
//
//    }

//    @Override
//    public void onLoadMore() {
//        refresh.loadMoreComplete();
//        refresh.refreshComplete();
//    }
//
//    @Override
//    public void onRefresh() {
//        refresh.loadMoreComplete();
//        refresh.refreshComplete();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

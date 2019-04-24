package com.easychange.admin.smallrain.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.OverallLearningProgressAndResultsActivity;
import com.easychange.admin.smallrain.activity.PerfectionChildrenInfoActivity;
import com.easychange.admin.smallrain.activity.TrainActivity;
import com.easychange.admin.smallrain.base.BaseFragment;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.views.CustomHorizontalProgresNoNum;
import com.google.gson.Gson;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import java.util.List;

import bean.TranningRecordBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * admin  2018/8/24 wan
 * 训练档案
 */
public class RecordsFragment extends BaseFragment {
    @BindView(R.id.ll_shownodata)
    LinearLayout llShownoData;
    @BindView(R.id.tv_hintmessage)
    TextView tvHintMessage;
    @BindView(R.id.ll_alledit)
    LinearLayout ll_alledit;
    @BindView(R.id.tv_allprogress)
    TextView tvAllprogress;
    @BindView(R.id.progress)
    CustomHorizontalProgresNoNum progress;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.recycler_progress)
    RecyclerView recyclerView;
    @BindView(R.id.ll_sum)
    LinearLayout llSum;
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private TranningRecordBean tranningRecordBean;
    private Dialog mDialog;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.frag_records, null);
        unbinder = ButterKnife.bind(this, view);
        if (!TextUtils.isEmpty(new PreferencesHelper(getActivity()).getToken())) {
            getDataFromServer(new PreferencesHelper(getActivity()).getToken());
        }
        return view;
    }


    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setFocusable(false);
        if (tranningRecordBean != null && tranningRecordBean.getStatisticsList().size() > 0) {
            for (TranningRecordBean.StatisticsListBean statisticsListBean : tranningRecordBean.getStatisticsList()) {
                if ("5".equals(statisticsListBean.getModule())) {


                    ll_alledit.setVisibility(View.VISIBLE);
                    tranningRecordBean.getStatisticsList().remove(statisticsListBean);
                    progress.setProgress((int) (statisticsListBean.getRate1() * 100));
                    tvProgress.setText(String.valueOf((double) (statisticsListBean.getRate1() * 100)) + "%");
                    if (statisticsListBean.getRate1() != 0) {
                        tvHintMessage.setText(tranningRecordBean.getChildName() + "的训练档案");
                    }
                    tvAllprogress.setText("总体通关进度");
                }
            }
            ProgressAdapter titleAdapter = new ProgressAdapter(R.layout.item_progress_recycle, tranningRecordBean.getStatisticsList());
            recyclerView.setAdapter(titleAdapter);

            titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TranningRecordBean.StatisticsListBean statisticsListBean = titleAdapter.getItem(position);
                    Log.i("liubiao", "onItemClick: "+statisticsListBean.getLearningTime());
                    if (statisticsListBean.getLearningTime() ==0) {
                        switch (statisticsListBean.getModule()) {
                            case "1":
                                ToastUtil.showToast(getActivity(), "学习进度暂无数据!");
                                break;
                            case "2":
                                ToastUtil.showToast(getActivity(), "学习进度暂无数据!");
                                break;
                            case "3":
                                ToastUtil.showToast(getActivity(), "学习进度暂无数据!");
                                break;
                            case "4":
                                ToastUtil.showToast(getActivity(), "学习进度暂无数据!");
                                break;
                        }
                        return;
                    }
                    Intent intent = new Intent(mContext, TrainActivity.class);
                    intent.putExtra("position", position + 1);
                    startActivity(intent);
                }
            });
        }
    }


    /**
     * 获取要展示的数据
     *
     * @param token
     */
    public void getDataFromServer(String token) {
        HttpHelp.getRetrofit(getActivity()).create(RemoteApi.class).getRecrdProgressList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<TranningRecordBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseBean<TranningRecordBean> listBaseBean) {
                        super.onNext(listBaseBean);
                        if (listBaseBean.code == 200) {
                            tranningRecordBean = listBaseBean.data;
                            llShownoData.setVisibility(View.GONE);
                            initData();
                        } else if (listBaseBean.code == 202) {
                            llShownoData.setVisibility(View.VISIBLE);
                        }else if (listBaseBean.code ==205 || listBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(getActivity());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    @Override
    protected void initLazyData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private class ProgressAdapter extends BaseQuickAdapter<TranningRecordBean.StatisticsListBean, BaseViewHolder> {

        public ProgressAdapter(int layoutResId, @Nullable List<TranningRecordBean.StatisticsListBean> data) {
            super(layoutResId, data);
        }

        //item_progress_recycle
        @Override
        protected void convert(BaseViewHolder helper, TranningRecordBean.StatisticsListBean item) {
            switch (item.getModule()) {
                case "1":
                    helper.setText(R.id.tv_title, "Level1名词短语结构学习进度");
                    break;
                case "2":
                    helper.setText(R.id.tv_title, "Level2动词短语结构学习进度");
                    break;
                case "3":
                    helper.setText(R.id.tv_title, "Level3-1句子结构成组学习进度");
                    break;
                case "4":
                    helper.setText(R.id.tv_title, "Level3-2句子结构分解学习进度");
                    break;
                case "5":
                    helper.setText(R.id.tv_title, "总体通关进度");
            }

            CustomHorizontalProgresNoNum customHorizontalProgresNoNum = (CustomHorizontalProgresNoNum) helper.getView(R.id.progress);
            int progress = (int) (item.getRate1() * 100);
            customHorizontalProgresNoNum.setProgress(progress);
           // 四舍五入 保留小数点两位
            helper.setText(R.id.tv_progress, String.format("%.2f",item.getRate1() * 100) + "%");
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_alledit, R.id.ll_allprogress, R.id.ll_noun, R.id.ll_verb, R.id.ll_sentence, R.id.ll_sentencefenjie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!"2".equals(((MainActivity)getActivity()).getIsRemind())){
                    return;
                }
                mActivity.finish();
                break;
            case R.id.ll_alledit:
                if ("0".equals(tvProgress.getText().toString().trim())) {
                    ToastUtil.showToast(getActivity(), "学习进度暂无数据!");
                    return;
                } else {
                    startActivity(new Intent(mContext, OverallLearningProgressAndResultsActivity.class));
                }
                break;
            case R.id.ll_allprogress:
                ToastUtil.showToast(getActivity(), "您还没有完善儿童信息!");
                break;
            case R.id.ll_noun:
                ToastUtil.showToast(getActivity(), "您还没有完善儿童信息!");
                break;
            case R.id.ll_verb:
                ToastUtil.showToast(getActivity(), "您还没有完善儿童信息!");
                break;
            case R.id.ll_sentence:
                ToastUtil.showToast(getActivity(), "您还没有完善儿童信息!");
                break;
            case R.id.ll_sentencefenjie:
                ToastUtil.showToast(getActivity(), "您还没有完善儿童信息!");
                break;
        }
    }

    public void isRindTitleShow() {

        UserBean userBean = new Gson().fromJson(new PreferencesHelper(getActivity()).getUserInfo(), UserBean.class);

        if (userBean != null) {
            if ("1".equals(userBean.getIsRemind())) {
                showTitleDialog();
            } else if ("2".equals(userBean.getIsRemind())) {

            }
        }
    }


    private void showTitleDialog() {

        mDialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popuwindow_isremind, null);
        mDialog.setContentView(inflate);
        Window dialogwindow = mDialog.getWindow();
        dialogwindow.setGravity(Gravity.TOP);
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        // lp.windowAnimations = R.style.PopUpBottomAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.y = ScreenUtil.getBottomStatusHeight(PersionMessageActivity.this);
        dialogwindow.setAttributes(lp);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        Button btnExerpirence, btnfinishMessage;
        btnExerpirence = inflate.findViewById(R.id.btn_experience);
        btnfinishMessage = inflate.findViewById(R.id.btn_finishmessage);
        btnExerpirence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                getActivity().finish();
            }
        });
        btnfinishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                PerfectionChildrenInfoActivity.startActivityWithnoParmeter(getActivity());
            }
        });

    }
}

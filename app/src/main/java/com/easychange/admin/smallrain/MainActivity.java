package com.easychange.admin.smallrain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.easychange.admin.smallrain.activity.BalloonActivity;
import com.easychange.admin.smallrain.activity.BalloonExperienceActivity;
import com.easychange.admin.smallrain.activity.PerfectionChildrenInfoActivity;
import com.easychange.admin.smallrain.adapter.FragPagerAdapter;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.fragment.AssessFragment;
import com.easychange.admin.smallrain.fragment.MoreFragment;
import com.easychange.admin.smallrain.fragment.ProductFragment;
import com.easychange.admin.smallrain.fragment.RecordsFragment;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.views.NoScrollViewPager;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bean.AssementReviewBean;
import bean.GotoQuestionnaireBean;
import bean.RegisterDestroyOtherBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import event.FinishRemindEvent;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_experience)
    Button btnExperience;
    @BindView(R.id.btn_finishmessage)
    Button btnFinishMessage;
    @BindView(R.id.rl_isrmind)
    RelativeLayout rlIsRmind;
    @BindView(R.id.act_main_vp)
    NoScrollViewPager actMainVp;
    @BindView(R.id.btn_top_one)
    Button btnTopOne;
    @BindView(R.id.btn_top_two)
    Button btnTopTwo;
    @BindView(R.id.btn_top_three)
    Button btnTopThree;
    @BindView(R.id.btn_top_four)
    Button btnTopFour;
    @BindView(R.id.btn_bottom_one)
    Button btnBottomOne;
    @BindView(R.id.btn_bottom_two)
    Button btnBottomTwo;
    @BindView(R.id.btn_bottom_three)
    Button btnBottomThree;
    @BindView(R.id.btn_bottom_four)
    Button btnBottomFour;

    private List<Fragment> fragments;

    private FragPagerAdapter pagerAdapter;

    public String isRemind = "1";

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();  //隐藏掉标题栏
        }
        initStatusBar();
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        fragments.add(new ProductFragment());
        fragments.add(new RecordsFragment());
        fragments.add(new AssessFragment());
        fragments.add(new MoreFragment());

        pagerAdapter = new FragPagerAdapter(getSupportFragmentManager(), fragments);
        actMainVp.setAdapter(pagerAdapter);
        actMainVp.setNoScroll(true);

        Intent intent = getIntent();
        if (intent != null) {
            int type = intent.getIntExtra("mainactivitytype", 1);
            setVisible(type);
            actMainVp.setCurrentItem(type - 1);
        }

        if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
            getAssessmentReview(new PreferencesHelper(this).getToken());
        }

        EventBusUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GotoQuestionnaireBean(GotoQuestionnaireBean event) {
        setVisible(3);
        actMainVp.setCurrentItem(2);
    }

    public static void startActivityWithParmeter(Context context, int type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("mainactivitytype", type);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInformation(FinishRemindEvent event) {
        if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
            getAssessmentReview(new PreferencesHelper(this).getToken());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    public void getAssessmentReview(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getAssessmentReview(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {
                                setIsRemind(assementReviewBean.getIsRemind());
                                isRemind = assementReviewBean.getIsRemind();
                                if ("1".equals(isRemind)) {
                                    rlIsRmind.setVisibility(View.VISIBLE);
                                } else if ("2".equals(isRemind)) {
                                    rlIsRmind.setVisibility(View.GONE);
                                }
                            }
                        }else if (assementReviewBeanBaseBean.code ==205 || assementReviewBeanBaseBean.code ==209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(MainActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    private void initStatusBar() {
        //最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 全透明实现
            // getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @OnClick({R.id.btn_bottom_one, R.id.btn_bottom_two, R.id.btn_bottom_three, R.id.btn_bottom_four, R.id.btn_experience, R.id.btn_finishmessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom_one:
                setVisible(1);
                actMainVp.setCurrentItem(0);

                break;
            case R.id.btn_bottom_two:
                setVisible(2);
                actMainVp.setCurrentItem(1);

                break;
            case R.id.btn_bottom_three:
                setVisible(3);
                actMainVp.setCurrentItem(2);

                break;
            case R.id.btn_bottom_four:
                setVisible(4);
                actMainVp.setCurrentItem(3);

                break;
            case R.id.btn_experience:

                Intent intent1 = new Intent(MainActivity.this, BalloonActivity.class);
//                intent1.putExtra("isFirstregister",true);
                startActivity(intent1);
                finish();
//                PreferencesHelper helper = new PreferencesHelper(MainActivity.this);
//                helper.saveInt("sp", "mingciJinbi_tiyan", 0);//金币
//
//                Intent intent1 = new Intent(MainActivity.this, MingciOneExperienceActivity.class);
//                startActivity(intent1);
//                finish();
                break;
            case R.id.btn_finishmessage:
                Intent intent = new Intent(MainActivity.this, PerfectionChildrenInfoActivity.class);
                intent.putExtra("registertype", "mainback");
                startActivityForResult(intent, 300);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
                getAssessmentReview(new PreferencesHelper(this).getToken());
            }
        }
    }

    private void setVisible(int visible) {
        if (visible == 1) {
            btnBottomOne.setVisibility(View.INVISIBLE);
            btnBottomTwo.setVisibility(View.VISIBLE);
            btnBottomThree.setVisibility(View.VISIBLE);
            btnBottomFour.setVisibility(View.VISIBLE);

            btnTopOne.setVisibility(View.VISIBLE);
            btnTopTwo.setVisibility(View.INVISIBLE);
            btnTopThree.setVisibility(View.INVISIBLE);
            btnTopFour.setVisibility(View.INVISIBLE);
        } else if (visible == 2) {
            btnBottomOne.setVisibility(View.VISIBLE);
            btnBottomTwo.setVisibility(View.INVISIBLE);
            btnBottomThree.setVisibility(View.VISIBLE);
            btnBottomFour.setVisibility(View.VISIBLE);

            btnTopOne.setVisibility(View.INVISIBLE);
            btnTopTwo.setVisibility(View.VISIBLE);
            btnTopThree.setVisibility(View.INVISIBLE);
            btnTopFour.setVisibility(View.INVISIBLE);

        } else if (visible == 3) {
            btnBottomOne.setVisibility(View.VISIBLE);
            btnBottomTwo.setVisibility(View.VISIBLE);
            btnBottomThree.setVisibility(View.INVISIBLE);
            btnBottomFour.setVisibility(View.VISIBLE);

            btnTopOne.setVisibility(View.INVISIBLE);
            btnTopTwo.setVisibility(View.INVISIBLE);
            btnTopThree.setVisibility(View.VISIBLE);
            btnTopFour.setVisibility(View.INVISIBLE);

        } else if (visible == 4) {
            btnBottomOne.setVisibility(View.VISIBLE);
            btnBottomTwo.setVisibility(View.VISIBLE);
            btnBottomThree.setVisibility(View.VISIBLE);
            btnBottomFour.setVisibility(View.INVISIBLE);

            btnTopOne.setVisibility(View.INVISIBLE);
            btnTopTwo.setVisibility(View.INVISIBLE);
            btnTopThree.setVisibility(View.INVISIBLE);
            btnTopFour.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBusUtil.post(new RegisterDestroyOtherBean());

            Intent intent = new Intent(MainActivity.this, BalloonActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}

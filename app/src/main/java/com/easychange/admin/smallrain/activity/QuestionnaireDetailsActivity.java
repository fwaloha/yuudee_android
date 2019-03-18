package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.adapter.QuestionParentAdapter;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.dialog.QuestionHintDialog;
import com.easychange.admin.smallrain.entity.QuestionListBean;
import com.easychange.admin.smallrain.event.OnQuestionContinueClickListener;
import com.easychange.admin.smallrain.login.LoginActivity;
import com.easychange.admin.smallrain.login.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create 2018/10/16 0016
 * by 李
 * 页面注释：ABC问卷详情
 **/
public class QuestionnaireDetailsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.parent_layout)
    LinearLayout parent;
    @BindView(R.id.tv_sure)
    TextView sure;
    private QuestionHintDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_details);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String[] array=getResources().getStringArray(R.array.question_abc);
        for (int i=0;i<5;i++){
            View view=View.inflate(this,R.layout.item_question,null);
            TextView textView=view.findViewById(R.id.tv_title);
            textView.setText(array[i]);
            parent.addView(view);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (dialog==null) {
                    dialog = new QuestionHintDialog(this);
                    dialog.setOnQuestionContinueClickListener(new OnQuestionContinueClickListener() {
                        @Override
                        public void onCom() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onEx() {
                            finish();
                        }
                    });
                }

               dialog.show();
                break;
            case R.id.tv_sure:
                //确定 走下一级的 必须finish
                finish();
                //startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
//5块钱的。
}

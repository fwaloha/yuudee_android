package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create 2018/10/20 0020
 * by 李
 * 页面注释：  注册结果页
 **/
public class CompleteRegisterActivity extends BaseActivity {

    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.img_home_left)
    ImageView left;
    @BindView(R.id.img_home_right)
    ImageView right;

    private View view;
    private TextView ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        ButterKnife.bind(this);
        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        view = View.inflate(this, R.layout.layout_complete_register, null);
        parentLayout.addView(view);
        ok = view.findViewById(R.id.tv_sure);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CompleteRegisterActivity.this, PerfectionChildrenInfoActivity.class);
                intent1.putExtra("registertype","firstregister");
                startActivity(intent1);

                finish();
            }
        });
    }
}

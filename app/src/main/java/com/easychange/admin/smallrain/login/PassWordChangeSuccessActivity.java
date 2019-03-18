package com.easychange.admin.smallrain.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenlipeng on 2018/11/22 0022
 * describe:  进入密码修改成功页面
 */
public class PassWordChangeSuccessActivity extends AppCompatActivity {
    // TODO: 2018/11/22 0022 页面要调整 
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word_change_success);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:  //

//                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }
    }
}

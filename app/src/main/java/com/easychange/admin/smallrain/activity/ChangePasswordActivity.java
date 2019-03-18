package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.login.LoginActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.SmsButtonUtil;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create 2018/10/18 0018
 * by 李
 * 修改密码
 **/
public class ChangePasswordActivity extends BaseActivity implements AsyncRequest {
    @BindView(R.id.et_one)
    EditText etOne;
    @BindView(R.id.et_two)
    EditText etTwo;
    @BindView(R.id.et_three)
    EditText etThree;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.layout_pt_content)
    LinearLayout layoutPtContent;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.img_home_left)
    ImageView imgHomeLeft;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;

//              1.上面三个空格都输入后，确定提交按钮点亮可点。
//            2.点击确定提交按钮，先判断当前密码是否输入正确，错误提示“当面密码错误，请重新输入”。当前密码栏可修改。
//            3.再判断新密码输入位数是否正确，错误提示“请输入6-13位数字或字母组合”。
//            4.最后判断新密码两次输入是否一致，不一致提示：“新密码两次输入不一致，请重新输入”。
//            5.点击确定提交按钮，判断条件都正确，提示“密码修改成功”，返回建议与设置页面。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data1);
        ButterKnife.bind(this);

        etOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!MyUtils.isSpecialChar(editable.toString())){
                    ToastUtil.showToast(ChangePasswordActivity.this,"请输入6-13位数字或字母");
                    editable.clear();
                    return;
                }
                setSureBtnStatus();
            }
        });
        etTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!MyUtils.isSpecialChar(editable.toString())){
                    ToastUtil.showToast(ChangePasswordActivity.this,"请输入6-13位数字或字母");
                    editable.clear();
                    return;
                }
                setSureBtnStatus();
            }
        });
        etThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!MyUtils.isSpecialChar(editable.toString())){
                    ToastUtil.showToast(ChangePasswordActivity.this,"请输入6-13位数字或字母");
                    editable.clear();
                    return;
                }
                setSureBtnStatus();
            }
        });
    }

    private void setSureBtnStatus() {
        String currentPassword = etOne.getText().toString();
        String twoPassword = etTwo.getText().toString();
        String threePassword = etThree.getText().toString();

        if (!TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(twoPassword) &&
                !TextUtils.isEmpty(threePassword)) {
            tvSure.setTextColor(Color.parseColor("#ffffff"));
            tvSure.setClickable(true);
        } else {
            tvSure.setTextColor(Color.parseColor("#b5ada5"));
            tvSure.setClickable(false);
        }
    }


    @OnClick({R.id.tv_sure, R.id.img_home_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_home_left:
                finish();
                break;
            case R.id.tv_sure:    //
//                更多页面修改密码：三个文本框都输入后，确定提交按钮点亮可点。否则确认按钮置灰不可点击
                String currentPassword = etOne.getText().toString().trim();
                String twoPassword = etTwo.getText().toString().trim();
                String threePassword = etThree.getText().toString().trim();

                if (TextUtils.isEmpty(currentPassword)) {
                    ToastUtil.showToast(mContext, "请输入当前密码");
                    return;
                }
                if (TextUtils.isEmpty(twoPassword)) {
                    ToastUtil.showToast(mContext, "请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(threePassword)) {
                    ToastUtil.showToast(mContext, "请输入确认密码");
                    return;
                }
                if (currentPassword.length()<6){
                    ToastUtil.showToast(mContext, "请输入至少6位密码");
                    return;
                }
                if (twoPassword.length() < 6 ) {
                    ToastUtil.showToast(mContext, "请输入至少6位密码");
                    return;
                }
                if (threePassword.length() < 6 ) {
                    ToastUtil.showToast(mContext, "请输入至少6位密码");
                    return;
                }

                if (twoPassword.equals(threePassword)) {
                    // TODO: 2018/11/2 0002    2.点击确定提交按钮，先判断当前密码是否输入正确，错误提示“当面密码错误，请重新输入”。当前密码栏可修改。
                    updatePassword(currentPassword, twoPassword);
                } else {

                    ToastUtil.showToast(mContext, "新密码两次输入不一致，请重新输入");
                }
                break;

        }
    }


    /**
     * 修改密码
     * token	是	string	用户标识
     * oldPassword	是	string	旧密码
     * newPassword	是	string	新密码
     *
     * @param oldPassword
     * @param newPassword
     */
    private void updatePassword(final String oldPassword, final String newPassword) {
        PreferencesHelper helper = new PreferencesHelper(ChangePasswordActivity.this);
        String token = helper.getToken();

        String url = Setting.updatePassword();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("token", token);
        stringStringHashMap.put("oldPassword", oldPassword);
        stringStringHashMap.put("newPassword", newPassword);//

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ChangePasswordActivity.this, this));

    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {

        if (object.equals(1)) {//标记那个接口

            String result = (String) data;
            (ChangePasswordActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {//密码修改成功
                            ToastUtil.showToast(ChangePasswordActivity.this, "密码修改成功");
                            finish();
                        } else {
                            ToastUtil.showToast(ChangePasswordActivity.this, msg1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {

        (ChangePasswordActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(ChangePasswordActivity.this, errorMessage);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

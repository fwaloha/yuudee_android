package com.easychange.admin.smallrain.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.CompleteRegisterActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;

import bean.RegisterBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenlipeng on 2018/10/30 0030
 * describe:  注册时候，设置密码页面
 */
public class SetLoginPassActivity extends BaseActivity {

    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_sure_pass)
    EditText edtSurePass;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    private String edtPhone;
    private String districeId;
//6.1  设置密码：允许输入6-13位数字或字母或数字和字母组合，输入超过13位，方框中不再显示。输入少于6位时，当点确认密码时，提示“请输入至少6位密码”
// 。当设置密码时输入的为其它符号，提示“请输入6-13位数字或字母”。
//            6.2 确认密码：输入超过13位不再显示。
//            6.3 完成按钮：当上面信息都填写后，完成按钮点亮可点。点击先判断两次密码输入是否一致，如不一致，显示‘两次密码输入不一致；如一致，点击完成进入注册结果页。如下。
//            7.注册结果页。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_login_pass);
        ButterKnife.bind(this);

        edtPhone = getIntent().getStringExtra("edtPhone");
        districeId = getIntent().getStringExtra("districeId");

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!MyUtils.isSpecialChar(editable.toString())){
                    ToastUtil.showToast(SetLoginPassActivity.this,"请输入6-13位数字或字母");
                    editable.clear();
                    return;
                }
            }
        });
        edtSurePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!MyUtils.isSpecialChar(editable.toString())){
                    ToastUtil.showToast(SetLoginPassActivity.this,"请输入6-13位数字或字母");
                    editable.clear();
                    return;
                }
                String s = editable.toString();
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(edtPass.getText().toString())) {
//                    if (MyUtils.CheckPhoneNum(SetLoginPassActivity.this, edtPhone.getText().toString())) {
                    tvSuccess.setTextColor(Color.parseColor("#fffdec"));
                    tvSuccess.setEnabled(true);
                } else {
                    tvSuccess.setTextColor(Color.parseColor("#b5ada5"));
                    tvSuccess.setEnabled(false);
                }

            }
        });
    }


    @OnClick({R.id.tv_success, R.id.img_home_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_success:
                String firstPassword = edtPass.getText().toString().trim();
                String surePassword = edtSurePass.getText().toString().trim();

                if (TextUtils.isEmpty(firstPassword)){
                    ToastUtil.showToast(mContext, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(surePassword)){
                    ToastUtil.showToast(mContext, "请输入密码");
                    return;
                }
                if (firstPassword.length()<6){
                    ToastUtil.showToast(mContext, "请输入至少6位密码");
                    return;
                }
                if (surePassword.length()<6){
                    ToastUtil.showToast(mContext, "请输入至少6位密码");
                    return;
                }

                if (firstPassword.equals(surePassword)){
                    register(edtPhone, firstPassword, districeId);
                }else {
                    ToastUtil.showToast(mContext, "两次密码输入不一致");
                }

                break;
            case R.id.img_home_right:
                finish();
                break;
        }
    }

    /**
     * 家长注册
     * phone	是	string	手机号
     * password	是	string	密码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @param phone
     * @param password
     * @param qcellcoreId
     */
    private void register(final String phone, final String password, String qcellcoreId) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).register(phone, password, qcellcoreId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<RegisterBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<RegisterBean> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
//                     {"msg":"注册成功","code":200,"data":{"id":15,"nickname":null,"age":null,"sex":null,"qcellcoreId":1,"phoneNumber":"18210182462","password":"00752D80279338EBFAA77EABC702972565828F2DD77B99094AD89163","createTime":1541039272418,"childId":null,"updateTime":null,"status":"1","token":"yekUICAbkcFTMQs7/VvC/Q==","city":null,"phonePrefix":null}}
//                            {"msg":"该手机已注册，请直接登录使用吧！","code":202,"data":""}
                            RegisterBean registerBean = baseBean.data;
                            if (registerBean!=null){
                                new PreferencesHelper(SetLoginPassActivity.this).saveToken(registerBean.getToken());
                            }
                            startActivity(new Intent(mContext, CompleteRegisterActivity.class));
                            finish();
                        } else {
                            ToastUtil.showToast(SetLoginPassActivity.this, baseBean.msg + "");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }
}

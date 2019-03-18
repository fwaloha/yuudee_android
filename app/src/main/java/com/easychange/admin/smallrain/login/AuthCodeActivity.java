package com.easychange.admin.smallrain.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.SendSmsTimerUtils;
import com.easychange.admin.smallrain.views.SecurityCodeView;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenlipeng on 2018/10/30 0030
 * describe:  注册的时候，接受验证码的页面
 */
public class AuthCodeActivity extends BaseActivity {
    //                            edtCode.setFocusable(true);
//                            edtCode.setFocusableInTouchMode(true);
//                            edtCode.requestFocus();
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    //    @BindView(R.id.edt_code)
//    FillBlankView edtCode;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.tv_phone_num_prefix)
    TextView tvPhoneNumPrefix;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;

    @BindView(R.id.edit_security_code)
    SecurityCodeView editSecurityCode;
    private String edtPhone = "";
    private String districeId;
    private String myphonePrefix;
    private String verificationCode = "111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_code);
        ButterKnife.bind(this);


        initListener();
//        intent.putExtra("edtPhone", edtPhone.getText().toString() + "");
//        intent.putExtra("districeId", districeId);

        edtPhone = getIntent().getStringExtra("edtPhone");
        districeId = getIntent().getStringExtra("districeId");
        myphonePrefix = getIntent().getStringExtra("myphonePrefix");
        tvPhoneNumPrefix.setText("验证码已发送至+" + myphonePrefix );
        indexPhone(edtPhone);

        registerSendCode(edtPhone, districeId);

    }

//     5.1 文案：“验证码已发送至+86 正确的手机号” “（60S倒计时）后重新获取”。
//            5.2 用户填入六位验证码后，自动验证是否正确，正确直接跳转到下一步设置密码页。
//            5.3如果用户60s时没有验证通过，“（60S倒计时）后重新获取”文案显示为“重新获取”，当用户点击“重新获取”时，5分钟内系统再一次发送同一验证码到该手机号，展示页面同上。如果验证码填写错误，系统提示文案“验证码错误，请重新输入”，此时6位数字框数字均可修改。
//    即： 验证码填入的6个表格，填满自动验证， 验证码输入正确，直接跳到下一页设置密码页。输入不正确，在方框上方提示文案“验证码输入不正确，请重新输入”，同时验证码输入框清空。
//            6.设置密码页：


    private void initListener() {
        editSecurityCode.setInputCompleteListener(new SecurityCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                String otherStr = editSecurityCode.getEditContent().toString();
                if (otherStr.length() >= 6) {
                    registerCodeverify(edtPhone, districeId, otherStr.toString());

                }
            }

            @Override
            public void deleteContent(boolean isDelete) {

            }
        });
    }

    @OnClick({R.id.tv_get_code, R.id.img_home_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                registerSendCode(edtPhone, districeId);
                break;
            case R.id.img_home_right:
                finish();
                break;
        }
    }


    public void indexPhone(String s){

        //手机号格式化xxx xxxx xxxx
        if (s == null || s.length() == 0)
            return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        tvPhoneNum.setText(sb.toString());

    }

    /**
     * 家长注册、手机验证码验证
     * phone	是	string	手机号
     * districeId	是	int	手机号归属地id
     * code	是	string	验证码
     *
     * @param phone
     * @param districeId
     */
    private void registerCodeverify(final String phone, final String districeId, String code) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).registerCodeverify(phone, districeId, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<String>>(this, null) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
                            Intent intent = new Intent(mContext, SetLoginPassActivity.class);
                            intent.putExtra("edtPhone", edtPhone);
                            intent.putExtra("districeId", districeId);
                            startActivity(intent);

                            finish();
                        } else {
                            ToastUtil.showToast(AuthCodeActivity.this, "验证码输入不正确，请重新输入");
                            editSecurityCode.clearEditText();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    /**
     * 注册获取验证码
     * phone	是	string	手机号
     * districeId	是	int	手机号所属地区id
     *
     * @param phone
     * @param districeId
     */
    private void registerSendCode(final String phone, final String districeId) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).registerSendCode(phone, districeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<String>>(this, null) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
//                            {"msg":"发送成功","code":200,"data":""}
                            verificationCode = baseBean.data;
                            SendSmsTimerUtils.sendSms(tvGetCode, R.color.fffColor, R.color.fffColor,1);
                        } else {
                            ToastUtil.showToast(AuthCodeActivity.this, baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }
}

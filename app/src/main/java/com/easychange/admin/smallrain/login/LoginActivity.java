package com.easychange.admin.smallrain.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.BalloonActivity;
import com.easychange.admin.smallrain.activity.ChoosePhoneHomeLocationActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.SendSmsTimerUtilsRedColor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.common.Constants;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.StringUtil;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import bean.FinishLoginBean;
import bean.PhoneIsRegisterBean;
import bean.PhoneIsRegisterDataBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import event.LoginTypeEvent;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenlipeng on 2018/10/30 0030
 * describe:  登录
 * 	支持两种登录方式：普通登陆和快捷登陆。
 * 	普通登陆逻辑如下：
 * 1.请输入手机号：
 * 1.1允许用户仅输入11位数字，格式自动为“135 1010 1010（中间用空格隔开）”，空格由前端设置，用户无需输入空格。
 * 1.2出现非法输入将直接弹出输入错误前端提示；
 * 1.3手机号满足11位，但是数据库不存在，提示：“用户不存在，请先注册”
 * 1.4手机号输入时使用数字键盘。
 * 2.请输入登录密码：
 * 2.1只允许输入6-13位，多余位数不再显示。
 * 3.点击忘记密码？进入忘记密码页面。（右图）
 * 4.点击登录按钮：
 * 4.1判断手机号与密码是否匹配，匹配登录成功，进入儿童训练首页；
 * 4.2不匹配，显示“用户名与密码不一致，请重新输入”，此时手机号和密码均可修改。
 * 5.点击“立即注册”进入注册页面。
 * 6.点击退出返回注册页。
 * 7.点击快捷登陆，进入快捷登陆页面。
 */
public class LoginActivity extends BaseActivity implements AsyncRequest {//联网要实现这个接口


    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.tv_pt_login)
    TextView tvPtLogin;
    @BindView(R.id.view_pt_login)
    View viewPtLogin;
    @BindView(R.id.layout_pt_login)
    LinearLayout layoutPtLogin;
    @BindView(R.id.tv_kj_login)
    TextView tvKjLogin;
    @BindView(R.id.view_kj_login)
    View viewKjLogin;
    @BindView(R.id.layout_kj_login)
    LinearLayout layoutKjLogin;
    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @BindView(R.id.layout_pt_content)
    LinearLayout layoutPtContent;
    @BindView(R.id.edt_kj_number)
    EditText edtKjNumber;
    @BindView(R.id.edt_kj_code)
    EditText edtKjCode;
    @BindView(R.id.img_get_code)
    ImageView imgGetCode;
    @BindView(R.id.tv_kj_sure_login)
    TextView tvKjSureLogin;
    @BindView(R.id.tv_kj_register)
    TextView tvKjRegister;
    @BindView(R.id.edt_input_message_authentication_code)
    EditText edtInputMessageAuthenticationCode;
    @BindView(R.id.tv_get_sms_code_qj)
    TextView tvGetSmsCodeQj;
    @BindView(R.id.ll_get_verification_code)
    LinearLayout llGetVerificationCode;
    @BindView(R.id.ll_qj_get_sms_code)
    LinearLayout llQjGetSmsCode;
    @BindView(R.id.layout_kj_content)
    LinearLayout layoutKjContent;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.tv_get_belonging_to_code)
    TextView tvGetBelongingToCode;
    @BindView(R.id.tv_get_belonging_to_code_kj)
    TextView tvGetBelongingToCodeKj;
    @BindView(R.id.ll_my_image_code)
    LinearLayout llMyImageCode;

    private String districeId = "1";
    private String rightCode = "";
    private boolean verifyCode = true;
    private boolean forbidDoubleClick = false ;
    private boolean iskjphone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            indexPhone(phone);

            register.setVisibility(View.VISIBLE);
            layoutPtContent.setVisibility(View.GONE);
            layoutKjContent.setVisibility(View.VISIBLE);
            viewPtLogin.setVisibility(View.INVISIBLE);
            viewKjLogin.setVisibility(View.VISIBLE);
            tvPtLogin.setTextColor(Color.parseColor("#000000"));
            tvKjLogin.setTextColor(Color.parseColor("#c06d00"));
        }
        authImageService();
        EventBusUtil.register(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    /**
     * 手机显示格式化
     *
     * @param s
     */
    public void indexPhone(String s) {

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
        edtKjNumber.setText(sb.toString());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GotoQuestionnaireBean(FinishLoginBean event) {
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginType(LoginTypeEvent loginTypeEvent) {

    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();

    }

    private void initViews() {
        edtInputMessageAuthenticationCode.addTextChangedListener(new TextWatcher() {//请输入短信验证码
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!TextUtils.isEmpty(s) && s.length() == 6) {
                    if (verifyCode) {
                        Log.e("数据", s.toString());
                        String trim = edtKjNumber.getText().toString().trim();
                        String currentKjPhone = trim.replace(" ", "");
                        efficacyCode(currentKjPhone, districeId, s);
                    } else {
                        Log.i("liubiao", "afterTextChanged: "+s+"==="+rightCode);
                        if (rightCode.equals(s)) {
                            tvKjSureLogin.setTextColor(getResources().getColor(R.color.white));
                            tvKjSureLogin.setEnabled(true);
                        } else {
                            tvKjSureLogin.setTextColor(getResources().getColor(R.color.textgrey));
                            tvKjSureLogin.setEnabled(false);
                            Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    tvKjSureLogin.setTextColor(getResources().getColor(R.color.textgrey));
                    tvKjSureLogin.setEnabled(false);
                }
            }
        });

        edtPhoneNumber.addTextChangedListener(new TextWatcher() {//普通的手机号
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == PHONE_INDEX_4 || sb.length() == PHONE_INDEX_9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }

                    edtPhoneNumber.setText(sb.toString());
                    edtPhoneNumber.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                String currentKjPhone = trim.replace(" ", "");
//用户不存在，请先注册
                if (!TextUtils.isEmpty(currentKjPhone)){
                    phoneIsRegister(currentKjPhone, districeId);
                }
            }
        });
        /*edtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hasFocus=false;
                }
                if(!hasFocus){

               }
            }
        });*/

        edtKjNumber.addTextChangedListener(new TextWatcher() {//快速的手机号
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == PHONE_INDEX_4 || sb.length() == PHONE_INDEX_9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }

                    edtKjNumber.setText(sb.toString());
                    edtKjNumber.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
           /*     String trim = editable.toString().trim();
                String currentKjPhone = trim.replace(" ", "");


                if("+86".equals(tvGetBelongingToCode.getText().toString().trim())) {
                    if (currentKjPhone.length() != 11) {
                    return;
                }

//                用户不存在，请先注册
                if (MyUtils.CheckPhoneNum(LoginActivity.this, currentKjPhone)) {
                    phoneIsRegisterQj(currentKjPhone, districeId);
                } else {
                    ToastUtil.showToast(mContext, "手机号格式错误");//手机号格式错误
                }
                }else {
                    if (!TextUtils.isEmpty(currentKjPhone)){
                        phoneIsRegisterQj(currentKjPhone, districeId);
                    }
                }

//*/
            }
        });
        edtKjCode.addTextChangedListener(new TextWatcher() {//快捷的图片验证码
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                2.1 先输入数字字母验证码，输入正确后此栏变成输入短信验证码，样式如上图页面下方展示。如数字字母验证码输入错误，前端提示：“验证码输入错误”。
                String trim = edtKjNumber.getText().toString().trim();
                String currentKjPhone = trim.replace(" ", "");

                if (TextUtils.isEmpty(currentKjPhone)){
                    ToastUtil.showToast(LoginActivity.this,"请先输入手机号");
                }else {
                    phoneIsRegisterQj(currentKjPhone, districeId);
                }
                String s = editable.toString();
                if (!TextUtils.isEmpty(s) && s.length() >= 4&&iskjphone) {
                    verifyimageCode(s);
                }


            }
        });
    }

    public boolean yetRegister = false;//是否已经注册了

    /**
     * 判断该手机号是否已注册
     * phone	是	string	用户名
     * districeId	是	int	手机号归属地id
     *
     * @param phone
     * @param districeId
     */
    private void phoneIsRegister(final String phone, final String districeId) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).phoneIsRegister(phone, districeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<PhoneIsRegisterBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<PhoneIsRegisterBean> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {

                            PhoneIsRegisterBean data = baseBean.data;
                            boolean isRegister = data.isIsRegister();
                            if (isRegister) {//已经注册了
                                yetRegister = true;
                                tvLogin.setEnabled(true);
//                                输入手机号后，登录按钮应该点亮
                                tvLogin.setTextColor(Color.parseColor("#FFFFFF"));
                            } else {
                                tvLogin.setEnabled(false);
                                tvLogin.setTextColor(Color.parseColor("#b5ada5"));

                                ToastUtil.showToast(LoginActivity.this, baseBean.msg);

                            }
                        } else {
                            ToastUtil.showToast(LoginActivity.this, baseBean.msg + "");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        //ToastUtil.showToast(LoginActivity.this,   "手机号码格式不正确");
                    }
                });
    }

    private void phoneIsRegisterQj(final String phone, final String districeId) {
        String url = Setting.phoneIsRegister();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("phone", phone);//
        stringStringHashMap.put("districeId", districeId);//

        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(1222)//XX接口的标识   这个标志可以共用的
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    /**
     * s* @param requestCode
     *
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            String phonePrefix = data.getStringExtra("phonePrefix");
            String cityId = data.getStringExtra("cityid");
            edtPhoneNumber.getText().clear();
            edtKjNumber.getText().clear();
            districeId = cityId;
            new PreferencesHelper(LoginActivity.this).saveSmsCode(phonePrefix);

            int visibility = layoutPtContent.getVisibility();
            if (visibility == View.VISIBLE) {
                tvGetBelongingToCode.setText("+" + phonePrefix);
            } else {
                tvGetBelongingToCodeKj.setText("+" + phonePrefix);
            }

        }


        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String phone = data.getStringExtra("phone");
            edtKjNumber.setText(phone);

            layoutPtContent.setVisibility(View.GONE);
            layoutKjContent.setVisibility(View.VISIBLE);
            viewPtLogin.setVisibility(View.INVISIBLE);
            viewKjLogin.setVisibility(View.VISIBLE);
            tvPtLogin.setTextColor(Color.parseColor("#000000"));
            tvKjLogin.setTextColor(Color.parseColor("#c06d00"));
        }
    }


    /**
     * @param view
     */
    @OnClick({R.id.tv_get_belonging_to_code_kj, R.id.tv_get_belonging_to_code, R.id.layout_pt_login, R.id.layout_kj_login, R.id.tv_login, R.id.tv_register, R.id.tv_forget_pass, R.id.img_home_right
            , R.id.img_get_code, R.id.tv_kj_sure_login, R.id.tv_kj_register, R.id.tv_get_sms_code_qj, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_belonging_to_code_kj:
            case R.id.tv_get_belonging_to_code:
                startActivityForResult(new Intent(getApplicationContext(), ChoosePhoneHomeLocationActivity.class), 111);
                break;
            case R.id.register:
                startActivityForResult(new Intent(mContext, RegisterActivity.class), 1);
                break;
            case R.id.layout_pt_login:
                edtKjNumber.setOnFocusChangeListener(null);
                register.setVisibility(View.GONE);
                layoutPtContent.setVisibility(View.VISIBLE);
                layoutKjContent.setVisibility(View.GONE);
                viewPtLogin.setVisibility(View.VISIBLE);
                viewKjLogin.setVisibility(View.INVISIBLE);
                tvPtLogin.setTextColor(Color.parseColor("#c06d00"));
                tvKjLogin.setTextColor(Color.parseColor("#000000"));
                        edtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(!hasFocus){
                                    String trim = edtPhoneNumber.getText().toString().trim();
                                    String currentKjPhone = trim.replace(" ", "");
                                    if (!TextUtils.isEmpty(currentKjPhone)){
                                        phoneIsRegister(currentKjPhone, districeId);
                                    }
                                }
                            }
                        });
                break;
            case R.id.layout_kj_login:
                edtPhoneNumber.setOnFocusChangeListener(null);
                register.setVisibility(View.VISIBLE);
                layoutPtContent.setVisibility(View.GONE);
                layoutKjContent.setVisibility(View.VISIBLE);
                viewPtLogin.setVisibility(View.INVISIBLE);
                viewKjLogin.setVisibility(View.VISIBLE);
                tvPtLogin.setTextColor(Color.parseColor("#000000"));
                tvKjLogin.setTextColor(Color.parseColor("#c06d00"));


                edtKjNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(!hasFocus){
                                    String trim = edtKjNumber.getText().toString().trim();
                                    String currentKjPhone = trim.replace(" ", "");
                                    if (!TextUtils.isEmpty(currentKjPhone)){
                                        phoneIsRegister(currentKjPhone, districeId);
                                    }
                                }
                            }
                        });
                break;
            case R.id.tv_login://普通登录
//                4.点击登录按钮：
//                4.1判断手机号与密码是否匹配，匹配登录成功，进入儿童训练首页；
//                4.2不匹配，显示“用户名与密码不一致，请重新输入”，此时手机号和密码均可修改。
//                if (isCommonType) {
                String phone = edtPhoneNumber.getText().toString();
                String currentPhone = phone.replace(" ", "");

                String password = edtPass.getText().toString();

                if (!StringUtil.isEmpty(currentPhone) && StringUtil.isEmpty(password)) {
                    ToastUtil.showToast(mContext, "用户名与密码不一致，请重新输入");
                    return;
                }
                if (password.length() < 6 || password.length() > 13) {
                    ToastUtil.showToast(mContext, "允许输入6-13位数字或字母或数字和字母组合");
                    return;
                }

                if (!yetRegister) {
                    ToastUtil.showToast(mContext, "手机号码没有注册");
                    return;
                }
                if (forbidDoubleClick){
                    return;
                }
                view.setEnabled(false);
                generalLogin(currentPhone, password, districeId);
                break;
            case R.id.tv_register:
                startActivityForResult(new Intent(mContext, RegisterActivity.class), 1);
                break;
            case R.id.tv_forget_pass://忘记密码
                startActivity(new Intent(mContext, ForgetPassActivity.class));
                break;
            case R.id.img_home_right:
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.img_get_code:

                authImageService();
                break;
            case R.id.tv_kj_sure_login://快速登录

                String phone1 = edtKjNumber.getText().toString();
                String currentKjPhone = phone1.replace(" ", "");

                String edtInputMessageAuthenticationCode1 = edtInputMessageAuthenticationCode.getText().toString();

                if (MyUtils.CheckPhoneNum(LoginActivity.this, currentKjPhone))
                    if (!TextUtils.isEmpty(rightCode) && rightCode.equals(edtInputMessageAuthenticationCode1)) {
                        shortcutLogin(currentKjPhone, edtInputMessageAuthenticationCode1, districeId);
                    } else {
                        ToastUtil.showToast(LoginActivity.this, "请输入正确的验证码");
                    }

                break;
            case R.id.tv_kj_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;

            case R.id.tv_get_sms_code_qj://快捷登录获取验证码
                String phone11 = edtKjNumber.getText().toString();
                String currentKjPhone1 = phone11.replace(" ", "");

                if (MyUtils.CheckPhoneNum(LoginActivity.this, currentKjPhone1)) {
                    shortcutLoginSend(currentKjPhone1, districeId);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 快捷登录
     * phone	是	string	手机号
     * code	是	string	验证码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @param phone
     * @param code
     * @param qcellcoreId
     */

    private void shortcutLogin(final String phone, final String code, String qcellcoreId) {
        String url = Setting.shortcutLogin();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("phone", phone);//
        stringStringHashMap.put("code", code);//
        stringStringHashMap.put("qcellcoreId", qcellcoreId);//


        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(1)//XX接口的标识   这个标志可以共用的
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    /**
     * 快捷登录图片验证码效验
     */
    private void authImageService() {
        new Thread(new Runnable() {

            @Override
            public void run() {//设置名字3：如果实现Runnable接口，先获取当前线程对象Thread.currentThread()
                String url = Constants.HOST+"app/authImage/service";
                try {
                    ResponseBody body = OkHttpUtils
                            .get()
                            .url(url)//接口地址
                            .id(3)//XX接口的标识
                            .build()
                            .execute().body();

                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);

                    (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                        @Override
                        public void run() {
                            imgGetCode.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 快捷登录图片验证码效验
     *
     * @param imageCode
     */
    private void verifyimageCode(String imageCode) {
        String url = Setting.verifyimageCode();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("imageCode", imageCode);//
        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    /**
     * 普通登录
     *
     * @param phone
     * @param password
     * @param qcellcoreId
     */
    private void generalLogin(final String phone, final String password, String qcellcoreId) {
        String url = Setting.generalLogin();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("phone", phone);
        stringStringHashMap.put("password", password);
        stringStringHashMap.put("qcellcoreId", qcellcoreId);//

        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    /**
     * 手机号code验证
     * phone	是	string	手机号
     * code	是	string	验证码
     * districeId	是	int	手机号归属地id
     *
     * @param phone
     * @param districeId
     */
    private void efficacyCode(final String phone, final String districeId, String code) {
        String url = Setting.efficacyCode();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("phone", phone);
        stringStringHashMap.put("code", code);
        stringStringHashMap.put("districeId", districeId);

        Log.e("数据", stringStringHashMap.toString());

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(33333)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     *               <p>
     *               {
     *               //                                "msg":"登录成功！", "code":200, "data":{
     *               //                                "IsRemind":"1", "parents":{
     *               //                                    "id":16, "nickname":null, "age":null, "sex":null, "qcellcoreId":
     *               //                                    1, "phoneNumber":"18210182462", "password":null, "createTime":
     *               //                                    1541039924000, "childId":null, "updateTime":null, "status":
     *               //                                    "1", "token":"Z2yIupsoZ9scMMVTlmCQ==", "city":
     *               //                                    null, "phonePrefix":null
     *               //                                }
     *               //                            }
     *               //                            }
     *               //                            {"msg":"密码不正确，请重新输入！","code":201,"data":""}
     *               {"msg":"登录成功！","code":200,"data":{"chilName":"哈哈哈","chilSex":"1",
     *               "chilPhoto":"http://yuudee.oss-cn-beijing.aliyuncs.com/89138b709ea34cd7a9f11796711caddf.temp",
     *               "IsRemind":"2","parents":{"id":16,"nickname":null,"age":null,"sex":null,"qcellcoreId":1,
     *               "phoneNumber":"18210182462","password":null,"createTime":1541039924000,"childId":5,
     *               "updateTime":1541384303000,"status":"1","token":"sREzvlBLquwEgAIoK546Xw==","feel":"1","city":null,
     *               "phonePrefix":null}}}
     */
    @Override
    public void RequestComplete(Object object, Object data) {


        if (object.equals(33333)) {//标记那个接口 手机号码验证

            String result = (String) data;
            (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");
                        Log.e("myLoginActivity", code);
                        if (code.equals("200")) {
                            rightCode = edtInputMessageAuthenticationCode.getText().toString();
                            verifyCode = false;
                            tvKjSureLogin.setTextColor(getResources().getColor(R.color.white));
                            tvKjSureLogin.setEnabled(true);
                        } else {
                            tvKjSureLogin.setTextColor(getResources().getColor(R.color.textgrey));
                            tvKjSureLogin.setEnabled(false);
                            ToastUtil.showToast(mContext, msg1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(1222)) {//标记那个接口  是否注册过了

            String result = (String) data;
            (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            PhoneIsRegisterDataBean model = gson.fromJson(result,
                                    new TypeToken<PhoneIsRegisterDataBean>() {
                                    }.getType());

                            boolean isRegister = model.getData().isIsRegister();
                            if (isRegister) {//已经注册了
                                iskjphone = true;
                            } else {
                                ToastUtil.showToast(mContext, msg1);
                            }

                        } else {
                            ToastUtil.showToast(mContext, msg1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(1)) {//标记那个接口  快捷登录

            String result = (String) data;
            (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data").getJSONObject("parents");

                            PreferencesHelper helper = new PreferencesHelper(LoginActivity.this);
                            helper.saveIsRemind(jsonObject.getJSONObject("data").getString("IsRemind"));
                            helper.saveToken(jsonObject1.getString("token"));
                            Log.e("token", jsonObject1.getString("token"));
                            helper.saveQcellCoreId(jsonObject1.getString("qcellcoreId"));
                            helper.savePhoneNum(jsonObject1.getString("phoneNumber"));
                            helper.saveUserInfo(jsonObject.getJSONObject("data").toString());


                            helper.savePhto(jsonObject.getJSONObject("data").getString("chilPhoto"));
                            helper.saveString("sp", "nickname",
                                    jsonObject.getJSONObject("data").getString("chilName"));
                            if (!forbidDoubleClick) {
                                forbidDoubleClick = true ;
                                ToastUtil.showToast(LoginActivity.this, "登录成功");
                                Intent intent = new Intent();
                                intent.setClass(mContext,BalloonActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                tvLogin.setEnabled(true);
                                LoginActivity.this.finish();
                            }
                        } else {
//                            用户名与密码不一致，请重新输入
                            tvLogin.setEnabled(true);
                            ToastUtil.showToast(LoginActivity.this, "用户名与密码不一致，请重新输入");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvLogin.setEnabled(true);
                        Log.e("数据", e.toString());
                    }

                }
            });
        }
        if (object.equals(2)) {//标记那个接口   图片验证码

            String result = (String) data;
            (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        ToastUtil.showToast(LoginActivity.this, msg1);
                        if (code.equals("200")) {

                            llMyImageCode.setVisibility(View.GONE);
                            llGetVerificationCode.setVisibility(View.VISIBLE);
                            String phone11 = edtKjNumber.getText().toString();
                            String currentKjPhone1 = phone11.replace(" ", "");

                            if (MyUtils.CheckPhoneNum(LoginActivity.this, currentKjPhone1)) {
                                shortcutLoginSend(currentKjPhone1, districeId);
                            }

                        } else {
                            llMyImageCode.setVisibility(View.VISIBLE);
                            llGetVerificationCode.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(3)) {//标记那个接口

            String result = (String) data;
            (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        ToastUtil.showToast(LoginActivity.this, msg1);
                        if (code.equals("200")) {
                            edtInputMessageAuthenticationCode.getText().clear();
                            verifyCode = true ;
                            SendSmsTimerUtilsRedColor.sendSms(tvGetSmsCodeQj, R.color.c06d00, R.color.c06d00);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    /**
     * 快捷登录获取验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机归属地id
     *
     * @param phone
     * @param qcellcoreId
     */
    private void shortcutLoginSend(final String phone, String qcellcoreId) {
        String url = Setting.shortcutLoginSend();

        OkHttpUtils
                .get()
                .addParams("phone", phone)
                .addParams("qcellcoreId", qcellcoreId)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(LoginActivity.this, this));
    }

    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {

        (LoginActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(LoginActivity.this, errorMessage);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void test() {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        i++;
        j++;
        k++;
        l++;
        i--;
        j--;
        k--;
        l--;
        System.out.println(i);
        System.out.println(j);
        System.out.println(k);
        System.out.println(l);
    }
}

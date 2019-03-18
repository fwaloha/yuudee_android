package com.easychange.admin.smallrain.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.ChoosePhoneHomeLocationActivity;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.SendSmsTimerUtils;
import com.easychange.admin.smallrain.utils.SendSmsTimerUtilsRedColor;
import com.easychange.admin.smallrain.views.SecurityCodeView;
import com.easychange.admin.smallrain.views.VerificationSeekBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import bean.PhoneIsRegisterDataBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;

/**
 * Created by chenlipeng on 2018/10/30 0030
 * describe:  忘记密码  过
 */
public class ForgetPassActivity extends BaseActivity implements AsyncRequest {

    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.seekbar_sb)
    VerificationSeekBar seekbarSb;
    @BindView(R.id.seekbar_tv)
    TextView seekbarTv;
    @BindView(R.id.seekbar_rlyt)
    RelativeLayout seekbarRlyt;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_sure_pass)
    EditText edtSurePass;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    // 特殊下标位置

    @BindView(R.id.v_floating_in_the_above)
    View vFloatingInTheAbove;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.ll_verification_code)
    RelativeLayout llVerificationCode;
    @BindView(R.id.tv_get_city_code)
    TextView tvGetCityCode;
    @BindView(R.id.edit_security_code)
    EditText editSecurityCode;
    private String districeId = "1";
    private String rightCode = "";
    private boolean verifyCode = true;

    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;

    //    没有滑动滑块框颜色为棕色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        editSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editSecurityCode.getText().toString();

                if (!TextUtils.isEmpty(s) && s.length() == 6) {
                    String trim = edtPhoneNumber.getText().toString().trim();
                    String currentKjPhone = trim.replace(" ", "");
                    if (verifyCode) {
                        efficacyCode(currentKjPhone, districeId, s);
                    } else {
                        if (rightCode.equals(s)) {
                            tvSuccess.setTextColor(Color.parseColor("#fffdec"));
                            tvSuccess.setEnabled(true);
                        } else {
                            tvSuccess.setTextColor(Color.parseColor("#b5ada5"));
                            tvSuccess.setEnabled(false);
                            ToastUtil.showToast(ForgetPassActivity.this, "请输入正确验证码");
                        }
                    }
                } else {
                    tvSuccess.setTextColor(Color.parseColor("#b5ada5"));
                    tvSuccess.setEnabled(false);
                }
            }
        });

        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
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
                String trim = edtPhoneNumber.getText().toString().trim();
                String currentKjPhone = trim.replace(" ", "");
//                用户不存在，请先注册
                if (currentKjPhone.length() == 11) {

                    boolean b = MyUtils.CheckPhoneNum(ForgetPassActivity.this, currentKjPhone);
                    if (b) {
                        phoneIsRegister(currentKjPhone, districeId);
                    } else {
                        ToastUtil.showToast(ForgetPassActivity.this, "输入有误");
                        vFloatingInTheAbove.setVisibility(View.VISIBLE);
                    }
                } else {
                    vFloatingInTheAbove.setVisibility(View.VISIBLE);
                }

            }
        });
        seekbarSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {  //滑块正在滑动过程中的监听
                if (progress >= 85) {       //滑动动作完成，设置滑块的图片是对号的图片
                    seekbarSb.setThumb(getResources().getDrawable(R.drawable.thumb_right));
                } else if (progress <= 15) {          //滑动动作没有完成，设置滑块的图片是箭头的图片
                    seekbarSb.setThumb(getResources().getDrawable(R.drawable.thumb_left));
                } else {
                    seekbarSb.setThumb(getResources().getDrawable(R.drawable.center_image_));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {   //滑块准备滑动前的监听
//                seekBar.setThumbOffset(0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() < 90) {
                    seekBar.setProgress(11);

                    seekbarTv.setVisibility(View.VISIBLE);
                    seekbarTv.setText("向右拖动滑块验证");
                    seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_left));
                } else {
                    seekBar.setProgress(92);
                    seekbarTv.setVisibility(View.VISIBLE);

                    seekbarTv.setText("验证成功");
                    seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_right));

                    String trim = edtPhoneNumber.getText().toString().trim();
                    String currentKjPhone = trim.replace(" ", "");

                    sendCode(currentKjPhone, districeId);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            String phonePrefix = data.getStringExtra("phonePrefix");
            String cityId = data.getStringExtra("cityid");
            tvGetCityCode.setText("+" + phonePrefix + " ");
            districeId = cityId;
        }
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

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(33333)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ForgetPassActivity.this, this));
    }

    /**
     * 判断该手机号是否已注册
     * phone	是	string	用户名
     * districeId	是	int	手机号归属地id
     *
     * @param phone
     * @param districeId
     */
    private void phoneIsRegister(final String phone, final String districeId) {
        String url = Setting.phoneIsRegister();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("phone", phone);
        stringStringHashMap.put("districeId", districeId);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(22)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ForgetPassActivity.this, this));
    }

    /**
     * 发送验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机号归属地id
     * type	是	string	发送类型(1：忘记密码 2：修改密码 3：更换手机号旧发送验证码 4:更换手机号新手机发送验证码)
     *
     * @param phone
     * @param qcellcoreId
     */
    private void sendCode(final String phone, final String qcellcoreId) {

        String url = Setting.sendCode();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("phone", phone);
        stringStringHashMap.put("qcellcoreId", qcellcoreId);
        stringStringHashMap.put("type", "1");//

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ForgetPassActivity.this, this));
    }

    /**
     * 忘记密码
     * phone	是	string	手机号
     * password	是	string	新密码
     * code	是	string	验证码
     *
     * @param phone
     * @param password
     * @param code
     */
    private void resetPassword(final String phone, final String password, final String code) {

        String url = Setting.resetPassword();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("phone", phone);
        stringStringHashMap.put("password", password);
        stringStringHashMap.put("code", code);//

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ForgetPassActivity.this, this));
    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(33333)) {//标记那个接口 手机号码验证

            String result = (String) data;
            (ForgetPassActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            verifyCode = false;
                            rightCode = editSecurityCode.getText().toString().trim();
                            tvSuccess.setTextColor(Color.parseColor("#fffdec"));
                            tvSuccess.setEnabled(true);
                        } else {
                            tvSuccess.setTextColor(Color.parseColor("#b5ada5"));
                            tvSuccess.setEnabled(false);
                            ToastUtil.showToast(ForgetPassActivity.this, msg1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(22)) {//判断该手机号是否已注册

            String result = (String) data;
            (ForgetPassActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
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
                                vFloatingInTheAbove.setVisibility(View.GONE);

                            } else {
                                vFloatingInTheAbove.setVisibility(View.VISIBLE);
                                ToastUtil.showToast(ForgetPassActivity.this, "不存在此手机号，请先注册");
                            }
                        } else {
                            vFloatingInTheAbove.setVisibility(View.VISIBLE);
                            ToastUtil.showToast(ForgetPassActivity.this, msg1 + "");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(1)) {//标记那个接口  发送验证码

            String result = (String) data;
            (ForgetPassActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            SendSmsTimerUtilsRedColor.sendSms(tvGetCode, R.color.c06d00, R.color.c06d00);
                            verifyCode = true ;
                            editSecurityCode.getText().clear();
                            seekbarRlyt.setVisibility(View.GONE);
                            llVerificationCode.setVisibility(View.VISIBLE);
                        } else {
                        }
                        ToastUtil.showToast(ForgetPassActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//标记那个接口  忘记密码

            String result = (String) data;
            (ForgetPassActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//                            进入密码修改成功页面
                            Intent intent1 = new Intent(ForgetPassActivity.this, PassWordChangeSuccessActivity.class);
                            startActivity(intent1);

                            finish();
                        } else {
                        }
                        ToastUtil.showToast(ForgetPassActivity.this, msg1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {

        (ForgetPassActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(ForgetPassActivity.this, errorMessage);
            }
        });

    }

    /**
     * @param view
     */
    @OnClick({R.id.tv_success, R.id.img_home_right, R.id.tv_get_code, R.id.tv_get_city_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_city_code:
                startActivityForResult(new Intent(getApplicationContext(), ChoosePhoneHomeLocationActivity.class), 111);
                break;
//            验证码输入正确，登录按钮点亮可点击
//            验证码输入错误或为空，登录按钮置灰不可点击
            case R.id.tv_success:
//                密码输入是否一致，如不一致，显示‘两次密码输入不一致；如一致，点击确认提示文案‘密码重置成功’，然后返回普通登录页面。

                String verificationCode = editSecurityCode.getText().toString();
                if (TextUtils.isEmpty(verificationCode)) {
                    ToastUtil.showToast(ForgetPassActivity.this, "验证码为空");
                    return;
                }

                if (TextUtils.isEmpty(edtPass.getText().toString())) {
                    ToastUtil.showToast(mContext, "请输入密码");
                } else if (TextUtils.isEmpty(edtSurePass.getText().toString())) {
                    ToastUtil.showToast(mContext, "请输入确认密码");
                } else if (!edtSurePass.getText().toString().equals(edtPass.getText().toString())) {
                    ToastUtil.showToast(mContext, "两次密码输入不一致");
                } else {

                    if ((edtPass.length()) < 6 || edtPass.length() > 13) {
                        ToastUtil.showToast(mContext, "请输入6-13位数字或字母组合");
                        return;
                    }
                    if ((edtSurePass.length()) < 6 || edtSurePass.length() > 13) {
                        ToastUtil.showToast(mContext, "请输入6-13位数字或字母组合");
                        return;
                    }

                    String phoneNum = edtPhoneNumber.getText().toString();
                    String currentKjPhone = phoneNum.replace(" ", "");

                    String password = edtPass.getText().toString();
                    resetPassword(currentKjPhone, password, verificationCode);
                }

                break;
            case R.id.img_home_right:
                finish();
                break;
            case R.id.tv_get_code:

                String phoneNum = edtPhoneNumber.getText().toString();
                String currentKjPhone = phoneNum.replace(" ", "");
//                按钮点亮
                sendCode(currentKjPhone, districeId);
                break;

        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

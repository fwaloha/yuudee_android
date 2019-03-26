package com.easychange.admin.smallrain.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.base.BaseDialog;
import com.easychange.admin.smallrain.login.LoginActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.SendSmsTimerUtils;
import com.easychange.admin.smallrain.utils.SoftKeyBoardListener;
import com.easychange.admin.smallrain.views.SecurityCodeView;
import com.easychange.admin.smallrain.views.VerificationSeekBar;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.xw.repo.fillblankview.FillBlankView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import bean.ExitLoginBean;
import bean.PhoneIsRegisterBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import event.UpdatePhoneEvent;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create 2018/10/18 0018
 * by 李
 * 页面注释：修改手机号
 **/
public class EditDataActivity extends BaseActivity implements TextWatcher, AsyncRequest {
    private String TAG = "EditDataActivity";
    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;

    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.img_home_left)
    ImageView left;
    @BindView(R.id.img_home_right)
    ImageView right;

    private int type = 0;
    private View view;
    private TextView tvSure;
    private EditText one;
    private EditText two;
    private EditText three;
    private SecurityCodeView etCode;

    private Intent intent;
    private EditText myPhone;
    private TextView tv_describe;
    private TextView tv_get_code;
    private EditText myPhoneCode;
    private EditText newPhone;
    private PreferencesHelper helper;
    private String currentLoginPhoneNum;
    private String newDistriceId = "1";
    private String rightCode = "";
    private String messageHint = "验证码输入错误";
    private boolean verifyCode = true;
    private TextView currentGetVerifyCode;
    private boolean keyBoardIsShow = false;
    private BaseDialog dialog;
    private TextView tvLocation;
    private ImageView ivLocation;
    private boolean isNewPhoneVerify = false;
    private TextView tvGetStatesCode;
    //    更换手机号页面逻辑：
//            1.请输入当前账号绑定手机号：下方方框中展示原注册手机号，但隐藏中间四位，用户可点击输入此手机号。具体输入手机号逻辑同之前注册输入手机号逻辑。
//            2.输入手机号符合要求，验证按钮点亮可点，点击验证按钮，判断此手机号是否与原注册绑定手机号一致，一致直接发送验证码到此手机号；不一致，提示文案“已绑定手机号输入错误，请重新输入”。用户可重新输入手机号，逻辑同上。
//            3.发送验证码后开始验证，显示60s倒计时，倒计时完毕显示重新获取。用户输入验证码后，验证按钮可点，点击验证判断验证码输入正确与否，错误提示文案‘验证错误’，验证码栏可修改；验证正确，文案提示’验证正确‘。
//            4.原注册手机号验证码验证正确后进入输入新的手机号页面，输入新的手机号逻辑同注册时输入手机号逻辑。如果用户输入的手机号之前在本产品注册绑定过，弹窗提示如图。
// 点击继续，原绑定账号自动解绑，点取消，即可取消弹窗，可以重新输入手机号。输入手机号后进行验证码验证，验证通过，进入结果页-更换手机号成功。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        ButterKnife.bind(this);

        helper = new PreferencesHelper(EditDataActivity.this);

        type = getIntent().getIntExtra("TYPE", 0);
        initView(type);
        initEvent();

        SoftKeyBoardListener.setListener(EditDataActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                keyBoardIsShow = true;
            }

            @Override
            public void keyBoardHide(int height) {
                keyBoardIsShow = false;
            }
        });


    }

    private void initView(int type) {
        parentLayout.setPadding(0, 0, 0, 0);
        switch (type) {
            case 2:
                currentLoginPhoneNum = helper.getPhoneNum();

//              1821 0182 462
                view = View.inflate(this, R.layout.layout_edit_phone_one, null);
                parentLayout.addView(view);
                tvSure = parentLayout.findViewById(R.id.tv_sure);

                myPhone = (EditText) parentLayout.findViewById(R.id.et_one);
                tvGetStatesCode = ((TextView) parentLayout.findViewById(R.id.tv_get_belonging_to_code));
                tvGetStatesCode.setText("+"+new PreferencesHelper(EditDataActivity.this).getSmsCode());

                tvGetStatesCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(getApplicationContext(), ChoosePhoneHomeLocationActivity.class), 222);
                    }
                });

                myPhone.addTextChangedListener(new TextWatcher() {//普通的手机号
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
                            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                                continue;
                            } else {
                                sb.append(s.charAt(i));
                                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
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

                            myPhone.setText(sb.toString());
                            myPhone.setSelection(index);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String trim = myPhone.getText().toString().trim();
                        String currentKjPhone = trim.replace(" ", "");
                        if (currentLoginPhoneNum.equals(currentKjPhone)) {
                            tvSure.setEnabled(true);
                            tvSure.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            tvSure.setEnabled(false);
                            tvSure.setTextColor(getResources().getColor(R.color.textgrey));
                            if (currentKjPhone.length() == 11) {
                                ToastUtil.showToast(EditDataActivity.this, "当前绑定的手机号输入错误");
                            }
                        }

                        Log.e("phone", currentLoginPhoneNum);
                        Log.e("phone", currentKjPhone);

                    }
                });
                tvSure.setOnClickListener(onClickListener);
                break;
            case 3://更换手机号

                currentLoginPhoneNum = helper.getPhoneNum();

                view = View.inflate(this, R.layout.layout_edit_phone_two, null);
                parentLayout.addView(view);
                tvSure = parentLayout.findViewById(R.id.tv_sure);
                myPhoneCode = (EditText) findViewById(R.id.et_one);
                myPhoneCode.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String trim = editable.toString().trim();
                        if (trim.length() == 6) {
                            if (verifyCode) {
                                Log.e(TAG, "trimtrue" + trim);
                                efficacyCode(currentLoginPhoneNum, newDistriceId, trim);
                            } else {
                                if (rightCode.equals(trim)) {
                                    tvSure.setEnabled(true);
                                    tvSure.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    tvSure.setEnabled(false);
                                    tvSure.setTextColor(getResources().getColor(R.color.textgrey));
                                    Log.e(TAG, "trimfalse" + messageHint);
                                    ToastUtil.showToast(EditDataActivity.this, messageHint);
                                }
                            }

                        } else {
                            tvSure.setEnabled(false);
                            tvSure.setTextColor(getResources().getColor(R.color.textgrey));
                        }

                    }
                });
                tv_describe = (TextView) findViewById(R.id.tv_describe);

                String phone = currentLoginPhoneNum.substring(0, 3) + "****" + currentLoginPhoneNum.substring(7, currentLoginPhoneNum.length());
                tv_describe.setText("短信验证码已发送至+86 " + phone);

                tv_get_code = (TextView) findViewById(R.id.tv_get_code);
                SendSmsTimerUtils.sendSms(tv_get_code, R.color.fffColor, R.color.fffColor, 2);
                sendCode(currentLoginPhoneNum, newDistriceId, "3");

                tv_get_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentGetVerifyCode = tv_get_code;
                        sendCode(currentLoginPhoneNum, newDistriceId, "3");
                    }
                });

                tvSure.setOnClickListener(onClickListener);
                break;
            case 4://  更换新的没有注册的手机号码

                view = View.inflate(this, R.layout.layout_edit_phone_three, null);
                parentLayout.addView(view);
                tvLocation = ((TextView) findViewById(R.id.tv_location));
                tvLocation.setText("+"+new PreferencesHelper(EditDataActivity.this).getSmsCode());
                ivLocation = (ImageView) findViewById(R.id.iv_location);
                newPhone = (EditText) findViewById(R.id.et_one);
                newPhone.addTextChangedListener(new TextWatcher() {
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

                            newPhone.setText(sb.toString());
                            newPhone.setSelection(index);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String trim = newPhone.getText().toString().trim();

                        if (!TextUtils.isEmpty(trim)) {
                            String newPhoneNum = trim.replace(" ", "");
                            if (MyUtils.CheckPhoneNum(EditDataActivity.this, newPhoneNum)) {
                                phoneIsRegisterOnNewPhoneNumberImport(newPhoneNum, newDistriceId);
                                tvSure.setTextColor(Color.parseColor("#ffffff"));
                                tvSure.setClickable(true);
                            } else {
                                tvSure.setTextColor(Color.parseColor("#b5ada5"));
                                tvSure.setClickable(false);
                            }
                        }

                    }
                });

                LinearLayout ll_phone_location = (LinearLayout) findViewById(R.id.ll_phone_location);//手机归属地
                ll_phone_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(getApplicationContext(), ChoosePhoneHomeLocationActivity.class), 111);
                    }
                });

                tvSure = parentLayout.findViewById(R.id.tv_sure);
                tvSure.setOnClickListener(onClickListener);


                break;
            case 5:
                String newPhone = getIntent().getStringExtra("newPhone");//新的号码

                view = View.inflate(this, R.layout.layout_edit_phone_four, null);
                parentLayout.addView(view);

                TextView tv_new_phone = parentLayout.findViewById(R.id.tv_new_phone);//新的手机号
                tv_new_phone.setText(newPhone + "");

                TextView tvGetCode = parentLayout.findViewById(R.id.tv_get_code);
                tvGetCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentGetVerifyCode = tvGetCode;
                        sendCode(newPhone, newDistriceId, "4");
                    }
                });

                etCode = parentLayout.findViewById(R.id.edt_code);//验证码
//                etCode.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        String s = editable.toString();
//
//                        if (s.length() >= 6) {
//
//                        }
//                    }
//                });
                etCode.setInputCompleteListener(new SecurityCodeView.InputCompleteListener() {
                    @Override
                    public void inputComplete() {
                        String s = etCode.getEditContent().toString();
                        if (s.length() >= 6) {
                            isNewPhoneVerify = true;
                            updatePhone(newPhone, newDistriceId, s);
                        }
                    }

                    @Override
                    public void deleteContent(boolean isDelete) {

                    }
                });
                currentGetVerifyCode = tvGetCode;
                sendCode(newPhone, newDistriceId, "4");
                break;
            case 6:
                view = View.inflate(this, R.layout.layout_edit_phone_five, null);
                parentLayout.addView(view);
                tvSure = parentLayout.findViewById(R.id.tv_sure);
                tvSure.setOnClickListener(onClickListener);
                break;
        }
    }

    private void initEvent() {
        left.setOnClickListener(onClickListener);
        right.setOnClickListener(onClickListener);
        if (type == 6) {
            left.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_sure:
                    if (type == 2) {
                        String phoneNum = myPhone.getText().toString().trim();
                        String currentKjPhone = phoneNum.replace(" ", "");
                        boolean b = MyUtils.CheckPhoneNum(EditDataActivity.this, currentKjPhone);
                        if (b) {

                            if (currentKjPhone.equals(currentLoginPhoneNum)) {
                                Intent intent1 = new Intent(EditDataActivity.this, EditDataActivity.class);
                                intent1.putExtra("TYPE", type + 1);
                                startActivity(intent1);
                                finish();
                            } else {
                                ToastUtil.showToast(EditDataActivity.this, "已绑定手机号输入错误");
                            }

                        }

                    } else if (type == 3) {//更换手机号码  点击下一步
                        if (!TextUtils.isEmpty(myPhoneCode.getText().toString())) {
                                if (!verifyCode){
                                    Intent intent1 = new Intent(EditDataActivity.this, EditDataActivity.class);
                                    intent1.putExtra("TYPE", type + 1);
                                    startActivity(intent1);
                                    finish();
                                }
//                            updatePhone(currentLoginPhoneNum, newDistriceId, myPhoneCode.getText().toString());
                        }
                    } else if (type == 4) {//修改手机号，第三个页面，更换手机号输入新的手机号
                        String trim = newPhone.getText().toString().trim();

                        if (!TextUtils.isEmpty(trim)) {
                            String newPhoneNum = trim.replace(" ", "");
                            phoneIsRegister(newPhoneNum, newDistriceId);
                        }

                    } else if (type == 6) {
                        //    从新登录
                        finish();
                        removeAllActivitys();
                        PreferencesHelper preferencesHelper = new PreferencesHelper(EditDataActivity.this);
                        preferencesHelper.saveToken("");
                        EventBusUtil.post(new ExitLoginBean());
                        startActivity(new Intent(EditDataActivity.this, LoginActivity.class));
                    }
                    break;
                case R.id.img_home_right:
                    finish();
                    break;
                case R.id.img_home_left:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() >= 6) {
            intent = new Intent(EditDataActivity.this, EditDataActivity.class);
            intent.putExtra("TYPE", type + 1);
            startActivity(intent);
        }
    }

    private void phoneIsRegisterOnNewPhoneNumberImport(final String phone, final String districeId) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).phoneIsRegister(phone, districeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<PhoneIsRegisterBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<PhoneIsRegisterBean> baseBean) {
                        super.onNext(baseBean);
//                        {"msg":"该手机号还没有注册！","code":200,"data":{"isRegister":false}}
                        if (baseBean.code == 200) {

                            PhoneIsRegisterBean data = baseBean.data;
                            boolean isRegister = data.isIsRegister();
                            if (isRegister) {//已经注册了
                                showFamilyDialog(phone);
                            }
                        } else {
                            ToastUtil.showToast(EditDataActivity.this, baseBean.msg + "");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
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
        HttpHelp.getRetrofit(this).create(RemoteApi.class).phoneIsRegister(phone, districeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<PhoneIsRegisterBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<PhoneIsRegisterBean> baseBean) {
                        super.onNext(baseBean);
//                        {"msg":"该手机号还没有注册！","code":200,"data":{"isRegister":false}}
                        if (baseBean.code == 200) {

                            PhoneIsRegisterBean data = baseBean.data;
                            boolean isRegister = data.isIsRegister();
                            if (isRegister) {//已经注册了
                                if (keyBoardIsShow) {
                                    showFamilyDialog(phone);
                                } else {
                                    showGetCodeDialog(phone, true);
                                }

                            } else {
                                showGetCodeDialog(phone, false);
                            }
                        } else {
                            ToastUtil.showToast(EditDataActivity.this, baseBean.msg + "");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    /**
     * 向右滑动的弹框.获取验证码
     * getWindow().getDecorView().invalidate();
     *
     * @param phone
     */
    private void showGetCodeDialog(String phone, Boolean yetRegister) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog = builder.setViewId(R.layout.dialog_scoll_getcode)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        ImageView ivClose = dialog.getView(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView mSeekBar_tv = dialog.getView(R.id.seekbar_tv);
        VerificationSeekBar mSeekBar_Sb = dialog.getView(R.id.seekbar_sb);
        mSeekBar_Sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {  //滑块正在滑动过程中的监听
                if (progress >= 85) {       //滑动动作完成，设置滑块的图片是对号的图片
                    mSeekBar_Sb.setThumb(getResources().getDrawable(R.drawable.thumb_right));

                } else if (progress <= 15) {          //滑动动作没有完成，设置滑块的图片是箭头的图片
                    mSeekBar_Sb.setThumb(getResources().getDrawable(R.drawable.thumb_left));

                } else {
                    mSeekBar_Sb.setThumb(getResources().getDrawable(R.drawable.center_image_));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {   //滑块准备滑动前的监听
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() < 90) {
                    seekBar.setProgress(11);
                    mSeekBar_tv.setVisibility(View.GONE);
                    mSeekBar_Sb.setThumb(getResources().getDrawable(R.drawable.thumb_left));
                } else {
                    seekBar.setProgress(92);
                    mSeekBar_tv.setVisibility(View.VISIBLE);
                    mSeekBar_Sb.setThumb(getResources().getDrawable(R.drawable.thumb_right));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (!yetRegister) {
                                    Message message = handler.obtainMessage();
                                    message.what = 0;
                                    message.obj = phone;
                                    handler.sendMessage(message);
                                } else {
                                    Message message = handler.obtainMessage();
                                    message.what = 1;
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {    //隐藏滑块，显示密码登录
            switch (msg.what) {
                case 0:
                    Intent intent1 = new Intent(EditDataActivity.this, EditDataActivity.class);
                    intent1.putExtra("TYPE", type + 1);
                    intent1.putExtra("newPhone", (String) msg.obj);

                    startActivity(intent1);
                    finish();
                    dialog.dismiss();
                    break;
                case 1:
                    ToastUtil.showToast(mContext, "该手机号已经注册，不能重复绑定！");
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }

    };

    private void showFamilyDialog(String phone) {
//        1~100之间的，随机的
        int height1 = MyUtils.dip2px(EditDataActivity.this, 172);

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_yet_register_on_change_phone_num)
                //设置dialogpadding
                .setPaddingdp(20, 0, 20, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, height1)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();

        dialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSure.setTextColor(Color.parseColor("#b5ada5"));
                tvSure.setClickable(true);
                keyBoardIsShow = true;
                dialog.dismiss();
            }
        });

        dialog.getView(R.id.tv_go_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSure.setTextColor(Color.parseColor("#ffffff"));
                tvSure.setClickable(true);
                keyBoardIsShow = false;
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            String phonePrefix = data.getStringExtra("phonePrefix");
            new PreferencesHelper(EditDataActivity.this).saveSmsCode(phonePrefix);
            String id = data.getStringExtra("cityid");
            newDistriceId = id;
            String cityName = data.getStringExtra("cityName");
            if ("1".equals(newDistriceId)||"7".equals(newDistriceId)||"19".equals(newDistriceId)||"44".equals(newDistriceId)){
                ivLocation.setVisibility(View.VISIBLE);
            }else {
                ivLocation.setVisibility(View.GONE);
            }
            tvLocation.setText(cityName + "( +" + phonePrefix + ")");

        }else if (requestCode == 222 && resultCode == Activity.RESULT_OK){
            String phonePrefix = data.getStringExtra("phonePrefix");
            tvGetStatesCode.setText("+" + phonePrefix);
            new PreferencesHelper(EditDataActivity.this).saveSmsCode(phonePrefix);
        }



    }

    /**
     * 用户修改手机号
     * token	是	string	标识
     * phone	是	string	手机号
     * districeId	是	string	手机号归属地id
     * code	是	string	验证码
     *
     * @param phone
     */
    private void updatePhone(String phone, String qcellcoreId, String code) {

        String token = helper.getToken();
        String url = Setting.updatePhone();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);//

        stringStringHashMap.put("phone", phone);//
        stringStringHashMap.put("districeId", qcellcoreId);//
        stringStringHashMap.put("code", code);//
        Log.e(TAG + "test", phone + "-------" + code + "=====" + qcellcoreId);
        OkHttpUtils
                .post()
                .params(stringStringHashMap)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(EditDataActivity.this, this));
    }

    /**
     * 发送验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机号归属地id
     * type	是	string	发送类型(1：忘记密码 2：修改密码 3：更换手机号旧发送验证码 4:更换手机号新手机发送验证码)
     *
     * @param phone
     */
    private void sendCode(String phone, String qcellcoreId, String type) {
        String url = Setting.sendCode();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("phone", phone);//
        stringStringHashMap.put("qcellcoreId", qcellcoreId);//
        stringStringHashMap.put("type", type);//

        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(EditDataActivity.this, this));
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
                .execute(new BaseStringCallback_Host(EditDataActivity.this, this));
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
            (EditDataActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            verifyCode = false;
                            rightCode = myPhoneCode.getText().toString().trim();
                            messageHint = msg1;
                            tvSure.setEnabled(true);
                            tvSure.setTextColor(getResources().getColor(R.color.white));
                            Log.e(TAG, "coderight" + code + messageHint + rightCode);
                        } else {

                            messageHint = msg1;
                            tvSure.setEnabled(false);
                            tvSure.setTextColor(getResources().getColor(R.color.textgrey));
                            ToastUtil.showToast(mContext, msg1);
                            Log.e(TAG, "codewrong" + code + messageHint + rightCode);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(1)) {//标记那个接口  发送验证码

            String result = (String) data;
            (EditDataActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            if (null != currentGetVerifyCode) {
                                SendSmsTimerUtils.sendSms(currentGetVerifyCode, R.color.text_code, R.color.fffColor, 2);
                                Log.e(TAG, "SendSmsTimerUtils" + code + messageHint + rightCode);
                            }
                        } else {
                            ToastUtil.showToast(EditDataActivity.this, msg1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(2)) {//标记那个接口  用户修改手机号

            String result = (String) data;
            (EditDataActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            EventBusUtil.post(new UpdatePhoneEvent());

                            Intent intent1 = new Intent(EditDataActivity.this, EditDataActivity.class);
                            intent1.putExtra("TYPE", type + 1);
                            startActivity(intent1);
                            finish();

                        } else {
                            ToastUtil.showToast(EditDataActivity.this, msg1);
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

        (EditDataActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(EditDataActivity.this, errorMessage);

//                if ((int) object == 2) {
//                    Intent intent1 = new Intent(EditDataActivity.this, EditDataActivity.class);
//                    intent1.putExtra("TYPE", type + 1);
//                    startActivity(intent1);
//                    finish();
////                            }
//                }
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}

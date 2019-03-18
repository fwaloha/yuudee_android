package com.easychange.admin.smallrain.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.ChoosePhoneHomeLocationActivity;
import com.easychange.admin.smallrain.adapter.SelectCityAdapter;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.base.BaseDialog;
import com.easychange.admin.smallrain.entity.CityListBean;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.utils.SoftKeyBoardListener;
import com.easychange.admin.smallrain.views.VerificationSeekBar;
//TODO
//import com.qlzx.mylibrary.base.BaseSubscriber;
//import com.qlzx.mylibrary.bean.BaseBean;
//import com.qlzx.mylibrary.http.HttpHelp;
//import com.qlzx.mylibrary.util.EventBusUtil;
//import com.qlzx.mylibrary.util.ToastUtil;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import bean.FinishLoginBean;
import bean.LocationBean;
import bean.PhoneIsRegisterBean;
import bean.RegisterDestroyOtherBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import event.LoginTypeEvent;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenlipeng on 2018/10/30 0030
 * describe:  立即注册  过
 */
public class RegisterActivity extends BaseActivity {
    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;
    @BindView(R.id.layout_quhao)
    LinearLayout layoutQuhao;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    // 特殊下标位置
    private TextView mSeekBar_tv;
    private VerificationSeekBar mSeekBar_Sb;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {    //隐藏滑块，显示密码登录
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(mContext, AuthCodeActivity.class);
                    intent.putExtra("edtPhone", currentPhone + "");
                    intent.putExtra("districeId", districeId);
                    intent.putExtra("myphonePrefix",phonePrefix);
                    startActivity(intent);

                    dialog.dismiss();
                    break;

                default:
                    break;
            }
        }

    };
    private BaseDialog dialog;
    private String districeId = "1";//中国默认
    private String currentPhone = "";
    private String phonePrefix = "86";//中国默认

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBusUtil.register(this);

        initView();

      /*  SoftKeyBoardListener.setListener(RegisterActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                if (!TextUtils.isEmpty(currentPhone)) {
                    if (phonePrefix.equals("86")) {
                        if (currentPhone.length() != 11) {
                            ToastUtil.showToast(mContext, "手机号位数不正确");
                        }
                    }else {
                        if (currentPhone.length()<10){
                            ToastUtil.showToast(mContext,"手机号位数不正确");
                        }
                    }
                }
            }
        });
*/
    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();

    }

    private void isNumLegal(String phoneNumber, String qcellcoreId) {
        HttpHelp.getRetrofit(this).create(RemoteApi.class).isNumberLegal(phoneNumber, qcellcoreId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<String>>(this, null) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
                            //手机号是否合法 （1:合法 0：不合法 ）
                            if ("0".equals(baseBean.data)) {
                                //不合法
                                tvNext.setTextColor(getResources().getColor(R.color.textgrey));
                                tvNext.setEnabled(false);
                                ToastUtil.showToast(RegisterActivity.this, "手机号格式输入错误");
                            } else if ("1".equals(baseBean.data)) {
                                // 合法
                                tvNext.setTextColor(getResources().getColor(R.color.white));
                                tvNext.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HeadChangeBean(RegisterDestroyOtherBean event) {
        finish();
    }

//1.手机号归属地：中国按钮可点，点击可查看所有国家的手机号区号列表。默认显示中国。
//            2.方框中手机号前面的区号显示，根据上方归属地中显示的区域，对应显示区号。
//            3.手机号：
//            3.1允许用户最多输入11位数字，格式为“135 1010 1010（中间用空格隔开）”
//            3.2出现非法输入将直接弹出输入错误前端提示；
//            3.3手机号输入满足11位时，下一步按钮点亮可点。
//            3.4 手机号后面的取消按钮，点击可逐位删除已填写的手机号。
//            4.下一步：
//            4.1点击下一步按钮，先判断此手机号数据库是否存在，如果已经存在，弹窗提示：“该手机号已经被注册，验证手机号后可直接登录”。


    private void initView() {
        edtPhone.addTextChangedListener(new TextWatcher() {
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

                    edtPhone.setText(sb.toString());
                    edtPhone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = edtPhone.getText().toString().trim();
                currentPhone = trim.replace(" ", "");

                if (phonePrefix.equals("86")){
                    if (currentPhone.length() == 11) {
                        isNumLegal(currentPhone, districeId);
                    } else {
                        tvNext.setTextColor(getResources().getColor(R.color.textgrey));
                        tvNext.setEnabled(false);
                    }
                }else {
                    if (currentPhone.length()<10){
                        tvNext.setTextColor(getResources().getColor(R.color.textgrey));
                        tvNext.setEnabled(false);
                    }else {
                        isNumLegal(currentPhone, districeId);
                    }
                }
            }
        });
    }

//1.手机号归属地：中国按钮可点，点击可查看所有国家的手机号区号列表。默认显示中国。
//            2.方框中手机号前面的区号显示，根据上方归属地中显示的区域，对应显示区号。
//            3.手机号：
//            3.1允许用户最多输入11位数字，格式为“135 1010 1010（中间用空格隔开）”
//            3.2出现非法输入将直接弹出输入错误前端提示；
//            3.3手机号输入满足11位时，下一步按钮点亮可点。
//            3.4 手机号后面的取消按钮，点击可逐位删除已填写的手机号。
//            4.下一步：
//            4.1点击下一步按钮，先判断此手机号数据库是否存在，如果已经存在，弹窗提示：“该手机号已经被注册，验证手机号后可直接登录”。


    @OnClick({R.id.layout_quhao, R.id.tv_next, R.id.img_home_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_quhao:
                startActivityForResult(new Intent(getApplicationContext(), ChoosePhoneHomeLocationActivity.class), 111);
                break;
            case R.id.tv_next:
                phoneIsRegister(currentPhone, districeId);
                break;
            case R.id.img_home_right:
                finish();
                break;
        }
    }

    /**
     * 手机号数据库存在的弹框
     */
    private void yetRegisterDialog() {
        int height1 = MyUtils.dip2px(RegisterActivity.this, 222);

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog = builder.setViewId(R.layout.dialog_yet_register)
                //设置dialogpadding
                .setPaddingdp(0, 0, 0, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, height1)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();

        View tv_cancel = dialog.getView(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View tv_go_login = dialog.getView(R.id.tv_go_login);
        tv_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                EventBusUtil.post(new FinishLoginBean());
                EventBusUtil.post(new LoginTypeEvent());
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                intent1.putExtra("phone", currentPhone);
                startActivity(intent1);
                finish();
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
                        if (baseBean.code == 200) {
                            PhoneIsRegisterBean data = baseBean.data;
                            boolean isRegister = data.isIsRegister();
                            if (isRegister) {//已经注册了
                                yetRegisterDialog();//手机号数据库存在的弹框
                            } else {
                                showGetCodeDialog();
                            }
                        } else {
                            ToastUtil.showToast(RegisterActivity.this, baseBean.msg + "");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }
//    直接跳转快捷登录页面，
//    并且把已经填写的手机号补充在快捷登陆“请输入手机号”一栏。

    /**
     * 向右滑动的弹框.获取验证码
     * getWindow().getDecorView().invalidate();
     */
    private void showGetCodeDialog() {
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
        mSeekBar_tv = dialog.getView(R.id.seekbar_tv);
        mSeekBar_Sb = dialog.getView(R.id.seekbar_sb);
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
                                Message message = handler.obtainMessage();
                                message.what = 0;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            phonePrefix = data.getStringExtra("phonePrefix");
            String cityName = data.getStringExtra("cityName");
            String id = data.getStringExtra("cityid");
            districeId = id;
            if ("1".equals(districeId)||"7".equals(districeId)||"19".equals(districeId)||"44".equals(districeId)){
                imgCode.setVisibility(View.VISIBLE);
            }else {
                imgCode.setVisibility(View.GONE);
            }
            tvCode.setText(cityName + "( +" + phonePrefix + ")");
            edtPhone.getText().clear();
        }
    }
}

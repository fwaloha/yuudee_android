package com.easychange.admin.smallrain.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.SoftKeyBoardListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import bean.ChildInfoBean;
import bean.GetThisCityListBean;
import bean.RegisterDestroyOtherBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.Setting;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

/**
 * Created by chenlipeng on 2018/10/18 0018
 * describe:  完善训练儿童个人信息
 */
public class PerfectionChildrenInfoActivity extends BaseActivity implements CityPickerListener, AsyncRequest {
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.ll_edt_phone_number)
    LinearLayout llEdtPhoneNumber;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_yixue_zhenduan)
    TextView tvYixueZhenduan;
    @BindView(R.id.et_first_language)
    EditText etFirstLanguage;
    @BindView(R.id.tv_first_language)
    TextView tvFirstLanguage;
    @BindView(R.id.ll_first_language)
    RelativeLayout llFirstLanguage;
    @BindView(R.id.ev_second_language)
    EditText evSecondLanguage;
    @BindView(R.id.tv_second_language)
    TextView tvSecondLanguage;
    @BindView(R.id.ll_second_language)
    RelativeLayout llSecondLanguage;
    @BindView(R.id.tv_dad_culture)
    TextView tvDadCulture;
    @BindView(R.id.ll_dad_culture)
    LinearLayout llDadCulture;
    @BindView(R.id.tv_mom_culture)
    TextView tvMomCulture;
    @BindView(R.id.ll_mom_culture)
    LinearLayout llMomCulture;
    @BindView(R.id.ev_training_treatment)
    EditText evTrainingTreatment;
    @BindView(R.id.tv_training_treatment)
    TextView tvTrainingTreatment;
    @BindView(R.id.ll_training_treatment)
    RelativeLayout llTrainingTreatment;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.layout_pt_content)
    LinearLayout layoutPtContent;//联网要实现这个接口
    @BindView(R.id.et_yixue_zhenduan)
    EditText etYixueZhenduan;
    @BindView(R.id.rl_yixue_zhenduan)
    RelativeLayout rlYixueZhenduan;

    private View right;
    private View sure;
    private int month;
    private int DATE;
    private CityPicker cityPicker;

    private int currentTextSize = 16;
    private String token = "";
    private String nickName;
    private String sex;
    private String birthdate;
    private String location;
    private String firstLanguage = "";
    private String secondLanguage = "";
    private String dadCulture;
    private String momCulture;
    private String yixueZhenduan;
    private String trainingTreatment;
    private String currentDadCul;
    private String currentMomCul = "";
    private ArrayList getCountryList;
    private ArrayList getProvinceList;
    private ArrayList getCityList;
    private String currentCountryList = "";
    private GetThisCityListBean model1;
    private String currentProvice = "";
    private String currentCity = "";
    private int countryAareaid = -1;
    private int areaidProvince = -1;
    private int areaidCity = -1;
    private int currentChoosePosition = 1;


    private String finishType = "back";

    /**
     * balloontype
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_children_info);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("registertype") != null) {
            finishType = getIntent().getStringExtra("registertype");
        }

        Calendar calendar = Calendar.getInstance();
//              获得当前时间的月份，月份从0开始所以结果要加1
        month = calendar.get(Calendar.MONTH) + 1;
        DATE = calendar.get(Calendar.DATE);

        PreferencesHelper helper = new PreferencesHelper(PerfectionChildrenInfoActivity.this);
        token = helper.getToken();

        initView();

        cityPicker = new CityPicker(PerfectionChildrenInfoActivity.this, this);

        SoftKeyBoardListener.setListener(PerfectionChildrenInfoActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                if (currentChoosePosition == 1) {
//                    etFirstLanguage.setVisibility(View.GONE);
//                    tvFirstLanguage.setVisibility(View.VISIBLE);
//                    tvFirstLanguage.setText(etFirstLanguage.getText().toString() + "");

                    if (TextUtils.isEmpty(etFirstLanguage.getText().toString())) {
                        tvFirstLanguage.setText("请选择或填写儿童第一语言");
                    } else {
                        tvFirstLanguage.setText(etFirstLanguage.getText().toString() + "");
                    }
                }
                if (currentChoosePosition == 2) {
//                    evSecondLanguage.setVisibility(View.GONE);
//                    tvSecondLanguage.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(evSecondLanguage.getText().toString())) {
                        tvSecondLanguage.setText("请选择或填写儿童第二语言");
                    } else {
                        tvSecondLanguage.setText(evSecondLanguage.getText().toString() + "");
                    }
                }
                if (currentChoosePosition == 3) {
//                    evTrainingTreatment.setVisibility(View.GONE);
//                    tvTrainingTreatment.setVisibility(View.VISIBLE);
//                    tvTrainingTreatment.setText(evTrainingTreatment.getText().toString() + "");

                    if (TextUtils.isEmpty(evTrainingTreatment.getText().toString())) {
                        tvTrainingTreatment.setText("请选择目前尝试的训练及疗法");
                    } else {
                        tvTrainingTreatment.setText(evTrainingTreatment.getText().toString() + "");
                    }
                }
                if (currentChoosePosition == 4) {
//                    etYixueZhenduan.setVisibility(View.GONE);
//                    tvYixueZhenduan.setVisibility(View.VISIBLE);
//                    tvYixueZhenduan.setText(etYixueZhenduan.getText().toString() + "");

                    if (TextUtils.isEmpty(etYixueZhenduan.getText().toString())) {
                        tvYixueZhenduan.setText("请选择医学诊断");
                    } else {
                        tvYixueZhenduan.setText(etYixueZhenduan.getText().toString() + "");
                    }
                }

                if (etFirstLanguage.getVisibility() == View.VISIBLE) {
//                    etFirstLanguage.setVisibility(View.GONE);
//                    tvFirstLanguage.setVisibility(View.VISIBLE);
//                    tvFirstLanguage.setText(etFirstLanguage.getText().toString() + "");

                    if (TextUtils.isEmpty(etFirstLanguage.getText().toString())) {
                        tvFirstLanguage.setText("请选择或填写儿童第一语言");
                    } else {
                        tvFirstLanguage.setText(etFirstLanguage.getText().toString() + "");
                    }
                }

                if (evSecondLanguage.getVisibility() == View.VISIBLE) {
//                    evSecondLanguage.setVisibility(View.GONE);
//                    tvSecondLanguage.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(evSecondLanguage.getText().toString())) {
                        tvSecondLanguage.setText("请选择或填写儿童第二语言");
                    } else {
                        tvSecondLanguage.setText(evSecondLanguage.getText().toString() + "");
                    }
                }


                if (evTrainingTreatment.getVisibility() == View.VISIBLE) {
//                    evTrainingTreatment.setVisibility(View.GONE);
//                    tvTrainingTreatment.setVisibility(View.VISIBLE);
//                    tvTrainingTreatment.setText(evTrainingTreatment.getText().toString() + "");

                    if (TextUtils.isEmpty(evTrainingTreatment.getText().toString())) {
                        tvTrainingTreatment.setText("请选择目前尝试的训练及疗法");
                    } else {
                        tvTrainingTreatment.setText(evTrainingTreatment.getText().toString() + "");
                    }
                }

                if (etYixueZhenduan.getVisibility() == View.VISIBLE) {
//                    etYixueZhenduan.setVisibility(View.GONE);
//                    tvYixueZhenduan.setVisibility(View.VISIBLE);
//                    tvYixueZhenduan.setText(etYixueZhenduan.getText().toString() + "");

                    if (TextUtils.isEmpty(etYixueZhenduan.getText().toString())) {
                        tvYixueZhenduan.setText("请选择医学诊断");
                    } else {
                        tvYixueZhenduan.setText(etYixueZhenduan.getText().toString() + "");
                    }
                }

            }

        });
        systemGetThisCityList();
        getChilInfo();

        setIsDDD(edtPhoneNumber);
        setEditTextInhibitInputSpeChat(edtPhoneNumber);
        setEditTextInhibitInputSpeChat(etFirstLanguage);
        setEditTextInhibitInputSpeChat(evSecondLanguage);
        setEditTextInhibitInputSpeChat(evTrainingTreatment);
        setEditTextInhibitInputSpeChat(etYixueZhenduan);
        setIsDDD(tvSex);
        setIsDDD(tvBirthday);

        setIsDDD(tvYixueZhenduan);
        setIsDDD(tvFirstLanguage);
        setIsDDD(tvSecondLanguage);

        setIsDDD(tvDadCulture);
        setIsDDD(tvMomCulture);
        setIsDDD(tvTrainingTreatment);
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\ \\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public void setIsDDD(TextView view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvLogin.setTextColor(Color.parseColor("#b5ada5"));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nickName = edtPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
//                    ToastUtil.showToast(mContext, "儿童昵称不能为空");
//                    请输入字母，数字或文字形式的儿童昵称
                    return;
                }

                boolean b = MyUtils.compileExChar(nickName);
                if (b) {
//                    ToastUtil.showToast(mContext, "请输入字母，数字或文字形式的儿童昵称");
                    return;
                }

                sex = tvSex.getText().toString();
                if (TextUtils.isEmpty(sex)) {
//                    ToastUtil.showToast(mContext, "儿童性别不能为空");
                    return;
                }
                birthdate = tvBirthday.getText().toString();
                if (TextUtils.isEmpty(birthdate)) {
//                    ToastUtil.showToast(mContext, "儿童出生日期不能为空");
                    return;
                }


                yixueZhenduan = tvYixueZhenduan.getText().toString();
                if (TextUtils.isEmpty(yixueZhenduan)) {
//                    ToastUtil.showToast(mContext, "医学诊断不能为空");
                    return;
                }
                //请选择或填写儿童第一语言
                firstLanguage = tvFirstLanguage.getText().toString();
                if (firstLanguage.equals("请选择或填写儿童第一语言")) {
//                    ToastUtil.showToast(mContext, "第一语言不能为空");
                    return;
                }
                if (TextUtils.isEmpty(firstLanguage)) {
//                    ToastUtil.showToast(mContext, "目前训练及疗法不能为空");
                    return;
                }
                //请选择或填写儿童第二语言
                secondLanguage = tvSecondLanguage.getText().toString();
                if (secondLanguage.equals("请选择或填写儿童第二语言")) {
//                    ToastUtil.showToast(mContext, "第二语言不能为空");
                    return;
                }
                if (TextUtils.isEmpty(secondLanguage)) {
//                    ToastUtil.showToast(mContext, "目前训练及疗法不能为空");
                    return;
                }
                dadCulture = tvDadCulture.getText().toString();
                if (TextUtils.isEmpty(dadCulture)) {
//                    ToastUtil.showToast(mContext, "父亲文化程度不能为空");
                    return;
                }

                momCulture = tvMomCulture.getText().toString();
                if (TextUtils.isEmpty(momCulture)) {
//                    ToastUtil.showToast(mContext, "母亲文化程度不能为空");
                    return;
                }

                trainingTreatment = tvTrainingTreatment.getText().toString();
                if (trainingTreatment.equals("请选择目前尝试的训练及疗法")) {
//                    ToastUtil.showToast(mContext, "目前训练及疗法不能为空");
                    return;
                }

                if (TextUtils.isEmpty(trainingTreatment)) {
//                    ToastUtil.showToast(mContext, "目前训练及疗法不能为空");
                    return;
                }
                tvLogin.setTextColor(Color.parseColor("#fffdec"));

            }
        });
    }

    /**
     * 跳转静态方法
     *
     * @param context
     */
    public static void startActivityWithnoParmeter(Context context) {
        Intent intent = new Intent(context, PerfectionChildrenInfoActivity.class);
        context.startActivity(intent);
    }


    /**
     * 城市选择
     *
     * @param name
     */
    @Override
    public void getCity(String name) {
        tvLocation.setText(name);
    }

    @OnClick({R.id.edt_phone_number, R.id.tv_location, R.id.tv_sex, R.id.tv_birthday, R.id.rl_yixue_zhenduan, R.id.ll_first_language, R.id.ll_second_language
            , R.id.ll_dad_culture, R.id.ll_mom_culture, R.id.ll_training_treatment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_phone_number:  //
                break;
            case R.id.tv_location:  //居住地
                onOptionPicker(tvLocation);
//                cityPicker.show();
                break;
            case R.id.tv_sex:  //
                ChooseSex();
                break;
            case R.id.tv_birthday:  //
                yearMonthDay();
                break;
            case R.id.rl_yixue_zhenduan:  //
                tvYixueZhenduan.setText("");
                doctorDiagnosesDialog();

                break;
            case R.id.ll_first_language:  //
//                选项1：普通话
//                选项2：方言
//                选项3：其它语言_______
                tvFirstLanguage.setText("");
                showLanguageListDialog(tvFirstLanguage, etFirstLanguage, 1);
                break;
            case R.id.ll_second_language:  //
//                选项1：普通话
//                选项2：方言
//                选项3：其它语言_______
                tvSecondLanguage.setText("");
                showLanguageListDialog(tvSecondLanguage, evSecondLanguage, 2);
                break;
            case R.id.ll_dad_culture:  //
                onOptionPickerDucationBackgroundList(tvDadCulture, 1);
                break;

            case R.id.ll_mom_culture:  //
                onOptionPickerDucationBackgroundList(tvMomCulture, 2);
                break;
            case R.id.ll_training_treatment:  //

//            选项1：ABA训练  选项2：其它疗法训练_____
//            选项3：无训练
//            当用户选择第三个选项的时候，页面本栏变成输入栏，光标提示，同时文案变成：“请输入其它疗法名称”
                tvTrainingTreatment.setText("");
                showTrainingTreatmentListDialog(tvTrainingTreatment, evTrainingTreatment, 3);
                break;
        }
    }

    /**
     * 语言
     *
     * @param tvFirstLanguage1
     * @param etFirstLanguage
     * @param i
     */
    private void showLanguageListDialog(TextView tvFirstLanguage1, EditText etFirstLanguage, int i) {
        OptionPicker picker = new OptionPicker(this, new String[]{"普通话", "方言", "其它语言_______"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(currentTextSize);
        if (i == 1) {
            picker.setTitleText("请选择儿童第一语言");
        } else if (i == 2) {
            picker.setTitleText("请选择儿童第二语言");
        }
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
//                showToast("index=" + index + ", item=" + item);

                if (index == 2) {
                    tvFirstLanguage1.setVisibility(View.GONE);
                    etFirstLanguage.setVisibility(View.VISIBLE);
                    picker.dismiss();
                    etFirstLanguage.setText("");
                    etFirstLanguage.setHint("请输入语言名称");
                    etFirstLanguage.setHintTextColor(getResources().getColor(R.color.text_tu));//@color/text_tu

                    etFirstLanguage.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String editable = etFirstLanguage.getText().toString();
                            String str = stringFilter(editable.toString());
                            if (!editable.equals(str)) {
                                etFirstLanguage.setText(str);
                                //设置新的光标所在位置
                                etFirstLanguage.setSelection(str.length());
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    etFirstLanguage.setFocusable(true);
                    etFirstLanguage.setFocusableInTouchMode(true);

                    etFirstLanguage.requestFocus();//获取焦点 光标出现
                    showSoftKeyboard(etFirstLanguage, mContext);

                    currentChoosePosition = i;
                } else {
                    tvFirstLanguage1.setVisibility(View.VISIBLE);
                    etFirstLanguage.setVisibility(View.GONE);
                    etFirstLanguage.setText("");
                    tvFirstLanguage1.setText(item);
                }
            }
        });
        picker.show();

    }

    /**
     * 学历
     *
     * @param tvMomCulture
     */
    public void onOptionPickerDucationBackgroundList(TextView tvMomCulture, int type) {
        OptionPicker picker = new OptionPicker(this, new String[]{"小学-高中", "大学", "硕士研究生", "博士或类似"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(currentTextSize);
        if (type == 1) {
            picker.setTitleText("请选择父亲最高文化程度");
        } else if (type == 2) {
            picker.setTitleText("请选择母亲最高文化程度");
        }
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
//                showToast("index=" + index + ", item=" + item);
                tvMomCulture.setText(item);

            }
        });
        picker.show();
    }


    /**
     * @param tvFirstLanguage
     * @param etFirstLanguage
     * @param i
     */
    private void showTrainingTreatmentListDialog(TextView tvFirstLanguage, EditText etFirstLanguage, int i) {
        OptionPicker picker = new OptionPicker(this, new String[]{"ABA训练", "其它疗法训练_____", "无训练"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(currentTextSize);
        picker.setTitleText("请选择目前尝试的训练及疗法");
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

                if (index == 1) {
                    tvFirstLanguage.setText("");
                    tvFirstLanguage.setVisibility(View.GONE);
                    etFirstLanguage.setVisibility(View.VISIBLE);
                    picker.dismiss();

                    etFirstLanguage.setText("");
                    etFirstLanguage.setHint("请输入其它疗法名称");
                    etFirstLanguage.setHintTextColor(getResources().getColor(R.color.text_tu));//@color/text_tu

                    etFirstLanguage.setFocusable(true);
                    etFirstLanguage.setFocusableInTouchMode(true);

                    etFirstLanguage.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String editable = etFirstLanguage.getText().toString();
                            String str = stringFilter(editable.toString());
                            if (!editable.equals(str)) {
                                etFirstLanguage.setText(str);
                                //设置新的光标所在位置
                                etFirstLanguage.setSelection(str.length());
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    etFirstLanguage.requestFocus();//获取焦点 光标出现
                    showSoftKeyboard(etFirstLanguage, mContext);

                    currentChoosePosition = i;
                } else {
                    tvFirstLanguage.setVisibility(View.VISIBLE);
                    etFirstLanguage.setVisibility(View.GONE);
                    etFirstLanguage.setText("");
                    tvFirstLanguage.setText(item);
                }

            }
        });
        picker.show();
    }

    /**
     * 性别
     */
    private void ChooseSex() {
        OptionPicker picker = new OptionPicker(this, new String[]{"男", "女"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(currentTextSize);
        picker.setTitleText("选择性别");
        setWheelStyle(picker);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tvSex.setText(item);
            }
        });
        picker.show();
    }

    public void setWheelStyle(AddressPicker picker) {
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
    }

    public void setWheelStyle(OptionPicker picker) {
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
    }

    /**
     * 医学诊断
     */
    private void doctorDiagnosesDialog() {
        //                选项1：自闭症 ，选择后，需要选程度： 轻 中 重 不清楚
//                选项2：语言发育迟缓（其它正常）
//                选项3：单纯性智力低下（无自闭症状）
//                选项4：正常
        OptionPicker picker = new OptionPicker(this, new String[]{"自闭症： 轻", "自闭症： 中", "自闭症： 重", "自闭症： 不清楚",
                "语言发育迟缓（其它正常）",
                "单纯性智力低下（无自闭症状）", "其他诊断", "正常"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(currentTextSize);
        picker.setTitleText("请选择医学诊断");
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

                if (index == 6) {

                    tvYixueZhenduan.setVisibility(View.GONE);
                    etYixueZhenduan.setVisibility(View.VISIBLE);
                    picker.dismiss();

                    etYixueZhenduan.setText("");
                    etYixueZhenduan.setHint("请输入医学诊断");
                    etYixueZhenduan.setHintTextColor(getResources().getColor(R.color.text_tu));//@color/text_tu


                    etYixueZhenduan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String editable = etYixueZhenduan.getText().toString();
                            String str = stringFilter(editable.toString());
                            if (!editable.equals(str)) {
                                etYixueZhenduan.setText(str);
                                //设置新的光标所在位置
                                etYixueZhenduan.setSelection(str.length());
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    etYixueZhenduan.setFocusable(true);
                    etYixueZhenduan.setFocusableInTouchMode(true);

                    etYixueZhenduan.requestFocus();//获取焦点 光标出现

                    showSoftKeyboard(etYixueZhenduan, mContext);
                    currentChoosePosition = 4;

                } else {
                    etYixueZhenduan.setText("");
                    etYixueZhenduan.setVisibility(View.GONE);
                    tvYixueZhenduan.setVisibility(View.VISIBLE);
                    tvYixueZhenduan.setText(item);
                }

            }
        });
        picker.show();
    }

    public void showSoftKeyboard(View view, Context mContext) {
        if (view != null) {
            view.postDelayed(() -> {
                if (view.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 200);
        }
    }

    private void initView() {
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editable = edtPhoneNumber.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    edtPhoneNumber.setText(str);
                    //设置新的光标所在位置
                    edtPhoneNumber.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        right = findViewById(R.id.img_home_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfectionAddChildOnFinished();
            }
        });
        sure = findViewById(R.id.tv_login);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickName = edtPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtil.showToast(mContext, "儿童昵称不能为空");
//                    请输入字母，数字或文字形式的儿童昵称
                    return;
                }

                boolean b = MyUtils.compileExChar(nickName);
                if (b) {
                    ToastUtil.showToast(mContext, "请输入字母，数字或文字形式的儿童昵称");
                    return;
                }

                sex = tvSex.getText().toString();
                if (TextUtils.isEmpty(sex)) {
                    ToastUtil.showToast(mContext, "儿童性别不能为空");
                    return;
                }
                birthdate = tvBirthday.getText().toString();
                if (TextUtils.isEmpty(birthdate)) {
                    ToastUtil.showToast(mContext, "儿童出生日期不能为空");
                    return;
                }

                location = tvLocation.getText().toString();
                if (TextUtils.isEmpty(location)) {
                    ToastUtil.showToast(mContext, "长期居住地不能为空");
                    return;
                }
                if (tvYixueZhenduan.getVisibility() == View.VISIBLE)
                    yixueZhenduan = tvYixueZhenduan.getText().toString();
                else yixueZhenduan = etYixueZhenduan.getText().toString();
                if (tvYixueZhenduan.getVisibility() != View.GONE
                        && TextUtils.isEmpty(yixueZhenduan) || "请选择医学诊断".equals(yixueZhenduan)) {
                    ToastUtil.showToast(mContext, "医学诊断不能为空");
                    return;
                }
                //请选择或填写儿童第一语言
                if (tvFirstLanguage.getVisibility() == View.VISIBLE)
                    firstLanguage = tvFirstLanguage.getText().toString();
                else firstLanguage = etFirstLanguage.getText().toString();
                if (TextUtils.isEmpty(firstLanguage) || "请选择或填写儿童第一语言".equals(firstLanguage)) {
                    ToastUtil.showToast(mContext, "第一语言不能为空");
                    return;
                }
                //请选择或填写儿童第二语言
                if (tvSecondLanguage.getVisibility() == View.VISIBLE)
                    secondLanguage = tvSecondLanguage.getText().toString();
                else secondLanguage = evSecondLanguage.getText().toString();
                if (TextUtils.isEmpty(secondLanguage) || "请选择或填写儿童第二语言".equals(secondLanguage)) {
                    ToastUtil.showToast(mContext, "第二语言不能为空");
                    return;
                }

                dadCulture = tvDadCulture.getText().toString();
                if (TextUtils.isEmpty(dadCulture)) {
                    ToastUtil.showToast(mContext, "父亲文化程度不能为空");
                    return;
                }

                momCulture = tvMomCulture.getText().toString();
                if (TextUtils.isEmpty(momCulture)) {
                    ToastUtil.showToast(mContext, "母亲文化程度不能为空");
                    return;
                }
                if (tvTrainingTreatment.getVisibility() == View.VISIBLE)
                    trainingTreatment = tvTrainingTreatment.getText().toString();
                else trainingTreatment = evTrainingTreatment.getText().toString();
                if (TextUtils.isEmpty(trainingTreatment) || "请选择目前尝试的训练及疗法".equals(trainingTreatment)) {
                    ToastUtil.showToast(mContext, "目前训练及疗法不能为空");
                    return;
                }
                perfectionAddChild();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            perfectionAddChildOnFinished();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //上传完整的数据
    private void perfectionAddChild() {
//        medical：0 “自闭症”, 1 “语言发育迟缓（其他正常）”, 2 “单纯性智力低下（无自闭症）”, 3 “其他诊断”, 4 “正常”,
//        medicalState：0：轻 1：中 2：重 3：不清楚 （当medical为0时才传）
//        firstLanguage：0:普通话 1: 方言 10：其他语言
//        secondLanguage：0:普通话 1: 方言 10：其他语言
//        fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
//        trainingMethod：1:ABA 2:其他训练疗法 3：无训练

        String states = "1";//是否完善（0 否 1是）
//        儿童性别（0：男 1：女）
        int sexInt = sex.equals("男") ? 0 : 1;
//        "自闭症： 轻", "自闭症： 中", "自闭症： 重", "自闭症： 不清楚", "语言发育迟缓（其它正常）",
//                "单纯性智力低下（无自闭症状）", "正常"

        String currentType_Medical = "";
        String medicalState = "";

        if (yixueZhenduan.equals("自闭症： 轻")) {
            currentType_Medical = "0";
            medicalState = "0";
        } else if (yixueZhenduan.equals("自闭症： 中")) {
            currentType_Medical = "0";
            medicalState = "1";
        } else if (yixueZhenduan.equals("自闭症： 重")) {
            currentType_Medical = "0";
            medicalState = "2";
        } else if (yixueZhenduan.equals("自闭症： 不清楚")) {
            currentType_Medical = "0";
            medicalState = "3";

        } else if (yixueZhenduan.equals("语言发育迟缓（其它正常）")) {
            currentType_Medical = "1";

        } else if (yixueZhenduan.equals("单纯性智力低下（无自闭症状）")) {
            currentType_Medical = "2";

        } else if (yixueZhenduan.equals("正常")) {
            currentType_Medical = "4";
        } else {
            currentType_Medical = "3";
            medicalState = etYixueZhenduan.getText().toString().trim();
        }

//        fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
//  {"小学-高中", "大学", "硕士研究生", "博士或类似"});
        if (dadCulture.equals("小学-高中")) {
            currentDadCul = "0";
        } else if (dadCulture.equals("大学")) {
            currentDadCul = "1";
        } else if (dadCulture.equals("硕士研究生")) {
            currentDadCul = "2";
        } else if (dadCulture.equals("博士或类似")) {
            currentDadCul = "3";
        }


        if (momCulture.equals("小学-高中")) {
            currentMomCul = "0";
        } else if (momCulture.equals("大学")) {
            currentMomCul = "1";
        } else if (momCulture.equals("硕士研究生")) {
            currentMomCul = "2";
        } else if (momCulture.equals("博士或类似")) {
            currentMomCul = "3";
        }
//        firstLanguage：0:普通话 1: 方言 10：其他语言
//        {"普通话", "方言", "其它语言_______"});
        String firstLanguagetype = "";
        String firstLanguageOtherType = "";

        if (!TextUtils.isEmpty(firstLanguage)) {
            if (firstLanguage.equals("普通话")) {
                firstLanguagetype = "0";
            } else if (firstLanguage.equals("方言")) {
                firstLanguagetype = "1";
            } else if (firstLanguage.equals("请选择或填写儿童第一语言")) {
            } else {

                firstLanguagetype = "10";
                firstLanguageOtherType = firstLanguage;
            }
        } else {
//            states = "0";//是否完善（0 否 1是）
        }

        String secondLanguagetype = "";
        String secondLanguageOtherType = "";

        if (!TextUtils.isEmpty(secondLanguage)) {
            if (secondLanguage.equals("普通话")) {
                secondLanguagetype = "0";
            } else if (secondLanguage.equals("方言")) {
                secondLanguagetype = "1";
            } else if (secondLanguage.equals("请选择或填写儿童第二语言")) {
            } else {
                secondLanguagetype = "10";
                secondLanguageOtherType = secondLanguage;

            }
        } else {
//            states = "0";//是否完善（0 否 1是）
        }
//      {"ABA训练", "其它疗法训练_____", "无训练"});
//        trainingMethod：1:ABA 2:其他训练疗法 3：无训练

        String trainingMethod = "";
        String trainingRests = "";

        if (trainingTreatment.equals("ABA训练")) {
            trainingMethod = "1";
        } else if (trainingTreatment.equals("无训练")) {
            trainingMethod = "3";
        } else if (trainingTreatment.equals("请选择目前尝试的训练及疗法")) {
        } else {
            trainingMethod = "2";
            trainingRests = trainingTreatment;
        }

//        countiyId	是	int	国际id
//        provinceId	是	int	省id
//        cityId	是	int	城市id
//        states	是	Sring	是否完善（0 否 1是）

        String url = Setting.perfectionAddChild();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);//
        stringStringHashMap.put("name", nickName);//
        stringStringHashMap.put("sex", sexInt + "");//
        stringStringHashMap.put("birthdate", birthdate);//
        stringStringHashMap.put("medical", currentType_Medical);//

        if (!TextUtils.isEmpty(medicalState)) {
            stringStringHashMap.put("medicalState", medicalState);//
        }
        if (!TextUtils.isEmpty(firstLanguagetype)) {
            stringStringHashMap.put("firstLanguage", firstLanguagetype);//
        } else {
            ToastUtil.showToast(mContext, "请选择或填写儿童第一语言");
            return;
        }
        if (!TextUtils.isEmpty(firstLanguageOtherType)) {
            stringStringHashMap.put("firstRests", firstLanguageOtherType);//
        }
        if (!TextUtils.isEmpty(secondLanguagetype)) {
            stringStringHashMap.put("secondLanguage", secondLanguagetype);//
        } else {
            ToastUtil.showToast(mContext, "请选择或填写儿童第二语言");
            return;
        }

        if (!TextUtils.isEmpty(secondLanguageOtherType)) {
            stringStringHashMap.put("secondRests", secondLanguageOtherType);//
        }
//        stringStringHashMap.put("secondRests", secondLanguageOtherType);//
        stringStringHashMap.put("fatherCulture", currentDadCul);//
        stringStringHashMap.put("motherCulture", currentMomCul);//

        if (!TextUtils.isEmpty(trainingMethod)) {
            stringStringHashMap.put("trainingMethod", trainingMethod);//
        } else {
            ToastUtil.showToast(mContext, "请选择目前尝试的训练及疗法");
            return;
        }

        if (!TextUtils.isEmpty(trainingRests)) {
            stringStringHashMap.put("trainingRests", trainingRests);//
        }

        if (countryAareaid != -1) {
            stringStringHashMap.put("countiyId", countryAareaid + "");//
        } else {
            ToastUtil.showToast(mContext, "请选择国家省份城市");
            return;
        }

        if (areaidProvince != -1) {
            stringStringHashMap.put("provinceId", areaidProvince + "");//
        } else {
//            ToastUtil.showToast(mContext, "请选择国家省份城市");
            stringStringHashMap.put("provinceId", -1+"");//
    //        return;
        }

        if (areaidCity != -1) {
            stringStringHashMap.put("cityId", areaidCity + "");//
        } else {
            stringStringHashMap.put("cityId", -1 + "");//
        }

        stringStringHashMap.put("states", states);//

        Log.e("数据", stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PerfectionChildrenInfoActivity.this, this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //    完善儿童个人信息
    private void perfectionAddChildOnFinished() {

        nickName = edtPhoneNumber.getText().toString();


        boolean b = MyUtils.compileExChar(nickName);
        if (b) {
            ToastUtil.showToast(mContext, "请输入字母，数字或文字形式的儿童昵称");
            return;
        }

        sex = tvSex.getText().toString();

        birthdate = tvBirthday.getText().toString();
        if (tvYixueZhenduan.getVisibility() == View.VISIBLE)
            yixueZhenduan = tvYixueZhenduan.getText().toString();
        else yixueZhenduan = etYixueZhenduan.getText().toString();
        //请选择或填写儿童第一语言
        if (tvFirstLanguage.getVisibility() == View.VISIBLE)
            firstLanguage = tvFirstLanguage.getText().toString();
        else firstLanguage = etFirstLanguage.getText().toString();
        //请选择或填写儿童第二语言
        if (tvSecondLanguage.getVisibility() == View.VISIBLE)
            secondLanguage = tvSecondLanguage.getText().toString();
        else secondLanguage = evSecondLanguage.getText().toString();
        dadCulture = tvDadCulture.getText().toString();

        momCulture = tvMomCulture.getText().toString();

        trainingTreatment = tvTrainingTreatment.getText().toString();


//        medical：0 “自闭症”, 1 “语言发育迟缓（其他正常）”, 2 “单纯性智力低下（无自闭症）”, 3 “其他诊断”, 4 “正常”,
//        medicalState：0：轻 1：中 2：重 3：不清楚 （当medical为0时才传）
//        firstLanguage：0:普通话 1: 方言 10：其他语言
//        secondLanguage：0:普通话 1: 方言 10：其他语言
//        fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
//        trainingMethod：1:ABA 2:其他训练疗法 3：无训练

        String states = "0";//是否完善（0 否 1是）
//        儿童性别（0：男 1：女）

        int sexInt = -1;
        if (sex.endsWith("男") || sex.endsWith("女")) {
            sexInt = sex.equals("男") ? 0 : 1;
        }
//        "自闭症： 轻", "自闭症： 中", "自闭症： 重", "自闭症： 不清楚", "语言发育迟缓（其它正常）",
//                "单纯性智力低下（无自闭症状）", "正常"

        String currentType_Medical = "";
        String medicalState = "";

        if (yixueZhenduan.equals("自闭症： 轻")) {
            currentType_Medical = "0";
            medicalState = "0";
        } else if (yixueZhenduan.equals("自闭症： 中")) {
            currentType_Medical = "0";
            medicalState = "1";
        } else if (yixueZhenduan.equals("自闭症： 重")) {
            currentType_Medical = "0";
            medicalState = "2";
        } else if (yixueZhenduan.equals("自闭症： 不清楚")) {
            currentType_Medical = "0";
            medicalState = "3";

        } else if (yixueZhenduan.equals("语言发育迟缓（其它正常）")) {
            currentType_Medical = "1";

        } else if (yixueZhenduan.equals("单纯性智力低下（无自闭症状）")) {
            currentType_Medical = "2";
        } else if (yixueZhenduan.equals("正常")) {
            currentType_Medical = "4";
        } else {
            currentType_Medical = "3";
            medicalState = etYixueZhenduan.getText().toString().trim();
        }

//        fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
//  {"小学-高中", "大学", "硕士研究生", "博士或类似"});
        if (dadCulture.equals("小学-高中")) {
            currentDadCul = "0";
        } else if (dadCulture.equals("大学")) {
            currentDadCul = "1";
        } else if (dadCulture.equals("硕士研究生")) {
            currentDadCul = "2";
        } else if (dadCulture.equals("博士或类似")) {
            currentDadCul = "3";
        }


        if (momCulture.equals("小学-高中")) {
            currentMomCul = "0";
        } else if (momCulture.equals("大学")) {
            currentMomCul = "1";
        } else if (momCulture.equals("硕士研究生")) {
            currentMomCul = "2";
        } else if (momCulture.equals("博士或类似")) {
            currentMomCul = "3";
        }
//        firstLanguage：0:普通话 1: 方言 10：其他语言
//        {"普通话", "方言", "其它语言_______"});
        String firstLanguagetype = "";
        String firstLanguageOtherType = "";

        if (!TextUtils.isEmpty(firstLanguage)) {
            if (firstLanguage.equals("普通话")) {
                firstLanguagetype = "0";
            } else if (firstLanguage.equals("方言")) {
                firstLanguagetype = "1";
            } else if (firstLanguage.equals("请选择或填写儿童第一语言")) {
            } else {

                firstLanguagetype = "10";
                firstLanguageOtherType = firstLanguage;
            }
        } else {
//            states = "0";//是否完善（0 否 1是）
        }

        String secondLanguagetype = "";
        String secondLanguageOtherType = "";

        if (!TextUtils.isEmpty(secondLanguage)) {
            if (secondLanguage.equals("普通话")) {
                secondLanguagetype = "0";
            } else if (secondLanguage.equals("方言")) {
                secondLanguagetype = "1";
            } else if (secondLanguage.equals("请选择或填写儿童第二语言")) {
            } else {
                secondLanguagetype = "10";
                secondLanguageOtherType = secondLanguage;

            }
        } else {
//            states = "0";//是否完善（0 否 1是）
        }
//      {"ABA训练", "其它疗法训练_____", "无训练"});
//        trainingMethod：1:ABA 2:其他训练疗法 3：无训练

        String trainingMethod = "";
        String trainingRests = "";

        if (trainingTreatment.equals("ABA训练")) {
            trainingMethod = "1";
        } else if (trainingTreatment.equals("无训练")) {
            trainingMethod = "3";
        } else if (trainingTreatment.equals("请选择目前尝试的训练及疗法")) {
        } else {
            trainingMethod = "2";
            trainingRests = trainingTreatment;
        }

//        countiyId	是	int	国际id
//        provinceId	是	int	省id
//        cityId	是	int	城市id
//        states	是	Sring	是否完善（0 否 1是）

        String url = Setting.perfectionAddChild();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);//
        if (!TextUtils.isEmpty(nickName)) {
            stringStringHashMap.put("name", nickName);
        }
        if (sexInt != -1) {
            stringStringHashMap.put("sex", sexInt + "");//
        }

        if (!TextUtils.isEmpty(birthdate)) {
            stringStringHashMap.put("birthdate", birthdate);//
        }
        if (!TextUtils.isEmpty(currentType_Medical)) {
            stringStringHashMap.put("medical", currentType_Medical);//
        }

        if (!TextUtils.isEmpty(medicalState)) {
            stringStringHashMap.put("medicalState", medicalState);//
        }
        if (!TextUtils.isEmpty(firstLanguagetype)) {
            stringStringHashMap.put("firstLanguage", firstLanguagetype);//
        }

        if (!TextUtils.isEmpty(firstLanguageOtherType)) {
            stringStringHashMap.put("firstRests", firstLanguageOtherType);//
        }
        if (!TextUtils.isEmpty(secondLanguagetype)) {
            stringStringHashMap.put("secondLanguage", secondLanguagetype);//
        }

        if (!TextUtils.isEmpty(secondLanguageOtherType)) {
            stringStringHashMap.put("secondRests", secondLanguageOtherType);//
        }

        if (!TextUtils.isEmpty(currentDadCul)) {
            stringStringHashMap.put("fatherCulture", currentDadCul);//
        }
        if (!TextUtils.isEmpty(currentMomCul)) {
            stringStringHashMap.put("motherCulture", currentMomCul);//
        }

        if (!TextUtils.isEmpty(trainingMethod)) {
            stringStringHashMap.put("trainingMethod", trainingMethod);//
        }

        if (!TextUtils.isEmpty(trainingRests)) {
            stringStringHashMap.put("trainingRests", trainingRests);//
        }

        if (countryAareaid != -1) {
            stringStringHashMap.put("countiyId", countryAareaid + "");//
        }

        if (areaidProvince != -1) {
            stringStringHashMap.put("provinceId", areaidProvince + "");//
        }else {
            stringStringHashMap.put("provinceId",  -1+ "");//
        }

        if (areaidCity != -1) {
            stringStringHashMap.put("cityId", areaidCity + "");//
        }else {
            stringStringHashMap.put("cityId", -1 + "");//
        }

        stringStringHashMap.put("states", states);//

        Log.e("数据", stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(2444)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PerfectionChildrenInfoActivity.this, this));
    }

    /**
     */

    private void getChilInfo() {
        String url = Setting.getChilInfo();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);//

        OkHttpUtils
                .post().params(stringStringHashMap)//入参
                .url(url)//接口地址
                .id(4433)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PerfectionChildrenInfoActivity.this, this));
    }

    /**
     * 获取三级城市列表
     */

    private void systemGetThisCityList() {

        String url = Setting.systemGetThisCityList();

        OkHttpUtils
                .post()
                .url(url)//接口地址
                .id(44)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(PerfectionChildrenInfoActivity.this, this));
    }

    ArrayList<Province> locationData = new ArrayList<>();

    public void onOptionPicker(View view) {

        AddressPicker picker = new AddressPicker(PerfectionChildrenInfoActivity.this, locationData);
        picker.setHideProvince(false);
        picker.setHideCounty(false);
        setWheelStyle(picker);
        picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);//省级、地级和县级的比例为2:3:3

//        picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(Province province, City city, County county) {

                if (null != county) {
                    tvLocation.setText(province.getAreaName() + "-" + city.getAreaName() + "-" + county.getAreaName());
                    countryAareaid = Integer.parseInt(province.getAreaId());
                    areaidProvince = Integer.parseInt(city.getAreaId());
                    areaidCity = Integer.parseInt(county.getAreaId());
                } else if (null != city) {
                    tvLocation.setText(province.getAreaName() + "-" + city.getAreaName());
                    countryAareaid = Integer.parseInt(province.getAreaId());
                    areaidProvince = Integer.parseInt(city.getAreaId());
                    areaidCity = -1;
                } else if (null != province) {
                    tvLocation.setText(province.getAreaName());
                    countryAareaid = Integer.parseInt(province.getAreaId());
                    areaidProvince = -1;
                    areaidCity = -1;
                }

            }
        });
        picker.show();

    }


    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(2444)) {//标记那个接口

            String result = (String) data;
            (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            if (finishType.equals("back")) {
                                finish();
                            } else if (finishType.equals("mainback")) {
                                Intent intent = new Intent();
                                PerfectionChildrenInfoActivity.this.setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else if (finishType.equals("firstregister")) {
                                EventBusUtil.post(new RegisterDestroyOtherBean());

                                Intent intent = new Intent(PerfectionChildrenInfoActivity.this,
                                        BalloonActivity.class);
                                // intent.putExtra("isFirstregister", true);
                                startActivity(intent);
                                finish();
                            } else {
                                finish();
                            }
                        } else {
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(4433)) {//标记那个接口

            String result = (String) data;
            (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            ChildInfoBean model1 = gson.fromJson(result,
                                    new TypeToken<ChildInfoBean>() {
                                    }.getType());


                            ChildInfoBean.DataBean data1 = model1.getData();
                            if (data1.getXydChild().getName() != null) {
                                edtPhoneNumber.setText(data1.getXydChild().getName() + "");
                            }
                            if (data1.getXydChild().getBirthdate() != null) {
                                long time = Long.parseLong(data1.getXydChild().getBirthdate()) / 1000;
                                tvBirthday.setText(getStrTime(time + ""));
                            }


                            String sex = data1.getXydChild().getSex();
                            if (!TextUtils.isEmpty(sex)) {
                                if (sex.endsWith("0")) {//   儿童性别（0：男 1：女）
                                    tvSex.setText("男");
                                } else {
                                    tvSex.setText("女");
                                }
                            }
//                            tvBirthday.setText();
//        medical：0 “自闭症”, 1 “语言发育迟缓（其他正常）”, 2 “单纯性智力低下（无自闭症）”, 3 “其他诊断”, 4 “正常”,
//        medicalState：0：轻 1：中 2：重 3：不清楚 （当medical为0时才传）
                            String medical = data1.getXydChild().getMedical();
                            String medicalState = data1.getXydChild().getMedicalState();

                            if (!TextUtils.isEmpty(medical)) {
                                if (medical.equals("0")) {

                                    if (!TextUtils.isEmpty(medicalState)) {
                                        if (medicalState.equals("0")) {
                                            tvYixueZhenduan.setText("自闭症： 轻");
                                        } else if (medicalState.equals("1")) {
                                            tvYixueZhenduan.setText("自闭症： 中");

                                        } else if (medicalState.equals("2")) {
                                            tvYixueZhenduan.setText("自闭症： 重");
                                        } else if (medicalState.equals("3")) {
                                            tvYixueZhenduan.setText("自闭症： 不清楚");
                                        }
                                    }
                                } else if (medical.equals("1）")) {
                                    tvYixueZhenduan.setText("语言发育迟缓（其他正常）");
                                } else if (medical.equals("2")) {
                                    tvYixueZhenduan.setText("单纯性智力低下（无自闭症）");
                                } else if (medical.equals("3")) {
                                    tvYixueZhenduan.setText("");
                                    tvYixueZhenduan.setVisibility(View.GONE);
                                    etYixueZhenduan.setVisibility(View.VISIBLE);
                                    etYixueZhenduan.setHint("请输入医学诊断");
                                    etYixueZhenduan.setFocusable(true);
                                    etYixueZhenduan.setFocusableInTouchMode(true);
                                    etYixueZhenduan.setHintTextColor(getResources().getColor(R.color.text_tu));
                                    etYixueZhenduan.requestFocus();
                                    etYixueZhenduan.setText(medicalState);
                                } else if (medical.equals("4")) {
                                    tvYixueZhenduan.setText("正常");
                                }
                            }

                            //请选择或填写儿童第一语言
                            String firstLanguage = data1.getXydChild().getFirstLanguage();
                            if (!TextUtils.isEmpty(firstLanguage)) {
                                if (firstLanguage.equals("0")) {
                                    tvFirstLanguage.setText("普通话");
                                } else if (firstLanguage.equals("1")) {
                                    tvFirstLanguage.setText("方言");
                                } else {
                                    String firstRests = data1.getXydChild().getFirstRests();
                                    tvFirstLanguage.setVisibility(View.GONE);
                                    tvFirstLanguage.setText("");
                                    etFirstLanguage.setVisibility(View.VISIBLE);
                                    etFirstLanguage.setFocusable(true);
                                    etFirstLanguage.setFocusableInTouchMode(true);
                                    etFirstLanguage.setHintTextColor(getResources().getColor(R.color.text_tu));
                                    etFirstLanguage.requestFocus();
                                    etFirstLanguage.setText(firstRests);
                                }
                            }

                            String secondLanguage = data1.getXydChild().getSecondLanguage();
                            if (!TextUtils.isEmpty(secondLanguage)) {
                                if (secondLanguage.equals("0")) {
                                    tvSecondLanguage.setText("普通话");
                                } else if (secondLanguage.equals("1")) {
                                    tvSecondLanguage.setText("方言");
                                } else {
                                    String secondRests = data1.getXydChild().getSecondRests();
                                    tvSecondLanguage.setText("");
                                    tvSecondLanguage.setVisibility(View.GONE);
                                    evSecondLanguage.setVisibility(View.VISIBLE);
                                    evSecondLanguage.setFocusable(true);
                                    evSecondLanguage.setFocusableInTouchMode(true);
                                    evSecondLanguage.setHintTextColor(getResources().getColor(R.color.text_tu));
                                    evSecondLanguage.requestFocus();
                                    evSecondLanguage.setText(secondRests);
                                }
                            }

                            String address = data1.getXydChild().getAddress();
                            if (!TextUtils.isEmpty(address)) {
                                if (!address.contains("null")) {
                                    Matcher matcher=Pattern.compile("-").matcher(address);
                                    if(matcher.find()){
                                    String  address1 =   address.substring(0,matcher.start());
                                        if(!address.contains("中国")){
                                            tvLocation.setText(address1);
                                        }else {
                                            tvLocation.setText(address);
                                        }

                                    }
//                                    tvLocation.setText(address);

                                }else {
                                    Matcher matcher=Pattern.compile("-").matcher(address);
                                    if(matcher.find()){
                                         address =   address.substring(0,matcher.start());
                                        tvLocation.setText(address);
                                    }
                                }
                                if(data1.getXydChild().getCityId() != -1)
                                    countryAareaid = Integer.valueOf(data1.getXydChild().getCountiyId());
                                if(data1.getXydChild().getProvinceId() != -1)
                                    areaidProvince = Integer.valueOf(data1.getXydChild().getProvinceId());
                                if(data1.getXydChild().getCityId() != -1)
                                    areaidCity = Integer.valueOf(data1.getXydChild().getCityId());
                            }
//                            "countiy": "中国",
//                                    "province": "宁夏",
//                                    "city": "银川",

//        medical：0 “自闭症”, 1 “语言发育迟缓（其他正常）”, 2 “单纯性智力低下（无自闭症）”, 3 “其他诊断”, 4 “正常”,
//        medicalState：0：轻 1：中 2：重 3：不清楚 （当medical为0时才传）
//        firstLanguage：0:普通话 1: 方言 10：其他语言
//        secondLanguage：0:普通话 1: 方言 10：其他语言
//        fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
//        trainingMethod：1:ABA 2:其他训练疗法 3：无训练
//                               * fatherCulture : 1
//                               * motherCulture : 2
//                            {"小学-高中", "大学", "硕士研究生", "博士或类似"});
                            String fatherCulture = data1.getXydChild().getFatherCulture();
                            if (!TextUtils.isEmpty(fatherCulture)) {
                                if (fatherCulture.equals("0")) {
                                    tvDadCulture.setText("小学-高中");
                                } else if (fatherCulture.equals("1")) {
                                    tvDadCulture.setText("大学");
                                } else if (fatherCulture.equals("2")) {
                                    tvDadCulture.setText("硕士研究生");
                                } else {
                                    tvDadCulture.setText("博士或类似");
                                }
                            }
                            String motherCulture = data1.getXydChild().getMotherCulture();
                            if (!TextUtils.isEmpty(motherCulture)) {
                                if (motherCulture.equals("0")) {
                                    tvMomCulture.setText("小学-高中");
                                } else if (motherCulture.equals("1")) {
                                    tvMomCulture.setText("大学");
                                } else if (motherCulture.equals("2")) {
                                    tvMomCulture.setText("硕士研究生");
                                } else {
                                    tvMomCulture.setText("博士或类似");
                                }
                            }
                            String trainingMethod = data1.getXydChild().getTrainingMethod();
                            String trainingRests = data1.getXydChild().getTrainingRests();

                            if (!TextUtils.isEmpty(trainingMethod)) {
                                if (trainingMethod.equals("1")) {
                                    tvTrainingTreatment.setText("ABA训练");
                                } else if (trainingMethod.equals("3")) {
                                    tvTrainingTreatment.setText("无训练");
                                } else if (trainingMethod.equals("2")) {
                                    if (!TextUtils.isEmpty(trainingRests)) {
                                        tvTrainingTreatment.setVisibility(View.GONE);
                                        tvTrainingTreatment.setText("");
                                        evTrainingTreatment.setVisibility(View.VISIBLE);
                                        evTrainingTreatment.setFocusable(true);
                                        evTrainingTreatment.setFocusableInTouchMode(true);
                                        evTrainingTreatment.setHintTextColor(getResources().getColor(R.color.text_tu));
                                        evTrainingTreatment.requestFocus();
                                        evTrainingTreatment.setText(trainingRests);
                                    }
                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(44)) {//标记那个接口

            String result = (String) data;
            (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Gson gson = new Gson();
                            model1 = gson.fromJson(result,
                                    new TypeToken<GetThisCityListBean>() {
                                    }.getType());

                            getCountryList = new ArrayList();
                            for (int i = 0; i < model1.getData().getCountryList().size(); i++) {
                                String areaname = model1.getData().getCountryList().get(i).getAreaname();
                                int areaid = model1.getData().getCountryList().get(i).getAreaid();

                                getCountryList.add(areaname);

                                Province province = new Province();
                                province.setAreaName(areaname);
                                province.setAreaId(areaid + "");

                                ArrayList<City> cityData = new ArrayList<>();

                                for (int j = 0; j < model1.getData().getProvinceList().size(); j++) {
                                    int parentid = model1.getData().getProvinceList().get(j).getParentid();
                                    String areaname1 = model1.getData().getProvinceList().get(j).getAreaname();
                                    int areaid1 = model1.getData().getProvinceList().get(j).getAreaid();

                                    if (parentid == areaid) {
                                        City city = new City(areaid1 + "", areaname1);
                                        cityData.add(city);

                                        ArrayList<County> countyData = new ArrayList<>();

                                        for (int k = 0; k < model1.getData().getCityList().size(); k++) {
//                                            GetThisCityListBean.DataBean.CityListBean cityListBean = model1.getData().getCityList().get(k);
                                            int parentid12 = model1.getData().getCityList().get(k).getParentid();

                                            String areaname12 = model1.getData().getCityList().get(k).getAreaname();
                                            int areaid12 = model1.getData().getCityList().get(k).getAreaid();

                                            if (parentid12 == areaid1) {
                                                County county = new County(areaid12 + "", areaname12);
                                                countyData.add(county);
                                            }
                                        }

                                        city.setCounties(countyData);
                                    }

                                }
                                province.setCities(cityData);

                                locationData.add(province);
                            }

                            getProvinceList = new ArrayList();
//                            for (int i = 0; i < model1.getData().getProvinceList().size(); i++) {
//                                String areaname = model1.getData().getProvinceList().get(i).getAreaname();
//                                getProvinceList.add(areaname);
//                            }
//
                            getCityList = new ArrayList();
//                            for (int i = 0; i < model1.getData().getCityList().size(); i++) {
//                                String areaname = model1.getData().getCityList().get(i).getAreaname();
//                                getCityList.add(areaname);
//                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(1)) {//标记那个接口

            String result = (String) data;
            (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (object.equals(2)) {//标记那个接口  完善个人信息成功回调

            String result = (String) data;
            (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);

                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");
//                        {"msg":"您已完善了儿童个人信息！","code":201,"data":""}
                        if (code.equals("200")) {
                            Intent intent = new Intent(PerfectionChildrenInfoActivity.this, AssessActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }


    @Override
    public void RequestError(Object object, int errorId, final String errorMessage) {

        (PerfectionChildrenInfoActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(PerfectionChildrenInfoActivity.this, errorMessage);
            }
        });

    }

    /**
     * 出生日期选择
     */
    private void yearMonthDay() {
        String currentYear = getCurrentYear();

        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        //获取默认选中的日期的年月日星期的值，并赋值
        Calendar calendar = Calendar.getInstance();
        picker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        picker.setRangeStart(1910, 8, 29);
        picker.setSelectedItem(Integer.parseInt(currentYear), month, DATE);
        picker.setResetWhileWheel(false);
        picker.setTitleText("请选择出生日期");
        picker.setTitleTextColor(getResources().getColor(R.color.hinttext));
        picker.setCancelTextColor(getResources().getColor(R.color.hinttext));
        picker.setSubmitTextColor(getResources().getColor(R.color.hinttext));

        picker.setTextColor(getResources().getColor(R.color.black));
        picker.setLabelTextColor(getResources().getColor(R.color.white));
        picker.setDividerColor(getResources().getColor(R.color.gray_line));

        picker.setShadowColor(getResources().getColor(R.color.white));
        picker.setTopLineColor(getResources().getColor(R.color.gray_line));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvBirthday.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
//                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Date d = new Date();
        String t = sdf.format(d);
        String y = t.substring(0, 4);
        return y;
    }
// TODO: 2018/10/30 0030

    /**
     * 儿童昵称	用户填写	允许输入字母，数字或文字的儿童昵称，不允许输入特殊符号，当输入特殊符号时，提示“请输入字母，数字或文字形式的儿童昵称”。输入位数为1-13位。
     * 性别	用户选择	浮窗显示男女选项，点击选择。
     * 出生日期	用户选择	用户通过旋转日期滚轮选择宝宝出生的年月日
     * 长期居住地	用户选择	将中国所有的省市做成滚轮用户选择，并且多一个其它国家选项。
     * 医学诊断	用户选择	家长可以选择四个选项其中的一个，点选后可以重新选择。
     * 选项1：自闭症 ，选择后，需要选程度： 轻 中 重 不清楚
     * 选项2：语言发育迟缓（其它正常）
     * 选项3：单纯性智力低下（无自闭症状）
     * 选项4：正常
     * 请选择或填写儿童第一语言	用户选择或填写	选项1：普通话
     * 选项2：方言
     * 选项3：其它语言_______
     * 当用户选择第三个选项的时候，页面本栏变成输入栏，光标提示，同时文案变成：“请输入语言名称”
     * 请选择或填写儿童第二语言	用户选择或填写	选项1：普通话
     * 选项2：方言
     * 选项3：其它语言_______
     * 当用户选择第三个选项的时候，页面本栏变成输入栏，光标提示，同时文案变成：“请输入语言名称”
     * 请选择父亲最高文化程度	用户选择	选项1：小学-高中   选项2：大学
     * 选项3：硕士研究生  选项4：博士或类似
     * 请选择母亲最高文化程度	用户选择	选项1：小学-高中   选项2：大学
     * 选项3：硕士研究生  选项4：博士或类似
     * 请选择目前尝试的训练及疗法	用户选择或填写	选项1：ABA训练  选项2：其它疗法训练_____
     * 选项3：无训练
     * 当用户选择第三个选项的时候，页面本栏变成输入栏，光标提示，同时文案变成：“请输入其它疗法名称”
     * 退出按钮		点击退出进入“家长中心—更多”页面。
     * 完成		点击完成进入问卷评估页面
     * 注：如果儿童未填写完儿童信息就返回了，再次进入此页面时，已经填写的信息保留，可以修改，继续填写完成信息完善.
     * <p>
     * (@Field("token") String token, @Field("name") String name
     * , @Field("sex") String sex, @Field("birthdate") String birthdate, @Field("medical") String medical,
     *
     * @Field("medicalState") String medicalState, @Field("firstLanguage") String firstLanguage
     * , @Field("secondLanguage") String secondLanguage, @Field("secondRests") String secondRests
     * , @Field("fatherCulture") String fatherCulture
     * , @Field("motherCulture") String motherCulture
     * , @Field("trainingMethod") String trainingMethod
     * , @Field("trainingRests") String trainingRests
     * , @Field("states") String states);
     */


//    @Override
//    public void onClick(View view) {
//
//    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()) {
            cityPicker.close();
            return;
        }

        super.onBackPressed();
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

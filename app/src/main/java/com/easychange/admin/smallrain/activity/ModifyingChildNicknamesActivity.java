package com.easychange.admin.smallrain.activity;

import android.content.Intent;
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
import com.google.gson.Gson;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import bean.NickNameChangeBean;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create 2018/10/18 0018
 * by 李
 * 页面注释：type 0昵称 1密码 2更换手机号码 345 6完成
 **/
public class ModifyingChildNicknamesActivity extends BaseActivity implements AsyncRequest {

    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.img_home_left)
    ImageView left;
    @BindView(R.id.img_home_right)
    ImageView right;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_one)
    EditText etOne;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.layout_pt_content)
    LinearLayout layoutPtContent;
    private String token;

//1.“修改儿童昵称”字段不可点。
//            2.方框中可输入新的儿童昵称，要求与原儿童昵称输入格式要求相同。
//            4.返回：点击返回到建议与设置页面。且儿童昵称没有变化。
//            5.确定：点确定，返回建议与设置页面。且儿童昵称显示为修改后的新字段。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifying_child_nicknames);
        ButterKnife.bind(this);

        etOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = etOne.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    etOne.setText(str);
                    //设置新的光标所在位置
                    etOne.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initEvent();
    }
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    private void initEvent() {
        left.setOnClickListener(onClickListener);
        right.setOnClickListener(onClickListener);
        tvSure.setOnClickListener(onClickListener);
    }

    private String newNickName = "";
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_home_left:
                    finish();
                    break;
                case R.id.tv_sure:

                    newNickName = etOne.getText().toString();
                    if (!TextUtils.isEmpty(newNickName)) {
                        updateChildInfo(newNickName);
                    } else {
                        ToastUtil.showToast(mContext, "请输入儿童昵称");
                    }

                    break;
                case R.id.img_home_right:
                    finish();
                    break;
            }
        }
    };


    /**
     * 修改儿童昵称
     * token	是	string	标识
     * name	是	string	昵称
     *
     * @param name
     */
    private void updateChildInfo(String name) {
        PreferencesHelper helper = new PreferencesHelper(ModifyingChildNicknamesActivity.this);
        String token = helper.getToken();

        String url = Setting.updateChildInfo();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("token", token);
        stringStringHashMap.put("name", name);

        OkHttpUtils
                .post().params(stringStringHashMap)
                .url(url)//接口地址
                .id(1)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(ModifyingChildNicknamesActivity.this, this));

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
            (ModifyingChildNicknamesActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
                            Intent intent = new Intent();
                            intent.putExtra("name", newNickName);
                            setResult(RESULT_OK, intent);

                            PreferencesHelper helper = new PreferencesHelper(ModifyingChildNicknamesActivity.this);
                            helper.saveString("sp", "nickname", newNickName);
                            EventBusUtil.post(new NickNameChangeBean());

                            ToastUtil.showToast(mContext, "修改儿童昵称成功");
                            finish();
                        } else {
                            ToastUtil.showToast(ModifyingChildNicknamesActivity.this, msg1);
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
        (ModifyingChildNicknamesActivity.this).runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {
                ToastUtil.showToast(ModifyingChildNicknamesActivity.this, errorMessage);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

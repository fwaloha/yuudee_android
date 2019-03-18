package com.easychange.admin.smallrain.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.event.OnOrCodeClickListener;
import com.easychange.admin.smallrain.utils.GlideUtil;

public class OrCodeDialog extends Dialog {
    TextView saveBtn;
    ImageView shareIv;
    public ImageView ivQrcode;
    private Context context;
    private String qrUrl;

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    private OnOrCodeClickListener onOrCodeClickListener;

    public OrCodeDialog(@NonNull Context context) {
        super(context, R.style.orcode_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_or_code);
        setCanceledOnTouchOutside(true);


        Window window = getWindow();
        WindowManager m = window.getWindowManager();
        WindowManager.LayoutParams lp = window.getAttributes();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        //不对就换
        //window.setWindowAnimations(R.style.orcode_dialog);
        initView();
        initEvent();
    }

    private void initEvent() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存
                if (onOrCodeClickListener != null) {
                    onOrCodeClickListener.onSave("");
                }
            }
        });
        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOrCodeClickListener != null) {
                    //分享
                    onOrCodeClickListener.onShared("");
                }
            }
        });
    }

    private void initView() {
        saveBtn = findViewById(R.id.tv_save);
        shareIv = findViewById(R.id.iv_share);
        ivQrcode = findViewById(R.id.iv_qrcode);
        GlideUtil.display(context, getQrUrl(), ivQrcode);
    }

    public void setOnOrCodeClickListener(OnOrCodeClickListener onOrCodeClickListener) {
        this.onOrCodeClickListener = onOrCodeClickListener;
    }
}

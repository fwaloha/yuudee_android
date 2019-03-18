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
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.event.OnQuestionContinueClickListener;

/**
 * create 2018/10/17 0017
 * by 李
 * 页面注释：ABC问卷 退出时 弹出框
 **/
public class QuestionHintDialog extends Dialog {
    private OnQuestionContinueClickListener onQuestionContinueClickListener;
    private TextView tvCom,tvEx;
    public QuestionHintDialog(@NonNull Context context) {
        super(context, R.style.orcode_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_question_hint);
        setCanceledOnTouchOutside(true);


        Window window=getWindow();
        WindowManager m=window.getWindowManager();
        WindowManager.LayoutParams lp =window.getAttributes();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);

        initView();
        initEvent();
    }

    private void initEvent() {
        tvEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onQuestionContinueClickListener!=null){
                    onQuestionContinueClickListener.onEx();
                }
            }
        });
        tvCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onQuestionContinueClickListener!=null){
                    onQuestionContinueClickListener.onCom();
                }
            }
        });
    }

    private void initView() {
        tvCom=findViewById(R.id.tv_ex);
        tvEx=findViewById(R.id.tv_com);
    }

    public void setOnQuestionContinueClickListener(OnQuestionContinueClickListener onQuestionContinueClickListener) {
        this.onQuestionContinueClickListener = onQuestionContinueClickListener;
    }
}

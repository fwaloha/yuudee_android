package com.easychange.admin.smallrain.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.entity.QuestionListBean;
import com.easychange.admin.smallrain.event.OnCheckedChangeClickListener;

/**
 * create 2018/10/16 0016
 * by 李
 * 页面注释：
 **/
public class QuestionChildAdapter extends RecyclerViewAdapter<QuestionListBean.ChildBean> {
    private OnCheckedChangeClickListener onCheckedChangeClickListener;
    public QuestionChildAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_question_child);
    }


    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, final int position, QuestionListBean.ChildBean model) {
        viewHolderHelper.setText(R.id.tv_content,model.getContent());
        CheckBox checkBox=viewHolderHelper.getView(R.id.cb_check);
        checkBox.setChecked(model.getTrue());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onCheckedChangeClickListener!=null){
                    onCheckedChangeClickListener.onPos(position);
                }
            }
        });
    }

    public void setOnCheckedChangeClickListener(OnCheckedChangeClickListener onCheckedChangeClickListener) {
        this.onCheckedChangeClickListener = onCheckedChangeClickListener;
    }
}

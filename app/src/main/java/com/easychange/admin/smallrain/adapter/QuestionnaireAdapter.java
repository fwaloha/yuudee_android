package com.easychange.admin.smallrain.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.QuestionnaireDetailsActivity;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.entity.QuestionnaireListBean;
import com.easychange.admin.smallrain.event.OnItemChildClickListener;

public class QuestionnaireAdapter extends RecyclerViewAdapter<QuestionnaireListBean> {

    private Intent intent;
    public QuestionnaireAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_questionnaire);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, final QuestionnaireListBean model) {
        Button titleBtn=viewHolderHelper.getView(R.id.btn_title);
        titleBtn.setText(model.getTitle());
        viewHolderHelper.setText(R.id.tv_content,model.getContent());
        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG",model.getTitle());
                intent=new Intent(mContext,model.getActivity());
                mContext.startActivity(intent);
            }
        });;
    }
}

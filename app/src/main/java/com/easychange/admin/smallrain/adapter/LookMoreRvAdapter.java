package com.easychange.admin.smallrain.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.entity.QuestionnaireListBean;


public class LookMoreRvAdapter extends RecyclerViewAdapter<String> {

    private Intent intent;

    public LookMoreRvAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_look_more);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, final String model) {
        viewHolderHelper.setText(R.id.tv_position, "第" + (position+1) + "次测试正确率：");
        try {
            viewHolderHelper.setText(R.id.tv_position_accuracy, ((int)(Float.valueOf(model)*100))+"%");
        }catch (Exception e){
            viewHolderHelper.setText(R.id.tv_position_accuracy, 0+"%");
        }

//        Button titleBtn=viewHolderHelper.getView(R.id.btn_title);
//        titleBtn.setText(model.getTitle());
//        ; viewHolderHelper.setText(R.id.tv_content,model.getContent());
//        titleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("TAG",model.getTitle());
//                intent=new Intent(mContext,model.getActivity());
//                mContext.startActivity(intent);
//            }
//        });
    }
}

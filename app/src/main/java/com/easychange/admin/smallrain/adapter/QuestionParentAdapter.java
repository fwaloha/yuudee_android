package com.easychange.admin.smallrain.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.RecyclerViewAdapter;
import com.easychange.admin.smallrain.base.ViewHolderHelper;
import com.easychange.admin.smallrain.entity.QuestionListBean;
import com.easychange.admin.smallrain.event.OnCheckedChangeClickListener;

import java.util.List;

/**
 * create 2018/10/16 0016
 * by 李
 * 页面注释：
 **/
public class QuestionParentAdapter extends RecyclerViewAdapter<QuestionListBean>{
    private QuestionChildAdapter adapter;
    private  RecyclerView recyclerView;
    private List<QuestionListBean> listBean;
    public QuestionParentAdapter(RecyclerView recyclerView,List<QuestionListBean> listBean) {
        super(recyclerView, R.layout.item_question_parent);
        this.listBean = listBean;
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper,final   int position, final QuestionListBean model) {
       recyclerView=viewHolderHelper.getView(R.id.rl_child);
       viewHolderHelper.setText(R.id.tv_title,listBean.get(position).getTitle());
       adapter=new QuestionChildAdapter(recyclerView);
       recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
       adapter.setData(listBean.get(position).getChildBean());

       adapter.setOnCheckedChangeClickListener(new OnCheckedChangeClickListener() {
           @Override
           public void onPos(int pos) {
               for (QuestionListBean.ChildBean childBean:listBean.get(position).getChildBean()){
                   childBean.setTrue(false);
               }
               listBean.get(position).getChildBean().get(pos).setTrue(true);
               adapter.notifyItemChanged(pos);
           }
       });
    }
}

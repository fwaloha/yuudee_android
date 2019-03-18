package com.easychange.admin.smallrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.entity.CityListBean;

import java.util.List;


/**
 * 品牌适配器
 * Created by chenlipeng on 16/3/23.
 */
public class SelectCityAdapter extends BaseAdapter implements SectionIndexer {

    private List<CityListBean> list = null;
    private Context mContext;

//    private com.nostra13.universalimageloader.core.ImageLoader mImageLoader;
//    private DisplayImageOptions mOptions;

    private String usedCity;//最近访问城市
    private String locationCity;//定位城市


    public SelectCityAdapter(Context mContext, List<CityListBean> list) {

//        this.mImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
//        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//        this.mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_bg_s)
//                .showImageOnFail(R.drawable.icon_bg_s)
//                .showImageForEmptyUri(R.drawable.icon_bg_s)
//                .cacheInMemory()
//                .cacheOnDisc()
//                .imageScaleType(ImageScaleType.EXACTLY).build();

        this.mContext = mContext;
        this.list = list;

        this.usedCity = usedCity;
        this.locationCity = locationCity;

        //    mImageLoader.init(ImageLoaderConfiguration.createDefault(this.mContext));
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<CityListBean> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {

        ViewHolder viewHolder = null;

        final CityListBean bean = list.get(position);

        if (view == null) {

            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.select_city_list_item, null);

            viewHolder.select_city_letter = (TextView) view.findViewById(R.id.select_city_letter);
            viewHolder.select_city_name = (TextView) view.findViewById(R.id.select_city_name);
            viewHolder.tv_bianhao = (TextView) view.findViewById(R.id.tv_bianhao);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)&&!bean.getAlphaCode().startsWith("热门")) {

            viewHolder.select_city_letter.setVisibility(View.VISIBLE);
            viewHolder.select_city_letter.setText(bean.getAlphaCode());

        } else {
            viewHolder.select_city_letter.setVisibility(View.GONE);
        }

        viewHolder.select_city_name.setText(bean.getCityName());
        viewHolder.tv_bianhao.setText(bean.getPhonePrefix());


        return view;

    }


    final static class ViewHolder {

        private TextView select_city_letter;
        private TextView select_city_name;

        public TextView tv_bianhao;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getAlphaCode().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getAlphaCode();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }
}








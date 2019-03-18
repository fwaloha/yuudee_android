package com.easychange.admin.smallrain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * admin  2018/8/16 wan
 */
public class FragPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> lists;

    public FragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragPagerAdapter(FragmentManager fm, List<Fragment> lists) {
        super(fm);
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}

package com.smartloan.smtrick.laundryapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> titleArrayList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragement(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        titleArrayList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArrayList.get(position);
    }
}

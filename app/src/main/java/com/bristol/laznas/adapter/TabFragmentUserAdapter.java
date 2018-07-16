package com.bristol.laznas.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bristol.laznas.DonasiFragment;
import com.bristol.laznas.SahabatAmilFragment;

public class TabFragmentUserAdapter extends FragmentPagerAdapter {
    String[] title = new String[]{
            "Sahabat AMil"
    };
    public TabFragmentUserAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new SahabatAmilFragment();
                break;
            default:
                fragment = null;

                break;

        }
        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


    @Override
    public int getCount() {
        return title.length;
    }
}

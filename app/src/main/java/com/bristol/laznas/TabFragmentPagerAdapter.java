package com.bristol.laznas;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bristol.laznas.utils.SessionManager;


public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    //nama tab nya

    String[] title = new String[]{
            "Gerai", "Sahabat AMil"
    };
    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    //method ini yang akan memanipulasi penampilan Fragmen dilayar
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DonasiFragment();
                break;
            case 1:
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


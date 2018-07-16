package com.bristol.laznas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bristol.laznas.BerandaFragmentLaznas;
import com.bristol.laznas.KategoriFragment;
import com.bristol.laznas.ProfilFragmentLaznas;

public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                BerandaFragmentLaznas tab1 = new BerandaFragmentLaznas();
                Log.v("tab1", tab1.toString());
                return tab1;
            case 1:
                DonasiTabFragment tab4 = new DonasiTabFragment();
                Log.v("tab4", tab4.toString());
                return tab4;
            case 2:
                KategoriFragment tab2 = new KategoriFragment();
                Log.v("tab2", tab2.toString());
                return tab2;
            case 3:
                ProfilFragmentLaznas tab3 = new ProfilFragmentLaznas();
                Log.v("tab3", tab3.toString());
                return tab3;
            default:
                return null;
        }
    }


    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
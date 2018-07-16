package com.bristol.laznas;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bristol.laznas.adapter.TabFragmentUserAdapter;
import com.bristol.laznas.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonasiTabFragment extends Fragment {
    View rootViewDonasi;
    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabs;
    private FragmentActivity myContext;
    SessionManager sessionManager;
    public DonasiTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootViewDonasi = inflater.inflate(R.layout.fragment_donasi_tab, container, false);
        sessionManager = new SessionManager(getContext());
//inisialisasi tab dan pager
        pager = (ViewPager)rootViewDonasi.findViewById(R.id.pager);
        tabs = (TabLayout)rootViewDonasi.findViewById(R.id.tabs);

//set object adapter kedalam ViewPager
        if(!sessionManager.getRolesId().equals("1") && !sessionManager.getRolesId().equals("2")){
            pager.setAdapter(new
                    TabFragmentPagerAdapter(getChildFragmentManager()));
        }else {
            pager.setAdapter(new
//                    TabFragmentPagerAdapter(getChildFragmentManager()));
                    TabFragmentUserAdapter(getChildFragmentManager()));
        }



//Manimpilasi sedikit untuk set TextColor pada Tab
        tabs.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        tabs.setTabTextColors(getResources().getColor(R.color.orangemuda),
                getResources().getColor(android.R.color.white));


//set tab ke ViewPager
        tabs.setupWithViewPager(pager);

//konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        return  rootViewDonasi;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}

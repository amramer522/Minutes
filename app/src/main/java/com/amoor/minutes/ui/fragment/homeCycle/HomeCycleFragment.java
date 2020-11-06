package com.amoor.minutes.ui.fragment.homeCycle;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.MyPagerAdapter;
import com.amoor.minutes.ui.activity.home.HomeActivity;
import com.amoor.minutes.ui.activity.user.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCycleFragment extends Fragment {


    @BindView(R.id.content_home_tab)
    TabLayout contentHomeTab;
    @BindView(R.id.content_home_pager)
    ViewPager contentHomePager;
    Unbinder unbinder;
    @BindView(R.id.Home_Cycle_Fragment_Srl_Swipe)
    SwipeRefreshLayout HomeCycleFragmentSrlSwipe;

    public HomeCycleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_cycle, container, false);
        unbinder = ButterKnife.bind(this, view);

        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        contentHomePager.setAdapter(adapter);
        contentHomeTab.setupWithViewPager(contentHomePager);
        contentHomePager.setCurrentItem(1);

        HomeCycleFragmentSrlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                int selectedTabPosition = contentHomeTab.getSelectedTabPosition();
                MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
                contentHomePager.setAdapter(adapter);
                contentHomePager.setCurrentItem(selectedTabPosition);
                contentHomeTab.setupWithViewPager(contentHomePager);
                contentHomePager.setCurrentItem(1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HomeCycleFragmentSrlSwipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

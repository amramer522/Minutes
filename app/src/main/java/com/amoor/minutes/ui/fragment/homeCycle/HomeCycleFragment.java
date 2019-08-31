package com.amoor.minutes.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.MyPagerAdapter;

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

    public HomeCycleFragment()
    {
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

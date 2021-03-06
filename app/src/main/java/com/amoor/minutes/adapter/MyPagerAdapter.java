package com.amoor.minutes.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.amoor.minutes.ui.fragment.homeCycle.tab.BusesFragment;
import com.amoor.minutes.ui.fragment.homeCycle.tab.HomeFragment;
import com.amoor.minutes.ui.fragment.homeCycle.tab.NavigationFragment;

public class MyPagerAdapter extends FragmentPagerAdapter
{
    Fragment [] frags ={new BusesFragment(),new HomeFragment(),new NavigationFragment()};
    String [] titles = {"Buses","Home","Navigation"};
    public MyPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i)
    {
        Log.i("adapter", "getItem: tab "+i);
        return frags[i];
    }

    @Override
    public int getCount()
    {
        return frags.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }
}

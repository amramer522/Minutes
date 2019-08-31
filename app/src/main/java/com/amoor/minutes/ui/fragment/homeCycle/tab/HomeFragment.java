package com.amoor.minutes.ui.fragment.homeCycle.tab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.HomeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.home_fragment_recycler)
    RecyclerView homeFragmentRecycler;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

//        HomeAdapter adapter = new HomeAdapter(getContext());
//        homeFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        homeFragmentRecycler.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.amoor.minutes.ui.fragment.homeCycle.tab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.BusesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusesFragment extends Fragment {


    @BindView(R.id.buses_fragment_recycler)
    RecyclerView busesFragmentRecycler;
    Unbinder unbinder;

    public BusesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buses, container, false);
        unbinder = ButterKnife.bind(this, root);

        busesFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        BusesAdapter adapter = new BusesAdapter(getContext());
        busesFragmentRecycler.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

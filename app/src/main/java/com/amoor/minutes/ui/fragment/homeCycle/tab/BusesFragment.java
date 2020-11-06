package com.amoor.minutes.ui.fragment.homeCycle.tab;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.BusesAdapter;
import com.amoor.minutes.data.model.drivers.Driverbu;
import com.amoor.minutes.data.rest.ApiServices;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusesFragment extends Fragment {


    @BindView(R.id.buses_fragment_recycler)
    RecyclerView busesFragmentRecycler;
    Unbinder unbinder;
    private ApiServices apiServices;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private BusesAdapter adapter;


    public BusesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buses, container, false);
        unbinder = ButterKnife.bind(this, root);
        apiServices = getClient().create(ApiServices.class);
        preferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getContext());

        busesFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BusesAdapter(getContext());
        busesFragmentRecycler.setAdapter(adapter);

        getBuses();


        return root;
    }

    private void getBuses() {
        progressDialog.setMessage("انتظر قليلا يتم تحميل الباصات ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        String line = preferences.getString("line", "");
        apiServices.getLineBuses(line).enqueue(new Callback<List<Driverbu>>() {
            @Override
            public void onResponse(Call<List<Driverbu>> call, Response<List<Driverbu>> response) {
                if (response.isSuccessful()) {
                    List<Driverbu> buses = response.body();
                    adapter.setData(buses);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(),  response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Driverbu>> call, Throwable t)
            {
//                Toast.makeText(getContext(), "getBuse onFailure :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        progressDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

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

import com.amoor.minutes.R;
import com.amoor.minutes.adapter.HomeAdapter;
import com.amoor.minutes.data.model.tweet.Tweet;
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
public class HomeFragment extends Fragment {


    @BindView(R.id.home_fragment_recycler)
    RecyclerView homeFragmentRecycler;
    Unbinder unbinder;
    private ApiServices apiServices;
    private HomeAdapter adapter;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        apiServices = getClient().create(ApiServices.class);
        progressDialog = new ProgressDialog(getContext());
        preferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String line = preferences.getString("line", "");
        getTweets(line);

        adapter = new HomeAdapter(getContext());
        homeFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        homeFragmentRecycler.setAdapter(adapter);


        return root;
    }

    private void getTweets(String line)
    {
        apiServices.getTweets(line).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    List<Tweet> tweetList = response.body();
                    adapter.setData(tweetList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
//                Toast.makeText(getContext(), "getTweets onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

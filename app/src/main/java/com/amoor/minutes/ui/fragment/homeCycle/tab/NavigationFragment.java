package com.amoor.minutes.ui.fragment.homeCycle.tab;

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
import com.amoor.minutes.adapter.LinksAdapter;
import com.amoor.minutes.data.model.links.Link;
import com.amoor.minutes.data.rest.ApiServices;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;

public class NavigationFragment extends Fragment {


    @BindView(R.id.navigation_fragment_recycler)
    RecyclerView navigationFragmentRecycler;
    Unbinder unbinder;

    private LinksAdapter adapter;
    private ApiServices apiServices;
    private SharedPreferences preferences;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        unbinder = ButterKnife.bind(this, root);
        apiServices = getClient().create(ApiServices.class);
        preferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);


        navigationFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LinksAdapter(getContext());
        navigationFragmentRecycler.setAdapter(adapter);

        getLinks();

        return root;
    }

    private void getLinks() {
        String university = preferences.getString("university", "");
        apiServices.getLinks(university).enqueue(new Callback<List<Link>>() {
            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                if (response.isSuccessful()) {
                    List<Link> links = response.body();
                    adapter.setData(links);
                    adapter.notifyDataSetChanged();
                } else {
//                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t)
            {
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

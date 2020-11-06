package com.amoor.minutes.ui.fragment.homeCycle.nav;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.about.About;
import com.amoor.minutes.data.rest.ApiServices;

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
public class AboutUsFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.About_Us_Tv_content)
    TextView AboutUsTvContent;
    private ApiServices apiServices;
    private SharedPreferences preferences;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        preferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        apiServices = getClient().create(ApiServices.class);

        getAboutDetails();

        return view;
    }

    private void getAboutDetails()
    {
        apiServices.getAboutUsDetails().enqueue(new Callback<About>() {
            @Override
            public void onResponse(Call<About> call, Response<About> response)
            {
                if (response.isSuccessful())
                {
                    About about = response.body();
                    AboutUsTvContent.setText(about.getAboutUs());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("about",about.getAboutUs());
                    editor.apply();

                }
                else
                {
                    String about = preferences.getString("about", "");
                    if (about!=null)
                    {
                        AboutUsTvContent.setText(about);
                    }
                    Toast.makeText(getContext(), "النت ضعيف", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<About> call, Throwable t)
            {

                String about = preferences.getString("about", "");
                if (about!=null)
                {
                    AboutUsTvContent.setText(about);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

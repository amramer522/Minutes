package com.amoor.minutes.ui.fragment.homeCycle.nav;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.profile.Profile;
import com.amoor.minutes.data.rest.ApiServices;
import com.bumptech.glide.Glide;

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
public class ProfileFragment extends Fragment {


    @BindView(R.id.profile_fragment_img)
    ImageView profileFragmentImg;
    @BindView(R.id.profile_fragment_tv_user_name)
    TextView profileFragmentTvUserName;
    @BindView(R.id.profile_fragment_tv_id)
    TextView profileFragmentTvId;
    @BindView(R.id.profile_fragment_tv_email)
    TextView profileFragmentTvEmail;
    @BindView(R.id.profile_fragment_tv_mobile_number)
    TextView profileFragmentTvMobileNumber;
    Unbinder unbinder;
    @BindView(R.id.profile_fragment_tv_bus_line)
    TextView profileFragmentTvBusLine;
    private ApiServices apiServices;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, root);
        preferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String access_token = preferences.getString("access_token", "");
        apiServices = getClient().create(ApiServices.class);
        progressDialog = new ProgressDialog(getContext());

        getProfileData(access_token);
//        getProfileData("d70691f4f6137c1402c2c4839c0680d2");


        return root;
    }

    private void getProfileData(String access_token)
    {
        progressDialog.setMessage("انتظر قليلا يتم تحميل بياناتك ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        apiServices.getProfileData(access_token).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Profile profileData = response.body();
                    Glide.with( getContext() ).load( "http://gtex.ddns.net/busbooking-system/data/include/uploads/"+profileData.getImage() ).into( profileFragmentImg );
                    profileFragmentTvUserName.setText(profileData.getName());
                    profileFragmentTvId.setText(profileData.getSId());
                    profileFragmentTvEmail.setText(profileData.getEmail());
                    profileFragmentTvMobileNumber.setText(profileData.getMob());
                    profileFragmentTvBusLine.setText(profileData.getLine());

                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(getContext(), "onResponse: " + response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "النت ضعيف", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

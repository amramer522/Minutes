package com.amoor.minutes.ui.activity.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.login.Login;
import com.amoor.minutes.data.rest.ApiServices;
import com.amoor.minutes.data.rest.RetrofitClient;
import com.amoor.minutes.helper.HelperMethod;
import com.amoor.minutes.ui.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.helper.HelperMethod.getTextFromTil;
import static com.amoor.minutes.helper.HelperMethod.setTilEmpty;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_activity_til_student_id)
    TextInputLayout loginActivityTilStudentId;
    @BindView(R.id.login_activity_til_password)
    TextInputLayout loginActivityTilPassword;
    private ApiServices apiServices;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        progressDialog = new ProgressDialog(this);


    }

    @OnClick({R.id.login_activity_btn_sign_in, R.id.login_activity_btn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_activity_btn_sign_in:
                login();
                break;
            case R.id.login_activity_btn_sign_up:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                break;
        }
    }


    private void login() {
        String studentId = getTextFromTil(loginActivityTilStudentId);
        String password = getTextFromTil(loginActivityTilPassword);
        if (!TextUtils.isEmpty(studentId) && !TextUtils.isEmpty(password)) {

            loginRequest(studentId, password);

        } else {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginRequest(String studentId, String password) {
        progressDialog.setMessage("انتظر قليلا يتم تسجيل الدخول ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        apiServices.login(studentId, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus())
                    {
                        String accessToken = response.body().getAccessToken();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("status", true);
                        editor.putString("access_token", accessToken);
                        editor.apply();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();

                    } else
                        {
                            progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            builder.setView(R.layout.dialog_wrong_data);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                            dialog.show();

                            Button tryAgain = dialog.findViewById(R.id.dialog_try_again);
                            tryAgain.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "wrong data " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        setTilEmpty(loginActivityTilPassword);
                        setTilEmpty(loginActivityTilStudentId);

                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "النت ضعيف", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(LoginActivity.this, "onResponse: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
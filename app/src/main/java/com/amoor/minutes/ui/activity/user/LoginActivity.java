package com.amoor.minutes.ui.activity.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.amoor.minutes.R;
import com.amoor.minutes.data.rest.ApiServices;
import com.amoor.minutes.data.rest.RetrofitClient;
import com.amoor.minutes.helper.HelperMethod;
import com.amoor.minutes.ui.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amoor.minutes.helper.HelperMethod.getTextFromTil;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_activity_til_student_id)
    TextInputLayout loginActivityTilStudentId;
    @BindView(R.id.login_activity_til_password)
    TextInputLayout loginActivityTilPassword;
    private ApiServices apiServices;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private Dialog dialog;

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
        if (!TextUtils.isEmpty(studentId)&&!TextUtils.isEmpty(password))
        {

//            // when success
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("status", true);
////            editor.putString("user_ID", user_id);
//            editor.apply();
//
//            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
//            finish();

        }
        else
        {
            // show dialog  to confirm
            dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.dialog_wrong_data);
            dialog.setCancelable(true);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            Button tryAgain = dialog.findViewById(R.id.dialog_try_again);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            });

//            HelperMethod.showToastMsg(getApplicationContext(),getString(R.string.fill_all_fields));
        }

//        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//        finish();
    }
}

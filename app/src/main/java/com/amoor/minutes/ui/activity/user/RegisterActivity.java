package com.amoor.minutes.ui.activity.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.busLine.BusLine;
import com.amoor.minutes.data.model.login.Login;
import com.amoor.minutes.data.model.register.User;
import com.amoor.minutes.data.model.university.University;
import com.amoor.minutes.data.rest.ApiServices;
import com.amoor.minutes.helper.HelperMethod;
import com.amoor.minutes.ui.activity.home.HomeActivity;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;
import static com.amoor.minutes.helper.HelperMethod.getTextFromEt;
import static com.amoor.minutes.helper.HelperMethod.getTextFromSpinner;
import static com.amoor.minutes.helper.HelperMethod.isValidate;
import static com.amoor.minutes.helper.HelperMethod.setSpinnerAdapter;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.register_username)
    EditText registerUsername;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_mobile_number)
    EditText registerMobileNumber;
    @BindView(R.id.register_email)
    EditText registerEmail;
    @BindView(R.id.register_home_address)
    EditText registerHomeAddress;
    //    @BindView(R.id.register_bus_line)
//    EditText registerBusLine;
//    @BindView(R.id.register_university)
//    EditText registerUniversity;
    @BindView(R.id.register_faculty)
    EditText registerFaculty;
    @BindView(R.id.register_level)
    EditText registerLevel;
    //    @BindView(R.id.register_user_profile)
//    ImageView registerUserProfile;
//    @BindView(R.id.register_user_table)
//    ImageView registerUserTable;
    @BindView(R.id.register_confirm_password)
    EditText registerConfirmPassword;
    @BindView(R.id.register_submit)
    Button registerSubmit;
    @BindView(R.id.register_student_id)
    EditText registerStudentId;
    @BindView(R.id.register_activity_Sp_bus_line)
    Spinner registerActivitySpBusLine;
    @BindView(R.id.register_activity_Sp_university)
    Spinner registerActivitySpUniversity;
    private Intent intent;
    private Uri uri;
    private String student_id, userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level;
    private Bitmap profileImage = null;
    private Bitmap tableImage = null;
    private ApiServices apiServices;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private ArrayList<String> arrayLinesNamesList;
    private ArrayList<String> arrayUniversityesNamesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        apiServices = getClient().create(ApiServices.class);
        setBusLineSpinnerValues();
        setUniversitySpinnerValues();
        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    //    @OnClick({R.id.register_user_profile, R.id.register_user_table, R.id.register_submit,R.id.register_activity_Iv_back})
    @OnClick({R.id.register_submit, R.id.register_activity_Iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_activity_Iv_back:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
//            case R.id.register_user_profile:
////                selectImg(1);
//                break;
//            case R.id.register_user_table:
////                selectImg(2);
//                break;
            case R.id.register_submit:
                userName = getTextFromEt(registerUsername);
                password = getTextFromEt(registerPassword);
                confirm_password = getTextFromEt(registerConfirmPassword);
                mobile_number = getTextFromEt(registerMobileNumber);
                email = getTextFromEt(registerEmail);
                home_address = getTextFromEt(registerHomeAddress);
                bus_line = getTextFromSpinner(registerActivitySpBusLine);
                university = getTextFromSpinner(registerActivitySpUniversity);
                faculty = getTextFromEt(registerFaculty);
                level = getTextFromEt(registerLevel);
                student_id = getTextFromEt(registerStudentId);


                boolean validate = isValidate(this, student_id, userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level);
                if (validate)
                {
//                    if (profileImage != null && tableImage != null) {
                    register(student_id, userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level, profileImage, tableImage);
//                    } else {
//                        showToastMsg(this, this.getString(R.string.tost_profile_table_images));
//                    }

                }
                break;
        }
    }

    private void register(String student_id, String userName, String password, String confirm_password, String mobile_number, String email, String home_address, String bus_line, String university, final String faculty, String level, Bitmap profileImage, Bitmap tableImage) {

        progressDialog.setMessage("انتظر قليلا يتم تسجيل حسابك ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        apiServices.register(student_id, userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        progressDialog.dismiss();
                        String accessToken = response.body().getAccessToken();
//                        Toast.makeText(RegisterActivity.this, "register done wit access token \n " + accessToken, Toast.LENGTH_SHORT).show();
                        //            // when success
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putBoolean("status", true);
//                        editor.putString("access_token", accessToken);
//                        editor.apply();
                        Toast.makeText(RegisterActivity.this, "Registration Done wait for admin confirmation", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "النت ضعيف", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


//    private void selectImg(int PICK_IMAGE_REQUEST) {
//        intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super method removed
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                uri = data.getData();
//                try {
//                    profileImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                registerUserProfile.setImageBitmap(profileImage);
//
//            } else if (requestCode == 2) {
//                uri = data.getData();
//                tableImage = null;
//                try {
//                    tableImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                registerUserTable.setImageBitmap(tableImage);
//            }
//        }
//
//    }

    private void setBusLineSpinnerValues()
    {
        progressDialog.setMessage("انتظر قليلا يتم تحميل خطوط السير والجامعات ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        apiServices.getBusLines().enqueue(new Callback<List<BusLine>>() {
            @Override
            public void onResponse(Call<List<BusLine>> call, Response<List<BusLine>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    List<BusLine> busLineList = response.body();
                    String[] lines = new String[busLineList.size()];

                    for (int i = 0; i < busLineList.size(); i++) {
                        lines[i] = busLineList.get(i).getRoute();
                    }

                    setSpinnerAdapter(RegisterActivity.this,lines,registerActivitySpBusLine);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "النت ضعيف", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegisterActivity.this, "النت ضعيف" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BusLine>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUniversitySpinnerValues() {
        apiServices.getUniversites().enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                if (response.isSuccessful()) {
                    List<University> universities = response.body();
                    String[] uni = new String[universities.size()];

                    for (int i = 0; i < universities.size(); i++) {
                        uni[i] = universities.get(i).getUniversity();
                    }

                    setSpinnerAdapter(RegisterActivity.this, uni, registerActivitySpUniversity);

                } else {
//                    Toast.makeText(RegisterActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<University>> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}

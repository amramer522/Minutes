package com.amoor.minutes.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amoor.minutes.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amoor.minutes.helper.HelperMethod.getTextFromEt;
import static com.amoor.minutes.helper.HelperMethod.isValidate;
import static com.amoor.minutes.helper.HelperMethod.showToastMsg;

public class RegisterActivity extends AppCompatActivity {

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
    @BindView(R.id.register_bus_line)
    EditText registerBusLine;
    @BindView(R.id.register_university)
    EditText registerUniversity;
    @BindView(R.id.register_faculty)
    EditText registerFaculty;
    @BindView(R.id.register_level)
    EditText registerLevel;
    @BindView(R.id.register_user_profile)
    ImageView registerUserProfile;
    @BindView(R.id.register_user_table)
    ImageView registerUserTable;
    @BindView(R.id.register_confirm_password)
    EditText registerConfirmPassword;
    @BindView(R.id.register_submit)
    Button registerSubmit;
    private Intent intent;
    private Uri uri;
    private String userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level;
    private Bitmap profileImage = null;
    private Bitmap tableImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @OnClick({R.id.register_user_profile, R.id.register_user_table, R.id.register_submit,R.id.register_activity_Iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_activity_Iv_back:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.register_user_profile:
                selectImg(1);
                break;
            case R.id.register_user_table:
                selectImg(2);
                break;
            case R.id.register_submit:
                userName = getTextFromEt(registerUsername);
                password = getTextFromEt(registerPassword);
                confirm_password = getTextFromEt(registerConfirmPassword);
                mobile_number = getTextFromEt(registerMobileNumber);
                email = getTextFromEt(registerEmail);
                home_address = getTextFromEt(registerHomeAddress);
                bus_line = getTextFromEt(registerBusLine);
                university = getTextFromEt(registerUniversity);
                faculty = getTextFromEt(registerFaculty);
                level = getTextFromEt(registerLevel);


                boolean validate = isValidate(this, userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level);
                if (validate) {
                    if (profileImage != null && tableImage != null) {
                        register(userName, password, confirm_password, mobile_number, email, home_address, bus_line, university, faculty, level, profileImage, tableImage);
                        showToastMsg(this, "good boy");
                    } else {
                        showToastMsg(this, this.getString(R.string.tost_profile_table_images));
                    }

                }
                break;
        }
    }

    private void register(String userName, String password, String confirm_password, String mobile_number, String email, String home_address, String bus_line, String university, String faculty, String level, Bitmap profileImage, Bitmap tableImage) {
        // here register request
    }


    private void selectImg(int PICK_IMAGE_REQUEST) {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                uri = data.getData();
                try {
                    profileImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                registerUserProfile.setImageBitmap(profileImage);

            } else if (requestCode == 2) {
                uri = data.getData();
                tableImage = null;
                try {
                    tableImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                registerUserTable.setImageBitmap(tableImage);
            }
        }

    }

}

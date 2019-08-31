package com.amoor.minutes.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethod
{
    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    public static String getTextFromTil(TextInputLayout textInputLayout)
    {
        String text = textInputLayout.getEditText().getText().toString().trim();
        return text;
    }
    public static String getTextFromEt(EditText editText)
    {
        String text = editText.getText().toString().trim();
        return text;
    }

    public static void setTextToTil(TextInputLayout textInputLayout,String text) {
        textInputLayout.getEditText().setText(text);
    }

    public static String getTextFromSpinner(Spinner spinner) {
        String text = spinner.getSelectedItem().toString().trim();
        return text;
    }

    public static void setAppLanguage(Context context, String localeCode)
    {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(localeCode.toLowerCase()));
        res.updateConfiguration(conf,dm);

    }
    public static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void setTilEmpty(TextInputLayout registerFragmentTilEmailAddress)
    {
        registerFragmentTilEmailAddress.getEditText().setText("");
    }


    public static void showToastMsg(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean tilsVaild(String name, String email, String phone, String gender,String password, String confirm_password)
    {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password))
        {

            return true;
        }
        else
        {
            return false;
        }

    }



    public static boolean isValidate(Context context,String...values)
    {
        String [] msgs ={"User name","password","Confirm password","Mobile phone","Email","Home address","Bus Line","University","Faculty","Level"};
        for (int i = 0; i <values.length ; i++)
        {
            boolean empty = TextUtils.isEmpty(values[i]);
            if (empty)
            {
                showToastMsg(context,msgs[i]+" is empty");
                return false;
            }
        }

        if (TextUtils.equals(values[1],values[2]))
        {
            if (values[1].length()>7)
            {
                return true;
            }
            else
            {
                showToastMsg(context,"password must be at least 7 chars");
                return false;
            }
        }
        else
        {
            showToastMsg(context,"passwords not matched");
            return false;
        }
    }


}

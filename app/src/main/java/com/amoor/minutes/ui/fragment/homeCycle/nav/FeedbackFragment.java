package com.amoor.minutes.ui.fragment.homeCycle.nav;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.feedback.Feedback;
import com.amoor.minutes.data.rest.ApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;
import static com.amoor.minutes.helper.HelperMethod.getTextFromEt;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {


    @BindView(R.id.feedback_fragment_Et_student_name)
    EditText feedbackFragmentEtStudentName;
    @BindView(R.id.feedback_fragment_Et_student_id)
    EditText feedbackFragmentEtStudentId;
    @BindView(R.id.feedback_fragment_Et_message)
    EditText feedbackFragmentEtMessage;
    Unbinder unbinder;
    private ApiServices apiServices;
    private ProgressDialog progressDialog;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClient().create(ApiServices.class);
        progressDialog = new ProgressDialog(getContext());



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.register_submit)
    public void onViewClicked()
    {
        String student_name = getTextFromEt(feedbackFragmentEtStudentName);
        String student_id = getTextFromEt(feedbackFragmentEtStudentId);
        String student_message = getTextFromEt(feedbackFragmentEtMessage);
        if (!TextUtils.isEmpty(student_name)&&!TextUtils.isEmpty(student_id)&&!TextUtils.isEmpty(student_message))
        {
            sendFeedback(student_name,student_id,student_message);
        }
        else
        {
            Toast.makeText(getContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendFeedback(String student_name, String student_id, String student_message)
    {
        progressDialog.setMessage("انتظر قليلا يتم ارسال رأيك ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        apiServices.sendFeedback(student_name,student_id,student_message).enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body().getStatus())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        feedbackFragmentEtStudentName.setText("");
                        feedbackFragmentEtStudentId.setText("");
                        feedbackFragmentEtMessage.setText("");

                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

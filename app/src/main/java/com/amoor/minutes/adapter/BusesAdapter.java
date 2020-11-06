package com.amoor.minutes.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.drivers.Driverbu;
import com.amoor.minutes.data.model.studentOnBus.StudentOnBus;
import com.amoor.minutes.data.rest.ApiServices;
import com.amoor.minutes.ui.activity.home.MapActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;
import static com.amoor.minutes.helper.HelperMethod.getTextFromEt;

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.Hold> {

    private Context context;
    private Dialog dialog;
    private List<Driverbu> buses = new ArrayList<>();
    private SharedPreferences preferences;
    private ApiServices apiServices;
    private int numOfStudentsOnBus = 0;
    private EditText note;
//    private TextView noBuses;


    public BusesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        apiServices = getClient().create(ApiServices.class);
        preferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);

//        noBuses = parent.findViewById(R.id.Buses_Fragment_Tv_No_Buses);
        return new Hold(LayoutInflater.from(context).inflate(R.layout.item_driver, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final Hold hold, int i) {
        final int position = i;

        hold.busTime.setText(hold.busTime.getText() + "  " + buses.get(i).getTime());
        hold.driverName.setText(hold.driverName.getText() + "  " + buses.get(i).getDriver());
        hold.studentOnBus.setText(hold.studentOnBus.getText()+" 0 Student");
        hold.availableSeats.setText(hold.availableSeats.getText()+" 0 Seat");
        getNumOfStudentsOnBus(hold, position, buses.get(i).getDriverId());
        Glide.with(context).load("http://gtex.ddns.net/busbooking-system/data/include/uploads/" + buses.get(i).getDriverPhoto()).into(hold.driverPhoto);
        setAction(hold, position);

    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public void setData(List<Driverbu> buses) {
        this.buses = buses;
    }

    private void getNumOfStudentsOnBus(final Hold hold2, final int i, String driver_id) {
        apiServices.getNumOfStudentsOnBus(driver_id).enqueue(new Callback<StudentOnBus>() {
            @Override
            public void onResponse(Call<StudentOnBus> call, Response<StudentOnBus> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStudentAboard().isEmpty()) {
                    } else {
                        numOfStudentsOnBus = Integer.parseInt(response.body().getStudentAboard());
                        hold2.studentOnBus.setText("Students on bus : "+ numOfStudentsOnBus + "  Student");
                    }

                    hold2.availableSeats.setText("Available seats : " + (Integer.parseInt(buses.get(i).getBusCapacity()) - numOfStudentsOnBus) + " Seat");
                }
            }

            @Override
            public void onFailure(Call<StudentOnBus> call, Throwable t) {
            }
        });

    }

    class Hold extends RecyclerView.ViewHolder {
        TextView busTime, driverName, studentOnBus, availableSeats;
        ImageView callDriver;
        CircleImageView driverPhoto;
        Button busRequest;

        public Hold(@NonNull View itemView) {
            super(itemView);
            callDriver = itemView.findViewById(R.id.item_driver_call);
            busRequest = itemView.findViewById(R.id.item_driver_bus_request);
            busTime = itemView.findViewById(R.id.item_driver_time);
            driverName = itemView.findViewById(R.id.item_driver_name);
            driverPhoto = itemView.findViewById(R.id.item_driver_img);
            studentOnBus = itemView.findViewById(R.id.item_driver_num_of_student);
            availableSeats = itemView.findViewById(R.id.item_driver_num_of_available_seats);


        }
    }


    public void setAction(Hold hold, final int position) {
        hold.callDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:"+"01027545022"));
                intent.setData(Uri.parse("tel:" + buses.get(position).getDriverNumber()));
                context.startActivity(intent);
            }
        });

        hold.busRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_driver_type_note);
                dialog.setTitle("Mark your location");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                note = dialog.findViewById(R.id.editText_note);

                Button markStudentLocation = dialog.findViewById(R.id.dialog_driver_note_Btn_mark_student_location);
                markStudentLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String noteTxt = getTextFromEt(note);
                        dialog.dismiss();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("driver_id", buses.get(position).getDriverId());
                        editor.putString("driver_time", buses.get(position).getTime());
                        editor.putString("note", noteTxt);
                        editor.apply();

                        context.startActivity(new Intent(context, MapActivity.class));
                        ((Activity) context).finish();
                    }
                });

            }
        });
    }
}

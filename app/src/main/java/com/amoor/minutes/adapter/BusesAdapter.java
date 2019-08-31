package com.amoor.minutes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amoor.minutes.R;

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.Hold> {
    private Context context;
    int[] imgs = {R.drawable.img_bus_request, R.drawable.img_bus_line, R.drawable.img_bus_routes};
    String[] titles = {"Bus Request", "Bus Lines", "Bus Routes"};

    public BusesAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new Hold(LayoutInflater.from(context).inflate(R.layout.item_buses, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Hold hold, int i) {
        hold.textView.setText(titles[i]);
        hold.imageView.setImageResource(imgs[i]);
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    class Hold extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Hold(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_buses_bg);
            textView = itemView.findViewById(R.id.item_buses_title);
        }
    }
}

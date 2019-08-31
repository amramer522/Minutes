package com.amoor.minutes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amoor.minutes.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.hold>
{
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public hold onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new hold(inflate);    }

    @Override
    public void onBindViewHolder(@NonNull hold hold, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class hold extends RecyclerView.ViewHolder
    {
        public hold(@NonNull View itemView) {
            super(itemView);
        }
    }
}

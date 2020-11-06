package com.amoor.minutes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.links.Link;
import com.amoor.minutes.ui.activity.home.FollowBusActivity;

import java.util.ArrayList;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.Hold> {
    private Context context;
    private List<Link> links =  new ArrayList<>();

    public LinksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new Hold(LayoutInflater.from(context).inflate(R.layout.item_link, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Hold hold, int i)
    {
        hold.button.setText(links.get(i).getPlateNum());
        setAction(hold,i);
    }

    private void setAction(Hold hold, final int i)
    {
        hold.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, FollowBusActivity.class);
                intent.putExtra("link",links.get(i).getLink());
                context.startActivity(intent);
                ((Activity)context).finish();
                Toast.makeText(context, ""+links.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public void setData(List<Link> links)
    {
        this.links= links;
    }

    class Hold extends RecyclerView.ViewHolder
    {
        Button button ;
        Hold(@NonNull View itemView)
        {
            super(itemView);
            button= itemView.findViewById(R.id.item_link_btn);

        }
    }
}

package com.careclues.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.careclues.app.R;
import com.careclues.app.activity.ChatActivity;
import com.careclues.app.model.DataModel;
import com.careclues.app.model.DoctorModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {
    private Context context;
    private List<DataModel> values;

    public DoctorListAdapter(Context ctx, List<DataModel> values) {
        this.context = ctx;
        this.values = values;
    }

    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.doctorlist_adapter_layout, viewGroup, false);
        return new DoctorListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorListAdapter.ViewHolder holder, int position) {
        final DataModel dObj = values.get(position);
        holder.doctornameTxt.setText("Dr. " + dObj.getFirst_name() + " " + dObj.getLast_name());
        holder.doctorRatingBar.setRating(Float.valueOf(dObj.getRating()));
        holder.doctorQualificationTxt.setText("N/A");
        dObj.getLinkList().forEach(links -> {
            if (links.getRel().equals("profile_photo")) {
                Picasso.get().load(links.getHref()).into(holder.doctorImage);
            }
        });


        holder.containerLayout.setOnClickListener(onClick -> {
            Intent intent = new Intent(context, ChatActivity.class);
            context.startActivity(intent);
        });


        //if(dObj.getLinkList().get())
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctornameTxt, doctorQualificationTxt;
        CircleImageView doctorImage;
        RatingBar doctorRatingBar;
        LinearLayout containerLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            doctornameTxt = itemView.findViewById(R.id.docNameTxt);
            doctorQualificationTxt = itemView.findViewById(R.id.docQualificationTxt);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            doctorRatingBar = itemView.findViewById(R.id.docRateBar);
            containerLayout = itemView.findViewById(R.id.containerLayout);

        }
    }


}


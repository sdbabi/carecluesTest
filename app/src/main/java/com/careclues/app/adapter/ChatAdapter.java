package com.careclues.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.careclues.app.R;

import com.careclues.app.model.MessageModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private List<MessageModel> values;
    boolean isOutbound = false;

    public ChatAdapter(Context ctx, List<MessageModel> values, boolean isOutbound) {
        this.context = ctx;
        this.values = values;
        this.isOutbound = isOutbound;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.chat_adapter, viewGroup, false);
        return new ChatAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        final MessageModel mObj = values.get(position);
        if (isOutbound) {

            holder.incomingLayout.setVisibility(View.GONE);
            holder.outgoingLayout.setVisibility(View.VISIBLE);
            holder.outgoingTxt.setTextSize(15.0f);
            holder.outgoingTxt.setText(mObj.getMessage());
            holder.outgoingTxt.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.incomingLayout.setVisibility(View.VISIBLE);
            holder.outgoingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView incommingTxt,outgoingTxt;
        LinearLayout incomingLayout,outgoingLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            incommingTxt = itemView.findViewById(R.id.incomingTxt);
            outgoingTxt= itemView.findViewById(R.id.outgoingTxt);
            incomingLayout=itemView.findViewById(R.id.incomingLayout);
            outgoingLayout = itemView.findViewById(R.id.outgoingLayout);

        }
    }
}

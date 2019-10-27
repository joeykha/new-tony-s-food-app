package com.example.myheroapp.ui.UserMainActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class UserScheduleViewHolder extends RecyclerView.ViewHolder {

    public TextView tvClient;
    public TextView tvLocation;
    public TextView tvDate;

    public UserScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        tvClient = itemView.findViewById(R.id.tvTitle);
        tvLocation = itemView.findViewById(R.id.tvLocation);
        tvDate = itemView.findViewById(R.id.tvDate);
    }
}

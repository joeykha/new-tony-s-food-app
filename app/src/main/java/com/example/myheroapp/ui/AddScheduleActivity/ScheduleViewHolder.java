package com.example.myheroapp.ui.AddScheduleActivity;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    public TextView tvUsername;
    public TextView tvLocation;
    public TextView tvDate;
    public ImageView ivDelete;

    public ScheduleViewHolder(@NonNull LayoutInflater inflater, @LayoutRes int resource, ViewGroup parent) {
        super(inflater.inflate(resource, parent, false));
        tvUsername = itemView.findViewById(R.id.tvClientName);
        tvLocation = itemView.findViewById(R.id.tvLocation);
        tvDate = itemView.findViewById(R.id.tvDate);
        ivDelete = itemView.findViewById(R.id.ivDelete);
    }
}

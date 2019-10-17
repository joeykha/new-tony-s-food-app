package com.example.myheroapp.ui.AdminMainActivity.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class TitleViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvClientName);
    }
}

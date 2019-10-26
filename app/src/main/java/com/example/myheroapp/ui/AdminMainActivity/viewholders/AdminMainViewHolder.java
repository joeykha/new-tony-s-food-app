package com.example.myheroapp.ui.AdminMainActivity.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class AdminMainViewHolder extends RecyclerView.ViewHolder {

    public TextView tvClientName;
    public Button btnCheckStock;


    public AdminMainViewHolder(@NonNull View itemView) {
        super(itemView);
        tvClientName = itemView.findViewById(R.id.tvClientName);
        btnCheckStock = itemView.findViewById(R.id.btnCheckStock);
    }
}

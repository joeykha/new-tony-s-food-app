package com.example.myheroapp.ui.AddClientActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class ClientViewHolder extends RecyclerView.ViewHolder {

    TextView tvClientName, tvLocation;
    ImageView ivDelete;

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);
        tvClientName = itemView.findViewById(R.id.tvClientName);
        tvLocation = itemView.findViewById(R.id.tvLocation);
        ivDelete = itemView.findViewById(R.id.ivDelete);
    }
}

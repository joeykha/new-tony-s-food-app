package com.example.myheroapp.ui.AddUserActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView tvUsername;
    ImageView ivUpdate;
    ImageView ivDelete;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        ivUpdate = itemView.findViewById(R.id.ivUpdate);
        ivDelete = itemView.findViewById(R.id.ivDelete);
    }
}

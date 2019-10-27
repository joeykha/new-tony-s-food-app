package com.example.myheroapp.ui.ProductDetailsActivity.viewholders;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class ButtonViewHolder extends RecyclerView.ViewHolder {

    public Button btn;

    public ButtonViewHolder(@NonNull View itemView) {
        super(itemView);
        btn = itemView.findViewById(R.id.btn);
    }
}

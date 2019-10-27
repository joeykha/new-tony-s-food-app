package com.example.myheroapp.ui.ProductDetailsActivity.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class TextViewHolder extends RecyclerView.ViewHolder {

    public TextView tvLabel;
    public TextView tvText;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        tvLabel = itemView.findViewById(R.id.tvLabel);
        tvText = itemView.findViewById(R.id.tvText);
    }
}

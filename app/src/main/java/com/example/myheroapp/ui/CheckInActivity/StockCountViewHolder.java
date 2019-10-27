package com.example.myheroapp.ui.CheckInActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class StockCountViewHolder extends RecyclerView.ViewHolder {

    public TextView tvProduct, tvQuantity;
    public ImageView ivRemove, ivAdd;

    public StockCountViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProduct = itemView.findViewById(R.id.tvProductName);
        tvQuantity = itemView.findViewById(R.id.tvQuantity);
        ivRemove = itemView.findViewById(R.id.ivRemove);
        ivAdd = itemView.findViewById(R.id.ivAdd);
    }
}

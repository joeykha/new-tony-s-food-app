package com.example.myheroapp.ui.ProductDetailsActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;

public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {

    public TextView tvProductName, tvQuantity;

    public ProductDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tvProductName);
        tvQuantity = itemView.findViewById(R.id.tvQuantity);

    }
}

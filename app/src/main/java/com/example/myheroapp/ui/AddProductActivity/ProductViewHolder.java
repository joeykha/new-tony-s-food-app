package com.example.myheroapp.ui.AddProductActivity;

import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.myheroapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    TextView tvProductName;
    ImageView ivDelete;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tvUsername);
        ivDelete = itemView.findViewById(R.id.ivDelete);
    }
}

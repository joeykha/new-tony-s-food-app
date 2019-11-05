package com.example.myheroapp.ui.CheckInActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class StockCountAdapter extends RecyclerView.Adapter<StockCountViewHolder> {

    public interface StockCountInterface{
        void OnAddQuantityClicked(int productId);
        void OnRemoveQuantityClicked(int productId);
    }

    private Context mContext;
    private StockCountInterface mStockCountInterface;

    private List<Product> mItems;


    public StockCountAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public StockCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_stock_count,parent,false);
        return new StockCountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockCountViewHolder holder, int position) {
        final Product product = mItems.get(position);
        holder.tvProduct.setText(product.getName());
        holder.tvQuantity.setText(String.valueOf(product.getTmpQuantity()));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getTmpQuantity() > 0){
                    product.addQuantity(-1);
                    mStockCountInterface.OnRemoveQuantityClicked(product.getId());
                    holder.tvQuantity.setText(String.valueOf(product.getTmpQuantity()));
                }
            }
        });

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getTmpQuantity() >= 0){
                    product.addQuantity(1);
                    mStockCountInterface.OnAddQuantityClicked(product.getId());
                    holder.tvQuantity.setText(String.valueOf(product.getTmpQuantity()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int retInt = 0;
        if(mItems != null){
            retInt = mItems.size();
        }
        return retInt;
    }

    public void setItems(List<Product> products){
        if(mItems == null){
            mItems = new ArrayList<>();
        }else{
            mItems.clear();
        }
        mItems.addAll(products);
        notifyDataSetChanged();
    }

    public void setStockCountInterface(StockCountInterface stockCountInterface) {
        mStockCountInterface = stockCountInterface;
    }
}

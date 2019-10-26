package com.example.myheroapp.ui.ProductDetailsActivity;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.models.ClientProduct;
import com.example.myheroapp.models.viewholder_models.ButtonObject;
import com.example.myheroapp.models.viewholder_models.ProductQuantity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

//    public interface ProductDetailsInterface {
//        void OnCheckStockClicked(int clientId);
//    }

    private List<Object> mItems;
    private Context mContext;
  //  private ProductDetailsInterference mProductDetailsInterface;

    public ProductDetailsAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        Object item = mItems.get(position);
        if (item instanceof ProductQuantity) {
            viewType = 1;
        } else if (item instanceof String) {
            viewType = 2;
        }else if(item instanceof Pair){
            viewType = 3;
         }else if(item instanceof ButtonObject){
            viewType = 4;
        }
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_product_details, parent, false);
                return new ProductDetailsViewHolder(view);
//            case 2:
//                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_title, parent, false);
//                return new TitleViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_product_details, parent, false);
                return new ProductDetailsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == 1) {
            final ClientProduct clientProduct = (ClientProduct) mItems.get(position);
            ((ProductDetailsViewHolder) holder).tvProductName.setText(String.format("%s\n%s",clientProduct.getId_Product(), clientProduct.getQuantity()));
           // ((ProductDetailsViewHolder) holder).btnCheckStock.setOnClickListener(new View.OnClickListener() {
             //   @Override
            //    public void onClick(View view) {
                  // mProductDetailsInterface.OnCheckStockClicked(client.getId());
               // }
           // });
        }// else if (viewType == 2) {
           // ((TitleViewHolder) holder).tvTitle.setText((String) mItems.get(position));
        //}
    }

    @Override
    public int getItemCount() {
        int retInt = 0;
        if (mItems != null) {
            retInt = mItems.size();
        }
        return retInt;
    }

    public void setItems(List<Object> items) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        } else {
            mItems.clear();
        }
        mItems.addAll(items);
    }

   // public void setProductDetailsInterface(ProductDetailsInterface ProductMainInterface) {
     //   this.mProductDetailsInterface = ProductMainInterface;
   // }

}


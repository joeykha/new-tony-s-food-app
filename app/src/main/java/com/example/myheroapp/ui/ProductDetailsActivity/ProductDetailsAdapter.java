package com.example.myheroapp.ui.ProductDetailsActivity;

import android.app.UiAutomation;
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
import com.example.myheroapp.ui.AdminMainActivity.viewholders.TitleViewHolder;
import com.example.myheroapp.ui.ProductDetailsActivity.viewholders.ButtonViewHolder;
import com.example.myheroapp.ui.ProductDetailsActivity.viewholders.ProductDetailsViewHolder;
import com.example.myheroapp.ui.ProductDetailsActivity.viewholders.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static String NO_PRODUCTS_FOUND = "No Products Found!";

    private List<Object> mItems;
    private Context mContext;

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
        }
//        } else if (item instanceof Pair) {
//            viewType = 3;
//        } else if (item instanceof ButtonObject) {
//            viewType = 4;
//        }
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
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_title, parent, false);
                return new TitleViewHolder(view);
//            case 3:
//                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_text, parent, false);
//                return new TextViewHolder(view);
//            case 4:
//                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_button, parent, false);
//                return new ButtonViewHolder(view);
            default:
//                view = LayoutInflater.from(mContext).inflate(R.layout.view_holder_text, parent, false);
                return null;//new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == 1) {
            final ProductQuantity productQuantity = (ProductQuantity) mItems.get(position);
            ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder) holder;
            productDetailsViewHolder.tvProductName.setText(productQuantity.getProductName());
            productDetailsViewHolder.tvQuantity.setText(String.valueOf(productQuantity.getQuantity()));
        } else if (viewType == 2) {
            TitleViewHolder textViewHolder = (TitleViewHolder) holder;
            if(mItems.get(position).equals(NO_PRODUCTS_FOUND)){
                textViewHolder.tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textViewHolder.tvTitle.setText((String) mItems.get(position));
                textViewHolder.divider.setVisibility(View.GONE);
            }else{
                textViewHolder.tvTitle.setText((String) mItems.get(position));
            }
        }
//        }else if (viewType == 3) {
//            Pair<String, String> pair = (Pair) mItems.get(position);
//            ((TextViewHolder) holder).tvLabel.setText(pair.first);
//            ((TextViewHolder) holder).tvText.setText(pair.second);
//        }else if (viewType == 4) {
//            final ButtonObject btnObject = (ButtonObject) mItems.get(position);
//            ((ButtonViewHolder) holder).btn.setText(btnObject.getBtnLabel());
//            ((ButtonViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mProductDetailsInterface.OnCheckStockClicked(btnObject.getClientId());
//                }
//            });
//        }
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



}


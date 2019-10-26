package com.example.myheroapp.models.viewholder_models;

public class ProductQuantity {


    private String mProductName;
    private int mQuantity;

    public ProductQuantity(String productName, int quantity) {
        mProductName = productName;
        mQuantity = quantity;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void seuantity(int quantity) {
        mQuantity = quantity;
    }
}

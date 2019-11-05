package com.example.myheroapp.models.viewholder_models;

public class ProductQuantity {


    private int mProductId;
    private String mProductName;
    private int mQuantity;

    public ProductQuantity(String productName, int quantity) {
        mProductName = productName;
        mQuantity = quantity;
    }

    public ProductQuantity(int productId, int quantity) {
        mProductId = productId;
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

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void addQuant(int quantity) {
        mQuantity += quantity;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }
}

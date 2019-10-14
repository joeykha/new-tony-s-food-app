package com.example.myheroapp.resources;

public class ProductApi {

    private static final String ROOT_URL = String.format("%sTonysFood/v1/ProductApi.php?apicall=", ClientApi.IP_ADDRESS);

    public static final String URL_CREATE_PRODUCT = ROOT_URL + "createProduct";
    public static final String URL_READ_PRODUCTS = ROOT_URL + "getProducts";
    public static final String URL_UPDATE_PRODUCT = ROOT_URL + "updateProduct";
    public static final String URL_DELETE_PRODUCT = ROOT_URL + "deleteProduct&id=";
}



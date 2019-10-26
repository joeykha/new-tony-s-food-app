package com.example.myheroapp.resources;

public class ClientProductApi {

    private static final String ROOT_URL = String.format("%sTonysFood/v1/client_productApi.php?apicall=", ClientApi.IP_ADDRESS);

    public static final String URL_CREATE_CLIENT_PRODUCT = ROOT_URL + "createClient_products";
    public static final String URL_READ_CLIENT_PRODUCT = ROOT_URL + "getClient_products";
    public static final String URL_UPDATE_CLIENT_PRODUCT = ROOT_URL + "updateClient_product";
    public static final String URL_DELETE_CLIENT_PRODUCT = ROOT_URL + "deleteClient_product&id=";
}

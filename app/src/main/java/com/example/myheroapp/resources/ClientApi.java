package com.example.myheroapp.resources;

public class ClientApi {

    public static final String IP_ADDRESS = "http://10.0.134.148/";

    private static final String ROOT_URL = String.format("%sTonysFood/v1/clientApi.php?apicall=", IP_ADDRESS);

    public static final String URL_CREATE_CLIENT = ROOT_URL + "createClient";
    public static final String URL_READ_CLIENTS = ROOT_URL + "getClients";
    public static final String URL_UPDATE_CLIENT = ROOT_URL + "updateClient";
    public static final String URL_DELETE_CLIENT = ROOT_URL + "deleteClient&id=";

}

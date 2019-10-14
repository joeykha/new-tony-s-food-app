package com.example.myheroapp.resources;

public class LoginApi {

    private static final String ROOT_URL = String.format("%sTonysFood/v1/loginApi.php?apicall=", ClientApi.IP_ADDRESS);
    public static final String URL_LOGIN= ROOT_URL + "login";

}

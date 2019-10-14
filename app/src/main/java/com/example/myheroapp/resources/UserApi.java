package com.example.myheroapp.resources;


public class UserApi {

    private static final String ROOT_URL = String.format("%sTonysFood/v1/userApi.php?apicall=", ClientApi.IP_ADDRESS);

    public static final String URL_CREATE_USER = ROOT_URL + "createUser";
    public static final String URL_READ_USERS = ROOT_URL + "getUsers";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser";
    public static final String URL_DELETE_USER = ROOT_URL + "deleteUser&id=";
}

package com.example.myheroapp.models;

public class ClientProduct {
    private int id;
    private String quantity, cp_Date, id_user, id_Client, id_Product, check_in, check_out;


    public ClientProduct(int id, String quantity, String cp_Date, String id_user, String id_Client, String id_Product, String check_in, String check_out) {

        this.id = id;
        this.quantity = quantity;
        this.cp_Date = cp_Date;
        this.id_user = id_user;
        this.id_Client = id_Client;
        this.id_Product = id_Product;
        this.check_in = check_in;
        this.check_out = check_out;

    }

    public int getId() {

        return id;
    }

    public String getquantity() {

        return quantity;
    }

    public String getCp_Date() {
        return cp_Date;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_Client() {
        return id_Client;
    }

    public String getId_Product() {
        return id_Product;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }
}



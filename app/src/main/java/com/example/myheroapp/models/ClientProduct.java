package com.example.myheroapp.models;

public class ClientProduct {
    private int id;
    private int quantity;
    private int id_user;
    private int id_Client;
    private int id_Product;
    private String cp_Date;
    private String check_in;
    private String check_out;

    public ClientProduct(int id, int quantity, int id_user, int id_Client, int id_Product, String cp_Date, String check_in, String check_out) {
        this.id = id;
        this.quantity = quantity;
        this.id_user = id_user;
        this.id_Client = id_Client;
        this.id_Product = id_Product;
        this.cp_Date = cp_Date;
        this.check_in = check_in;
        this.check_out = check_out;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_Client() {
        return id_Client;
    }

    public void setId_Client(int id_Client) {
        this.id_Client = id_Client;
    }

    public int getId_Product() {
        return id_Product;
    }

    public void setId_Product(int id_Product) {
        this.id_Product = id_Product;
    }

    public String getCp_Date() {
        return cp_Date;
    }

    public void setCp_Date(String cp_Date) {
        this.cp_Date = cp_Date;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

}



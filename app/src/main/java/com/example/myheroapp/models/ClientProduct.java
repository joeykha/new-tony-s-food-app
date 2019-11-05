package com.example.myheroapp.models;

public class ClientProduct {
    private int id;
    private int quantity;
    private String cp_Date;
    private int id_user;
    private int id_Client;
    private int id_Product;



    public ClientProduct(int id, int quantity, String cp_Date, int id_user, int id_Client, int id_Product) {
        this.id = id;
        this.quantity = quantity;
        this.cp_Date = cp_Date;
        this.id_user = id_user;
        this.id_Client = id_Client;
        this.id_Product = id_Product;
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


}



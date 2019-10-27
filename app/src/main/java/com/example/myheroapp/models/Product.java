package com.example.myheroapp.models;


public class Product  {

    private int id;
    private String name;
    private int tmpQuantity;


    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(int id, String name, int tmpQuantity) {
        this.id = id;
        this.name = name;
        this.tmpQuantity = tmpQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTmpQuantity() {
        return tmpQuantity;
    }

    public void addTmpQuantity(int tmpQuantity) {
        this.tmpQuantity += tmpQuantity;
    }

}





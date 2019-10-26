package com.example.myheroapp.models;

import java.io.Serializable;

public class Client implements Serializable {

    private int id;
    private String name, location ;


    public Client(int id, String name, String location) {
        //public User(int id, String firstName, String fatherName, String lastName, String userName, String password) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    }


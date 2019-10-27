package com.example.myheroapp.models;


import java.io.Serializable;

public class LUser implements Serializable {

    private int id;
    private String firstName, fatherName, lastName,userName;
    private int phoneNumber,isActive,isAdmin;

    public LUser(int id, String firstName, String fatherName, String lastName, String userName,  int phoneNumber, int isActive, int isAdmin) {
        // public User(int id, String firstName, String fatherName, String lastName, String userName, String password,  int phoneNumber, int isActive, int isAdmin) {

        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.userName = userName;
        // this.password= password;
        this.phoneNumber=phoneNumber;
        this.isActive=isActive;
        this.isAdmin=isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    // public String getPassword() {
    //  return password;
    //}

    public  int getPhoneNumber(){
        return phoneNumber;
    }

    public int getIsActive(){
        return  isActive;
    }

    public int getIsAdmin(){
        return  isAdmin;
    }
}
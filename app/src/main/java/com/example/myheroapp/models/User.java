package com.example.myheroapp.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "FIRST_NAME")
    private String firstName;

    @ColumnInfo(name = "FATHER_NAME")
    private String fatherName;

    @ColumnInfo(name = "LAST_NAME")
    private String lastName;

    @ColumnInfo(name = "USER_NAME")
    private String userName;

    @Ignore
    private String password;


    @ColumnInfo(name = "PHONE_NUMBER")
    private int phoneNumber;

    @ColumnInfo(name = "IS_ACTIVE")
    private int isActive;

    @ColumnInfo(name = "IS_ADMIN")
    private int isAdmin;



    public User(int id, String firstName, String fatherName, String lastName, String userName,  int phoneNumber, int isActive, int isAdmin) {

        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }


    @Ignore
    public User(int id, String userName) {
        this.userName = userName;
        this.id = id;
    }
    @Ignore
    public User(int id, String firstName, String fatherName, String lastName, String userName, String password, int phoneNumber, int isActive, int isAdmin) {

        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
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

    public String getPassword() {
        return password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getIsActive() {
        return isActive;
    }

    public int getIsAdmin() {
        return isAdmin;
    }
}
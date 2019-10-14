package com.example.myheroapp.models;

public class Schedule {

    private int id, id_User, id_Client;
    private String mUser, mLocation;
    private String Sch_Date;
    private String imageBefore, imageAfter;


    public Schedule(int id, String user, String location, String sch_Date) {
        this.id = id;
        this.mUser = user;
        this.mLocation = location;
        this.Sch_Date = sch_Date;
    }

    public Schedule(int id, int id_User, int id_Client, String sch_Date) {
        this.id = id;
        this.id_User = id_User;
        this.id_Client = id_Client;
        this.Sch_Date = sch_Date;
    }

    public int getId() {
        return id;
    }

    public int getid_User() {
        return id_User;
    }

    public int getid_Client() {
        return id_Client;
    }

    public String getSch_Date() {return Sch_Date; }

    public String getImageBefore() {return imageBefore; }

    public String getImageAfter() {return imageAfter; }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
}


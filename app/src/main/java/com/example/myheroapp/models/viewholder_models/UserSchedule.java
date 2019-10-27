package com.example.myheroapp.models.viewholder_models;

import com.example.myheroapp.models.Client;

public class UserSchedule {

    private Client mClient;
    private String mScheduleDate;

    public UserSchedule(Client client, String scheduleDate) {
        mClient = client;
        mScheduleDate = scheduleDate;
    }


    public Client getClient() {
        return mClient;
    }

    public void setClient(Client client) {
        mClient = client;
    }

    public String getScheduleDate() {
        return mScheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        mScheduleDate = scheduleDate;
    }
}

package com.example.myheroapp.resources;


public class ScheduleApi {

    private static final String ROOT_URL = String.format("%sTonysFood/v1/scheduleApi.php?apicall=", ClientApi.IP_ADDRESS);

    public static final String URL_CREATE_SCHEDULE = ROOT_URL + "createSchedule";
    public static final String URL_READ_SCHEDULE = ROOT_URL + "getSchedules";
    public static final String URL_UPDATE_SCHEDULE = ROOT_URL + "updateSchedule";
    public static final String URL_DELETE_SCHEDULE = ROOT_URL + "deleteSchedule&id=";
}
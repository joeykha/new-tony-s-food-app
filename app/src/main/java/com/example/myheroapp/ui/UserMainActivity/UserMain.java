package com.example.myheroapp.ui.UserMainActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.common.helpers.SharedPrefManager;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.models.LUser;
import com.example.myheroapp.models.Schedule;
import com.example.myheroapp.models.viewholder_models.UserSchedule;
import com.example.myheroapp.resources.ClientApi;
import com.example.myheroapp.resources.ScheduleApi;
import com.example.myheroapp.ui.CheckInActivity.CheckInActivity;
import com.example.myheroapp.ui.ClientAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class UserMain extends AppCompatActivity implements ClientAdapter.UserMainInterface {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String CLIENT_REQUEST = "CLIENT_REQUEST";
    private static final String SCHEDULE_REQUEST = "SCHEDULE_REQUEST";

    public static final String CLIENT_TAG = "CLIENT_TAG";
    public static final String USER_TAG = "USER_TAG";

    ProgressBar pb;
    TextView tvUsername, tvCurrentDate;

    List<Object> rvItems;
    RecyclerView rvClients;

    LUser user;
    List<Client> allClients;
    List<Schedule> allSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        pb = findViewById(R.id.pb);
        rvClients = findViewById(R.id.rvClients);
        rvClients.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        tvUsername = findViewById(R.id.tvUsername);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);

        user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        if (user != null) {
            tvUsername.setText(String.format("Welcome %s", user.getUserName()));
        }
        //todo change date format
        tvCurrentDate.setText(Calendar.getInstance().getTime().toString());

        readclients();
        readSchedules();

    }

    @Override
    public void onBackPressed() {

    }

    private void readSchedules() {
        PerformNetworkRequest request = new PerformNetworkRequest(ScheduleApi.URL_READ_SCHEDULE, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readclients() {
        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_READ_CLIENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if(url.equals(ClientApi.URL_READ_CLIENTS)){
                        getData(object.getJSONArray("client"), CLIENT_REQUEST);
                    }else if (url.equals(ScheduleApi.URL_READ_SCHEDULE)){
                        getData(object.getJSONArray("schedule"), SCHEDULE_REQUEST);
                        List<Object> scheduleClients = findUserSchedules();
                        if(scheduleClients.size() > 0){
                            refreshClientAdapter(scheduleClients);
                        }else{
                            //if no schedule found
                            List<Object> obj = new ArrayList<>();
                            obj.add(ClientAdapter.NO_SCHEDULE_FOUND_TAG);
                            refreshClientAdapter(obj);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void getData(JSONArray objects, String requestType) throws JSONException {

        if(requestType == CLIENT_REQUEST){
            if (allClients == null) {
                allClients = new ArrayList<>();
            } else {
                allClients.clear();
            }
            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);
                allClients.add(new Client(
                        obj.getInt("id"),
                        obj.getString("name"),
                        obj.getString("location")
                ));
            }
        }else if (requestType == SCHEDULE_REQUEST) {
            if (allSchedules == null) {
                allSchedules = new ArrayList<>();
            } else {
                allSchedules.clear();
            }
            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);
                allSchedules.add(new Schedule(
                        obj.getInt("id"),
                        obj.getInt("id_User"),
                        obj.getInt("id_Client"),
                        obj.getString("sch_Date")
                ));
            }
        }
    }

    public void refreshClientAdapter(List<Object> objects){
        ClientAdapter adapter = new ClientAdapter(getApplicationContext());
        adapter.setItems(objects);
        adapter.setUserMainInterface(this);
        rvClients.setAdapter(adapter);
    }
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    public List<Object> findUserSchedules(){
        List<Object> userSchedules = new ArrayList<>();
        Schedule schedule;
        for(int i = 0; i < allSchedules.size();i++ ){
            schedule = allSchedules.get(i);
            if(schedule.getid_User() == user.getId()){
                for(int j= 0; j< allClients.size(); j++){
                    if(schedule.getSch_Date().equals(date)  &&  allClients.get(j).getId() == schedule.getid_Client() ){
                        userSchedules.add(new UserSchedule(allClients.get(j), schedule.getSch_Date()));
                    }

                }
            }
        }
        return userSchedules;
    }

    @Override
    public void OnCheckInClicked(Client client) {
        Bundle args = new Bundle();
        args.putSerializable(CLIENT_TAG, client);
        args.putSerializable(USER_TAG, user);
        startActivity(new Intent(UserMain.this, CheckInActivity.class).putExtras(args));
    }
}

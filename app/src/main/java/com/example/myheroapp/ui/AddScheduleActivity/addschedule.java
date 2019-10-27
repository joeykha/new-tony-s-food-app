package com.example.myheroapp.ui.AddScheduleActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.common.helpers.Helper;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.models.Schedule;
import com.example.myheroapp.models.User;
import com.example.myheroapp.resources.ClientApi;
import com.example.myheroapp.resources.ScheduleApi;
import com.example.myheroapp.resources.UserApi;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class addschedule extends AppCompatActivity implements ScheduleAdapter.DeleteScheduleInterface {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private static final String USER_REQUEST = "USER_REQUEST";
    private static final String CLIENT_REQUEST = "CLIENT_REQUEST";
    private static final String SCHEDULE_REQUEST = "SCHEDULE_REQUEST";

    //Objects
    private List<User> mUsers;
    private CharSequence[] mUserSelectionList;
    private int mSelectedUserId;

    private List<Client> mClients;
    private CharSequence[] mLocationSelectionList;
    private int mSelectedClientId;

    private List<Schedule> mSchedules;

    private String mSelectedScheduleDate;
    private String mSearchScheduleDate;

    private TextInputEditText mTietUsername, mTietLocation, mTietDate, mTietSearchDate;
    private TextInputLayout mTilUsername, mTilLocation, mTilDate, mTilSearchDate;
    private Button mBtnAdd;
    private Button mBtnSearch;

    private RecyclerView mRvSchedule;
    private ScheduleAdapter mAdapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addschedule);
        mTietUsername = findViewById(R.id.tietUsername);
        mTietLocation = findViewById(R.id.tietLocation);
        mTietDate = findViewById(R.id.tietDate);
        mTietSearchDate = findViewById(R.id.tietSearchDate);
        mTilUsername = findViewById(R.id.tilUsername);
        mTilLocation = findViewById(R.id.tilLocation);
        mTilDate = findViewById(R.id.tilDate);
        mTilSearchDate = findViewById(R.id.tilSearchDate);

        mBtnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);
        readSchedules();
        readUsers();
        readClients();


        mTietUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(addschedule.this)
                        .setTitle("Choose a user")
                        .setSingleChoiceItems(mUserSelectionList, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTietUsername.setText(mUserSelectionList[i]);
                                mSelectedUserId = mUsers.get(i).getId();
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        mTietLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(addschedule.this)
                        .setTitle("Choose a client")
                        .setSingleChoiceItems(mLocationSelectionList, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTietLocation.setText(mLocationSelectionList[i]);
                                mSelectedClientId = mClients.get(i).getId();
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        mTietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(addschedule.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            mSelectedScheduleDate = outputFormat.format(calendar.getTime());
                        } catch (Exception e) {

                        }
                        String myFormat = "EEEE MMMM dd, yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        mTietDate.setText(sdf.format(calendar.getTime()));
                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());

                dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1296000000);
                dpd.show();
            }
        });


        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo check error message

                if (mTietUsername.getText().toString().trim().equals("")) {
                    mTietUsername.setError("Required");
                }
                if (mTietLocation.getText().toString().trim().equals("")) {
                    mTilLocation.setError("Required");
                }
                if (mTietDate.getText().toString().trim().equals("")) {
                    mTilDate.setError("Required");
                }

                if (!mTietUsername.getText().toString().trim().equals("") && !mTietLocation.getText().toString().trim().equals("")
                        && !mTietDate.getText().toString().trim().equals("")) {
                    mTilLocation.setErrorEnabled(false);
                    mTilDate.setErrorEnabled(false);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id_User", String.valueOf(mSelectedUserId));
                    params.put("id_Client", String.valueOf(mSelectedClientId));
                    params.put("sch_Date", mSelectedScheduleDate);

                    PerformNetworkRequest request = new PerformNetworkRequest(ScheduleApi.URL_CREATE_SCHEDULE, params, CODE_POST_REQUEST);
                    request.execute();
                    Helper.hideKeyboard(addschedule.this);
                    readSchedules();
                }
            }
        });

        mRvSchedule = findViewById(R.id.rvScheduals);
        mRvSchedule.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        mAdapter = new ScheduleAdapter(getApplicationContext());
        mAdapter.setDeleteScheduleInterface(this);


        mTietSearchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(addschedule.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            mSearchScheduleDate = outputFormat.format(calendar.getTime());
                        } catch (Exception e) {

                        }
                        String myFormat = "EEEE MMMM dd "; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        mTietSearchDate.setText(sdf.format(calendar.getTime()));

                        List<Schedule> chosenSchedules = new ArrayList<>();
                        SimpleDateFormat outputFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        for (int i = 0; i < mSchedules.size(); i++) {
                            String scheduleDateText = mSchedules.get(i).getSch_Date();
                            String scheduleDate = "";
                            try {
                                Date tmpDate = outputFormat1.parse(scheduleDateText);
                                scheduleDate = outputFormat1.format(tmpDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (scheduleDate != null && mSearchScheduleDate.equals(scheduleDate)) {
                                Schedule curSchedule = mSchedules.get(i);
                                Schedule tmpSchedule = new Schedule(curSchedule.getId(), getUserNameById(mUsers, curSchedule.getid_User()), getLocationById(mClients, curSchedule.getid_Client()), scheduleDate);
                                chosenSchedules.add(tmpSchedule);
                            }
                        }
                        mAdapter.setItems(chosenSchedules);
                        mRvSchedule.setAdapter(mAdapter);

                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 1296000000);
                dpd.show();
            }
        });
    }


    private void readSchedules() {
        PerformNetworkRequest request = new PerformNetworkRequest(ScheduleApi.URL_READ_SCHEDULE, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void deleteSchedule(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(ScheduleApi.URL_DELETE_SCHEDULE + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshList(JSONArray objects, String requestType) throws JSONException {
        if (requestType == USER_REQUEST) {
            int activeUserCount = 0;
            if (mUsers == null) {
                mUsers = new ArrayList<>();
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject obj = objects.getJSONObject(i);
                    if (obj.getInt("isActive") == 1 && obj.getInt("isAdmin") == 0) {
                        activeUserCount++;
                    }
                }
                mUserSelectionList = new CharSequence[activeUserCount];
            } else {
                mUsers.clear();
            }

            int selectionIndexInsert = 0;
            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);
                if (obj.getInt("isActive") == 1 && obj.getInt("isAdmin") == 0) {
                    mUsers.add(new User(
                            obj.getInt("id"),
                            obj.getString("userName")
                    ));

                    mUserSelectionList[selectionIndexInsert++] = obj.getString("userName");
                }

            }
        } else if (requestType == CLIENT_REQUEST) {
            if (mClients == null) {
                mClients = new ArrayList<>();
                mLocationSelectionList = new CharSequence[objects.length()];
            } else {
                mClients.clear();
            }
            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);

                mClients.add(new Client(
                        obj.getInt("id"),
                        obj.getString("name"),
                        obj.getString("location")
                ));

                mLocationSelectionList[i] = String.format("%s - %s", obj.getString("name"), obj.getString("location"));
            }
        } else if (requestType == SCHEDULE_REQUEST) {
            if (mSchedules == null) {
                mSchedules = new ArrayList<>();
            } else {
                mSchedules.clear();
            }
            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);
                mSchedules.add(new Schedule(
                        obj.getInt("id"),
                        obj.getInt("id_User"),
                        obj.getInt("id_Client"),
                        obj.getString("sch_Date")
                ));
            }
        }
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (url.equals(UserApi.URL_READ_USERS)) {
                        refreshList(object.getJSONArray("user"), USER_REQUEST);
                    } else if (url.equals(ClientApi.URL_READ_CLIENTS)) {
                        refreshList(object.getJSONArray("client"), CLIENT_REQUEST);
                    } else if (url.equals(ScheduleApi.URL_READ_SCHEDULE)) {
                        refreshList(object.getJSONArray("schedule"), SCHEDULE_REQUEST);
                    } else if (url.equals(ScheduleApi.URL_CREATE_SCHEDULE)) {
                        readSchedules();
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

    private void readUsers() {
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_READ_USERS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readClients() {
        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_READ_CLIENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    public String getUserNameById(List<User> users, int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i).getUserName();
            }
        }
        return "";
    }

    public String getLocationById(List<Client> clients, int id) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == id) {
                return String.format("%s - %s", clients.get(i).getName(), clients.get(i).getLocation());
            }
        }
        return "";
    }

    @Override
    public void OnScheduleDeleted(final int scheduleId, final int position) {
        new AlertDialog.Builder(addschedule.this)
                .setCancelable(false)
                .setTitle("Delete Schedule")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSchedule(scheduleId);
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}

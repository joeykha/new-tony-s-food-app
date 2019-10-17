package com.example.myheroapp.ui.AddUserActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.models.User;
import com.example.myheroapp.resources.UserApi;
import com.example.myheroapp.ui.AddClientActivity.addclient;
import com.example.myheroapp.ui.AddScheduleActivity.addschedule;
import com.example.myheroapp.ui.AdminMainActivity.AdminMain;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class addUser extends AppCompatActivity implements UserAdapter.UserInterface {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    TextInputLayout tilFirstName, tilFathersName, tilLastName, tilUsername, tilPassword, tilPhoneNumber;
    TextInputEditText tietFirstName, tietFathersName, tietLastName, tietUsername, tietPassword, tietPhoneNumber;
    ToggleButton toggleIsActive, toggleIsAdmin;
    ProgressBar progressBar;
    Button buttonAddUpdate;

    RecyclerView rvUsers;
    UserAdapter userAdapter;
    List<User> userList;

    boolean isUpdating = false;
    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        rvUsers = findViewById(R.id.rvUsers);

        tilFirstName = findViewById(R.id.tilFirstName);
        tilFathersName = findViewById(R.id.tilFathersName);
        tilLastName = findViewById(R.id.tilLastName);
        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);

        tietFirstName = findViewById(R.id.tietFirstName);
        tietFathersName = findViewById(R.id.tietFathersName);
        tietLastName = findViewById(R.id.tietLastName);
        tietUsername = findViewById(R.id.tietUsername);
        tietPassword = findViewById(R.id.tietPassword);
        tietPhoneNumber = findViewById(R.id.tietPhoneNumber);
        toggleIsActive = findViewById(R.id.toggleIsActive);
        toggleIsAdmin = findViewById(R.id.toggleIsAdmin);


        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);

        progressBar = findViewById(R.id.progressBar);

        userList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpdating) {
                    updateUser();
                } else {
                    createUser();
                }
            }
        });
        readUsers();

    }


    private void movetoaddclient() {
        Intent intent = new Intent(addUser.this, addclient.class);
        startActivity(intent);
    }

    private void movetoaddproduct() {
        Intent intent = new Intent(addUser.this, AdminMain.class);
        startActivity(intent);
    }


    private void createUser() {
        String firstname = tietFirstName.getText().toString().trim();
        String fathername = tietFathersName.getText().toString().trim();
        String lastname = tietLastName.getText().toString().trim();
        String username = tietUsername.getText().toString().trim();
        String password = tietPassword.getText().toString().trim();
        String hashed_password = md5(password);
        String phonenumber = tietPhoneNumber.getText().toString().trim();
        String isactive = "1";
        if (toggleIsActive.isChecked() == true) {
            isactive = "1";
        } else {
            isactive = "0";
        }
        String isadmin = "1";
        if (toggleIsAdmin.isChecked() == true) {
            isadmin = "1";
        } else {
            isadmin = "0";
        }


        if (TextUtils.isEmpty(firstname)) {
            tietFirstName.setError("Please enter First Name");
            tietFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fathername)) {
            tietFathersName.setError("Please enter Father Name");
            tietFathersName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            tietLastName.setError("Please enter Last Name");
            tietLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            tietUsername.setError("Please enter Username");
            tietUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            tietPassword.setError("Please enter Password");
            tietPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phonenumber)) {
            tietPhoneNumber.setError("Please enter Phone Number");
            tietPhoneNumber.requestFocus();
            return;
        }

/// add condition for empty fields

        HashMap<String, String> params = new HashMap<>();
        params.put("firstName", firstname);
        params.put("fatherName", fathername);
        params.put("lastName", lastname);
        params.put("userName", username);
        params.put("password", hashed_password);
        params.put("phoneNumber", String.valueOf(phonenumber));
        params.put("isActive", isactive);
        params.put("isAdmin", isadmin);

        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_CREATE_USER, params, CODE_POST_REQUEST);
        request.execute();
        tietFirstName.setText("");
        tietFathersName.setText("");
        tietLastName.setText("");
        tietUsername.setText("");
        tietPassword.setText("");
        tietPhoneNumber.setText("");
        toggleIsActive.setChecked(false);
        toggleIsAdmin.setChecked(false);
    }

    private void readUsers() {
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_READ_USERS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateUser() {

        String firstname = tietFirstName.getText().toString().trim();
        String fathername = tietFathersName.getText().toString().trim();
        String lastname = tietLastName.getText().toString().trim();
        String username = tietUsername.getText().toString().trim();
        String password = tietPassword.getText().toString().trim();
        String phonenumber = tietPhoneNumber.getText().toString().trim();
        String isactive = "1";
        if (toggleIsActive.isChecked() == true) {
            isactive = "1";
        } else {
            isactive = "0";
        }
        String isadmin = "1";
        if (toggleIsAdmin.isChecked() == true) {
            isadmin = "1";
        } else {
            isadmin = "0";
        }


        if (TextUtils.isEmpty(firstname)) {
            tietFirstName.setError("Please enter First Name");
            tietFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fathername)) {
            tietFathersName.setError("Please enter Father Name");
            tietFathersName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            tietLastName.setError("Please enter Last Name");
            tietLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            tietUsername.setError("Please enter Username");
            tietUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            tietPassword.setError("Please enter Password");
            tietPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phonenumber)) {
            tietPhoneNumber.setError("Please enter Phone Number");
            tietPhoneNumber.requestFocus();
            return;
        }

        //todo check why password is commented
        HashMap<String, String> params = new HashMap<>();
        params.put("firstName", firstname);
        params.put("fatherName", fathername);
        params.put("lastName", lastname);
        params.put("userName", username);
//        params.put("password", password);
        params.put("phoneNumber", String.valueOf(phonenumber));
        params.put("isActive", isactive);
        params.put("isAdmin", isadmin);


        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        tietFirstName.setText("");
        tietFathersName.setText("");
        tietLastName.setText("");
        tietUsername.setText("");
        tietPassword.setText("");
        tietPassword.setVisibility(View.VISIBLE);
        tietPhoneNumber.setText("");
        toggleIsActive.setChecked(false);
        toggleIsAdmin.setChecked(false);

        isUpdating = false;
    }

    private void deleteUser(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_DELETE_USER + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshUserList(JSONArray user) throws JSONException {
        userList.clear();

        for (int i = 0; i < user.length(); i++) {
            JSONObject obj = user.getJSONObject(i);

            userList.add(new User(
                    obj.getInt("id"),
                    obj.getString("firstName"),
                    obj.getString("fatherName"),
                    obj.getString("lastName"),
                    obj.getString("userName"),
                    obj.getString("password"),
                    obj.getInt("phoneNumber"),
                    obj.getInt("isActive"),
                    obj.getInt("isAdmin")
            ));
        }

        rvUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false) );
        userAdapter = new UserAdapter(getApplicationContext());
        userAdapter.setUserInterface(this);
        userAdapter.setItems(userList);
        rvUsers.setAdapter(userAdapter);
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
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (url.equals(UserApi.URL_READ_USERS)) {
                        refreshUserList(object.getJSONArray("user"));
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

    static public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void OnUserDeleted(final int userId, final int position) {
        new AlertDialog.Builder(addUser.this)
                .setCancelable(false)
                .setTitle("Delete User")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(userId);
                        userAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void OnUserUpdated(int userId) {

    }

}


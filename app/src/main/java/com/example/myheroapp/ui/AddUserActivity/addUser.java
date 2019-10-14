package com.example.myheroapp.ui.AddUserActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.models.User;
import com.example.myheroapp.resources.UserApi;
import com.example.myheroapp.ui.AddClientActivity.addclient;
import com.example.myheroapp.ui.AdminMainActivity.AdminMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class addUser extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    EditText editTextUserId, editTextFirstName, editTextFatherName, editTextLastName, editTextUserName, editTextPassword, editTextPhoneNumber;
    ToggleButton toggleIsActive, toggleIsAdmin;
    ProgressBar progressBar;

    ListView listView;
    Button buttonAddUpdate;
    List<User> userList;

    boolean isUpdating = false;
    private Button BtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_adduser);
        setContentView(R.layout.activity_user);

        editTextUserId = (EditText) findViewById(R.id.editTextUserId);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextFatherName = (EditText) findViewById(R.id.editTextFatherName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        toggleIsActive = (ToggleButton) findViewById(R.id.toggleIsActive);
        toggleIsAdmin = (ToggleButton) findViewById(R.id.toggleIsAdmin);


        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewUsers);

        userList = new ArrayList<>();


        BtnMove = findViewById(R.id.buttonclient);

//        BtnMove.setOnClickListener(new View.OnClickListener() {
        //          @Override
        //        public void onClick(View view) {
        //          movetoaddclient();
        //    }
        //});

        BtnMove = findViewById(R.id.buttonproductt);

//        BtnMove.setOnClickListener(new View.OnClickListener() {
        //          @Override
        //        public void onClick(View view) {
        //          movetoaddproduct();
        //    }
        // });


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
        String firstname = editTextFirstName.getText().toString().trim();
        String fathername = editTextFatherName.getText().toString().trim();
        String lastname = editTextLastName.getText().toString().trim();
        String username = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String hashed_password = md5(password);
        String phonenumber = editTextPhoneNumber.getText().toString().trim();
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
            editTextFirstName.setError("Please enter First Name");
            editTextFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fathername)) {
            editTextFatherName.setError("Please enter Father Name");
            editTextFatherName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            editTextLastName.setError("Please enter Last Name");
            editTextLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            editTextUserName.setError("Please enter Username");
            editTextUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter Password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phonenumber)) {
            editTextPhoneNumber.setError("Please enter Phone Number");
            editTextPhoneNumber.requestFocus();
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
        editTextFirstName.setText("");
        editTextFatherName.setText("");
        editTextLastName.setText("");
        editTextUserName.setText("");
        editTextPassword.setText("");
        editTextPhoneNumber.setText("");
        toggleIsActive.setChecked(false);
        toggleIsAdmin.setChecked(false);
    }

    private void readUsers() {
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_READ_USERS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateUser() {
        String id = editTextUserId.getText().toString();
        String firstname = editTextFirstName.getText().toString().trim();
        String fathername = editTextFatherName.getText().toString().trim();
        String lastname = editTextLastName.getText().toString().trim();
        String username = editTextUserName.getText().toString().trim();
        //String password = editTextPassword.getText().toString().trim();
        String phonenumber = editTextPhoneNumber.getText().toString();
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
            editTextFirstName.setError("Please enter First Name");
            editTextFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fathername)) {
            editTextFatherName.setError("Please enter Father Name");
            editTextFatherName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            editTextLastName.setError("Please enter Last Name");
            editTextLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            editTextUserName.setError("Please enter Username");
            editTextUserName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(phonenumber)) {
            editTextPhoneNumber.setError("Please enter Phone Number");
            editTextPhoneNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            editTextPhoneNumber.setError("Please enter Phone Number");
            editTextPhoneNumber.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("firstName", firstname);
        params.put("fatherName", fathername);
        params.put("lastName", lastname);
        params.put("userName", username);
        //params.put("password", password);
        params.put("phoneNumber", String.valueOf(phonenumber));
        params.put("isActive", isactive);
        params.put("isAdmin", isadmin);


        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextFirstName.setText("");
        editTextFatherName.setText("");
        editTextLastName.setText("");
        editTextUserName.setText("");
        editTextPassword.setVisibility(View.VISIBLE);
        editTextPassword.setText("");
        editTextPhoneNumber.setText("");
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

        UserAdapter adapter = new UserAdapter(userList);
        listView.setAdapter(adapter);
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
                    //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshUserList(object.getJSONArray("user"));
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

    class UserAdapter extends ArrayAdapter<User> {
        List<User> userList;

        public UserAdapter(List<User> userList) {
            super(addUser.this, R.layout.layout_user_list, userList);
            this.userList = userList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

            TextView textViewfirstName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final User user = userList.get(position);

            textViewfirstName.setText(user.getFirstName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextUserId.setText(String.valueOf(user.getId()));
                    editTextFirstName.setText(user.getFirstName());
                    editTextFatherName.setText(user.getFatherName());
                    editTextLastName.setText(user.getLastName());
                    editTextUserName.setText(user.getUserName());
                    editTextPassword.setVisibility(GONE);
                    //editTextPassword.setText("");
                    editTextPhoneNumber.setText(String.valueOf(user.getPhoneNumber()));
                    if (user.getIsActive() == 1) {
                        toggleIsActive.setChecked(true);
                    } else {
                        toggleIsActive.setChecked(false);
                    }
                    if (user.getIsAdmin() == 1) {
                        toggleIsAdmin.setChecked(true);
                    } else {
                        toggleIsAdmin.setChecked(false);
                    }

                    // ratingBar.setRating(hero.getRating());

                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(addUser.this);

                    builder.setTitle("Delete " + user.getFirstName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteUser(user.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            });

            return listViewItem;
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

}


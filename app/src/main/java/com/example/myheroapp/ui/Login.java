package com.example.myheroapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.helpers.SharedPrefManager;
import com.example.myheroapp.models.LUser;
import com.example.myheroapp.resources.LoginApi;
import com.example.myheroapp.ui.AddUserActivity.addUser;
import com.example.myheroapp.ui.AdminMainActivity.AdminMain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    //todo fix background
    //EditText editTextUsername, editTextPassword;
    Button Login_button;
    EditText login_password,login_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Login_button = (Button) findViewById(R.id.Login_button);
        login_password = (EditText) findViewById(R.id.login_password);
        login_username = (EditText) findViewById(R.id.login_username);

        //editTextUsername = (EditText) findViewById(R.id.editTextUsername);
       // editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


    }

    private void userLogin() {
        //first getting the values
        final String username = login_username.getText().toString();
        final String password = login_password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            login_username.setError("Please enter your username");
            login_username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            login_password.setError("Please enter your password");
            login_password.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        LUser user = new LUser(
                                userJson.getInt("id"),
                                userJson.getString("firstName"),
                                userJson.getString("fatherName"),
                                userJson.getString("lastName"),
                                userJson.getString("userName"),
                                userJson.getInt("phoneNumber"),
                                userJson.getInt("isActive"),
                                userJson.getInt("isAdmin")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        ///???????????????????????????
                        ///???????????????????????????
                        ///???????????????????????????
                        ///??????????????????????????

                        if(user.getIsActive() == 0){
                            Toast.makeText(getApplicationContext(), getString(R.string.user_not_active) , Toast.LENGTH_LONG).show();
                        }else if(user.getIsAdmin() == 1){
                            startActivity(new Intent(getApplicationContext(), AdminMain.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), UserMain.class));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("userName", username);
                params.put("password", addUser.md5(password));

                //returing the response
                return requestHandler.sendPostRequest(LoginApi.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
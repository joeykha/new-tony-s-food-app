package com.example.myheroapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myheroapp.R;


public class loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Thread xyz= new Thread()
        {
            public void run()
            {
                try{
                    sleep(1000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent myIntent = new Intent(loading.this, Login.class);
                    startActivity(myIntent);
                }

            }
        };
        xyz.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }

}

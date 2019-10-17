package com.example.myheroapp.ui.AdminMainActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.resources.ClientApi;
import com.example.myheroapp.ui.AddClientActivity.ClientAdapter;
import com.example.myheroapp.ui.AddClientActivity.addclient;
import com.example.myheroapp.ui.AddProductActivity.addproduct;
import com.example.myheroapp.ui.AddScheduleActivity.addschedule;
import com.example.myheroapp.ui.AddUserActivity.addUser;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class AdminMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdminMainAdapter.AdminMainInterface {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar pb;

    List<Object> rvItems;
    RecyclerView rvClients;
    AdminMainAdapter clientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        pb = findViewById(R.id.pb);
        rvClients = findViewById(R.id.rvAdminMain);
        rvClients.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL, false));


        readclients();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product) {
            Intent u = new Intent(AdminMain.this, addproduct.class);
            startActivity(u);

            // Handle the camera action
        } else if (id == R.id.nav_client) {
            Intent i = new Intent(AdminMain.this, addclient.class);
            startActivity(i);

        } else if (id == R.id.nav_User) {
            Intent p = new Intent(AdminMain.this, addUser.class);
            startActivity(p);

        } else if (id == R.id.nav_schedule) {
            Intent p = new Intent(AdminMain.this, addschedule.class);
            startActivity(p);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    refreshclientList(object.getJSONArray("client"));
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

    private void refreshclientList(JSONArray client) throws JSONException {
        List<Client> clientItems = new ArrayList<>();

        for (int i = 0; i < client.length(); i++) {
            JSONObject obj = client.getJSONObject(i);

            clientItems.add(new Client(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("location")
            ));
        }

        rvItems = new ArrayList<>();
        rvItems.add("Clients:");
        rvItems.addAll(clientItems);

        AdminMainAdapter adapter = new AdminMainAdapter(getApplicationContext());
        adapter.setItems(rvItems);
        adapter.setAdminMainInterface(this);
        rvClients.setAdapter(adapter);
    }

    @Override
    public void OnCheckStockClicked(int clientId) {
        startActivity(new Intent(AdminMain.this, ProductDetailsActivity.class));
    }
}


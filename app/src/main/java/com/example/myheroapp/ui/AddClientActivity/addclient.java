package com.example.myheroapp.ui.AddClientActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.resources.ClientApi;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class addclient extends AppCompatActivity implements ClientAdapter.DeleteClientInterface {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    TextInputEditText tietClientName, tietLocation;
    ProgressBar progressBar;

    RecyclerView rv;
    Button buttonAddUpdate;
    List<Client> clientList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclient);

        tietClientName = findViewById(R.id.tietClientName);
        tietLocation = findViewById(R.id.tietLocation);

        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);

        progressBar = findViewById(R.id.progressBar);
        rv = findViewById(R.id.rvClients);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        clientList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateClient();
                } else {
                    createClient();
                }
            }
        });
        readclients();
    }


    private void createClient() {
        String name = tietClientName.getText().toString().trim();
        String location = tietLocation.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            tietClientName.setError("Please enter Name");
            tietClientName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            tietLocation.setError("Please enter Location");
            tietLocation.requestFocus();
            return;
        }


/// add condition for empty fields

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("location", location);

        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_CREATE_CLIENT, params, CODE_POST_REQUEST);
        request.execute();
        tietClientName.setText("");
        tietLocation.setText("");
    }

    private void readclients() {
        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_READ_CLIENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

//todo to remove updateClient
    private void updateClient() {
        //todo get client id
//        String id = edittextclientid.getText().toString();
        String name = tietClientName.getText().toString().trim();
        String location = tietLocation.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            tietClientName.setError("Please enter Name");
            tietClientName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            tietLocation.setError("Please enter Location");
            tietLocation.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
//        params.put("id", String.valueOf(id));
        params.put("name", name);
        params.put("location", location);


        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_UPDATE_CLIENT, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        tietClientName.setText("");
        tietLocation.setText("");

        isUpdating = false;
    }

    private void deleteclient(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(ClientApi.URL_DELETE_CLIENT + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshclientList(JSONArray client) throws JSONException {
        clientList.clear();

        for (int i = 0; i < client.length(); i++) {
            JSONObject obj = client.getJSONObject(i);

            clientList.add(new Client(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("location")
            ));
        }

        ClientAdapter clientAdapter = new ClientAdapter(getApplicationContext());
        clientAdapter.setItems(clientList);
        clientAdapter.setDeleteClientInterface(this);
        rv.setAdapter(clientAdapter);
    }

    @Override
    public void OnClientDeleted(final int clientId) {
        new AlertDialog.Builder(addclient.this)
                .setTitle("Delete Client?")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteclient(clientId);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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

    //todo change from listView to recycleView
    class clientAdapter extends ArrayAdapter<Client> {
        private final List<Client> Clientlist;
        List<Client> clientList;

        public clientAdapter(List<Client> clientList) {
            super(addclient.this, R.layout.layout_client_list, clientList);
            this.Clientlist = clientList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_client_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Client client = Clientlist.get(position);

            textViewName.setText(client.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
//                    edittextclientid.setText(String.valueOf(client.getId()));
                    tietClientName.setText(client.getName());
                    tietLocation.setText(client.getLocation());

                    // ratingBar.setRating(hero.getRating());

                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(addclient.this);

                    builder.setTitle("Delete " + client.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteclient(client.getId());
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

}

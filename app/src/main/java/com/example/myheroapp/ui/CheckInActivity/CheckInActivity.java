package com.example.myheroapp.ui.CheckInActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myheroapp.R;
import com.example.myheroapp.RequestHandler;
import com.example.myheroapp.models.Client;
import com.example.myheroapp.models.ClientProduct;
import com.example.myheroapp.models.LUser;
import com.example.myheroapp.models.Product;
import com.example.myheroapp.models.viewholder_models.ProductQuantity;
import com.example.myheroapp.resources.ClientProductApi;
import com.example.myheroapp.resources.ProductApi;
import com.example.myheroapp.ui.UserMainActivity.UserMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class CheckInActivity extends AppCompatActivity implements StockCountAdapter.StockCountInterface {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    RecyclerView rv;
    Button btnPicBefore, btnPicAfter, btnCheckOut;


    List<ProductQuantity> productCounts;
    List<Product> products;
    Client client;
    LUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        progressBar = findViewById(R.id.pb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Check In");
        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        client = (Client) args.getSerializable(UserMain.CLIENT_TAG);
        user = (LUser) args.getSerializable(UserMain.USER_TAG);
        ((TextView) findViewById(R.id.tvClient)).setText(String.format("%s\n%s", client.getName(), client.getLocation()));


        rv = findViewById(R.id.rv);
        btnPicBefore = findViewById(R.id.btnPicBefore);
        btnPicAfter = findViewById(R.id.btnPicAfter);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));


        btnPicBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPicAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CheckInActivity.this)
                        .setCancelable(false)
                        .setTitle("Check Out?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Check Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (productCounts.size() > 0) {
                                    createClientProducts(productCounts);
                                    Intent intent = new Intent(CheckInActivity.this, UserMain.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        readProducts();
    }

    @Override
    public void onBackPressed() {


    }
    @Override
    public void OnAddQuantityClicked(int productId) {
        if (productCounts.size() > 0) {
            for (int i = 0; i < productCounts.size(); i++) {
                ProductQuantity productQuantity = productCounts.get(i);
                if ((productQuantity.getProductId()) == productId) {
                    productQuantity.addQuant(1);
                }
            }
        }
    }

    @Override
    public void OnRemoveQuantityClicked(int productId) {
        if (productCounts.size() > 0) {
            for (int i = 0; i < productCounts.size(); i++) {
                ProductQuantity productQuantity = productCounts.get(i);
                if ((productQuantity.getProductId()) == productId) {
                    productQuantity.addQuant(-1);
                }
            }
        }
    }

    @Override
    public void OnSetQuantityClicked(int productId, int quantity) {
        if (productCounts.size() > 0) {
            for (int i = 0; i < productCounts.size(); i++) {
                ProductQuantity productQuantity = productCounts.get(i);
                if ((productQuantity.getProductId()) == productId) {
                    productQuantity.setQuantity(quantity);
                }
            }
        }
    }

    private void getProducts(JSONArray productsArray) throws JSONException {
        products = new ArrayList<>();
        productCounts = new ArrayList<>();
        for (int j = 0; j < productsArray.length(); j++) {
                JSONObject obj = productsArray.getJSONObject(j);
                productCounts.add(new ProductQuantity(obj.getInt("id"), 0));
                    products.add(new Product(
                            obj.getInt("id"),
                            obj.getString("name"),
                            0
                    ));
            }

        if (products.size() > 0) {
            StockCountAdapter stockCountAdapter = new StockCountAdapter(getApplicationContext());
            stockCountAdapter.setStockCountInterface(this);
            stockCountAdapter.setItems(products);
            rv.setAdapter(stockCountAdapter);
        }
    }

//todo fix create ClientProduct
    private void createClientProducts(List<ProductQuantity> productQuantities) {
        String cpdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        for (int i = 0; i < productQuantities.size(); i++) {
            ProductQuantity productQuantity = productQuantities.get(i);
            HashMap<String, String> params = new HashMap<>();

            params.put("quantity", String.valueOf(productQuantity.getQuantity()));
            params.put("cp_Date", cpdate);
            params.put("id_User", String.valueOf(user.getId()));
            params.put("id_Client", String.valueOf(client.getId()));
            params.put("id_Product", String.valueOf(productQuantity.getProductId()));

            PerformNetworkRequest request = new PerformNetworkRequest(ClientProductApi.URL_CREATE_CLIENT_PRODUCT, params, CODE_POST_REQUEST);
            request.execute();


        }


    }

    private void readProducts() {
        PerformNetworkRequest request = new PerformNetworkRequest(ProductApi.URL_READ_PRODUCTS, null, CODE_GET_REQUEST);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            int count = 0;
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (url.equals(ProductApi.URL_READ_PRODUCTS)) {
                        getProducts(object.getJSONArray("product"));
                    } else if (url.equals(ClientProductApi.URL_CREATE_CLIENT_PRODUCT)) {
                        count++;
                        if(count == productCounts.size()){
                            CheckInActivity.this.finish();
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
}

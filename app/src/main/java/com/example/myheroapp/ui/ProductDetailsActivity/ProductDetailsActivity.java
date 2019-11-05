package com.example.myheroapp.ui.ProductDetailsActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.myheroapp.models.User;
import com.example.myheroapp.models.viewholder_models.ProductQuantity;
import com.example.myheroapp.resources.ClientProductApi;
import com.example.myheroapp.resources.ProductApi;
import com.example.myheroapp.resources.UserApi;
import com.example.myheroapp.ui.AdminMainActivity.AdminMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    RecyclerView rv;
    ProgressBar progressBar;
    TextView tvMerchandiser, tvClient, tvStockTime;
    Button btnStockCheck;

    List<ClientProduct> clientProducts;
    List<ProductQuantity> productQuantities;
    User user;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.pb);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle args = getIntent().getExtras();
        tvClient = findViewById(R.id.tvClient);
        client = (Client) args.getSerializable(AdminMain.CLIENT_TAG);
        tvClient.setText(String.format("%s\n%s", client.getName(), client.getLocation()));

        tvMerchandiser = findViewById(R.id.tvMerchandiser);
        tvStockTime = findViewById(R.id.tvStockTime);

        btnStockCheck = findViewById(R.id.btnStockCheck);
        btnStockCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo intent to stock images with client id
//                client.getId();
            }
        });


        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        readClientProducts();
        readProductQuantities();
        readUsers();


        //todo get api objects to display in recycler View (do not implement the recycler vie)


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private void refreshProductDetails(JSONArray clientProductsArray) throws JSONException {
        clientProducts = new ArrayList<>();
        for (int i = 0; i < clientProductsArray.length(); i++) {
            JSONObject obj = clientProductsArray.getJSONObject(i);
            if (client.getId() == obj.getInt("id_Client")) {
                clientProducts.add(new ClientProduct(
                        obj.getInt("id"),
                        obj.getInt("quantity"),
                        obj.getString("cp_Date"),
                        obj.getInt("id_User"),
                        obj.getInt("id_Client"),
                        obj.getInt("id_Product")
                ));
            }
        }


    }

    private void readClientProducts() {
        PerformNetworkRequest request = new PerformNetworkRequest(ClientProductApi.URL_READ_CLIENT_PRODUCT, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readUsers() {
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_READ_USERS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readProductQuantities() {
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
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (url.equals(ClientProductApi.URL_READ_CLIENT_PRODUCT)) {
                        refreshProductDetails(object.getJSONArray("client_product"));
                    } else if (clientProducts.size() > 0) {
                        if (url.equals(UserApi.URL_READ_USERS)) {
                            if (clientProducts.size() > 0) {
                                user = getUserById(object.getJSONArray("user"), clientProducts.get(0).getId_user());
                                if (user != null) {
                                    tvMerchandiser.setText(user.getUserName());
                                    tvStockTime.setText(clientProducts.get(0).getCp_Date());
                                }
                            }
                        } else if (url.equals(ProductApi.URL_READ_PRODUCTS)) {
                            productQuantities = getProductQuantites(object.getJSONArray("product"), clientProducts);
                            List<Object> rvItems = new ArrayList<Object>();
                            //todo fix database to get products
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
//                            rvItems.add(new ProductQuantity("Pringles", 90));
                            if (productQuantities.size() > 0) {
                                for (int i = 0; i < productQuantities.size(); i++) {
                                    ProductQuantity productQuantity = productQuantities.get(i);
                                    rvItems.add(new ProductQuantity(productQuantity.getProductName(), productQuantity.getQuantity()));
                                }
                            } else {
                                //no products found
                                rvItems.add(ProductDetailsAdapter.NO_PRODUCTS_FOUND);
                            }
                            ProductDetailsAdapter adapter = new ProductDetailsAdapter(getApplicationContext());
                            adapter.setItems(rvItems);
                            rv.setAdapter(adapter);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No product found!", Toast.LENGTH_SHORT).show();
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


    public User getUserById(JSONArray usersArray, int id) throws JSONException {

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject obj = usersArray.getJSONObject(i);
            if (obj.getInt("id") == id && obj.getInt("isActive") == 1 && obj.getInt("isAdmin") == 0) {
                return new User(
                        obj.getInt("id"),
                        obj.getString("userName")
                );
            }
        }
        return null;
    }

    public List<ProductQuantity> getProductQuantites(JSONArray productsArray, List<ClientProduct> products) throws JSONException {
        JSONObject obj;
        ClientProduct product;
        List<ProductQuantity> productQuantities = new ArrayList<>();
        for (int i = 0; i < productsArray.length(); i++) {
            obj = productsArray.getJSONObject(i);
            for (int j = 0; j < products.size(); j++) {
                product = products.get(j);
                if (obj.getInt("id") == product.getId_Product()) {
                    productQuantities.add(new ProductQuantity(
                            obj.getString("name"),
                            product.getQuantity()
                    ));
                }
            }
        }
        return productQuantities;
    }


    public void setAdapterItems(User user, Client client, List<ProductQuantity> productQuantities) {
        List<Object> items = new ArrayList<>();
        items.add(user.getUserName());
        items.add(String.format("%s - %s", client.getName(), client.getLocation()));
        //       items.add(new Pair<String,String>);

    }
}

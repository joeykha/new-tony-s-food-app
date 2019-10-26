package com.example.myheroapp.ui.ProductDetailsActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    List<ClientProduct> clientProducts;
    List<ProductQuantity> productQuantities;
    User user;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle args = getIntent().getExtras();
        client = (Client) args.getSerializable(AdminMain.CLIENT_TAG);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.pb);

        rv = findViewById(R.id.rv);
        readClientProducts();
        readUsers();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

            clientProducts.add(new ClientProduct(
                    obj.getInt("id"),
                    obj.getInt("quantity"),
                    obj.getInt("id_User"),
                    obj.getInt("id_Client"),
                    obj.getInt("id_Product"),
                    obj.getString("cp_Date"),
                    obj.getString("check_in"),
                    obj.getString("check_out")
            ));

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
                    if (url.equals(ClientProductApi.URL_READ_CLIENT_PRODUCT)) {
                        refreshProductDetails(object.getJSONArray("client_product"));
                    } else if (url.equals(ProductApi.URL_READ_PRODUCTS)) {
                        productQuantities = getProductQuantites(object.getJSONArray("client_product"), clientProducts);
                    } else if (url.equals(UserApi.URL_READ_USERS)) {
                        user = getUserById(object.getJSONArray("user"), clientProducts.get(0).getId_user());

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
                if (obj.getInt("id") == product.getId()) {
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

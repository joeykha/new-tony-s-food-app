package com.example.myheroapp.ui.AddProductActivity;

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
import com.example.myheroapp.models.Product;
import com.example.myheroapp.resources.ProductApi;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class addproduct extends AppCompatActivity implements ProductAdapter.DeleteProductInterface {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    TextInputEditText tietProductName;
    ProgressBar progressBar;

    RecyclerView rv;
    Button buttonAddUpdate;
    List<Product> productList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        tietProductName = findViewById(R.id.tietProductName);

        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);

        progressBar = findViewById(R.id.progressBar);
        rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        productList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateProduct();
                } else {
                    createProduct();
                }
            }
        });
        readProducts();
    }


    private void createProduct() {
        String name = tietProductName.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            tietProductName.setError("Please enter Name");
            tietProductName.requestFocus();
            return;
        }



/// add condition for empty fields

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);

        PerformNetworkRequest request = new PerformNetworkRequest(ProductApi.URL_CREATE_PRODUCT, params, CODE_POST_REQUEST);

        request.execute();
        tietProductName.setText("");

    }

    private void readProducts() {
        PerformNetworkRequest request = new PerformNetworkRequest(ProductApi.URL_READ_PRODUCTS, null, CODE_GET_REQUEST);
        request.execute();
    }


    private void updateProduct() {

        String name = tietProductName.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            tietProductName.setError("Please enter Name");
            tietProductName.requestFocus();
            return;
        }



        HashMap<String, String> params = new HashMap<>();
//        params.put("id", String.valueOf(id));
        params.put("name", name);


        PerformNetworkRequest request = new PerformNetworkRequest(ProductApi.URL_UPDATE_PRODUCT, params, CODE_POST_REQUEST);
        request.execute();
        buttonAddUpdate.setText("Add");

        tietProductName.setText("");

        isUpdating = false;
    }

    private void deleteProduct(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(ProductApi.URL_DELETE_PRODUCT + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshProductList(JSONArray product) throws JSONException {
        productList.clear();

        for (int i = 0; i < product.length(); i++) {
            JSONObject obj = product.getJSONObject(i);

            productList.add(new Product(
                    obj.getInt("id"),
                    obj.getString("name")
            ));
        }

        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext());


        productAdapter.setItems(productList);
        productAdapter.setDeleteProductInterface(this);
        rv.setAdapter(productAdapter);
    }

    @Override
    public void OnProductDeleted(final int productId) {
        new AlertDialog.Builder(addproduct.this)
                .setTitle("Delete Product?")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(productId);
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
                    refreshProductList(object.getJSONArray("product"));
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
    class productAdapter extends ArrayAdapter<Product> {
        private final List<Product> ProductList;
        List<Product> productList;

        public productAdapter(List<Product> productList) {
            super(addproduct.this, R.layout.layout_product_list, productList);
            this.ProductList = productList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_product_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Product product = ProductList.get(position);

            textViewName.setText(product.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    tietProductName.setText(product.getName());

                    // ratingBar.setRating(hero.getRating());

                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(addproduct.this);

                    builder.setTitle("Delete " + product.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteProduct(product.getId());
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

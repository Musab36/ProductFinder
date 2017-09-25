package com.salajim.musab.productfinder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.salajim.musab.productfinder.Constants;
import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.adapters.ProductListAdapter;
import com.salajim.musab.productfinder.models.Product;
import com.salajim.musab.productfinder.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProductListActivity extends AppCompatActivity {
    public static final String TAG = ProductListActivity.class.getSimpleName();
    @Bind(R.id.resultsTextView) TextView mResultsTextView;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentProduct;

    public ArrayList<Product> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String product = intent.getStringExtra("product");
        //mResultsTextView.setText("Results for: " + product);

        getProducts(product);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentProduct = mSharedPreferences.getString(Constants.PREFERENCES_PRODUCT_KEY, null);

        if(mRecentProduct != null) {
            getProducts(mRecentProduct);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Callback method for desplaying response data
    public void getProducts(String products) {
        final ProductService productService = new ProductService();
        ProductService.findProducts(products, new Callback() {// new Callback represents the second argument in findRestaurants() method
            // This method is run when our request fails
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();// prints details of the error to the output console
            }

            // This method is run if our request is successful
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // We are triggering .processResults() and collect its return value
                mProducts = productService.processResults(response);

                // We are switching to UI thread, Runnable helps sharing code between threads
                ProductListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {//Runnable method which contains the code for the UI thread
                        mAdapter = new ProductListAdapter(getApplicationContext(), mProducts);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });
            }
        });
    }

    private void addToSharedPreferences(String product) {
        mEditor.putString(Constants.PREFERENCES_PRODUCT_KEY, product).apply();
    }
}


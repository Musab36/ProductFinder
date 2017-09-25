package com.salajim.musab.productfinder.services;

import android.util.Log;

import com.salajim.musab.productfinder.Constants;
import com.salajim.musab.productfinder.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductService {
    public static void findProducts(String items, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.PRODUCTS_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETER, items);
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETER_FORMAT, "json");
        urlBuilder.addQueryParameter(Constants.QUERY_APIKEY_HOLDER, Constants.ApiKey);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.i("myUrl", url);

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

        public ArrayList<Product> processResults(Response response) {
            ArrayList<Product> products = new ArrayList<>();

            try {
                String jsonData = response.body().string();
                if(response.isSuccessful()) {
                    JSONObject productsJSON = new JSONObject(jsonData);
                    JSONArray itemsJSON = productsJSON.getJSONArray("items");
                    for(int i = 0; i < itemsJSON.length(); i ++) {
                        JSONObject productJSON = itemsJSON.getJSONObject(i);
                        String name = productJSON.getString("name");
                        String largeImage = productJSON.getString("largeImage");
                        String salePrice = productJSON.getString("salePrice");
                        String categoryPath = productJSON.getString("categoryPath");
                        String customerRating = productJSON.getString("customerRating");
                        String addToCartUrl = productJSON.getString("addToCartUrl");
                        String productUrl = productJSON.getString("productUrl");

                        Product product = new Product(name, largeImage, salePrice, categoryPath, customerRating, addToCartUrl, productUrl);
                        products.add(product);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return products;
        }
    }


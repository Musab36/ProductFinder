package com.salajim.musab.productfinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        Log.i("myUrl",url);

        Call call = client.newCall(request);
        call.enqueue(callback);

        public ArrayList<Product> processResults(Response response) {
            ArrayList<Product> products = new ArrayList<>();

            try {
                String jsonObject = response.body().string();
                if(response.isSuccessful) {
                    JSONObject productsJSON = new JSONObject(jsonData);
                    JSONArray itemsJSON = productsJSON.getJSONArray("items");
                    for(int i = 0; i < itemsJSON.length(); i ++) {
                        
                    }
                }
            }
        }
    }
}


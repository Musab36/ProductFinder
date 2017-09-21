package com.salajim.musab.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {
    public static final String TAG = ProductListActivity.class.getSimpleName();
    @Bind(R.id.resultsTextView) TextView mResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String product = intent.getStringExtra("product");
        mResultsTextView.setText("Results for: " + product);
    }
}

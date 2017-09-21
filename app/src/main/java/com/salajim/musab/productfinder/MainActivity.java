package com.salajim.musab.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.findProductsbtn) Button mFindProductsBtn;
    @Bind(R.id.searchEditText) EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindProductsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindProductsBtn) {
            String product = mSearchEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        }
    }
}

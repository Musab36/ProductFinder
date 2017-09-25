package com.salajim.musab.productfinder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salajim.musab.productfinder.Constants;
import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.models.Product;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedProductListActivity extends AppCompatActivity {
    private DatabaseReference mProductReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        mProductReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PRODUCTS);

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Product, FirebaseProductViewHolder>
                (Product.class, R.layout.product_list_item, FirebaseProductViewHolder.class, mProductReference) {

            @Override
            protected void populateViewHolder(FirebaseProductViewHolder viewHolder, Product model, int position) {
                viewHolder.bindProduct(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}

package com.salajim.musab.productfinder.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salajim.musab.productfinder.Constants;
import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.models.Product;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.salajim.musab.productfinder.R.id.imageView;

public class FirebaseProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseProductViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindProduct(Product product) {
        TextView productNameTextView = (TextView) mView.findViewById(R.id.productNameTextView);
        ImageView productImageView = (ImageView) mView.findViewById(imageView);
        TextView priceTextView = (TextView) mView.findViewById(R.id.priceTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);

        Picasso.with(mContext)
                .load(product.getLargeImage())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(productImageView);

        productNameTextView.setText(product.getName());
        priceTextView.setText("Price: " + "$" + product.getSalePrice());
        categoryTextView.setText("Category: " + product.getCategoryPath());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Product> products = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PRODUCTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    products.add(snapshot.getValue(Product.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("products", Parcels.wrap(products));

                mContext.startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

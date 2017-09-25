package com.salajim.musab.productfinder.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salajim.musab.productfinder.Constants;
import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.models.Product;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @Bind(R.id.productImageView) ImageView mProductImageView;
    @Bind(R.id.productName) TextView mProductName;
    @Bind(R.id.productPriceTextView) TextView mProductPriceTextView;
    @Bind(R.id.customerRatingTextView) TextView mCustomerRatingTextView;
    @Bind(R.id.productCategoryTextView) TextView mProductCategoryTextView;
    @Bind(R.id.addToCartTextView) TextView mAddToCartTextView;
    @Bind(R.id.productUrl) TextView mProductUrl;
    @Bind(R.id.saveItemBtn) Button mSaveItem;

    private Product mProduct;

    // newInstance(), is used instead of a constructor and returns a new instance of ProductDetailFragment
    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("product", Parcels.wrap(product));
        productDetailFragment.setArguments(args);
        return productDetailFragment;
    }
    //onCreate(), is called when the fragment is created. Here, we unwrap our product object from the arguments we added in the newInstance()
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = Parcels.unwrap(getArguments().getParcelable("product"));
    }

    // onCreateView(), this product object is then used to set our ImageView and TextViews
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mProduct.getLargeImage())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mProductImageView);

        mProductName.setText(mProduct.getName());
        mProductPriceTextView.setText("Price: " + "$" + mProduct.getSalePrice());
        mCustomerRatingTextView.setText("Rating: " + mProduct.getCustomerRating() + "/5");
        mProductCategoryTextView.setText("Category: " + mProduct.getCategoryPath());

        mAddToCartTextView.setOnClickListener(this);
        mProductUrl.setOnClickListener(this);
        mSaveItem.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        if(v == mAddToCartTextView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mProduct.getAddToCartUrl()));
            startActivity(webIntent);
        }
        if(v == mProductUrl) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mProduct.getProductUrl()));
            startActivity(webIntent);
        }
        if(v == mSaveItem) {
            DatabaseReference productRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PRODUCTS);
            productRef.push().setValue(mProduct);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}

package com.salajim.musab.productfinder.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.adapters.ProductPagerAdapter;
import com.salajim.musab.productfinder.models.Product;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private ProductPagerAdapter adapterViewPager;
    ArrayList<Product> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        //We pull out our ArrayList<Product> Parcelable using the unwrap() method
        mProducts = Parcels.unwrap(getIntent().getParcelableExtra("products"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new ProductPagerAdapter(getSupportFragmentManager(), mProducts);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}

package com.salajim.musab.productfinder.adapters;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.salajim.musab.productfinder.models.Product;
import com.salajim.musab.productfinder.ui.ProductDetailFragment;

import java.util.ArrayList;

public class ProductPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Product> mProducts;

    //A constructor where we set the required FragmentManager and array list of products we will be swiping through.
    public ProductPagerAdapter(FragmentManager fm, ArrayList<Product> products) {
        super(fm);
        mProducts = products;
    }

    //Returns an instance of the ProductDetailFragment for the product in the position provided as an argument
    @Override
    public Fragment getItem(int position) {
        return ProductDetailFragment.newInstance(mProducts.get(position));
    }

    //Determines how many products are in our Array List. This lets our adapter know how many fragments it must create.
    @Override
    public int getCount() {
        return mProducts.size();
    }

    //Updates the title that appears in the scrolling tabs at the top of the screen
    @Override
    public CharSequence getPageTitle(int position) {
        return mProducts.get(position).getName();
    }
}

package com.salajim.musab.productfinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salajim.musab.productfinder.ui.ProductDetailActivity;
import com.salajim.musab.productfinder.R;
import com.salajim.musab.productfinder.models.Product;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductListAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        mProducts = products;
    }

    // onCreateViewHolder method which inflates the layout, and creates the ViewHolder object required from the adapter
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    // onBindViewHolder method which updates the contents of the ItemView to reflect the product in the given position
    @Override
    public void onBindViewHolder(ProductListAdapter.ProductViewHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
    }

    // getItemCount method which sets the number of items the adapter will display
    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    // viewHolder class as an inner/nested class
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.productNameTextView) TextView mProductNameTextView;
        @Bind(R.id.imageView) ImageView mImageView;
        @Bind(R.id.priceTextView) TextView mPriceTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;

        private Context mContext;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();//Retrieves the position of the specific list item clicked
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("products", Parcels.wrap(mProducts));
            mContext.startActivity(intent);
        }

        public void bindProduct(Product product) {
            Picasso.with(mContext)
                    .load(product.getLargeImage())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mImageView);

            mProductNameTextView.setText(product.getName());
            mPriceTextView.setText("Price: " + "$" + product.getSalePrice());
            mCategoryTextView.setText("Category: " + product.getCategoryPath());
        }
    }
}

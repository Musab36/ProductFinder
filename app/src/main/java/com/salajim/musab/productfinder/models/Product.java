package com.salajim.musab.productfinder.models;

import org.parceler.Parcel;

@Parcel
public class Product {
    String name;
    String largeImage;
    String salePrice;
    String categoryPath;
    String customerRating;
    String addToCartUrl;
    String productUrl;

    public Product() {}

    public Product(String name, String largeImage, String salePrice, String categoryPath, String customerRating, String addToCartUrl, String productUrl) {
        this.name = name;
        this.largeImage = largeImage;
        this.salePrice = salePrice;
        this.categoryPath = categoryPath;
        this.customerRating = customerRating;
        this.addToCartUrl = addToCartUrl;
        this.productUrl = productUrl;
    }

    public String getName() {
        return name;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public String getAddToCartUrl() {
        return addToCartUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }
}

package com.salajim.musab.productfinder;

import org.parceler.Parcel;

@Parcel
public class Product {
    String name;
    String largeImage;
    String salePrice;
    String categoryPath;

    public Product() {}

    public Product(String name, String largeImage, String salePrice, String categoryPath) {
        this.name = name;
        this.largeImage = largeImage;
        this.salePrice = salePrice;
        this.categoryPath = categoryPath;
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
}

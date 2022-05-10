package com.example.miraclemakers.UIModels;

import android.graphics.Bitmap;

public class CustomerProductModel {



    Bitmap product_pic;
    String productName;
    String price;
    int available;
    String sellerId;
    String productId;

    public CustomerProductModel(Bitmap product_pic, String productName, String price, int available, String sellerId, String productId) {
        this.product_pic = product_pic;
        this.productName = productName;
        this.price = price;
        this.available = available;
        this.sellerId = sellerId;
        this.productId = productId;
    }

    public Bitmap getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(Bitmap product_pic) {
        this.product_pic = product_pic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

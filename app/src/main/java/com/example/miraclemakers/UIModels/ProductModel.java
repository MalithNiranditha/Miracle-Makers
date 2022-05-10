package com.example.miraclemakers.UIModels;

import android.graphics.Bitmap;

public class ProductModel {

    Bitmap product_pic;
    String productName;
    String description;
     String id;

    public String getId() {
        return id;
    }

    public ProductModel(Bitmap product_pic, String productName, String description, String id) {
        this.product_pic = product_pic;
        this.productName = productName;
        this.description = description;
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

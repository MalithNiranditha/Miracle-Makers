package com.example.miraclemakers.Api_Models;

public class ApiResponseProduct {

    int product_id;
    String product_name;
    String product_category;
    String product_description;
    int product_price;
    int product_delivery;
    String encoded_image;
    int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ApiResponseProduct(int product_id, String product_name, String product_category, String product_description, int product_price, int product_delivery, String encoded_image) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_delivery = product_delivery;
        this.encoded_image = encoded_image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_delivery() {
        return product_delivery;
    }

    public void setProduct_delivery(int product_delivery) {
        this.product_delivery = product_delivery;
    }

    public String getEncoded_image() {
        return encoded_image;
    }

    public void setEncoded_image(String encoded_image) {
        this.encoded_image = encoded_image;
    }
}

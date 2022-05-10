package com.example.miraclemakers.Api_Models;

public class ApiCart {

    int cart_id;
    String product_name;
    int price;
    int qty;
    int total;

    public ApiCart(int cart_id, String product_name, int price, int qty, int total) {
        this.cart_id = cart_id;
        this.product_name = product_name;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.example.miraclemakers.Api_Models;

public class ApiProfile {
    String status;
    int result;
    String date;
    String name;
    String email;
    String phone;
    String address;
    String encoded_image;

    public ApiProfile(String status, int result, String date, String name, String email, String phone, String address, String encoded_image) {
        this.status = status;
        this.result = result;
        this.date = date;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.encoded_image = encoded_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEncoded_image() {
        return encoded_image;
    }

    public void setEncoded_image(String encoded_image) {
        this.encoded_image = encoded_image;
    }
}

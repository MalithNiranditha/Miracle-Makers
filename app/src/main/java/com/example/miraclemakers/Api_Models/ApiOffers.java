package com.example.miraclemakers.Api_Models;

public class ApiOffers {

    String encoded_image;
    String description;
    String to_date;
    String from_date;
    int offer_id;
    int user_id;


    public ApiOffers(String encoded_image, String description, String to_date, String from_date, int offer_id, int user_id) {
        this.encoded_image = encoded_image;
        this.description = description;
        this.to_date = to_date;
        this.from_date = from_date;
        this.offer_id = offer_id;
        this.user_id = user_id;
    }

    public  ApiOffers()
    {

    }

    public String getEncoded_image() {
        return encoded_image;
    }

    public void setEncoded_image(String encoded_image) {
        this.encoded_image = encoded_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

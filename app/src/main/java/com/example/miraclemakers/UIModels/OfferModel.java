package com.example.miraclemakers.UIModels;

import android.graphics.Bitmap;

public class OfferModel {

    Bitmap bitmap;
    String from_date;
    String to_date;
    String description;
    int  offer_id;

    public OfferModel(Bitmap bitmap, String from_date, String to_date, String description, int offer_id) {
        this.bitmap = bitmap;
        this.from_date = from_date;
        this.to_date = to_date;
        this.description = description;
        this.offer_id = offer_id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }
}

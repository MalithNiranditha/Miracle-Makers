package com.example.miraclemakers.Api_Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {

    private static final String BASE_URL="https://miraclemakers2022.000webhostapp.com/API/";
    private static Retrofit retrofit=null;


    public static Retrofit getApiClient()
    {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}

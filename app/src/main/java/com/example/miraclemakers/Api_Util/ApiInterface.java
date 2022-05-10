package com.example.miraclemakers.Api_Util;

import com.example.miraclemakers.Api_Models.ApiCart;
import com.example.miraclemakers.Api_Models.ApiOffers;
import com.example.miraclemakers.Api_Models.ApiProfile;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Models.ApiResponseProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponseLogin> performLogin(@Field("email") String email, @Field("password") String password, @Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponseLogin> performSignup(@Field("name") String name, @Field("email") String email, @Field("phone") int phone, @Field("password") String password, @Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("get_userid.php")
    Call<ApiResponseLogin> getuser_id(@Field("email") String email);


    @FormUrlEncoded
    @POST("create_profile.php")
    Call<ApiResponseProfile> create_profile(@Field("user_id") int userid, @Field("encoded_image") String encoded_image, @Field("address") String address);

    @FormUrlEncoded
    @POST("create_profile_seller.php")
    Call<ApiResponseProfile> create_profile_seller(@Field("user_id") int userid, @Field("encoded_image") String encoded_image, @Field("address") String address, @Field("delivery") int delivery, @Field("res_name") String res_name);

    @FormUrlEncoded
    @POST("add_product.php")
    Call<ApiResponseProfile> add_product(
            @Field("user_id") int user_id,
            @Field("category") String category,
            @Field("delivery") int delivery,
            @Field("product_name") String product_name,
            @Field("price") int price,
            @Field("encoded_image") String encoded_image,
            @Field("description") String description);


    @FormUrlEncoded
    @POST("get_all_products.php")
    Call<List<ApiResponseProduct>> get_all_products(@Field("user_id") int userid);

    @FormUrlEncoded
    @POST("delete_product.php")
    Call<ApiResponseLogin> Delete_product(@Field("product_id") int product_id);


    @FormUrlEncoded
    @POST("get_product.php")
    Call<ApiResponseProduct> get_product(@Field("product_id") int product);

    @FormUrlEncoded
    @POST("update_product.php")
    Call<ApiResponseProfile> update_product(
            @Field("product_id") int product_id,
            @Field("user_id") int user_id,
            @Field("category") String category,
            @Field("delivery") int delivery,
            @Field("product_name") String product_name,
            @Field("price") int price,
            @Field("encoded_image") String encoded_image,
            @Field("description") String description);


    @GET("get_customer_products.php")
    Call<List<ApiResponseProduct>> getall();


    @FormUrlEncoded
    @POST("add_cart.php")
    Call<ApiResponseLogin> add_cart(

            @Field("product_name") String product_name,
            @Field("price") int price,
            @Field("quantity") int quantity,
            @Field("product_id") int product_id,
            @Field("user_id") int user_id,
            @Field("seller_id") int seller_id
    );

    @FormUrlEncoded
    @POST("get_cart.php")
    Call<List<ApiCart>> get_cart(@Field("user_id") int user_id);


    @FormUrlEncoded
    @POST("delete_cart.php")
    Call<ApiResponseLogin> delete_cart(@Field("cart_id") int user_id);

    @FormUrlEncoded
    @POST("update_cart.php")
    Call<ApiResponseLogin> updateCart(@Field("cart_id") int cart_id, @Field("qty") int qty);


    @FormUrlEncoded
    @POST("save_offer.php")
    Call<ApiResponseLogin> save_offer(@Field("user_id") int user_id, @Field("encoded_image") String encoded_image, @Field("description") String description, @Field("from_date") String from_date, @Field("to_date") String to_date,@Field("filename")String filename);


    @FormUrlEncoded
    @POST("delete_offer.php")
    Call<ApiResponseLogin> deleteoffer(@Field("offer_id") int offer_id);


    @FormUrlEncoded
    @POST("update_offer.php")
    Call<ApiResponseLogin> update_offer(@Field("offer_id") int offer_id, @Field("description") String Description);


    @FormUrlEncoded
    @POST("get_offers.php")
    Call<List<ApiOffers>> get_all_offers(@Field("user_id") int user_id);


    @GET("get_all_offers.php")
    Call<List<ApiOffers>> get_offers();

    //Not ok
    @FormUrlEncoded
    @POST("get_profile.php")
    Call<ApiProfile> get_profile(@Field("user_id") int user_id);


//   @FormUrlEncoded
//   @POST("createprofile.php")
//   Call<>getProfile();


}

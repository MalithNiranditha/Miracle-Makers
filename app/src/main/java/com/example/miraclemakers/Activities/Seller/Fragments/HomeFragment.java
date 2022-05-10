package com.example.miraclemakers.Activities.Seller.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miraclemakers.Adapters.ProductAdapter;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    View view;
    int user_id;
    Context applicationContext;
    RecyclerView RV;
    ArrayList<ProductModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        RV = view.findViewById(R.id.productDashboard);
        get_data_bundle();
        get_all_products();
        return view;
    }


    private void get_all_products() {
        list = new ArrayList<>();
        Call<List<ApiResponseProduct>> call = ApiClient.getApiClient().create(ApiInterface.class).get_all_products(user_id);
        call.enqueue(new Callback<List<ApiResponseProduct>>() {
            @Override
            public void onResponse(Call<List<ApiResponseProduct>> call, Response<List<ApiResponseProduct>> response) {
                if(response.isSuccessful())
                {
                    List<ApiResponseProduct> data=response.body();
                    for (int i=0;i< data.size();i++)
                    {
                        int product_id=data.get(i).getProduct_id();
                        String product_name=data.get(i).getProduct_name();
                        String product_category=data.get(i).getProduct_category();
                        String product_description=data.get(i).getProduct_description();
                        int product_price=data.get(i).getProduct_price();
                        int product_delivery=data.get(i).getProduct_delivery();
                        String encoded_image=data.get(i).getEncoded_image();
                        byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                        list.add(new ProductModel(decodedImage,product_name,product_description,product_id+""));
                        ProductAdapter adapter=new ProductAdapter(list,getContext(),user_id);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
                        RV.setLayoutManager(layoutManager);
                        RV.setAdapter(adapter);
                    }

                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<List<ApiResponseProduct>> call, Throwable t) {

            }
        });
    }

    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }
}
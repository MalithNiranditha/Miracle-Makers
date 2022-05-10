package com.example.miraclemakers.Activities.Customer.Fragment;

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

import com.example.miraclemakers.Adapters.CustomerProductAdapter;
import com.example.miraclemakers.Adapters.ProductAdapter;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.CustomerProductModel;
import com.example.miraclemakers.UIModels.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerProductFragment extends Fragment {

    int user_id;
    Context applicationContext;
    RecyclerView RV;
    ArrayList<CustomerProductModel> list;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_customer_product, container, false);
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        RV = view.findViewById(R.id.productDashboard);
        get_data_bundle();
        get_all_products();
        System.out.println(user_id);
        return view;
    }

    private void get_all_products() {
        list = new ArrayList<>();
        Call<List<ApiResponseProduct>> call = ApiClient.getApiClient().create(ApiInterface.class).getall();
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
                        int seller_id=data.get(i).getUser_id();
                        byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                        System.out.println(product_name+product_price+""+product_delivery+seller_id+""+product_id+"");
                        list.add(new CustomerProductModel(decodedImage,product_name,product_price+"",product_delivery,seller_id+"",product_id+""));
                        CustomerProductAdapter adapter=new CustomerProductAdapter(list,getContext(),product_id,seller_id,user_id);
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
package com.example.miraclemakers.Activities.Customer.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.miraclemakers.Activities.Customer.DoneActivity;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Adapters.CartAdapter;
import com.example.miraclemakers.Adapters.CustomerProductAdapter;
import com.example.miraclemakers.Api_Models.ApiCart;
import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.Models.CartModel;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.CustomerProductModel;
import com.example.miraclemakers.UIModels.UserCartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

   View view;
    int user_id;
    Context applicationContext;
    RecyclerView RV;
    ArrayList<UserCartModel> list;
    Button btn_checkout;
int total=0;
TextView txt_total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_cart, container, false);
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        RV = view.findViewById(R.id.productDashboard);
        txt_total=view.findViewById(R.id.txt_total);
        btn_checkout=view.findViewById(R.id.btn_checkout);
        get_data_bundle();
        get_all_products();
        System.out.println(user_id);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), DoneActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
    private  void setd(int tot)
    {
        txt_total.setText(tot+"");
    }
    private void get_all_products() {
        list = new ArrayList<>();
        Call<List<ApiCart>> call = ApiClient.getApiClient().create(ApiInterface.class).get_cart(user_id);
        call.enqueue(new Callback<List<ApiCart>>() {
            @Override
            public void onResponse(Call<List<ApiCart>> call, Response<List<ApiCart>> response) {
                if(response.isSuccessful())
                {
                    List<ApiCart> data=response.body();
                    for (int i=0;i< data.size();i++)
                    {
                        System.out.println(data.get(i).getCart_id()+data.get(i).getProduct_name()+data.get(i).getPrice()+data.get(i).getQty()+data.get(i).getTotal());
                        list.add(new UserCartModel(data.get(i).getCart_id(),data.get(i).getProduct_name(),data.get(i).getPrice(),data.get(i).getQty(),data.get(i).getTotal()));
                        CartAdapter adapter=new CartAdapter(list,getContext(),user_id);
                        total=total+(data.get(i).getTotal());
                        System.out.println(total);
                        txt_total.setText("");
                        setd(total);
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
            public void onFailure(Call<List<ApiCart>> call, Throwable t) {

            }
        });

    }
    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }
}
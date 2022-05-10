package com.example.miraclemakers.Activities.Customer.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.ProductModel;

import java.util.ArrayList;


public class CustomerHomeFragment extends Fragment {

  View view;
    int user_id;
    Context applicationContext;
    RecyclerView RV;
    ArrayList<ProductModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.fragment_customer_home, container, false);
//        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
//        RV = view.findViewById(R.id.productDashboard);
//        get_data_bundle();
//        get_all_products();
       return  view;
    }

    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }
}
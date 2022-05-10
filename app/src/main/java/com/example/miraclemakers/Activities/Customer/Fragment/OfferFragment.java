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

import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Adapters.OfferAdapter;
import com.example.miraclemakers.Adapters.SellerViewOfferAdapter;
import com.example.miraclemakers.Api_Models.ApiOffers;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.OfferModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OfferFragment extends Fragment {

   View view;
    int user_id;
    Context applicationContext;
    RecyclerView RV;
    ArrayList<OfferModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_offer, container, false);
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        RV = view.findViewById(R.id.productDashboard);
        get_data_bundle();
        get_offers();
        return view;
    }
    private void get_data_bundle()
    {
        user_id = getArguments().getInt("user_id");
    }
    private void get_offers()
    {
        list = new ArrayList<>();
        Call<List<ApiOffers>> call = ApiClient.getApiClient().create(ApiInterface.class).get_offers();
        call.enqueue(new Callback<List<ApiOffers>>() {
            @Override
            public void onResponse(Call<List<ApiOffers>> call, Response<List<ApiOffers>> response) {

                if(response.isSuccessful())
                {
                    List<ApiOffers> data=response.body();
                    for (int i=0;i< data.size();i++)
                    {
                        int offer_id=data.get(i).getOffer_id();
                        String description=data.get(i).getDescription();
                        String from_date=data.get(i).getFrom_date();
                        String to_date=data.get(i).getTo_date();
                        String encoded_image=data.get(i).getEncoded_image();
                        byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                        list.add(new OfferModel(decodedImage,from_date,to_date,description,offer_id));
                        OfferAdapter adapter=new OfferAdapter(list,getContext(),user_id);
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
            public void onFailure(Call<List<ApiOffers>> call, Throwable t) {

            }
        });

    }
}
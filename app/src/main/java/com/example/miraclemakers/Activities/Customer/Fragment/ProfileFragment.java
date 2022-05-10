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
import android.widget.TextView;

import com.example.miraclemakers.Adapters.CustomerProductAdapter;
import com.example.miraclemakers.Api_Models.ApiProfile;
import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.CustomerProductModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    View view;
    TextView txt_username,txt_email,txt_phone,txt_address,txt_date;
    CircleImageView img;

    int user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.fragment_profile, container, false);
        txt_username=view.findViewById(R.id.txt_username);
        txt_email=view.findViewById(R.id.txt_email);
        txt_phone=view.findViewById(R.id.txt_phone);
        txt_address=view.findViewById(R.id.txt_address);
        txt_date=view.findViewById(R.id.txt_registered_date);
        img=view.findViewById(R.id.img);
        get_data_bundle();
        get_profile();
       return view;
    }

    private void get_profile()
    {
        Call<ApiProfile> call = ApiClient.getApiClient().create(ApiInterface.class).get_profile(user_id);
        call.enqueue(new Callback<ApiProfile>() {
            @Override
            public void onResponse(Call<ApiProfile> call, Response<ApiProfile> response) {
               if(response.isSuccessful())
               {
                   String date=response.body().getDate();
                   String name=response.body().getName();
                   String email=response.body().getEmail();
                   String phone=response.body().getPhone();
                   String address=response.body().getAddress();
                   String encoded_image=response.body().getEncoded_image();
                   byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                   Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                  txt_username.setText(name);
                  txt_email.setText(email);
                  txt_date.setText("Registered On : "+date);
                  txt_phone.setText(phone+"");
                  txt_address.setText(address+"");
                  img.setImageBitmap(decodedImage);

               }
            }

            @Override
            public void onFailure(Call<ApiProfile> call, Throwable t) {

            }
        });
    }
    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }
}
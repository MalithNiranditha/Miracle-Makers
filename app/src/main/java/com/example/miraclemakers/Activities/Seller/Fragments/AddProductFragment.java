package com.example.miraclemakers.Activities.Seller.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.miraclemakers.Activities.Customer.LoginCustomerActivity;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseProfile;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.Models.UserModel;
import com.example.miraclemakers.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductFragment extends Fragment {


    int user_id;
    View view;
    EditText txt_foodname, txt_category, txt_description, txt_price;
    ImageView img_pic;
    Button add_image, add_product;
    Switch switch1;
    Context applicationContext;
    Bitmap bitmap = null;
    int IMG_REQUEST = 21;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_product, container, false);
        //Craete Objects
        oncreateobjects();
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        //Get Data
        get_data_bundle();
        System.out.println(user_id);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_img();
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_product();
            }
        });
        return view;
    }

    private void oncreateobjects() {
        txt_foodname = view.findViewById(R.id.txt_food_name);
        txt_category = view.findViewById(R.id.txt_category);
        txt_description = view.findViewById(R.id.txt_description);
        txt_price = view.findViewById(R.id.txt_price);
        img_pic = view.findViewById(R.id.img_pic);
        add_image = view.findViewById(R.id.btn_addImage);
        add_product = view.findViewById(R.id.btn_addproduct);
        switch1 = view.findViewById(R.id.switch1);

    }

    //Select Image
    private void select_img() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    //Select Image Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), path);
                img_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }

    private void add_product() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedProfileImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        int switchval = 0;
        if (switch1.isChecked()) {
            switchval = 1;
        } else {
            switchval = 0;
        }


        Call<ApiResponseProfile> call =
                ApiClient.getApiClient().create(ApiInterface.class).
                        add_product(
                                user_id,
                                txt_category.getText().toString(),
                                switchval,
                                txt_foodname.getText().toString(),
                                Integer.parseInt(txt_price.getText().toString()),
                                encodedProfileImage,
                                txt_description.getText().toString());
        call.enqueue(new Callback<ApiResponseProfile>() {
            @Override
            public void onResponse(Call<ApiResponseProfile> call, Response<ApiResponseProfile> response) {
                if (response.code() == 200) {
                    System.out.println(response.body().getRemaks());

                    if (response.body().getStatus_code() == 1) {

                        Toast.makeText(getActivity(), "Successfully Created Product !   ", Toast.LENGTH_LONG).show();
                        clear();
                    } else {
                        Toast.makeText(getActivity(), "Unknown Error   ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error !   ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ApiResponseProfile> call, Throwable t) {

            }
        });

    }

    private void clear()
    {
        txt_foodname.setText("");
        txt_price.setText("");
        txt_category.setText("");
        txt_description.setText("");
    }

}
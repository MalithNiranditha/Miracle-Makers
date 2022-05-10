package com.example.miraclemakers.Activities.Seller.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.miraclemakers.Activities.Customer.UserDashboardActivity;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOfferFragment extends Fragment {

    View view;
    ImageView img_pic;
    DatePicker from_date, to_date;
    EditText txt_description;
    Context applicationContext;
    Bitmap bitmap = null;
    int IMG_REQUEST = 21;
    int user_id;
    Button upload,save;
    Bundle bundle = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        get_data_bundle();
        create_bundle();
        System.out.println(user_id);
        applicationContext = ResOwnerDashboardActivity.getContextOfApplication();
        oncreate();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_img();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_offer();
                AddOfferFragment N=new AddOfferFragment();
                N.setArguments(bundle);
                replacefragment(N);
            }
        });
        return view;
    }
    private void oncreate() {
        img_pic = view.findViewById(R.id.img_pic);
        from_date = view.findViewById(R.id.from_date);
        to_date = view.findViewById(R.id.to_date);
        txt_description = view.findViewById(R.id.txt_description);
        upload=view.findViewById(R.id.btn_addImage);
        save=view.findViewById(R.id.btn_save);
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

    private void save_offer()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        String from=from_date.getDayOfMonth()+"/"+(from_date.getMonth()+1)+"/"+from_date.getYear();
        String to=to_date.getDayOfMonth()+"/"+(to_date.getMonth()+1)+"/"+to_date.getYear();
        String description=txt_description.getText().toString();
        System.out.println(from);
        System.out.println(to);
        System.out.println(description);
        System.out.println(encodedImage);
        System.out.println(user_id);
        String filename=user_id+to_date.getDayOfMonth()+"and"+from_date.getMonth()+1+"and"+from_date.getYear();
        System.out.println(filename);
        Call<ApiResponseLogin> call= ApiClient.getApiClient().create(ApiInterface.class).save_offer(user_id,encodedImage,description,from,to,filename);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {

                if(response.body().getResult()==1)
                {
                    Toast.makeText(applicationContext.getApplicationContext(), "SuccessFull", Toast.LENGTH_LONG).show();
                }
                else if(response.body().getResult()==0)
                {
                    System.out.println("No");

                }
                else
                {
                    Toast.makeText(applicationContext.getApplicationContext(), "SuccessFull", Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });


    }
    //Replace Fragmnts
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void create_bundle() {
        bundle.putInt("user_id", user_id);
    }

}


package com.example.miraclemakers.Activities.Customer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Models.ApiResponseProfile;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileActivity extends AppCompatActivity {
    private EditText txt_name, txt_phone, txt_email;
    private EditText txt_address;
    private int phone;

    private String email, name;
    Bitmap bitmap = null;
    Button btn_select_img, create_profile;
    int IMG_REQUEST = 21;
    private CircleImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        oncreate_objets();

        //Bind Data
        BindData();

        //Set Data
        setdata();


        btn_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_img();
            }
        });
        create_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getuser_id();
            }
        });
    }


    private void oncreate_objets() {

        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_address = findViewById(R.id.txt_address);
        btn_select_img = findViewById(R.id.btn_addImage);
        profile_pic = findViewById(R.id.profile_pic);
        create_profile = findViewById(R.id.btn_create);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(CreateProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateProfileActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    private void BindData() {
        phone = getIntent().getIntExtra("phone", 0);
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
    }

    private void setdata() {
        txt_phone.setText(phone+"");
        txt_name.setText(name);
        txt_email.setText(email);
        txt_email.setEnabled(false);
        txt_name.setEnabled(false);
        txt_phone.setEnabled(false);
    }

    private void getuser_id() {
        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).getuser_id(email);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {
                            int user_id;
                            user_id = response.body().getUser_id();
                            create_profile(user_id);
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect User", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Connection Problem", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });
    }

    private void create_profile(int id) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedProfileImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        System.out.println(id);
        System.out.println(encodedProfileImage);
        System.out.println(txt_address.getText().toString());
        Call<ApiResponseProfile> call = ApiClient.getApiClient().create(ApiInterface.class).create_profile(id, encodedProfileImage, txt_address.getText().toString());
        call.enqueue(new Callback<ApiResponseProfile>() {
            @Override
            public void onResponse(Call<ApiResponseProfile> call, Response<ApiResponseProfile> response) {
                if(response.code()==200)
                {
                    System.out.println(response.body().getRemaks());

                    if(response.body().getStatus_code()==1)
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginCustomerActivity.class);
                        Toast.makeText(getApplicationContext(), "Successfully Created Profile.Please Login !   ", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Unknown Error   ", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Network Error !   ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ApiResponseProfile> call, Throwable t) {

            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profile_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
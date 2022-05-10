package com.example.miraclemakers.Activities.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.Models.UserModel;
import com.example.miraclemakers.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCustomerActivity extends AppCompatActivity  {
    private Button btn_register;
    private Button btn_login;
    private EditText txt_email;
    private EditText txt_password;
    private Call<ApiResponseLogin> call;

    //    private String status;
//    private int result;
//    private int user_id;
//    private String name;
//    private String email;
//    private String password;
//    private int category_id;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        oncreate_objects();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterCustomerActivity.class);
                startActivity(intent);
            }
        });


    }

    private void oncreate_objects() {

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
    }

    private void performLogin() {

        String Email = txt_email.getText().toString();
        String Password = txt_password.getText().toString();
        int category_id=2;

        System.out.println(Email);
        System.out.println(Password);
        System.out.println(category_id);

        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).performLogin(Email, Password,category_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {
                            user = new UserModel(response.body().getUser_id(), response.body().getName(), response.body().getEmail(), response.body().getPassword(), response.body().getCategory_id());
                            System.out.println(user);
                            Intent intent=new Intent(getApplicationContext(),UserDashboardActivity.class);
                            intent.putExtra("UserModel", (Serializable) user);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Email or Password ! ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Network Error ! ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });


    }

    private void getpofile()
    {

    }


}
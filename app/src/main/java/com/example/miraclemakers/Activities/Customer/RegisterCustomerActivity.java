package com.example.miraclemakers.Activities.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCustomerActivity extends AppCompatActivity {
    private EditText txt_email, txt_name, txt_phone, txt_password;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        oncreate_object();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegister();
            }
        });
    }
    private void oncreate_object() {
        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_password = findViewById(R.id.txt_password);
        btn_next=findViewById(R.id.btn_next);
    }

    private void performRegister() {

        String name = txt_name.getText().toString();
        String email = txt_email.getText().toString();
        int phone = Integer.parseInt(txt_phone.getText().toString());
        String password = txt_password.getText().toString();
        int category_id = 2;

        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).performSignup(name, email, phone, password, category_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 0) {
                            Toast.makeText(getApplicationContext(), "Email Already Exists !!", Toast.LENGTH_LONG).show();

                        } else if (response.body().getResult() == 1) {
                            Toast.makeText(getApplicationContext(), "Successfully Registered !! ", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(), CreateProfileActivity.class);
                            System.out.println("HI");
                            intent.putExtra("email",email);
                            intent.putExtra("name",name);
                            intent.putExtra("phone",phone);
                            startActivity(intent);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });


    }
}
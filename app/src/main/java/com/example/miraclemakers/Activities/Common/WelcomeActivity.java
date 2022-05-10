package com.example.miraclemakers.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.miraclemakers.Activities.Seller.LoginSellerActivity;
import com.example.miraclemakers.Activities.Customer.LoginCustomerActivity;
import com.example.miraclemakers.R;

public class WelcomeActivity extends AppCompatActivity {
private Button btn_seller;
private Button btn_customer;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        oncreate_objects();

    btn_seller.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(), LoginSellerActivity.class);
            startActivity(intent);

        }
    });


    btn_customer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(), LoginCustomerActivity.class);
            startActivity(intent);

        }
    });
    }




    private void oncreate_objects()
    {
        btn_seller=findViewById(R.id.btn_seller);
        btn_customer=findViewById(R.id.btn_customer);
    }
}
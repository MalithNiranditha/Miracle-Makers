package com.example.miraclemakers.Activities.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.miraclemakers.Activities.Customer.Fragment.CartFragment;
import com.example.miraclemakers.Activities.Customer.Fragment.CustomerProductFragment;
import com.example.miraclemakers.Activities.Customer.Fragment.OfferFragment;
import com.example.miraclemakers.Activities.Customer.Fragment.ProfileFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.AddProductFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.HomeFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.OrdersFragment;
import com.example.miraclemakers.Models.UserModel;
import com.example.miraclemakers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

public class UserDashboardActivity extends AppCompatActivity  {
    public static Context contextOfApplication;
    BottomNavigationView navigationView;
    Bundle bundle = new Bundle();
    UserModel myObject=new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        contextOfApplication = getApplicationContext();
        myObject =  (UserModel) getIntent().getSerializableExtra("UserModel");
        create_bundle();
        navigationView=findViewById(R.id.bottom_navigation);
        Navigation();
        CustomerProductFragment F = new CustomerProductFragment();
        F.setArguments(bundle);
        replacefragment(F);

    }

    //Navigation
    private void Navigation() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_product:
                        CustomerProductFragment F = new CustomerProductFragment();
                        F.setArguments(bundle);
                        replacefragment(F);
                        break;

                    case R.id.nav_offers:
                        OfferFragment L=new OfferFragment();
                        L.setArguments(bundle);
                        replacefragment(L);
                        break;

                    case R.id.nav_profile:
                        ProfileFragment P=new ProfileFragment();
                        P.setArguments(bundle);
                        replacefragment(P);
                        break;

                    case R.id.nav_cart:
                        CartFragment N=new CartFragment();
                        N.setArguments(bundle);
                        replacefragment(N);
                        break;


                }
                return true;
            }
        });
    }
    //Child Fragment Helper
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    //Replace Fragmnts
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void create_bundle()
    {
        bundle.putInt("user_id",myObject.getUser_id());
    }
}
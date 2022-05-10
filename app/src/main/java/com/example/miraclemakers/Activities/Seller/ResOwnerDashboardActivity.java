package com.example.miraclemakers.Activities.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.miraclemakers.Activities.Seller.Fragments.AddOfferFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.AddProductFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.HomeFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.OrdersFragment;
import com.example.miraclemakers.Activities.Seller.Fragments.ViewOffersFragment;
import com.example.miraclemakers.Models.UserModel;
import com.example.miraclemakers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

public class ResOwnerDashboardActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    BottomNavigationView navigationView;
    Bundle bundle = new Bundle();
    UserModel myObject=new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_owner_dashboard);
        contextOfApplication = getApplicationContext();
         myObject =  (UserModel) getIntent().getSerializableExtra("UserModel");
        System.out.println(myObject.getCategory_id());
        create_bundle();
        navigationView=findViewById(R.id.bottom_navigation);
        Navigation();
        HomeFragment A=new HomeFragment();
        A.setArguments(bundle);
        replacefragment(A);
    }
    //Navigation
    private void Navigation() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeFragment A=new HomeFragment();
                        A.setArguments(bundle);
                        replacefragment(A);
                        break;

                    case R.id.nav_addproduct:
                        AddProductFragment F=new AddProductFragment();
                        F.setArguments(bundle);
                        replacefragment(F);
                        break;



                    case R.id.nav_viewoffers:
                        ViewOffersFragment V=new ViewOffersFragment();
                        V.setArguments(bundle);
                        replacefragment(V);
                        break;

                    case  R.id.nav_addoffer:
                        AddOfferFragment R=new AddOfferFragment();
                        R.setArguments(bundle);
                        replacefragment(R);
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
        bundle.putInt("user_id",myObject.getUser_id() );
    }
}
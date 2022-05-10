package com.example.miraclemakers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miraclemakers.Activities.Seller.Fragments.HomeFragment;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Activities.Seller.SellerEditProductActivity;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {

    ArrayList<ProductModel> list;
    Context context;
    int Product_id;
    int user_id;
    Bundle bundle = new Bundle();

    public ProductAdapter(ArrayList<ProductModel> list, Context context, int user_id) {
        this.list = list;
        this.context = context;
        this.user_id=user_id;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_design, parent, false);
        return new viewHolder(view);
    }

    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.profile.setImageBitmap(model.getProduct_pic());
        holder.txt_id.setText(model.getId());
        holder.description.setText(model.getDescription());
        holder.productName.setText(model.getProductName());
        //Delete Button
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_id = Integer.parseInt((String) holder.txt_id.getText());
                delete_product();
            }
        });

        //Edit Button
        holder.btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product_id = Integer.parseInt((String) holder.txt_id.getText());
                Intent intent=new Intent(context, SellerEditProductActivity.class);
                intent.putExtra("product_id",Product_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView txt_id, description, productName;
        Button btn_Edit, btn_Delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.img_profile);
            txt_id = itemView.findViewById(R.id.txt_id);
            description = itemView.findViewById(R.id.txt_description);
            productName = itemView.findViewById(R.id.txt_productName);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
            btn_Edit = itemView.findViewById(R.id.btn_Edit);

        }
    }

    private void delete_product() {
        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).Delete_product(Product_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getStatus().equals("ok")) {
                            create_bundle();
                            Toast.makeText(context.getApplicationContext(), "Successfully Deleted !! ", Toast.LENGTH_LONG).show();
                            HomeFragment N = new HomeFragment();
                            N.setArguments(bundle);
                            replacefragment(N);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });
    }

    //Replace Fragmnts
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = ((ResOwnerDashboardActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
    private void create_bundle()
    {
        bundle.putInt("user_id",user_id);
    }
}

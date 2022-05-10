package com.example.miraclemakers.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miraclemakers.Activities.Customer.UserDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.CustomerProductModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class CustomerProductAdapter extends RecyclerView.Adapter<CustomerProductAdapter.viewHolder> {

    ArrayList<CustomerProductModel> list;
    Context context;
    int Product_id;
    int seller_id;
    int user_id;
    Bundle bundle = new Bundle();

    public CustomerProductAdapter(ArrayList<CustomerProductModel> list, Context context, int product_id, int seller_id, int user_id) {
        this.list = list;
        this.context = context;
        Product_id = product_id;
        this.seller_id = seller_id;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public CustomerProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_customer, parent, false);
        return new CustomerProductAdapter.viewHolder(view);
    }

    public void onBindViewHolder(@NonNull CustomerProductAdapter.viewHolder holder, int position) {
        CustomerProductModel model = list.get(position);
        String av = "";
        holder.profile.setImageBitmap(model.getProduct_pic());
        holder.txt_productName.setText(model.getProductName());
        holder.txt_price.setText(model.getPrice() + "");
        holder.txt_sellerid.setText(model.getSellerId());
        holder.txt_productid.setText(model.getProductId());
        if (model.getAvailable() == 1) {
            av = "Available";
        } else if (model.getAvailable() == 0) {
            av = "Unavailable";
        }
        holder.txt_available.setText(av);
        holder.btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(holder.txt_productName.getText().toString()+ holder.txt_price.getText().toString()+holder.txt_qty.getText().toString()+ holder.txt_productid.getText().toString()+user_id+holder.txt_sellerid.getText().toString());
                add_to_Cart(holder.txt_productName.getText().toString(), Integer.parseInt(holder.txt_price.getText().toString()), Integer.parseInt(holder.txt_qty.getText().toString()), Integer.parseInt(holder.txt_productid.getText().toString()), user_id, Integer.parseInt(holder.txt_sellerid.getText().toString()));
              holder.txt_qty.setText("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CircleImageView profile;
        TextView txt_productName, txt_price, txt_available, txt_sellerid, txt_productid;
        EditText txt_qty;
        Button btn_addtocart;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.img_profile);
            txt_productName = itemView.findViewById(R.id.txt_productName);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_available = itemView.findViewById(R.id.txt_available);
            txt_sellerid = itemView.findViewById(R.id.txt_sellerid);
            txt_productid = itemView.findViewById(R.id.txt_productid);
            txt_qty = itemView.findViewById(R.id.txt_qty);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);

        }
    }

    //Add Product to Cart
    private void add_to_Cart(String product_name, int price, int quantity, int product_id, int user_id, int seller_id) {
        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).add_cart(product_name, price, quantity, product_id, user_id, seller_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {
                            Toast.makeText(context.getApplicationContext(), "Successfully Added to the cart !!", Toast.LENGTH_LONG).show();

                        } else if (response.body().getResult() == 0) {
                            Toast.makeText(context.getApplicationContext(), "Unknown", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(context.getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(context.getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseLogin> call, Throwable t) {

            }
        });
    }


    //Replace Fragmnts
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = ((UserDashboardActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void create_bundle() {
        bundle.putInt("user_id", user_id);
    }
}

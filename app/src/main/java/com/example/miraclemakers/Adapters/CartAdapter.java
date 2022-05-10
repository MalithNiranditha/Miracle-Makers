package com.example.miraclemakers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miraclemakers.Activities.Customer.Fragment.CartFragment;
import com.example.miraclemakers.Activities.Customer.UserDashboardActivity;
import com.example.miraclemakers.Activities.Seller.Fragments.HomeFragment;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Activities.Seller.SellerEditProductActivity;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.ProductModel;
import com.example.miraclemakers.UIModels.UserCartModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    ArrayList<UserCartModel> list;
    Context context;
    int user_id;
    Bundle bundle = new Bundle();
    int cart_id;
    int count = 0;

    public CartAdapter(ArrayList<UserCartModel> list, Context context, int user_id) {
        this.list = list;
        this.context = context;
        this.user_id = user_id;
    }


    @NonNull
    @Override
    public CartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_design, parent, false);
        return new CartAdapter.viewHolder(view);
    }

    public void onBindViewHolder(@NonNull CartAdapter.viewHolder holder, int position) {
        UserCartModel model = list.get(position);
        holder.txt_cartid.setText(model.getCart_id() + "");
        holder.qty.setText("Product Quantity : " + model.getQty() + "");
        holder.price.setText("Product Price : " + model.getPrice() + "");
        holder.productName.setText("Product Name : " + model.getProduct_name());
        //Delete Button
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart_id = Integer.parseInt(holder.txt_cartid.getText().toString());
                System.out.println(cart_id);
                delete_product();

            }
        });

        //Edit Button
        holder.btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               update(Integer.parseInt(holder.txt_cartid.getText().toString()),Integer.parseInt(holder.txt_edit_id.getText().toString()));
            }
        });

        holder.btn_editqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count == 0) {
                    holder.txt_edit_id.setVisibility(View.VISIBLE);
                    count++;
                } else {
                    count++;
                }
                holder.txt_edit_id.setText(count + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txt_cartid, qty, price, productName;
        Button btn_Edit, btn_Delete, btn_editqty;
        EditText txt_edit_id;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txt_cartid = itemView.findViewById(R.id.txt_cartid);
            qty = itemView.findViewById(R.id.txt_qty);
            price = itemView.findViewById(R.id.txt_price);
            productName = itemView.findViewById(R.id.txt_productname);
            btn_Delete = itemView.findViewById(R.id.btn_remove);
            btn_Edit = itemView.findViewById(R.id.btn_edit);
            btn_editqty = itemView.findViewById(R.id.btn_addqty);
            txt_edit_id = itemView.findViewById(R.id.txt_edit_id);

        }
    }

    private void delete_product() {
        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).delete_cart(cart_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getStatus().equals("ok")) {
                            create_bundle();
                            Toast.makeText(context.getApplicationContext(), "Successfully Deleted !! ", Toast.LENGTH_LONG).show();
                            CartFragment N = new CartFragment();
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
    private void update(int cart_id,int qty)
    {
        System.out.println(cart_id+""+qty);
        Call<ApiResponseLogin> call=ApiClient.getApiClient().create(ApiInterface.class).updateCart(cart_id,qty);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {

                if(response.code()==200)
                {
                    Toast.makeText(context.getApplicationContext(), "Successfully Updated !! ", Toast.LENGTH_LONG).show();
                    create_bundle();
                    CartFragment N = new CartFragment();
                    N.setArguments(bundle);
                    replacefragment(N);
                }
                else
                {
                    Toast.makeText(context.getApplicationContext(), "IDK ", Toast.LENGTH_LONG).show();

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

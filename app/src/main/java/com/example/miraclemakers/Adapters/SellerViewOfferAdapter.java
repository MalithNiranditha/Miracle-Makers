package com.example.miraclemakers.Adapters;

import android.content.Context;
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
import com.example.miraclemakers.Activities.Seller.Fragments.ViewOffersFragment;
import com.example.miraclemakers.Activities.Seller.ResOwnerDashboardActivity;
import com.example.miraclemakers.Api_Models.ApiResponseLogin;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;
import com.example.miraclemakers.UIModels.OfferModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerViewOfferAdapter extends RecyclerView.Adapter<SellerViewOfferAdapter.viewHolder> {

    ArrayList<OfferModel> list;
    Context context;
    int user_id;
    Bundle bundle = new Bundle();
    int offer_id;

    public SellerViewOfferAdapter(ArrayList<OfferModel> list, Context context, int user_id) {
        this.list = list;
        this.context = context;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public SellerViewOfferAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.offer_design_seller, parent, false);
        return new SellerViewOfferAdapter.viewHolder(view);
    }

    public void onBindViewHolder(@NonNull SellerViewOfferAdapter.viewHolder holder, int position) {
        OfferModel model = list.get(position);
        holder.txt_offer_id.setText(model.getOffer_id() + "");
        holder.txt_to_date.setText(model.getTo_date() + "");
        holder.txt_from_date.setText(model.getFrom_date() + "");
        holder.txt_description.setText(model.getDescription());
        holder.img_pic.setImageBitmap(model.getBitmap());
        //Delete Button
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offer_id = Integer.parseInt(holder.txt_offer_id.getText().toString());
                System.out.println(offer_id);
                delete_offer();

            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        update(Integer.parseInt(holder.txt_offer_id.getText().toString()),holder.txt_description.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txt_from_date, txt_to_date, txt_offer_id;
        ImageView img_pic;
        EditText txt_description;
        Button btn_edit, btn_delete;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txt_from_date = itemView.findViewById(R.id.txt_from_date);
            txt_to_date = itemView.findViewById(R.id.txt_to_date);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_offer_id = itemView.findViewById(R.id.txt_offer_id);
            img_pic = itemView.findViewById(R.id.img_pic);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_delete=itemView.findViewById(R.id.btn_delete);

        }
    }

    private void delete_offer() {
        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).deleteoffer(offer_id);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                if (response.code() == 200) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getStatus().equals("ok")) {
                            create_bundle();
                            Toast.makeText(context.getApplicationContext(), "Successfully Deleted !! ", Toast.LENGTH_LONG).show();
                            ViewOffersFragment N = new ViewOffersFragment();
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

    private void update(int offer_id, String description) {

        Call<ApiResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).update_offer(offer_id, description);
        call.enqueue(new Callback<ApiResponseLogin>() {
            @Override
            public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {

                if (response.code() == 200) {
                    Toast.makeText(context.getApplicationContext(), "Successfully Updated !! ", Toast.LENGTH_LONG).show();
                    create_bundle();
                    ViewOffersFragment N = new ViewOffersFragment();
                    N.setArguments(bundle);
                    replacefragment(N);
                } else {
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
        FragmentManager fragmentManager = ((ResOwnerDashboardActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void create_bundle() {
        bundle.putInt("user_id", user_id);
    }
}

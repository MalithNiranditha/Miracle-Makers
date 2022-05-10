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

import com.example.miraclemakers.Activities.Customer.UserDashboardActivity;
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

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.viewHolder> {

    ArrayList<OfferModel> list;
    Context context;
    int user_id;

    public OfferAdapter(ArrayList<OfferModel> list, Context context, int user_id) {
        this.list = list;
        this.context = context;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public OfferAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.offer_design, parent, false);
        return new OfferAdapter.viewHolder(view);
    }

    public void onBindViewHolder(@NonNull OfferAdapter.viewHolder holder, int position) {
        OfferModel model = list.get(position);
        holder.txt_offer_id.setText(model.getOffer_id() + "");
        holder.txt_to_date.setText(model.getTo_date() + "");
        holder.txt_from_date.setText(model.getFrom_date() + "");
        holder.txt_description.setText(model.getDescription());
        holder.img_pic.setImageBitmap(model.getBitmap());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txt_from_date, txt_to_date, txt_offer_id;
        ImageView img_pic;
        TextView txt_description;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txt_from_date = itemView.findViewById(R.id.txt_from_date);
            txt_to_date = itemView.findViewById(R.id.txt_to_date);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_offer_id = itemView.findViewById(R.id.txt_offer_id);
            img_pic = itemView.findViewById(R.id.img_pic);


        }
    }


}
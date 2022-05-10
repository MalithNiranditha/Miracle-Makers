package com.example.miraclemakers.Activities.Seller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miraclemakers.Api_Models.ApiResponseProduct;
import com.example.miraclemakers.Api_Models.ApiResponseProfile;
import com.example.miraclemakers.Api_Util.ApiClient;
import com.example.miraclemakers.Api_Util.ApiInterface;
import com.example.miraclemakers.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerEditProductActivity extends AppCompatActivity {
    int product_id;
    int user_id;
    View view;
    EditText txt_foodname, txt_category, txt_description, txt_price;
    TextView txt_productid;
    ImageView img_pic;
    Button add_image, add_product;
    Switch switch1;
    Context applicationContext;
    Bitmap bitmap = null;
    int IMG_REQUEST = 21;
    int count=0;
    String encoded_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_product);
        product_id=getIntent().getIntExtra("product_id",0);
        oncreateobjects();
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_img();
                count++;
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_product();
            }
        });
        get_details();
    }
    private void oncreateobjects() {
        txt_foodname = findViewById(R.id.txt_food_name);
        txt_category = findViewById(R.id.txt_category);
        txt_description = findViewById(R.id.txt_description);
        txt_price = findViewById(R.id.txt_price);
        img_pic = findViewById(R.id.img_pic);
        add_image = findViewById(R.id.btn_addImage);
        add_product = findViewById(R.id.btn_addproduct);
        switch1 = findViewById(R.id.switch1);
        txt_productid=findViewById(R.id.txt_productid);

    }

    //Select Image
    private void select_img() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    //Select Image Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), path);
                img_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Get Product Details
    private void get_details()
    {
        Call<ApiResponseProduct> call= ApiClient.getApiClient().create(ApiInterface.class).get_product(product_id);
        call.enqueue(new Callback<ApiResponseProduct>() {
            @Override
            public void onResponse(Call<ApiResponseProduct> call, Response<ApiResponseProduct> response) {
                if(response.isSuccessful())
                {
                    txt_foodname.setText(response.body().getProduct_name());
                    txt_productid.setText(response.body().getProduct_id()+"");
                    txt_category.setText(response.body().getProduct_category());
                    txt_description.setText(response.body().getProduct_description());
                    txt_price.setText(response.body().getProduct_price()+"");
                    if(response.body().getProduct_delivery()==1)
                    {
                        switch1.setChecked(true);
                    }
                    else if(response.body().getProduct_delivery()==1)
                    {
                        switch1.setChecked(false);
                    }

                    encoded_image=response.body().getEncoded_image();
                    byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    img_pic.setImageBitmap(decodedImage);
                    user_id=response.body().getUser_id();
                    System.out.println(user_id);

                }
            }

            @Override
            public void onFailure(Call<ApiResponseProduct> call, Throwable t) {

            }
        });
    }
    //Update Product
    private void update_product()
    {
        String encodedProfileImage;
        if(count==0)
        {
            encodedProfileImage =encoded_image ;
        }
        else
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
            byte[] imageInByte = byteArrayOutputStream.toByteArray();
            encodedProfileImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        }

        int selectedval;
        if(switch1.isChecked())
        {
            selectedval=1;
        }
        else
        {
            selectedval=0;
        }
        Call<ApiResponseProfile> call  =ApiClient.getApiClient().create(ApiInterface.class).
        update_product(Integer.parseInt(txt_productid.getText().toString()),user_id,
                txt_category.getText().toString(),selectedval,txt_foodname.getText().toString(),Integer.parseInt(txt_price.getText().toString()),encodedProfileImage,txt_description.getText().toString());
        call.enqueue(new Callback<ApiResponseProfile>() {
            @Override
            public void onResponse(Call<ApiResponseProfile> call, Response<ApiResponseProfile> response) {
                finish();
                if(response.isSuccessful())
                {

                    if(response.body().getStatus_code()==1)
                    {
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                        if(response.body().getRemaks().equals("ok"))
                        {

                            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponseProfile> call, Throwable t) {

            }
        });
    }

}
package com.example.red.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.red.Models.Product;
import com.example.red.R;
import com.example.red.Utils.Constants;
import com.example.red.databinding.ActivityProductDetailBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetail_Activity extends AppCompatActivity {
   ActivityProductDetailBinding binding;
   Product currentProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        Double price = getIntent().getDoubleExtra("price",0);

        Glide.with(this)
                .load(image)
                .into(binding.Productimg);
        binding.prPrice.setText("INR "+price);

        getProductDetails(id);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // it will enable the back btn in you action bar


        Cart cart = TinyCartHelper.getCart();
        binding.AddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1); // we have seted the default quantity to 1;
                binding.AddToCartBtn.setEnabled(false);
                binding.AddToCartBtn.setText("Added To Cart..");
//                Toast.makeText(ProductDetail_Activity.this,"Product Added..",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override

    public boolean onSupportNavigateUp() {
        finish(); // so whenever the we click on the back btn finish this activity;

        return super.onSupportNavigateUp();
    }

    @Override // we make a menu in res and we have to show it on this page
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // when we clicked on cart btn
        if(item.getItemId()==R.id.cart){
            Intent intent = new Intent(this,CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void getProductDetails(int id){
        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_DETAILS_URL +id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                   JSONObject mainobj = new JSONObject(response);
                   if(mainobj.getString("status").equals("success")){
                       JSONObject object = mainobj.getJSONObject("product");
                       binding.productDesc.setText(
                               Html.fromHtml(object.getString("description"))
                       );

                       currentProduct = new Product(
                               object.getString("name"),
                               Constants.PRODUCTS_IMAGE_URL +    object.getString("image"),
                               object.getString("status"),
                               object.getDouble("price"),
                               object.getDouble("price_discount"),
                               object.getInt("stock"),
                               object.getInt("id")
                       );

                   }else{
                       // Do Nothing
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


    }
}
package com.example.red.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.red.Adapters.CartAdapter;
import com.example.red.Models.Product;
import com.example.red.databinding.ActivityCartBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        products = new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();
//        cart.getAllItemsWithQty() // this will return a map
        //entrySet() method in Java is used to create a set out of the same elements contained in the hash map. It basically returns a set view of the hash map or we can create a new set and store the map elements into them.
        for(Map.Entry<Item,Integer> item : cart.getAllItemsWithQty().entrySet()){

//            //To get the key and value elements, we should call the getKey() and getValue() methods.
//            The Map.Entry interface contains the getKey() and getValue() methods.
//            But, we should call the entrySet() method of Map interface to get the instance of Map.Entry.
                      Product product = (Product) item.getKey();
                      int quantity = item.getValue();
                      product.setQuantity(quantity);

                      products.add(product);

        }
        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void OnQuantityChanged() {
                binding.subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));
            }
        });

//        binding.cartlist.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartlist.addItemDecoration(itemDecoration);// this divider item decoration is ex you have 2 item in your recycler view this divider item decoration gives a bottom line b/w the twp item.....
        binding.cartlist.setLayoutManager(layoutManager);
        binding.cartlist.setAdapter(adapter);

        binding.subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));// %.2f means .do decimal hi rhna chahiye point k baadh

      binding.continueBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent(CartActivity.this, CheckOutActivity.class);
              startActivity(intent);
          }
      });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
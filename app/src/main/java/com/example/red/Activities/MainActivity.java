package com.example.red.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.red.Adapters.CategoryAdapter;
import com.example.red.Adapters.ProductAdapter;
import com.example.red.Models.Category;
import com.example.red.Models.Product;
import com.example.red.R;
import com.example.red.Utils.Constants;
import com.example.red.databinding.ActivityMainBinding;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this,Search_Adtivity.class);
                intent.putExtra("query",text.toString());

                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        intCategory();
        intProduct();
        intCarousel();


    }
    void intCarousel() {
//binding.carousel.addData(new CarouselItem("","Caption here"));
//        binding.carousel.addData(new CarouselItem("","Caption here"));
//        binding.carousel.addData(new CarouselItem("","Caption here"));
//        binding.carousel.setOnClickListener(addOnNewIntentListener(
        getRecentOffers();

    }


    void intCategory(){
        categories= new ArrayList<>();
        getCategory();
        categoryAdapter = new CategoryAdapter(this,categories);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);


    }

    void getRecentOffers(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                   JSONObject mainObj = new JSONObject(response);
                   if(mainObj.getString("status").equals("success")){
                       JSONArray offerArray = mainObj.getJSONArray("news_infos");
                       for(int i =0 ; i<offerArray.length() ; i++){
                           JSONObject object = offerArray.getJSONObject(i);
                           binding.carousel.addData(new CarouselItem(

                                  Constants.NEWS_IMAGE_URL+ object.getString("image"),
                                   object.getString("title")
                           ));
                       }

                   }else {
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

    void getRecentProduct(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCTS_URL + "?count=11";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                 JSONObject mainObj = new  JSONObject(response);
                 if(mainObj.getString("status").equals("success")){
                     JSONArray ProductArray = mainObj.getJSONArray("products");
                     for(int i =0 ; i<ProductArray.length() ; i++){
                         JSONObject object = ProductArray.getJSONObject(i);
                         Product product = new Product(
                                 object.getString("name"),
                              Constants.PRODUCTS_IMAGE_URL +    object.getString("image"),
                                 object.getString("status"),
                                 object.getDouble("price"),
                                 object.getDouble("price_discount"),
                                 object.getInt("stock"),
                                 object.getInt("id")
                         );
                         products.add(product);
                     }
                     productAdapter.notifyDataSetChanged();
                 }else{
                     // Do Nothing
                 }
               }catch (JSONException e) {
                   e.printStackTrace();
               }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        queue.add(request);

    }

    void getCategory(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try {
                   JSONObject mainobj = new JSONObject(response);
                   if(mainobj.getString("status").equals("success")){
                       JSONArray categoriesArray = mainobj.getJSONArray("categories");
                       for(int i =0  ; i<categoriesArray.length() ; i++){
                           JSONObject object = categoriesArray.getJSONObject(i);
                           Category category = new Category(
                                   object.getString("name"),
                                Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                   object.getString("color"),
                                   object.getString("brief"),
                                   object.getInt("id")
                           );
                           categories.add(category);
                       }
                       categoryAdapter.notifyDataSetChanged();

                   }else{
                       // Do nothing
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

    void intProduct(){
        products=new ArrayList<>();
        getRecentProduct();
        productAdapter = new ProductAdapter(this,products);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.ProductList.setLayoutManager(layoutManager);
        binding.ProductList.setAdapter(productAdapter);
    }
}
package com.example.red.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.red.Activities.Category_Activity;
import com.example.red.Models.Category;
import com.example.red.R;
import com.example.red.databinding.ItemCatogariesBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_catogaries,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
       Category category = categories.get(position);
       // here html.from html we used this becoz in the api there are some texr like tushaer<br/>kumar
       holder.binding.label.setText(Html.fromHtml(category.getName()+category.getId()));
       // when we have to use the img from the intenet we use the glide library
        Glide.with(context)
                .load(category.getIcon())
                .into(holder.binding.image);
        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Category_Activity.class);
                intent.putExtra("catId",category.getId());
                intent.putExtra("categoryName",category.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ItemCatogariesBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCatogariesBinding.bind(itemView);
        }
    }

}

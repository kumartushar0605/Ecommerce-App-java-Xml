package com.example.red.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.red.Activities.ProductDetail_Activity;
import com.example.red.Models.Product;
import com.example.red.R;
import com.example.red.databinding.ItemProductBinding;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return  new ProductViewHolder(LayoutInflater.from(context).inflate( R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
           Product product = products.get(position);
           holder.binding.label.setText(product.getName());
           holder.binding.price.setText("INR"+product.getPrice());
           Glide.with(context)
                   .load(product.getImage())
                   .into(holder.binding.image);
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, ProductDetail_Activity.class);
                   intent.putExtra("name ",product.getName());
                   intent.putExtra("image",product.getImage());
                   intent.putExtra("id",product.getId());
                   intent.putExtra("price",product.getPrice());
                   context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
   ItemProductBinding binding;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemProductBinding.bind(itemView);
        }
    }
}

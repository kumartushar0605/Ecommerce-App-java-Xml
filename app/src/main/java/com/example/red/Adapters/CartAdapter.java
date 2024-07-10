package com.example.red.Adapters;

import static androidx.core.os.LocaleListCompat.create;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.red.Models.Product;
import com.example.red.R;
import com.example.red.databinding.ItemCartBinding;
import com.example.red.databinding.QuantityDialogBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    Context context;
    ArrayList<Product> products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener{
        public void OnQuantityChanged();
    }


    public CartAdapter(Context context, ArrayList<Product> products , CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        // here we are initialzing the car means aceeseing it...
        cart= TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.imageCart);

        holder.binding.nameCart.setText(
                product.getName()
        );
        holder.binding.priceCart.setText(
                "INR"+ product.getPrice()
        );
        holder.binding.quantity.setText("qty" + product.getQuantity());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here the below code we are defining our quantitydialog layout
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

            quantityDialogBinding.productName.setText(product.getName());
            quantityDialogBinding.productStock.setText("Stock" + product.getStock());
            quantityDialogBinding.quantity.setText("qty" + product.getQuantity());

            int stock = product.getStock();


            quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = product.getQuantity();
                    quantity++;

                    if(quantity>product.getStock()){
                        Toast.makeText(context,"MAX STOCK AVAILABLE "+product.getStock(),Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        product.setQuantity(quantity);
                        quantityDialogBinding.quantity.setText("qty"+product.getQuantity());
                    }



                }
            });

            quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     int quantityy = product.getQuantity();
                     if(quantityy>1){
                         quantityy--;
                         product.setQuantity(quantityy);
                         quantityDialogBinding.quantity.setText("qty"+product.getQuantity());
                     }

                }
            });

            quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            alertDialog.dismiss();
            notifyDataSetChanged(); // saving
            cart.updateItem(product,product.getQuantity());
            cartListener.OnQuantityChanged();
                }
            });

                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ItemCartBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCartBinding.bind(itemView);
        }
    }
}

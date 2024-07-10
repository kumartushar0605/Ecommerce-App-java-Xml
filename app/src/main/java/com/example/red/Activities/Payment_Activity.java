package com.example.red.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.red.R;
import com.example.red.Utils.Constants;
import com.example.red.databinding.ActivityPaymentBinding;

public class Payment_Activity extends AppCompatActivity {
    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String orderCode = getIntent().getStringExtra("orderNumber");
        binding.webview.setMixedContentAllowed(true);
        binding.webview.loadUrl(Constants.PAYMENT_URL + orderCode);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
package com.example.toyskingdom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.toyskingdom.databinding.ActivityProductDetailBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private NumberFormat numberFormat;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        numberFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        sharedPreferences = getSharedPreferences("checkout", Context.MODE_PRIVATE);

        String productId = getIntent().getStringExtra("product_id");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String status = getIntent().getStringExtra("status");
        String category = getIntent().getStringExtra("category");
        String stock = getIntent().getStringExtra("stock");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");

        setupViews(name, price, status, category, stock, description, image);
        setupClickListeners(name, price, stock, image);
    }

    private void setupViews(String name, String price, String status,
                            String category, String stock, String description, String image) {
        binding.tvProductName.setText(name);
        binding.tvPrice.setText(formatPrice(price));
        binding.chipStatus.setText(status);
        binding.tvCategory.setText(category);
        binding.tvStock.setText(stock);
        binding.tvDescription.setText(description);

        // Set chip background based on status
        if ("Tersedia".equals(status)) {
            binding.chipStatus.setChipBackgroundColorResource(R.color.chip_bg_available);
            binding.chipStatus.setTextColor(getColor(R.color.chip_text_available));
        } else {
            binding.chipStatus.setChipBackgroundColorResource(R.color.chip_bg_unavailable);
            binding.chipStatus.setTextColor(getColor(R.color.chip_text_unavailable));
        }

        Glide.with(this)
                .load(image)
                .into(binding.ivProduct);
    }

    private void setupClickListeners(String name, String price, String stock, String image) {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnAddToCart.setOnClickListener(v -> {
            try {
                String cartItems = sharedPreferences.getString("cart_products", "");
                boolean itemExists = false;
                StringBuilder updateCart = new StringBuilder();

                if (!cartItems.isEmpty()) {
                    String[] items = cartItems.split(";");
                    for (String item : items) {
                        if (!item.isEmpty()) {
                            String[] parts = item.split("\\|");
                            if (parts[0].equals(name)) {
                                int qty = Integer.parseInt(parts[4]) + 1;
                                updateCart.append(String.format("%s|%s|%s|%s|%d;",
                                        parts[0], parts[1], parts[2], parts[3], qty));
                                itemExists = true;
                            } else {
                                updateCart.append(item).append(";");
                            }
                        }
                    }
                }

                if (!itemExists) {
                    updateCart.append(String.format("%s|%s|%s|%s|1;",
                            name, price, stock, image));
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cart_products", updateCart.toString());
                editor.apply();

                Snackbar.make(v, itemExists ?
                                "Increased quantity for " + name :
                                "Added " + name + " to cart",
                        Snackbar.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this,
                        "Failed to add " + name + " to cart",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatPrice(String price) {
        try {
            double amount = Double.parseDouble(price);
            return numberFormat.format(amount).replace("Rp", "Rp ");
        } catch (NumberFormatException e) {
            return "Rp " + price;
        }
    }
}
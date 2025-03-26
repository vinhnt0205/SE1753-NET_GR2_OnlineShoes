package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.R;

public class activity_product_detail extends AppCompatActivity {

    private Context _context;
    private ImageView ivProductImage;
    private TextView tvProductName, tvProductDescription, tvOriginalPrice, tvSalePrice;

//    public activity_product_detail(Context context) {
//        this._context = context;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        ivProductImage = findViewById(R.id.ivProductThumbnail);
        tvProductName = findViewById(R.id.tvProductTitle);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvSalePrice = findViewById(R.id.tvSalePrice);

        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("product_name");
            String productDescription = intent.getStringExtra("product_description");
            double productPrice = intent.getDoubleExtra("product_price", 0.0);
            String productImage = intent.getStringExtra("product_image");
            double salePrice = intent.getDoubleExtra("sale_price", 0.0);

            // Hiển thị dữ liệu lên UI
            tvProductName.setText(productName);
            tvProductDescription.setText(productDescription);
            tvOriginalPrice.setText(String.format("$%.2f", productPrice));
            tvSalePrice.setText(String.format("$%.2f", salePrice) );


            // Load ảnh sản phẩm với Glide
            if (productImage != null && !productImage.isEmpty()) {
                Glide.with(this)
                        .load(productImage)
//                        .placeholder(R.drawable.placeholder_image)
//                        .error(R.drawable.error_image)
                        .into(ivProductImage);
            } else {
//                ivProductImage.setImageResource(R.drawable.placeholder_image);
            }
        }

    }
}
package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1753net_gr2_onlineshoes.R;

public class activity_order_error extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_error);
        TextView tvErrorMessage = findViewById(R.id.tvErrorMessage);
        Button btnBackCart = findViewById(R.id.btnBackCart);

        // Lấy thông báo lỗi từ Intent
        String errorMessage = getIntent().getStringExtra("ERROR_MESSAGE");
        tvErrorMessage.setText("Lỗi đặt hàng: " + errorMessage);

        btnBackCart.setOnClickListener(v -> {
            Intent intent = new Intent(activity_order_error.this, activity_cart_contact.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
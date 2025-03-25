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
import com.example.se1753net_gr2_onlineshoes.ui.MainActivity;

public class activity_order_completion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_completion);
        TextView tvMessage = findViewById(R.id.tvMessage);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        // Lấy thông tin đơn hàng từ Intent
        String orderId = getIntent().getStringExtra("ORDER_ID");
        tvMessage.setText("Đơn hàng #" + orderId + " đã đặt thành công!");

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(activity_order_completion.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
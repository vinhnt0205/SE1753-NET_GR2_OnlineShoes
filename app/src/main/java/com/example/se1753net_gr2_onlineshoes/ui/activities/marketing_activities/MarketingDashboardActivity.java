package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.activity.activity_login;
import com.example.se1753net_gr2_onlineshoes.ui.activities.admin_activities.AdminUserListActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities.OrderListActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities.SaleDashboardActivity;

public class MarketingDashboardActivity extends AppCompatActivity {
    private CardView productsCardView;
    private CardView analyticsCardView;
    private CardView loginCardView;
    private CardView salesCardView;
    private CardView sliderCardView;
    private CardView orderListCardView;
    private CardView adminCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productsCardView = findViewById(R.id.productsCardView);
        analyticsCardView = findViewById(R.id.analyticsCardView);
        loginCardView = findViewById(R.id.loginCardView);
        salesCardView = findViewById(R.id.salesCardView);
        sliderCardView = findViewById(R.id.sliderCardView);
        orderListCardView = findViewById(R.id.buttonToOrderListActivity);
        adminCardView = findViewById(R.id.adminCardView);

        productsCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, MarketingProductListActivity.class);
            startActivity(intent);
        });

        analyticsCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, MarketingAnalyticActivity.class);
            startActivity(intent);
        });

        loginCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, activity_login.class);
            startActivity(intent);
        });

        salesCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, SaleDashboardActivity.class);
            startActivity(intent);
        });

        sliderCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, MarketingSliderListActivity.class);
            startActivity(intent);
        });

        orderListCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, OrderListActivity.class);
            startActivity(intent);
        });

        adminCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MarketingDashboardActivity.this, AdminUserListActivity.class);
            startActivity(intent);
        });
    }
}
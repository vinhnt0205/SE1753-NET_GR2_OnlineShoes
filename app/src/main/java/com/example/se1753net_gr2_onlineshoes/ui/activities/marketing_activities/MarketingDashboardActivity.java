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
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductStatisticsDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;
import com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities.OrderListActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities.SaleDashboardActivity;

public class MarketingDashboardActivity extends AppCompatActivity {
    private CardView productsCardView;
    private CardView analyticsCardView;
    private CardView loginCardView;
    private CardView salesCardView;
    private CardView sliderCardView;
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

        productsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingDashboardActivity.this, MarketingProductListActivity.class);
                startActivity(intent);
            }
        });

        analyticsCardView = findViewById(R.id.analyticsCardView);

        analyticsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingDashboardActivity.this, MarketingAnalyticActivity.class);
                startActivity(intent);
            }
        });

        loginCardView = findViewById(R.id.loginCardView);

        loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingDashboardActivity.this, activity_login.class);
                startActivity(intent);
            }
        });

        salesCardView = findViewById(R.id.salesCardView);

        salesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingDashboardActivity.this, SaleDashboardActivity.class);
                startActivity(intent);
            }
        });

        sliderCardView = findViewById(R.id.sliderCardView);

        sliderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingDashboardActivity.this, MarketingSliderListActivity.class);
                startActivity(intent);
            }
        });
    }
}
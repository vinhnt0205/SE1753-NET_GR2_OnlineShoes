package com.example.se1753net_gr2_onlineshoes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities.MarketingAnalyticActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities.MarketingDashboardActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities.MarketingProductListActivity;
import com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities.CustomerListActivity;

public class MainActivity extends AppCompatActivity {

    private Button BtnToMarketingDashboard;
    private Button BtnToMarketingAnalytic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BtnToMarketingDashboard = findViewById(R.id.BtnToMarketingDashboard);
        BtnToMarketingDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarketingDashboardActivity.class);
                startActivity(intent);
            }
        });

        BtnToMarketingAnalytic = findViewById(R.id.BtnToMarketingAnalytic);
        BtnToMarketingAnalytic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarketingAnalyticActivity.class);
                startActivity(intent);
            }
        });


    }

}
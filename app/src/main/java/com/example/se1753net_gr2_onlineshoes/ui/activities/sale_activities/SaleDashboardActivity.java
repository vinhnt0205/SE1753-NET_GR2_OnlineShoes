package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.SalesOverviewViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel.SalesOverviewViewModel;

public class SaleDashboardActivity extends AppCompatActivity {

    private SalesOverviewViewModel salesOverviewViewModel;
    private TextView totalOrdersTextView, totalRevenueTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_dashboard);

        // Ánh xạ View
        totalOrdersTextView = findViewById(R.id.txtTotalOrders);
        totalRevenueTextView = findViewById(R.id.txtTotalRevenue);

        // Lấy database instance
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(this);
        OrderDao orderDao = database.orderDao();


        // Khởi tạo ViewModel
        OrderRepository orderRepository = new OrderRepository(orderDao);
        salesOverviewViewModel = new ViewModelProvider(
                this, new SalesOverviewViewModelFactory(orderRepository)
        ).get(SalesOverviewViewModel.class);

        // Gọi hàm tải dữ liệu
        loadSalesData();
    }

    private void loadSalesData() {
        salesOverviewViewModel.getOrderSummary().observe(this, summary -> {
            if (summary != null) {
                long totalOrders = summary.getTotalOrders();
                double totalRevenue = summary.getTotalRevenue();

                totalOrdersTextView.setText("Total Orders: " + totalOrders);
                totalRevenueTextView.setText("Total Revenue: $" + totalRevenue);
            }
        });
    }

}

package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.data.repository.StatisticRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingOrderListAdapter;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingProductListAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingAnalyticViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingAnalyticViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarketingAnalyticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MarketingProductListAdapter adapter;
    private MarketingAnalyticViewModel marketingAnalyticViewModel;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_analytic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbarProductList);
        setSupportActionBar(toolbar); // Important! This enables the ActionBar

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back); // Custom back icon (optional)
        }

        recyclerView = findViewById(R.id.recyclerOrdersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MarketingOrderListAdapter adapter = new MarketingOrderListAdapter();
        recyclerView.setAdapter(adapter);

        // Get an instance of the repository
        OrderRepository orderRepository = new OrderRepository(getApplication());
        StatisticRepository statisticRepository = new StatisticRepository(getApplication());
        ProductRepository productRepository = new ProductRepository(getApplication());

        // Create ViewModel using the Factory
        MarketingAnalyticViewModelFactory factory = new MarketingAnalyticViewModelFactory(orderRepository, statisticRepository, productRepository);
        marketingAnalyticViewModel = new ViewModelProvider(this, factory).get(MarketingAnalyticViewModel.class);

        // Observe data changes
        marketingAnalyticViewModel.getOrders().observe(this, adapter::setOrderList);

        //Making Line chart
        lineChart = findViewById(R.id.lineChart);
        // Observe LiveData
        marketingAnalyticViewModel.getProductStatisticLiveData().observe(this, productStatistics -> {
            if (productStatistics != null && !productStatistics.isEmpty()) {
                setupLineChart(productStatistics);
            }
        });

    }

    private void setupLineChart(List<ProductStatistics> statsList) {
        List<Entry> totalSoldEntries = new ArrayList<>();
        List<Entry> avgRatingEntries = new ArrayList<>();
        List<Entry> viewsEntries = new ArrayList<>();

        for (ProductStatistics stats : statsList) {
            long time = stats.createdAt.getTime(); // X-Axis (Time in milliseconds)
            totalSoldEntries.add(new Entry(time, stats.totalSold)); // Y-Axis (Total Sold)
            avgRatingEntries.add(new Entry(time, stats.avgRating != null ? stats.avgRating.floatValue() : 0f)); // Y-Axis (Avg Rating)
            viewsEntries.add(new Entry(time, stats.views)); // Y-Axis (Views)
        }

        // Dataset for Total Sold
        LineDataSet totalSoldDataSet = new LineDataSet(totalSoldEntries, "Total Sold");
        totalSoldDataSet.setColor(Color.BLUE);
        totalSoldDataSet.setCircleColor(Color.BLUE);
        totalSoldDataSet.setLineWidth(2f);
        totalSoldDataSet.setDrawValues(true);

        // Dataset for Average Rating
        LineDataSet avgRatingDataSet = new LineDataSet(avgRatingEntries, "Avg Rating");
        avgRatingDataSet.setColor(Color.RED);
        avgRatingDataSet.setCircleColor(Color.RED);
        avgRatingDataSet.setLineWidth(2f);
        avgRatingDataSet.setDrawValues(true);

        // Dataset for Views
        LineDataSet viewsDataSet = new LineDataSet(viewsEntries, "Views");
        viewsDataSet.setColor(Color.GREEN);
        viewsDataSet.setCircleColor(Color.GREEN);
        viewsDataSet.setLineWidth(2f);
        viewsDataSet.setDrawValues(true);

        // Combine all datasets
        LineData lineData = new LineData(totalSoldDataSet, avgRatingDataSet, viewsDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh chart

        setupXAxis();
    }


    private void setupXAxis() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());

            @Override
            public String getFormattedValue(float value) {
                return sdf.format(new Date((long) value));
            }
        });
        xAxis.setGranularity(1f);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle back button action
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.analytic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            marketingAnalyticViewModel.loadProducts(products -> {
                if (products != null && !products.isEmpty()) {
                    showProductSelectionDialog(products);
                } else {
                    Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProductSelectionDialog(List<Product> products) {
        List<String> productNames = new ArrayList<>();
        List<String> productIds = new ArrayList<>();

        // Use the first product as the default selection
        if (!products.isEmpty()) {
            productNames.add(products.get(0).name);
            productIds.add(products.get(0).productId);
        }

        // Add the remaining products
        for (int i = 1; i < products.size(); i++) {
            productNames.add(products.get(i).name);
            productIds.add(products.get(i).productId);
        }

        String[] namesArray = productNames.toArray(new String[0]);
        String[] idsArray = productIds.toArray(new String[0]);

        // Automatically select the first product
        String defaultProductId = idsArray[0]; // First product ID

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Product")
                .setItems(namesArray, (dialog, which) -> {
                    String selectedProductId = idsArray[which];
                    updateChartForProduct(selectedProductId);
                })
                .show();

        // Auto-update the chart with the first product
        updateChartForProduct(defaultProductId);
    }


    private void updateChartForProduct(String productId) {
            marketingAnalyticViewModel.loadProductStatisticsByProductId(productId);
    }

}
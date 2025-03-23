package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingOrderListAdapter;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingProductListAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingOrderListViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingOrderListViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MarketingAnalyticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MarketingProductListAdapter adapter;
    private MarketingOrderListViewModel marketingOrderListViewModel;

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

        recyclerView = findViewById(R.id.recyclerOrdersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MarketingOrderListAdapter adapter = new MarketingOrderListAdapter();
        recyclerView.setAdapter(adapter);

        // Get an instance of the repository
        OrderRepository repository = new OrderRepository(getApplication());

        // Create ViewModel using the Factory
        MarketingOrderListViewModelFactory factory = new MarketingOrderListViewModelFactory(repository);
        marketingOrderListViewModel = new ViewModelProvider(this, factory).get(MarketingOrderListViewModel.class);

        // Observe data changes
        marketingOrderListViewModel.getOrders().observe(this, adapter::setOrderList);

        LineChart lineChart = findViewById(R.id.lineChart);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 10));
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 30));

        LineDataSet dataSet = new LineDataSet(entries, "Sales");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
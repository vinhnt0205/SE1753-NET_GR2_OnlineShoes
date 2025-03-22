package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.annotation.SuppressLint;
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
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingProductListAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingProductListViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingProductListViewModel;

public class MarketingProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MarketingProductListAdapter adapter;
    private MarketingProductListViewModel marketingProductListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewProductsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MarketingProductListAdapter adapter = new MarketingProductListAdapter();
        recyclerView.setAdapter(adapter);

        // Get an instance of the repository
        ProductRepository repository = new ProductRepository(getApplication());

        // Create ViewModel using the Factory
        MarketingProductListViewModelFactory factory = new MarketingProductListViewModelFactory(repository);
        marketingProductListViewModel = new ViewModelProvider(this, factory).get(MarketingProductListViewModel.class);

        // Observe data changes
        marketingProductListViewModel.getProducts().observe(this, adapter::setProducts);
    }
}
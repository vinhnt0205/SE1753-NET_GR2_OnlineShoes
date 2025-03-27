package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingProductListAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingProductListViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingProductListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketingProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MarketingProductListAdapter adapter;
    private MarketingProductListViewModel marketingProductListViewModel;

    private List<Product> productListAllItem = new ArrayList<>();

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

        Toolbar toolbar = findViewById(R.id.toolbarProductList);
        setSupportActionBar(toolbar); // Important! This enables the ActionBar

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back); // Custom back icon (optional)
        }

        recyclerView = findViewById(R.id.recyclerViewProductsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get an instance of the repository
        ProductRepository repository = new ProductRepository(getApplication());

        repository.getAllProducts()
                .subscribeOn(Schedulers.io())  // Run on background thread
                .observeOn(AndroidSchedulers.mainThread())  // Observe on UI thread
                .subscribe(products -> {
                    productListAllItem.clear();
                    productListAllItem.addAll(products);
                    adapter.setProducts(products);
                }, throwable -> Log.e("ProductFetch", "Error fetching products", throwable));

        adapter = new MarketingProductListAdapter(productListAllItem);
        recyclerView.setAdapter(adapter);

        // Create ViewModel using the Factory
        MarketingProductListViewModelFactory factory = new MarketingProductListViewModelFactory(repository);
        marketingProductListViewModel = new ViewModelProvider(this, factory).get(MarketingProductListViewModel.class);

        // Observe data changes
        marketingProductListViewModel.getProducts().observe(this, adapter::setProducts);
        marketingProductListViewModel.getProductFirstImagesLiveData().observe(this, adapter::setProductImages);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("ToolbarMenu", "Menu is being inflated");
        getMenuInflater().inflate(R.menu.product_list_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        if (item == null) {
            Log.d("ToolbarMenu", "Search item is null");
        }
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle back button action
        return true;
    }
}
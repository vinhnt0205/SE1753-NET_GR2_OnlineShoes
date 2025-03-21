package com.example.se1753net_gr2_onlineshoes;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.adapter.MarketingProductListAdapter;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.ProductViewModel;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.ProductViewModelFactory;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    private RecyclerView recyclerView;
    private MarketingProductListAdapter adapter;


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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductDao productDao = ShoeShopDatabase.getInstance(this).productDao();
        ProductRepository repository = new ProductRepository(productDao);
        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(repository)).get(ProductViewModel.class);

        adapter = new MarketingProductListAdapter();
        recyclerView.setAdapter(adapter);

        productViewModel.getProducts().observe(this, products -> {
            adapter.setProducts(products);
        });
    }

}
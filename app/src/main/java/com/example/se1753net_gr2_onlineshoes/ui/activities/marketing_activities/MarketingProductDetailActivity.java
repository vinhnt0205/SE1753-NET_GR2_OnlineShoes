package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.repository.ProductRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingProductDetailViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingProductDetailViewModel;

public class MarketingProductDetailActivity extends AppCompatActivity {
    private MarketingProductDetailViewModel viewModel;
    private EditText editTextName, editTextDescription, editTextPrice, editTextStatus;
    private ImageView imageViewProduct;
    private Button buttonSave;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }

        imageViewProduct = findViewById(R.id.imageViewProduct);
        editTextName = findViewById(R.id.editTextProductName);
        editTextDescription = findViewById(R.id.editTextProductDescription);
        editTextPrice = findViewById(R.id.editTextProductPrice);
        editTextStatus = new EditText(this); // Add status field
        buttonSave = findViewById(R.id.buttonSaveProduct);

        // Get Product ID from Intent
        productId = getIntent().getStringExtra("product_id");
        if (productId == null) {
            Toast.makeText(this, "Error: No product ID provided!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set up ViewModel
        ProductRepository repository = new ProductRepository(getApplication());
        MarketingProductDetailViewModelFactory factory = new MarketingProductDetailViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MarketingProductDetailViewModel.class);

        // Fetch product details
        viewModel.getProductById(productId).observe(this, product -> {
            if (product != null) {
                editTextName.setText(product.name);
                editTextDescription.setText(product.description);
                editTextPrice.setText(String.valueOf(product.price));
                editTextStatus.setText(product.status);
            }
        });

        // Fetch product image
        viewModel.getProductImage(productId)
                .subscribe(productImage -> {
                    Glide.with(this).load(productImage.imageUrl).into(imageViewProduct);
                }, throwable -> Log.e("ProductImage", "Error fetching image", throwable));

        // Save button click listener
        buttonSave.setOnClickListener(v -> saveProductChanges());
    }
    private void saveProductChanges() {
        String newName = editTextName.getText().toString().trim();
        String newDescription = editTextDescription.getText().toString().trim();
        double newPrice = Double.parseDouble(editTextPrice.getText().toString().trim());
        String newStatus = editTextStatus.getText().toString().trim();

        viewModel.updateProduct(productId, newName, newDescription, newPrice, newStatus)
                .subscribe(() -> {
                    Toast.makeText(this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }, throwable -> {
                    Log.e("UpdateProduct", "Error updating product", throwable);
                    Toast.makeText(this, "Error updating product!", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
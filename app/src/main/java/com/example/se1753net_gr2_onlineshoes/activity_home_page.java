package com.example.se1753net_gr2_onlineshoes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.adapter.CategoryAdapter;
import com.example.se1753net_gr2_onlineshoes.data.adapter.ProductAdapter;
import com.example.se1753net_gr2_onlineshoes.data.adapter.SliderAdapter;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Category;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.util.Log;

import java.util.logging.LogRecord;

public class activity_home_page extends AppCompatActivity {


    private ViewPager2 sliderViewPager;
    private RecyclerView categoryRecyclerView, productRecyclerView, latestPostsRecyclerView;
//    private Handler sliderHandler = new Handler();
    private List<Slider> sliderList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting activity_home_page");
        setContentView(R.layout.activity_home_page);

     sliderViewPager = findViewById(R.id.sliderView);
        categoryRecyclerView = findViewById(R.id.recyclerViewCategory);
        productRecyclerView = findViewById(R.id.recyclerViewPopular);
//        latestPostsRecyclerView = findViewById(R.id.latestPostsRecyclerView);

        setupSlider();
        setupCategoryFilter();
        setupProductList();
//        setupSidebar();
    }

    private void setupSlider() {
        try {
        sliderList = getActiveSliders();
        Log.d(sliderList.toString(), ">>>>> debug slider");

        if(sliderList == null || sliderList.isEmpty()) {
            return;
        }
        SliderAdapter adapter = new SliderAdapter(this, sliderList);
        sliderViewPager.setAdapter(adapter);



            sliderViewPager = findViewById(R.id.sliderView);
            categoryRecyclerView = findViewById(R.id.recyclerViewCategory);
            productRecyclerView = findViewById(R.id.recyclerViewPopular);
            // latestPostsRecyclerView = findViewById(R.id.latestPostsRecyclerView);

            setupSlider();
            setupCategoryFilter();
            setupProductList();
            // setupSidebar();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error initializing views", e);
        }
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (sliderViewPager.getCurrentItem() < sliderList.size() - 1) {
                    sliderViewPager.setCurrentItem(sliderViewPager.getCurrentItem() + 1);
                } else {
                    sliderViewPager.setCurrentItem(0);
                }
                Log.d(TAG, "sliderRunnable: Moved to item " + sliderViewPager.getCurrentItem());
            } catch (Exception e) {
                Log.e(TAG, "sliderRunnable: Error in slider runnable", e);
            }
        }

    };

    private void setupCategoryFilter() {
        try {
            categoryList = getAllCategories();
            Log.d(TAG, "setupCategoryFilter: categoryList size = " + categoryList.size());
            CategoryAdapter adapter = new CategoryAdapter(this, categoryList, this::filterProductsByCategory);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            categoryRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "setupCategoryFilter: Error setting up categories", e);
        }
    }

    private void setupProductList() {
        try {
            productList = getActiveProducts();
            Log.d(TAG, "setupProductList: productList size = " + productList.size());
            ProductAdapter adapter = new ProductAdapter(this, productList);
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            productRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "setupProductList: Error setting up products", e);
        }
    }

//    private void setupSidebar() {
//        List<Post> latestPosts = getLatestPosts();
//        PostAdapter postAdapter = new PostAdapter(this, latestPosts);
//        latestPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        latestPostsRecyclerView.setAdapter(postAdapter);
//    }

    private void filterProductsByCategory(String categoryId) {
        try {
            List<Product> filteredProducts = new ArrayList<>();
            /*for (Product product : productList) {
                if (product.categoryId != null && product.categoryId.equals(categoryId)) {
                    filteredProducts.add(product);
                }
            }*/
            Log.d(TAG, "filterProductsByCategory: filteredProducts size = " + filteredProducts.size());
            ProductAdapter adapter = new ProductAdapter(this, filteredProducts);
            productRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "filterProductsByCategory: Error filtering products", e);
        }
    }

    private List<Slider> getActiveSliders() {
        try {
            List<Slider> list = ShoeShopDatabase.getInstance(this).sliderDao().getActiveSliders();
            Log.d(TAG, "getActiveSliders: Retrieved " + list.size() + " active sliders");
            return list;
        } catch (Exception e) {
            Log.e(TAG, "getActiveSliders: Error fetching sliders", e);
            return new ArrayList<>();
        }
    }

    private List<Category> getAllCategories() {
        try {
            List<Category> list = ShoeShopDatabase.getInstance(this).categoryDao().getAllCategories();
            Log.d(TAG, "getAllCategories: Retrieved " + list.size() + " categories");
            return list;
        } catch (Exception e) {
            Log.e(TAG, "getAllCategories: Error fetching categories", e);
            return new ArrayList<>();
        }
    }

    private List<Product> getActiveProducts() {
        try {
            List<Product> list = ShoeShopDatabase.getInstance(this).productDao().getActiveProducts();
            Log.d(TAG, "getActiveProducts: Retrieved " + list.size() + " active products");
            return list;
        } catch (Exception e) {
            Log.e(TAG, "getActiveProducts: Error fetching products", e);
            return new ArrayList<>();
        }
    }

//    private List<Post> getLatestPosts() {
//        return DatabaseHelper.getInstance(this).postDao().getLatestPosts();
//    }
}

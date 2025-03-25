package com.example.se1753net_gr2_onlineshoes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.activity.activity_cart_detail;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.adapter.CategoryAdapter;
import com.example.se1753net_gr2_onlineshoes.data.adapter.ProductAdapter;
import com.example.se1753net_gr2_onlineshoes.data.adapter.SliderAdapter;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Category;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityHomePage extends AppCompatActivity {

    private TextView cartBadge;
    private ImageView cartIconView;
    private ImageView sliderViewPager;
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
        cartIconView =  findViewById(R.id.cart_icon);
        cartBadge = findViewById(R.id.cart_badge);
        cartIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHomePage.this, activity_cart_detail.class);
                startActivity(intent);
            }
        });
         sliderViewPager = findViewById(R.id.sliderView);
        categoryRecyclerView = findViewById(R.id.recyclerViewCategory);
        productRecyclerView = findViewById(R.id.recyclerViewPopular);
//        latestPostsRecyclerView = findViewById(R.id.latestPostsRecyclerView);

        setupSlider();
        setupCategoryFilter();
        setupProductList();
        updateCartBadge();
//        setupSidebar();
    }

    private void setupSlider() {
        try {


            getActiveSliders(sliderList -> {

                Log.d(TAG, "setUpSlider: Slider size = " + sliderList.get(0).imageUrl);
                if (sliderList == null || sliderList.isEmpty()) {
                    return;
                }

                // Thiết lập adapter sau khi nhận dữ liệu
                Glide.with(sliderViewPager.getContext()) // Đảm bảo đúng context
                        .load(sliderList.get(0).imageUrl)
//                        .placeholder(R.drawable.placeholder_image)  // Ảnh tạm thời
//                        .error(R.drawable.error_image)  // Ảnh nếu tải thất bại
                        .into(sliderViewPager);
            });

            // setupSidebar();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error initializing views", e);
        }
    }

//    private Runnable sliderRunnable = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                if (sliderViewPager.getCurrentItem() < sliderList.size() - 1) {
//                    sliderViewPager.setCurrentItem(sliderViewPager.getCurrentItem() + 1);
//                } else {
//                    sliderViewPager.setCurrentItem(0);
//                }
//                Log.d(TAG, "sliderRunnable: Moved to item " + sliderViewPager.getCurrentItem());
//            } catch (Exception e) {
//                Log.e(TAG, "sliderRunnable: Error in slider runnable", e);
//            }
//        }
//
//    };

    private void setupCategoryFilter() {
        try {


            getAllCategories(categoryList -> {
                Log.d(TAG, "setupCategoryFilter: categoryList size = " + categoryList.size());

                if (categoryList == null || categoryList.isEmpty()) {
                    return;
                }

                // Cài đặt adapter sau khi có dữ liệu
           LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                categoryRecyclerView.setLayoutManager(layoutManager);

                CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList, categoryId -> {
                    Toast.makeText(this, "Selected Category: " + categoryId, Toast.LENGTH_SHORT).show();
                });

                categoryRecyclerView.setAdapter(categoryAdapter);


            });
        } catch (Exception e) {
            Log.e(TAG, "setupCategoryFilter: Error setting up categories", e);
        }
    }

    private void setupProductList() {
        try {
            getActiveProducts(productList -> {
                Log.d(TAG, "setupProductList: productList size = " + productList.size());

                if (productList == null || productList.isEmpty()) {
                    return;
                }

                // Cài đặt adapter sau khi có dữ liệu
                ProductAdapter adapter = new ProductAdapter(this, productList);
                productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                productRecyclerView.setAdapter(adapter);
            });
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
            for (Product product : productList) {
                if (product.brandId != null && product.brandId.equals(categoryId)) {
                    filteredProducts.add(product);
                }
            }
            Log.d(TAG, "filterProductsByCategory: filteredProducts size = " + filteredProducts.size());
            ProductAdapter adapter = new ProductAdapter(this, filteredProducts);
            productRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "filterProductsByCategory: Error filtering products", e);
        }
    }



    private void getActiveSliders(Consumer<List<Slider>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Slider> list = ShoeShopDatabase.getInstance(this).sliderDao().getActiveSliders();
                Log.d(TAG, "getActiveSliders: Retrieved " + list.size() + " active sliders");
                runOnUiThread(() -> callback.accept(list)); // Gửi kết quả về UI thread
            } catch (Exception e) {
                Log.e(TAG, "getActiveSliders: Error fetching sliders", e);
                runOnUiThread(() -> callback.accept(new ArrayList<>()));
            }
        });
    }



    private void getAllCategories(Consumer<List<Category>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Category> list = ShoeShopDatabase.getInstance(this).categoryDao().getAllCategories();
                Log.d(TAG, "getAllCategories: Retrieved " + list.size() + " categories");
                runOnUiThread(() -> callback.accept(list)); // Gửi dữ liệu về UI thread
            } catch (Exception e) {
                Log.e(TAG, "getAllCategories: Error fetching categories", e);
                runOnUiThread(() -> callback.accept(new ArrayList<>()));
            }
        });
    }



    private void getActiveProducts(Consumer<List<Product>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Product> list = ShoeShopDatabase.getInstance(this).productDao().getActiveProducts();
                Log.d(TAG, "getActiveProducts: Retrieved " + list.size() + " active products");
                runOnUiThread(() -> callback.accept(list)); // Gửi dữ liệu về UI thread
            } catch (Exception e) {
                Log.e(TAG, "getActiveProducts: Error fetching products", e);
                runOnUiThread(() -> callback.accept(new ArrayList<>()));
            }
        });
    }

//    @Override
//    public void onCartUpdated() {
//        updateCartBadge();
//    }

    public void updateCartBadge() {
//        TextView badgeCount = findViewById(R.id.badge_count);
//        if (count > 0) {
//            badgeCount.setVisibility(View.VISIBLE);
//            badgeCount.setText(String.valueOf(count));
//        } else {
//            badgeCount.setVisibility(View.GONE);
//        }

        new Thread(() -> {
            int cartSize = ShoeShopDatabase.getInstance(this).cartItemDao().getCartItemCount();
            runOnUiThread(() -> {
                if (cartSize > 0) {
                    cartBadge.setText(String.valueOf(cartSize));
                    cartBadge.setVisibility(View.VISIBLE);
                } else {
                    cartBadge.setVisibility(View.GONE);
                }
            });
        }).start();
    }
//    private List<Post> getLatestPosts() {
//        return DatabaseHelper.getInstance(this).postDao().getLatestPosts();
//    }
}

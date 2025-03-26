    package com.example.se1753net_gr2_onlineshoes.activity;
    
    import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.se1753net_gr2_onlineshoes.ActivityHomePage;
    import com.example.se1753net_gr2_onlineshoes.R;
    import com.example.se1753net_gr2_onlineshoes.data.adapter.CartAdapter;
    import com.example.se1753net_gr2_onlineshoes.data.adapter.CategoryAdapter;
    import com.example.se1753net_gr2_onlineshoes.data.adapter.ProductAdapter;
    import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
    import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
    import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItemWithProduct;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.Category;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductWithImages;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.Executors;
    import java.util.function.Consumer;

    public class activity_cart_detail extends AppCompatActivity implements CartAdapter.CartItemListener {
    
        private RecyclerView rvCartItems;
        private TextView tvTotalPrice;
        private Button btnChooseMore, btnCheckout;

        private RecyclerView productRecyclerView, categoryRecyclerView;
        private CartAdapter cartAdapter;
        private CartItemDao cartItemDao;

        private ProductDao productDao;
//        private List<CartItem> cartItems;

        private List<CartItemWithProduct> cartItems;
        private double totalPrice = 0.0;
    
        private  CartItem cartItem;

        private String cartId;
    
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_cart_detail);

            productRecyclerView = findViewById(R.id.rv_latest_products);
            categoryRecyclerView = findViewById(R.id.rv_categories);
    
            rvCartItems = findViewById(R.id.rv_cart_items);
            tvTotalPrice = findViewById(R.id.tv_total_price);
            btnChooseMore = findViewById(R.id.btn_choose_more);
            btnCheckout = findViewById(R.id.btn_checkout);


    
            cartItemDao = ShoeShopDatabase.getInstance(this).cartItemDao();

            productDao = ShoeShopDatabase.getInstance(this).productDao();


            cartId = getIntent().getStringExtra("cartId");
//            Log.d(">>>>>> Debug cartId", cartId);
            if (cartId == null || cartId.isEmpty()) {
                Toast.makeText(this, "Lỗi: Không tìm thấy giỏ hàng!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity nếu không có cartId
                return;
            }


    
            loadCartItems(cartId);

//            onItemDeleted();
    
    //        btnChooseMore.setOnClickListener(v -> {
    //            Intent intent = new Intent(activity_cart_detail.this, ProductListActivity.class);
    //            startActivity(intent);
    //        });
    
            btnCheckout.setOnClickListener(v -> {
                if (cartItems.isEmpty()) {
                    Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(activity_cart_detail.this, activity_cart_contact.class);
                    startActivity(intent);
                }
            });

            btnChooseMore.setOnClickListener(v -> {
                Intent intent = new Intent(activity_cart_detail.this, ActivityHomePage.class);
                startActivity(intent);
            });
            setupCategoryFilter();

            setupProductList();


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
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    // Lấy danh sách sản phẩm ở background thread
                    List<ProductWithImages> productWithImagesList = ShoeShopDatabase.getInstance(this)
                            .productDao().getAllProductsWithImages();
                    Log.d(TAG, "setupProductList: productWithImagesList size = " + productWithImagesList.size());

                    if (productWithImagesList == null || productWithImagesList.isEmpty()) {
                        return;
                    }

                    // Chuyển sang UI thread để cập nhật RecyclerView
                    runOnUiThread(() -> {
                        ProductAdapter adapter = new ProductAdapter(this, productWithImagesList);
                        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        productRecyclerView.setAdapter(adapter);
                    });
                } catch (Exception e) {
                    Log.e(TAG, "setupProductList: Error setting up products", e);
                }
            });

        }
    
    
        private void loadCartItems(String cartId) {
            Executors.newSingleThreadExecutor().execute(() -> {
//                List<CartItem> items = cartItemDao.getCartItemsByCartId(cartId);
                List<CartItemWithProduct> items = cartItemDao.getCartItemsWithProduct(cartId);
                // Log thông tin giỏ hàng nếu cần
//                for (CartItem item : items) {
//                    Log.d("CartItem", "Sản phẩm: " + item.productId + " - Số lượng: " + item.quantity);
//                }
                for (CartItemWithProduct item : items) {
                    Log.d("CartItem", "Sản phẩm: " + item.product.productId + " - Số lượng: " + item.cartItem.quantity);
                }
                // Cập nhật UI trên Main Thread
                runOnUiThread(() -> {
                    cartItems = items;
                    cartAdapter = new CartAdapter(cartItems, this);
                    rvCartItems.setLayoutManager(new LinearLayoutManager(this));
                    rvCartItems.setAdapter(cartAdapter);
                    updateTotalPrice();
                });
            });
        }

        private void updateTotalPrice() {
            totalPrice = 0.0;
            for (CartItemWithProduct item : cartItems) {
                double price = item.product.price;
                int quantity = item.cartItem.quantity;
                totalPrice += price * quantity;
            }
            runOnUiThread(() -> tvTotalPrice.setText(String.format("$%.2f", totalPrice)));
        }

//        private double getTotalsCost() {
//            Executors.newSingleThreadExecutor().execute(() -> {
//                for (CartItem item : cartItems) {
//                    Product product = productDao.getProductById(item.getProductId());
//                    if(product != null) {
//                        // Gán giá từ Product cho CartItem
//                        item.price = product.price; // hoặc sử dụng salePrice nếu có
//                    }
//                }
//                runOnUiThread(() -> cartAdapter.notifyDataSetChanged());
//            });
//        }
//

        @Override
        public void onQuantityChanged() {
            updateTotalPrice();
        }
    
        @Override
        public void onItemDeleted(CartItem item) {
            Executors.newSingleThreadExecutor().execute(() -> {
                cartItemDao.deleteCartItem(item);
                // Sau khi xóa, load lại danh sách giỏ hàng
                loadCartItems(cartId);
            });
        }
    }
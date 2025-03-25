    package com.example.se1753net_gr2_onlineshoes.activity;
    
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    
    import com.example.se1753net_gr2_onlineshoes.R;
    import com.example.se1753net_gr2_onlineshoes.adapter.CartAdapter;
    import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
    import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
    
    import java.util.List;
    
    public class activity_cart_detail extends AppCompatActivity implements CartAdapter.CartItemListener {
    
        private RecyclerView rvCartItems;
        private TextView tvTotalPrice;
        private Button btnChooseMore, btnCheckout;
        private CartAdapter cartAdapter;
        private CartItemDao cartItemDao;
        private List<CartItem> cartItems;
        private double totalPrice = 0.0;
    
        private  CartItem cartItem;
    
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_cart_detail);
    
    
            rvCartItems = findViewById(R.id.rv_cart_items);
            tvTotalPrice = findViewById(R.id.tv_total_price);
            btnChooseMore = findViewById(R.id.btn_choose_more);
            btnCheckout = findViewById(R.id.btn_checkout);
    
            cartItemDao = ShoeShopDatabase.getInstance(this).cartItemDao();

            String cartId = getIntent().getStringExtra("cartId");
//            Log.d(">>>>>> Debug cartId", cartId);
            if (cartId == null || cartId.isEmpty()) {
                Toast.makeText(this, "Lỗi: Không tìm thấy giỏ hàng!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity nếu không có cartId
                return;
            }
    
    
            loadCartItems();
    
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
        }
    
    
        private void loadCartItems() {
            cartItems = cartItemDao.getCartItemsByCartId(cartItem.cartId);
    
            if(cartItems.isEmpty()) {
                Log.e("CartDetail", "cartItems is empty!");
            }
    
            if (cartItem != null) {
                String id = cartItem.cartId;
            } else {
                Log.e("CartDetail", "cartItem is null!");
            }
    
            for (CartItem item : cartItems) {
                Log.d("CartItem", "Sản phẩm: " + item.productId + " - Số lượng: " + item.quantity);
            }
            // Lấy danh sách sản phẩm trong giỏ
            cartAdapter = new CartAdapter(cartItems, this);
            rvCartItems.setLayoutManager(new LinearLayoutManager(this));
            rvCartItems.setAdapter(cartAdapter);
            updateTotalPrice();
        }
    
        private void updateTotalPrice() {
            totalPrice = 0.0;
            for (CartItem item : cartItems) {
                totalPrice += item.getTotalCost();
            }
            tvTotalPrice.setText("Tổng tiền: " + totalPrice + " VND");
        }
    
    
        public void onQuantityChanged() {
            updateTotalPrice();
        }
    
    //    @Override
        public void onItemDeleted(CartItem item) {
            cartItemDao.deleteCartItem(item);
            loadCartItems();
        }
    }
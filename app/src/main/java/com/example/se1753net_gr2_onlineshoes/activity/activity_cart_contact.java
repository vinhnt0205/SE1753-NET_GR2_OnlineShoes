package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.adapter.CartAdapter;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItemWithProduct;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.session.SessionManager;

import java.util.List;
import java.util.concurrent.Executors;

public class activity_cart_contact extends AppCompatActivity {
    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;
    private EditText etFullName, etEmail, etMobile, etAddress, etNotes;
    private RadioGroup rgGender;
    private Button btnChangeCart, btnSubmitOrder;
    private CartAdapter cartAdapter;
    private CartItemDao cartItemDao;
//    private List<CartItem> cartItems;

    private List<CartItemWithProduct> cartItems;
    private double totalPrice = 0.0;
    private User currentUser;
    private String cartId;

    private CartItem cartItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_contact);

        // Khởi tạo session manager
        SessionManager sessionManager = new SessionManager(this);
        String userId = sessionManager.getUserId();

        // Ánh xạ view
        rvCartItems = findViewById(R.id.rv_cart_items);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etMobile = findViewById(R.id.et_mobile);
        etAddress = findViewById(R.id.et_address);
        etNotes = findViewById(R.id.et_notes);
        rgGender = findViewById(R.id.rg_gender);
        btnChangeCart = findViewById(R.id.btn_change_cart);
        btnSubmitOrder = findViewById(R.id.btn_submit_order);

        cartItemDao = ShoeShopDatabase.getInstance(this).cartItemDao();

        cartId = getIntent().getStringExtra("cartId");
//            Log.d(">>>>>> Debug cartId", cartId);
        if (cartId == null || cartId.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy giỏ hàng!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity nếu không có cartId
            return;
        }
        loadCartItems(cartId);



        if (userId != null) {
            loadUserInfo(userId);
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập để tiếp tục!", Toast.LENGTH_SHORT).show();
        }

        btnChangeCart.setOnClickListener(v -> {
             Intent intent = new Intent(activity_cart_contact.this, activity_cart_detail.class);
             startActivity(intent);
        });

        btnSubmitOrder.setOnClickListener(v -> {
            Intent intent = new Intent(activity_cart_contact.this, activity_cart_completion.class);
        });

//        loadCartItems();


    }

    private void loadUserInfo(String userId) {
        new Thread(() -> {
            currentUser = ShoeShopDatabase.getInstance(this).userDao().getUserById(userId);
            if (currentUser != null) {
                runOnUiThread(() -> {
                    etFullName.setText(currentUser.getUsername());
                    etEmail.setText(currentUser.getEmail());
                    etMobile.setText(currentUser.getPhoneNumber());
                    etAddress.setText(currentUser.getAddress());
                    // Nếu user có giới tính thì chọn đúng radio button
//                    if (currentUser. != null) {
//                        if (currentUser.getGender().equalsIgnoreCase("Male")) {
//                            rgGender.check(R.id.rb_male);
//                        } else {
//                            rgGender.check(R.id.rb_female);
//                        }
//                    }

                });
            }
        }).start();
    }


//    private void loadCartItems() {
//        cartItems = cartItemDao.getCartItemsByCartId(cartItem.cartId);
//        cartAdapter = new CartAdapter(cartItems, null);
//        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
//        rvCartItems.setAdapter(cartAdapter);
//        updateTotalPrice();
//    }

    private void loadCartItems(String cartId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<CartItemWithProduct> items = cartItemDao.getCartItemsWithProduct(cartId);
            // Log thông tin giỏ hàng nếu cần
            for (CartItemWithProduct item : items) {
                Log.d("CartItem", "Sản phẩm: " + item.product.productId + " - Số lượng: " + item.cartItem.quantity);
            }
            // Cập nhật UI trên Main Thread
            runOnUiThread(() -> {
                cartItems = items;
                cartAdapter = new CartAdapter(cartItems, (CartAdapter.CartItemListener) this);
                rvCartItems.setLayoutManager(new LinearLayoutManager(this));
                rvCartItems.setAdapter(cartAdapter);
                updateTotalPrice();
            });
        });
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (CartItemWithProduct item : cartItems) {
            totalPrice += item.cartItem.getTotalCost();
        }
        tvTotalPrice.setText("Tổng tiền: " + totalPrice + " VND");
    }

    private void loadUserInfo() {
        currentUser = ShoeShopDatabase.getInstance(this).userDao().getUserById(currentUser.userId);
        if (currentUser != null) {
            etFullName.setText(currentUser.getUsername());
            etEmail.setText(currentUser.getEmail());
            etMobile.setText(currentUser.getPhoneNumber());
            etAddress.setText(currentUser.getAddress());
//            rgGender.check(currentUser.ge);
        }
    }

    private void submitOrder() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();
//        boolean isMale = rgGender.getCheckedRadioButtonId() == R.id.rb_male;

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin người nhận!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isOrderSuccess = processOrder(cartItems, fullName, email, mobile, address, notes);
        Intent intent = new Intent(this, isOrderSuccess ? activity_order_completion.class : activity_order_error.class);
        startActivity(intent);
    }

    private boolean processOrder(List<CartItemWithProduct> cartItems, String fullName, String email, String mobile, String address, String notes) {
        // Giả lập kiểm tra hàng tồn kho & xử lý đơn hàng
        for (CartItemWithProduct item : cartItems) {
            if (!isProductAvailable(item)) {
                return false; // Lỗi: có sản phẩm hết hàng
            }
        }
        // Xử lý đặt hàng thành công
        return true;
    }

    private boolean isProductAvailable(CartItemWithProduct item) {
        return item.cartItem.getQuantity() <= 10; // Giả lập: tồn kho tối đa 10 sản phẩm
    }
}
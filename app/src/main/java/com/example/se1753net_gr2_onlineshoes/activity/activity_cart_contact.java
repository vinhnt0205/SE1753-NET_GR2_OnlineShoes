package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import java.util.List;

public class activity_cart_contact extends AppCompatActivity {
    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;
    private EditText etFullName, etEmail, etMobile, etAddress, etNotes;
    private RadioGroup rgGender;
    private Button btnChangeCart, btnSubmitOrder;
    private CartAdapter cartAdapter;
    private CartItemDao cartItemDao;
    private List<CartItem> cartItems;
    private double totalPrice = 0.0;
    private User currentUser;

    private CartItem cartItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_contact);

    }


    private void loadCartItems() {
        cartItems = cartItemDao.getCartItemsByCartId(cartItem.cartId);
        cartAdapter = new CartAdapter(cartItems, null);
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

    private boolean processOrder(List<CartItem> cartItems, String fullName, String email, String mobile, String address, String notes) {
        // Giả lập kiểm tra hàng tồn kho & xử lý đơn hàng
        for (CartItem item : cartItems) {
            if (!isProductAvailable(item)) {
                return false; // Lỗi: có sản phẩm hết hàng
            }
        }
        // Xử lý đặt hàng thành công
        return true;
    }

    private boolean isProductAvailable(CartItem item) {
        return item.getQuantity() <= 10; // Giả lập: tồn kho tối đa 10 sản phẩm
    }
}
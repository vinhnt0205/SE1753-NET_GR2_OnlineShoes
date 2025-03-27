package com.example.se1753net_gr2_onlineshoes.ui.activities.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserRoleDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.UserRole;

public class AdminUserDetailActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail, tvPhone, tvAddress, tvRole;
    private ImageView imgAvatar;
    private UserDao userDao;
    private UserRoleDao userRoleDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_user_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolBar();
        initUI();
        userDao = ShoeShopDatabase.getInstance(this).userDao();
        userRoleDao = ShoeShopDatabase.getInstance(this).userRoleDao();
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve data from the Intent
            String userId = intent.getStringExtra("user_id");
            new Thread(() -> {
                User user = userDao.getUserById(userId);
                UserRole userRole = userRoleDao.getRoleById(user.roleId);
                runOnUiThread(() -> {
                    if (user != null) {
                        tvUsername.setText(user.username);
                        tvEmail.setText("Email: " + user.email);
                        tvPhone.setText("Phone: " + user.phoneNumber);
                        tvAddress.setText("Address: " + user.address);
                        tvRole.setText("Role: " + userRole.roleName);
                        // Load avatar if URL exists
                        String avatarUrl = user.avatarUrl;
                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            Glide.with(imgAvatar.getContext())
                                    .load(avatarUrl)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(imgAvatar);
                        } else {
                            imgAvatar.setImageResource(R.drawable.ic_launcher_background);
                        }
                    }
                });
            }).start();
        }
    }

    private void initUI() {
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);
        imgAvatar = findViewById(R.id.avatar);
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Important! This enables the ActionBar

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back); // Custom back icon (optional)
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle back button action
        return true;
    }
}
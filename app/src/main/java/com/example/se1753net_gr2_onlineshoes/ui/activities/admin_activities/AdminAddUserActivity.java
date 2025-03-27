package com.example.se1753net_gr2_onlineshoes.ui.activities.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.activity.activity_login;
import com.example.se1753net_gr2_onlineshoes.activity.activity_register;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserRoleDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.UserRole;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class AdminAddUserActivity extends AppCompatActivity {

    private EditText edtUsername, edtEmail, edtPhone, edtAddress;
    private Button btnAddUser;
    private ImageView imgUser;
    private UserDao userDao;
    private UserRoleDao userRoleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();
        toolBar();
        userDao = ShoeShopDatabase.getInstance(this).userDao();
        userRoleDao = ShoeShopDatabase.getInstance(this).userRoleDao();
        btnAddUser.setOnClickListener(v -> addUser());
    }

    void initUi() {
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        btnAddUser = findViewById(R.id.btnAddUser);
        imgUser = findViewById(R.id.imgUser);
        imgUser.setImageResource(R.drawable.avatar_default);
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

    private void addUser() {
        String fullName = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (!validateInput(fullName, email, phoneNumber, address)) {
            return;
        }

        new Thread(() -> {
            if (userDao.checkEmailExists(email) > 0) {
                runOnUiThread(() -> showToast("Email already exists!"));
                return;
            }

            String hashedPassword = BCrypt.hashpw("12345678", BCrypt.gensalt(10)); // Đảm bảo số rounds là 10
            String userId = UUID.randomUUID().toString();
            String roleId = "user"; // Tất cả user mặc định là "user"
            long currentTime = System.currentTimeMillis();

            User newUser = new User(
                    userId,
                    fullName,
                    email,
                    phoneNumber,
                    address,
                    "android.resource://" + getPackageName() + "/" + R.drawable.avatar_default,
                    roleId,
                    hashedPassword,
                    currentTime,
                    currentTime
            );

            userDao.insertUser(newUser);

            // Gán vai trò mặc định với ID riêng
            String userRoleId = UUID.randomUUID().toString(); // ID duy nhất
            UserRole userRole = new UserRole(userRoleId, "user");
            userRoleDao.insertRole(userRole);

            runOnUiThread(() -> {
                showToast("Add new user successfully");
                finish();
            });
        }).start();

    }
    private boolean validateInput(String fullName, String email, String phoneNumber, String address) {
        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            showToast("Please input full information");
            return false;
        }
        return true;
    }
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;


import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class activity_register extends AppCompatActivity {
    private EditText etFullName, etEmail, etMobile, etAddress, etPassword, etConfirmPassword;
    private RadioGroup rgGender;
    private Button btnRegister;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        userDao = ShoeShopDatabase.getInstance(this).userDao();

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void initializeUI() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validateInput(fullName, email, password, confirmPassword)) {
            if (userDao.getUserByEmail(email) != null) {
                showToast("Email đã tồn tại!");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(UUID.randomUUID().toString(), fullName, hashedPassword, email, null, "user");
            userDao.insertUser(newUser);

            showToast("Đăng ký thành công!");
            startActivity(new Intent(this, activity_login.class));
            finish();
        }
    }

    private boolean validateInput(String fullName, String email, String password, String confirmPassword) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showToast("Mật khẩu không khớp!");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

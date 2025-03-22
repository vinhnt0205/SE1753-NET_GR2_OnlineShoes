package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

public class activity_login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Khởi tạo database
        userDao = ShoeShopDatabase.getInstance(this).userDao();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(activity_login.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    User user = userDao.getUserByEmail(email);
                    if (user != null && user.passwordHash.equals(password)) { // Giả định password lưu trực tiếp (cần hash thực tế)
                        runOnUiThread(() -> {
                            Toast.makeText(activity_login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            // Chuyển hướng sang trang chính
                            startActivity(new Intent(activity_login.this, MainActivity.class));
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(activity_login.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }
        });

        tvRegister.setOnClickListener(v -> startActivity(new Intent(activity_login.this, activity_register.class)));
        tvForgotPassword.setOnClickListener(v -> startActivity(new Intent(activity_login.this, activity_forgot_password.class)));
    }
}

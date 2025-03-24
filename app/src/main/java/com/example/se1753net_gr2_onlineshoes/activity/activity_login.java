package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import org.mindrot.jbcrypt.BCrypt;

public class activity_login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private UserDao userDao;
    private SharedPreferences userPreferences; // Lưu thông tin user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        userDao = ShoeShopDatabase.getInstance(this).userDao();
        userPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity_login.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                User user = userDao.getUserByEmail(email);
                if (user != null && checkLogin(email, password)) {
                    runOnUiThread(() -> {
                        Toast.makeText(activity_login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_login.this, activity_edit_profile.class);
                        intent.putExtra("USER_ID", user.userId); // Truyền userId là String
                        startActivity(intent);
                        finish();
                    });
                }
            }).start();
        });


        tvRegister.setOnClickListener(v -> startActivity(new Intent(activity_login.this, activity_register.class)));
        tvForgotPassword.setOnClickListener(v -> startActivity(new Intent(activity_login.this, activity_forgot_password.class)));
    }

    private boolean checkLogin(String email, String password) {
        // 🔹 Kiểm tra trong SharedPreferences trước
        String storedHashedPassword = userPreferences.getString("password_" + email, "");

        if (!TextUtils.isEmpty(storedHashedPassword)) {
            Log.d("LOGIN_DEBUG", "Stored Hashed Password from SharedPreferences: " + storedHashedPassword);
            if (BCrypt.checkpw(password, storedHashedPassword)) {
                return true;
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show());
                return false;
            }
        }

        // 🔹 Nếu không có trong SharedPreferences, kiểm tra database
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            Log.d("LOGIN_DEBUG", "Stored Hashed Password from Database: " + user.passwordHash);
            if (BCrypt.checkpw(password, user.passwordHash)) {
                // Lưu mật khẩu vào SharedPreferences để lần sau không cần truy vấn DB
                userPreferences.edit().putString("password_" + email, user.passwordHash).apply();
                return true;
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show());
                return false;
            }
        } else {
            runOnUiThread(() -> Toast.makeText(this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show());
            return false;
        }
    }
}

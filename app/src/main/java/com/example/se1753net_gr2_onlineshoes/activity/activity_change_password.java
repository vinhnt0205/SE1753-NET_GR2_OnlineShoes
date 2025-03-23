package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import org.mindrot.jbcrypt.BCrypt;

public class activity_change_password extends AppCompatActivity {

    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;
    private UserDao userDao;
    private SharedPreferences userPreferences;
    private String userEmail; // Email của user đang đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        userDao = ShoeShopDatabase.getInstance(this).userDao();
        userPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Giả sử email được lấy từ SharedPreferences khi đăng nhập
        userEmail = userPreferences.getString("user_email", "");

        btnChangePassword.setOnClickListener(v -> {
            String currentPassword = etCurrentPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                if (checkCurrentPassword(userEmail, currentPassword)) {
                    updatePassword(userEmail, newPassword);
                }
            }).start();
        });
    }

    private boolean checkCurrentPassword(String email, String currentPassword) {
        // 🔹 Kiểm tra mật khẩu trong SharedPreferences trước
        String storedHashedPassword = userPreferences.getString("password_" + email, "");

        if (!TextUtils.isEmpty(storedHashedPassword)) {
            Log.d("CHANGE_PASSWORD_DEBUG", "Stored Hashed Password from SharedPreferences: " + storedHashedPassword);
            if (BCrypt.checkpw(currentPassword, storedHashedPassword)) {
                return true;
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Sai mật khẩu hiện tại!", Toast.LENGTH_SHORT).show());
                return false;
            }
        }

        // 🔹 Nếu không có trong SharedPreferences, kiểm tra database
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            Log.d("CHANGE_PASSWORD_DEBUG", "Stored Hashed Password from Database: " + user.passwordHash);
            if (BCrypt.checkpw(currentPassword, user.passwordHash)) {
                // Lưu lại vào SharedPreferences để lần sau không cần kiểm tra DB
                userPreferences.edit().putString("password_" + email, user.passwordHash).apply();
                return true;
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Sai mật khẩu hiện tại!", Toast.LENGTH_SHORT).show());
                return false;
            }
        } else {
            runOnUiThread(() -> Toast.makeText(this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show());
            return false;
        }
    }

    private void updatePassword(String email, String newPassword) {
        // Hash mật khẩu mới
        String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));

        // Cập nhật vào database
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            user.passwordHash = newHashedPassword;
            userDao.updateUser(user);

            // Cập nhật vào SharedPreferences
            userPreferences.edit().putString("password_" + email, newHashedPassword).apply();

            runOnUiThread(() -> {
                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng activity sau khi đổi mật khẩu
            });
        }
    }
}

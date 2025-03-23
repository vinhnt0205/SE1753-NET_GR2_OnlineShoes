package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;

import org.mindrot.jbcrypt.BCrypt;

public class activity_reset_password extends AppCompatActivity {

    private EditText etNewPassword, etConfirmPassword;
    private Button btnReset;
    private String resetCode;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reset_password);
//
//        etNewPassword = findViewById(R.id.etNewPassword);
//        etConfirmPassword = findViewById(R.id.etConfirmPassword);
//        btnReset = findViewById(R.id.btnReset);
//
//        // Lấy mã đặt lại mật khẩu từ link
//        Uri uri = getIntent().getData();
//        if (uri != null) {
//            resetCode = uri.getQueryParameter("code");
//            if (!TextUtils.isEmpty(resetCode)) {
//                Toast.makeText(this, "Mã xác nhận: " + resetCode, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        btnReset.setOnClickListener(v -> saveNewPassword());
//    }
//
//    private void saveNewPassword() {
//        String newPassword = etNewPassword.getText().toString().trim();
//        String confirmPassword = etConfirmPassword.getText().toString().trim();
//
//        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
//            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Lấy mã và thời gian hết hạn từ SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("ResetPrefs", MODE_PRIVATE);
//        String storedResetCode = sharedPreferences.getString("resetCode", "");
//        long expiryTime = sharedPreferences.getLong("resetExpiry", 0);
//        String resetEmail = sharedPreferences.getString("resetEmail", "");
//
//        if (TextUtils.isEmpty(storedResetCode) || !storedResetCode.equals(resetCode) || System.currentTimeMillis() > expiryTime) {
//            Toast.makeText(this, "Mã đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Cập nhật mật khẩu
//        SharedPreferences userPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//        SharedPreferences.Editor userEditor = userPreferences.edit();
//        userEditor.putString("password_" + resetEmail, newPassword); // Lưu theo email để tránh ghi đè user khác
//        userEditor.apply();
//
//        // Xóa resetCode sau khi dùng
//        sharedPreferences.edit().clear().apply();
//
//        Toast.makeText(this, "Mật khẩu đã được cập nhật!", Toast.LENGTH_LONG).show();
//        finish();
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);

    etNewPassword = findViewById(R.id.etNewPassword);
    etConfirmPassword = findViewById(R.id.etConfirmPassword);
    btnReset = findViewById(R.id.btnReset);

    // Lấy mã đặt lại mật khẩu từ link
    Uri uri = getIntent().getData();
    if (uri != null) {
        resetCode = uri.getQueryParameter("code");

        // Lấy mã reset và thời gian hết hạn từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ResetPrefs", MODE_PRIVATE);
        String storedResetCode = sharedPreferences.getString("resetCode", "");
        long expiryTime = sharedPreferences.getLong("resetExpiry", 0);

        // 👉 Log kiểm tra
        Log.d("RESET_PAGE", "Received ResetCode: " + resetCode);
        Log.d("RESET_PAGE", "Stored ResetCode: " + storedResetCode + " | Expiry: " + expiryTime);

        // Kiểm tra mã có hợp lệ không
        if (TextUtils.isEmpty(resetCode) || !resetCode.equals(storedResetCode) || System.currentTimeMillis() > expiryTime) {
            Toast.makeText(this, "Mã không hợp lệ hoặc đã hết hạn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Toast.makeText(this, "Mã hợp lệ, hãy đặt lại mật khẩu!", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Không tìm thấy mã đặt lại mật khẩu!", Toast.LENGTH_SHORT).show();
        finish();
    }

    btnReset.setOnClickListener(v -> saveNewPassword());
}
    private void saveNewPassword() {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy email của người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ResetPrefs", MODE_PRIVATE);
        String resetEmail = sharedPreferences.getString("resetEmail", "");
        String storedResetCode = sharedPreferences.getString("resetCode", "");
        long expiryTime = sharedPreferences.getLong("resetExpiry", 0);

        // Kiểm tra mã có hợp lệ không
        if (TextUtils.isEmpty(storedResetCode) || !storedResetCode.equals(resetCode) || System.currentTimeMillis() > expiryTime) {
            Toast.makeText(this, "Mã đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash mật khẩu trước khi lưu
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));

        // Lưu mật khẩu đã hash vào SharedPreferences
        SharedPreferences userPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor userEditor = userPreferences.edit();
        userEditor.putString("password_" + resetEmail, hashedPassword);
        userEditor.apply();

        // Đọc lại để kiểm tra xem đã lưu thành công chưa
        String savedPassword = userPreferences.getString("password_" + resetEmail, "");
        Log.d("PASSWORD_DEBUG", "Hashed Password Saved: " + savedPassword);

        if (!TextUtils.isEmpty(savedPassword)) {
            Toast.makeText(this, "Mật khẩu đã được cập nhật!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Lỗi khi lưu mật khẩu!", Toast.LENGTH_LONG).show();
        }

        // Xóa resetCode sau khi sử dụng
        sharedPreferences.edit().clear().apply();

        finish();
    }




}

package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;

public class activity_reset_password extends AppCompatActivity {

    private EditText etNewPassword, etConfirmPassword;
    private Button btnReset;
    private String resetCode;

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
            if (!TextUtils.isEmpty(resetCode)) {
                Toast.makeText(this, "Mã xác nhận: " + resetCode, Toast.LENGTH_SHORT).show();
            }
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

        // Lưu mật khẩu vào SharedPreferences (có thể thay bằng SQLite nếu cần)
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", newPassword);
        editor.apply();

        Toast.makeText(this, "Mật khẩu đã được cập nhật!", Toast.LENGTH_LONG).show();
        finish();
    }
}

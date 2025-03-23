package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

import java.util.Random;
import java.util.concurrent.Executors;

public class activity_forgot_password extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSend;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnSend = findViewById(R.id.btnSend);

        userDao = ShoeShopDatabase.getInstance(this).userDao();

        btnSend.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chạy kiểm tra email trong background thread
            Executors.newSingleThreadExecutor().execute(() -> {
                int emailExists = userDao.checkEmailExists(email);
                runOnUiThread(() -> {
                    if (emailExists > 0) {
                        sendResetEmail(email);
                    } else {
                        Toast.makeText(this, "Email không tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

    }

//    private void sendResetEmail(String email) {
//        String resetCode = generateResetCode();
//        // In resetCode ra log để kiểm tra
//        android.util.Log.d("RESET_CODE", "Mã reset: " + resetCode);
//        Executors.newSingleThreadExecutor().execute(() -> {
//            boolean emailSent = com.example.se1753net_gr2_onlineshoes.utils.EmailSender.sendEmail(email, resetCode);
//            runOnUiThread(() -> {
//                if (emailSent) {
//                    Toast.makeText(this, "Vui lòng kiểm tra email!", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "Lỗi khi gửi email!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//    }
private void sendResetEmail(String email) {
    String resetCode = generateResetCode();
    long expiryTime = System.currentTimeMillis() + (10 * 60 * 1000); // Hết hạn sau 10 phút

    // Lưu vào SharedPreferences
    SharedPreferences sharedPreferences = getSharedPreferences("ResetPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("resetCode", resetCode);
    editor.putLong("resetExpiry", expiryTime);
    editor.putString("resetEmail", email);
    editor.apply();
    Log.d("RESET_CODE", "Email: " + email + " | ResetCode: " + resetCode + " | Expiry: " + expiryTime);

    Executors.newSingleThreadExecutor().execute(() -> {
        boolean emailSent = com.example.se1753net_gr2_onlineshoes.activity.EmailSender.sendEmail(email, resetCode);
        runOnUiThread(() -> {
            if (emailSent) {
                Toast.makeText(this, "Vui lòng kiểm tra email!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Lỗi khi gửi email!", Toast.LENGTH_SHORT).show();
            }
        });
    });
}



    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}

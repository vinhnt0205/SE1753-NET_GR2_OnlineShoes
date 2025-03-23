package com.example.se1753net_gr2_onlineshoes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;

public class activity_edit_profile extends AppCompatActivity {
    private EditText etFullName, etEmail, etMobile, etAddress;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private ImageView ivAvatar;
    private Button btnSave, btnCancel;
    private UserDao userDao;
    private User currentUser;
    private String userId; // Sử dụng String thay vì int
    private Button btnChangePassword; // Thêm biến này

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        userDao = ShoeShopDatabase.getInstance(this).userDao();

        // Nhận USER_ID từ Intent (dưới dạng String)
        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy dữ liệu người dùng từ database trên Background Thread
        new Thread(() -> {
            currentUser = userDao.getUserById(userId);
            if (currentUser != null) {
                runOnUiThread(this::loadUserData);
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi: Không thể tải dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();

        // Xử lý sự kiện nút bấm
        btnSave.setOnClickListener(v -> saveUserData());
        btnCancel.setOnClickListener(v -> finish());
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(activity_edit_profile.this, activity_change_password.class);
            intent.putExtra("USER_ID", userId); // Truyền ID người dùng sang trang đổi mật khẩu
            startActivity(intent);
        });

    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etAddress = findViewById(R.id.etAddress);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        ivAvatar = findViewById(R.id.ivAvatar);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnChangePassword = findViewById(R.id.btnChangePassword); // Ánh xạ nút

    }

    private void loadUserData() {
        etFullName.setText(currentUser.username);
        etEmail.setText(currentUser.email);
        etMobile.setText(currentUser.phoneNumber);
        etAddress.setText(currentUser.address);

        if ("male".equalsIgnoreCase(currentUser.roleId)) {
            rbMale.setChecked(true);
        } else {
            rbFemale.setChecked(true);
        }
    }

    private void saveUserData() {
        String newEmail = etEmail.getText().toString().trim();

        if (!currentUser.email.equals(newEmail) && userDao.checkEmailExists(newEmail) > 0) {
            showToast("Email này đã tồn tại!");
            return;
        }

        currentUser.username = etFullName.getText().toString();
        currentUser.email = newEmail;
        currentUser.phoneNumber = etMobile.getText().toString();
        currentUser.address = etAddress.getText().toString();
        currentUser.updatedAt = System.currentTimeMillis();

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        currentUser.roleId = (selectedGenderId == R.id.rbMale) ? "male" : "female";

        new Thread(() -> {
            userDao.updateUser(currentUser);
            runOnUiThread(() -> {
                showToast("Cập nhật thông tin thành công!");
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            });
        }).start();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

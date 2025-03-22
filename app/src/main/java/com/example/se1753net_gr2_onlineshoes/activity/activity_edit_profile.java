package com.example.se1753net_gr2_onlineshoes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private String userId = "current_user_id"; // Thay bằng ID thực tế của người dùng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userDao = ShoeShopDatabase.getInstance(this).userDao();
        currentUser = userDao.getUserById(userId);

        initViews();
        loadUserData();

        btnSave.setOnClickListener(v -> saveUserData());
        btnCancel.setOnClickListener(v -> finish());
        Button btnGoToChangePassword = findViewById(R.id.btnGoToChangePassword);
        btnGoToChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(activity_edit_profile.this, activity_change_password.class);
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
    }

    private void loadUserData() {
        if (currentUser != null) {
            etFullName.setText(currentUser.username);
            etEmail.setText(currentUser.email);
            etMobile.setText(currentUser.roleId); // Giả sử mobile được lưu ở roleId, cần điều chỉnh
            etAddress.setText(currentUser.avatarUrl); // Giả sử address lưu ở avatarUrl, cần điều chỉnh

            if (currentUser.roleId.equals("male")) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }
        }
    }

    private void saveUserData() {
        if (currentUser != null) {
            currentUser.username = etFullName.getText().toString();
            currentUser.email = etEmail.getText().toString();
            currentUser.roleId = etMobile.getText().toString();
            currentUser.avatarUrl = etAddress.getText().toString();
            currentUser.updatedAt = new java.util.Date();

            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            if (selectedGenderId == R.id.rbMale) {
                currentUser.roleId = "male";
            } else {
                currentUser.roleId = "female";
            }

            userDao.updateUser(currentUser);
            Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }
    }
}

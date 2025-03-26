package com.example.se1753net_gr2_onlineshoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserRoleDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.UserRole;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class activity_register extends AppCompatActivity {
    private EditText etFullName, etEmail, etMobile, etAddress, etPassword, etConfirmPassword;
    private RadioGroup rgGender;
    private Button btnRegister;
    private UserDao userDao;
    private UserRoleDao userRoleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        ShoeShopDatabase db = ShoeShopDatabase.getInstance(this);
        userDao = db.userDao();
        userRoleDao = db.userRoleDao();

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
        String phoneNumber = etMobile.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (!validateInput(fullName, email, phoneNumber, address, password, confirmPassword)) {
            return;
        }

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        RadioButton selectedGenderRadio = findViewById(selectedGenderId);
        String gender = (selectedGenderRadio != null) ? selectedGenderRadio.getText().toString() : "Khác";

        new Thread(() -> {
            if (userDao.checkEmailExists(email) > 0) {
                runOnUiThread(() -> showToast("Email đã tồn tại!"));
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10)); // Đảm bảo số rounds là 10
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
                showToast("Đăng ký thành công!");
                startActivity(new Intent(activity_register.this, activity_login.class));
                finish();
            });
        }).start();

    }

    private boolean validateInput(String fullName, String email, String phoneNumber, String address, String password, String confirmPassword) {
        // Kiểm tra các trường không được để trống
        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        // Kiểm tra định dạng email hợp lệ
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Email không hợp lệ!");
            return false;
        }

        // Kiểm tra số điện thoại chỉ chứa 10 chữ số
        if (!phoneNumber.matches("^0[0-9]{9}$")) {
            showToast("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!");
            return false;
        }

        // Kiểm tra mật khẩu có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            showToast("Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return false;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu khớp nhau
        if (!password.equals(confirmPassword)) {
            showToast("Mật khẩu không khớp!");
            return false;
        }

        // Kiểm tra xem người dùng đã chọn giới tính chưa
        if (rgGender.getCheckedRadioButtonId() == -1) {
            showToast("Vui lòng chọn giới tính!");
            return false;
        }

        return true;
    }


    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}

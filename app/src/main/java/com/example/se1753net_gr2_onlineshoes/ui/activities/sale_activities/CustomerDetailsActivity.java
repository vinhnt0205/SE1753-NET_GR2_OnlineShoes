    package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

    import android.os.Bundle;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.lifecycle.ViewModelProvider;

    import com.example.se1753net_gr2_onlineshoes.R;
    import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
    import com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel.UserViewModel;

    public class CustomerDetailsActivity  extends AppCompatActivity {
        private TextView tvCustomerName, tvCustomerEmail, tvCustomerMobile, tvCustomerAddress, tvCustomerStatus;
        private UserViewModel userViewModel;
        private String userId;

        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_details);

            tvCustomerName = findViewById(R.id.tvCustomerName);
            tvCustomerEmail = findViewById(R.id.tvCustomerEmail);
            tvCustomerMobile = findViewById(R.id.tvCustomerMobile);
            tvCustomerAddress = findViewById(R.id.tvCustomerAddress);
            tvCustomerStatus = findViewById(R.id.tvCustomerStatus);

            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            userId = getIntent().getStringExtra("USER_ID");
            if (userId != null) {
                userViewModel.loadCustomerById(userId); // ✅ Gọi hàm load dữ liệu
            }

            // ✅ Quan sát dữ liệu từ ViewModel
            userViewModel.getSelectedCustomer().observe(this, user -> {
                if (user != null) {
                    displayCustomerInfo(user);
                }
            });
        }

        private void displayCustomerInfo(User user) {
            tvCustomerName.setText(user.getUsername());
            tvCustomerEmail.setText(user.getEmail());
            tvCustomerMobile.setText(user.getMobile());
            tvCustomerAddress.setText(user.getAddress());

        }


    }
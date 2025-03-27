package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.repository.CustomerRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.sale_adapter.CustomerAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.UserViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.sale_viewmodel.UserViewModel;

public class CustomerListActivity  extends AppCompatActivity {
    private RecyclerView rvCustomers;
    private CustomerAdapter adapter;
    private UserViewModel userViewModel;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        rvCustomers = findViewById(R.id.rvCustomers);
        etSearch = findViewById(R.id.etSearch);

        adapter = new CustomerAdapter(customer -> {
            Intent intent = new Intent(CustomerListActivity.this, OrderDetailsActivity.class);
            intent.putExtra("USER_ID", customer.getUserId());
            startActivity(intent);
        });

        rvCustomers.setLayoutManager(new LinearLayoutManager(this));
        rvCustomers.setAdapter(adapter);

        // 🔥 Sử dụng ViewModelFactory để khởi tạo UserViewModel
        Application application = getApplication();
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        OrderDao orderDao = database.orderDao();
        CustomerRepository repository = new CustomerRepository(orderDao);
        UserViewModelFactory factory = new UserViewModelFactory(application, repository);

        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        // 🔥 Quan sát danh sách khách hàng
        userViewModel.getCustomersList().observe(this, users -> adapter.setCustomers(users));
        userViewModel.loadAllCustomers();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userViewModel.searchCustomers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
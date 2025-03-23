package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.sale_adapter.OrderAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private EditText searchBox;
    private Spinner statusSpinner;
    private Button btnFilter;
    private DatePicker fromDate, toDate;
    private List<Order> orderList;
    private final int pageSize = 10;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_orders_list);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        searchBox = findViewById(R.id.searchBox);
        statusSpinner = findViewById(R.id.spinnerStatus);
        btnFilter = findViewById(R.id.btnFilter);
        fromDate = findViewById(R.id.fromDatePicker);
        toDate = findViewById(R.id.toDatePicker);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load danh sách đơn hàng ban đầu
        loadOrders("", "All", null, null);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                loadOrders(s.toString(), statusSpinner.getSelectedItem().toString(), null, null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btnFilter.setOnClickListener(v -> {
            Date from = parseDate(fromDate);
            Date to = parseDate(toDate);
            loadOrders(searchBox.getText().toString(), statusSpinner.getSelectedItem().toString(), from, to);
        });
    }

    private Date parseDate(DatePicker datePicker) {
        try {
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();
            String dateStr = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null; // Trả về null nếu không chọn ngày
        }
    }

    private void loadOrders(String keyword, String status, Date from, Date to) {
        ShoeShopDatabase db = ShoeShopDatabase.getInstance(this);
        orderList = db.orderDao().getFilteredOrders(keyword, status, from, to, pageSize);

        if (orderList.isEmpty()) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        }

        orderAdapter = new OrderAdapter(orderList, order -> {
            Intent intent = new Intent(OrderListActivity.this, OrderDetailsActivity.class);
            intent.putExtra("order_id", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(orderAdapter);
    }
}

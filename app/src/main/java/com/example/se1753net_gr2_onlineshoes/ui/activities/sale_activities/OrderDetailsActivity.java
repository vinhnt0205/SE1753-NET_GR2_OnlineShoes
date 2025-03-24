package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.repository.OrderRepository;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailsActivity extends AppCompatActivity {
    private TextView txtOrderId, txtTotalCost, txtStatus;
    private Button btnComplete;
    private Order currentOrder;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order_details);

        txtOrderId = findViewById(R.id.txtOrderId);
        txtTotalCost = findViewById(R.id.txtTotalCost);
        txtStatus = findViewById(R.id.txtStatus);
        btnComplete = findViewById(R.id.btnComplete);

        orderRepository = new OrderRepository(getApplication());

        String orderId = getIntent().getStringExtra("order_id");
        if (orderId != null) {
            loadOrderDetails(orderId);
        } else {
            Toast.makeText(this, "Invalid Order ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnComplete.setOnClickListener(v -> confirmOrderCompletion());
    }

    private void loadOrderDetails(String orderId) {
        orderRepository.getOrderById(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(order -> {
                    currentOrder = order;
                    txtOrderId.setText("Order ID: " + order.getOrderId());
                    txtTotalCost.setText("Total Cost: $" + order.getTotalCost());
                    txtStatus.setText("Status: " + order.getStatus());

                    if ("Completed".equals(order.getStatus())) {
                        btnComplete.setEnabled(false);
                    }
                }, throwable -> {
                    Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void confirmOrderCompletion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Completion")
                .setMessage("Are you sure you want to mark this order as completed?")
                .setPositiveButton("Yes", (dialog, which) -> updateOrderStatus())
                .setNegativeButton("No", null)
                .show();
    }

    private void updateOrderStatus() {
        if (currentOrder != null) {
            currentOrder.setStatus("Completed");

            orderRepository.updateOrder(currentOrder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        txtStatus.setText("Status: Completed");
                        btnComplete.setEnabled(false);
                        Toast.makeText(this, "Order marked as Completed", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Failed to update order", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}

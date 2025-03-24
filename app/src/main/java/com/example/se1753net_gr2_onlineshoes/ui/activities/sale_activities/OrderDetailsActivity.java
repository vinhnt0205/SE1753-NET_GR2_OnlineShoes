package com.example.se1753net_gr2_onlineshoes.ui.activities.sale_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;

public class OrderDetailsActivity extends AppCompatActivity {
    private TextView txtOrderId, txtTotalCost, txtStatus;
    private Button btnComplete;
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order_details);

        txtOrderId = findViewById(R.id.txtOrderId);
        txtTotalCost = findViewById(R.id.txtTotalCost);
        txtStatus = findViewById(R.id.txtStatus);
        btnComplete = findViewById(R.id.btnComplete);

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
        ShoeShopDatabase db = ShoeShopDatabase.getInstance(this);
        currentOrder = db.orderDao().getOrderById(orderId);

        if (currentOrder != null) {
            txtOrderId.setText("Order ID: " + currentOrder.getOrderId());
            txtTotalCost.setText("Total Cost: $" + currentOrder.getTotalCost());
            txtStatus.setText("Status: " + currentOrder.getStatus());

            if ("Completed".equals(currentOrder.getStatus())) {
                btnComplete.setEnabled(false);
            }
        } else {
            Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
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
            ShoeShopDatabase db = ShoeShopDatabase.getInstance(this);
            currentOrder.setStatus("Completed");
            db.orderDao().updateOrder(currentOrder);
            txtStatus.setText("Status: Completed");
            btnComplete.setEnabled(false);
            Toast.makeText(this, "Order marked as Completed", Toast.LENGTH_SHORT).show();
        }
    }
}

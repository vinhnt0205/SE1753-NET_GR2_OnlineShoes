package com.example.se1753net_gr2_onlineshoes.ui.adapter.sale_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import java.util.List;
import com.example.se1753net_gr2_onlineshoes.R;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final List<Order> orderList;
    private final OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderId.setText("Order ID: " + order.getOrderId());
        holder.customerName.setText("Customer: " + order.getCustomerName());
        holder.totalCost.setText("Total: $" + order.getTotalCost());
        holder.status.setText("Status: " + order.getStatus());
        holder.itemView.setOnClickListener(v -> listener.onOrderClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, customerName, totalCost, status, productInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.txtOrderId);
            customerName = itemView.findViewById(R.id.txtCustomerName);
            totalCost = itemView.findViewById(R.id.txtTotalCost);
            status = itemView.findViewById(R.id.txtStatus);
            productInfo = itemView.findViewById(R.id.txtProductInfo);
        }
    }
}

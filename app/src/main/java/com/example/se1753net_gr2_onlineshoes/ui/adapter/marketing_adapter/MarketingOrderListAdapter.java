package com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class MarketingOrderListAdapter extends RecyclerView.Adapter<MarketingOrderListAdapter.OrderViewHolder> {
    private List<Order> orderList = new ArrayList<>();

    public MarketingOrderListAdapter() {

    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_marketing_recent_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.trackingNumberTextView.setText(order.trackingNumber);
        holder.shippingAddressTextView.setText(order.shippingAddress);
        holder.dateTextView.setText(order.orderDate.toString());
        //holder.imageView.setImageResource(product);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView trackingNumberTextView, shippingAddressTextView, dateTextView;
        ImageView orderImageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            trackingNumberTextView = itemView.findViewById(R.id.trackingNumberTextView);
            shippingAddressTextView = itemView.findViewById(R.id.shippingAddressTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            orderImageView = itemView.findViewById(R.id.orderImageView);
        }
    }
}

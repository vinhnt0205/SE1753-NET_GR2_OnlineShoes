package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Orders")
public class Order {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "order_id")
    public String orderId;

    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "order_date")
    public String orderDate;

    @ColumnInfo(name = "status")
    public String status; // ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled')

    @ColumnInfo(name = "total_amount")
    public double totalAmount;

    @ColumnInfo(name = "shipping_address")
    public String shippingAddress;

    @ColumnInfo(name = "recipient_name")
    public String recipientName;

    @ColumnInfo(name = "recipient_phone")
    public String recipientPhone;

    @ColumnInfo(name = "tracking_number")
    public String trackingNumber;
}

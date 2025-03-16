package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Order_Details")
public class OrderDetail {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "order_detail_id")
    public String orderDetailId;

    @ColumnInfo(name = "order_id")
    public String orderId;

    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "price")
    public double price;
}

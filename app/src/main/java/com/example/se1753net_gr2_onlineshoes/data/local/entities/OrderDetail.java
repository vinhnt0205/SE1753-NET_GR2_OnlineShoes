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

    @NonNull
    @ColumnInfo(name = "order_id")
    public String orderId;

    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @NonNull
    @ColumnInfo(name = "quantity")
    public int quantity;

    @NonNull
    @ColumnInfo(name = "price")
    public Double price;
}
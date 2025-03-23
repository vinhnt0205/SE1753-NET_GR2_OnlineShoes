package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Orders")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class Order {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "order_id")
    public String orderId;

    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @NonNull
    @ColumnInfo(name = "order_date")
    public Date orderDate;

    @NonNull
    @ColumnInfo(name = "status")
    public String status;

    @NonNull
    @ColumnInfo(name = "shipping_address")
    public String shippingAddress;

    @NonNull
    @ColumnInfo(name = "recipient_name")
    public String recipientName;

    @NonNull
    @ColumnInfo(name = "recipient_phone")
    public String recipientPhone;

    @ColumnInfo(name = "tracking_number")
    public String trackingNumber;
}


package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
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

//    public OrderDetail(int i, int i1, int i2, double v) {
//    }

    public OrderDetail() {
    }

    @Ignore
    public OrderDetail(@NonNull String orderDetailId, int quantity, @NonNull String productId, @NonNull String orderId, @NonNull Double price) {
        this.orderDetailId = orderDetailId;
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
        this.price = price;
    }

    @NonNull
    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(@NonNull String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @NonNull
    public Double getPrice() {
        return price;
    }

    public void setPrice(@NonNull Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }
}
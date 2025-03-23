package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;
import androidx.room.Ignore;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "Orders")
@TypeConverters(DateConverter.class)
public class Order {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "order_id")
    private String orderId;

    @NonNull
    @ColumnInfo(name = "user_id")
    private String userId;

    @NonNull
    @ColumnInfo(name = "order_date")
    private Date orderDate;

    @NonNull
    @ColumnInfo(name = "status")
    private String status;

    @NonNull
    @ColumnInfo(name = "shipping_address")
    private String shippingAddress;

    @NonNull
    @ColumnInfo(name = "recipient_name")
    private String recipientName;

    @NonNull
    @ColumnInfo(name = "recipient_phone")
    private String recipientPhone;

    @ColumnInfo(name = "tracking_number")
    private String trackingNumber;

    @ColumnInfo(name = "total_cost")
    private double totalCost;

    @Ignore  // Không lưu danh sách sản phẩm vào database
    private List<Product> productList;

    // Constructor
    public Order(@NonNull String orderId, @NonNull String userId, @NonNull Date orderDate,
                 @NonNull String status, @NonNull String shippingAddress, @NonNull String recipientName,
                 @NonNull String recipientPhone, String trackingNumber, double totalCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.trackingNumber = trackingNumber;
        this.totalCost = totalCost;
    }

    // Getter và Setter
    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(@NonNull Date orderDate) {
        this.orderDate = orderDate;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(@NonNull String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @NonNull
    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(@NonNull String recipientName) {
        this.recipientName = recipientName;
    }

    @NonNull
    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(@NonNull String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerName() {
        return recipientName;
    }

    // Getter và Setter cho danh sách sản phẩm
    public List<Product> getProductList() {
        return productList;
    }


    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

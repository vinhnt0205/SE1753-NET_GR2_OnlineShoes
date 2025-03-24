package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Customer_Activity_Summary", primaryKeys = {"user_id", "activity_type", "product_id"})
public class CustomerActivitySummary {
    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @NonNull
    @ColumnInfo(name = "activity_type")
    public String activityType; // 'View', 'Add to Cart', 'Purchase'

    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "count")
    public Integer count;
}
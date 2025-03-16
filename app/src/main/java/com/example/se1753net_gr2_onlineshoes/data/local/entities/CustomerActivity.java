package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Customer_Activity")
public class CustomerActivity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "activity_id")
    public String activityId;

    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "activity_type")
    public String activityType; // ENUM('View', 'Add to Cart', 'Purchase', 'Feedback')

    @ColumnInfo(name = "product_id")
    public String productId; // Nullable for non-product actions

    @ColumnInfo(name = "created_at")
    public String createdAt;
}


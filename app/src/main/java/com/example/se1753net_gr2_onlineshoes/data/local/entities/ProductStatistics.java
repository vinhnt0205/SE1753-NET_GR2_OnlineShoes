package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Product_Statistics")
public class ProductStatistics {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "stat_id")
    public String statId;

    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "views")
    public int views;

    @ColumnInfo(name = "total_sold")
    public int totalSold;

    @ColumnInfo(name = "avg_rating")
    public Double avgRating;

    @ColumnInfo(name = "created_at")
    public String createdAt;
}


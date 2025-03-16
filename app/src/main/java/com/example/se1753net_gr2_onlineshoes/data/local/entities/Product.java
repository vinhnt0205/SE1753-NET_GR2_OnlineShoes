package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Products")
public class Product {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "sale_price")
    public Double salePrice; // Nullable

    @ColumnInfo(name = "stock")
    public int stock;

    @ColumnInfo(name = "brand_id")
    public String brandId;

    @ColumnInfo(name = "is_featured")
    public boolean isFeatured;

    @ColumnInfo(name = "status")
    public String status; // ENUM('Active', 'Inactive')

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "updated_at")
    public String updatedAt;
}


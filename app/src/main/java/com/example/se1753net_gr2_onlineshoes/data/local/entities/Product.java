package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.se1753net_gr2_onlineshoes.data.local.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "Products")
@TypeConverters(DateConverter.class) // Apply TypeConverter
public class Product {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @NonNull
    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name = "sale_price")
    public Double salePrice;

    @NonNull
    @ColumnInfo(name = "stock")
    public int stock;

    @NonNull
    @ColumnInfo(name = "brand_id")
    public String brandId;

    @ColumnInfo(name = "is_featured")
    public Boolean isFeatured;

    @NonNull
    @ColumnInfo(name = "status")
    public String status;

    @NonNull
    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @NonNull
    @ColumnInfo(name = "updated_at")
    public Date updatedAt;

}


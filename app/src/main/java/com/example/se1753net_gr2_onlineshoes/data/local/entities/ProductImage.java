package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Product_Images")
public class ProductImage {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image_id")
    public String imageId;

    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @ColumnInfo(name = "created_at")
    public String createdAt;
}

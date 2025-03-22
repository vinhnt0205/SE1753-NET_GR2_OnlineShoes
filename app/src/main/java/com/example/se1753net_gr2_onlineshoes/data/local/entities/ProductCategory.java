package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;

@Entity(tableName = "Product_Categories", primaryKeys = {"product_id", "category_id"})
public class ProductCategory {
    @NonNull
    @ColumnInfo(name = "product_id")
    public String productId;

    @NonNull
    @ColumnInfo(name = "category_id")
    public String categoryId;
}

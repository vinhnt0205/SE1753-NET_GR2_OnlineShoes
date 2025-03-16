package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "category_id")
    public String categoryId;

    @ColumnInfo(name = "category_name")
    public String categoryName;
}


package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Brands")
public class Brand {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "brand_id")
    public String brandId;

    @NonNull
    @ColumnInfo(name = "brand_name")
    public String brandName;
}
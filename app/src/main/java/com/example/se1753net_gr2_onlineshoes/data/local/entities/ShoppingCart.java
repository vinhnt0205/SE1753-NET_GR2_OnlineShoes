package com.example.se1753net_gr2_onlineshoes.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Shopping_Cart")
public class ShoppingCart {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cart_id")
    public String cartId;

    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "created_at")
    public String createdAt;
}
